package au.gov.qld.fire.jms.uaa.web.servlet;

import java.math.BigDecimal;
import java.security.Policy;
import java.util.Arrays;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.BigDecimalConverter;
import org.apache.commons.beanutils.converters.BooleanConverter;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.web.context.ContextLoaderListener;

import au.gov.qld.fire.IBeanNames;
import au.gov.qld.fire.converters.DateConverter;
import au.gov.qld.fire.jms.uaa.service.EntityService;
import au.gov.qld.fire.security.CompositePolicy;
import au.gov.qld.fire.web.WebUtils;

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

        try
        {
            PropertyConfigurator.configure(getClass().getResource("/log4j.properties"));
            // As of log4j1.2.8 this needs to be set to avoid a possible deadlock on exception at the appender level. See bug#696819.
            // Learn this from http://wiki.jboss.org/wiki/Wiki.jsp?page=Logging.
            //LogLog.setQuietMode(true);
            LOG.info("Log4J has been initialised.");

            Policy defaultPolicy = Policy.getPolicy();
            if (!defaultPolicy.getClass().isAssignableFrom(CompositePolicy.class))
            {
                Policy securityPolicy = (Policy) WebUtils.getBean(event.getServletContext(),
                    IBeanNames.SECURITY_POLICY);
                Policy.setPolicy(new CompositePolicy(Arrays.asList(new Policy[]
                {
                    defaultPolicy, securityPolicy
                })));
                LOG.info("Policy has been initialised.");
            }

            // synchronise database
            EntityService entityService = (EntityService) WebUtils.getBean(event.getServletContext(),
                IBeanNames.ENTITY_SERVICE);
            entityService.synchroniseDatabase();

            // Convert: StringDate to Date
            ConvertUtils.register(new DateConverter(), java.util.Date.class);
            ConvertUtils.register(new BooleanConverter(Boolean.FALSE), Boolean.class);
            ConvertUtils.register(new BigDecimalConverter(BigDecimal.ZERO), BigDecimal.class);
            LOG.info("ConvertUtils has been initialised.");
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            LOG.error("Failed to do initialisation: " + e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see org.springframework.web.context.ContextLoaderListener#contextDestroyed(javax.servlet.ServletContextEvent)
     */
    @Override
    public void contextDestroyed(ServletContextEvent event)
    {
        ConvertUtils.deregister();

        //reset to default (empty)
        //XmlUtils.setDefaultJAXP();

        super.contextDestroyed(event);
    }

}