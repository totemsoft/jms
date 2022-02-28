package au.gov.qld.fire.jms.domain.action;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;

import au.gov.qld.fire.jms.domain.file.File;
import au.gov.qld.fire.jms.domain.job.Job;

/**
 * @hibernate.class table="JOB_ACTION" dynamic-update="true"
 */
@Entity
@Table(name = "JOB_ACTION")
public class JobAction extends BaseAction
{

    /** serialVersionUID */
    private static final long serialVersionUID = 23266208424544281L;

    private Job job;

    private JobAction parentJobAction;

    private List<JobAction> childrenJobActions;

    private FileAction parentFileAction;

    private List<FileAction> childrenFileActions;

    /**
     * 
     */
    public JobAction()
    {
        super();
    }

    /**
     * @param id
     */
    public JobAction(Long id)
    {
        super(id);
    }

    /**
     * @return
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "JOB_ACTION_ID")
    public Long getJobActionId()
    {
        return super.getId();
    }

    /**
     * @param id
     */
    public void setJobActionId(Long id)
    {
        super.setId(id);
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

    /** 
     *            @hibernate.many-to-one
     *             not-null="true"
     *            @hibernate.column name="JOB_ID"         
     *         
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "JOB_ID", nullable = false)
    public Job getJob()
    {
        if (job == null)
        {
            job = new Job();
        }
        return this.job;
    }

    public void setJob(Job job)
    {
        this.job = job;
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.domain.action.BaseAction#getFile()
     */
    @Override
    @Transient
    public File getFile()
    {
        return getJob().getFile();
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.domain.action.BaseAction#setFile(au.gov.qld.fire.jms.domain.file.File)
     */
    @Override
    public void setFile(File file)
    {
        getJob().setFile(file);
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ACTION_ID", nullable = true)
	public JobAction getParentJobAction()
	{
		return parentJobAction;
	}

	public void setParentJobAction(JobAction parentJobAction)
	{
		this.parentJobAction = parentJobAction;
	}

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parentJobAction")
    @Where(clause = "LOGICALLY_DELETED IS NULL")
	public List<JobAction> getChildrenJobActions()
	{
		if (childrenJobActions == null)
		{
			childrenJobActions = new ArrayList<JobAction>();
		}
		return childrenJobActions;
	}

	public void setChildrenJobActions(List<JobAction> childrenJobActions)
	{
		this.childrenJobActions = childrenJobActions;
	}

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_FILE_ACTION_ID", nullable = true)
	public FileAction getParentFileAction()
	{
		return parentFileAction;
	}

	public void setParentFileAction(FileAction parentFileAction)
	{
		this.parentFileAction = parentFileAction;
	}

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parentJobAction")
    @Where(clause = "LOGICALLY_DELETED IS NULL")
	public List<FileAction> getChildrenFileActions()
	{
		if (childrenFileActions == null)
		{
			childrenFileActions = new ArrayList<FileAction>();
		}
		return childrenFileActions;
	}

	public void setChildrenFileActions(List<FileAction> childrenFileActions)
	{
		this.childrenFileActions = childrenFileActions;
	}

}