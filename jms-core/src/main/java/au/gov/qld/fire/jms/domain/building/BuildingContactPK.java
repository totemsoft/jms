package au.gov.qld.fire.jms.domain.building;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/*  @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net) */
public class BuildingContactPK implements Serializable
{

    /** serialVersionUID */
    private static final long serialVersionUID = 7246370437193207090L;

    /** identifier field */
    private Long fileId;

    /** identifier field */
    private Long contactId;

    /** identifier field */
    private Long buildingContactTypeId;

    /** full constructor */
    public BuildingContactPK(Long fileId, Long contactId, Long buildingContactTypeId)
    {
        this.fileId = fileId;
        this.contactId = contactId;
        this.buildingContactTypeId = buildingContactTypeId;
    }

    /** default constructor */
    public BuildingContactPK()
    {
    }

    /** 
     *                @hibernate.property
     *                 column="FILE_ID"
     *                 length="11"
     *             
     */
    public Long getFileId()
    {
        return this.fileId;
    }

    public void setFileId(Long fileId)
    {
        this.fileId = fileId;
    }

    /** 
     *                @hibernate.property
     *                 column="CONTACT_ID"
     *                 length="11"
     *             
     */
    public Long getContactId()
    {
        return this.contactId;
    }

    public void setContactId(Long contactId)
    {
        this.contactId = contactId;
    }

    /** 
     *                @hibernate.property
     *                 column="BUILDING_CONTACT_TYPE_ID"
     *                 length="11"
     *             
     */
    public Long getBuildingContactTypeId()
    {
        return this.buildingContactTypeId;
    }

    public void setBuildingContactTypeId(Long buildingContactTypeId)
    {
        this.buildingContactTypeId = buildingContactTypeId;
    }

    public String toString()
    {
        return new ToStringBuilder(this).append("fileId", getFileId()).append("contactId",
            getContactId()).append("buildingContactTypeId", getBuildingContactTypeId()).toString();
    }

    public boolean equals(Object other)
    {
        if (!(other instanceof BuildingContactPK))
            return false;
        BuildingContactPK castOther = (BuildingContactPK) other;
        return new EqualsBuilder().append(this.getFileId(), castOther.getFileId()).append(
            this.getContactId(), castOther.getContactId()).append(this.getBuildingContactTypeId(),
            castOther.getBuildingContactTypeId()).isEquals();
    }

    public int hashCode()
    {
        return new HashCodeBuilder().append(getFileId()).append(getContactId()).append(
            getBuildingContactTypeId()).toHashCode();
    }

}
