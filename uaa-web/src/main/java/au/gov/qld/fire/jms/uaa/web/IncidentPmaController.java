package au.gov.qld.fire.jms.uaa.web;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import au.gov.qld.fire.jms.uaa.domain.IncidentSearchCriteria;
import au.gov.qld.fire.jms.uaa.service.FiruImportService;
import au.gov.qld.fire.jms.uaa.service.InjuryService;
import au.gov.qld.fire.service.ServiceException;

/**
 * 
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Controller
public class IncidentPmaController
{

	@Inject private FiruImportService firuImportService;

	@Inject private InjuryService injuryService;

    @RequestMapping(value="/incidentPma.do", params = "!dispatch", method = RequestMethod.GET)
    public String main(ModelMap model) throws ServiceException
    {
        return "incident/pma/main";
    }

    @RequestMapping(value="/incidentPma.do", params = "dispatch=new", method = RequestMethod.GET)
    public String tab0(@ModelAttribute IncidentSearchCriteria criteria,
    	ModelMap model) throws ServiceException
    {
        return "incident/pma/new";
    }

    @RequestMapping(value="/incidentPma.do", params = "dispatch=review", method = RequestMethod.GET)
    public String tab1(@ModelAttribute IncidentSearchCriteria criteria,
    	ModelMap model) throws ServiceException
    {
        return "incident/pma/review";
    }

}