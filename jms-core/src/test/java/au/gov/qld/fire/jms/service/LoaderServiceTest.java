package au.gov.qld.fire.jms.service;

import java.io.InputStream;
import java.net.URL;

import junit.framework.Assert;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import au.gov.qld.fire.Base4TestCase;
import au.gov.qld.fire.jms.domain.file.FileAudit;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class LoaderServiceTest extends Base4TestCase
{

    @Autowired private LoaderService loaderService;

	/**
	 * Test method for {@link au.gov.qld.fire.jms.service.LoaderService#loadOM89Form(java.io.InputStream)}.
	 */
	@Test @Rollback(false)
	public void testLoadOM89Form()
	{
        InputStream template = null;
        try
        {
        	final String name = "/drop/mailOut/OM89_data.xml";
        	//final String name = "/drop/mailOut/OM89_51045-01_2014_03_21_data.xml";
            URL templateUrl = getClass().getResource(name);
            template = templateUrl.openStream();
            FileAudit fileAudit = loaderService.loadOM89Form(name, template);
            Assert.assertTrue(fileAudit.getContent().length > 0);
            System.out.println(new String(fileAudit.getContent()));
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
        finally
        {
            IOUtils.closeQuietly(template);
        }
	}

}
