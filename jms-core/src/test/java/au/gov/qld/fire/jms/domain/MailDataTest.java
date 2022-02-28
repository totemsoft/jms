package au.gov.qld.fire.jms.domain;

import java.io.InputStream;
import java.net.URL;

import junit.framework.TestCase;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import au.gov.qld.fire.domain.MailData;
import au.gov.qld.fire.domain.document.TemplateEnum;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class MailDataTest extends TestCase
{

    /** logger. */
    private final static Logger LOG = Logger.getLogger(MailDataTest.class);

    /**
     * Test method for {@link au.gov.qld.fire.domain.MailData#MailData(java.net.URL)}.
     */
    public final void testMailDataURL()
    {
        InputStream template = null;
        try
        {
            if (template == null)
            {
                URL templateUrl = getClass().getResource(TemplateEnum.EMAIL_NEW_USER.getPath());
                template = templateUrl.openStream();
            }
            MailData data = new MailData(template);
            assertNotNull(data);
            assertTrue(data.getText().indexOf("</") > 0);
            String text = data.getText();
            boolean isHtml = text.indexOf("<") >= 0 && text.indexOf("</") > 0;
            assertTrue(isHtml);
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            fail(e.getMessage());
        }
        finally
        {
            IOUtils.closeQuietly(template);
        }
    }

}