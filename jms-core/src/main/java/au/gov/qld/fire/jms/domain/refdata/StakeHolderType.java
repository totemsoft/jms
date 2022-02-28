package au.gov.qld.fire.jms.domain.refdata;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import au.gov.qld.fire.domain.Auditable;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Entity
@Table(name = "STAKE_HOLDER_TYPE")
@Cache(region = "au.gov.qld.fire.jms.domain.refdata.StakeHolderType", usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class StakeHolderType extends Auditable<Long>
{

    /** serialVersionUID */
    private static final long serialVersionUID = -5658683757466175532L;

    /** persistent field */
    private String name;

    /**
     * 
     */
    public StakeHolderType()
    {
        super();
    }

    /**
     * @param id
     */
    public StakeHolderType(Long id)
    {
        super(id);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "STAKE_HOLDER_TYPE_ID")
    public Long getStakeHolderTypeId()
    {
        return super.getId();
    }

    public void setStakeHolderTypeId(Long id)
    {
        super.setId(id);
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