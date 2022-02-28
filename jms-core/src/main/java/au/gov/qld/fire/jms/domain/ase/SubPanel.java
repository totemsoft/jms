package au.gov.qld.fire.jms.domain.ase;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import au.gov.qld.fire.domain.Auditable;
import au.gov.qld.fire.domain.location.Address;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Entity
@IdClass(SubPanelPK.class)
@Table(name = "SUB_PANEL")
public class SubPanel extends Auditable<SubPanelPK>
{

    /** serialVersionUID */
    private static final long serialVersionUID = 489361584959452939L;

    /** identifier field */
    private Long aseFileId;

    /** identifier field */
    private Long subPanelOrderId;

    private String buildingName;

    private AseFile aseFile;

    private Address address;

    @Transient
    public SubPanelPK getId()
    {
        if (super.getId() == null)
        {
            super.setId(new SubPanelPK(getAseFileId(), getSubPanelOrderId()));
        }
        return super.getId();
    }

    @Id
	@Column(name = "ASE_FILE_ID", nullable = false)
    public Long getAseFileId()
    {
        return this.aseFileId;
    }

    public void setAseFileId(Long aseFileId)
    {
        this.aseFileId = aseFileId;
    }

    @Id
	@Column(name = "SUB_PANEL_ORDER_ID", nullable = false)
    public Long getSubPanelOrderId()
    {
        return this.subPanelOrderId;
    }

    public void setSubPanelOrderId(Long subPanelOrderId)
    {
        this.subPanelOrderId = subPanelOrderId;
    }

    /** 
     *            @hibernate.property
     *             column="BUILDING_NAME"
     *             length="250"
     *             not-null="true"
     *         
     */
	@Column(name = "BUILDING_NAME", nullable = false)
    public String getBuildingName()
    {
        return this.buildingName;
    }

    public void setBuildingName(String buildingName)
    {
        this.buildingName = buildingName;
    }

    /** 
     *            @hibernate.many-to-one
     *             update="false"
     *             insert="false"
     *         
     *            @hibernate.column
     *             name="ASE_FILE_ID"
     *         
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ASE_FILE_ID", nullable = false, insertable = false, updatable = false)
    public AseFile getAseFile()
    {
        return this.aseFile;
    }

    public void setAseFile(AseFile aseFile)
    {
        this.aseFile = aseFile;
    }

    /** 
     *            @hibernate.many-to-one
     *             not-null="true"
     *            @hibernate.column name="ADDRESS_ID"         
     *         
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ADDRESS_ID", nullable = false)
    public Address getAddress()
    {
        return this.address;
    }

    public void setAddress(Address address)
    {
        this.address = address;
    }

}