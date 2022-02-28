package au.gov.qld.fire.domain.refdata;

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
import au.gov.qld.fire.domain.BaseModel;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Entity
@Table(name = "WORK_GROUP")
@Cache(region = "au.gov.qld.fire.domain.refdata.WorkGroup", usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class WorkGroup extends BaseModel<Long>
{

    /** serialVersionUID */
    private static final long serialVersionUID = -5066630495271099759L;

    /**
     * WORK_GROUP_IGNORE
     */
    public static final String[] IGNORE = (String[]) ArrayUtils.addAll(Auditable.IGNORE, 
        new String[] {"workGroupId"});

    private String name;

    /**
     * 
     */
    public WorkGroup()
    {
        super();
    }

    /**
     * @param id
     */
    public WorkGroup(Long id)
    {
        super(id);
    }

    /** 
     *            @hibernate.id
     *             generator-class="sequence"
     *             type="java.lang.Long"
     *             column="WORK_GROUP_ID"
     *         
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WORK_GROUP_ID")
    public Long getWorkGroupId()
    {
        return super.getId();
    }

    public void setWorkGroupId(Long workGroupId)
    {
        super.setId(workGroupId);
    }

    /** 
     *            @hibernate.property
     *             column="NAME"
     *             length="50"
     *             not-null="true"
     *         
     */
    @Column(name = "NAME")
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