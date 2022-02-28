package au.gov.qld.fire.web.taglib;

import java.util.Set;

import javax.security.auth.Subject;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;

import au.gov.qld.fire.security.GroupPrincipal;
import au.gov.qld.fire.web.WebUtils;

/**
 * @see struts logic::present module="/file" path="/find"
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class PresentTag extends TagSupport
{

    /** serialVersionUID */
    private static final long serialVersionUID = 7690064285893842770L;

    /** Security Group name - this user belong to */
    protected String role;

    /** Struts action module */
    protected String module;

    /** Struts action path */
    protected String path;

    /**
     * @return the role
     */
    public String getRole()
    {
        return role;
    }

    /**
     * @param role the role to set
     */
    public void setRole(String role)
    {
        this.role = role;
    }

    /**
     * @return the module
     */
    public String getModule()
    {
        return module;
    }

    /**
     * @param module the module to set
     */
    public void setModule(String module)
    {
        this.module = module;
    }

    /**
     * @return the roles
     */
    public String getPath()
    {
        return path;
    }

    /**
     * @param path the path to set
     */
    public void setPath(String role)
    {
        this.path = role;
    }

    /* (non-Javadoc)
     * @see javax.servlet.jsp.tagext.TagSupport#doStartTag()
     */
    @Override
    public int doStartTag() throws JspException
    {
        boolean includeBody = false;
        if (StringUtils.isNotBlank(role))
        {
            includeBody = isUserInRole();
        }
        if (StringUtils.isNotBlank(module))
        {
            includeBody = StringUtils.isBlank(path) ? isUserInModule() : isUserInModulePath();
        }
        return includeBody ? EVAL_BODY_INCLUDE : SKIP_BODY;
    }

    /**
     * 
     * @param path
     * @return
     * @throws JspException
     */
    protected boolean isUserInRole() throws JspException
    {
        GroupPrincipal groupPrincipal = getGroupPrincipal();
        return groupPrincipal == null ? false : groupPrincipal.getName().equals(role);
    }

    /**
     * Check if user security group can access this module.
     * @param module
     * @return
     * @throws JspException
     */
    protected boolean isUserInModule() throws JspException
    {
        GroupPrincipal groupPrincipal = getGroupPrincipal();
        return groupPrincipal == null ? false : groupPrincipal.hasSystemFunction(module);
    }

    /**
     * 
     * @return
     * @throws JspException
     */
    protected boolean isUserInModulePath() throws JspException
    {
        GroupPrincipal groupPrincipal = getGroupPrincipal();
        return groupPrincipal == null ? false : groupPrincipal.hasSystemFunction(module, path, null);
    }

    /**
     * 
     * @return
     * @throws JspException
     */
    private GroupPrincipal getGroupPrincipal() throws JspException
    {
        //Check subject access group (loaded during login)
        Subject subject = WebUtils.getSubject(pageContext.getSession());
        if (subject == null)
        {
            //throw new JspException("No subject found.");
            return null;
        }

        //get GroupPrincipal (only one should exists)
        Set<GroupPrincipal> groupPrincipals = subject.getPrincipals(GroupPrincipal.class);
        if (groupPrincipals.isEmpty())
        {
            throw new JspException("No GroupPrincipal found for subject.");
        }
        if (groupPrincipals.size() > 1)
        {
            throw new JspException("More than one GroupPrincipal found for subject.");
        }
        return (GroupPrincipal) groupPrincipals.toArray()[0];
    }

}