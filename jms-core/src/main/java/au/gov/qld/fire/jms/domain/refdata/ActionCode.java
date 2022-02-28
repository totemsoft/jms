package au.gov.qld.fire.jms.domain.refdata;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;

import au.gov.qld.fire.domain.Auditable;
import au.gov.qld.fire.domain.IAuditable;
import au.gov.qld.fire.domain.document.Template;
import au.gov.qld.fire.domain.location.Area;
import au.gov.qld.fire.domain.refdata.WorkGroup;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Entity
@Table(name = "ACTION_CODE")
@Cache(region = "au.gov.qld.fire.jms.domain.refdata.ActionCode", usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Where(clause = IAuditable.WHERE)
public class ActionCode extends Auditable<Long>
{

    /** serialVersionUID */
    private static final long serialVersionUID = 5059435190298250625L;

    /** ACTION_TYPE property name */
    public static final String ACTION_TYPE = "actionType";

    /** TEMPLATE property name */
    public static final String TEMPLATE = "template";

    /** WORK_GROUP property name */
    public static final String WORK_GROUP = "workGroup";

    private String code;

    private String notation;

    private Template template;
    private Template documentTemplate;

    private ActionType actionType;

    private JobType jobType;

    private WorkGroup workGroup;

    private Area area;

    private boolean bulkMail;

    /**
     * 
     */
    public ActionCode()
    {
        super();
    }

    /**
     * @param id
     */
    public ActionCode(Long id)
    {
        super(id);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ACTION_CODE_ID")
    public Long getActionCodeId()
    {
        return super.getId();
    }

    public void setActionCodeId(Long id)
    {
        super.setId(id);
    }

    @Column(name = "CODE", nullable = false, length = 64)
    public String getCode()
    {
        return this.code;
    }

    public void setCode(String code)
    {
        this.code = code;
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

    /* (non-Javadoc)
	 * @see au.gov.qld.fire.domain.BaseModel#getLogicallyDeleted()
	 */
    @Override
    @Column(name = "LOGICALLY_DELETED", nullable = true)
    @Type(type = "yes_no")
	protected Boolean getLogicallyDeleted()
    {
		return super.getLogicallyDeleted();
	}

	/* (non-Javadoc)
     * @see au.gov.qld.fire.jms.domain.BaseModel#setLogicallyDeleted(java.lang.Boolean)
     */
    @Override
    public void setLogicallyDeleted(Boolean logicallyDeleted)
    {
        super.setLogicallyDeleted(logicallyDeleted);
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.domain.BaseModel#isActive()
     */
    @Override
    @Column(name = "ACTIVE", nullable = false)
    @Type(type = "yes_no")
    public boolean isActive()
    {
        return super.isActive();
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.domain.BaseModel#setActive(boolean)
     */
    @Override
    public void setActive(boolean active)
    {
        super.setActive(active);
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEMPLATE_ID", nullable = true)
    @NotFound(action = NotFoundAction.IGNORE) // template can be logically deleted
    public Template getTemplate()
    {
        return this.template;
    }

    public void setTemplate(Template template)
    {
        this.template = template;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DOCUMENT_TEMPLATE_ID", nullable = true)
    @NotFound(action = NotFoundAction.IGNORE) // template can be logically deleted
	public Template getDocumentTemplate()
	{
		return documentTemplate;
	}

	public void setDocumentTemplate(Template documentTemplate)
	{
		this.documentTemplate = documentTemplate;
	}

	@Transient
    public boolean isPdfForm()
    {
    	return documentTemplate == null ? false : documentTemplate.getTemplateTypeEnum().isPdfForm();
    }

	@ManyToOne//(fetch = FetchType.LAZY)
    @JoinColumn(name = "ACTION_TYPE_ID", nullable = false)
    public ActionType getActionType()
    {
        if (actionType == null)
        {
            actionType = new ActionType();
        }
        return this.actionType;
    }

    public void setActionType(ActionType actionType)
    {
        this.actionType = actionType;
    }

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "JOB_TYPE_ID", nullable = true)
	public JobType getJobType()
	{
		return jobType;
	}

	public void setJobType(JobType jobType)
	{
		this.jobType = jobType;
	}

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WORK_GROUP_ID", nullable = true)
    public WorkGroup getWorkGroup()
    {
        return this.workGroup;
    }

    public void setWorkGroup(WorkGroup workGroup)
    {
        this.workGroup = workGroup;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AREA_ID", nullable = true)
    public Area getArea()
    {
        return this.area;
    }

    public void setArea(Area area)
    {
        this.area = area;
    }

    @Column(name = "BULK_MAIL", nullable = false)
    @Type(type = "yes_no")
	public boolean isBulkMail()
	{
		return bulkMail;
	}

	public void setBulkMail(boolean bulkMail)
	{
		this.bulkMail = bulkMail;
	}

}