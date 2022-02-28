package au.gov.qld.fire.jms.domain.job;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Entity
@Table(name = "V_ACTIVE_JOB_REQUEST")
public class ActiveJobRequest
{

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "JOB_REQUEST_ID", nullable = false)
    private Long jobRequestId;

    @Column(name = "JOB_TYPE", nullable = true)
    private String jobType;

    @Column(name = "WORK_GROUP", nullable = true)
    private String workGroup;

    @Column(name = "REQUESTED_BY", nullable = false)
    private String requestedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "REQUESTED_DATE", nullable = false)
    private Date requestedDate;

    @Column(name = "REQUESTED_EMAIL", nullable = true)
    private String requestedEmail;

    @Column(name = "LINK", nullable = true)
    private String link;

    @Column(name = "JOB_TYPE_ID", nullable = true)
    private String jobTypeId;

    @Column(name = "WORK_GROUP_ID", nullable = true)
    private String workGroupId;

    public Long getJobRequestId()
    {
        return jobRequestId;
    }

    public String getJobType()
    {
        return jobType;
    }

    public String getWorkGroup()
    {
        return workGroup;
    }

    public String getRequestedBy()
    {
        return requestedBy;
    }

    public Date getRequestedDate()
    {
        return requestedDate;
    }

    public String getRequestedEmail()
    {
        return requestedEmail;
    }

    public String getLink()
    {
        return link;
    }

}