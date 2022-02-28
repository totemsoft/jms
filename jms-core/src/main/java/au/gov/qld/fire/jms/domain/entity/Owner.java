package au.gov.qld.fire.jms.domain.entity;

import java.util.Date;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang.ArrayUtils;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.annotations.Type;

import au.gov.qld.fire.domain.Auditable;
import au.gov.qld.fire.domain.entity.Contact;
import au.gov.qld.fire.domain.location.Address;
import au.gov.qld.fire.domain.security.User;
import au.gov.qld.fire.jms.domain.file.File;
import au.gov.qld.fire.jms.domain.refdata.OwnerType;
import au.gov.qld.fire.jms.domain.refdata.OwnerTypeEnum;

/*
 * @hibernate.class table="OWNER" dynamic-update="true"
 */
@Entity
@Table(name = "OWNER")
public class Owner extends Auditable<Long>
{

    /** serialVersionUID */
    private static final long serialVersionUID = -5610709351171241637L;

    public static final String[] IGNORE = (String[]) ArrayUtils.addAll(Auditable.IGNORE,
        new String[] {"ownerId", "ownerType", "address", "contact", "archBy", "file", "nextOwner"});

    private OwnerType ownerType;

    private boolean ownerOccupied;

    private String legalName;

    private String abn;

    private String reference;

    private String management;

    private boolean defaultContact;

    private Date archDate;

    private User archBy;

    private File file;

    private Owner nextOwner;

    private Contact contact;

    private Address address;

    /**
     * 
     */
    public Owner()
    {
        super();
    }

    /**
     * @param id
     */
    public Owner(Long id)
    {
        super(id);
    }

    /**
     * @param id
     */
    public Owner(OwnerTypeEnum ownerType)
    {
        getOwnerType().setId(new Long(ownerType.ordinal()));
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.domain.BaseModel#getId()
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OWNER_ID")
    public Long getOwnerId()
    {
        return super.getId();
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.domain.BaseModel#setId(java.lang.Object)
     */
    public void setOwnerId(Long ownerId)
    {
        super.setId(ownerId);
    }

    /** 
     *            @hibernate.many-to-one
     *             not-null="true"
     *            @hibernate.column name="OWNER_TYPE_ID"         
     *         
     */
    @ManyToOne//(fetch = FetchType.LAZY)
    @JoinColumn(name = "OWNER_TYPE_ID", nullable = false)
    @JsonProperty
	public OwnerType getOwnerType()
	{
    	if (ownerType == null)
    	{
    		ownerType = new OwnerType();
    	}
		return ownerType;
	}

	public void setOwnerType(OwnerType ownerType)
	{
		this.ownerType = ownerType;
	}

	@Transient
	public boolean isBodyCorporate()
	{
		return ownerType != null && ownerType.getId() != null && ownerType.getId() == OwnerTypeEnum.BODY_CORPORATE.ordinal();
	}

    @Column(name = "OWNER_OCCUPIED", nullable = false)
    @Type(type = "yes_no")
	public boolean isOwnerOccupied()
	{
		return ownerOccupied;
	}

	public void setOwnerOccupied(boolean ownerOccupied)
	{
		this.ownerOccupied = ownerOccupied;
	}

	/** 
     *            @hibernate.property
     *             column="LEGAL_NAME"
     *             length="255"
     *             not-null="true"
     *         
     */
    @Column(name = "LEGAL_NAME", nullable = false)
    @JsonProperty
    public String getLegalName()
    {
        return this.legalName;
    }

    public void setLegalName(String legalName)
    {
        this.legalName = legalName;
    }

	/** 
     *            @hibernate.property
     *             column="ABN"
     *             length="20"
     *             not-null="false"
     *         
     */
    @Column(name = "ABN", nullable = true)
    @JsonProperty
	public String getAbn()
	{
		return abn;
	}

	public void setAbn(String abn)
	{
		this.abn = abn;
	}

    @Column(name = "REFERENCE", nullable = true, length = 20)
    @JsonProperty
	public String getReference()
	{
		return reference;
	}

	public void setReference(String reference)
	{
		this.reference = reference;
	}

    @Column(name = "MANAGEMENT", nullable = true, length = 50)
    @JsonProperty
	public String getManagement()
	{
		return management;
	}

	public void setManagement(String management)
	{
		this.management = management;
	}

	/** 
     *            @hibernate.property
     *             column="DEFAULT_CONTACT"
     *             length="1"
     *             not-null="true"
     *         
     */
    @Column(name = "DEFAULT_CONTACT", nullable = false)
    @Type(type = "yes_no")
	public boolean isDefaultContact()
	{
		return defaultContact;
	}

	public void setDefaultContact(boolean defaultContact)
	{
		this.defaultContact = defaultContact;
		getContact().setDefaultContact(defaultContact); // transient property
	}

	/** 
     *            @hibernate.property
     *             column="ARCH_DATE"
     *             length="23"
     *         
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "ARCH_DATE", nullable = true)
    public Date getArchDate()
    {
        return this.archDate;
    }

    public void setArchDate(Date ownerArchDate)
    {
        this.archDate = ownerArchDate;
    }

    /** 
     *            @hibernate.many-to-one
     *            @hibernate.column name="ARCH_BY"
     *         
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ARCH_BY", nullable = true)
    public User getArchBy()
    {
        return this.archBy;
    }

    public void setArchBy(User ownerArchBy)
    {
        this.archBy = ownerArchBy;
    }

    /** 
     *            @hibernate.many-to-one
     *             not-null="true"
     *            @hibernate.column name="FILE_ID"         
     *         
     */
    @ManyToOne(fetch = FetchType.EAGER) // used in UI - need to be eager loaded
    @JoinColumn(name = "FILE_ID", nullable = false)
    public File getFile()
    {
        return this.file;
    }

    public void setFile(File file)
    {
        this.file = file;
    }

    /** 
     *            @hibernate.many-to-one
     *             not-null="false"
     *            @hibernate.column name="NEXT_OWNER_ID"         
     *         
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "NEXT_OWNER_ID", nullable = true)
    public Owner getNextOwner()
    {
        return this.nextOwner;
    }

    public void setNextOwner(Owner nextOwner)
    {
        this.nextOwner = nextOwner;
    }

    /** 
     *            @hibernate.many-to-one
     *             not-null="true"
     *            @hibernate.column name="CONTACT_ID"         
     *         
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "CONTACT_ID", nullable = false)
    @JsonProperty
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
    @JoinColumn(name = "ADDRESS_ID", nullable = false)
    @JsonProperty
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

}