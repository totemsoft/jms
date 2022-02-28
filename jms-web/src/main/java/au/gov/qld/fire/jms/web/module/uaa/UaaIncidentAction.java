package au.gov.qld.fire.jms.web.module.uaa;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import au.gov.qld.fire.domain.Request;
import au.gov.qld.fire.domain.refdata.ContentTypeEnum;
import au.gov.qld.fire.domain.task.ScheduledTaskEnum;
import au.gov.qld.fire.domain.task.TaskImportRequest;
import au.gov.qld.fire.jms.uaa.domain.Injury;
import au.gov.qld.fire.jms.web.module.AbstractDispatchAction;
import au.gov.qld.fire.validation.ValidationException.ValidationMessage;
import au.gov.qld.fire.web.SessionConstants;
import au.gov.qld.fire.web.WebUtils;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class UaaIncidentAction extends AbstractDispatchAction
{

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.web.module.AbstractDispatchAction#populateForm(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected void populateForm(ActionMapping mapping, ActionForm form, HttpServletRequest request)
        throws Exception
    {
        //UaaIncidentForm myform = (UaaIncidentForm) form;
    }

    public ActionForward find(ActionMapping mapping, ActionForm form, HttpServletRequest request,
        HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE find().. ");
        try
        {
            List<Injury> entities = find(form, request);
            request.setAttribute(SessionConstants.ENTITIES, entities);
            return mapping.getInputForward();
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            return mapping.getInputForward();
        }
    }

    public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request,
        HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE view().. ");
        try
        {
            //UaaIncidentForm myform = (UaaIncidentForm) form;
            // get entity id
        	Long id = WebUtils.getLongId(request);
            request.setAttribute(SessionConstants.ENTITY, getInjuryService().findInjuryById(id));
            return findForward(mapping, VIEW);
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            return mapping.getInputForward();
        }
    }

    public ActionForward export(ActionMapping mapping, ActionForm form, HttpServletRequest request,
        HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE export()..");
        final ContentTypeEnum contentType = ContentTypeEnum.APPLICATION_CSV;
        try
        {
            List<Injury> entities = find(form, request);
            String contentName = getContentName("uaa-incident.", contentType);
            ByteArrayOutputStream contentStream = new ByteArrayOutputStream();
            // add entities to contentStream
            getInjuryService().exportInjury(entities, contentStream, contentType);
            WebUtils.downloadContent(response, contentStream, contentType.getContentType(), contentName);

            return null;
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            return null;
        }
    }

    private List<Injury> find(ActionForm form, HttpServletRequest request) throws Exception
    {
        UaaIncidentForm myform = (UaaIncidentForm) form;
        return getInjuryService().findInjuryByCriteria(myform.getCriteria());
    }

    public ActionForward importData(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE importData()..");
        try
        {
            return findForward(mapping, IMPORT_DATA);
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            return mapping.getInputForward();
        }
    }

    public ActionForward saveImportData(ActionMapping mapping, ActionForm form, HttpServletRequest request,
        HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE saveImportData()..");
        try
        {
            // save changes (if any)
        	UaaIncidentForm myform = (UaaIncidentForm) form;
            TaskImportRequest taskImportRequest = new TaskImportRequest();
            taskImportRequest.setTask(ScheduledTaskEnum.uaaIncidentImporter);
            taskImportRequest.setContentName(myform.getContentName());
            taskImportRequest.setContent(myform.getContent());
            taskImportRequest.setContentType(myform.getContentType());
            // check for validation errors (if any)
            Map<? extends Request, List<ValidationMessage>> errors = getTaskService().importData(taskImportRequest);
            if (!MapUtils.isEmpty(errors))
            {
            	LOG.warn(errors);
            	request.setAttribute("errors", errors);
                return findForward(mapping, IMPORT_DATA);
            }
            return null;
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            return findForward(mapping, IMPORT_DATA);
        }
    }

}