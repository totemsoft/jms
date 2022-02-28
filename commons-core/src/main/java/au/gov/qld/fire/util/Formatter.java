package au.gov.qld.fire.util;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;


/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class Formatter
{

    /** logger */
    private static final Logger LOG = Logger.getLogger(Formatter.class);

    public static final Character NEW_LINE_CHAR  = '\n'; // CR
    public static final Character LINE_FEED_CHAR = '\r'; // LF

    private final static NumberFormat NUMBER_FORMAT = NumberFormat.getNumberInstance();

    private final static NumberFormat PERCENT_FORMAT = NumberFormat.getPercentInstance();

    private final static NumberFormat CURRENCY_FORMAT = NumberFormat.getCurrencyInstance();
    static
    {
        NUMBER_FORMAT.setMinimumFractionDigits(2);
        NUMBER_FORMAT.setMaximumFractionDigits(2);
        NUMBER_FORMAT.setGroupingUsed(false);
        PERCENT_FORMAT.setMinimumFractionDigits(2);
        PERCENT_FORMAT.setMaximumFractionDigits(2);
    }

    /**
     * Format a <code>String</code> in the format 'dd/mm/yyyy' into its
     * correspondent <code>Date</code>. If an error occurs while processing,
     * then <code>null</code> will be returned.
     *
     * @param date
     *            Date to be formatted.
     * @return The correstpondent Date.
     */
    public static Date parseDate(String date)
    {
        try
        {
            return StringUtils.isNotBlank(date) ? DateUtils.D_M_YYYY.parse(date) : null;
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public static Date parseDateTime(String date, String time)
    {
        try
        {
            if (StringUtils.isBlank(time) && StringUtils.isBlank(date))
            {
                return null;
            }
            if (StringUtils.isBlank(date))
            {
                return DateUtils.H_mm.parse(time);
            }
            return DateUtils.D_M_YYYY_H_mm.parse(date + " " + time);
        }
        catch (Exception e)
        {
            return null;
        }
    }

    /**
     * Format a <code>Date</code> into a <code>String</code> in the format
     * 'dd/mm/yyyy'. If <code>date</code> is <code>null</code>, then an
     * empty string will be returned.
     *
     * @param date
     *            <code>Date</code> to be converted.
     * @return
     */
    public static String formatDate(Date date)
    {
        return date == null ? "" : DateUtils.DD_MM_YYYY.format(date);
    }

    public static String formatTime(Date date)
    {
        return date == null ? "" : DateUtils.HH_mm.format(date);
    }

    public static String formatDateTime(Date date)
    {
        return date == null ? "" : DateUtils.DD_MM_YYYY_HH_mm.format(date);
    }

    public static String formatCurrency(Number value)
    {
        return value == null ? formatCurrency(0f) : CURRENCY_FORMAT.format(value);
    }

    public static String formatPercent(Number value)
    {
        return value == null ? formatPercent(0f) : PERCENT_FORMAT.format(value);
    }

    /**
     * 
     * @param sNumber
     * @return
     */
    public static Long parseLong(String sNumber)
    {
        try
        {
            return new Long(sNumber.replaceAll(",", ""));
        }
        catch (Exception ignore)
        {
            LOG.debug(ignore.getMessage());
            return null;
        }
    }

    /**
     * 
     * @param sNumber
     * @return
     */
    public static BigDecimal parseBigDecimal(String sNumber)
    {
        if (StringUtils.isBlank(sNumber))
        {
            return null;
        }
        try
        {
            sNumber = sNumber.replaceAll(",", "");
            if (!Character.isDigit(sNumber.charAt(0)))
            {
                sNumber = sNumber.substring(1); // remove currency symbol
            }
            return new BigDecimal(sNumber);
        }
        catch (Exception e)
        {
            LOG.warn(e.getMessage());
            return null;
        }
    }

    /**
     * 
     * @param text
     * @param maxLength
     * @return
     */
    public static String[] split(String text, int maxLength)
    {
        List<String> texts = new ArrayList<String>();
        String tmp = "";
        for (String s : text.split(" "))
        {
            if (tmp.length() + s.length() > maxLength)
            {
                texts.add(tmp.trim());
                tmp = "";
            }
            tmp += " " + s;
        }
        if (tmp.length() > 0)
        {
            texts.add(tmp.trim());
        }
        return texts.toArray(new String[0]);
    }

    /**
     * 
     * @param html
     * @return
     */
    public static String htmlToText(String html)
    {
        if (StringUtils.isBlank(html))
        {
            return null;
        }
        int pos = -1;
        while ((pos = html.indexOf('<', pos)) >= 0)
        {
            int pos2 = html.indexOf('>', pos);
            if (pos2 >= 0)
            {
                html = html.substring(0, pos) + html.substring(pos2 + 1);
            }
            else
            {
                break;
            }
        }
        return html;
    }

    /**
     * Format for json data (encode unsafe json character(s)).
     * @param s
     * @return
     * @throws UnsupportedEncodingException 
     */
    public static String formatJson(String s)
    {
        if (StringUtils.isBlank(s))
        {
            return "";
        }
        s = format(s);
        s = s.replace('"', '\'');
        s = s.replace(NEW_LINE_CHAR.toString(), "<br/>");
        return format(s);
    }

    /**
     * Format for ms-office data (encode unsafe character(s)).
     * @param s
     * @return
     */
    public static String format(String s)
    {
        if (StringUtils.isBlank(s))
        {
            return s;
        }
        //
        //s = new String(s.getBytes(), "iso-8859-1");
        s = s.replaceAll("((\\r\\n)|(\\n)|(\\r))+", NEW_LINE_CHAR.toString());
        //
        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++)
        {
            char c = chars[i];
            if (c == NEW_LINE_CHAR)
            {
                //chars[i] = c; // to preserve CR (will be replaced with <br/> for html UI)
            }
            else if (Character.isWhitespace(c))
            {
                c = ' '; // to remove ms-office crap
            }
            else if (Character.isIdentifierIgnorable(c))
            {
                c = format(c);
                if (c == chars[i])
                {
                    LOG.warn("The following Unicode character is ignored: " + c + "[" + (int) c + "]");
                    c = '?';
                }
            }
            else
            {
                c = format(c); // just in case
            }
            chars[i] = c;
        }
        s = new String(chars);
        return s.trim();
    }

    private static char format(char c)
    {
        if (c == 145 || c == 146 || c == 8216 || c == 8217)
        {
            return '\'';
        }
        if (c == 147 || c == 148 || c == 8220 || c == 8221)
        {
            return '"';
        }
        if (c == 150 || c == 8211 || c == 8212) // en dash, em dash
        {
            return '-';
        }
        if (c == 149 || c == 8226) // bullet
        {
            return '*';
        }
        if (c == 8230) // horizontal ellipsis
        {
            return '.';
        }
        if (c == 0x0FFFD) // &#65533; pound (how they manage to put it there)
        {
            return 0xA3;
        }
        if (c > 8211) {
            return '?'; // extra protection
        }
        return c;
    }

}