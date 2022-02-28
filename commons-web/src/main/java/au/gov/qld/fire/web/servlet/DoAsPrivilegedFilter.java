package au.gov.qld.fire.web.servlet;

import java.io.IOException;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;

import javax.security.auth.Subject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import au.gov.qld.fire.web.WebUtils;

public class DoAsPrivilegedFilter implements Filter
{
    /** Used for logging. */
    private static Logger LOG = Logger.getLogger(DoAsPrivilegedFilter.class);

    /** filterConfig */
    //private FilterConfig filterConfig;

    /* (non-Javadoc)
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */
    public void init(FilterConfig filterConfig) throws ServletException
    {
        //this.filterConfig = filterConfig;
    }

    /* (non-Javadoc)
     * @see javax.servlet.Filter#destroy()
     */
    public void destroy()
    {
        //filterConfig = null;
    }

    public void doFilter(final ServletRequest request, final ServletResponse response,
        final FilterChain chain) throws IOException, ServletException
    {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        Subject subject = WebUtils.getSubject(httpRequest);
        if (subject == null) {
            chain.doFilter(request, response);
            return;
//            if (LOG.isDebugEnabled()) LOG.debug("No Subject found, so creating new Subject.");
//            subject = new Subject();
        }

        try
        {
            //if (LOG.isDebugEnabled()) LOG.debug("[BEGIN] Running doAsPrivileged block with Subject: " + subject);
            //DoAsPrivilegedFilter passes in a null AccessControllerContext
            //as the third argument to doAsPrivileged, assuring that only
            //authenticated Subject is used for authorization.
            Subject.doAsPrivileged(subject, new PrivilegedExceptionAction<Object>() {
                public Object run() throws Exception {
                    chain.doFilter(request, response);
                    return null;
                }
            }, null);
            //if (LOG.isDebugEnabled()) LOG.debug("[END] Running doAsPrivileged block with Subject: " + subject);
        }
        catch (PrivilegedActionException e)
        {
            Throwable t = ExceptionUtils.getCause(e);
            if (t == null) {
            	t = e;
            }
            LOG.error(t.getMessage(), t);
            throw new ServletException(t.getMessage());
        }
    }

}