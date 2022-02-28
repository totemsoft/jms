package au.gov.qld.fire.jms.service.impl;

import java.io.InputStream;

import org.apache.commons.io.IOUtils;

import au.gov.qld.fire.jms.service.HelpService;
import au.gov.qld.fire.service.ServiceException;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class HelpServiceImpl implements HelpService
{

    /** logger. */
    //private static final Logger LOG = Logger.getLogger(HelpServiceImpl.class);
    /** The location of all default help files. */
    private static final String HELP_DIR = "/au/gov/qld/fire/jms/help";

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.HelpService#getHelp(java.lang.String, java.lang.String)
     */
    public String getHelp(String module, String path) throws ServiceException
    {
        try
        {
            //static html help
            InputStream helpSource = getClass().getResourceAsStream(
                HELP_DIR + module + path + ".html");
            if (helpSource != null)
            {
                try
                {
                    return IOUtils.toString(helpSource);
                }
                finally
                {
                    IOUtils.closeQuietly(helpSource);
                }
            }
            //no help found
            return "*** TBD ***<br/>" + module + path;
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

}