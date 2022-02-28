package au.gov.qld.fire.domain.envers;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

import au.gov.qld.fire.domain.BaseModel;

/**
 * 
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Entity
@Table(name = "REVINFO"/*, schema = "dbo", catalog = "JMS"*/)
@org.hibernate.envers.RevisionEntity(RevisionListener.class)
@EntityListeners({RevisionListener.class})
public class RevisionEntity extends BaseModel<Long>
{

	/** serialVersionUID */
	private static final long serialVersionUID = -7898794796019330081L;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "REV_ID", nullable = false)
    @RevisionNumber
    private Long id;

    @Column(name = "REV_TSTMP", nullable = false)
    @RevisionTimestamp
    private long timestamp;

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
	 * @return the timestamp
	 */
	public long getTimestamp()
	{
		return timestamp;
	}

	public void setTimestamp(long timestamp)
	{
		this.timestamp = timestamp;
	}

    @Transient
    public Date getDate()
    {
        return new Date(timestamp);
    }

}