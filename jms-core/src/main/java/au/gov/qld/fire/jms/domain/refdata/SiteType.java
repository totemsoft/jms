package au.gov.qld.fire.jms.domain.refdata;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.ArrayUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import au.gov.qld.fire.domain.Auditable;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Entity
@Table(name = "SITE_TYPE")
@Cache(region = "au.gov.qld.fire.jms.domain.refdata.SiteType", usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class SiteType extends Auditable<Long>
{

    /** serialVersionUID */
	private static final long serialVersionUID = -985866368948090928L;

    public static final String[] IGNORE = (String[]) ArrayUtils.addAll(Auditable.IGNORE,
        new String[] {"siteTypeId"});

	/** persistent field */
    private String name;

    /**
     * 
     */
    public SiteType()
    {
        super();
    }

    /**
     * @param id
     */
    public SiteType(Long id)
    {
        super(id);
    }

    /**
     *            @hibernate.id
     *             generator-class="identity"
     *             type="java.lang.Long"
     *             column="SITE_TYPE_ID"
     *
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SITE_TYPE_ID")
    public Long getSiteTypeId()
    {
        return super.getId();
    }

    public void setSiteTypeId(Long siteTypeId)
    {
        super.setId(siteTypeId);
    }

    /**
     *            @hibernate.property
     *             column="NAME"
     *             length="50"
     *             not-null="true"
     *
     */
    @Column(name = "NAME", nullable = false)
    public String getName()
    {
        return this.name;
    }

    public void setName(String name)
    {
        this.name = name;
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