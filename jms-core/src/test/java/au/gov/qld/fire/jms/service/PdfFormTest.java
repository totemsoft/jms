package au.gov.qld.fire.jms.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.inject.Inject;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;

import junit.framework.Assert;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.springframework.oxm.Marshaller;

import au.gov.qld.fire.BaseTestCase;
import au.gov.qld.fire.domain.IBase;
import au.gov.qld.fire.domain.refdata.TemplateTypeEnum;
import au.gov.qld.fire.jms.dao.FileDao;
import au.gov.qld.fire.jms.domain.ConvertUtils;
import au.gov.qld.fire.jms.domain.file.File;
import au.gov.qld.fire.jms.domain.mailOut.OM89Form;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class PdfFormTest extends BaseTestCase
{

	@Inject private DocumentService documentService;

    /** oxm */
    @Inject private Marshaller marshaller;
    //@Inject private Unmarshaller unmarshaller;

    @Inject private FileDao fileDao;

	@Test
	public final void testReadDocument() {
		//final String name = "xfa";
    	//final String name = "OM89_Form";
    	final String name = "Form1";
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

	/**
	 * Static XFA Form - XFA, or XML Forms Architecture, is an XML-based forms architecture
	 * introduced by Adobe in 2003 (with PDF version 1.5).
	 * Internally, forms objects are represented in XML, and creating XFA forms
	 * for use in Adobe Reader requires Adobe LiveCycle Designer.
	 * Static XFA Forms contain a predefined set of form fields which display in a predetermined fashion.
	 * XFA Forms are not compatible with Adobe Reader prior to version 6.
	 * @throws IOException 
	 */
	@Test
	public final void testWriteDocumentXFA() throws IOException {
		final Long fileId = 13567L;
		//final String name = "xfa"; // This PDF contains a static non-shell XFA.
    	final String name = "Form1"; // This PDF contains a static non-shell XFA.
        InputStream templateStream = null;
        OutputStream output = null;
        try {
    		File file = fileDao.findById(fileId);
    		//
            URL templateUrl = getClass().getResource("/pdf/" + name + ".pdf");
    		templateStream = templateUrl.openStream();
    		//
    		Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
    		dataMap.put(IBase.CONTEXT_BEAN, file);
    		InputStream propsInputStream = getClass().getResource("/pdf/" + name + ".txt").openStream();
    		Properties props = new Properties();
    		props.load(propsInputStream);
    		for (Entry<Object, Object> entry : props.entrySet()) {
    			dataMap.put(entry.getKey().toString(), entry.getValue());
    		}
			propsInputStream.close();
        	//
		    output = new FileOutputStream("src/test/resources/pdf/out/" + name + "_output.pdf");
		    //
    		documentService.writeDocument(TemplateTypeEnum.PDF_FORM, templateStream, dataMap, null, output);
        }
        catch (Exception e) {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
        finally {
            output.close();
            templateStream.close();
        }
	}

	@Test
	public final void testOM89Form() {
		final Long fileId = 13567L;
    	final String name = "OM89_Form"; // This PDF contains a static non-shell XFA.
        InputStream templateStream = null;
        OutputStream output = null;
        try {
    		File file = fileDao.findById(fileId);
    		//
            URL templateUrl = getClass().getResource("/pdf/" + name + ".pdf");
    		templateStream = templateUrl.openStream();
    		//
    		Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
    		dataMap.put(IBase.CONTEXT_BEAN, file);
    		InputStream propsInputStream = getClass().getResource("/pdf/" + name + ".txt").openStream();
    		Properties props = new Properties();
    		props.load(propsInputStream);
    		for (Entry<Object, Object> entry : props.entrySet()) {
    			dataMap.put(entry.getKey().toString(), entry.getValue());
    		}
			propsInputStream.close();
    		//
        	OM89Form form = ConvertUtils.convert(file, null);
        	ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Result result = new StreamResult(baos);
           	marshaller.marshal(form, result);
        	InputStream dataStream = new ByteArrayInputStream(baos.toByteArray());
        	//
		    output = new FileOutputStream("src/test/resources/pdf/out/" + name + "_output.pdf");
		    //
    		documentService.writeDocument(TemplateTypeEnum.PDF_FORM, templateStream, dataMap, dataStream, output);
        }
        catch (Exception e) {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
        finally {
            IOUtils.closeQuietly(output);
            IOUtils.closeQuietly(templateStream);
        }
	}

}