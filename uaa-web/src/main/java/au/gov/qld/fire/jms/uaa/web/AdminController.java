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
public class AdminController
{

	@Inject private FiruImportService firuImportService;

	@Inject private InjuryService injuryService;

    @RequestMapping(value="/admin.do", params = "!dispatch", method = RequestMethod.GET)
    public String main(ModelMap model) throws ServiceException
    {
        return "admin/main";
    }

    @RequestMapping(value="/admin.do", params = "dispatch=import", method = RequestMethod.GET)
    public String tab0(ModelMap model) throws ServiceException
    {
        return "admin/import";
    }

    @RequestMapping(value="/admin.do", params = "dispatch=undoExport", method = RequestMethod.GET)
    public String tab1(ModelMap model) throws ServiceException
    {
        return "admin/undoExport";
    }

    @RequestMapping(value="/admin.do", params = "dispatch=chargeRates", method = RequestMethod.GET)
    public String tab2(ModelMap model) throws ServiceException
    {
        return "admin/chargeRates";
    }

    @RequestMapping(value="/admin.do", params = "dispatch=leniency", method = RequestMethod.GET)
    public String tab3(ModelMap model) throws ServiceException
    {
        return "admin/leniency";
    }

    @RequestMapping(value="/admin.do", params = "dispatch=commonwealth", method = RequestMethod.GET)
    public String tab4(ModelMap model) throws ServiceException
    {
        return "admin/commonwealth";
    }

    @RequestMapping(value="/admin.do", params = "dispatch=customers", method = RequestMethod.GET)
    public String tab5(ModelMap model) throws ServiceException
    {
        return "admin/customers";
    }

    @RequestMapping(value="/admin.do", params = "dispatch=audit", method = RequestMethod.GET)
    public String tab6(ModelMap model) throws ServiceException
    {
        return "admin/audit";
    }

}