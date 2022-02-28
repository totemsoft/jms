package au.gov.qld.fire.jms.web.module.setup;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import au.gov.qld.fire.jms.domain.action.ActionOutcome;
import au.gov.qld.fire.jms.domain.action.ActionWorkflow;
import au.gov.qld.fire.jms.domain.refdata.ActionCode;
import au.gov.qld.fire.jms.service.EntityService;
import au.gov.qld.fire.jms.web.module.AbstractDispatchAction;
import au.gov.qld.fire.web.SessionConstants;
import au.gov.qld.fire.web.WebUtils;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class ActionWorkflowAction extends AbstractDispatchAction
{

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.web.module.AbstractDispatchAction#populateRequest(org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected void populateRequest(ActionForm form, HttpServletRequest request) throws Exception
    {
        // set references
        //ActionWorkflowForm myform = (ActionWorkflowForm) form;
    	final EntityService entityService = getEntityService();
        request.setAttribute(SessionConstants.JOB_TYPES, entityService.findJobTypes());
        //Long jobTypeId = myform.getJobTypeId();
        request.setAttribute(SessionConstants.ACTION_CODES, /*jobTypeId != null ? entityService.findActionCodeByJobType(jobTypeId) : */entityService.findActionCodesActive());
        request.setAttribute(SessionConstants.ACTION_OUTCOMES, entityService.findActionOutcomes());
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.web.module.AbstractDispatchAction#populateForm(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected void populateForm(ActionMapping mapping, ActionForm form, HttpServletRequest request) throws Exception
    {
    	final EntityService entityService = getEntityService();
        ActionWorkflowForm myform = (ActionWorkflowForm) form;
        // get entity id
        Long id = WebUtils.getLongId(request);
        // get actionCode/outcome (from table row selection)
        Long actionCodeId = WebUtils.getLongId(request, SessionConstants.ACTION_CODE_ID);
        ActionCode actionCode = entityService.findActionCodeById(actionCodeId);
        Long outcomeId = WebUtils.getLongId(request, SessionConstants.OUTCOME_ID);
        ActionOutcome actionOutcome = entityService.findActionOutcomeById(outcomeId);
        // set form data
        if (id == null) {
            ActionWorkflow entity = new ActionWorkflow();
            entity.setActionCode(actionCode);
            entity.setActionOutcome(actionOutcome);
            myform.setEntity(entity);
            myform.setActionWorkflows(null);
        }
        else {
            ActionWorkflow entity = entityService.findActionWorkflowById(id);
            myform.setEntity(entity);
            List<ActionWorkflow> actionWorkflows = entityService.findActionWorkflowByActionCodeOutcome(actionCode, actionOutcome);
            myform.setActionWorkflows(actionWorkflows);
        }
    }

    /**
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward find(ActionMapping mapping, ActionForm form, HttpServletRequest request,
        HttpServletResponse response) throws Exception
    {
        try
        {
        	final EntityService entityService = getEntityService();
            ActionWorkflowForm myform = (ActionWorkflowForm) form;
            final Long jobTypeId = myform.getJobTypeId();
            Long actionCodeId = myform.getActionCodeId();
            boolean active = myform.isActive();
            // save to session (will be used in view/edit)
            request.setAttribute(SessionConstants.JOB_TYPE_ID, jobTypeId);
            request.setAttribute(SessionConstants.JOB_TYPE, myform.getJobType());
            request.setAttribute(SessionConstants.ACTION_CODE_ID, actionCodeId);
            request.setAttribute(SessionConstants.ACTION_CODE, myform.getActionCode());
            request.setAttribute(SessionConstants.ACTIVE, active);

            // jobTypeId or actionCode is required for this UI
            if (jobTypeId == null && actionCodeId == null) {
                populateRequest(form, request);
                return mapping.getInputForward();
            }
            //
            request.setAttribute(SessionConstants.ENTITIES, entityService.findActionWorkflows(jobTypeId, actionCodeId, active));

            populateRequest(form, request);
            return mapping.getInputForward();
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            populateRequest(form, request);
            return mapping.getInputForward();
        }
    }

    /**
     * Present view ActionCode form.
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request,
        HttpServletResponse response) throws Exception
    {
        try
        {
            populateForm(mapping, form, request);
            return mapping.getInputForward();
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            return mapping.getInputForward();
        }
    }

    /**
     * Present edit user form.
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
        HttpServletResponse response) throws Exception
    {
        try
        {
            populateForm(mapping, form, request);
            populateRequest(form, request);
            return mapping.getInputForward();
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            populateRequest(form, request);
            return mapping.getInputForward();
        }
    }

    /**
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward addActionWorkflow(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        try
        {
            // set form data
            ActionWorkflowForm myform = (ActionWorkflowForm) form;
            myform.add(new ActionWorkflow());
            populateRequest(form, request);
            return mapping.getInputForward();
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            populateRequest(form, request);
            return mapping.getInputForward();
        }
    }

    /**
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward removeActionWorkflow(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        try
        {
            //get index to remove
            int index = WebUtils.getIndex(request);
            //set form data
            ActionWorkflowForm myform = (ActionWorkflowForm) form;
            ActionWorkflow actionWorkflow = myform.getActionWorkflows().get(index);
            myform.remove(actionWorkflow);
            //
            populateRequest(form, request);
            return mapping.getInputForward();
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            populateRequest(form, request);
            return mapping.getInputForward();
        }
    }

    /**
     * Update/Insert new entity.
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
        HttpServletResponse response) throws Exception
    {
        try
        {
            ActionErrors errors = form.validate(mapping, request);
            if (!errors.isEmpty())
            {
                saveErrors(request, response, errors);
                populateRequest(form, request);
                return findForwardError(mapping);
            }

            ActionWorkflowForm myform = (ActionWorkflowForm) form;
            // save changes
        	final EntityService entityService = getEntityService();
            entityService.saveActionWorkflow(myform.getActionWorkflows());
            entityService.deleteActionWorkflow(myform.getActionWorkflowsDelete());

            return findForwardSuccess(mapping);
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            populateRequest(form, request);
            return findForwardError(mapping);
        }
    }

}