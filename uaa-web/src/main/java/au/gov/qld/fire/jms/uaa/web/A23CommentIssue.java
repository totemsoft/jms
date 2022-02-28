package au.gov.qld.fire.jms.uaa.web;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import au.gov.qld.fire.jms.uaa.service.FiruImportService;
import au.gov.qld.fire.jms.uaa.service.InjuryService;
import au.gov.qld.fire.service.ServiceException;

/**
 * 
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Controller
public class A23CommentIssue
{

	@Inject private FiruImportService firuImportService;

	@Inject private InjuryService injuryService;

    @RequestMapping(value="/a23CommentIssue.do", params = "!dispatch", method = RequestMethod.GET)
    public String main(ModelMap model) throws ServiceException
    {
        return "a23CommentIssue/main";
    }

    @RequestMapping(value="/a23CommentIssue.do", params = "dispatch=a23CommentIssue", method = RequestMethod.GET)
    public String tab0(ModelMap model) throws ServiceException
    {
        return "a23CommentIssue/a23CommentIssue";
    }

}