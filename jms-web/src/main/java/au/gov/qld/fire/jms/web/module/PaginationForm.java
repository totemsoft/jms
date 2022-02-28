package au.gov.qld.fire.jms.web.module;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import au.gov.qld.fire.domain.Pagination;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public abstract class PaginationForm<T> extends AbstractValidatorForm<T>
{

    private static final String CURRENT_PAGE_NO = "currentPageNo";

    private static final String PAGE_SIZE = "pageSize";

    /**
     * rowsPerPageOptions  0 = All
     */
    private static final int[] rowsPerPageOptions =
    {
    	10, 50, 100, 200, 500, 1000
    };

    /** pagination */
    private Pagination pagination = new Pagination();

    /**
     * Get all page size(s).
     */
    public int[] getRowsPerPageOptions()
    {
        //return new int[] {1, 10, 20, 50, 100};
        return rowsPerPageOptions;
    }

    /**
     * Get maxItemCount.
     */
    public int getMaxItemCount()
    {
        return pagination.getMaxItemCount();
    }

    public void setMaxItemCount(int maxItemCount)
    {
        pagination.setMaxItemCount(maxItemCount);
    }

    /**
     * @return the pagination
     */
    public Pagination getPagination()
    {
        return pagination;
    }

    /**
     * Get current page number.
     */
    public int getCurrentPageNo()
    {
        return pagination.getCurrentPageNo();
    }

    public void setCurrentPageNo(int currentPageNo)
    {
        pagination.setCurrentPageNo(currentPageNo);
    }

    /**
     * Get page size.
     */
    public int getPageSize()
    {
        return pagination.getPageSize();
    }

    public void setPageSize(int pageSize)
    {
        pagination.setPageSize(pageSize);
    }

    /**
     * Update the page infomation.
     * If the request has valid parameter of "currentPageNo" or "pageSize",
     * these value will be set to the form.
     * @param request The http request.
     */
    public void updatePageInfo(HttpServletRequest request)
    {
        String sCurrentPageNo = request.getParameter(CURRENT_PAGE_NO);
        String sPageSize = request.getParameter(PAGE_SIZE);
        if (StringUtils.isNotBlank(sCurrentPageNo))
        {
            setCurrentPageNo(Integer.parseInt(sCurrentPageNo));
        }
        if (StringUtils.isNotBlank(sPageSize))
        {
            //Set the currentPageNo to 1, if the pageSize is changed.
            if (getPageSize() != Integer.parseInt(sPageSize))
            {
                setPageSize(Integer.parseInt(sPageSize));
                setCurrentPageNo(1);
            }
        }
    }

    /**
     * Get First Result (calculated).
     * @return
     */
    public int getFirstResult()
    {
        return pagination.getFirstResult();
    }

    /**
     * Get Last Result (calculated).
     * @return
     */
    public int getLastResult()
    {
        return pagination.getLastResult();
    }

    /**
     * Get max page number (calculated).
     */
    public int getMaxPageNo()
    {
        return pagination.getMaxPageNo();
    }

}