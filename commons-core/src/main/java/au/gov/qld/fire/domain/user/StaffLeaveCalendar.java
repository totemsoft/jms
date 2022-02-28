package au.gov.qld.fire.domain.user;

import java.util.Date;

import au.gov.qld.fire.domain.BaseCalendar;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class StaffLeaveCalendar extends BaseCalendar<StaffLeaveCalendarItem>
{

    /**
     * @param year
     * @param month
     */
    public StaffLeaveCalendar(int year, int month)
    {
        super(year, month);
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.domain.BaseCalendar#createCalendarItem(int)
     */
    @Override
    protected StaffLeaveCalendarItem createCalendarItem(int day)
    {
        return new StaffLeaveCalendarItem(getYear(), getMonth(), day);
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.domain.BaseCalendar#getDate(java.lang.Object)
     */
    @Override
    protected Date getDate(StaffLeaveCalendarItem item)
    {
        return item == null ? null : item.getDate();
    }

}