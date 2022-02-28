package au.gov.qld.fire.jms.domain.job;

import java.util.Date;

import au.gov.qld.fire.domain.BaseCalendar;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class JobCalendar extends BaseCalendar<JobCalendarItem>
{

    /**
     * @param year
     * @param month
     */
    public JobCalendar(int year, int month)
    {
        super(year, month);
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.domain.BaseCalendar#createCalendarItem(int)
     */
    @Override
    protected JobCalendarItem createCalendarItem(int day)
    {
        return new JobCalendarItem(getYear(), getMonth(), day);
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.domain.BaseCalendar#getDate(java.lang.Object)
     */
    @Override
    protected Date getDate(JobCalendarItem item)
    {
        return item == null ? null : item.getDate();
    }

}