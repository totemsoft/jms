package au.gov.qld.fire.domain;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Data Transfer Object for pagination.
 * @author Valera Shibaev
 */
public class Pagination implements Serializable
{

	/** serialVersionUID */
	private static final long serialVersionUID = -6902858914931342661L;

	/** The previous page number. */
    private transient int previousPageNo = -1;

    /** The current page number. */
    private int currentPageNo;

    /** The page size. */
    private int pageSize = 0; // no pagination

    /** Maximum available items size/count. */
    private int maxItemCount = -1; // unknown

    /** sorting column name */
    private String sortBy;

    /** sorting column direction */
    private boolean sortAsc = true;

    /**
     * Default ctor.
     */
    public Pagination()
    {
        super();
    }

    /**
     * Fields ctor.
     * @param currentPageNo
     * @param pageSize
     */
    public Pagination(int currentPageNo, int pageSize)
    {
        this.currentPageNo = currentPageNo;
        this.pageSize = pageSize;
    }

    /**
     * @return the previousPageNo
     */
    public int getPreviousPageNo()
    {
        return previousPageNo;
    }

    /**
     * @return the currentPageNo
     */
    public int getCurrentPageNo()
    {
        return currentPageNo;
    }

    /**
     * @param currentPageNo the currentPageNo to set (1 based)
     */
    public void setCurrentPageNo(int currentPageNo)
    {
        this.previousPageNo = this.currentPageNo;
        this.currentPageNo = currentPageNo;
    }

    /**
     * @return the pageSize
     */
    public int getPageSize()
    {
        return pageSize;
    }

    /**
     * @param pageSize the pageSize to set
     */
    public void setPageSize(int pageSize)
    {
        this.pageSize = pageSize;
    }

    /**
     * @return the maxItemCount
     */
    public int getMaxItemCount()
    {
        return maxItemCount;
    }

    /**
     * @param maxItemCount the maxItemCount to set
     */
    public void setMaxItemCount(int maxItemCount)
    {
        this.maxItemCount = maxItemCount;
    }

    /**
     * Get First Result, zero based (calculated).
     * @return
     */
    public int getFirstResult()
    {
        return getFirstResult(currentPageNo);
    }

    /**
     * Get Last Result, zero based (calculated).
     * @return
     */
    public int getLastResult()
    {
        return getLastResult(currentPageNo);
    }

    /**
     * Get Page First Result, zero based (calculated).
     * @param pageNo
     * @return
     */
    public int getFirstResult(int pageNo)
    {
        return pageSize * (pageNo - 1);
    }

    /**
     * Get Page Last Result, zero based (calculated).
     * @param pageNo
     * @return
     */
    public int getLastResult(int pageNo)
    {
        if (pageSize * pageNo > maxItemCount)
        {
            return maxItemCount - 1;
        }
        return pageSize * pageNo - 1;
    }

    /**
     * Get max page number (calculated).
     */
    public int getMaxPageNo()
    {
        int maxPageNo = 1;
        if (pageSize == 0)
        {
            //do nothing
        }
        else if (pageSize * (maxItemCount / pageSize) < maxItemCount)
        {
            maxPageNo = maxItemCount / pageSize + 1;
        }
        else
        {
            maxPageNo = maxItemCount / pageSize;
        }
        return maxPageNo;
    }

    /**
     * @return the sortBy
     */
    public String getSortBy()
    {
        return sortBy;
    }

    /**
     * @param sortBy the sortBy to set
     */
    public void setSortBy(String sortBy)
    {
        this.sortBy = sortBy;
    }

    /**
     * @return the sortAsc
     */
    public boolean isSortAsc()
    {
        return sortAsc;
    }

    /**
     * @param sortAsc the sortAsc to set
     */
    public void setSortAsc(boolean sortAsc)
    {
        this.sortAsc = sortAsc;
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this)
		    .append("currentPageNo", currentPageNo)
		    .append("firstResult", getFirstResult())
		    .append("maxItemCount", maxItemCount)
		    .append("pageSize", pageSize)
		    .toString();
	}

}