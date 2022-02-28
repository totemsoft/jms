package au.gov.qld.fire.jms.domain.isolation;

import java.util.Date;

import au.gov.qld.fire.domain.SearchCriteria;


/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class IsolationHistorySearchCriteria extends SearchCriteria
{

	/** serialVersionUID */
	private static final long serialVersionUID = 5001485566694084055L;

    private String fcaId;

    private Long regionId;

    private String area;

    private String station;

    private Integer input;

	private Date isolationDate;

    private Date deIsolationDate;

    /**
     * Set Max results to N rows.
     */
    public IsolationHistorySearchCriteria()
    {
        setMaxResults(DEFAULT_MAX);
    }

    /**
     * @return the fcaId
     */
    public String getFcaId()
    {
        return fcaId;
    }

    public void setFcaId(String fcaId)
    {
        this.fcaId = fcaId;
    }

    /**
     * @return regionId
     */
	public Long getRegionId()
	{
		return regionId;
	}

	public void setRegionId(Long regionId)
	{
		this.regionId = toLong(regionId);
	}

	/**
	 * @return the area
	 */
	public String getArea()
	{
		return area;
	}

	public void setArea(String area)
	{
		this.area = area;
	}

	/**
	 * @return the station
	 */
	public String getStation()
	{
		return station;
	}

	public void setStation(String station)
	{
		this.station = station;
	}

	/**
	 * @return the input
	 */
	public Integer getInput()
	{
		return input;
	}

	public void setInput(Integer input)
	{
		this.input = input;
	}

	/**
	 * @return the isolationDate
	 */
	public Date getIsolationDate()
	{
		return isolationDate;
	}

	public void setIsolationDate(Date isolationDate)
	{
		this.isolationDate = isolationDate;
	}

	/**
	 * @return the deIsolationDate
	 */
	public Date getDeIsolationDate()
	{
		return deIsolationDate;
	}

	public void setDeIsolationDate(Date deIsolationDate)
	{
		this.deIsolationDate = deIsolationDate;
	}

}