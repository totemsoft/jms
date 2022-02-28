package au.gov.qld.fire.domain.location;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang.ArrayUtils;
import org.hibernate.annotations.Type;

import au.gov.qld.fire.domain.Auditable;
import au.gov.qld.fire.domain.entity.Contact;

/**
 * @hibernate.class table="REGION" dynamic-update="true"
 *
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Entity
@Table(name = "REGION")
public class Region extends Auditable<Long>
{

    /** serialVersionUID */
    private static final long serialVersionUID = -6124627758777609674L;

    /**
     * REGION_IGNORE
     */
    public static final String[] IGNORE = (String[]) ArrayUtils.addAll(Auditable.IGNORE,
        new String[] {"regionId", "address", "contact", "areas"});

    private String name;

    private String code;

    private Contact contact;

    private Address address;

    private List<Area> areas;

    /**
     * 
     */
    public Region()
    {
        super();
    }

    /**
     * @param id
     */
    public Region(Long id)
    {
        super(id);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REGION_ID")
    public Long getRegionId()
    {
        return super.getId();
    }

    public void setRegionId(Long regionId)
    {
        super.setId(regionId);
    }

    @Column(name = "NAME")
    public String getName()
    {
        return this.name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Column(name = "CODE", columnDefinition = "char", length = 2)
	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	/** 
     *            @hibernate.many-to-one
     *             not-null="true"
     *            @hibernate.column name="CONTACT_ID"         
     *         
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "CONTACT_ID", nullable = true)
    public Contact getContact()
    {
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
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "ADDRESS_ID")
    public Address getAddress()
    {
        return this.address;
    }

    public void setAddress(Address address)
    {
        this.address = address;
    }

    /** 
     *            @hibernate.bag
     *             lazy="true"
     *             inverse="true"
     *             cascade="none"
     *            @hibernate.collection-key
     *             column="REGION_ID"
     *            @hibernate.collection-one-to-many
     *             class="au.gov.qld.fire.jms.domain.location.Area"
     *         
     */
    @OneToMany(fetch = FetchType.LAZY)
    public List<Area> getAreas()
    {
        if (areas == null)
        {
            areas = new ArrayList<Area>();
        }
        return areas;
    }

    public void setAreas(List<Area> areas)
    {
        this.areas = areas;
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