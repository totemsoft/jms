package au.gov.qld.fire.jms.uaa.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import au.gov.qld.fire.service.ServiceException;

/**
 * Initialise main page layout (based on user security role, preferences)
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Controller
public class MainController
{

	//@Inject private UserService userService;

    @RequestMapping(value="/main.do", params = "!dispatch", method = RequestMethod.GET)
    public String main(ModelMap model) throws ServiceException
    {
        return "main";
    }

//    @RequestMapping(value = "/main.do", params = "dispatch=find")
//    public String menuFind(ModelMap model) throws Exception {
//        //set home page - per role
//    	SystemFunction home = userService.getUserHome();
//        model.addAttribute("home", home == null ? null : home.getName());
//        model.addAttribute("homeUrl", home == null ? null : home.getQuery());
//        model.addAttribute("username", ThreadLocalUtils.getUser().getLogin());
//        return "menu/find";
//    }

}