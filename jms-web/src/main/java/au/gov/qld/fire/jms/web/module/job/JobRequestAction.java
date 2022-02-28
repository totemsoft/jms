package au.gov.qld.fire.jms.web.module.job;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import au.gov.qld.fire.jms.domain.job.ActiveJobRequest;
import au.gov.qld.fire.jms.domain.job.JobRequest;
import au.gov.qld.fire.jms.domain.job.JobRequestSearchCriteria;
import au.gov.qld.fire.jms.web.module.AbstractDispatchAction;
import au.gov.qld.fire.web.SessionConstants;
import au.gov.qld.fire.web.WebUtils;

/*
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class JobRequestAction extends AbstractDispatchAction
{

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.web.module.AbstractDispatchAction#populateRequest(org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected void populateRequest(ActionForm form, HttpServletRequest request) throws Exception
    {
        //set references
        request.setAttribute(SessionConstants.JOB_TYPES, getEntityService().findJobTypes());
        request.setAttribute(SessionConstants.WORK_GROUPS, getEntityService().findWorkGroupsActive());
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.web.module.AbstractDispatchAction#populateForm(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected void populateForm(ActionMapping mapping, ActionForm form, HttpServletRequest request)
        throws Exception
    {
        JobRequestForm myform = (JobRequestForm) form;
        //get entity id
        Long id = WebUtils.getLongId(request);
        if (id == null)
        {
            myform.reset(mapping, request);
        }
        else
        {
            JobRequest entity = getJobService().findJobRequestById(id);
            myform.setEntity(entity);
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
        LOG.debug("INSIDE find().. ");
        try
        {
            String jobRequestId = request.getParameter(SessionConstants.JOB_REQUEST_ID);
            String jobType = request.getParameter(SessionConstants.JOB_TYPE);
            String workGroup = request.getParameter(SessionConstants.WORK_GROUP);

            JobRequestSearchCriteria criteria = new JobRequestSearchCriteria();
            criteria.setJobRequestNo(jobRequestId);
            criteria.setJobType(jobType);
            criteria.setWorkGroup(workGroup);
            List<ActiveJobRequest> entities = getJobService().findActiveJobRequests(criteria);

            request.setAttribute(SessionConstants.ENTITIES, entities);

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
     * Present view entity form.
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward view(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE view()..");
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
     * Present edit entity form.
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
        LOG.debug("INSIDE edit()..");
        try
        {
            populateRequest(form, request);
            populateForm(mapping, form, request);
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
        LOG.debug("INSIDE save()..");
        try
        {
            ActionErrors errors = form.validate(mapping, request);
            if (!errors.isEmpty())
            {
                saveErrors(request, response, errors);
                populateRequest(form, request);
                return findForwardError(mapping);
            }

            JobRequestForm myform = (JobRequestForm) form;
            JobRequest entity = myform.getEntity();
            //save changes (if any)
            getJobService().saveJobRequest(entity);

            return findForwardSuccess(mapping);
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            populateRequest(form, request);
            return findForwardError(mapping);
        }
    }

    /**
     * Present accept view entity form.
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward accept(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE accept()..");
        try
        {
            JobRequestForm myform = (JobRequestForm) form;
            JobRequest entity = myform.getEntity();
            //save changes (if any)
            getJobService().acceptJobRequest(entity);

            return findForwardSuccess(mapping);
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            //populateRequest(form, request);
            return mapping.getInputForward();
        }
    }

    /**
     * Present reject edit entity form.
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward reject(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE reject()..");
        try
        {
            ActionErrors errors = form.validate(mapping, request);
            if (!errors.isEmpty())
            {
                saveErrors(request, response, errors);
                populateRequest(form, request);
                return findForwardError(mapping);
            }

            JobRequestForm myform = (JobRequestForm) form;
            JobRequest entity = myform.getEntity();
            //save changes (if any)
            getJobService().rejectJobRequest(entity);

            return findForwardSuccess(mapping);
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            //populateRequest(form, request);
            return findForwardError(mapping);
        }
    }

}