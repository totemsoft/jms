package au.gov.qld.fire.web.taglib;

import javax.servlet.jsp.JspException;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class NotGrantedPermissionTag extends PermissionTag
{

    /** serialVersionUID */
    private static final long serialVersionUID = 7664970312672108197L;

    /* (non-Javadoc)
     * @see javax.servlet.jsp.tagext.TagSupport#doStartTag()
     */
    @Override
    public int doStartTag() throws JspException
    {
        boolean granted = checkPermission();
        return granted ? SKIP_BODY : EVAL_BODY_INCLUDE;
    }

}