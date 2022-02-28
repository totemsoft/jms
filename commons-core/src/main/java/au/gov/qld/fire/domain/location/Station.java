package au.gov.qld.fire.domain.location;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang.ArrayUtils;
import org.hibernate.annotations.Type;

import au.gov.qld.fire.domain.Auditable;
import au.gov.qld.fire.domain.entity.Contact;

/**
 * @hibernate.class table="STATION" dynamic-update="true"
 *
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Entity
@Table(name = "STATION")
public class Station extends Auditable<String>
{

    /** serialVersionUID */
    private static final long serialVersionUID = 6706477454971502610L;

    /**
     * STATION_IGNORE
     */
    public static final String[] IGNORE = (String[]) ArrayUtils.addAll(Auditable.IGNORE,
        new String[] {"stationId", "address", "contact", "area"});

    private String name;

    private Contact contact;

    private Address address;

    private Area area;

    /**
     * 
     */
    public Station()
    {
        super();
    }

    /**
     * @param id
     */
    public Station(String id)
    {
        super(id);
    }

    /** 
     *            @hibernate.id
     *             generator-class="assigned"
     *             type="java.lang.String"
     *             column="STATION_CODE"
     *         
     */
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "STATION_CODE")
    public String getStationId()
    {
        return super.getId();
    }

    public void setStationId(String stationId)
    {
        super.setId(stationId);
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
     *            @hibernate.many-to-one
     *             not-null="true"
     *            @hibernate.column name="AREA_ID"         
     *         
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AREA_ID")
    public Area getArea()
    {
        return this.area;
    }

    public void setArea(Area area)
    {
        this.area = area;
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