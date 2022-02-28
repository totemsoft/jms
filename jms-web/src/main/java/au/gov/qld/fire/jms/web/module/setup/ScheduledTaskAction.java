package au.gov.qld.fire.jms.web.module.setup;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import au.gov.qld.fire.domain.Request;
import au.gov.qld.fire.domain.task.ScheduledTask;
import au.gov.qld.fire.domain.task.ScheduledTaskEnum;
import au.gov.qld.fire.domain.task.ScheduledTaskHistory;
import au.gov.qld.fire.domain.task.ScheduledTaskSearchCriteria;
import au.gov.qld.fire.domain.task.TaskImportRequest;
import au.gov.qld.fire.jms.web.module.AbstractDispatchAction;
import au.gov.qld.fire.util.ThreadLocalUtils;
import au.gov.qld.fire.validation.ValidationException.ValidationMessage;
import au.gov.qld.fire.web.SessionConstants;
import au.gov.qld.fire.web.WebUtils;

/*
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class ScheduledTaskAction extends AbstractDispatchAction
{

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.web.module.AbstractDispatchAction#populateRequest(org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected void populateRequest(ActionForm form, HttpServletRequest request) throws Exception
    {
        // set references
    	request.setAttribute("scheduledTasks", ArrayUtils.remove(ScheduledTaskEnum.values(), 0));
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.web.module.AbstractDispatchAction#populateForm(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected void populateForm(ActionMapping mapping, ActionForm form, HttpServletRequest request) throws Exception
    {
        // get entity id
        Long id = WebUtils.getLongId(request);
        //
        ScheduledTaskForm myform = (ScheduledTaskForm) form;
        ScheduledTask entity;
        if (id == null)
        {
            entity = new ScheduledTask();
        }
        else
        {
            entity = getTaskService().findScheduledTaskById(id);
        }
        myform.setEntity(entity);
    }

    public ActionForward find(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        try
        {
        	ScheduledTaskSearchCriteria criteria = new ScheduledTaskSearchCriteria();
        	String task = request.getParameter("task");
            criteria.setTask(ScheduledTaskEnum.findByName(task));
            criteria.setActive(null);
            List<ScheduledTask> entities = getTaskService().findByCriteria(criteria);
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

    public ActionForward view(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        try
        {
            populateForm(mapping, form, request);
            return findForward(mapping, VIEW);
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            return mapping.getInputForward();
        }
    }

    public ActionForward edit(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        try
        {
            populateRequest(form, request);
            populateForm(mapping, form, request);
            return findForward(mapping, EDIT);
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            populateRequest(form, request);
            return mapping.getInputForward();
        }
    }

    public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
        HttpServletResponse response) throws Exception
    {
        try
        {
            ActionErrors errors = form.validate(mapping, request);
            if (!errors.isEmpty())
            {
                saveErrors(request, response, errors);
                return edit(mapping, form, request, response);
            }

            ScheduledTaskForm myform = (ScheduledTaskForm) form;
            // save changes (if any)
            getTaskService().saveScheduledTask(myform.getEntity());

            return findForwardSuccess(mapping);
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            populateRequest(form, request);
            return findForwardError(mapping);
        }
    }

    public ActionForward importData(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
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
        try
        {
            // save changes (if any)
            ScheduledTaskForm myform = (ScheduledTaskForm) form;
            Long scheduledTaskId = myform.getEntity().getScheduledTaskId();
            ScheduledTask task = getTaskService().findScheduledTaskById(scheduledTaskId);
            TaskImportRequest taskImportRequest = new TaskImportRequest();
            taskImportRequest.setTask(ScheduledTaskEnum.findByName(task.getName()));
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

    public ActionForward runTask(ActionMapping mapping, ActionForm form, HttpServletRequest request,
        HttpServletResponse response) throws Exception
    {
        try
        {
            ScheduledTaskForm myform = (ScheduledTaskForm) form;
            Long scheduledTaskId = myform.getEntity().getScheduledTaskId();
            ScheduledTask scheduledTask = getTaskService().findScheduledTaskById(scheduledTaskId);
			ScheduledTaskHistory task = new ScheduledTaskHistory(scheduledTask);
			task.setCreatedDate(ThreadLocalUtils.getDate());
			getTaskService().runTaskWithStatus(task);
            return null;
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            return findForward(mapping, IMPORT_DATA);
        }
    }

}