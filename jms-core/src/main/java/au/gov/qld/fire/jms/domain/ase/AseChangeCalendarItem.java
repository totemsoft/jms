package au.gov.qld.fire.jms.domain.ase;

import java.util.Date;
import java.util.List;

import au.gov.qld.fire.domain.BaseCalendarItem;
import au.gov.qld.fire.domain.entity.Contact;
import au.gov.qld.fire.jms.domain.building.Building;
import au.gov.qld.fire.jms.domain.building.BuildingContact;
import au.gov.qld.fire.jms.domain.file.File;
import au.gov.qld.fire.util.DateUtils;

/*
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class AseChangeCalendarItem extends BaseCalendarItem<AseChange>
{

    /**
     * @param year
     * @param month
     * @param day
     */
    public AseChangeCalendarItem(int year, int month, int day)
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
            for (AseChange item : getItems())
            {
                sb.append("<li>");

                //build text string
                StringBuffer text = new StringBuffer();
                Date dateChange = item.getDateChange();
                if (dateChange != null)
                {
                    text.append("<b>").append(DateUtils.HH_mm.format(dateChange)).append("</b>");
                }
                else
                {
                    text.append("<b>TBD</b>");
                }
                File file = item.getAseFile().getFile();
                text.append("<br/><b>File No:</b> ").append(file.getId());
                Building building = file.getBuilding();
                if (building != null)
                {
                    text.append("<br/><b>Building:</b> ").append(building.getName());
                    if (building.getAddress() != null)
                    {
                        text.append("<br/>").append(building.getAddress().getFullAddress());
                    }
                }

                // build title string
                StringBuffer title = new StringBuffer();
                // Day Time contacts
                List<BuildingContact> bcs = file.getOwnerBuildingContacts();
                if (!bcs.isEmpty())
                {
                    title.append("<ul>Owner:");
                }
                for (BuildingContact bc : bcs)
                {
                    Contact c = bc.getContact();
                    if (c != null)
                    {
                        title.append("<li>").append(c.getFullName()).append("</li>");
                    }
                }
                if (!bcs.isEmpty())
                {
                    title.append("</ul>");
                }
//                // Day Time contacts
//                bcs = file.getDaytimeBuildingContacts();
//                if (!bcs.isEmpty())
//                {
//                    title.append("<ul>Daytime:");
//                }
//                for (BuildingContact bc : bcs)
//                {
//                    Contact c = bc.getContact();
//                    if (c != null)
//                    {
//                        title.append("<li>").append(c.getFullName()).append("</li>");
//                    }
//                }
//                if (!bcs.isEmpty())
//                {
//                    title.append("</ul>");
//                }
//                // After Hours contacts
//                bcs = file.getAfterHoursBuildingContacts();
//                if (!bcs.isEmpty())
//                {
//                    title.append("<ul>AfterHours:");
//                }
//                for (BuildingContact bc : bcs)
//                {
//                    Contact c = bc.getContact();
//                    if (c != null)
//                    {
//                        title.append("<li>").append(c.getFullName()).append("</li>");
//                    }
//                }
//                if (!bcs.isEmpty())
//                {
//                    title.append("</ul>");
//                }
                // TODO: move to web layer
                sb.append("<a href='javascript:YAHOO.jms.editAseChange("
                    + file.getId() + ");' title='" + title.toString() + "' id='pc" + item.getId()
                    + "'>" + text.toString() + "</a>");
                sb.append("</li>");
            }
            sb.append("</ul>");
        }
        return sb.toString();
    }

}