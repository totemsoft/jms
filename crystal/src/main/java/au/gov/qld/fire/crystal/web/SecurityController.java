package au.gov.qld.fire.crystal.web;

import javax.security.auth.Subject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import au.gov.qld.fire.security.UserCredential;
import au.gov.qld.fire.web.WebUtils;
import au.gov.qld.fire.web.util.LoginUtils;

/**
 * 
 * @author vchibaev (Valeri SHIBAEV)
 */
@Controller
@RequestMapping("/security.do")
public class SecurityController {

    @RequestMapping(value = "/security.do", params = "dispatch=login", method = RequestMethod.GET)
    public String login(ModelMap model) throws Exception
    {
        return "login";
    }

    @RequestMapping(value = "/security.do", params = "dispatch=login", method = RequestMethod.POST)
    public String loginPost(HttpServletRequest request,
       	@RequestParam(value = "login") String login,
    	@RequestParam(value = "userId", required = false) Long userId,
    	ModelMap model) throws Exception
    {
        // TODO: authenticate
        Subject subject = new Subject();
        UserCredential cred = new UserCredential(login, userId);
        subject.getPublicCredentials().add(cred);
        WebUtils.setSubject(request.getSession(), subject);
        //
        return "redirect:reportViewer.do";
    }

    @RequestMapping(value = "/security.do", params = "dispatch=logout")
    public String logout(HttpServletRequest request) throws Exception
    {
        // logout (JAAS)
        LoginUtils.logout(request);
        //
        return "login";
    }

}