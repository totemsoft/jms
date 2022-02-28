package au.gov.qld.fire.jms.web.module.file;

import java.io.ByteArrayOutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import au.gov.qld.fire.domain.refdata.ContentTypeEnum;
import au.gov.qld.fire.domain.refdata.MailType;
import au.gov.qld.fire.domain.security.User;
import au.gov.qld.fire.jms.domain.entity.Owner;
import au.gov.qld.fire.jms.domain.file.File;
import au.gov.qld.fire.jms.domain.mail.MailRegister;
import au.gov.qld.fire.jms.domain.mail.MailRegisterSearchCriteria;
import au.gov.qld.fire.jms.domain.refdata.ActionType;
import au.gov.qld.fire.jms.domain.refdata.ActionTypeEnum;
import au.gov.qld.fire.jms.web.module.AbstractDispatchAction;
import au.gov.qld.fire.util.BeanUtils;
import au.gov.qld.fire.util.DateUtils;
import au.gov.qld.fire.web.SessionConstants;
import au.gov.qld.fire.web.WebUtils;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class MailRegisterAction extends AbstractDispatchAction
{

    /* (non-Javadoc)
	 * @see au.gov.qld.fire.jms.web.module.AbstractDispatchAction#populateRequest(org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected void populateRequest(ActionForm form, HttpServletRequest request) throws Exception
	{
		request.setAttribute("mailTypes", getEntityService().findMailTypes());
		request.setAttribute(SessionConstants.SALUTATIONS, getEntityService().findSalutations());
        request.setAttribute(SessionConstants.STATES, getEntityService().findStates());
        request.setAttribute(SessionConstants.WORK_GROUPS, getEntityService().findWorkGroupsActive());
        // list of file/job actions
    	MailRegisterForm myform = (MailRegisterForm) form;
    	File file = myform.getFile();
    	if (!myform.getEntity().isMailIn())
    	{
    		ActionType actionType = new ActionType(ActionTypeEnum.LETTER.getId());
    		request.setAttribute("fileActions", getActionService().findFileActionsByFile(file, actionType));
    		request.setAttribute("jobActions", getActionService().findJobActionsByFile(file, actionType));
    	}
	}

	/* (non-Javadoc)
     * @see au.gov.qld.fire.jms.web.module.AbstractDispatchAction#populateForm(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected void populateForm(ActionMapping mapping, ActionForm form, HttpServletRequest request)
        throws Exception
    {
    	MailRegisterForm myform = (MailRegisterForm) form;
        // get entity id
    	Long id = WebUtils.getLongId(request);
    	MailRegister entity = getFileService().findMailRegister(id);
    	entity = entity == null ? new MailRegister() : entity;
    	if (entity.getId() != null)
    	{
        	myform.setEntity(entity);
            request.setAttribute(SessionConstants.ENTITY, myform.getEntity());
    	}
    	else
    	{
    		Long fileId = /*id == null || */myform.getFile() == null ? null : myform.getFile().getFileId();
        	if (fileId != null)
        	{
        		entity.setFile(getFileService().findFileById(fileId));
        	}
        	myform.setEntity(entity);
    	}
    }

    public ActionForward find(ActionMapping mapping, ActionForm form, HttpServletRequest request,
        HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE find().. ");
        try
        {
    		request.setAttribute("mailTypes", getEntityService().findMailTypes());
            List<MailRegister> entities = find(request);
            request.setAttribute(SessionConstants.ENTITIES, entities);
            return mapping.getInputForward();
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            return mapping.getInputForward();
        }
    }

    public ActionForward view(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE view()..");
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
        LOG.debug("INSIDE edit()..");
        Long fileId = WebUtils.getFileId(request);
        request.setAttribute(SessionConstants.FILE_ID, fileId);
        return findForward(mapping, "edit");
    }

    public ActionForward editMailIn(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE editMailIn()..");
        try
        {
        	populateForm(mapping, form, request);
        	populateRequest(form, request);
            return findForward(mapping, "editMailIn");
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
        	populateRequest(form, request);
            return mapping.getInputForward();
        }
    }

    public ActionForward editMailOut(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE editMailOut()..");
        try
        {
        	populateForm(mapping, form, request);
        	populateRequest(form, request);
            return findForward(mapping, "editMailOut");
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
        	populateRequest(form, request);
            return mapping.getInputForward();
        }
    }

    public ActionForward reload(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE reload()..");
        try
        {
        	MailRegisterForm myform = (MailRegisterForm) form;
            boolean mailIn = myform.getEntity().isMailIn();
        	// file
        	File file = myform.getFile();
        	if (file.getId() != null)
        	{
        		file = getFileService().findFileById(file.getId());
        	}
        	else
        	{
            	Long sapCustNo = file.getSapHeader().getSapCustNo();
            	if (sapCustNo != null)
            	{
            		file = getFileService().findFileBySapCustNo(sapCustNo);
            	}
        	}
        	// owner
        	if (file != null)
        	{
        		myform.setFile(file);
                if (!mailIn)
                {
                	Owner owner = myform.getOwner();
                	if (owner != null && owner.getId() != null && owner.getId() < 0L)
                	{
                		; // "other"
                	}
                	else if (owner == null || owner.getId() == null || !file.getOwners().contains(owner))
                	{
                  		myform.setOwner(file.getDefaultOwner()); // no selection
                	}
                }
        	}
            // user
            User user = getUserService().findUserById(myform.getUser().getId());
            myform.getEntity().setUser(user == null ? new User() : user);
        	//
            populateRequest(form, request);
            //
            return findForward(mapping, mailIn ? "editMailIn" : "editMailOut");
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            populateRequest(form, request);
            return mapping.getInputForward();
        }
    }

    public ActionForward detailView(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE detailView()..");
        try
        {
        	populateForm(mapping, form, request);
        	populateRequest(form, request);
        	//
            FileForm fileForm = FileForm.getInstance(request);
        	MailRegisterForm myform = (MailRegisterForm) form;
            File entity = myform.getEntity().getFile();
            fileForm.setEntity(entity);
            //CRM Actions To Do
            //CRM Completed Actions
            populateFileActions(entity, request);
            //Job Actions To Do
            //Job Completed Actions
            populateJobActions(entity.getJobs(), request);
            //
            return findForward(mapping, "detailView");
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
        	populateRequest(form, request);
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
            List<MailRegister> entities = find(request);
            String contentName = getContentName("mailRegister", contentType);
            ByteArrayOutputStream contentStream = new ByteArrayOutputStream();
            // add entities to contentStream
            getFileService().exportMailRegister(entities, contentStream, contentType);
            WebUtils.downloadContent(response, contentStream, contentType.getContentType(), contentName);
            return null;
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            return null;
        }
    }

    public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
        HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE save()..");
        try
        {
            MailRegisterForm myform = (MailRegisterForm) form;
            MailRegister entity = myform.getEntity();
            boolean isNew = entity.getId() == null;
            boolean mailIn = entity.isMailIn();
            ActionErrors errors = form.validate(mapping, request);
            if (!errors.isEmpty())
            {
                saveErrors(request, response, errors);
                populateRequest(form, request);
                //return findForwardError(mapping);
                return findForward(mapping, mailIn ? "editMailIn" : "editMailOut");
            }

            // save changes (if any)
            if (entity.getFile().getId() == null)
            {
            	entity.setFile(myform.getFile());
            }
            entity = getFileService().saveMailRegister(entity, myform.getOwner());

            // RTS is just really a job outcome from mail register.
            // Needs to allocate to the right workgroup / user of course.
            // New job which will have a process (like alarm connection); and can result in alarm transfer etc
            if (isNew && mailIn && entity.isRts())
            {
            	FileForm fileForm = FileForm.getInstance(request);
            	fileForm.setEntity(entity.getFile());
            	return findForward(mapping, "editJob");
            }
            return findForwardSuccess(mapping);
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            populateRequest(form, request);
            return findForwardError(mapping);
        }
    }

    private List<MailRegister> find(HttpServletRequest request) throws Exception
    {
        String date = request.getParameter("date");
        String fcaNo = request.getParameter("fcaNo");
        String sapCustNo = request.getParameter("sapCustNo");
        String mailIn = request.getParameter("mailIn");
        String mailOut = request.getParameter("mailOut");
        String rts = request.getParameter("rts");
        MailType mailType = getEntityService().findMailTypeByName(request.getParameter("mailType"));
        String mailRegisterNo = request.getParameter("mailRegisterNo");
        String contactName = request.getParameter("contactName");
        MailRegisterSearchCriteria criteria = new MailRegisterSearchCriteria();
        criteria.setDate(DateUtils.parse(date, DateUtils.DD_MM_YYYY));
        criteria.setFcaNo(fcaNo);
        criteria.setSapCustNo(sapCustNo);
        criteria.setMailIn(BeanUtils.compare(mailIn, mailOut) == 0 ? null :
        	(StringUtils.isBlank(mailIn) ? Boolean.parseBoolean(mailOut) : Boolean.parseBoolean(mailIn)));
        criteria.setRts(StringUtils.isBlank(rts) || !Boolean.parseBoolean(rts) ? null : Boolean.parseBoolean(rts));
        criteria.setMailTypeId(mailType == null ? null : mailType.getId());
        criteria.setMailRegisterNo(mailRegisterNo);
        criteria.setContactName(contactName);
        return getFileService().findMailRegister(criteria);
    }

}