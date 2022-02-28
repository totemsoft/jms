package au.gov.qld.fire.web.taglib;

import javax.servlet.jsp.JspException;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class GrantedPermissionTag extends PermissionTag
{

    /** serialVersionUID */
    private static final long serialVersionUID = 1417830521077882033L;

    /* (non-Javadoc)
     * @see javax.servlet.jsp.tagext.TagSupport#doStartTag()
     */
    @Override
    public int doStartTag() throws JspException
    {
        boolean granted = checkPermission();
        return granted ? EVAL_BODY_INCLUDE : SKIP_BODY;
    }

}