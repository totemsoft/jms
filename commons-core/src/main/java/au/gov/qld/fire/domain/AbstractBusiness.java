package au.gov.qld.fire.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import org.apache.commons.lang.ArrayUtils;
import org.hibernate.annotations.Type;

import au.gov.qld.fire.domain.entity.Contact;
import au.gov.qld.fire.domain.location.Address;
import au.gov.qld.fire.domain.location.Region;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@MappedSuperclass
public abstract class AbstractBusiness extends Auditable<Long>
{

	/** serialVersionUID */
	private static final long serialVersionUID = 3521647734594091137L;

    public static final String[] IGNORE = (String[]) ArrayUtils.addAll(Auditable.IGNORE,
        new String[] {"address", "contact", "postAddress", "region"});

    private String name;

    private String legalName;

    private String abn;

    private Region region;

    private Contact contact;

    private Address address;

    private Address postAddress;

    protected AbstractBusiness()
    {
        super();
    }

    protected AbstractBusiness(Long id)
    {
        super(id);
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

    @Column(name = "NAME", nullable = false)
    public String getName()
    {
        return this.name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Column(name = "LEGAL_NAME")
    public String getLegalName()
    {
        return this.legalName;
    }

    public void setLegalName(String legalName)
    {
        this.legalName = legalName;
    }

    @Column(name = "ABN")
    public String getAbn()
    {
        return this.abn;
    }

    public void setAbn(String abn)
    {
        this.abn = abn;
    }

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

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "CONTACT_ID")
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

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "ADDRESS_ID")
    public Address getAddress()
    {
        if (address == null)
        {
            address = new Address();
        }
        return this.address;
    }

    public void setAddress(Address address)
    {
        this.address = address;
    }

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "POST_ADDRESS_ID")
    public Address getPostAddress()
    {
        if (postAddress == null)
        {
            postAddress = new Address();
        }
        return this.postAddress;
    }

    public void setPostAddress(Address postAddress)
    {
        this.postAddress = postAddress;
    }

}