package au.gov.qld.fire.jms.domain.action;

import au.gov.qld.fire.domain.SearchCriteria;


/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class TodoSearchCriteria extends SearchCriteria
{

    /** serialVersionUID */
    private static final long serialVersionUID = -3369352417608351058L;

    private String fcaNo;
    private String fileNo;

    private Boolean completed; /* null, true or false */

    private Long actionTypeId;

    private Long actionCodeId;
    private String actionCode;

    private Long regionId;
    private String region;

    private Long workGroupId;
    private String workGroup;

    private Long responsibleUserId;

    /**
     * Set Max results to N rows.
     */
    public TodoSearchCriteria()
    {
        setMaxResults(DEFAULT_MAX);
        setCompleted(false);
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
	 * @return the completed
	 */
	public Boolean getCompleted()
	{
		return completed;
	}

	public void setCompleted(Boolean completed)
	{
		this.completed = completed;
	}

	/**
	 * @return the actionTypeId
	 */
	public Long getActionTypeId()
	{
		return actionTypeId;
	}

	public void setActionTypeId(Long actionTypeId)
	{
		this.actionTypeId = toLong(actionTypeId);
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
	 * @return the regionId
	 */
	public Long getRegionId()
	{
		return regionId;
	}

	public void setRegionId(Long regionId) 
	{
		this.regionId = regionId;
	}

	/**
	 * @return the region
	 */
	public String getRegion()
	{
		return region;
	}

	public void setRegion(String region)
	{
		this.region = region;
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

	/**
	 * @return the responsibleUserId
	 */
	public Long getResponsibleUserId()
	{
		return responsibleUserId;
	}

	public void setResponsibleUserId(Long responsibleUserId)
	{
		this.responsibleUserId = toLong(responsibleUserId);
	}

}