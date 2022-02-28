package au.gov.qld.fire.web.module;

import java.io.Serializable;

/**
 *
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public final class Link implements Serializable
{
    /** serialVersionUID */
    private static final long serialVersionUID = 3544061014855966268L;

    /** module */
    private String module;

    /** action */
    private String action;

    /** title */
    private String title;

    /**
     * 
     * @param module
     * @param action
     * @param title
     */
    public Link(String module, String action, String title)
    {
        this.module = module;
        this.action = action;
        this.title = title;
    }

    /**
     * @return the module
     */
    public String getModule()
    {
        return module;
    }

    /**
     * @return the action
     */
    public String getAction()
    {
        return action;
    }

    /**
     * @return the title
     */
    public String getTitle()
    {
        return title;
    }

}
