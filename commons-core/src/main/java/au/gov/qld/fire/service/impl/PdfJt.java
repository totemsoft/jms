package au.gov.qld.fire.service.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;

import au.gov.qld.fire.domain.refdata.ContentTypeEnum;
import au.gov.qld.fire.service.Pdf;
import au.gov.qld.fire.service.ServiceException;
import au.gov.qld.fire.util.IOUtils;

import com.adobe.internal.io.ByteWriter;
import com.adobe.internal.io.InputStreamByteReader;
import com.adobe.internal.io.RandomAccessFileByteWriter;
import com.adobe.pdfjt.core.exceptions.PDFIOException;
import com.adobe.pdfjt.core.license.LicenseManager;
import com.adobe.pdfjt.pdf.document.PDFDocument;
import com.adobe.pdfjt.pdf.document.PDFDocument.PDFDocumentType;
import com.adobe.pdfjt.pdf.document.PDFOpenOptions;
import com.adobe.pdfjt.pdf.document.PDFSaveFullOptions;
import com.adobe.pdfjt.pdf.document.PDFSaveIncrementalOptions;
import com.adobe.pdfjt.services.digsig.SignatureManager;
import com.adobe.pdfjt.services.fdf.FDFDocument;
import com.adobe.pdfjt.services.fdf.FDFService;
import com.adobe.pdfjt.services.xfa.XFAService;
import com.adobe.pdfjt.services.xfa.XFAService.XFAElement;
import com.adobe.pdfjt.services.xfdf.XFDFService;

/**
 * 
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class PdfJt extends BasePdf implements Pdf
{

    /** logger. */
    private static final Logger LOG = Logger.getLogger(PdfJt.class);

	public PdfJt() throws ServiceException {
		try {
			//LicenseManager.setLicensePath("src/test/resources/06-10-2014.l4je");
			LicenseManager.validateLicense();
		} catch (PDFIOException e) {
			throw new ServiceException(e);
		}
	}

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.service.Pdf#read(java.io.InputStream, java.util.Map)
	 */
	public void read(InputStream template, Map<String, Object> params) throws IOException
	{
		throw new IOException("Not implemented yet ..");
	}

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.service.Pdf#write(byte[], java.util.Map, java.io.InputStream, java.io.OutputStream)
	 */
	public void write(byte[] template, Map<String, Object> dataMap, InputStream dataStream, OutputStream output) throws IOException
	{
		write(new ByteArrayInputStream(template), dataMap, dataStream, output);
	}

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.service.Pdf#write(java.io.InputStream, java.util.Map, java.io.InputStream, java.io.OutputStream)
	 */
	public void write(InputStream template, Map<String, Object> dataMap, InputStream dataStream, OutputStream output) throws IOException
	{
    	PDFDocument pdfTemplate = null;
        try {
            //dataStream = IOUtils.toInputStream("src/test/resources/drop/mailOut/OM89_data.xml");
            //
        	pdfTemplate = PDFDocument.newInstance(new InputStreamByteReader(template), PDFOpenOptions.newInstance());
            PDFDocumentType documentType = XFAService.getDocumentType(pdfTemplate);
            // check XFA document
            if (documentType.isXFA() ||
                documentType == PDFDocumentType.StaticNonShellXFA ||
                documentType == PDFDocumentType.DynamicShellXFA ||
                documentType == PDFDocumentType.DynamicNonShellXFA ||
                documentType == PDFDocumentType.StaticShellXFA) {
                final DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                if (!hasXFAheaders(new FileInputStream("src/test/resources/drop/mailOut/OM89_data.xml"), builder)) {
                	dataStream = addXFAheaders(dataStream, builder);
                }
                if (!XFAService.importElement(pdfTemplate, XFAElement.DATASETS, dataStream)) {
                	throw new IOException("Failed XFAService.importElement(..)");
                }
            } else {
                // check other document the form file type and merge data
                FileID fileId = identFile(dataStream);
                if (fileId == FileID.FDF) {
                    // it's an fdf file, process as such
                    FDFService fdfService = new FDFService(pdfTemplate);
                    FDFDocument fdf = FDFDocument.newInstance(new InputStreamByteReader(dataStream));
                    fdfService.importForm(fdf);
                	fdf.close();
                } else if (fileId == FileID.XML) {
                    // assume it's an XFDF, process as such
                    XFDFService.importFormData(pdfTemplate, dataStream);
                }
            }
            // save, and done
            File file = File.createTempFile("fromPdfTemplate", "." + ContentTypeEnum.APPLICATION_PDF.getDefaultExt());
            RandomAccessFile raf = new RandomAccessFile(file, "rw");
            ByteWriter outputByteWriter = new RandomAccessFileByteWriter(raf);
        	SignatureManager sigMgr = SignatureManager.newInstance(pdfTemplate);
            if (sigMgr.hasUsageRights()) {
                // save incrementally so that we don't invalidate the Usage Rights signature.
                pdfTemplate.save(outputByteWriter, PDFSaveIncrementalOptions.newInstance());
            } else {
            	pdfTemplate.save(outputByteWriter, PDFSaveFullOptions.newInstance());
            }
            IOUtils.copy(file, output);
            if (!file.delete()) {
            	LOG.warn("Failed to delete file: " + file.getCanonicalPath());
            }
        } catch (IOException e) {
        	throw e;
        } catch (Exception e) {
        	throw new IOException(e.getMessage(), e);
		} finally {
            if (pdfTemplate != null) {
            	try { pdfTemplate.close(); }
            	catch (Exception e) { throw new IOException(e.getMessage(), e); }
            }
        }
	}

}