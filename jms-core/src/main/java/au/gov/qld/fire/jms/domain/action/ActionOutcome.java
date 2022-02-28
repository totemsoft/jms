package au.gov.qld.fire.jms.domain.action;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import au.gov.qld.fire.domain.Auditable;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Entity
@Table(name = "ACTION_OUTCOME")
public class ActionOutcome extends Auditable<Long>
{

    /** serialVersionUID */
    private static final long serialVersionUID = 8348188455240525729L;

    /** persistent field */
    private String name;

    /** persistent field */
    private boolean fixed = false;

    /**
     * 
     */
    public ActionOutcome()
    {
        super();
    }

    /**
     * @param id
     */
    public ActionOutcome(Long id)
    {
        super(id);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ACTION_OUTCOME_ID")
    public Long getActionOutcomeId()
    {
        return super.getId();
    }

    /**
     * @param id
     */
    public void setActionOutcomeId(Long id)
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

    /** 
     *            @hibernate.property
     *             column="FIXED"
     *             length="1"
     *             not-null="true"
     *         
     */
    @Column(name = "FIXED", nullable = false)
    @Type(type = "yes_no")
    public boolean isFixed()
    {
        return this.fixed;
    }

    public void setFixed(boolean fixed)
    {
        this.fixed = fixed;
    }

}