package au.gov.qld.fire.web.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.commons.lang.StringUtils;

import au.gov.qld.fire.util.Formatter;

/**
 * @author Valeri CHIBAEV
 */
public class JsonTextTag extends SimpleTagSupport
{

    private String text;

    private int limit = 0;

    public void setLimit(int limit)
    {
        this.limit = limit;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    /* (non-Javadoc)
     * @see javax.servlet.jsp.tagext.SimpleTagSupport#doTag()
     */
    @Override
    public void doTag() throws JspException, IOException
    {
        JspWriter out = getJspContext().getOut();
        text = Formatter.format(text);
        if (limit > 0) {
            text = StringUtils.abbreviate(text, limit);
        }
        text = text.replace(Formatter.NEW_LINE_CHAR.toString(), "<br/>").replace(Formatter.LINE_FEED_CHAR.toString(), "");
        text = text.replace('"', '\''); // TODO: how to keep double quote (replace with single for now)?
        text = StringUtils.replace(text, "\\", "\\\\");
        out.write(text);
    }

}