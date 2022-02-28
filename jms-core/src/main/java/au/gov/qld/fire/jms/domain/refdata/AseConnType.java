package au.gov.qld.fire.jms.domain.refdata;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import au.gov.qld.fire.domain.Auditable;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Entity
@Table(name = "ASE_CONN_TYPE")
@Cache(region = "au.gov.qld.fire.jms.domain.refdata.AseConnType", usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class AseConnType extends Auditable<Long>
{

    /** serialVersionUID */
    private static final long serialVersionUID = 5805301288858915275L;

    /** persistent field */
    private String name;

    /**
     * 
     */
    public AseConnType()
    {
        super();
    }

    /**
     * @param id
     */
    public AseConnType(Long id)
    {
        super(id);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ASE_CONN_TYPE_ID")
    public Long getAseConnTypeId()
    {
        return super.getId();
    }

    public void setAseConnTypeId(Long id)
    {
        super.setId(id);
    }

    /** 
     *            @hibernate.property
     *             column="NAME"
     *             length="100"
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