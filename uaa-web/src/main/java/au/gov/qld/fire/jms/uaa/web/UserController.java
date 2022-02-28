package au.gov.qld.fire.jms.uaa.web;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import au.gov.qld.fire.domain.security.User;
import au.gov.qld.fire.jms.uaa.service.EntityService;
import au.gov.qld.fire.service.UserService;
import au.gov.qld.fire.web.SessionConstants;
import au.gov.qld.fire.web.util.LoginUtils;

/**
 * 
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Controller
public class UserController
{

    /** Logger */
	private static final Logger LOG = Logger.getLogger(UserController.class);

	@Inject private EntityService entityService;

	@Inject private UserService userService;

    @RequestMapping(value="/user/changePassword.do", method = RequestMethod.GET)
    public String changePassword(@RequestParam(value = "login") String login,
        HttpServletRequest request, ModelMap model) throws Exception
    {
        model.addAttribute("entity", userService.findByLogin(login));
        return "user/changePassword";
    }


    @RequestMapping(value="/user/changePassword.do", method = RequestMethod.POST)
    public String changePasswordSave(@ModelAttribute(value = "entity") User user,
        @RequestParam(value = "password") char[] password,
        @RequestParam(value = "passwordNew") char[] passwordNew,
        @RequestParam(value = "passwordConfirm") char[] passwordConfirm,
        HttpServletRequest request, ModelMap model) throws Exception
    {
        try
        {
        	// TODO: validate
        	
            //change user password
        	user = userService.changePassword(user.getLogin(), password, passwordNew);
            //login (JAAS)
            LoginUtils.login(request, user.getLogin(), passwordNew);
            //STEP 2 - add parameter to forward
            model.addAttribute("id", user.getId());
            return "redirect:changeContact.do"; // relative
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
        	model.addAttribute("exception", e);
            return "user/changePassword";
        }
    }

    @RequestMapping(value="/user/changeContact.do", method = RequestMethod.GET)
    public String changeContact(@RequestParam(value = "id") Long id,
        HttpServletRequest request, ModelMap model) throws Exception
    {
    	model.addAttribute(SessionConstants.SALUTATIONS, entityService.findSalutations());
        model.addAttribute("entity", userService.findUserById(id));
        return "user/changeContact";
    }

    @RequestMapping(value="/user/changeContact.do", method = RequestMethod.POST)
    public String changeContactSave(@ModelAttribute(value = "entity") User user,
        HttpServletRequest request, ModelMap model) throws Exception
    {
        try
        {
        	// TODO: validate
        	
        	// TODO: save
        	

        	return "redirect:/main.do"; // absolute
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
        	model.addAttribute("exception", e);
            return "user/changeContact";
        }
    }

}