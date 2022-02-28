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

import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;

import au.gov.qld.fire.jms.domain.file.File;

/**
 * @hibernate.class table="FILE_ACTION" dynamic-update="true"
 */
@Entity
@Table(name = "FILE_ACTION")
public class FileAction extends BaseAction
{

    /** serialVersionUID */
    private static final long serialVersionUID = -3127057973072044023L;

    private File file;

    private FileAction parentFileAction;

    private List<FileAction> childrenFileActions;

    private JobAction parentJobAction;

    private List<JobAction> childrenJobActions;

    /**
     * 
     */
    public FileAction()
    {
        super();
    }

    /**
     * @param id
     */
    public FileAction(Long id)
    {
        super(id);
    }

    /**
     * @return
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FILE_ACTION_ID")
    public Long getFileActionId()
    {
        return super.getId();
    }

    /**
     * @param id
     */
    public void setFileActionId(Long id)
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

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.domain.action.BaseAction#getFile()
     *
     *            @hibernate.many-to-one
     *             not-null="true"
     *            @hibernate.column name="FILE_ID"         
     *         
     */
    @Override
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FILE_ID", nullable = false)
    public File getFile()
    {
        if (file == null)
        {
            file = new File();
        }
        return this.file;
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.domain.action.BaseAction#setFile(au.gov.qld.fire.jms.domain.file.File)
     */
    @Override
    public void setFile(File file)
    {
        this.file = file;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ACTION_ID", nullable = true)
	public FileAction getParentFileAction()
	{
		return parentFileAction;
	}

	public void setParentFileAction(FileAction parentFileAction)
	{
		this.parentFileAction = parentFileAction;
	}

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parentFileAction")
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_JOB_ACTION_ID", nullable = true)
	public JobAction getParentJobAction()
	{
		return parentJobAction;
	}

	public void setParentJobAction(JobAction parentJobAction)
	{
		this.parentJobAction = parentJobAction;
	}

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parentFileAction")
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

}