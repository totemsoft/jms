package au.gov.qld.fire.jms.domain.file;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@MappedSuperclass
public abstract class AbstractActiveFile
{

    @Column(name = "FCA_NO", nullable = true)
    private String fcaId;

    @Column(name = "BUILDING_ID", nullable = true)
    private Long buildingId;

    @Column(name = "BUILDING_NAME", nullable = true)
    private String buildingName;

    @Column(name = "BUILDING_ADDRESS", nullable = true)
    private String buildingAddress;

    @Column(name = "SUPPLIER_NAME", nullable = true)
    private String supplierName;

    @Column(name = "OWNER_NAME", nullable = true)
    private String ownerName;

    @Column(name = "FILE_TYPE_ID", nullable = true)
    private Long fileTypeId;

    @Column(name = "FILE_STATUS_ID", nullable = true)
    private Long fileStatusId;

    @Column(name = "BUILDING_SUBURB", nullable = true)
    private String buildingSuburb;

    @Column(name = "OWNER_CONTACT", nullable = true)
    private String ownerContact;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE", nullable = false)
    private Date createdDate;

    public abstract Long getFileId();

    /**
     * @return the fcaId
     */
    public String getFcaId()
    {
        return fcaId;
    }

	/**
     * @return the buildingName
     */
    public String getBuildingName()
    {
        return buildingName;
    }

    /**
     * @return the buildingAddress
     */
    public String getBuildingAddress()
    {
        return buildingAddress;
    }

    /**
     * @return the supplierName
     */
    public String getSupplierName()
    {
        return supplierName;
    }

    /**
     * @return the ownerName
     */
    public String getOwnerName()
    {
        return ownerName;
    }

    /**
	 * @return the fileTypeId
	 */
	public Long getFileTypeId()
	{
		return fileTypeId;
	}

	/**
	 * @return the fileStatusId
	 */
	public Long getFileStatusId()
	{
		return fileStatusId;
	}

	/**
	 * @return the buildingSuburb
	 */
	public String getBuildingSuburb()
	{
		return buildingSuburb;
	}

	/**
	 * @return the ownerContact
	 */
	public String getOwnerContact()
	{
		return ownerContact;
	}

	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate()
	{
		return createdDate;
	}

}