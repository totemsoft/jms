package au.gov.qld.fire.domain;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import au.gov.qld.fire.util.DateUtils;

/*
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public abstract class BaseCalendar<T>
{

    /** logger. */
    protected final Logger LOG = Logger.getLogger(getClass());

    /** Number of day in one week. */
    private static final int WEEK_DAYS = 7;

    /** year */
    private final int year;

    /** month */
    private final int month;

    /** monthly calendar items */
    private final List<List<T>> items = new ArrayList<List<T>>();

    /**
     * @param year
     * @param month
     */
    public BaseCalendar(final int year, final int month)
    {
        this.year = year;
        this.month = month;
        // init items
        try
        {
            int day = 1;
            Date date = DateUtils.D_M_YYYY.parse(day + "/" + (month + 1) + "/" + year);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK); //Sunday=1
            //
            boolean nextMonth = false;
            while (!nextMonth)
            {
                List<T> weekItems = new ArrayList<T>(WEEK_DAYS);
                for (int i = 0; i < WEEK_DAYS; i++)
                {
                    T item = null;
                    if (day >= dayOfWeek)
                    {
                        item = createCalendarItem(day);
                        calendar.setTime(getDate(item));
                        if (calendar.get(Calendar.MONTH) == month)
                        {
                            day++;
                        }
                        else
                        {
                            nextMonth = true;
                            item = null;
                        }
                    }
                    else
                    {
                        dayOfWeek--;
                    }
                    weekItems.add(item);
                }
                if (weekItems.get(0) != null || weekItems.get(WEEK_DAYS - 1) != null)
                {
                    items.add(weekItems);
                }
            }
        }
        catch (ParseException e)
        {
            LOG.error(e.getMessage(), e);
        }
    }

    /**
     * @return the year
     */
    public int getYear()
    {
        return year;
    }

    /**
     * @return the month
     */
    public int getMonth()
    {
        return month;
    }

    /**
     * @return the items
     */
    public List<List<T>> getItems()
    {
        return items;
    }

    /**
     * 
     * @param day
     * @return
     */
    protected abstract T createCalendarItem(int day);

    /**
     * 
     * @param item
     * @return
     */
    protected abstract Date getDate(T item);

}