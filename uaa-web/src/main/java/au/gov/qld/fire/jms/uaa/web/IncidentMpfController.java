package au.gov.qld.fire.jms.uaa.web;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import au.gov.qld.fire.domain.AbstractResponse;
import au.gov.qld.fire.domain.FailureResponse;
import au.gov.qld.fire.domain.SuccessResponse;
import au.gov.qld.fire.jms.uaa.domain.ImportIncidentMpfRequest;
import au.gov.qld.fire.jms.uaa.domain.IncidentSearchCriteria;
import au.gov.qld.fire.jms.uaa.domain.firu.InjuryViewPK;
import au.gov.qld.fire.jms.uaa.service.FiruImportService;
import au.gov.qld.fire.jms.uaa.service.InjuryService;
import au.gov.qld.fire.service.ServiceException;

/**
 * 
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Controller
public class IncidentMpfController
{

	@Inject private FiruImportService firuImportService;

	@Inject private InjuryService injuryService;

    @RequestMapping(value="/incidentMpf.do", params = "!dispatch", method = RequestMethod.GET)
    public String main(ModelMap model) throws ServiceException
    {
        return "incident/mpf/main";
    }

    @RequestMapping(value="/incidentMpf.do", params = "dispatch=new", method = RequestMethod.GET)
    public String tab0(@ModelAttribute IncidentSearchCriteria criteria,
    	ModelMap model) throws ServiceException
    {
        return "incident/mpf/new";
    }

    @RequestMapping(value="/incidentMpf.do", params = "dispatch=findInjury")
    public String findInjury(@ModelAttribute IncidentSearchCriteria criteria,
    	ModelMap model) throws ServiceException
    {
    	model.addAttribute("entities", firuImportService.findInjuryViewByCriteria(criteria));
        return "incident/mpf/findInjury";
    }

    @RequestMapping(value="/incidentMpf.do", params = "dispatch=importInjury")
    public @ResponseBody AbstractResponse importInjury(@RequestBody ImportIncidentMpfRequest request)
    	throws ServiceException
    {
    	String[] ids = request.getIds();
    	if (ArrayUtils.isEmpty(ids))
    	{
    		return new FailureResponse("Nothing selected.");
    	}
    	//
    	List<InjuryViewPK> importIds = new ArrayList<InjuryViewPK>(ids.length);
    	for (String id : ids) {
    		importIds.add(new InjuryViewPK(id));
    	}
    	firuImportService.importInjury(importIds);
    	AbstractResponse response = new SuccessResponse("Success.");
        return response;
    }

    @RequestMapping(value="/incidentMpf.do", params = "dispatch=feedback", method = RequestMethod.GET)
    public String tab1(@ModelAttribute IncidentSearchCriteria criteria,
    	ModelMap model) throws ServiceException
    {
        return "incident/mpf/feedback";
    }

    @RequestMapping(value="/incidentMpf.do", params = "dispatch=review", method = RequestMethod.GET)
    public String tab2(@ModelAttribute IncidentSearchCriteria criteria,
    	ModelMap model) throws ServiceException
    {
        return "incident/mpf/review";
    }

    @RequestMapping(value="/incidentMpf.do", params = "dispatch=findInjuryView")
    public String findInjuryView(@ModelAttribute IncidentSearchCriteria criteria,
    	ModelMap model) throws ServiceException
    {
    	model.addAttribute("entities", injuryService.findInjuryByCriteria(criteria));
        return "incident/mpf/findInjury";
    }

}