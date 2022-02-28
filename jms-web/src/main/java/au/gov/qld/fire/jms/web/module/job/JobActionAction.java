package au.gov.qld.fire.jms.web.module.job;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import au.gov.qld.fire.jms.domain.action.JobAction;
import au.gov.qld.fire.jms.domain.job.Job;
import au.gov.qld.fire.jms.domain.refdata.ActionTypeEnum;
import au.gov.qld.fire.jms.web.module.BaseActionAction;
import au.gov.qld.fire.jms.web.module.file.FileActionForm;
import au.gov.qld.fire.web.SessionConstants;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class JobActionAction extends BaseActionAction
{

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.web.module.AbstractDispatchAction#populateRequest(org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected void populateRequest(ActionForm form, HttpServletRequest request) throws Exception
    {
        super.populateRequest(form, request);
        //action type(s)
        List<ActionTypeEnum> actionTypes = new ArrayList<ActionTypeEnum>();
        actionTypes.add(ActionTypeEnum.CALL);
        actionTypes.add(ActionTypeEnum.DIARY);
        actionTypes.add(ActionTypeEnum.EMAIL);
        //actionTypes.add(ActionTypeEnum.FILE_STATUS);
        //actionTypes.add(ActionTypeEnum.JOB);
        //actionTypes.add(ActionTypeEnum.JOB_CLOSE);
        actionTypes.add(ActionTypeEnum.LETTER);
        //actionTypes.add(ActionTypeEnum.MESSAGE);
        actionTypes.add(ActionTypeEnum.RFI);
        actionTypes.add(ActionTypeEnum.SMS);
        request.setAttribute(SessionConstants.ACTION_TYPES, actionTypes);
        //request.setAttribute(SessionConstants.ACTION_TYPES, getEntityService().findActionTypes());
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.web.module.AbstractDispatchAction#populateForm(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected void populateForm(ActionMapping mapping, ActionForm form, HttpServletRequest request)
        throws Exception
    {
        JobForm jobForm = (JobForm) request.getSession().getAttribute(JobForm.BEAN_NAME);
        Job job = jobForm.getEntity();
        job = getJobService().findJobById(job.getId()); // reload job
        
        FileActionForm myform = (FileActionForm) form;
        JobAction jobAction = new JobAction();
        jobAction.setJob(job);
        myform.setJobAction(jobAction);
    }

    /**
     * Present edit form.
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
            populateForm(mapping, form, request);
            FileActionForm myform = (FileActionForm) form;
            myform.setActionType(null);
            populateRequest(form, request);
            return mapping.getInputForward();
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            return mapping.getInputForward();
        }
    }

    /**
     * Present edit Diary form.
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward editDiary(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE editDiary()..");
        try
        {
            populateForm(mapping, form, request);
            FileActionForm myform = (FileActionForm) form;
            myform.setActionType(ActionTypeEnum.DIARY);
            populateRequest(form, request);
            updateCompleted(request, myform.getJobAction());

            return mapping.getInputForward();
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            return mapping.getInputForward();
        }
    }

    /**
     * Present edit Call form.
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward editCall(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE editCall()..");
        try
        {
            populateForm(mapping, form, request);
            FileActionForm myform = (FileActionForm) form;
            myform.setActionType(ActionTypeEnum.CALL);
            populateRequest(form, request);
            updateCompleted(request, myform.getJobAction());

            return mapping.getInputForward();
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            return mapping.getInputForward();
        }
    }

    /**
     * Present edit Letter form.
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward editLetter(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE editLetter()..");
        try
        {
            populateForm(mapping, form, request);
            FileActionForm myform = (FileActionForm) form;
            myform.setActionType(ActionTypeEnum.LETTER);
            populateRequest(form, request);
            updateCompleted(request, myform.getJobAction());

            return mapping.getInputForward();
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            return mapping.getInputForward();
        }
    }

    /**
     * Present edit Email form.
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward editEmail(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE editEmail()..");
        try
        {
            populateForm(mapping, form, request);
            FileActionForm myform = (FileActionForm) form;
            myform.setActionType(ActionTypeEnum.EMAIL);
            populateRequest(form, request);
            updateCompleted(request, myform.getJobAction());

            return mapping.getInputForward();
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            return mapping.getInputForward();
        }
    }

    /**
     * Present edit SMS form.
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward editSMS(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE editSMS()..");
        try
        {
            populateForm(mapping, form, request);
            FileActionForm myform = (FileActionForm) form;
            myform.setActionType(ActionTypeEnum.SMS);
            populateRequest(form, request);
            updateCompleted(request, myform.getJobAction());

            return mapping.getInputForward();
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            return mapping.getInputForward();
        }
    }

    /**
     * Present edit RFI form.
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward editRFI(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE editRFI()..");
        try
        {
            populateForm(mapping, form, request);
            FileActionForm myform = (FileActionForm) form;
            myform.setActionType(ActionTypeEnum.RFI);
            populateRequest(form, request);
            updateCompleted(request, myform.getJobAction());

            return mapping.getInputForward();
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            return mapping.getInputForward();
        }
    }

    /**
     * Present edit Job form.
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward closeJob(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE closeJob()..");
        try
        {
            populateForm(mapping, form, request);
            FileActionForm myform = (FileActionForm) form;
            myform.setActionType(ActionTypeEnum.JOB_CLOSE);
            populateRequest(form, request);

            return mapping.getInputForward();
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            return mapping.getInputForward();
        }
    }

}