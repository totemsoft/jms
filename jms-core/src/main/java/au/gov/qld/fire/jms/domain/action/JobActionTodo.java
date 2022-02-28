package au.gov.qld.fire.jms.domain.action;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Entity
@Table(name = "V_JOB_ACTION_TODO")
public class JobActionTodo extends BaseActionTodo
{

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "JOB_ACTION_ID", nullable = false)
    private Long id;

    @Column(name = "JOB_ID", nullable = false)
    private Long jobId;

    @Column(name = "JOB_TYPE_ID", nullable = false)
    private Long jobTypeId;

    @Column(name = "JOB_TYPE_NAME", nullable = false)
    private String jobType;

    @Temporal(TemporalType.DATE)
    @Column(name = "JOB_START_DATE", nullable = false)
    private Date jobStartDate;

    @Column(name = "RESPONSIBLE_USER_ID", nullable = true)
    private Long responsibleUserId;

    @Column(name = "WORK_GROUP_USER_ID", nullable = true)
    private Long workGroupUserId;

    public JobActionTodo()
    {
		super();
	}

    public JobActionTodo(Long id)
    {
		this.id = id;
	}

    /**
	 * @return the id
	 */
	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	@Transient
	public boolean isJobAction()
	{
		return true;
	}

	/**
     * @return the jobId
     */
    public Long getJobId()
    {
        return jobId;
    }

	public void setJobId(Long jobId)
	{
		this.jobId = jobId;
	}

    /**
     * @return the alarmStatus
     */
    public String getJobType()
    {
        return jobType;
    }

	public void setJobType(String jobType)
	{
		this.jobType = jobType;
	}

    /**
     * @return the jobStartDate
     */
    public Date getJobStartDate()
    {
        return jobStartDate;
    }

	public void setJobStartDate(Date jobStartDate)
	{
		this.jobStartDate = jobStartDate;
	}

}