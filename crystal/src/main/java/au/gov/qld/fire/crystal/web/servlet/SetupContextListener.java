package au.gov.qld.fire.crystal.web.servlet;

import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.springframework.web.context.ContextLoaderListener;

import com.crystaldecisions.report.web.viewer.CrystalImageCleaner;

/**
 * Helper class for setting up the web application.
 * It must be configured as a listener on in the web application <code>web.xml</code>.
 *
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class SetupContextListener extends ContextLoaderListener implements ServletContextListener
{

    /** Used for logging. */
    private static final Logger LOG = Logger.getLogger(SetupContextListener.class);
    static
    {
        try
        {
            StringBuffer sb = new StringBuffer();
            for (Map.Entry<String, String> entry : System.getenv().entrySet())
            {
                sb.append(entry.getKey() + "=" + entry.getValue()).append('\n');
            }
            //LOG.info("System environment variables:\n" + sb.toString());
        }
        catch (Exception e)
        {
            LOG.warn(e.getMessage());
        }

        try
        {
            StringBuffer sb = new StringBuffer();
            for (Map.Entry<Object, Object> entry : System.getProperties().entrySet())
            {
                sb.append(entry.getKey() + "=" + entry.getValue()).append('\n');
            }
            //LOG.info("System Properties:\n" + sb.toString());
        }
        catch (RuntimeException e)
        {
            LOG.warn(e.getMessage());
        }
    }

    /* (non-Javadoc)
     * @see org.springframework.web.context.ContextLoaderListener#contextInitialized(javax.servlet.ServletContextEvent)
     */
    @Override
    public void contextInitialized(final ServletContextEvent event)
    {
        super.contextInitialized(event);
    }

    /* (non-Javadoc)
     * @see org.springframework.web.context.ContextLoaderListener#contextDestroyed(javax.servlet.ServletContextEvent)
     */
    @Override
    public void contextDestroyed(ServletContextEvent event)
    {
        CrystalImageCleaner.stop(event.getServletContext());
        super.contextDestroyed(event);
    }

}