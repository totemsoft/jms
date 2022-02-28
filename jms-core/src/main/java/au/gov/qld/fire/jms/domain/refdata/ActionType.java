package au.gov.qld.fire.jms.domain.refdata;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import au.gov.qld.fire.domain.Auditable;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Entity
@Table(name = "ACTION_TYPE")
@Cache(region = "au.gov.qld.fire.jms.domain.refdata.ActionType", usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class ActionType extends Auditable<Long>
{

    /** serialVersionUID */
    private static final long serialVersionUID = -6164796314763902491L;

    /** persistent field */
    private String name;

    /**
     * 
     */
    public ActionType()
    {
        super();
    }

    /**
     * @param id
     */
    public ActionType(Long id)
    {
        super(id);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ACTION_TYPE_ID")
    public Long getActionTypeId()
    {
        return super.getId();
    }

    public void setActionTypeId(Long actionTypeId)
    {
        super.setId(actionTypeId);
    }

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
     * Calculated read only property
     * @return
     */
    @Transient
    public boolean isCall()
    {
        return ActionTypeEnum.isCall(getId());
    }

    @Transient
    public boolean isEmail()
    {
        return ActionTypeEnum.isEmail(getId());
    }

    @Transient
    public boolean isSms()
    {
        return ActionTypeEnum.isSms(getId());
    }

    @Transient
    public boolean isLetter()
    {
        return ActionTypeEnum.isLetter(getId());
    }

}