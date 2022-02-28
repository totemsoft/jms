package au.gov.qld.fire.jms.domain.uaa;

import au.gov.qld.fire.domain.SearchCriteria;


/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class UaaHistorySearchCriteria extends SearchCriteria
{

	/** serialVersionUID */
	private static final long serialVersionUID = -4717509934757339424L;

    private String fcaId;

    private Long regionId;

    private String area;

    private String station;

    /**
     * Set Max results to N rows.
     */
    public UaaHistorySearchCriteria()
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
		if (regionId != null && regionId == 0L)
		{
			regionId = null;
		}
		return regionId;
	}

	public void setRegionId(Long regionId)
	{
		this.regionId = regionId;
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

}