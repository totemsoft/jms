package au.gov.qld.fire.jms.web.module;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import au.gov.qld.fire.domain.document.Document;
import au.gov.qld.fire.domain.document.Template;
import au.gov.qld.fire.domain.entity.Contact;
import au.gov.qld.fire.domain.refdata.ContentTypeEnum;
import au.gov.qld.fire.domain.security.User;
import au.gov.qld.fire.jms.domain.action.ActionOutcome;
import au.gov.qld.fire.jms.domain.action.ActionWorkflow;
import au.gov.qld.fire.jms.domain.action.BaseAction;
import au.gov.qld.fire.jms.domain.action.FileAction;
import au.gov.qld.fire.jms.domain.action.FileActionRequest;
import au.gov.qld.fire.jms.domain.action.JobAction;
import au.gov.qld.fire.jms.domain.action.JobActionRequest;
import au.gov.qld.fire.jms.domain.entity.Owner;
import au.gov.qld.fire.jms.domain.file.File;
import au.gov.qld.fire.jms.domain.refdata.ActionCode;
import au.gov.qld.fire.jms.domain.refdata.ActionType;
import au.gov.qld.fire.jms.domain.refdata.ActionTypeEnum;
import au.gov.qld.fire.jms.domain.refdata.FileStatus;
import au.gov.qld.fire.jms.domain.refdata.JobType;
import au.gov.qld.fire.jms.web.module.file.FileActionForm;
import au.gov.qld.fire.util.DateUtils;
import au.gov.qld.fire.util.ThreadLocalUtils;
import au.gov.qld.fire.web.SessionConstants;
import au.gov.qld.fire.web.WebUtils;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public abstract class BaseActionAction extends AbstractDispatchAction
{

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.web.module.AbstractDispatchAction#populateRequest(org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected void populateRequest(ActionForm form, HttpServletRequest request) throws Exception
    {
        //super.populateRequest(form, request);
        //
        request.setAttribute(SessionConstants.ACTION_CODES, getEntityService().findActionCodesActive());
        request.setAttribute(SessionConstants.JOB_TYPES, getEntityService().findJobTypes());
        //request.setAttribute(SessionConstants.JOB_TYPES, getEntityService().findFileNewJobTypes());
        // set form data
        FileActionForm myform = (FileActionForm) form;
        // get default actions based on user selected actionCode and actionOutcome
        ActionCode actionCode = myform.getActionCode();
        // action code selected, load active outcomes
        request.setAttribute(SessionConstants.ACTION_OUTCOMES, getEntityService().findActionOutcomesByActionCode(actionCode));
        //
        Long actionTypeId = myform.getActionTypeId();
        ActionTypeEnum actionTypeEnum = ActionTypeEnum.findByActionTypeId(actionTypeId);
        if (actionTypeEnum != null)
        {
            // ActionCode(s) - use simple logic to distinguish between file/job action (entity != null)
        	List<ActionCode> actionCodes;
        	BaseAction action = myform.getEntity();
        	if (action == null) {
        		action = myform.getJobAction();
        	}
        	if (action != null) {
        		actionCodes = getEntityService().findActionCodeByActionType(actionTypeEnum);
        	} else {
        		JobAction ja = myform.getJobAction();
        		JobType jobType = ja == null ? null : ja.getJob().getJobType();
        		actionCodes = getEntityService().findActionCodeByActionTypeJobType(actionTypeEnum, jobType);
        	}
            request.setAttribute(SessionConstants.ACTION_CODES_TYPE, actionCodes);
            // Contact(s)
            if (ActionTypeEnum.isCall(actionTypeId)
                || ActionTypeEnum.isEmail(actionTypeId)
                || ActionTypeEnum.isSms(actionTypeId))
            {
            	List<Contact> contacts = getFileService().findFileContacts(myform.getFile().getFileId());
            	if (StringUtils.isBlank(action.getDestination())) {
                	for (Contact c : contacts) {
                		if (c.isDefaultContact()) {
                			String destination = null;
                			     if (ActionTypeEnum.isCall(actionTypeId)) {destination = c.getPhone();}
                			else if (ActionTypeEnum.isEmail(actionTypeId)) {destination = c.getEmail();}
                			else if (ActionTypeEnum.isSms(actionTypeId)) {destination = c.getMobile();}
                			action.setDestination(destination);
                			break;
                		}
                	}
            	}
                request.setAttribute(SessionConstants.CONTACTS, contacts);
            }
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
    public ActionForward reload(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        try
        {
            // set form data (reset ActionOutcome)
            FileActionForm myform = (FileActionForm) form;
            myform.setActionOutcome(new ActionOutcome());
            updateDefaultAction(mapping, form, request, response);

            BaseAction baseAction = myform.getEntity();
            if (baseAction == null) {
                baseAction = myform.getJobAction();
            }
            baseAction.setFile(myform.getFile());

            ActionCode actionCode = myform.getActionCode();
            actionCode = getEntityService().findActionCodeById(actionCode.getId());
            if (actionCode != null) {
                // set default notation
                baseAction.setNotation(actionCode.getNotation());
            }
            // generate document(s) (if action code has template associated)
            if (ActionTypeEnum.EMAIL.equals(myform.getActionType())) {
                Template template = actionCode == null ? null : actionCode.getTemplate();
                if (template != null && ContentTypeEnum.APPLICATION_HTML.equals(template.getContentTypeEnum())) {
                	Document document = getDocumentService().createActionDocument(template, baseAction);
            		byte[] content = document.getContent();
                    myform.setHtmlInput(new String(content));
                } else {
                    myform.setHtmlInput("Type email text here...");
                }
                Template documentTemplate = actionCode == null ? null : actionCode.getDocumentTemplate();
                myform.setPdfInput(documentTemplate != null && ContentTypeEnum.APPLICATION_PDF.equals(documentTemplate.getContentTypeEnum()));
            } else if (ActionTypeEnum.LETTER.equals(myform.getActionType())) {
                Template documentTemplate = actionCode == null ? null : actionCode.getDocumentTemplate();
                myform.setPdfInput(documentTemplate != null && ContentTypeEnum.APPLICATION_PDF.equals(documentTemplate.getContentTypeEnum()));
                Owner defaultOwner = myform.getEntity().getFile().getDefaultOwner();
                myform.setManualDestination(defaultOwner == null ? null : defaultOwner.getAddress().getFullAddress());
            } else {
                myform.setHtmlInput(null);
                myform.setPdfInput(false);
            }
            //
            User responsibleUser = getUserService().findUserById(myform.getResponsibleUser().getId());
            myform.setResponsibleUser(responsibleUser);
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
    public ActionForward changeNextActionCode(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE changeNextActionCode()..");
        try
        {
            //get index to change
            int index = WebUtils.getIndex(request);
            //set default notation (from action code)
            FileActionForm myform = (FileActionForm) form;
            BaseAction nextAction = myform.getNextActions().get(index);
            ActionCode actionCode = nextAction.getActionCode();
            actionCode = getEntityService().findActionCodeById(actionCode.getId());
            nextAction.setNotation(actionCode == null ? null : actionCode.getNotation());

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
    public ActionForward changeActionType(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE changeActionType()..");
        try
        {
            FileActionForm myform = (FileActionForm) form;
            ActionTypeEnum actionType = myform.getActionType();
            if (ActionTypeEnum.CALL.equals(actionType))
            {
                return mapping.findForward("call");
            }
            if (ActionTypeEnum.DIARY.equals(actionType))
            {
                return mapping.findForward("diary");
            }
            if (ActionTypeEnum.EMAIL.equals(actionType))
            {
                return mapping.findForward("email");
            }
            if (ActionTypeEnum.FILE_STATUS.equals(actionType))
            {
                return mapping.findForward("fileStatus");
            }
            if (ActionTypeEnum.JOB.equals(actionType))
            {
                return mapping.findForward("job");
            }
            if (ActionTypeEnum.LETTER.equals(actionType))
            {
                return mapping.findForward("letter");
            }
            if (ActionTypeEnum.MESSAGE.equals(actionType))
            {
                return mapping.findForward("message");
            }
            if (ActionTypeEnum.RFI.equals(actionType))
            {
                return mapping.findForward("rfi");
            }
            if (ActionTypeEnum.SMS.equals(actionType))
            {
                return mapping.findForward("sms");
            }
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
    public ActionForward changeDefaultActionCode(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE changeDefaultActionCode()..");
        try
        {
            //get index to change
            int index = WebUtils.getIndex(request);
            //set default notation (from action code)
            FileActionForm myform = (FileActionForm) form;
            BaseAction defaultAction = myform.getDefaultActions().get(index);
            ActionCode actionCode = defaultAction.getActionCode();
            actionCode = getEntityService().findActionCodeById(actionCode.getId());
            defaultAction.setNotation(actionCode == null ? null : actionCode.getNotation());

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
    public ActionForward addNextAction(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE addNextAction()..");
        try
        {
            //set form data
            FileActionForm myform = (FileActionForm) form;
            FileAction nextAction = new FileAction();
            myform.getNextActions().add(nextAction);
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
    public ActionForward removeNextAction(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE removeNextAction()..");
        try
        {
            //get index to remove
            int index = WebUtils.getIndex(request);
            //set form data
            FileActionForm myform = (FileActionForm) form;
            FileAction nextAction = myform.getNextActions().get(index);
            myform.getNextActions().remove(nextAction);
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
    public ActionForward updateDefaultAction(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE updateDefaultAction()..");
        try
        {
            //set form data
            FileActionForm myform = (FileActionForm) form;
            List<FileAction> defaultActions = myform.getDefaultActions();
            defaultActions.clear();
            //get default actions based on user selected actionCode and actionOutcome
            ActionCode actionCode = myform.getActionCode();
            ActionOutcome actionOutcome = myform.getActionOutcome();
            if (actionOutcome != null && actionOutcome.getId() != null)
            {
                //both action code and action outcome selected, load default actions
                List<ActionWorkflow> actionWorkflows = getEntityService()
                    .findActionWorkflowByActionCodeOutcome(actionCode, actionOutcome);
                Date now = DateUtils.getCurrentDate();
                for (ActionWorkflow actionWorkflow : actionWorkflows)
                {
                    ActionCode nextActionCode = actionWorkflow.getNextActionCode();
                    FileAction defaultAction = new FileAction();
                    defaultAction.setActionCode(nextActionCode);
                    defaultAction.setNotation(nextActionCode.getNotation());
                    defaultAction.setDueDate(DateUtils.addDays(now, actionWorkflow.getNextDueDays()));
                    defaultActions.add(defaultAction);
                }
            }
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
    public ActionForward removeDefaultAction(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE removeDefaultAction()..");
        try
        {
            //get index to remove
            int index = WebUtils.getIndex(request);
            //set form data
            FileActionForm myform = (FileActionForm) form;
            FileAction defaultAction = myform.getDefaultActions().get(index);
            myform.getDefaultActions().remove(defaultAction);
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
     * Save file action.
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward saveFileAction(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        try
        {
            FileActionForm myform = (FileActionForm) form;
            FileAction fileAction = myform.getEntity();
            File file = myform.getFile();
            // ResponsibleUser
            User u = myform.getResponsibleUser();
            fileAction.setResponsibleUser(u.getId() == null ? null : u);
            // fileStatus
            FileStatus fileStatus = myform.getFileStatus();
            if (!file.getFileStatus().equals(fileStatus))
            {
            	file.setFileStatus(fileStatus);
            }
            // update form data
            saveBaseAction(fileAction, myform.getActionCode(), myform.getActionOutcome(),
            	myform.getManualDestination(), myform.getContactId(), myform.getHtmlInput());
            
            // validate
            ActionErrors errors = form.validate(mapping, request);
            if (!errors.isEmpty())
            {
                //saveValidateErrors(request, response, errors);
                saveErrors(request, response, errors);
                populateRequest(form, request);
                //return findForwardError(mapping);
                return mapping.getInputForward();
            }

            // save
            getActionService().saveFileAction(
                new FileActionRequest(file, fileAction, null,
                    myform.getDefaultActions(), myform.getNextActions(),
                    myform.getAttachments(), myform.getArchive(), null));

            return findForwardSuccess(mapping);
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            saveErrors(request, response, toActionErrors(e));
            populateRequest(form, request);
            return mapping.getInputForward();
        }
    }

    /**
     * Save job action.
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward saveJobAction(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE saveJobAction()..");
        try
        {
            FileActionForm myform = (FileActionForm) form;
            JobAction jobAction = myform.getJobAction();
            File file = myform.getFile();
            // ResponsibleUser
            User u = myform.getResponsibleUser();
            jobAction.setResponsibleUser(u.getId() == null ? null : u);
            // fileStatus
            FileStatus fileStatus = myform.getFileStatus();
            if (!file.getFileStatus().equals(fileStatus))
            {
            	file.setFileStatus(fileStatus);
            }
            // update form data
            saveBaseAction(jobAction, myform.getActionCode(), myform.getActionOutcome(),
            	myform.getManualDestination(), myform.getContactId(), myform.getHtmlInput());

            // validate
            ActionErrors errors = myform.validate(mapping, request);
            if (!errors.isEmpty())
            {
                saveErrors(request, response, errors);
                populateRequest(form, request);
                //return findForwardError(mapping);
                return mapping.getInputForward();
            }

            // save
            getActionService().saveJobAction(new JobActionRequest(file, jobAction, null,
                myform.getDefaultActions(), myform.getNextActions(), myform.getAttachments(), null));

            return findForwardSuccess(mapping);
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            populateRequest(form, request);
            return mapping.getInputForward();
        }
    }

    private void saveBaseAction(BaseAction baseAction, ActionCode actionCode,
        ActionOutcome actionOutcome, String manualDestination, Long contactId, String htmlInput) throws Exception
    {
    	actionCode = getEntityService().findActionCodeById(actionCode.getId());
        baseAction.setActionCode(actionCode);
        // for new action only
        if (baseAction.getId() == null) {
            baseAction.setActionOutcome(actionOutcome);
        }
        // set Destination using ManualDestination
        if (StringUtils.isBlank(baseAction.getDestination()) && StringUtils.isNotBlank(manualDestination)) {
            baseAction.setDestination(manualDestination);
        }
        // set Contact
        if (contactId != null && contactId != 0L) {
        	baseAction.setContact(new Contact(contactId));
        }
        // TODO: move to service layer
        if (StringUtils.isNotBlank(htmlInput)) {
            Document document = baseAction.getDocument();
            if (document == null) {
            	document = new Document();
                baseAction.setDocument(document);
            }
            document.setContent(htmlInput.getBytes());
            document.setContentType(ContentTypeEnum.APPLICATION_HTML.getContentType());
            // set document name (TODO: add to UI?)
            String name;
            Template template = actionCode.getTemplate();
            if (template != null) {
                name = template.getName();
            } else {
            	name = baseAction.getSubject();
            }
            document.setName(name);
            // set document description
            document.setDescription(baseAction.getMessage());
        }
    }

    /**
     * When completing an action from top menu they should be completed actions
     * @param request
     * @param action
     */
    protected void updateCompleted(HttpServletRequest request, BaseAction action)
    {
        Boolean completed = WebUtils.getCompleted(request);
        if (completed == null || completed.booleanValue())
        {
            action.setCompletedBy(ThreadLocalUtils.getUser());
            action.setCompletedDate(ThreadLocalUtils.getDate());
        }
    }

    /**
     * Close job action.
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward closeJobAction(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE closeJobAction()..");
        try
        {
            FileActionForm myform = (FileActionForm) form;
            JobAction jobAction = myform.getJobAction();
            if (jobAction.getId() == null)
            {
                //for new action only
                jobAction.setActionCode(myform.getActionCode());
                jobAction.setActionOutcome(myform.getActionOutcome());
            }

            //validate
            ActionErrors errors = form.validate(mapping, request);
            if (!errors.isEmpty())
            {
                saveErrors(request, response, errors);
                populateRequest(form, request);
                return findForwardError(mapping);
            }

            //close
            getActionService().closeJobAction(myform.getFile(), jobAction,
                myform.getDefaultActions());

            return findForwardSuccess(mapping);
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            populateRequest(form, request);
            return findForwardError(mapping);
        }
    }

    /** ============================================================================
     * Present view/edit/delete FileAction form.
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward viewFileAction(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        try
        {
            // get actionId
            Long actionId = WebUtils.getActionId(request);
            FileAction action = getActionService().findFileActionById(actionId);
            // set form
            FileActionForm myform = (FileActionForm) form;
            myform.setEntity(action);
            // update document(s)
            viewBaseAction(action, myform, request, response);
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
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward deleteFileAction(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE deleteFileAction()..");
        try
        {
            // get actionId
            FileActionForm myform = (FileActionForm) form;
            Long actionId = myform.getEntity().getId();
            FileAction fileAction = getActionService().findFileActionById(actionId);
            getActionService().deleteFileAction(fileAction);

            return findForwardSuccess(mapping);
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
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
    public ActionForward downloadFileAction(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        try
        {
            Long actionId = WebUtils.getActionId(request);
            Document document;
            if (actionId != null) {
                document = getActionService().findFileActionById(actionId).getDocument();
            } else {
            	// action is not saved yet, lets generate document
                FileActionForm myform = (FileActionForm) form;
                ActionCode actionCode = getEntityService().findActionCodeById(myform.getActionCode().getId());
                Template template = actionCode.getTemplate();
                if (template == null) {
                	return null;
                }
                document = getDocumentService().createActionDocument(template, myform.getEntity());
            }
            downloadDocument(document, response);
            return null;
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            return findForwardError(mapping);
        }
    }
    public ActionForward downloadFileActionAttachment(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        try
        {
            Long actionId = WebUtils.getActionId(request);
            Document document;
            if (actionId != null) {
                document = getActionService().findFileActionById(actionId).getAttachment();
            } else {
            	// action is not saved yet, lets generate document
                FileActionForm myform = (FileActionForm) form;
                ActionCode actionCode = getEntityService().findActionCodeById(myform.getActionCode().getId());
                Template template = actionCode.getDocumentTemplate();
                if (template == null) {
                	return null;
                }
                document = getDocumentService().createActionDocument(template, myform.getEntity());
            }
            downloadDocument(document, response);
            return null;
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
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
    public ActionForward downloadJobAction(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        try
        {
            Long actionId = WebUtils.getActionId(request);
            Document document;
            if (actionId != null) {
                document = getActionService().findJobActionById(actionId).getDocument();
            } else {
            	// action is not saved yet, lets generate document
                FileActionForm myform = (FileActionForm) form;
                ActionCode actionCode = getEntityService().findActionCodeById(myform.getActionCode().getId());
                Template template = actionCode.getTemplate();
                if (template == null) {
                	return null;
                }
                document = getDocumentService().createActionDocument(template, myform.getEntity());
            }
            downloadDocument(document, response);
            return null;
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            return findForwardError(mapping);
        }
    }
    public ActionForward downloadJobActionAttachment(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        try
        {
            Long actionId = WebUtils.getActionId(request);
            Document document;
            if (actionId != null) {
                document = getActionService().findJobActionById(actionId).getAttachment();
            } else {
            	// action is not saved yet, lets generate document
                FileActionForm myform = (FileActionForm) form;
                ActionCode actionCode = getEntityService().findActionCodeById(myform.getActionCode().getId());
                Template template = actionCode.getDocumentTemplate();
                if (template == null) {
                	return null;
                }
                document = getDocumentService().createActionDocument(template, myform.getEntity());
            }
            downloadDocument(document, response);
            return null;
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            return findForwardError(mapping);
        }
    }

    /** ============================================================================
     * Present view/edit/delete JobAction form.
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward viewJobAction(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        try
        {
            //get actionId
            Long actionId = WebUtils.getActionId(request);
            JobAction action = getActionService().findJobActionById(actionId);
            //set form
            FileActionForm myform = (FileActionForm) form;
            myform.setJobAction(action);
            // update document(s)
            viewBaseAction(action, myform, request, response);
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
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward deleteJobAction(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE deleteJobAction()..");
        try
        {
            //get actionId
            FileActionForm myform = (FileActionForm) form;
            Long actionId = myform.getJobAction().getId();
            JobAction jobAction = getActionService().findJobActionById(actionId);
            getActionService().deleteJobAction(jobAction);

            return findForwardSuccess(mapping);
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            return mapping.getInputForward();
        }
    }

    protected void viewBaseAction(BaseAction action, FileActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        // set form data (reset ActionOutcome)
        ActionCode actionCode = action.getActionCode();
        ActionType actionType = actionCode.getActionType();
        form.setActionCode(actionCode);
        form.setActionOutcome(new ActionOutcome());
        //
        if (StringUtils.isBlank(action.getSubject()) && actionType.isEmail()) {
        	action.setSubject(actionCode.getNotation());
        }
        if (StringUtils.isBlank(action.getDestination()) && (actionType.isEmail() || actionType.isCall() || actionType.isSms())) {
        	action.setDestination(action.getFile().getDefaultDestination(actionType));
        }
        //
        Document document = action.getDocument();
        if (document != null) {
    		byte[] content = document.getContent();
        	if (ContentTypeEnum.APPLICATION_HTML.equals(document.getContentTypeEnum())) {
        		form.setHtmlInput(content == null ? null : new String(content));
        	}
        }
        Document attachment = action.getAttachment();
        form.setPdfInput(attachment != null && ContentTypeEnum.APPLICATION_PDF.equals(attachment.getContentTypeEnum()));
    }

}