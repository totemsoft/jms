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
@Table(name = "BUILDING_CONTACT_TYPE")
@Cache(region = "au.gov.qld.fire.jms.domain.refdata.BuildingContactType", usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class BuildingContactType extends Auditable<Long>
{

    /** serialVersionUID */
    private static final long serialVersionUID = 4025410660016591954L;

    public static final BuildingContactType OWNER = new BuildingContactType(BuildingContactTypeEnum.OWNER.getId());
    @Deprecated public static final BuildingContactType DAYTIME = new BuildingContactType(BuildingContactTypeEnum.DAYTIME.getId());
    @Deprecated public static final BuildingContactType AFTER_HOURS = new BuildingContactType(BuildingContactTypeEnum.AFTER_HOURS.getId());
    public static final BuildingContactType SECURITY = new BuildingContactType(BuildingContactTypeEnum.SECURITY.getId());
    public static final BuildingContactType FIRE = new BuildingContactType(BuildingContactTypeEnum.FIRE.getId());

    /** persistent field */
    private String name;

    /**
     * 
     */
    public BuildingContactType()
    {
        super();
    }

    /**
     * @param id
     */
    public BuildingContactType(Long id)
    {
        super(id);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BUILDING_CONTACT_TYPE_ID")
    public Long getBuildingContactTypeId()
    {
        return super.getId();
    }

    public void setBuildingContactTypeId(Long id)
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

}