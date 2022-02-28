package au.gov.qld.fire.jms.web.module.file;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import au.gov.qld.fire.domain.Request;
import au.gov.qld.fire.domain.refdata.ContentTypeEnum;
import au.gov.qld.fire.domain.task.ScheduledTaskEnum;
import au.gov.qld.fire.domain.task.TaskImportRequest;
import au.gov.qld.fire.jms.domain.isolation.IsolationHistory;
import au.gov.qld.fire.jms.domain.isolation.IsolationHistorySearchCriteria;
import au.gov.qld.fire.jms.web.module.AbstractDispatchAction;
import au.gov.qld.fire.util.DateUtils;
import au.gov.qld.fire.validation.ValidationException.ValidationMessage;
import au.gov.qld.fire.web.SessionConstants;
import au.gov.qld.fire.web.WebUtils;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class IsolationHistoryAction extends AbstractDispatchAction
{

    /* (non-Javadoc)
	 * @see au.gov.qld.fire.jms.web.module.AbstractDispatchAction#populateRequest(org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected void populateRequest(ActionForm form, HttpServletRequest request)	throws Exception
	{
        request.setAttribute(SessionConstants.REGIONS, getEntityService().findRegionsActive());
	}

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.web.module.AbstractDispatchAction#populateForm(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected void populateForm(ActionMapping mapping, ActionForm form, HttpServletRequest request) throws Exception
    {
    	IsolationHistoryForm myform = (IsolationHistoryForm) form;
        // get entity id
    	Long id = WebUtils.getLongId(request);
    	IsolationHistory entity = getFileService().findIsolationHistory(id);
    	entity = entity == null ? new IsolationHistory() : entity;
    	myform.setEntity(entity);
    	if (entity.getId() != null)
    	{
            request.setAttribute(SessionConstants.ENTITY, myform.getEntity());
    	}
    }

    public ActionForward find(ActionMapping mapping, ActionForm form, HttpServletRequest request,
        HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE find().. ");
        try
        {
            List<IsolationHistory> entities = find(request);
            request.setAttribute(SessionConstants.ENTITIES, entities);
            String completed = request.getParameter(SessionConstants.COMPLETED);
            request.setAttribute(SessionConstants.COMPLETED, Boolean.valueOf(completed));

        	populateRequest(form, request);
            return mapping.getInputForward();
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            return mapping.getInputForward();
        }
    }

    public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE edit().. ");
        try
        {
        	populateForm(mapping, form, request);
        	//populateRequest(form, request);
            return findForward(mapping, EDIT);
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            return findForward(mapping, EDIT);
        }
    }

    public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
        HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE save()..");
        try
        {
            // save changes (if any)
        	IsolationHistoryForm myform = (IsolationHistoryForm) form;
            ActionErrors errors = form.validate(mapping, request);
            if (!errors.isEmpty())
            {
                saveErrors(request, response, errors);
                //populateRequest(form, request);
                return findForward(mapping, EDIT);
            }
        	IsolationHistory entity = myform.getEntity();
            getFileService().saveIsolationHistory(entity);
            return findForwardSuccess(mapping);
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            return findForward(mapping, IMPORT_DATA);
        }
    }

    public ActionForward export(ActionMapping mapping, ActionForm form, HttpServletRequest request,
        HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE export()..");
        final ContentTypeEnum contentType = ContentTypeEnum.APPLICATION_CSV;
        try
        {
            List<IsolationHistory> entities = find(request);
            String contentName = getContentName("isolation.", contentType);
            ByteArrayOutputStream contentStream = new ByteArrayOutputStream();
            // add entities to contentStream
            getFileService().exportIsolationHistory(entities, contentStream, contentType);
            WebUtils.downloadContent(response, contentStream, contentType.getContentType(), contentName);

            return null;
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            return null;
        }
    }

    private List<IsolationHistory> find(HttpServletRequest request) throws Exception
    {
        IsolationHistorySearchCriteria criteria = new IsolationHistorySearchCriteria();
        String fcaId = request.getParameter(SessionConstants.FCA_ID);
        criteria.setFcaId(fcaId);
        String isolationDate = request.getParameter("isolationDate");
        criteria.setIsolationDate(DateUtils.parse(isolationDate, DateUtils.DD_MM_YYYY_HH_mm));
        String deIsolationDate = request.getParameter("deIsolationDate");
        criteria.setDeIsolationDate(DateUtils.parse(deIsolationDate, DateUtils.DD_MM_YYYY_HH_mm));
        Long regionId = WebUtils.getLongId(request, SessionConstants.REGION_ID);
        criteria.setRegionId(regionId);
        String area = request.getParameter(SessionConstants.AREA);
        criteria.setArea(area);
        String station = request.getParameter(SessionConstants.STATION);
        criteria.setStation(station);
        return getFileService().findIsolationHistory(criteria);
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
        	IsolationHistoryForm myform = (IsolationHistoryForm) form;
            TaskImportRequest taskImportRequest = new TaskImportRequest();
            taskImportRequest.setTask(ScheduledTaskEnum.isolationImporter);
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