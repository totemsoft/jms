package au.gov.qld.fire.jms.domain.job;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

import au.gov.qld.fire.domain.Auditable;
import au.gov.qld.fire.domain.security.User;
import au.gov.qld.fire.jms.domain.Fca;
import au.gov.qld.fire.jms.domain.refdata.JobType;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Entity
@Table(name = "JOB_REQUEST")
public class JobRequest extends Auditable<Long>
{

    /** serialVersionUID */
    private static final long serialVersionUID = 5980231942388746233L;

    /** REQUESTED_BY property name */
    public static final String REQUESTED_BY = "requestedBy";

    /** FCA property name */
    public static final String FCA = "fca";

    /** ACCEPTED_BY property name */
    public static final String ACCEPTED_BY = "acceptedBy";

    /** JOB_TYPE property name */
    public static final String JOB_TYPE = "jobType";

    private String description;

    private User requestedBy;

    private Date requestedDate;

    private String requestedEmail;

    private String link;

    private Boolean accepted;

    private String rejectReason;

    private User acceptedBy;

    private Date acceptedDate;

    private Fca fca;

    private JobType jobType;

    public JobRequest()
    {
        super();
    }

    public JobRequest(Long id)
    {
        super(id);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "JOB_REQUEST_ID")
    public Long getJobRequestId()
    {
        return super.getId();
    }

    public void setJobRequestId(Long id)
    {
        super.setId(id);
    }

    /** 
     *            @hibernate.property
     *             column="DESCRIPTION"
     *             length="255"
     *             not-null="true"
     *         
     */
    @Column(name = "DESCRIPTION", nullable = false)
    public String getDescription()
    {
        return this.description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    /** 
     *            @hibernate.many-to-one
     *             not-null="true"
     *            @hibernate.column name="REQUESTED_BY"
     *         
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REQUESTED_BY", nullable = true)
    public User getRequestedBy()
    {
        return this.requestedBy;
    }

    public void setRequestedBy(User requestedBy)
    {
        this.requestedBy = requestedBy;
    }

    /** 
     *            @hibernate.property
     *             column="REQUESTED_DATE"
     *             length="23"
     *             not-null="true"
     *         
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "REQUESTED_DATE", nullable = false)
    public Date getRequestedDate()
    {
        return this.requestedDate;
    }

    public void setRequestedDate(Date requestedDate)
    {
        this.requestedDate = requestedDate;
    }

    /** 
     *            @hibernate.property
     *             column="REQUESTED_EMAIL"
     *             length="255"
     *             not-null="true"
     *         
     */
    @Column(name = "REQUESTED_EMAIL", nullable = false)
    public String getRequestedEmail()
    {
        return this.requestedEmail;
    }

    public void setRequestedEmail(String requestedEmail)
    {
        this.requestedEmail = requestedEmail;
    }

    /** 
     *            @hibernate.property
     *             column="LINK"
     *             length="255"
     *             not-null="true"
     *         
     */
    @Column(name = "LINK", nullable = false)
    public String getLink()
    {
        return this.link;
    }

    public void setLink(String link)
    {
        this.link = link;
    }

    /** 
     *            @hibernate.property
     *             column="ACCEPTED"
     *             length="1"
     *         
     */
    @Column(name = "ACCEPTED", nullable = true)
    @Type(type = "yes_no")
    public Boolean getAccepted()
    {
        return this.accepted;
    }

    public void setAccepted(Boolean accepted)
    {
        this.accepted = accepted;
    }

    /** 
     *            @hibernate.property
     *             column="REJECT_REASON"
     *             length="255"
     *         
     */
    @Column(name = "REJECT_REASON", nullable = false)
    public String getRejectReason()
    {
        return this.rejectReason;
    }

    public void setRejectReason(String rejectReason)
    {
        this.rejectReason = rejectReason;
    }

    /** 
     *            @hibernate.many-to-one
     *             not-null="false"
     *            @hibernate.column name="ACCEPTED_BY"
     *         
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ACCEPTED_BY", nullable = true)
    public User getAcceptedBy()
    {
        return this.acceptedBy;
    }

    public void setAcceptedBy(User acceptedBy)
    {
        this.acceptedBy = acceptedBy;
    }

    /** 
     *            @hibernate.property
     *             column="ACCEPTED_DATE"
     *             length="23"
     *         
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ACCEPTED_DATE", nullable = true)
    public Date getAcceptedDate()
    {
        return this.acceptedDate;
    }

    public void setAcceptedDate(Date acceptedDate)
    {
        this.acceptedDate = acceptedDate;
    }

    /** 
     *            @hibernate.many-to-one
     *             not-null="true"
     *            @hibernate.column name="FCA_NO"         
     *         
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FCA_NO", nullable = false)
    public Fca getFca()
    {
        if (fca == null)
        {
            fca = new Fca();
        }
        return this.fca;
    }

    public void setFca(Fca fca)
    {
        this.fca = fca;
    }

    /** 
     *            @hibernate.many-to-one
     *             not-null="true"
     *            @hibernate.column name="JOB_TYPE_ID"         
     *         
     */
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

}