package au.gov.qld.fire.jms.domain.job;

import au.gov.qld.fire.domain.SearchCriteria;


/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class JobRequestSearchCriteria extends SearchCriteria
{

    /** serialVersionUID */
    private static final long serialVersionUID = 8755239471560526059L;

    /** jobRequestNo */
    private String jobRequestNo;

    /** fcaNo */
    private String fcaNo;

    /** fileNo */
    private String fileNo;

    /** jobType */
    private String jobType;

    /** jobTypeId */
    private Long jobTypeId;

    /** workGroup */
    private String workGroup;

    /** workGroupId */
    private Long workGroupId;

    /**
     * Set Max results to N rows.
     */
    public JobRequestSearchCriteria()
    {
        setMaxResults(DEFAULT_MAX);
    }

    /**
     * @return the jobRequestNo
     */
    public String getJobRequestNo()
    {
        return jobRequestNo;
    }

    /**
     * @param jobRequestNo the jobRequestNo to set
     */
    public void setJobRequestNo(String jobRequestNo)
    {
        this.jobRequestNo = jobRequestNo;
    }

    /**
     * @return the fcaNo
     */
    public String getFcaNo()
    {
        return fcaNo;
    }

    /**
     * @param fcaNo the fcaNo to set
     */
    public void setFcaNo(String fcaNo)
    {
        this.fcaNo = fcaNo;
    }

    /**
     * @return the fileNo
     */
    public String getFileNo()
    {
        return fileNo;
    }

    /**
     * @param fileNo the fileNo to set
     */
    public void setFileNo(String fileNo)
    {
        this.fileNo = fileNo;
    }

    /**
     * @return the jobType
     */
    public String getJobType()
    {
        return jobType;
    }

    /**
     * @param jobType the jobType to set
     */
    public void setJobType(String jobType)
    {
        this.jobType = jobType;
    }

    /**
     * @return the jobTypeId
     */
    public Long getJobTypeId()
    {
        return jobTypeId;
    }

    /**
     * @param jobTypeId the jobTypeId to set
     */
    public void setJobTypeId(Long jobTypeId)
    {
        this.jobTypeId = toLong(jobTypeId);
    }

    /**
     * @return the workGroup
     */
    public String getWorkGroup()
    {
        return workGroup;
    }

    /**
     * @param workGroup the workGroup to set
     */
    public void setWorkGroup(String workGroup)
    {
        this.workGroup = workGroup;
    }

    /**
     * @return the workGroupId
     */
    public Long getWorkGroupId()
    {
        return workGroupId;
    }

    /**
     * @param workGroupId the workGroupId to set
     */
    public void setWorkGroupId(Long workGroupId)
    {
        this.workGroupId = toLong(workGroupId);
    }

}