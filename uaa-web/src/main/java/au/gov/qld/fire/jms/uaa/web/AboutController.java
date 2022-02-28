package au.gov.qld.fire.jms.uaa.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import au.gov.qld.fire.service.ServiceException;

/**
 * 
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Controller
public class AboutController
{

    @RequestMapping(value="/about.do", params = "!dispatch", method = RequestMethod.GET)
    public String main() throws ServiceException
    {
        //return "redirect:incident.do?dispatch=mpf";
        return "about/main";
    }

    @RequestMapping(value="/about.do", params = "dispatch=about", method = RequestMethod.GET)
    public String tab0() throws ServiceException
    {
        return "about/about";
    }

}