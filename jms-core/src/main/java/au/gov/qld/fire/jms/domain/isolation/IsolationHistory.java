package au.gov.qld.fire.jms.domain.isolation;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang.ArrayUtils;

import au.gov.qld.fire.domain.Auditable;
import au.gov.qld.fire.jms.domain.Fca;
import au.gov.qld.fire.util.DateUtils;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Entity
@Table(name = "ISOLATION_HISTORY")
public class IsolationHistory extends Auditable<Long>
{

    /** serialVersionUID */
	private static final long serialVersionUID = 621810638608403286L;

	/**
     * IGNORE
     */
    public static final String[] IGNORE = (String[]) ArrayUtils.addAll(Auditable.IGNORE,
        new String[] {"isolationHistoryId", "fca"});

    private Fca fca;

	private String csNumber;

    private Integer input;

	private Date isolationDate;

    private Date deIsolationDate;

    private String details;

    private String keyDetails;

    public IsolationHistory()
    {
		super();
	}

	public IsolationHistory(Long id)
	{
		super(id);
	}

	public IsolationHistory(Fca fca)
	{
		this.fca = fca;
	}

	/** 
     *            @hibernate.id
     *             generator-class="sequence"
     *             type="java.lang.Long"
     *             column="ISOLATION_HISTORY_ID"
     *         
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ISOLATION_HISTORY_ID")
    public Long getIsolationHistoryId()
    {
        return super.getId();
    }

    public void setIsolationHistoryId(Long isolationHistoryId)
    {
        super.setId(isolationHistoryId);
    }

    /** 
     *            @hibernate.many-to-one
     *             not-null="true"
     *            @hibernate.column name="FCA_NO"         
     *         
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FCA_NO", nullable = false)
	public Fca getFca()
	{
    	if (fca == null)
    	{
    		fca = new Fca();
    	}
		return fca;
	}

	public void setFca(Fca fca)
	{
		this.fca = fca;
	}

	/**
	 * @return the input
	 */
    @Column(name = "INPUT", nullable = false, precision = 3)
	public Integer getInput()
    {
		return input;
	}

	public void setInput(Integer input)
	{
		this.input = input;
	}

    /**
	 * @return the csNumber
	 */
    @Column(name = "CS_NUMBER", nullable = true, length = 32)
	public String getCsNumber()
	{
		return csNumber;
	}

	public void setCsNumber(String csNumber)
	{
		this.csNumber = csNumber;
	}

	/**
     *            @hibernate.property
     *             column="ISOLATION_DATE"
     *             length="23"
     *             not-null="true"
     *
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ISOLATION_DATE", nullable = false)
	public Date getIsolationDate()
	{
		return isolationDate;
	}

	public void setIsolationDate(Date isolationDate)
	{
		this.isolationDate = isolationDate;
	}

    /**
     *            @hibernate.property
     *             column="DE_ISOLATION_DATE"
     *             length="23"
     *             not-null="true"
     *
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DE_ISOLATION_DATE", nullable = false)
	public Date getDeIsolationDate()
	{
		return deIsolationDate;
	}

	public void setDeIsolationDate(Date deIsolationDate)
	{
		this.deIsolationDate = deIsolationDate;
	}

	@Transient
	public long getIsolationTime()
	{
		return deIsolationDate.getTime() - isolationDate.getTime();
	}

	@Transient
	public String getIsolationTimeText()
	{
		return DateUtils.format(getIsolationTime());
	}

	/**
	 * @return the details
	 */
    @Column(name = "DETAILS", nullable = true, length = 512)
	public String getDetails()
    {
		return details;
	}

	public void setDetails(String details)
	{
		this.details = details;
	}

	/**
	 * @return the keyDetails
	 */
    @Column(name = "KEY_DETAILS", nullable = true, length = 512)
	public String getKeyDetails()
    {
		return keyDetails;
	}

	public void setKeyDetails(String keyDetails)
	{
		this.keyDetails = keyDetails;
	}

}