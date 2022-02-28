package au.gov.qld.fire.jms.domain.ase;

import au.gov.qld.fire.domain.SearchCriteria;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class AseChangeSearchCriteria extends SearchCriteria
{

    /** serialVersionUID */
    private static final long serialVersionUID = 2513184845124541742L;

    /** fcaNo */
    private String fcaNo;

    /** fileNo */
    private String fileNo;

    /** building */
    private Long buildingId;
    private String buildingName;

    /** supplierName */
    private String supplierName;

    /**
     * Set Max results to N rows.
     */
    public AseChangeSearchCriteria()
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
     * @return the supplierName
     */
    public String getSupplierName()
    {
        return supplierName;
    }

    public void setSupplierName(String supplierName)
    {
        this.supplierName = supplierName;
    }

}