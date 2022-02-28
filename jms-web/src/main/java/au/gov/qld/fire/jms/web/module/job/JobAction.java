package au.gov.qld.fire.jms.web.module.job;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import au.gov.qld.fire.domain.security.User;
import au.gov.qld.fire.jms.domain.job.ActiveJob;
import au.gov.qld.fire.jms.domain.job.Job;
import au.gov.qld.fire.jms.domain.job.JobSearchCriteria;
import au.gov.qld.fire.jms.web.module.AbstractDispatchAction;
import au.gov.qld.fire.web.SessionConstants;
import au.gov.qld.fire.web.WebUtils;

/*
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class JobAction extends AbstractDispatchAction
{

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.web.module.AbstractDispatchAction#populateRequest(org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected void populateRequest(ActionForm form, HttpServletRequest request) throws Exception
    {
        // set references
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
        JobForm myform = (JobForm) form;
        Job entity = myform.getEntity();
        // get entity id
        Long id = WebUtils.getLongId(request);
        if (id == null && entity != null)
        {
            id = entity.getId();
        }
        // refresh
        if (id != null)
        {
            entity = getJobService().findJobById(id);
            myform.setEntity(entity);
        }
        // Job Actions To Do, Job Completed Actions
        List<Job> jobs = entity.getFile().getJobs(); // TODO: why ALL file jobs - check with AB
        //List<Job> jobs = Arrays.asList(entity);
        populateJobActions(jobs, request);
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
            String jobId = request.getParameter(SessionConstants.JOB_ID);
            String jobType = request.getParameter(SessionConstants.JOB_TYPE);
            String workGroup = request.getParameter(SessionConstants.WORK_GROUP);

            JobSearchCriteria criteria = new JobSearchCriteria();
            criteria.setJobNo(jobId);
            criteria.setJobType(jobType);
            criteria.setWorkGroup(workGroup);
            List<ActiveJob> entities = getJobService().findActiveJobs(criteria);

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
     * Present view job form.
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
            JobForm myform = (JobForm) form;
            Long id = WebUtils.getLongId(request);
            Job entity = getJobService().findJobById(id);
            myform.setEntity(entity);
            //populateForm(mapping, form, request);
            return mapping.getInputForward();
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            return mapping.getInputForward();
        }
    }

    /**
     * Present new job form.
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward create(ActionMapping mapping, ActionForm form, HttpServletRequest request,
        HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE create()..");
        try
        {
            populateRequest(form, request);
            JobForm myform = (JobForm) form;
            Job entity = new Job();
            myform.setEntity(entity);
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
     * Update/Insert new job.
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
        JobForm myform = (JobForm) form;
        Job entity = myform.getEntity();
        // ResponsibleUser
        User u = myform.getResponsibleUser();
        u = u.getId() == null ? null : u;
        if (u == null)
        {
            entity.setResponsibleUser(u);
        }
        try
        {
            // validate
            ActionErrors errors = form.validate(mapping, request);
            if (!errors.isEmpty())
            {
                saveErrors(request, response, errors);
                populateRequest(form, request);
                // set form responsibleUser
                User responsibleUser = getUserService().findUserById(myform.getResponsibleUser().getId());
                myform.setResponsibleUser(responsibleUser);
                return findForwardError(mapping);
            }
            // save changes (if any)
            getJobService().saveJob(entity);
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
     * Present edit job form - detail view (TODO: move to own action?).
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

}