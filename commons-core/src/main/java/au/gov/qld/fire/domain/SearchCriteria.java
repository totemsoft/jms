package au.gov.qld.fire.domain;

import java.io.Serializable;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public abstract class SearchCriteria implements Serializable
{

    /** serialVersionUID */
    private static final long serialVersionUID = 5292735257520475901L;

    /** MAX = 3000 */
    public static final int MAX = 3000;

    /** DEFAULT_MAX = 300 */
    public static final int DEFAULT_MAX = 300;

    /** maxResults */
    private int maxResults;

    /**
     * @return the maxResults
     */
    public int getMaxResults()
    {
        return maxResults;
    }

    public void setMaxResults(int maxResults)
    {
        this.maxResults = maxResults;
    }

    /**
     * Helper method
     * @param value
     * @return
     */
    protected final Long toLong(Long value)
    {
    	return value != null && value == 0L ? null : value;
    }

    protected final Integer toInteger(Integer value)
    {
    	return value != null && value == 0 ? null : value;
    }

}