package au.gov.qld.fire.jms.domain.job;

import java.util.ArrayList;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.ArrayUtils;
import org.hibernate.annotations.Type;

import au.gov.qld.fire.domain.Auditable;
import au.gov.qld.fire.domain.security.User;
import au.gov.qld.fire.jms.domain.Fca;
import au.gov.qld.fire.jms.domain.action.JobAction;
import au.gov.qld.fire.jms.domain.file.File;
import au.gov.qld.fire.jms.domain.refdata.JobType;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Entity
@Table(name = "JOBS")
public class Job extends Auditable<Long>
{

    /** serialVersionUID */
    private static final long serialVersionUID = -5693520841861776981L;

    /** IGNORE */
    public static final String[] IGNORE = (String[]) ArrayUtils.addAll(Auditable.IGNORE,
        new String[]
        {
    		"completedBy", "fca", "file", "jobActions", "jobDocs", "jobRequest", "jobType"
        });

    private String description;

    private String requestedEmail;

    private String link;

    private boolean status = true;

    private User responsibleUser;

    private User completedBy;

    private Date completedDate;

    private String closeReason;

    private File file;

    private Fca fca;

    private JobType jobType;

    private JobRequest jobRequest;

    private List<JobDoc> jobDocs;

    private List<JobAction> jobActions;

    public Job()
    {
        super();
    }

    public Job(Long id)
    {
        super(id);
    }

    public Job(JobRequest jobRequest)
    {
        this.description = jobRequest.getDescription();
        this.requestedEmail = jobRequest.getRequestedEmail();
        this.link = jobRequest.getLink();
        this.status = true;
        this.file = jobRequest.getFca().getFile();
        this.fca = jobRequest.getFca();
        this.jobType = jobRequest.getJobType();
        this.jobRequest = jobRequest;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "JOB_ID")
    public Long getJobId()
    {
        return super.getId();
    }

    public void setJobId(Long id)
    {
        super.setId(id);
    }

    @Column(name = "DESCRIPTION", nullable = false)
    public String getDescription()
    {
        return this.description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    @Column(name = "REQUESTED_EMAIL", nullable = true)
    public String getRequestedEmail()
    {
        return this.requestedEmail;
    }

    public void setRequestedEmail(String requestedEmail)
    {
        this.requestedEmail = requestedEmail;
    }

    @Column(name = "LINK", nullable = true)
    public String getLink()
    {
        return this.link;
    }

    public void setLink(String link)
    {
        this.link = link;
    }

    @Column(name = "STATUS", nullable = false)
    @Type(type = "yes_no")
    public boolean isStatus()
    {
        return this.status;
    }

    public void setStatus(boolean status)
    {
        this.status = status;
    }

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

    @Column(name = "CLOSE_REASON", nullable = true)
    public String getCloseReason()
    {
        return this.closeReason;
    }

    public void setCloseReason(String closeReason)
    {
        this.closeReason = closeReason;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FILE_ID", nullable = false)
    public File getFile()
    {
        return this.file;
    }

    public void setFile(File file)
    {
        this.file = file;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FCA_NO", nullable = false)
    public Fca getFca()
    {
        return this.fca;
    }

    public void setFca(Fca fca)
    {
        this.fca = fca;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "JOB_TYPE_ID", nullable = false)
    public JobType getJobType()
    {
        if (jobType == null)
        {
            jobType = new JobType();
        }
        return this.jobType;
    }

    public void setJobType(JobType jobType)
    {
        this.jobType = jobType;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RESPONSIBLE_USER_ID", nullable = true)
	public User getResponsibleUser()
	{
		return responsibleUser;
	}

	public void setResponsibleUser(User responsibleUser)
	{
		this.responsibleUser = responsibleUser;
	}

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "JOB_REQUEST_ID", nullable = true)
    public JobRequest getJobRequest()
    {
        return this.jobRequest;
    }

    public void setJobRequest(JobRequest jobRequest)
    {
        this.jobRequest = jobRequest;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "job")
    //@Where(clause = "LOGICALLY_DELETED IS NULL")
    public List<JobDoc> getJobDocs()
    {
        if (jobDocs == null)
        {
            jobDocs = new ArrayList<JobDoc>();
        }
        return this.jobDocs;
    }

    public void setJobDocs(List<JobDoc> jobDocs)
    {
        this.jobDocs = jobDocs;
    }

    public void add(JobDoc jobDoc)
    {
        jobDoc.setJob(this);
        getJobDocs().add(jobDoc);
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "job")
    //@Where(clause = "LOGICALLY_DELETED IS NULL")
    public List<JobAction> getJobActions()
    {
        if (jobActions == null)
        {
            jobActions = new ArrayList<JobAction>();
        }
        return this.jobActions;
    }

    public void setJobActions(List<JobAction> jobActions)
    {
        this.jobActions = jobActions;
    }

}