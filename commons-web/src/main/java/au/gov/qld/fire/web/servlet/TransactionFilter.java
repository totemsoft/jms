package au.gov.qld.fire.web.servlet;

import java.io.IOException;
import java.util.Date;

import javax.security.auth.Subject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import au.gov.qld.fire.IBeanNames;
import au.gov.qld.fire.domain.security.User;
import au.gov.qld.fire.util.DateUtils;
import au.gov.qld.fire.util.ThreadLocalUtils;
import au.gov.qld.fire.web.WebUtils;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class TransactionFilter implements Filter
{

    /** Used for logging. */
    private static final Logger LOG = Logger.getLogger(TransactionFilter.class);

    /** Used for WEB_STAT logging. */
    private static final Log WEB_STAT = LogFactory.getLog("WEB_STAT");

    private PlatformTransactionManager transactionManager;

    /* (non-Javadoc)
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */
    public void init(FilterConfig filterConfig) throws ServletException
    {
        this.transactionManager = WebUtils.getBean(filterConfig.getServletContext(),
            IBeanNames.TRANSACTION_MANAGER, PlatformTransactionManager.class);
    }

    /* (non-Javadoc)
     * @see javax.servlet.Filter#destroy()
     */
    public void destroy()
    {

    }

    /* (non-Javadoc)
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException
    {
        Date txnStartDate = DateUtils.getCurrentDateTime();

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String uri = httpRequest.getRequestURI(); // eg /jms/xxx.do
        String query = httpRequest.getQueryString();
        //if (LOG.isDebugEnabled()) LOG.debug("RequestURI [" + uri + "?" + query + "]");

        Subject subject = WebUtils.getSubject(httpRequest);
        HttpSession session = httpRequest.getSession(false);
        //eg /jms/login.do
        if (subject == null || session == null || !httpRequest.isRequestedSessionIdValid())
        {
            //log performance (no login)
            WEB_STAT.info(getStatisticMessage(httpRequest, null, txnStartDate));
            //execute business logic here
            chain.doFilter(httpRequest, httpResponse);
            return;
        }

        //user logged in already (has valid Subject)
        synchronized (session)
        {
            // get/set propogationBehavior
            Integer p = WebUtils.getPropogationBehavior(httpRequest);
            if (p == null)
            {
                p = TransactionDefinition.PROPAGATION_REQUIRED;
            }
            if (p == TransactionDefinition.PROPAGATION_NOT_SUPPORTED || p == TransactionDefinition.PROPAGATION_NEVER)
            {
                // log performance
                LOG.info(getStatisticMessage(httpRequest, null, txnStartDate));
                // execute business logic here
                chain.doFilter(httpRequest, httpResponse);
                return;
            }

            DefaultTransactionDefinition txnDef = new DefaultTransactionDefinition(p);

            //set readOnly flag
            boolean readOnly = WebUtils.isReadOnly(httpRequest);
            if (LOG.isDebugEnabled())
                LOG.debug("Transaction readOnly=" + readOnly);
            txnDef.setReadOnly(readOnly);

            // Return a currently active transaction or create a new one,
            // according to the specified propagation behavior.
            TransactionStatus txnStatus = transactionManager.getTransaction(txnDef);
            if (txnStatus.isNewTransaction())
            {
                //add to session, so Action can control rollback
                session.setAttribute(TransactionStatus.class.getSimpleName(), txnStatus);
                if (LOG.isDebugEnabled())
                    LOG.debug("Transaction created [" + uri + "] ...");
            }

            if (readOnly)
            {
                //set rollbackOnly for readOnly transaction
                txnStatus.setRollbackOnly();
            }

            //set transaction current date/user (will be used in HibernateInterceptor to Audit data)
            ThreadLocalUtils.setDate(txnStartDate);
            //get/set user in http session
            User user = WebUtils.getUser(session);
            if (user == null)
            {
                user = new User(subject);
                WebUtils.setUser(session, user);
            }
            ThreadLocalUtils.setUser(user);
            try
            {
                //execute business logic here
                chain.doFilter(httpRequest, httpResponse);
                //
                if (txnStatus.isRollbackOnly())
                {
                    transactionManager.rollback(txnStatus);
                    if (LOG.isDebugEnabled())
                        LOG.debug("... Transaction rollback [" + uri + "].");
                }
                else if (txnStatus.isNewTransaction()) // && txnStatus.isCompleted()
                {
                    transactionManager.commit(txnStatus);
                    if (LOG.isDebugEnabled())
                        LOG.debug("... Transaction commit [" + uri + "].");
                }

                //log performance
                WEB_STAT.info(getStatisticMessage(httpRequest, user, txnStartDate));
            }
            catch (Exception e)
            {
                Throwable t = ExceptionUtils.getCause(e);
                if (t == null)
                {
                	t = e;
                }
                // log performance
                WEB_STAT.error(getStatisticMessage(httpRequest, user, txnStartDate));

                LOG.error("... Unable to doFilter in transaction: " + t.getMessage(), t);
                if (txnStatus.isNewTransaction() && !(t instanceof RuntimeException))
                {
                    transactionManager.rollback(txnStatus);
                    LOG.error("... Transaction rollback [" + uri
                        + (query == null ? "" : ("?" + query)) + "].");
                }
                httpResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Unable to doFilter in transaction: " + t.getMessage());
            }
            finally
            {
                //cleanup session and thread
                if (txnStatus.isNewTransaction() && httpRequest.isRequestedSessionIdValid())
                {
                    session.removeAttribute(TransactionStatus.class.getSimpleName());
                }
                ThreadLocalUtils.clear();
            }
        }
    }

    /**
     * Create message to log performance
     * @param httpRequest
     * @param user
     * @param startDate
     * @return
     */
    private String getStatisticMessage(HttpServletRequest httpRequest, User user, Date startDate)
    {
        String uri = httpRequest.getRequestURI(); // eg /jms/xxx.do
        String query = httpRequest.getQueryString();
        double delta = ((System.currentTimeMillis() - startDate.getTime()) * 1000) / 1000000.;
        return httpRequest.getRemoteHost() + "," + httpRequest.getRemoteAddr() + ","
            + (user == null ? "" : user.getLogin()) + "," + uri
            + (query == null ? "" : ("?" + query)) + "," + delta;
    }

}