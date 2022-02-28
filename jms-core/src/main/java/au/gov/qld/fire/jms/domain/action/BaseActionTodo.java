package au.gov.qld.fire.jms.domain.action;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import au.gov.qld.fire.domain.entity.Contact;
import au.gov.qld.fire.jms.domain.mail.MailMethodEnum;
import au.gov.qld.fire.util.Formatter;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@MappedSuperclass
public abstract class BaseActionTodo
{

    @Column(name = "FILE_ID", nullable = false)
    private Long fileId;

    @Column(name = "FCA_NO", nullable = true)
    private String fcaId;

    @Temporal(TemporalType.DATE)
    @Column(name = "NEXT_ACTION_DATE", nullable = true)
    private Date nextActionDate;

    @Column(name = "NEXT_ACTION_CODE", nullable = true)
    private String nextAction;

    @Column(name = "WORK_GROUP", nullable = true)
    private String workGroup;

    @Transient
    private Long sapCustNo;

    @Transient
    private String buildingName;

    @Transient
    private String createdBy;

    @Transient
    private String responsibleUser;

    @Transient
    private String completedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "COMPLETED_DATE", nullable = true)
    private Date completedDate;

    @Transient
    private Contact contact;

    @Transient
    private String destination;

    @Transient
    private String notation;

    @Transient
    private MailMethodEnum mailMethod;

    @Transient
    private boolean noMailOut;

    @Column(name = "NEXT_ACTION_TYPE_ID", nullable = true)
    private Long actionTypeId;

    @Column(name = "NEXT_ACTION_CODE_ID", nullable = true)
    private Long actionCodeId;

    @Column(name = "WORK_GROUP_ID", nullable = true)
    private Long workGroupId;

    /**
	 * @return the id
	 */
	public abstract Long getId();

	@Transient
	public abstract boolean isJobAction();

	/**
     * @return the fileNo
     */
    public Long getFileId()
    {
        return fileId;
    }

	public void setFileId(Long fileId)
	{
		this.fileId = fileId;
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
     * @return the actionCodeId
     */
	public Long getActionCodeId()
	{
		return actionCodeId;
	}

	public void setActionCodeId(Long actionCodeId)
	{
		this.actionCodeId = actionCodeId;
	}

	/**
     * @return the fcaId
     */
    public String getFcaId()
    {
        return fcaId;
    }

	public void setFcaId(String fcaId)
	{
		this.fcaId = fcaId;
	}

    /**
	 * @return the sapCustNo
	 */
	public Long getSapCustNo()
	{
		return sapCustNo;
	}

	public void setSapCustNo(Long sapCustNo)
	{
		this.sapCustNo = sapCustNo;
	}

	/**
	 * @return the buildingName
	 */
	public String getBuildingName()
	{
		return buildingName;
	}

	public void setBuildingName(String buildingName)
	{
		this.buildingName = buildingName;
	}

	/**
     * @return the nextActionDate
     */
    public Date getNextActionDate()
    {
        return nextActionDate;
    }

    public void setNextActionDate(Date nextActionDate)
    {
		this.nextActionDate = nextActionDate;
	}

	/**
     * @return the nextAction
     */
    public String getNextAction()
    {
        return nextAction;
    }

    public void setNextAction(String nextAction)
    {
		this.nextAction = nextAction;
	}

	/**
     * @return the workGroup
     */
    public String getWorkGroup()
    {
        return workGroup;
    }

	public void setWorkGroup(String workGroup)
	{
		this.workGroup = workGroup;
	}

	/**
	 * @return the createdBy
	 */
	public String getCreatedBy()
	{
		return createdBy;
	}

	public void setCreatedBy(String createdBy)
	{
		this.createdBy = createdBy;
	}

	/**
	 * @return the responsibleUser
	 */
	public String getResponsibleUser()
	{
		return responsibleUser;
	}

	public void setResponsibleUser(String responsibleUser)
	{
		this.responsibleUser = responsibleUser;
	}

	/**
	 * @return the completedBy
	 */
	public String getCompletedBy()
	{
		return completedBy;
	}

	public void setCompletedBy(String completedBy)
	{
		this.completedBy = completedBy;
	}

	/**
	 * @return the completedDate
	 */
	public Date getCompletedDate()
	{
		return completedDate;
	}

	public void setCompletedDate(Date completedDate)
	{
		this.completedDate = completedDate;
	}

	/**
	 * @return the contact
	 */
	public Contact getContact()
	{
		return contact;
	}

	public void setContact(Contact contact)
	{
		this.contact = contact;
	}

	/**
	 * @return the destination
	 */
	public String getDestination()
	{
		return destination;
	}

	public void setDestination(String destination)
	{
		this.destination = destination;
	}

	/**
	 * @return the notation
	 */
	public String getNotation()
	{
		return notation;
	}

	public void setNotation(String notation)
	{
		this.notation = Formatter.formatJson(notation);
	}

	/**
	 * @return the mailMethod
	 */
	public MailMethodEnum getMailMethod()
	{
		return mailMethod;
	}

	public void setMailMethod(MailMethodEnum mailMethod)
	{
		this.mailMethod = mailMethod;
	}

	/**
	 * @return the noMailOut
	 */
	public boolean isNoMailOut()
	{
		return noMailOut;
	}

	public void setNoMailOut(boolean noMailOut)
	{
		this.noMailOut = noMailOut;
	}

}