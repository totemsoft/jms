package au.gov.qld.fire.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public final class DateUtils
{

    private static final Logger LOG = Logger.getLogger(DateUtils.class);

    /** d/M/yyyy date format */
    public static final SimpleDateFormat D_M_YYYY = new SimpleDateFormat("d/M/yyyy");

    /** dd/MM/yyyy date format */
    public static final SimpleDateFormat DD_MM_YYYY = new SimpleDateFormat("dd/MM/yyyy");

    /** yyyy-MM date format */
    public static final SimpleDateFormat YYYY_MM = new SimpleDateFormat("yyyy-MM");

    /** yyyy-M-d date format */
    public static final SimpleDateFormat YYYY_M_D = new SimpleDateFormat("yyyy-M-d");

    /** yyyy-MM-dd date format */
    public static final SimpleDateFormat YYYY_MM_DD = new SimpleDateFormat("yyyy-MM-dd");

    /** HH:mm time format */
    public static final SimpleDateFormat H_mm = new SimpleDateFormat("H:mm");

    /** HH:mm time format */
    public static final SimpleDateFormat HH_mm = new SimpleDateFormat("HH:mm");

    /** HH:mm:ss time format */
    public static final SimpleDateFormat HH_mm_ss = new SimpleDateFormat("HH:mm:ss");

    /** dd MMMMM yyyy date format */
    public static final SimpleDateFormat DD_MMMMM_YYYY = new SimpleDateFormat("dd MMMMM yyyy");

    /** d/M/yyyy H:m date-time format */
    public static final SimpleDateFormat D_M_YYYY_H_mm = new SimpleDateFormat("d/M/yyyy H:mm");

    /** dd/MM/yyyy HH:mm date-time format */
    public static final SimpleDateFormat DD_MM_YYYY_HH_mm = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    /** dd/MM/yyyy HH:mm:ss date-time format */
    public static final SimpleDateFormat DD_MM_YYYY_HH_mm_ss = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    /** 17/08/2012 01:37:54 PM */
    public static final SimpleDateFormat DD_MM_YYYY_hh_mm_ss_aaa = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss aaa");

    public static final SimpleDateFormat YYYYMMDD = new SimpleDateFormat("yyyyMMdd");
    public static final SimpleDateFormat YYYYMMDD_HHMMSS = new SimpleDateFormat("yyyyMMdd'T'HHmmss");

    public static final SimpleDateFormat DEFAULT_DATE = YYYY_MM_DD;

    public static final long SECONDS_PER_MINUTE = 60;

    public static final long MILLIS_PER_MINUTE = SECONDS_PER_MINUTE * 1000L;

    public static final long SECONDS_PER_HOUR = 60 * 60;

    public static final long MILLIS_PER_HOUR = SECONDS_PER_HOUR * 1000L;

    public static final long SECONDS_PER_DAY = 24 * 60 * 60;

    /** Number of milliseconds in one day. */
    public static final long MILLIS_PER_DAY = SECONDS_PER_DAY * 1000L;

    public static final long MILLIS_PER_WEEK = MILLIS_PER_DAY * 7;

    private static final double EXCEL_DATE_29_02_1900 = 60.;

    private static final Date EXCEL_ZERO_DATE;

    private static final Date JAVA_ZERO_DATE;
    static
    {
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        calendar.set(Calendar.YEAR, 1900);
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        EXCEL_ZERO_DATE = calendar.getTime();

        calendar.set(Calendar.YEAR, 1970);
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        JAVA_ZERO_DATE = calendar.getTime();
    }

    public static long JAVA_EXCEL_TIME_DIFF = JAVA_ZERO_DATE.getTime() - EXCEL_ZERO_DATE.getTime();

    public static long JAVA_EXCEL_DAY_DIFF = JAVA_EXCEL_TIME_DIFF / MILLIS_PER_DAY;

    /** hide ctor. */
    private DateUtils()
    {
    }

    /**
     *
     * @return
     */
    public static Date getCurrentDate()
    {
        return getStartOfDay(getCurrentDateTime());
    }

    /**
     *
     * @return
     */
    public static Date getCurrentDateTime()
    {
        return new Date();
    }

    /**
     * Return the 9am of the day for the specified date.
     *
     * @param date a date
     * @param time a time eg 9:00
     * @return 9am of the day of the day for the specified date
    public static Date getTimeOfDay(Date date, String time)
    {
        String[] times = StringUtils.isBlank(time) ? new String[0] : time.split(":");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, times.length > 0 ? Integer.parseInt(times[0]) : 0);
        calendar.set(Calendar.MINUTE, times.length > 1 ? Integer.parseInt(times[1]) : 0);
        calendar.set(Calendar.SECOND, times.length > 2 ? Integer.parseInt(times[2]) : 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
    */

    /**
     * 
     * @param ms
     * @return
     */
    public static String format(long ms) {
		long t = ms / 1000;
		long s = t % 60;
		t = t - s;
		long h = (t - t % 3600) / 3600;
		long m = (t - h * 3600) / 60;
		return h + ":" + (m < 10 ? "0" : "") + m + ":" + (s < 10 ? "0" : "") + s;
    }

    public static Date parse(String date, SimpleDateFormat dateFormat)
        throws ParseException
    {
        return StringUtils.isBlank(date) ? null : dateFormat.parse(date);
    }

    public static Date parse(String date, SimpleDateFormat dateFormat, Date defaultValue)
    {
        try
        {
			return StringUtils.isBlank(date) ? null : dateFormat.parse(date);
		}
        catch (ParseException e)
        {
        	LOG.warn("FAILED to parse date: [" + date + "] " + e.getMessage());
			return defaultValue;
		}
    }

    /**
     * 
     * @param date
     * @param time 15:00:00, 3:00
     * @param dateTimeFormat
     * @return date
     * @throws ParseException
     */
    public static Date parse(String date, String time, SimpleDateFormat dateTimeFormat)
        throws ParseException
    {
        return StringUtils.isBlank(date) ? null : dateTimeFormat.parse(date + " " + time);
    }

    /**
     * 
     * @param date
     * @return
     */
	public static int getFiscalYear(Date date)
	{
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR) +
            (calendar.get(Calendar.MONTH) < Calendar.JULY ? 0 : 1);
	}

    /**
     * Create future date (relative to supplied date) based on timeString and timeFormat.
     * @see SimpleDateFormat<br/>
     * F  Day of week in month  Number  2<br/>
     * E  Day in week  Text  Tuesday; Tue<br/>
     * H  Hour in day (0-23)  Number  0<br/>
     * m  Minute in hour  Number  30<br/>
     * s  Second in minute  Number  55<br/>
     *
     * @param date
     * @param timeString Tue 15:00:00, 3:00
     * @param timeFormat E HH:mm:ss,   HH:mm
     * @return future date only
     * @throws ParseException
     */
    public static Date getFutureDate(final Date date, String timeString, SimpleDateFormat timeFormat)
        throws ParseException
    {
        Calendar cal = Calendar.getInstance();
        long zoneOffset = cal.get(Calendar.ZONE_OFFSET);
        long dstOffset = cal.get(Calendar.DST_OFFSET);
        long offset = zoneOffset + dstOffset;

        String pattern = timeFormat.toPattern();
        long factor = MILLIS_PER_DAY;
        if (pattern.indexOf('F') >= 0 || pattern.indexOf('E') >= 0)
        {
            factor = MILLIS_PER_WEEK;
        }

        Date time = timeFormat.parse(timeString);
        long days = (date.getTime() + zoneOffset) / MILLIS_PER_DAY;
        long millis = (time.getTime() + zoneOffset) % factor;
        Date result = new Date(days * MILLIS_PER_DAY + millis - offset);

        //return future date only
        if (!result.after(date))
        {
            result = new Date(result.getTime() + factor);
        }

        return result;
    }

    /**
     * Return the start of the day for the specified date.
     *
     * @param date a date
     * @return start of the day of the day for the specified date
     */
    public static boolean isStartOfDay(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY) == 0 && calendar.get(Calendar.MINUTE) == 0
            && calendar.get(Calendar.SECOND) == 0 && calendar.get(Calendar.MILLISECOND) == 0;
    }

    /**
     * Return the start of the day for the specified date.
     *
     * @param date a date
     * @return start of the day of the day for the specified date
     */
    public static Date getStartOfDay(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * Return the end of the day for the specified date.
     *
     * @param date a date
     * @return end of the day of the day for the specified date
     */
    public static Date getEndOfDay(Date date)
    {
        return addDays(getStartOfDay(date), 1);
    }

    /**
     * Return the end of the last day of the month for the specified date.
     *
     * @param date a date
     * @return end of the last day of the month for the specified date
     */
    public static Date getEndOfMonth(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH); // max day in the month
        calendar.set(Calendar.DAY_OF_MONTH, maxDay);
        return calendar.getTime();
    }

    /**
     * Return the end of the week, where week starts Monday and ends Sunday.
     * @param date a date
     * @return end of the week, where week starts Monday and ends Sunday.
     */
    public static Date getEndOfWeek(Date date)
    {
        Date endDate = date;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        if (Calendar.SUNDAY != calendar.get(Calendar.DAY_OF_WEEK))
        {
            int daysToAdd = Calendar.SATURDAY + 1 - calendar.get(Calendar.DAY_OF_WEEK);
            calendar.add(Calendar.DATE, daysToAdd);
            endDate = calendar.getTime();
        }
        return endDate;
    }

    /**
     * Return the start of the week, where week starts Monday and ends Sunday.
     * @param date a date
     * @return start of the week, where week starts Monday and ends Sunday.
     */
    public static Date getStartOfWeek(Date date)
    {
        Date endDate = date;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        if (Calendar.MONDAY != calendar.get(Calendar.DAY_OF_WEEK))
        {
            int daysToGoBack = 2 - calendar.get(Calendar.DAY_OF_WEEK);
            //Sunday
            if (Calendar.SUNDAY == calendar.get(Calendar.DAY_OF_WEEK))
            {
                daysToGoBack = calendar.get(Calendar.DAY_OF_WEEK) - Calendar.SATURDAY;
            }
            calendar.add(Calendar.DATE, daysToGoBack);
            endDate = calendar.getTime();
        }
        return endDate;
    }

    /**
     * Month of the year, starts with 1
     * @param date
     * @return
     */
    public static int getMonth(Date date)
    {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.MONTH) + 1;
    }

    /**
     * Day of the month
     * @param date
     * @return
     */
    public static int getDay(Date date)
    {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * Add the specified number of seconds to a Date.
     *
     * @param date The date to add seconds to.
     * @param seconds  The number of seconds to add (specify -ve value to subtract seconds).
     * @return The Date of date + seconds.
     */
    public static Date addSeconds(Date date, int seconds)
    {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.SECOND, seconds);
        return c.getTime();
    }

    /**
     * Add the specified number of days to a Date.
     *
     * @param date The date to add days to.
     * @param days  The number of days to add (specify -ve value to subtract days).
     * @return The Date of date + days.
     */
    public static Date addDays(Date date, Number days)
    {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, days.intValue());
        return c.getTime();
    }

    /**
     * Add the specified number of weeks to a Date.
     *
     * @param date  The date to add weeks to.
     * @param weeks The number of weeks to add (specify -ve value to subtract weeks).
     * @return The Date of date + weeks.
     */
    public static Date addWeeks(Date date, Number weeks)
    {
        return addDays(date, 7 * weeks.intValue());
    }

    /**
     * Add the specified number of months to a Date.
     *
     * @param date  The date to add months to.
     * @param months The number of months to add (specify -ve value to subtract months).
     * @return The Date of date + months.
     */
    public static Date addMonths(Date date, Number months)
    {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, months.intValue());
        return c.getTime();
    }

    /**
     * Add the specified number of years to a Date.
     *
     * @param date The date to add years to.
     * @param years The number of years to add (specify -ve value to subtract years).
     * @return The Date of aDate + years.
     */
    public static Date addYears(Date date, Number years)
    {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.YEAR, years.intValue());
        return c.getTime();
    }

    /**
     * Determines if a date is on a weekend.
     * @param date date given.
     * @return true if date is on a weekend.
     */
    public static boolean isWeekend(Date date)
    {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        return dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY;
    }

    /**
     * Determines if a date is on a Monday.
     * @param date date given.
     * @return true if date is on a Monday.
     */
    public static boolean isMonday(Date date)
    {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        return dayOfWeek == Calendar.MONDAY;
    }

    /**
     * Determines if a date is on a Friday.
     * @param date date given.
     * @return true if date is on a Friday.
     */
    public static boolean isFriday(Date date)
    {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        return dayOfWeek == Calendar.FRIDAY;
    }

    /**
     * Determines if a date is on a Saturday.
     * @param date date given.
     * @return true if date is on a Saturday.
     */
    public static boolean isSaturday(Date date)
    {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        return dayOfWeek == Calendar.SATURDAY;
    }

    /**
     * Determines if a date is on a Sunday.
     * @param date date given.
     * @return true if date is on a Sunday.
     */
    public static boolean isSunday(Date date)
    {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        return dayOfWeek == Calendar.SUNDAY;
    }

    /**
     * Return the Following Monday for the specified date.
     *
     * @param date a date
     * @return Following Monday of the day for the specified date
     */
    public static Date getFollowingMonday(Date date)
    {
        if (isSaturday(date))
        {
            return addDays(date, 2);
        }
        if (isSunday(date))
        {
            return addDays(date, 1);
        }
        return date;
    }

    /**
     * To convert ExcelDate to JavaDate.
     * @param excelDate
     * @return
     */
    public static Date parseExcelDate(double excelDate)
    {
        Calendar calendar = Calendar.getInstance();
        // The year 1900 is a leap year
        if ((int) excelDate >= EXCEL_DATE_29_02_1900)
            excelDate -= 1.;

        // 01/01/1900 has numeric value of 1 (not a zero based)
        excelDate -= 1.;

        long ms = (long) (excelDate * MILLIS_PER_DAY) - JAVA_EXCEL_TIME_DIFF;
        calendar.setTimeInMillis(ms);
        long ZONE_OFFSET = calendar.get(Calendar.ZONE_OFFSET);
        long DST_OFFSET = calendar.get(Calendar.DST_OFFSET);
        long dtd = ZONE_OFFSET + DST_OFFSET;
        return new Date(ms - dtd);
    }

}
