package au.gov.qld.fire.jms.domain.action;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang.ArrayUtils;
import org.hibernate.annotations.Type;

import au.gov.qld.fire.domain.Auditable;
import au.gov.qld.fire.jms.domain.refdata.ActionCode;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Entity
@Table(name = "ACTION_WORKFLOW")
public class ActionWorkflow extends Auditable<Long>
{

    /** serialVersionUID */
    private static final long serialVersionUID = -8681694076910014855L;

    /**
     * ACTION_WORKFLOW_IGNORE
     */
    public static final String[] IGNORE = (String[]) ArrayUtils.addAll(
        Auditable.IGNORE, new String[]
        {
            "actionCode", "nextActionCode", "actionOutcome"
        });

    /** persistent field */
    private Long nextDueDays = 0L;

    /** persistent field */
    private ActionCode actionCode;

    /** persistent field */
    private ActionCode nextActionCode;

    /** persistent field */
    private ActionOutcome actionOutcome;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ACTION_WORKFLOW_ID")
    public Long getActionWorkflowId()
    {
        return super.getId();
    }

    public void setActionWorkflowId(Long id)
    {
        super.setId(id);
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

    @Column(name = "NEXT_DUE_DAYS", nullable = false)
    public Long getNextDueDays()
    {
        return this.nextDueDays;
    }

    public void setNextDueDays(Long nextDueDays)
    {
        this.nextDueDays = nextDueDays;
    }

    /** 
     *            @hibernate.many-to-one
     *             not-null="true"
     *            @hibernate.column name="ACTION_CODE_ID"         
     *         
     */
    @ManyToOne//(fetch = FetchType.LAZY)
    @JoinColumn(name = "ACTION_CODE_ID", nullable = false)
    public ActionCode getActionCode()
    {
        if (actionCode == null)
        {
            actionCode = new ActionCode();
        }
        return this.actionCode;
    }

    public void setActionCode(ActionCode actionCode)
    {
        this.actionCode = actionCode;
    }

    /** 
     *            @hibernate.many-to-one
     *             not-null="true"
     *            @hibernate.column name="NEXT_ACTION_CODE_ID"         
     *         
     */
    @ManyToOne//(fetch = FetchType.LAZY)
    @JoinColumn(name = "NEXT_ACTION_CODE_ID", nullable = false)
    public ActionCode getNextActionCode()
    {
        if (nextActionCode == null)
        {
            nextActionCode = new ActionCode();
        }
        return this.nextActionCode;
    }

    public void setNextActionCode(ActionCode nextActionCode)
    {
        this.nextActionCode = nextActionCode;
    }

    /** 
     *            @hibernate.many-to-one
     *             not-null="true"
     *            @hibernate.column name="ACTION_OUTCOME_ID"         
     *         
     */
    @ManyToOne//(fetch = FetchType.LAZY)
    @JoinColumn(name = "ACTION_OUTCOME_ID", nullable = false)
    public ActionOutcome getActionOutcome()
    {
        if (actionOutcome == null)
        {
            actionOutcome = new ActionOutcome();
        }
        return this.actionOutcome;
    }

    public void setActionOutcome(ActionOutcome actionOutcome)
    {
        this.actionOutcome = actionOutcome;
    }

}