package au.gov.qld.fire.domain;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import au.gov.qld.fire.util.DateUtils;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public abstract class BaseCalendarItem<T>
{

    /** logger. */
    private static final Logger LOG = Logger.getLogger(BaseCalendarItem.class);

    /** MAX_TEXT_SIZE. */
    //protected static final int MAX_TEXT_SIZE = 40;

    /** year */
    private final int year;

    /** month */
    private final int month;

    /** day */
    private int day;

    /** items */
    private List<T> items;

    /**
     * @param day
     */
    public BaseCalendarItem(final int year, final int month, int day)
    {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    /**
     * @return the day
     */
    public int getDay()
    {
        return day;
    }

    /**
     * @return the jobActions
     */
    public List<T> getItems()
    {
        if (items == null)
        {
            items = new ArrayList<T>();
        }
        return items;
    }

    /**
     * @param jobActions the jobActions to set
     */
    public void setItems(List<T> jobActions)
    {
        this.items = jobActions;
    }

    /**
     * @return the date
     */
    public Date getDate()
    {
        try
        {
            return DateUtils.D_M_YYYY.parse(day + "/" + (month + 1) + "/" + year);
        }
        catch (ParseException e)
        {
            LOG.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * TODO: move to web layer.
     * This day item(s) formatted as HTML.
     * @return
     */
    public abstract String getInnerHtml();

}