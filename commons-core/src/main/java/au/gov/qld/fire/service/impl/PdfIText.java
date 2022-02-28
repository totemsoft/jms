package au.gov.qld.fire.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import org.apache.commons.jxpath.JXPathContext;
import org.apache.commons.lang.StringUtils;

import au.gov.qld.fire.domain.IBase;
import au.gov.qld.fire.service.Pdf;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.AcroFields;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.XfaForm;
/*
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.XfaForm;
*/
/**
 * 
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class PdfIText extends BasePdf implements Pdf
{

    /* (non-Javadoc)
     * @see au.gov.qld.fire.service.Pdf#read(java.io.InputStream, java.util.Map)
     */
    public void read(InputStream template, Map<String, Object> params)
        throws IOException
    {
        PdfReader pdfTemplate = null;
        try {
        	pdfTemplate = new PdfReader(template);
        	AcroFields form = pdfTemplate.getAcroFields();
            XfaForm xfa = form.getXfa();
            System.out.println(xfa.isXfaPresent() ? "XFA form" : "AcroForm");
            //params.put(name, name);
            for (Object name : form.getFields().keySet()) {
            	if (name != null) {
                	System.out.println(name);
                	params.put(name.toString(), name);
            	}
            }
		} catch (IOException e) {
			throw e;
		} catch (Exception e) {
			throw new IOException(e.getMessage(), e);
        } finally {
        	if (pdfTemplate != null) {
        		pdfTemplate.close();
        	}
        }
    }

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.service.Pdf#write(byte[], java.util.Map, java.io.InputStream, java.io.OutputStream)
	 */
	@Override
	public void write(byte[] template, Map<String, Object> dataMap, InputStream dataStream, OutputStream output) throws IOException
	{
		write(new PdfReader(template), dataMap, dataStream, output);
    }

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.service.Pdf#write(java.io.InputStream, java.util.Map, java.io.InputStream, java.io.OutputStream)
	 */
	public void write(InputStream template, Map<String, Object> dataMap, InputStream dataStream, OutputStream output)
	    throws IOException
	{
		write(new PdfReader(template), dataMap, dataStream, output);
	}

	private void write(PdfReader pdfTemplate, Map<String, Object> dataMap, InputStream dataStream, OutputStream output) throws IOException
	{
        PdfStamper stamper = null;
        try {
        	stamper = new PdfStamper(pdfTemplate, output);
        	AcroFields form = stamper.getAcroFields();
            // 
            if (dataMap != null) {
                Object contextBean = dataMap.get(IBase.CONTEXT_BEAN);
                JXPathContext context = null;
                if (contextBean != null) {
                	context = JXPathContext.newContext(contextBean);
                	context.setLenient(true);
                }
                for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
                	String name = entry.getKey();
                    //
                	Object value = entry.getValue();
                	if (value instanceof String && StringUtils.isNotBlank(value.toString())) {
                    	if (context != null) {
                    		value = context.getValue(value.toString());
                    	}
                    	if (value != null) {
                    		form.setField(name, value.toString());
                    	}
                	}
                }
            }
		} catch (IOException e) {
			throw e;
		} catch (Exception e) {
			throw new IOException(e.getMessage(), e);
        } finally {
        	if (stamper != null) {
				try {
					stamper.close();
				} catch (DocumentException e) {
					throw new IOException(e.getMessage(), e);
				}
        	}
        	if (pdfTemplate != null) {
        		pdfTemplate.close();
        	}
        }
    }

}