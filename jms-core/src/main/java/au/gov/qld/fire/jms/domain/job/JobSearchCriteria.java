package au.gov.qld.fire.jms.domain.job;

import au.gov.qld.fire.domain.SearchCriteria;


/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class JobSearchCriteria extends SearchCriteria
{

    /** serialVersionUID */
    private static final long serialVersionUID = 6574758059943424961L;

    /** building */
    private Long buildingId;
    private String buildingName;

    /** jobNo */
    private String jobNo;

    /** fcaNo */
    private String fcaNo;

    /** fileNo */
    private String fileNo;

    /** jobType */
    private String jobType;

    /** jobTypeId */
    private Long jobTypeId;

    /** actionCodeId */
    private Long actionCodeId;

    /** actionCodeId */
    private String actionCode;

    /** workGroup */
    private String workGroup;

    /** workGroupId */
    private Long workGroupId;

    /**
     * Set Max results to N rows.
     */
    public JobSearchCriteria()
    {
        setMaxResults(DEFAULT_MAX);
    }

    /**
	 * @return the buildingId
	 */
	public Long getBuildingId()
	{
		return buildingId;
	}

	public void setBuildingId(Long buildingId)
	{
		this.buildingId = toLong(buildingId);
	}

	/**
     * @return the buildingName
     */
    public String getBuildingName()
    {
        return buildingName;
    }

    public void setBuildingName(String buildingName)
    {
        this.buildingName = buildingName;
    }

    /**
     * @return the jobNo
     */
    public String getJobNo()
    {
        return jobNo;
    }

    public void setJobNo(String jobNo)
    {
        this.jobNo = jobNo;
    }

    /**
     * @return the fcaNo
     */
    public String getFcaNo()
    {
        return fcaNo;
    }

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

    public void setJobTypeId(Long jobTypeId)
    {
        this.jobTypeId = toLong(jobTypeId);
    }

    /**
     * @return the actionCodeId
     */
    public Long getActionCodeId()
    {
        return actionCodeId;
    }

    public void setActionCodeId(Long actionCodeId)
    {
        this.actionCodeId = toLong(actionCodeId);
    }

    /**
     * @return the actionCode
     */
    public String getActionCode()
    {
        return actionCode;
    }

    public void setActionCode(String actionCode)
    {
        this.actionCode = actionCode;
    }

    /**
     * @return the workGroup
     */
    public String getWorkGroup()
    {
        return workGroup;
    }

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

    public void setWorkGroupId(Long workGroupId)
    {
        this.workGroupId = toLong(workGroupId);
    }

}