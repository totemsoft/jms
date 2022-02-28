package au.gov.qld.fire.jms.web;

import java.io.IOException;
import java.util.Arrays;
import java.util.Set;

import javax.security.auth.Subject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.InvalidCancelException;

import au.gov.qld.fire.security.GroupPrincipal;
import au.gov.qld.fire.domain.security.SystemFunction;
import au.gov.qld.fire.web.UrlConstants;
import au.gov.qld.fire.web.WebUtils;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class RequestProcessor extends org.apache.struts.action.RequestProcessor //DelegatingRequestProcessor
{

    /** logger */
    private static final Logger LOG = Logger.getLogger(RequestProcessor.class);

    /* (non-Javadoc)
     * @see org.apache.struts.action.RequestProcessor#processRoles(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.apache.struts.action.ActionMapping)
     */
    @Override
    protected boolean processRoles(HttpServletRequest request, HttpServletResponse response,
        ActionMapping mapping) throws IOException, ServletException
    {
        String uri = request.getRequestURI(); // eg /webapp/action.do
        //String contextPath = request.getContextPath(); //webapp
        String q = request.getQueryString();
        String url = uri + (q == null ? "" : "?" + q);

        //get request url details
        String module = mapping.getModuleConfig().getPrefix();
        String path = mapping.getPath(); // eg /action
        if (UrlConstants.LOGIN_PATH.equals(path) || "/login".equals(path) || UrlConstants.LOGOUT_PATH.equals(path)
            || path.equals(UrlConstants.CHANGE_PASSWORD_PATH)
            || path.equals(UrlConstants.EMPTY_PATH)
            || path.equals(UrlConstants.ERROR_PATH))
        {
            if (LOG.isTraceEnabled()) LOG.trace("Access granted for: " + url);
            return true;
        }

        String error = "Access denied for: " + url + ".";
        try
        {
            //Struts mapping roles will be ignored!!!
            String[] roles = mapping.getRoleNames();
            Arrays.sort(roles);
            if (roles.length > 0)
            {
                LOG.warn("Struts mapping roles will be ignored. Access checking for: " + url);
            }

            //Check subject access group (loaded during login)
            Subject subject = WebUtils.getSubject(request);
            if (subject == null)
            {
                throw new SecurityException("No subject found.");
            }
            Set<GroupPrincipal> groupPrincipals = subject.getPrincipals(GroupPrincipal.class);
            if (groupPrincipals.isEmpty())
            {
                throw new SecurityException("No GroupPrincipal found for subject.");
            }
            if (groupPrincipals.size() > 1)
            {
                throw new SecurityException("More than one GroupPrincipal found for subject.");
            }

            //used by GroupPrincipal access control
            String query = null;
            String[] paramValues = request.getParameterValues("parameter");
            if (paramValues != null && paramValues.length == 1 && StringUtils.isNotBlank(paramValues[0]))
            {
                query = "parameter=" + paramValues[0];
            }

            //get GroupPrincipal (only one should exists)
            GroupPrincipal groupPrincipal = (GroupPrincipal) groupPrincipals.toArray()[0];
            SystemFunction systemFunction = groupPrincipal.findSystemFunction(module, path, query);
            if (systemFunction != null)
            {
            	if (LOG.isTraceEnabled()) LOG.trace("Access granted for: " + url + ". " + subject + systemFunction);
                //response.setDateHeader("Expires", System.currentTimeMillis() + 1 * 1000L);
                return true;
            }
        }
        catch (Exception e)
        {
            error += " " + e.getMessage();
        }

        //subject has no access group
        String accessDeniedURL = UrlConstants.ACCESS_DENIED_URL;
        if (LOG.isTraceEnabled()) LOG.trace("Access denied for: " + url + ". Redirecting to [" + accessDeniedURL + "]");
        accessDeniedURL = request.getContextPath() + accessDeniedURL;

        //response.getWriter().write("<script language=javascript>");
        //response.getWriter().write("top.location.href='" + accessDeniedURL + "';");
        //response.getWriter().write("</script>");
        //response.sendRedirect(request.getContextPath() + mapping.findForward("???").getPath());

        //401
        //error = getInternal().getMessage("text.accessDenied", mapping.getPath());
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, error);
        //request.getRequestDispatcher(UrlConstants.ACCESS_DENIED_URL).forward(request, response);
        return false;
    }

    /* (non-Javadoc)
     * @see org.apache.struts.action.RequestProcessor#processValidate(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.apache.struts.action.ActionForm, org.apache.struts.action.ActionMapping)
     */
    @Override
    protected boolean processValidate(HttpServletRequest request, HttpServletResponse response,
        ActionForm form, ActionMapping mapping) throws IOException, ServletException,
        InvalidCancelException
    {
        return super.processValidate(request, response, form, mapping);
    }

}