package au.gov.qld.fire.jms.web.module;

import org.apache.log4j.Logger;
import org.apache.struts.actions.BaseAction;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public abstract class AbstractBaseAction extends BaseAction
{

    /** logger */
    protected final Logger LOG = Logger.getLogger(getClass());

    /** CANCEL */
    public final static String CANCEL = "cancel";

    /** SUCCESS */
    public final static String SUCCESS = "success";

    /** ERROR */
    public final static String FAILURE = "failure";

}