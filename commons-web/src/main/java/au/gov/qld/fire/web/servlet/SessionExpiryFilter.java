package au.gov.qld.fire.web.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import au.gov.qld.fire.util.Encoder;
import au.gov.qld.fire.web.SessionConstants;
import au.gov.qld.fire.web.UrlConstants;
import au.gov.qld.fire.web.WebUtils;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class SessionExpiryFilter implements Filter
{
    /** Used for logging. */
    private static Logger LOG = Logger.getLogger(SessionExpiryFilter.class);

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
        //this.filterConfig = null;
    }

    /* (non-Javadoc)
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException
    {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String context = httpRequest.getContextPath();
        String uri = httpRequest.getRequestURI(); // eg /webapp/xxx.do
        String path = uri.substring(context.length());
        String query = httpRequest.getQueryString();
        String url = uri + (query == null ? "" : "?" + query);

        if (uri.contains(UrlConstants.LOGIN_PATH) || uri.contains("/login.do") || uri.contains("/security.do"))
        {
            if (LOG.isDebugEnabled()) LOG.debug("Login. RequestURI: " + url);
        }
        else if (uri.contains(UrlConstants.CHANGE_PASSWORD_PATH))
        {
            if (LOG.isDebugEnabled()) LOG.debug("Change password. RequestURI: " + url);
        }
        else if (uri.contains(UrlConstants.LOGOUT_PATH))
        {
            if (LOG.isDebugEnabled()) LOG.debug("Logout. RequestURI: " + url);
            // SC_TEMPORARY_REDIRECT = 307, SC_UNAUTHORIZED = 401, SC_PROXY_AUTHENTICATION_REQUIRED = 407
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.getWriter().write("Your session is expired. Please login again.");
        }
        else if (isRequestedSessionExpired(httpRequest) || WebUtils.getSubject(httpRequest) == null)
        {
            LOG.warn("Session expired. RequestURI: " + url);
            String loginURL = UrlConstants.LOGIN_URL;
            if (!uri.equals(loginURL)) {
                LOG.warn("... Redirecting to login page [" + loginURL + "]");
                // used to set response failure
                // SC_TEMPORARY_REDIRECT = 307, SC_UNAUTHORIZED = 401, SC_PROXY_AUTHENTICATION_REQUIRED = 407
                httpResponse.setStatus(HttpServletResponse.SC_PROXY_AUTHENTICATION_REQUIRED);
                httpResponse.getWriter().write("Your session is expired. Please login again.");
                httpResponse.sendRedirect(context + "/logout.do?dispatch=logout&" + SessionConstants.REQUESTED_URL + "=" + Encoder.base64(url.getBytes()));
                return;
            }
        }
        //execute business logic here
        //if (LOG.isDebugEnabled())
        //    LOG.debug("[BEGIN] doFilter");
        chain.doFilter(httpRequest, httpResponse);
        //if (LOG.isDebugEnabled())
        //    LOG.debug("[END] doFilter");
    }

    /**
     *
     * @param request
     * @return
     */
    private boolean isRequestedSessionExpired(HttpServletRequest request)
    {
        if (request.getRequestedSessionId() != null && !request.isRequestedSessionIdValid())
        {
            //will never create new session and thus set new session cookie to response
            HttpSession session = request.getSession(false);
            if (session == null)
            {
                return true;
            }
            if (session.isNew())
            {
                return false;
            }
        }
        return false;
    }

}