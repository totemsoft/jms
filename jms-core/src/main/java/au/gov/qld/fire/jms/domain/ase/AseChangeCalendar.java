package au.gov.qld.fire.jms.domain.ase;

import java.util.Date;

import au.gov.qld.fire.domain.BaseCalendar;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class AseChangeCalendar extends BaseCalendar<AseChangeCalendarItem>
{

    /**
     * @param year
     * @param month
     */
    public AseChangeCalendar(int year, int month)
    {
        super(year, month);
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.domain.BaseCalendar#createCalendarItem(int)
     */
    @Override
    protected AseChangeCalendarItem createCalendarItem(int day)
    {
        return new AseChangeCalendarItem(getYear(), getMonth(), day);
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.domain.BaseCalendar#getDate(java.lang.Object)
     */
    @Override
    protected Date getDate(AseChangeCalendarItem item)
    {
        return item == null ? null : item.getDate();
    }

}