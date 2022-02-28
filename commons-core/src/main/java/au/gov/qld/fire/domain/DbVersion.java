package au.gov.qld.fire.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Entity
@Table(name = "DBVERSION")
@org.hibernate.annotations.GenericGenerator(name = "assigned-strategy", strategy = "assigned")
public class DbVersion extends BaseModel<String>
{

    /** serialVersionUID */
	private static final long serialVersionUID = -2100653390736502707L;

	/** identity persistent field */
    private String id;

    private String prevDbVersion;

    private Date createdDate;

    @Id
    @GeneratedValue(generator = "assigned-strategy")
    @Column(name = "DBVERSION")
	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

    @Column(name = "PREV_DBVERSION", nullable = true)
	public String getPrevDbVersion()
	{
		return prevDbVersion;
	}

	public void setPrevDbVersion(String prevDbVersion)
	{
		this.prevDbVersion = prevDbVersion;
	}

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE", nullable = false, insertable = true, updatable = false)
	public Date getCreatedDate()
	{
		return createdDate;
	}

	public void setCreatedDate(Date createdDate)
	{
		this.createdDate = createdDate;
	}

}