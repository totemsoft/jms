package au.gov.qld.fire.jms.web.module.job;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import au.gov.qld.fire.jms.domain.job.Job;
import au.gov.qld.fire.jms.domain.job.JobDoc;
import au.gov.qld.fire.jms.web.module.AbstractDispatchAction;
import au.gov.qld.fire.web.SessionConstants;
import au.gov.qld.fire.web.WebUtils;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class JobDocAction extends AbstractDispatchAction
{

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.web.module.AbstractDispatchAction#populateRequest(org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected void populateRequest(ActionForm form, HttpServletRequest request) throws Exception
    {
        //set references
        request.setAttribute(SessionConstants.DOC_TYPES, getEntityService().findDocTypes());
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
            //populateForm(mapping, form, request);
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
    public ActionForward addJobDoc(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE addJobDoc()..");
        try
        {
            //set form data
            JobForm myform = (JobForm) form;
            Job job = myform.getEntity();
            job.add(new JobDoc());
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
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward removeJobDoc(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE removeJobDoc()..");
        try
        {
            //get index to remove
            int index = WebUtils.getIndex(request);
            //set form data
            JobForm myform = (JobForm) form;
            Job job = myform.getEntity();
            JobDoc jobDoc = job.getJobDocs().get(index);
            job.getJobDocs().remove(jobDoc);
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
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward moveJobDoc(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE moveJobDoc()..");
        try
        {
            //get index to remove
            int index = WebUtils.getIndex(request);
            //set form data
            JobForm jobForm = (JobForm) request.getSession().getAttribute(JobForm.BEAN_NAME);
            Job job = jobForm.getEntity();
            JobDoc jobDoc = job.getJobDocs().get(index);
            JobDocForm myform = (JobDocForm) form;
            myform.setEntity(jobDoc);

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
     * Save changes (if any).
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

            JobForm myform = (JobForm) form;
            Job job = myform.getEntity();
            //save JobDoc(s) changes
            getJobService().saveJobDoc(job.getJobId(), job.getJobDocs());

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
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward moveJobDocSave(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE moveJobDocSave()..");
        try
        {
            ActionErrors errors = form.validate(mapping, request);
            if (!errors.isEmpty())
            {
                saveErrors(request, response, errors);
                populateRequest(form, request);
                return findForwardError(mapping);
            }

            JobDocForm myform = (JobDocForm) form;
            JobDoc jobDoc = myform.getEntity();
            //save JobDoc move changes
            getJobService().moveJobDoc(jobDoc.getId(), myform.getMoveJobId());

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