package au.gov.qld.fire.jms.web.module.file;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import au.gov.qld.fire.domain.security.User;
import au.gov.qld.fire.jms.domain.Fca;
import au.gov.qld.fire.jms.domain.action.ActionOutcome;
import au.gov.qld.fire.jms.domain.action.FileAction;
import au.gov.qld.fire.jms.domain.action.JobAction;
import au.gov.qld.fire.jms.domain.file.Archive;
import au.gov.qld.fire.jms.domain.file.File;
import au.gov.qld.fire.jms.domain.refdata.ActionCode;
import au.gov.qld.fire.jms.domain.refdata.ActionTypeEnum;
import au.gov.qld.fire.jms.domain.refdata.FileStatus;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class FileActionForm extends AbstractAttachmentForm<FileAction>
{

    /** serialVersionUID */
    private static final long serialVersionUID = -7469206808432191907L;

    /** flag to separate todo/completed actions */
    private boolean todoAction;

    private File file;

    private FileStatus fileStatus;

    private JobAction jobAction;

    private User responsibleUser;

    private String manualDestination;

    /** contactId for destination */
    private Long contactId;

    private Long actionTypeId;

    private ActionCode actionCode;

    private ActionOutcome actionOutcome;

    private List<FileAction> defaultActions;

    private List<FileAction> nextActions;

    private String htmlInput; // from actionCode.template
    private boolean pdfInput; // from actionCode.documentTemplate

    private Archive archive;

    /* (non-Javadoc)
     * @see org.apache.struts.validator.ValidatorForm#reset(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
     */
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request)
    {
        //do nothing (this form is refreshed via wizard like style: add/remove action)
        //super.reset(mapping, request);
    	fileStatus = null;
    	archive = null;
    }

    /* (non-Javadoc)
     * @see org.apache.struts.validator.ValidatorForm#validate(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
     */
    @Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
    {
        //getMultipartRequestHandler().
        return super.validate(mapping, request);
    }

    /**
	 * @return the todoAction
	 */
	public boolean isTodoAction()
	{
		return todoAction;
	}

	public void setTodoAction(boolean todoAction)
	{
		this.todoAction = todoAction;
	}

	/**
     * @return the fileId
     */
    public Long getFileId()
    {
    	if (file != null) {
    		return file.getFileId();
    	}
        return getEntity() == null || getEntity().getFile() == null ? null : getEntity().getFile().getFileId();
    }

	/**
     * @return the file (parent)
     */
    public File getFile()
    {
        return file;
    }

	/**
	 * @return the fileStatus
	 */
	public FileStatus getFileStatus()
	{
		if (fileStatus == null)
		{
			fileStatus = new FileStatus();
		}
		return fileStatus;
	}

	public void setFileStatus(FileStatus fileStatus)
	{
		this.fileStatus = fileStatus;
	}

	/* (non-Javadoc)
     * @see au.gov.qld.fire.jms.web.module.AbstractValidatorForm#setEntity(java.lang.Object)
     */
    @Override
    public void setEntity(FileAction entity)
    {
        super.setEntity(entity);
        if (entity != null)
        {
            file = entity.getFile();
        }
        if (entity != null && file != null && file.getFca() == null)
        {
        	file.setFca(new Fca());
        }
        if (entity != null)
        {
            responsibleUser = entity.getResponsibleUser() != null ? entity.getResponsibleUser() : new User();
        }
        //reset properties
        //todoAction = false;
        //actionType = null;
        manualDestination = null;
        contactId = null;
        actionCode = null;
        actionOutcome = null;
        defaultActions = null;
        nextActions = null;
        htmlInput = null;
        pdfInput = false;
    	fileStatus = entity != null && file != null ? file.getFileStatus() : null;
    	archive = null;
    }

    /**
     * @return the jobAction
     */
    public JobAction getJobAction()
    {
        return jobAction;
    }

    public void setJobAction(JobAction jobAction)
    {
        setEntity(null);
        this.jobAction = jobAction;
        if (jobAction != null)
        {
            file = jobAction.getFile();
            responsibleUser = jobAction.getResponsibleUser() != null ? jobAction.getResponsibleUser() : new User();
        }
        else
        {
        	responsibleUser = new User();
        }
//        //reset properties
//        //actionType = null;
//        manualDestination = null;
//        actionCode = null;
//        actionOutcome = null;
//        defaultActions = null;
//        nextActions = null;
//        editorInput = null;
//        attachmentFiles = null;
//        attachments = null;
    }

    /**
	 * @return the responsibleUser
	 */
	public User getResponsibleUser()
	{
		if (responsibleUser == null)
		{
			responsibleUser = new User();
		}
		return responsibleUser;
	}

	public void setResponsibleUser(User responsibleUser)
	{
		this.responsibleUser = responsibleUser;
	}

	/**
     * @return the manualDestination
     */
    public String getManualDestination()
    {
        return manualDestination;
    }

    public void setManualDestination(String manualDestination)
    {
        this.manualDestination = manualDestination;
    }

    /**
	 * @return the contactId
	 */
	public Long getContactId()
	{
		return contactId;
	}

	public void setContactId(Long contactId)
	{
		this.contactId = contactId;
	}

	/**
     * @return the actionTypeId
     */
    public Long getActionTypeId()
    {
        return actionTypeId;
    }

    public void setActionTypeId(Long actionTypeId)
    {
        this.actionTypeId = actionTypeId;
    }

    /**
     * @return the actionType
     */
    public ActionTypeEnum getActionType()
    {
        return ActionTypeEnum.findByActionTypeId(actionTypeId);
    }

    public void setActionType(ActionTypeEnum actionType)
    {
        this.actionTypeId = actionType == null ? null : actionType.getId();
    }

    /**
     * @return the actionCode
     */
    public ActionCode getActionCode()
    {
        if (actionCode == null)
        {
            actionCode = new ActionCode();
        }
        return actionCode;
    }

    public void setActionCode(ActionCode actionCode)
    {
        this.actionCode = actionCode;
    }

    /**
     * @return the actionOutcome
     */
    public ActionOutcome getActionOutcome()
    {
        if (actionOutcome == null)
        {
            actionOutcome = new ActionOutcome();
        }
        return actionOutcome;
    }

    public void setActionOutcome(ActionOutcome actionOutcome)
    {
        this.actionOutcome = actionOutcome;
    }

    /**
     * @return the defaultActions
     */
    public List<FileAction> getDefaultActions()
    {
        if (defaultActions == null)
        {
            defaultActions = new ArrayList<FileAction>();
        }
        return defaultActions;
    }

    /**
     * @return the nextActions
     */
    public List<FileAction> getNextActions()
    {
        if (nextActions == null)
        {
            nextActions = new ArrayList<FileAction>();
        }
        return nextActions;
    }

    /**
     * Convert byte[] to String for editor
     * @return
     */
    public String getHtmlInput()
    {
        return htmlInput;
    }

    public void setHtmlInput(String htmlInput)
    {
        this.htmlInput = htmlInput;
    }

    /**
	 * @return the pdfInput
	 */
	public boolean isPdfInput()
	{
		return pdfInput;
	}

	public void setPdfInput(boolean pdfInput)
	{
		this.pdfInput = pdfInput;
	}

	/**
	 * @return the archive
	 */
	public Archive getArchive()
	{
		if (archive == null)
		{
			archive = new Archive();
		}
		return archive;
	}

	public void setArchive(Archive archive)
	{
		this.archive = archive;
	}

}