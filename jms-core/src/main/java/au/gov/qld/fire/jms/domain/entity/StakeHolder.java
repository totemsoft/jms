package au.gov.qld.fire.jms.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import au.gov.qld.fire.domain.Auditable;
import au.gov.qld.fire.domain.entity.Contact;
import au.gov.qld.fire.domain.location.Address;
import au.gov.qld.fire.domain.location.Region;
import au.gov.qld.fire.jms.domain.refdata.StakeHolderType;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Entity
@Table(name = "STAKE_HOLDER")
public class StakeHolder extends Auditable<Long>
{

    /** serialVersionUID */
    private static final long serialVersionUID = 7143534949278448947L;

    /** REGION property name */
    public static final String REGION = "region";

    /** CONTACT property name */
    public static final String CONTACT = "contact";

    /** ADDRESS property name */
    public static final String ADDRESS = "address";

    /** STAKE_HOLDER_TYPE property name */
    public static final String STAKE_HOLDER_TYPE = "stakeHolderType";

    private Region region;

    private Contact contact;

    private Address address;

    private StakeHolderType stakeHolderType;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "STAKE_HOLDER_ID")
    public Long getStakeHolderId()
    {
        return super.getId();
    }

    public void setStakeHolderId(Long stakeHolderId)
    {
        super.setId(stakeHolderId);
    }

    /** 
     *            @hibernate.many-to-one
     *             not-null="true"
     *            @hibernate.column name="REGION_ID"         
     *         
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REGION_ID", nullable = false)
    public Region getRegion()
    {
        return this.region;
    }

    public void setRegion(Region region)
    {
        this.region = region;
    }

    /** 
     *            @hibernate.many-to-one
     *             not-null="true"
     *            @hibernate.column name="CONTACT_ID"         
     *         
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CONTACT_ID", nullable = false)
    public Contact getContact()
    {
    	if (contact == null)
    	{
    		contact = new Contact();
    	}
        return this.contact;
    }

    public void setContact(Contact contact)
    {
        this.contact = contact;
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

    /** 
     *            @hibernate.many-to-one
     *             not-null="true"
     *            @hibernate.column name="STAKE_HOLDER_TYPE_ID"         
     *         
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STAKE_HOLDER_TYPE_ID", nullable = false)
    public StakeHolderType getStakeHolderType()
    {
        return this.stakeHolderType;
    }

    public void setStakeHolderType(StakeHolderType stakeHolderType)
    {
        this.stakeHolderType = stakeHolderType;
    }


    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.domain.BaseModel#isActive()
     */
    @Override
    @Column(name = "ACTIVE", nullable = false)
    @Type(type = "yes_no")
    public boolean isActive()
    {
        return super.isActive();
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.domain.BaseModel#setActive(boolean)
     */
    @Override
    public void setActive(boolean active)
    {
        super.setActive(active);
    }

}