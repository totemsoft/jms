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
 * @hibernate.class table="AREA" dynamic-update="true"
 *
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Entity
@Table(name = "AREA")
public class Area extends Auditable<String>
{

    /** serialVersionUID */
    private static final long serialVersionUID = -4837990105240442510L;

    /**
     * AREA_IGNORE
     */
    public static final String[] AREA_IGNORE = (String[]) ArrayUtils.addAll(Auditable.IGNORE,
        new String[] {"areaId", "address", "contact", "region", "stations"});

    private String name;

    private Region region;

    private Contact contact;

    private Address address;

    private List<Station> stations;

    /**
     * 
     */
    public Area()
    {
        super();
    }

    /**
     * @param id
     */
    public Area(String id)
    {
        super(id);
    }

    /** 
     *            @hibernate.id
     *             generator-class="assigned"
     *             type="java.lang.String"
     *             column="AREA_ID"
     *         
     */
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "AREA_ID")
    public String getAreaId()
    {
        return super.getId();
    }

    public void setAreaId(String areaId)
    {
        super.setId(areaId);
    }

    /** 
     *            @hibernate.property
     *             column="NAME"
     *             length="255"
     *             not-null="true"
     *         
     */
    @Column(name = "NAME")
    public String getName()
    {
        return this.name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    /** 
     *            @hibernate.many-to-one
     *             not-null="true"
     *            @hibernate.column name="REGION_ID"         
     *         
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REGION_ID")
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
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
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
     *             column="AREA_ID"
     *            @hibernate.collection-one-to-many
     *             class="au.gov.qld.fire.jms.domain.location.Station"
     *         
     */
    @OneToMany(fetch = FetchType.LAZY)
    public List<Station> getStations()
    {
        if (stations == null)
        {
            stations = new ArrayList<Station>();
        }
        return stations;
    }

    public void setStations(List<Station> stations)
    {
        this.stations = stations;
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