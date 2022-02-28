package au.gov.qld.fire.domain.refdata;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.ArrayUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import au.gov.qld.fire.domain.Auditable;
import au.gov.qld.fire.domain.BaseModel;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Entity
@Table(name = "USER_TYPE")
@Cache(region = "au.gov.qld.fire.domain.refdata.UserType", usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class UserType extends BaseModel<Long>
{

    /** serialVersionUID */
    private static final long serialVersionUID = 4138436262887959466L;

    /**
     * USER_TYPE_IGNORE
     */
    public static final String[] IGNORE = (String[]) ArrayUtils.addAll(Auditable.IGNORE,
        new String[] {"userTypeId"});

    private String name;

    /**
     * 
     */
    public UserType()
    {
        super();
    }

    /**
     * @param id
     */
    public UserType(Long id)
    {
        super(id);
    }

    /* (non-Javadoc)
	 * @see au.gov.qld.fire.domain.BaseModel#getId()
	 */
	@Override
	@Transient
	public Long getId()
	{
		return id; // hide base implementation (treats 0 as null), userTypeId = 0 is System userType
	}

	/** 
     *            @hibernate.id
     *             generator-class="assigned"
     *             type="java.lang.Long"
     *             column="USER_TYPE_ID"
     *         
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_TYPE_ID")
    public Long getUserTypeId()
    {
        return getId();
    }

    public void setUserTypeId(Long userTypeId)
    {
        setId(userTypeId);
    }

    /** 
     *            @hibernate.property
     *             column="DESCRIPTION"
     *             length="100"
     *             not-null="true"
     *         
     */
    @Column(name = "NAME")
    public String getName()
    {
        return this.name;
    }

    public void setName(String description)
    {
        this.name = description;
    }

}