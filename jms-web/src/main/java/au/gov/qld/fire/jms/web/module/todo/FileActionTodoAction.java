package au.gov.qld.fire.jms.web.module.todo;

import java.io.ByteArrayOutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import au.gov.qld.fire.domain.refdata.ContentTypeEnum;
import au.gov.qld.fire.jms.domain.action.FileAction;
import au.gov.qld.fire.jms.domain.action.FileActionTodo;
import au.gov.qld.fire.jms.domain.action.TodoSearchCriteria;
import au.gov.qld.fire.jms.web.module.AbstractDispatchAction;
import au.gov.qld.fire.web.SessionConstants;
import au.gov.qld.fire.web.WebUtils;

/*
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class FileActionTodoAction extends AbstractDispatchAction
{

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.web.module.AbstractDispatchAction#populateRequest(org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected void populateRequest(ActionForm form, HttpServletRequest request) throws Exception
    {
        //set references
        request.setAttribute(SessionConstants.WORK_GROUPS, getEntityService().findWorkGroupsActive());
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.web.module.AbstractDispatchAction#populateForm(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected void populateForm(ActionMapping mapping, ActionForm form, HttpServletRequest request)
        throws Exception
    {
        //form is session bound (id can be null, eg just created)
        FileActionTodoForm myform = (FileActionTodoForm) form;
        FileAction entity = myform.getEntity();
        //get entity id
        Long id = WebUtils.getLongId(request);
        if (id == null && entity != null)
        {
            id = entity.getId();
        }
        //refresh
        if (id != null)
        {
            //entity = getFileService().findFileById(id);
            //myform.setEntity(entity);
        }
    }

    /**
     * 
     * @param request
     * @return
     * @throws Exception
     */
    private List<FileActionTodo> find(HttpServletRequest request) throws Exception
    {
        String fileId = request.getParameter(SessionConstants.FILE_ID);
        String workGroup = request.getParameter(SessionConstants.WORK_GROUP);
        String actionCode = request.getParameter(SessionConstants.ACTION_CODE);

        TodoSearchCriteria criteria = new TodoSearchCriteria();
        criteria.setFileNo(fileId);
        criteria.setWorkGroup(workGroup);
        criteria.setActionCode(actionCode);
        return getActionService().findFileActionToDo(criteria);
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
            List<FileActionTodo> entities = find(request);
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
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward export(ActionMapping mapping, ActionForm form, HttpServletRequest request,
        HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE export().. ");
        final ContentTypeEnum contentType = ContentTypeEnum.APPLICATION_CSV;
        try
        {
            List<FileActionTodo> entities = find(request);
            String contentName = getContentName("mytodo.file.", contentType);
            ByteArrayOutputStream contentStream = new ByteArrayOutputStream();
            //add entities to contentStream
            getActionService().export(entities, contentStream, contentType);
            WebUtils.downloadContent(response, contentStream, contentType.getContentType(), contentName);

            return null;
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            return null;
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
            populateForm(mapping, form, request);
            return mapping.getInputForward();
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            return mapping.getInputForward();
        }
    }

}