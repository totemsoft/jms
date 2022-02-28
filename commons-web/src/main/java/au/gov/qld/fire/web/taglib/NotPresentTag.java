package au.gov.qld.fire.web.taglib;

import javax.servlet.jsp.JspException;

import org.apache.commons.lang.StringUtils;

/**
 * @see struts logic::notpresent path="role1"
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class NotPresentTag extends PresentTag
{

    /** serialVersionUID */
    private static final long serialVersionUID = 1318656147409142728L;

    /* (non-Javadoc)
     * @see javax.servlet.jsp.tagext.TagSupport#doStartTag()
     */
    @Override
    public int doStartTag() throws JspException
    {
        boolean skipBody = true;
        if (StringUtils.isNotBlank(role))
        {
            skipBody = isUserInRole();
        }
        if (StringUtils.isNotBlank(module))
        {
            skipBody = StringUtils.isBlank(path) ? isUserInModule() : isUserInModulePath();
        }
        return skipBody ? SKIP_BODY : EVAL_BODY_INCLUDE;
    }

}