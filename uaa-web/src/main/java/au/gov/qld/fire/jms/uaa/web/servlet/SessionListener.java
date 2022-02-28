package au.gov.qld.fire.jms.uaa.web.servlet;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;

import au.gov.qld.fire.IBeanNames;
import au.gov.qld.fire.domain.location.State;
import au.gov.qld.fire.domain.security.User;
import au.gov.qld.fire.jms.uaa.service.EntityService;
import au.gov.qld.fire.web.SessionConstants;
import au.gov.qld.fire.web.WebUtils;
import au.gov.qld.fire.util.DateUtils;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class SessionListener implements HttpSessionListener
{

    /** Used for logging. */
    private static Logger LOG = Logger.getLogger(SessionListener.class);

    /** Used for WEB_STAT logging. */
    private static final Log WEB_STAT = LogFactory.getLog("WEB_STAT");

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpSessionListener#sessionCreated(javax.servlet.http.HttpSessionEvent)
     */
    public void sessionCreated(HttpSessionEvent event)
    {
    	WEB_STAT.debug("Creating session");

        HttpSession session = event.getSession();
        //init session variable(s) here
        //pattern
        session.setAttribute(SessionConstants.DATE_PATTERN_KEY, DateUtils.DD_MM_YYYY.toPattern());
        session.setAttribute(SessionConstants.TIME_PATTERN_KEY, DateUtils.HH_mm.toPattern());
        //top menu
        session.setAttribute("logout", "logout.do?dispatch=logout");
        //main menu
        session.setAttribute("aboutUrl", "about.do");
        session.setAttribute("adminUrl", "admin.do");
        session.setAttribute("activationUrl", "activation.do");
        session.setAttribute("incidentMpfUrl", "incidentMpf.do");
        session.setAttribute("incidentPmaUrl", "incidentPma.do");
        session.setAttribute("a23CommentIssueUrl", "a23CommentIssue.do");
        session.setAttribute("awaitingExportUrl", "awaitingExport.do");
        session.setAttribute("reportUrl", "report.do");
        //user security groups
        //session.setAttribute(SecurityGroupEnum.ADMINISTRATOR.getValue(), SecurityGroupEnum.ADMINISTRATOR);

        WebApplicationContext ctx = WebUtils.getApplicationContext(session.getServletContext());
        EntityService entityService = (EntityService) ctx.getBean(IBeanNames.ENTITY_SERVICE);
        try
        {
            //default State
            List<State> stateDefault = entityService.findStateDefault();
            if (stateDefault.isEmpty())
            {
                LOG.warn("Failed to find default State");
            }
            else
            {
                WebUtils.setStateDefault(session, stateDefault.get(0).getState());
            }
        }
        catch (Exception e)
        {
            LOG.warn("Failed to find default State: " + e.getMessage());
        }
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpSessionListener#sessionDestroyed(javax.servlet.http.HttpSessionEvent)
     */
    public void sessionDestroyed(HttpSessionEvent event)
    {
        HttpSession session = event.getSession();
        //get user in http session
        User user = WebUtils.getUser(session);
        if (user != null)
        {
            //log session destroyed
            WEB_STAT.info("Destroying session: " + user.getLogin());
        }
        //do manual cleanup here
    }

}