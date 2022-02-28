package au.gov.qld.fire.web.taglib;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.lang.StringUtils;

import au.gov.qld.fire.web.taglib.WindowOptionTag.Option;

/**
 * @author Valeri CHIBAEV
 */
public class WindowTag extends BodyTagSupport
{

    /** serialVersionUID */
    private static final long serialVersionUID = 6589076374037701983L;

    private transient static long toggleId = 0;

    private String id = null;

    private String title = "";

    private String width = null;

    private String iconClass = null;

    private boolean collapse = false;

    private boolean collapsed = false;

    private transient List<Option> options;

    private transient String tagBodyContent = "";

    public int doStartTag() throws JspException
    {
        if (toggleId > 1000)
        {
            toggleId = 0;
        }
        options = new ArrayList<Option>();
        return EVAL_PAGE;
    }

    public int doAfterBody() throws JspException
    {
        tagBodyContent = bodyContent == null ? "" : bodyContent.getString().trim();
        return SKIP_BODY;
    }

    public int doEndTag() throws JspException
    {
        toggleId++;
        try
        {
            JspWriter out = pageContext.getOut();
            out.write("<div" + (id != null ? " id='" + id + "'" : "") + " class='jms-container" + (iconClass != null ? " " + iconClass : "") + "'" + (width != null ? " style='width:" + width + ";'" : "") + ">");
            out.write("<div class='jms-container-hd'>");
            out.write("<h2>" + title + "</h2>");
            if (collapse)
            {
                out.write("<span id='toggle." + (id == null ? toggleId : id) + "' class='collapse" + (collapsed ? " collapsed" : "") + "'>X</span>");
            }
            out.write("</div>");
            out.write("<div id='toggle." + (id == null ? toggleId : id) + ".container' class='jms-container-bd'" + (collapsed ? " style='display:none;'" : "") + ">");
            if (!getOptions().isEmpty())
            {
                out.write("<div class='jms-container-option'>");
                for (Option o : getOptions())
                {
                	String href = o.getUrl();
                	if (StringUtils.isBlank(href))
                	{
                        out.write("<div"
                            + (o.getId() != null ? " id='" + o.getId() + "'" : "")
                            + (o.getClassName() != null ? " class='" + o.getClassName() + "'" : "")
                            + " title='" + o.getTitle() + "'>" + o.getName() + "</div>&#160;");
                	}
                	else
                	{
                        out.write("<a"
                            + (o.getId() != null ? " id='" + o.getId() + "'" : "")
                            + (o.getClassName() != null ? " class='" + o.getClassName() + "'" : "")
                            + " href='" + href + "' title='" + o.getTitle() + "' target='" + o.getTarget() + "'>" + o.getName() + "</a>&#160;");
                	}
                }
                out.write("</div>");
            }
            out.write(tagBodyContent);
            out.write("</div>");
            out.write("</div>");
            out.write("<span></span>"); //to make IE6 happy
        }
        catch (IOException e)
        {
            throw new JspTagException(e.toString());
        }
        return EVAL_PAGE;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id)
    {
        this.id = id;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title)
    {
        this.title = title;
    }

    /**
     * @param width the width to set
     */
    public void setWidth(String width)
    {
        this.width = width;
    }

    /**
     * @param iconClass the iconClass to set
     */
    public void setIconClass(String iconClass)
    {
        this.iconClass = iconClass;
    }

    /**
     * @param collapse the collapse to set
     */
    public void setCollapse(boolean collapse)
    {
        this.collapse = collapse;
    }

    /**
     * @param collapsed the collapsed to set
     */
    public void setCollapsed(boolean collapsed)
    {
        this.collapsed = collapsed;
    }

    /**
     * @return the options
     */
    public List<Option> getOptions()
    {
        return options;
    }

}