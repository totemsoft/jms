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
@Table(name = "V_ACTIVE_JOB")
public class ActiveJob
{

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "JOB_ID", nullable = false)
    private Long jobId;

    @Column(name = "FILE_ID", nullable = false)
    private Long fileId;

    @Column(name = "FCA_NO", nullable = true)
    private String fcaId;

    @Column(name = "JOB_TYPE_NAME", nullable = false)
    private String jobType;

    @Temporal(TemporalType.DATE)
    @Column(name = "JOB_START_DATE", nullable = false)
    private Date jobStartDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "NEXT_ACTION_DATE", nullable = true)
    private Date nextActionDate;

    @Column(name = "NEXT_ACTION_CODE", nullable = true)
    private String nextAction;

    @Column(name = "REQUESTED_BY", nullable = false)
    private String requestedBy;

    @Column(name = "WORK_GROUP", nullable = true)
    private String workGroup;

    @Column(name = "BUILDING_ID", nullable = true)
    private Long buildingId;

    @Column(name = "BUILDING_NAME", nullable = true)
    private String buildingName;

    @Column(name = "NEXT_ACTION_CODE_ID", nullable = true)
    private Long actionCodeId;

    @Column(name = "WORK_GROUP_ID", nullable = true)
    private Long workGroupId;

    @Column(name = "JOB_TYPE_ID", nullable = false)
    private Long jobTypeId;

    /**
     * @return the jobId
     */
    public Long getJobId()
    {
        return jobId;
    }

    /**
     * @return the fileNo
     */
    public Long getFileId()
    {
        return fileId;
    }

    /**
     * @return the fcaId
     */
    public String getFcaId()
    {
        return fcaId;
    }

    /**
     * @return the alarmStatus
     */
    public String getJobType()
    {
        return jobType;
    }

    /**
     * @return the jobStartDate
     */
    public Date getJobStartDate()
    {
        return jobStartDate;
    }

    /**
     * @return the nextActionDate
     */
    public Date getNextActionDate()
    {
        return nextActionDate;
    }

    /**
     * @return the nextAction
     */
    public String getNextAction()
    {
        return nextAction;
    }

    /**
     * @return the requestedBy
     */
    public String getRequestedBy()
    {
        return requestedBy;
    }

    /**
     * @return the workGroup
     */
    public String getWorkGroup()
    {
        return workGroup;
    }

}