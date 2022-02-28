package au.gov.qld.fire.web.taglib;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.security.AccessController;
import java.security.Permission;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public abstract class PermissionTag extends TagSupport
{

    /** serialVersionUID */
    private static final long serialVersionUID = 2617518797169052573L;

    private String type;

    private String name;

    private String actions;

    /**
     * @return the type
     */
    public String getType()
    {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type)
    {
        this.type = type;
    }

    /**
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * @return the actions
     */
    public String getActions()
    {
        return actions;
    }

    /**
     * @param actions the actions to set
     */
    public void setActions(String actions)
    {
        this.actions = actions;
    }

    /**
     * 
     * @return
     * @throws JspException
     */
    protected boolean checkPermission() throws JspException
    {
        //Subject ctxSubject = Subject.getSubject(AccessController.getContext());
        if (StringUtils.isBlank(type))
        {
            throw new NullPointerException("type is blank.");
        }

        Class<?> clazz;
        try
        {
            clazz = Class.forName(type);
        }
        catch (ClassNotFoundException e)
        {
            throw new JspException("'" + type + "' was not found.", e);
        }

        if (!Permission.class.isAssignableFrom(clazz))
        {
            throw new IllegalArgumentException(type + " is not a 'java.security.Permission'.");
        }

        Permission perm;
        try
        {
            if (StringUtils.isNotBlank(name) && StringUtils.isBlank(actions))
            {
                Constructor<?> c = clazz.getConstructor(new Class[]
                {
                    String.class
                });
                perm = (Permission) c.newInstance(new Object[]
                {
                    name
                });
            }
            else if (StringUtils.isNotBlank(name) && StringUtils.isNotBlank(actions))
            {
                // name and actions
                Constructor<?> c = clazz.getConstructor(new Class[]
                {
                    String.class, String.class
                });
                perm = (Permission) c.newInstance(new Object[]
                {
                    name, actions
                });
            }
            else
            {
                throw new NullPointerException("Permission name must be specified.");
            }
        }
        catch (SecurityException e)
        {
            throw new JspException(e);
        }
        catch (NoSuchMethodException e)
        {
            throw new JspException("Could not instantiate " + type + " instance.", e);
        }
        catch (IllegalArgumentException e)
        {
            throw new JspException(e);
        }
        catch (InstantiationException e)
        {
            throw new JspException(e);
        }
        catch (IllegalAccessException e)
        {
            throw new JspException(e);
        }
        catch (InvocationTargetException e)
        {
            throw new JspException(e);
        }

        try
        {
            AccessController.checkPermission(perm);
            //permission granted
            return true;
        }
        catch (SecurityException e)
        {
            return false;
        }
    }

}