package au.gov.qld.fire.domain.location;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import au.gov.qld.fire.domain.BaseModel;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Entity
@Table(name = "STATE")
@Cache(region = "au.gov.qld.fire.domain.location.State", usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class State extends BaseModel<String> implements Cloneable
{

    /** serialVersionUID */
    private static final long serialVersionUID = 8588764958464603462L;

    private Boolean defaultState;

    /**
     * 
     */
    public State()
    {
        super();
    }

    /**
     * @param id
     */
    public State(String id)
    {
        super(id);
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.domain.BaseModel#clone()
     */
    @Override
    public Object clone()
    {
        try
        {
            State state = (State) super.clone();
            state.setId(getId());
            return state;
        }
        catch (CloneNotSupportedException e)
        {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /** 
     *            @hibernate.id
     *             generator-class="assigned"
     *             type="java.lang.String"
     *             column="STATE"
     *         
     */
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "STATE")
    public String getState()
    {
        return super.getId();
    }

    public void setState(String state)
    {
        super.setId(state);
    }

    /** 
     *            @hibernate.property
     *             column="DEFAULT_STATE"
     *             length="1"
     *             not-null="true"
     *         
     */
    @Column(name = "DEFAULT_STATE")
    @Type(type = "yes_no")
    public Boolean getDefaultState()
    {
        return this.defaultState;
    }

    public void setDefaultState(Boolean defaultState)
    {
        this.defaultState = defaultState;
    }

}