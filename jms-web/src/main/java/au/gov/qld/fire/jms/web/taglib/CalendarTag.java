package au.gov.qld.fire.jms.web.taglib;

import java.util.Date;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import org.apache.struts.taglib.html.TextTag;

import au.gov.qld.fire.util.DateUtils;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class CalendarTag extends TextTag
{

    /** serialVersionUID */
    private static final long serialVersionUID = -1506970315083866076L;

    private String image = "images/calendar.gif";

    //===============JSCalendar 1.0 properties=======================
    //http://www.dynarch.com/demos/jscalendar/doc/html/reference.html
    private String ifFormat = "%d/%m/%Y";
    //private String ifFormat = "%d/%m/%Y %H:%M";

    private String daFormat = ifFormat;

    private String firstDay = "1";

    private boolean showsTime = false;

    private String timeFormat = "24";

    private String align = "Bl";

    private boolean singleClick = true;

    /* (non-Javadoc)
     * @see org.apache.struts.taglib.html.BaseFieldTag#formatValue(java.lang.Object)
     */
    @Override
    protected String formatValue(Object value) throws JspException
    {
        if (value instanceof Date)
        {
            //TODO: match ifFormat/daFormat
            return DateUtils.DD_MM_YYYY.format((Date) value);
            //return DateUtils.DD_MM_YYYY_HH_mm.format((Date) value);
        }
        return super.formatValue(value);
    }

    /* (non-Javadoc)
     * @see javax.servlet.jsp.tagext.TagSupport#doStartTag()
     */
    public int doStartTag() throws JspException
    {
        setSize("10");
        setMaxlength("12");
        setReadonly(false);

        setStyleId(getProperty() + "Input");

        String name = property.replace('.', '_').replace('[', '_').replace(']', '_');
        String button = name + "Trigger";

        JspWriter out = pageContext.getOut();
        try
        {
            out.write("<table border='0' cellpadding='0' cellspacing='0'>");
            out.write("<tr>");
            out.write("<td>");
            super.doStartTag();
            out.write("</td>");
            out.write("<td>&#160;</td>");
            out.write("<td>");
            out.write("<img src='" + image + "'" + " id='" + button + "'"
                + "' border='0'" + (getDisabled() ? " disabled='true'" : "") + " title='" + getTitle() + "'"
                + " style='cursor: pointer;'" + " onmouseover=\"this.style.background='red'\""
                + " onmouseout=\"this.style.background=''\"");
            out.write("/>");
            out.write("</td>");
            out.write("<script type='text/javascript'>");
            out.write("Calendar.setup({" + "inputField  : '" + getStyleId() + "',"
                + "ifFormat    : '" + ifFormat + "'," + "daFormat    : '" + daFormat + "',"
                + "firstDay    : '" + firstDay + "'," + "button      : '" + button + "',"
                + "showsTime   : " + showsTime + "," + "timeFormat  : '" + timeFormat + "',"
                + "align       : '" + align + "'," + "singleClick : " + singleClick + "" + "})");
            out.write("</script>");
            out.write("</tr>");
            out.write("</table>");

            return SKIP_BODY;
        }
        catch (Exception e)
        {
            throw new JspException(e.getMessage(), e);
        }
    }

    /**
     * @return the property
     */
    public String getProperty()
    {
        return property;
    }

    public void setProperty(String property)
    {
        this.property = property;
    }

    /**
     * @return the value
     */
    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    /**
     * @return the image
     */
    public String getImage()
    {
        return image;
    }

    public void setImage(String image)
    {
        this.image = image;
    }

    /**
     * @return the align
     */
    public String getAlign()
    {
        return align;
    }

    public void setAlign(String align)
    {
        this.align = align;
    }

    /**
     * @return the daFormat
     */
    public String getDaFormat()
    {
        return daFormat;
    }

    public void setDaFormat(String daFormat)
    {
        this.daFormat = daFormat;
    }

    /**
     * @return the firstDay
     */
    public String getFirstDay()
    {
        return firstDay;
    }

    public void setFirstDay(String firstDay)
    {
        this.firstDay = firstDay;
    }

    /**
     * @return the ifFormat
     */
    public String getIfFormat()
    {
        return ifFormat;
    }

    public void setIfFormat(String ifFormat)
    {
        this.ifFormat = ifFormat;
    }

    /**
     * @return the showsTime
     */
    public boolean isShowsTime()
    {
        return showsTime;
    }

    public void setShowsTime(boolean showsTime)
    {
        this.showsTime = showsTime;
    }

    /**
     * @return the singleClick
     */
    public boolean isSingleClick()
    {
        return singleClick;
    }

    public void setSingleClick(boolean singleClick)
    {
        this.singleClick = singleClick;
    }

    /**
     * @return the timeFormat
     */
    public String getTimeFormat()
    {
        return timeFormat;
    }

    public void setTimeFormat(String timeFormat)
    {
        this.timeFormat = timeFormat;
    }

}
