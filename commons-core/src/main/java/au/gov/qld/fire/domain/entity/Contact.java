package au.gov.qld.fire.domain.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
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
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.annotate.JsonProperty;

import au.gov.qld.fire.domain.Auditable;
import au.gov.qld.fire.domain.refdata.Salutation;

/**
 * @hibernate.class table="CONTACT" dynamic-update="true"
 *
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Entity
@Table(name = "CONTACT")
public class Contact extends Auditable<Long>
{

    /** serialVersionUID */
    private static final long serialVersionUID = 522455796315922901L;

    /** IGNORE */
    public static final String[] IGNORE = (String[]) ArrayUtils.addAll(Auditable.IGNORE,
        new String[] {"contactId", "salutation"});

    private String firstName;

    private String surname;

    private Date dateOfBirth;

    private String phone;

    private String mobile;

    private String email;

    private String emailConfirm;

    private String fax;

    private String skype;

    private Salutation salutation;
    @Transient
    private String salutationTitle;

    @Transient
    private String type;

    @Transient
    private boolean defaultContact;

    /**
     * 
     */
    public Contact()
    {
        super();
    }

    /**
     * @param id
     */
    public Contact(Long id)
    {
        super(id);
    }

    /** 
     *            @hibernate.id
     *             generator-class="sequence"
     *             type="java.lang.Long"
     *             column="CONTACT_ID"
     *         
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CONTACT_ID")
    public Long getContactId()
    {
        return super.getId();
    }

    public void setContactId(Long contactId)
    {
        super.setId(contactId);
    }

    /**
     * Calculated property.
     * @return
     */
    @Transient
    public String getName()
    {
        return new StringBuffer()
            .append(getFirstName() == null ? "" : getFirstName() + " ")
            .append(getSurname() == null ? "" : getSurname())
            .toString();
    }

    /**
     * Calculated property.
     * @return
     */
    @Transient
    public String getFullName()
    {
        return new StringBuffer()
            .append(getSalutation() == null || Salutation.UNKNOWN.equals(getSalutation()) ? "" : getSalutation().getSalutation() + " ")
            .append(getFirstName() == null ? "" : getFirstName() + " ")
            .append(getSurname() == null ? "" : getSurname())
            .toString();
    }

    /** 
     *            @hibernate.property
     *             column="FIRST_NAME"
     *             length="255"
     *             not-null="true"
     *         
     */
    @Column(name = "FIRST_NAME")
    @JsonProperty
    public String getFirstName()
    {
        return this.firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = StringUtils.trimToNull(firstName);
    }

    /** 
     *            @hibernate.property
     *             column="SURNAME"
     *             length="255"
     *             not-null="true"
     *         
     */
    @Column(name = "SURNAME")
    @JsonProperty
    public String getSurname()
    {
        return this.surname;
    }

    public void setSurname(String surname)
    {
        this.surname = StringUtils.trimToNull(surname);
    }

    /**
     *            @hibernate.property
     *             column="DATE_OF_BIRTH"
     *             length="23"
     *             not-null="false"
     *
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_OF_BIRTH")
    public Date getDateOfBirth()
    {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth)
	{
		this.dateOfBirth = dateOfBirth;
	}

	/** 
     *            @hibernate.property
     *             column="PHONE"
     *             length="10"
     *             not-null="true"
     *         
     */
    @Column(name = "PHONE")
    @JsonProperty
    public String getPhone()
    {
        return this.phone;
    }

    public void setPhone(String phone)
    {
        this.phone = StringUtils.trimToNull(phone);
    }

    /** 
     *            @hibernate.property
     *             column="MOBILE"
     *             length="10"
     *             not-null="true"
     *         
     */
    @Column(name = "MOBILE")
    @JsonProperty
    public String getMobile()
    {
        return this.mobile;
    }

    public void setMobile(String mobile)
    {
        this.mobile = StringUtils.trimToNull(mobile);
    }

    /** 
     *            @hibernate.property
     *             column="EMAIL"
     *             length="10"
     *             not-null="true"
     *         
     */
    @Column(name = "EMAIL")
    @JsonProperty
    public String getEmail()
    {
        return this.email;
    }

    public void setEmail(String email)
    {
        this.email = StringUtils.trimToNull(email);
    }

    /**
     * @return the emailConfirm
     */
    @Transient
    public String getEmailConfirm()
    {
        if (emailConfirm == null)
        {
            emailConfirm = email;
        }
        return emailConfirm;
    }

    /**
     * @param emailConfirm the emailConfirm to set
     */
    public void setEmailConfirm(String emailConfirm)
    {
        this.emailConfirm = StringUtils.trimToNull(emailConfirm);
    }

    /** 
     *            @hibernate.property
     *             column="FAX"
     *             length="10"
     *         
     */
    @Column(name = "FAX")
    @JsonProperty
    public String getFax()
    {
        return this.fax;
    }

    public void setFax(String fax)
    {
        this.fax = StringUtils.trimToNull(fax);
    }

    /** 
     *            @hibernate.property
     *             column="SKYPE"
     *             length="10"
     *         
     */
    @Column(name = "SKYPE")
    public String getSkype()
    {
        return this.skype;
    }

    public void setSkype(String skype)
    {
        this.skype = StringUtils.trimToNull(skype);
    }

    /** 
     *            @hibernate.many-to-one
     *             not-null="true"
     *            @hibernate.column name="SALUTATION"         
     *         
     */
    @ManyToOne//(fetch = FetchType.LAZY)
    @JoinColumn(name = "SALUTATION", nullable = false)
    public Salutation getSalutation()
    {
        if (salutation == null) {
            salutation = new Salutation(Salutation.UNKNOWN.getSalutation());   
        }
        return this.salutation;
    }

    public void setSalutation(Salutation salutation)
    {
        this.salutation = salutation;
        this.salutationTitle = getSalutation().getSalutation();
    }

    /**
	 * @return the salutationTitle
	 */
    @Transient
	public String getSalutationTitle()
	{
//    	if (salutationTitle == null) {
//    		salutationTitle = getSalutation().getSalutation();
//    	}
		return salutationTitle;
	}

	public void setSalutationTitle(String salutationTitle)
	{
		this.salutationTitle = salutationTitle;
	}

	/**
     * @return the type
     */
    @Transient
    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = StringUtils.trimToNull(type);
    }

	/**
	 * @return the defaultContact
	 */
    @Transient
	public boolean isDefaultContact()
    {
		return defaultContact;
	}

	public void setDefaultContact(boolean defaultContact)
	{
		this.defaultContact = defaultContact;
	}

	@Transient
	public boolean isEmpty()
	{
		return getContactId() == null;
	}

}