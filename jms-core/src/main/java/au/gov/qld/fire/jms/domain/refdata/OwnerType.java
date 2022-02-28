package au.gov.qld.fire.jms.domain.refdata;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import au.gov.qld.fire.domain.Auditable;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Entity
@Table(name = "OWNER_TYPE")
@org.hibernate.annotations.GenericGenerator(name = "assigned-strategy", strategy = "assigned")
@Cache(region = "au.gov.qld.fire.jms.domain.refdata.OwnerType", usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class OwnerType extends Auditable<Long>
{

    /** serialVersionUID */
	private static final long serialVersionUID = -5625049411750179597L;

    //public static final String[] IGNORE = (String[]) ArrayUtils.addAll(Auditable.IGNORE,
    //    new String[] {});

    private String name;

    /**
     * 
     */
    public OwnerType()
    {
        super();
    }

    /**
     * @param id
     */
    public OwnerType(Long id)
    {
        super(id);
    }

//	@Override
//	public Long getId() {
//		return super.getId();
//	}
//
//	@Override
//	public void setId(Long id) {
//		super.setId(id);
//	}

	/**
     *            @hibernate.id
     *             generator-class="assigned"
     *             type="java.lang.Long"
     *             column="OWNER_TYPE_ID"
     *
     */
    @Id
    @GeneratedValue(generator = "assigned-strategy")
    @Column(name = "OWNER_TYPE_ID")
    public Long getOwnerTypeId()
    {
        return super.getId();
    }

    public void setOwnerTypeId(Long ownerTypeId)
    {
        super.setId(ownerTypeId);
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

}