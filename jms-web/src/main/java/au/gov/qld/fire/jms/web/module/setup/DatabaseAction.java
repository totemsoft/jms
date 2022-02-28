package au.gov.qld.fire.jms.web.module.setup;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import au.gov.qld.fire.domain.StringPair;
import au.gov.qld.fire.jms.service.EntityService;
import au.gov.qld.fire.jms.web.module.AbstractDispatchAction;
import au.gov.qld.fire.web.SessionConstants;

/*
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class DatabaseAction extends AbstractDispatchAction
{

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.web.module.AbstractDispatchAction#populateRequest(org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected void populateRequest(ActionForm form, HttpServletRequest request) throws Exception
    {
        // set references
    }

    public ActionForward find(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        try
        {
        	final EntityService entityService = getEntityService();
        	List<StringPair> entities = entityService.findSyncProcesses();
        	entities.add(0, new StringPair("", "* All *"));
            request.setAttribute(SessionConstants.ENTITIES, entities);
            return mapping.getInputForward();
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            return mapping.getInputForward();
        }
    }

    public ActionForward runProcess(ActionMapping mapping, ActionForm form, HttpServletRequest request,
        HttpServletResponse response) throws Exception
    {
        try
        {
        	DatabaseForm myform = (DatabaseForm) form;
        	String process = myform.getName();
        	if (StringUtils.isBlank(process)) {
            	getEntityService().syncProcess();
        	} else {
            	getEntityService().syncProcess(process);
        	}
            return null;
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            populateRequest(form, request);
            return findForwardError(mapping);
        }
    }

}