package au.gov.qld.fire.jms.domain.file;

import java.util.List;

import au.gov.qld.fire.domain.SearchCriteria;


/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class FileSearchCriteria extends SearchCriteria
{

    /** serialVersionUID */
    private static final long serialVersionUID = -528525487066564307L;

    private Long fileTypeId;

    private String fcaNo;

    private String fileNo;

    /** building */
    private Long buildingId;
    private String buildingName;

    private String buildingAddress;

    private String ownerName;

    private String ownerContact;

    private String supplierName;

    private boolean disconnectedFile;
    private List<Long> fileStatusIds;

    /**
     * Set Max results to N rows.
     */
    public FileSearchCriteria()
    {
        setMaxResults(DEFAULT_MAX);
    }

    /**
	 * @return the fileTypeId
	 */
	public Long getFileTypeId()
	{
		return fileTypeId;
	}

	public void setFileTypeId(Long fileTypeId)
	{
		this.fileTypeId = toLong(fileTypeId);
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
     * @return the buildingAddress
     */
    public String getBuildingAddress()
    {
        return buildingAddress;
    }

    public void setBuildingAddress(String buildingAddress)
    {
        this.buildingAddress = buildingAddress;
    }

    /**
     * @return the ownerName
     */
    public String getOwnerName()
    {
        return ownerName;
    }

    public void setOwnerName(String ownerName)
    {
        this.ownerName = ownerName;
    }

    /**
     * @return the ownerContact
     */
    public String getOwnerContact()
    {
        return ownerContact;
    }

    public void setOwnerContact(String ownerContact)
    {
        this.ownerContact = ownerContact;
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

    /**
     * @return the disconnectedFile
     */
    public boolean isDisconnectedFile()
    {
        return disconnectedFile;
    }

    public void setDisconnectedFile(boolean disconnectedFile)
    {
        this.disconnectedFile = disconnectedFile;
    }

	/**
	 * @return the fileStatusIds
	 */
	public List<Long> getFileStatusIds()
	{
		return fileStatusIds;
	}

	public void setFileStatusIds(List<Long> fileStatusIds)
	{
		this.fileStatusIds = fileStatusIds;
	}

}