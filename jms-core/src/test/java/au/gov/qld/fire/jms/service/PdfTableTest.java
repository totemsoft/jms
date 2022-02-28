package au.gov.qld.fire.jms.service;

import java.io.InputStream;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import au.gov.qld.fire.BaseTestCase;
import au.gov.qld.fire.domain.refdata.TemplateTypeEnum;
import junit.framework.Assert;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class PdfTableTest extends BaseTestCase
{

    @Inject private DocumentService documentService;

    @Test
    public final void testReadDocument() {
        final String name = "table";
        InputStream template = null;
        try
        {
            URL templateUrl = getClass().getResource("/pdf/" + name + ".pdf");
            template = templateUrl.openStream();
            Map<String, Object> params = new LinkedHashMap<String, Object>();
            documentService.readDocument(TemplateTypeEnum.PDF_FORM, template, params);
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