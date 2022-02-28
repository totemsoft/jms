package au.gov.qld.fire.web.taglib;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.Tag;

/**
 * @author Valeri CHIBAEV
 */
public class WindowOptionTag extends BodyTagSupport
{

    /** serialVersionUID */
    private static final long serialVersionUID = 7368402551137377102L;

    private WindowTag parent;

    private String id = null;

    private String url = null;

    private String target = "";

    private String name = "";

    private String title = "";

    private String className = null;

    public int doEndTag() throws JspException
    {
        if (parent != null)
        {
            if (url != null && url.length() > 0 && url.charAt(0) == '/')
            {
                url = ((HttpServletRequest) pageContext.getRequest()).getContextPath() + url;
            }
            if (bodyContent != null)
            {
                name = bodyContent.getString().trim();
            }
            parent.getOptions().add(new Option(id, url, target, name, title, className));
        }
        return EVAL_PAGE;
    }

    /* (non-Javadoc)
     * @see javax.servlet.jsp.tagext.TagSupport#setParent(javax.servlet.jsp.tagext.Tag)
     */
    @Override
    public void setParent(Tag tag)
    {
        while (tag != null && !(tag instanceof WindowTag))
        {
            tag = tag.getParent();
        }
        if (tag != null)
        {
            parent = (WindowTag) tag;
        }
    }

    /**
     * @param id the id to set
     */
    public void setId(String id)
    {
        this.id = id;
    }

    /**
     * @return the url
     */
    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    /**
     * @return the target
     */
    public String getTarget()
    {
        return target;
    }

    public void setTarget(String target)
    {
        this.target = target;
    }

    /**
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * @return the title
     */
    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    /**
     * @return the className
     */
    public String getClassName()
    {
		return className;
	}

	public void setClassName(String className)
	{
		this.className = className;
	}

	class Option
    {
        private final String id;

        private final String url;

        private final String target;

        private final String name;

        private final String title;

        private final String className;

        public Option(String id, String url, String target, String name, String title, String className)
        {
            this.id = id;
            this.url = url;
            this.target = target;
            this.name = name;
            this.title = title;
            this.className = className;
        }

        public String getId()
        {
            return id;
        }

        public String getUrl()
        {
            return url;
        }

        public String getTarget()
        {
            return target;
        }

        public String getName()
        {
            return name;
        }

        public String getTitle()
        {
            return title;
        }

		public String getClassName()
		{
			return className;
		}

    }

}