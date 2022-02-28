package au.gov.qld.fire.jms.uaa.web;

import javax.security.auth.login.AccountExpiredException;
import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import au.gov.qld.fire.web.SessionConstants;
import au.gov.qld.fire.web.util.LoginUtils;

/**
 * 
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Controller
public class LoginController
{

    /** Logger */
	private static final Logger LOG = Logger.getLogger(LoginController.class);

    @RequestMapping(value="/login.do", params = "!dispatch")
    public String main()
    {
        return "login";
    }

    @RequestMapping(value="/j_security_check", params = "dispatch=login")
    public String login(@RequestParam(value = "j_username") String login,
        @RequestParam(value = "password") char[] password,
        HttpServletRequest request, ModelMap model)
    {
        LOG.debug("INSIDE login..");
        try
        {
            //login (JAAS)
            LoginUtils.login(request, login, password);
            //
            String requestedUrl = (String) request.getSession().getAttribute(SessionConstants.REQUESTED_URL);
            if (requestedUrl != null)
            {
                request.getSession().removeAttribute(SessionConstants.REQUESTED_URL);
                return requestedUrl;
            }
            return "redirect:main.do";
        }
        catch (AccountExpiredException e)
        {
            LOG.warn(e.getMessage());
            //STEP 1 - add parameter
            model.addAttribute("login", login);
            return "redirect:user/changePassword.do";
        }
        catch (LoginException e)
        {
        	model.addAttribute("exception", e);
            return "login";
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
        	model.addAttribute("exception", e);
            return "login";
        }
    }

    @RequestMapping(value="/logout.do")
    public String logout(HttpServletRequest request, ModelMap model) throws Exception
    {
        //logout (JAAS)
        LoginUtils.logout(request);
        return "redirect:login.do";
    }

}