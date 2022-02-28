package au.gov.qld.fire.jms.domain.action;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;

import au.gov.qld.fire.domain.Auditable;
import au.gov.qld.fire.domain.document.Document;
import au.gov.qld.fire.domain.entity.Contact;
import au.gov.qld.fire.domain.security.User;
import au.gov.qld.fire.jms.domain.file.File;
import au.gov.qld.fire.jms.domain.refdata.ActionCode;
import au.gov.qld.fire.util.DateUtils;

/**
 * @hibernate.class table="FILE_ACTION" dynamic-update="true"
 * @hibernate.class table="JOB_ACTION" dynamic-update="true"
 */
@MappedSuperclass
public abstract class BaseAction extends Auditable<Long>
{

    /** serialVersionUID */
    private static final long serialVersionUID = -2520456602043871963L;

    /** persistent field */
    private Date dueDate;

    /** persistent field */
    private String notation;

    /** nullable persistent field */
    private String destination;

    /** nullable persistent field */
    private String subject;

    /** eg email, call or sms contact */
    private Contact contact;

    /** nullable persistent field */
    private User completedBy;

    /** nullable persistent field */
    private Date completedDate;

    /** persistent field */
    private ActionCode actionCode;

    /** persistent field */
    private User responsibleUser;

    /** nullable persistent field */
    private ActionOutcome actionOutcome;

    /** nullable persistent field, eg email body */
    private Document document;
    /** nullable persistent field, attachment */
    private Document attachment;

    /** store optional JMS entity id, eg FileStatus.id */
    private Long linkId;

    public BaseAction()
    {
        super();
    }

    public BaseAction(Long id)
    {
        super(id);
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "DUE_DATE", nullable = false)
    public Date getDueDate()
    {
        if (dueDate == null) {
            dueDate = DateUtils.getCurrentDate();
        }
        return this.dueDate;
    }

    public void setDueDate(Date dueDate)
    {
        this.dueDate = dueDate;
    }

    @Column(name = "NOTATION", nullable = false)
    public String getNotation()
    {
        return this.notation;
    }

    public void setNotation(String notation)
    {
        this.notation = notation;
    }

    /**
     * Calculated property - used in jsp drop down boxes
     * @return
     */
    @Transient
    public String getName()
    {
        return
        	DateUtils.DD_MM_YYYY.format(getDueDate()) +
        	" [" + getActionCode().getCode() + "] " +
            (StringUtils.isBlank(getNotation()) ? actionCode.getNotation() : getNotation())
            ;
    }

    /**
     * Calculated property
     * @return
     */
    @Transient
    public String getMessage()
    {
        return
            (StringUtils.isBlank(getDestination()) ? "" : ("Destination: " + getDestination() + ", ")) +
            (StringUtils.isBlank(getSubject()) ? "" : ("Subject: " + getSubject() + ", ")) +
            (StringUtils.isBlank(getNotation()) ? "" : ("Notation: " + getNotation()))
            ;
    }

    /** 
     *            @hibernate.property
     *             column="DESTINATION"
     *             length="250"
     *         
     */
    @Column(name = "DESTINATION", nullable = true)
    public String getDestination()
    {
        return this.destination;
    }

    public void setDestination(String destination)
    {
        this.destination = destination;
    }

    /** 
     *            @hibernate.property
     *             column="SUBJECT"
     *             length="250"
     *         
     */
    @Column(name = "SUBJECT", nullable = true)
    public String getSubject()
    {
        return this.subject;
    }

    public void setSubject(String subject)
    {
        this.subject = subject;
    }

    /** 
     *            @hibernate.many-to-one
     *             not-null="false"
     *            @hibernate.column name="CONTACT_ID"         
     *         
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "CONTACT_ID", nullable = true)
    public Contact getContact()
    {
        return contact;
    }

    public void setContact(Contact contact)
    {
        this.contact = contact;
    }

    /** 
     *            @hibernate.property
     *             column="COMPLETED_BY"
     *             length="11"
     *             not-null="false"
     *         
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMPLETED_BY", nullable = true)
    public User getCompletedBy()
    {
        return this.completedBy;
    }

    public void setCompletedBy(User completedBy)
    {
        this.completedBy = completedBy;
    }

    /** 
     *            @hibernate.property
     *             column="COMPLETED_DATE"
     *             length="23"
     *             not-null="true"
     *         
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "COMPLETED_DATE", nullable = true)
    public Date getCompletedDate()
    {
        return this.completedDate;
    }

    public void setCompletedDate(Date completedDate)
    {
        this.completedDate = completedDate;
    }

    @Transient
    public boolean isCompleted()
    {
    	return getCompletedDate() != null;
    }

    /** 
     *            @hibernate.many-to-one
     *             not-null="true"
     *            @hibernate.column name="ACTION_CODE_ID"         
     *         
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ACTION_CODE_ID", nullable = false)
    public ActionCode getActionCode()
    {
        if (actionCode == null)
        {
            actionCode = new ActionCode();
        }
        return this.actionCode;
    }

    public void setActionCode(ActionCode actionCode)
    {
        this.actionCode = actionCode;
    }

    /** 
     *            @hibernate.many-to-one
     *             not-null="false"
     *            @hibernate.column name="RESPONSIBLE_USER_ID"         
     *         
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RESPONSIBLE_USER_ID", nullable = true)
    public User getResponsibleUser()
    {
        return responsibleUser;
    }

    /**
     * @param responsibleUser the responsibleUser to set
     */
    public void setResponsibleUser(User responsibleUser)
    {
        this.responsibleUser = responsibleUser;
    }

    /** 
     *            @hibernate.many-to-one
     *             not-null="true"
     *            @hibernate.column name="ACTION_OUTCOME_ID"         
     *         
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ACTION_OUTCOME_ID", nullable = true)
    public ActionOutcome getActionOutcome()
    {
        return this.actionOutcome;
    }

    public void setActionOutcome(ActionOutcome actionOutcome)
    {
        this.actionOutcome = actionOutcome;
    }

    /**
     * @return
     */
    @Transient
    public abstract File getFile();

    public abstract void setFile(File file);

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DOCUMENT_ID", nullable = true)
    public Document getDocument()
    {
        return document;
    }

    public void setDocument(Document document)
    {
        this.document = document;
    }

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "ATTACHMENT_ID", nullable = true)
	public Document getAttachment()
	{
		return attachment;
	}

	public void setAttachment(Document attachment)
	{
		this.attachment = attachment;
	}

	/**
	 * @return the linkId
	 */
    @Column(name = "LINK_ID", nullable = true)
	public Long getLinkId()
	{
		return linkId;
	}

	public void setLinkId(Long linkId)
	{
		this.linkId = linkId;
	}

}