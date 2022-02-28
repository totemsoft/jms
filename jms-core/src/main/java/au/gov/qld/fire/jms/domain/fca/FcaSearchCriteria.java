package au.gov.qld.fire.jms.domain.fca;

import au.gov.qld.fire.domain.SearchCriteria;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class FcaSearchCriteria extends SearchCriteria
{

    /** serialVersionUID */
    private static final long serialVersionUID = 4860724183873210303L;

    /** region */
    private String region;

    /** area */
    private String area;

    /** station */
    private String station;

    /** fcaNo */
    private String fcaNo;

    /** unassignedFca */
    private boolean unassignedFca;

    /** subPanel */
    private boolean subPanel;

    public FcaSearchCriteria()
    {
        setMaxResults(DEFAULT_MAX);
    }

    /**
     * @return the region
     */
    public String getRegion()
    {
        return region;
    }

    /**
     * @param region the region to set
     */
    public void setRegion(String region)
    {
        this.region = region;
    }

    /**
     * @return the area
     */
    public String getArea()
    {
        return area;
    }

    /**
     * @param area the area to set
     */
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

    /**
     * @param station the station to set
     */
    public void setStation(String station)
    {
        this.station = station;
    }

    /**
     * @return the fcaNo
     */
    public String getFcaNo()
    {
        return fcaNo;
    }

    /**
     * @param fcaNo the fcaNo to set
     */
    public void setFcaNo(String fcaNo)
    {
        this.fcaNo = fcaNo;
    }

    /**
     * @return the unassignedFca
     */
    public boolean isUnassignedFca()
    {
        return unassignedFca;
    }

    /**
     * @param unassignedFca the unassignedFca to set
     */
    public void setUnassignedFca(boolean unassignedFca)
    {
        this.unassignedFca = unassignedFca;
    }

    /**
     * @return the subPanel
     */
    public boolean isSubPanel()
    {
        return subPanel;
    }

    /**
     * @param subPanel the subPanel to set
     */
    public void setSubPanel(boolean subPanel)
    {
        this.subPanel = subPanel;
    }

}