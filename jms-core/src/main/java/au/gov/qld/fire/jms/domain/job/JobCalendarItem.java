package au.gov.qld.fire.jms.domain.job;

import au.gov.qld.fire.domain.BaseCalendarItem;
import au.gov.qld.fire.jms.domain.action.JobAction;

/*
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class JobCalendarItem extends BaseCalendarItem<JobAction>
{

    /**
     * @param year
     * @param month
     * @param day
     */
    public JobCalendarItem(int year, int month, int day)
    {
        super(year, month, day);
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.domain.BaseCalendarItem#getInnerHtml()
     */
    @Override
    public String getInnerHtml()
    {
        StringBuffer sb = new StringBuffer();
        if (!getItems().isEmpty())
        {
            sb.append("<ul>");
            for (JobAction item : getItems())
            {
                sb.append("<li>");
                String title = item.getNotation();
                //truncate link text (if too big)
                //if (title != null && title.length() > MAX_TEXT_SIZE)
                //{
                //    title = title.substring(0, MAX_TEXT_SIZE) + "..";
                //}
                // TODO: move to web layer
                sb.append("<a href='javascript:YAHOO.jms.editJobAction("
                    + item.getId() + ");' title='" + title + "' id='ja" + item.getId() + "'>"
                    + title + "</a>");
                sb.append("</li>");
            }
            sb.append("</ul>");
        }
        return sb.toString();
    }

}