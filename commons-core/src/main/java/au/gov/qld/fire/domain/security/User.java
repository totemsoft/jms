package au.gov.qld.fire.domain.security;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.security.auth.Subject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Type;

import au.gov.qld.fire.domain.Auditable;
import au.gov.qld.fire.domain.entity.Contact;
import au.gov.qld.fire.domain.refdata.UserType;
import au.gov.qld.fire.domain.refdata.UserTypeEnum;
import au.gov.qld.fire.domain.refdata.WorkGroup;
import au.gov.qld.fire.domain.supplier.Supplier;
import au.gov.qld.fire.security.UserCredential;
import au.gov.qld.fire.service.ServiceException;
import au.gov.qld.fire.util.Encoder;

/**
 * @hibernate.class table="USERS" dynamic-update="true"
 *
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Entity
@Table(name = "USERS")
public class User extends Auditable<Long>
{

    /** serialVersionUID */
    private static final long serialVersionUID = -7666284818599547947L;

    /**
     * USER_IGNORE
     */
    public static final String[] IGNORE = (String[]) ArrayUtils.addAll(Auditable.IGNORE,
        new String[] {"userId", "password", "passwordExpire", "contact", "securityGroup", "userType", "supplier"});

    private String login;

    private String password;

    private Date passwordExpire;

    private Contact contact;

    private SecurityGroup securityGroup;

    private UserType userType;

    private Supplier supplier;

    private List<WorkGroup> workGroups;

    /**
     * 
     */
    public User()
    {
        super();
    }

    /**
     * @param id
     */
    public User(Long id)
    {
        super(id);
    }

    /**
     * @param subject
     */
    public User(Subject subject)
    {
        Set<UserCredential> creds = subject.getPublicCredentials(UserCredential.class);
        if (creds.isEmpty())
        {
        	throw new IllegalArgumentException("JAAS configuration required: <login-module code=\"au.gov.qld.fire.security.auth.JdbcLoginModule\" flag=\"required\">");
        }
        //LOG.info("PublicCredentials: " + creds);
        UserCredential cred = (UserCredential) CollectionUtils.get(creds, 0);
        //LOG.info("PublicCredential: " + cred);
        setUserId(cred.getUserId());
        setLogin(cred.getName());
    }

    /** 
     *            @hibernate.id
     *             generator-class="sequence"
     *             type="java.lang.Long"
     *             column="USER_ID"
     *         
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    public Long getUserId()
    {
        return super.getId();
    }

    public void setUserId(Long userId)
    {
        super.setId(userId);
    }

    /** 
     *            @hibernate.property
     *             column="LOGIN"
     *             length="100"
     *             not-null="true"
     *         
     */
    @Column(name = "LOGIN")
    public String getLogin()
    {
        return this.login;
    }

    public void setLogin(String login)
    {
        this.login = login;
    }

    @Column(name = "PASSWORD", nullable = false, length = 50)
    public String getPassword()
    {
        return this.password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public void encodePassword(String password)
    {
    	final String defaultPassword = "Passw0rd";
    	if (isSystem()) {
    		this.password = Encoder.encode(StringUtils.isNotBlank(password) ? password : defaultPassword);
    	} else {
    		this.password = Encoder.digest((StringUtils.isNotBlank(password) ? password : defaultPassword).getBytes());
    	}
    }

    public String decodePassword()
    {
    	if (isSystem()) {
            return Encoder.decode(password);
    	}
        throw new ServiceException("Could not decode password for user: " + login);
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "PASSWORD_EXPIRE", nullable = true)
    public Date getPasswordExpire()
    {
        return this.passwordExpire;
    }

    public void setPasswordExpire(Date passwordExpire)
    {
        this.passwordExpire = passwordExpire;
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
     *            @hibernate.column name="SECURITY_GROUP_ID"         
     *         
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SECURITY_GROUP_ID")
    public SecurityGroup getSecurityGroup()
    {
        if (securityGroup == null)
        {
            securityGroup = new SecurityGroup();
        }
        return this.securityGroup;
    }

    public void setSecurityGroup(SecurityGroup securityGroup)
    {
        this.securityGroup = securityGroup;
    }

    /** 
     *            @hibernate.many-to-one
     *             not-null="true"
     *            @hibernate.column name="USER_TYPE_ID"         
     *         
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_TYPE_ID")
    public UserType getUserType()
    {
        if (userType == null)
        {
            userType = new UserType();
        }
        return this.userType;
    }

    public void setUserType(UserType userType)
    {
        this.userType = userType;
    }

    @Transient
	public boolean isSystem()
    {
		return userType != null && UserTypeEnum.SYSTEM.getId().equals(userType.getUserTypeId());
	}

    /** 
     *            @hibernate.many-to-one
     *             not-null="false"
     *            @hibernate.column name="SUPPLIER_ID"         
     *         
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SUPPLIER_ID")
    public Supplier getSupplier()
    {
        return supplier;
    }

    public void setSupplier(Supplier supplier)
    {
        this.supplier = supplier;
    }


    /** 
     *            @hibernate.bag
     *             lazy="true"
     *             inverse="true"
     *             cascade="none"
     *            @hibernate.collection-key
     *             column="USER_ID"
     *            @hibernate.collection-many-to-many
     *             class="au.gov.qld.fire.domain.refdata.WorkGroup"
     *
     */
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinTable(name = "USERS_WORKGROUP",
        joinColumns = @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID"),
        inverseJoinColumns = @JoinColumn(name = "WORK_GROUP_ID", referencedColumnName = "WORK_GROUP_ID"))
	public List<WorkGroup> getWorkGroups()
	{
    	if (workGroups == null)
    	{
    		workGroups = new ArrayList<WorkGroup>();
    	}
		return workGroups;
	}

	/**
	 * @param workGroups the workGroups to set
	 */
	public void setWorkGroups(List<WorkGroup> workGroups)
	{
		this.workGroups = workGroups;
	}

}
