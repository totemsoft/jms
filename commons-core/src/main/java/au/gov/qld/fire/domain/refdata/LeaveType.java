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

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Entity
@Table(name = "LEAVE_TYPE")
@Cache(region = "au.gov.qld.fire.domain.refdata.LeaveType", usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class LeaveType extends Auditable<Long>
{

	/** serialVersionUID */
	private static final long serialVersionUID = -8283133531114445543L;

    public static final String[] IGNORE = (String[]) ArrayUtils.addAll(Auditable.IGNORE,
        new String[] {"leaveTypeId"});

	/** persistent field */
    private String name;

    /**
     * 
     */
    public LeaveType()
    {
        super();
    }

    /**
     * @param id
     */
    public LeaveType(Long id)
    {
        super(id);
    }

    /**
     *            @hibernate.id
     *             generator-class="identity"
     *             type="java.lang.Long"
     *             column="LEAVE_TYPE_ID"
     *
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LEAVE_TYPE_ID")
    public Long getLeaveTypeId()
    {
        return super.getId();
    }

    public void setLeaveTypeId(Long leaveTypeId)
    {
        super.setId(leaveTypeId);
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