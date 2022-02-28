package au.gov.qld.fire.jms.uaa.web;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import au.gov.qld.fire.domain.document.Template;
import au.gov.qld.fire.domain.refdata.TemplateTypeEnum;
import au.gov.qld.fire.jms.uaa.service.EntityService;
import au.gov.qld.fire.jms.uaa.service.FiruImportService;
import au.gov.qld.fire.service.ServiceException;
import au.gov.qld.fire.web.SessionConstants;

/**
 * 
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Controller
public class ReportController
{

	@Inject private FiruImportService firuImportService;

	@Inject private EntityService entityService;

    @RequestMapping(value="/report.do", params = "!dispatch", method = RequestMethod.GET)
    public String main(ModelMap model) throws ServiceException
    {
        return "report/main";
    }

    @RequestMapping(value="/report.do", params = "dispatch=reports", method = RequestMethod.GET)
    public String tab0(ModelMap model) throws ServiceException
    {
        List<Template> reportFop = entityService.findTemplates(TemplateTypeEnum.REPORT_FOP);
        List<Template> reportCrystal = entityService.findTemplates(TemplateTypeEnum.REPORT_CRYSTAL);
        List<Template> entities = new ArrayList<Template>(reportCrystal);
        entities.addAll(reportFop);
        model.addAttribute(SessionConstants.ENTITIES, entities);
        return "report/reports";
    }

}