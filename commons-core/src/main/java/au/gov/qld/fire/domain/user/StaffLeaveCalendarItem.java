package au.gov.qld.fire.domain.user;

import au.gov.qld.fire.domain.BaseCalendarItem;

/*
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class StaffLeaveCalendarItem extends BaseCalendarItem<StaffLeave>
{

    /**
     * @param year
     * @param month
     * @param day
     */
    public StaffLeaveCalendarItem(int year, int month, int day)
    {
        super(year, month, day);
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.domain.BaseCalendarItem#getInnerHtml()
     */
    @Override
    public String getInnerHtml()
    {
        // TODO: move to web layer
        StringBuffer sb = new StringBuffer();
        if (!getItems().isEmpty())
        {
            sb.append("<ul>");
            if (getItems().isEmpty())
            {
                sb.append("&#160;");
            }
            else
            {
                for (StaffLeave item : getItems())
                {
                    sb.append("<li>");
                    String title = item.getLeaveType().getName() + "<br/>" + item.getDescription();
                    String href = "javascript:;";
                    if (item.isSubmitted())
                    {
                    	href = "javascript:YAHOO.jms.editStaffLeave(" + item.getId() + ");";
                    }
                    else
                    {
                    	title += "<hr/>" + item.getStatus();
                    }
                    sb.append("<a href='" + href + "' title='" + title + "' id='staffLeave." + item.getId() + "'>"
                            + title + "</a>");
                    sb.append("</li>");
                }
            }
            sb.append("</ul>");
        }
        return sb.toString();
    }

}