package au.gov.qld.fire.jms.domain.action;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Entity
@Table(name = "V_FILE_ACTION_TODO")
public class FileActionTodo extends BaseActionTodo
{

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "FILE_ACTION_ID", nullable = false)
    private Long id;

    @Transient
    private Date lastSent;

    @Transient
    private Date lastReceived;

    public FileActionTodo()
    {
        super();
    }

    public FileActionTodo(Long id)
    {
        this.id = id;
    }

    /**
     * @return the id
     */
    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    /**
	 * @return the lastSent
	 */
	public Date getLastSent()
	{
		return lastSent;
	}

	public void setLastSent(Date lastSent)
	{
		this.lastSent = lastSent;
	}

	/**
	 * @return the lastReceived
	 */
	public Date getLastReceived()
	{
		return lastReceived;
	}

	public void setLastReceived(Date lastReceived)
	{
		this.lastReceived = lastReceived;
	}

	@Transient
    public boolean isJobAction()
    {
        return false;
    }

}