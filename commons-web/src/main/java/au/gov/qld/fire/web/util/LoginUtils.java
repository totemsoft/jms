package au.gov.qld.fire.web.util;

import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import au.gov.qld.fire.security.ILoginModuleNames;
import au.gov.qld.fire.security.auth.callback.LoginCallbackHandler;
import au.gov.qld.fire.web.WebUtils;

/**
 *
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
//@Component
public class LoginUtils
{

    /** Logger */
	private static final Logger LOG = Logger.getLogger(LoginUtils.class);

	/**
	 * 
	 * @param request
	 * @param login
	 * @param password
	 * @throws Exception
	 */
	public static void login(HttpServletRequest request, final String login, final char[] password)
        throws Exception
    {
        //login (JAAS)
        CallbackHandler callbackHandler = new LoginCallbackHandler(login, password);
        LoginContext ctx = new LoginContext(ILoginModuleNames.LOGIN_CONFIG, callbackHandler);
        ctx.login();

        //success - store subject
        Subject subject = ctx.getSubject();
        WebUtils.setSubject(request.getSession(), subject);
        WebUtils.setLoginContext(request.getSession(), ctx);
        LOG.info("Authenticated Subject: " + subject);
    }

	/**
	 * 
	 * @param request
	 * @throws Exception
	 */
	public static void logout(HttpServletRequest request) throws Exception
    {
        HttpSession session = request.getSession(false);
        LoginContext ctx = WebUtils.getLoginContext(session);
        if (ctx != null)
        {
            ctx.logout();
        }
        // default - invalidate session and let filter redirect.
        if (session != null)
        {
            WebUtils.setSubject(session, null);
            session.invalidate();
        }
    }

}