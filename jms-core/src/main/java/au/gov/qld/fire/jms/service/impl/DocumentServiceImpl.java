package au.gov.qld.fire.jms.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.activation.DataHandler;
import javax.inject.Inject;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.Marshaller;

import au.gov.qld.fire.dao.ReportDao;
import au.gov.qld.fire.dao.SupplierDao;
import au.gov.qld.fire.dao.UserDao;
import au.gov.qld.fire.dao.WorkGroupDao;
import au.gov.qld.fire.domain.document.Document;
import au.gov.qld.fire.domain.document.FcaDocument;
import au.gov.qld.fire.domain.document.Template;
import au.gov.qld.fire.domain.refdata.ContentTypeEnum;
import au.gov.qld.fire.domain.refdata.TemplateTypeEnum;
import au.gov.qld.fire.domain.security.User;
import au.gov.qld.fire.jms.dao.ActionCodeDao;
import au.gov.qld.fire.jms.dao.FileDao;
import au.gov.qld.fire.jms.domain.ConvertUtils;
import au.gov.qld.fire.jms.domain.Fca;
import au.gov.qld.fire.jms.domain.action.BaseAction;
import au.gov.qld.fire.jms.domain.file.File;
import au.gov.qld.fire.jms.domain.job.JobRequest;
import au.gov.qld.fire.jms.domain.mailOut.OM89Form;
import au.gov.qld.fire.jms.domain.refdata.ActionCode;
import au.gov.qld.fire.jms.domain.report.ReportSearchCriteria;
import au.gov.qld.fire.jms.service.DocumentService;
import au.gov.qld.fire.jms.util.FoUtils;
import au.gov.qld.fire.service.ReportService;
import au.gov.qld.fire.service.ServiceException;
import au.gov.qld.fire.util.ThreadLocalUtils;
import au.gov.qld.fire.util.XmlUtils;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class DocumentServiceImpl extends au.gov.qld.fire.service.impl.DocumentServiceImpl implements DocumentService
{

    /** logger. */
    private static final Logger LOG = Logger.getLogger(DocumentServiceImpl.class);

    /** The location of all default reports. */
    private static final String REPORT_DIR = "/au/gov/qld/fire/jms/template/report/";

    private String documentDirectory;

    /** oxm */
    @Inject private Marshaller marshaller;
    //@Inject private Unmarshaller unmarshaller;

    @Inject private ActionCodeDao actionCodeDao;
    @Inject private FileDao fileDao;
    @Inject private ReportDao reportDao;
    @Inject private SupplierDao supplierDao;
    @Inject private UserDao userDao;
    @Inject private WorkGroupDao workGroupDao;

    /** Crystal Report - remoting service (or EJB local stateless bean) */
    @Autowired(required = false) private ReportService reportService;

    public void setDocumentDirectory(String documentDirectory)
    {
        this.documentDirectory = documentDirectory;
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.DocumentService#findFcaDocuments(Fca)
     */
    public List<FcaDocument> findFcaDocuments(Fca fca) throws ServiceException
    {
        String fcaNo = fca == null ? null : fca.getFcaId();
        if (StringUtils.isBlank(fcaNo)) {
            return Collections.emptyList();
        }
        try
        {
            java.io.File dir = new java.io.File(documentDirectory + '/' + fcaNo);
            if (LOG.isDebugEnabled()) LOG.debug("documentDirectory=" + dir.getCanonicalPath());
            if (!dir.exists()) {
                return Collections.emptyList();
            }
            return listDirectory(dir);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    private List<FcaDocument> listDirectory(java.io.File dir) throws IOException
    {
        List<FcaDocument> result = new ArrayList<FcaDocument>();
        for (java.io.File p : dir.listFiles()) {
            String name = p.getName();
            String contentType;
            if (p.isHidden()) {
                continue;
            } else if (p.isDirectory()) {
                contentType = null;
            } else if (p.isFile()) {
                String ext = name.substring(name.lastIndexOf('.') + 1);
                contentType = ContentTypeEnum.findByExt(ext).getContentType();
            } else {
                continue;
            }
            FcaDocument d = new FcaDocument(name, p.getCanonicalPath(), contentType, new Date(p.lastModified()));
            result.add(d);
            //
            if (p.isDirectory()) {
                d.setChildren(listDirectory(p));
            }
        }
        return result;
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.DocumentService#updateName(au.gov.qld.fire.jms.domain.Fca, java.lang.String, java.lang.String)
     */
    public String updateName(Fca fca, String oldName, String newName)
        throws ServiceException
    {
        String fcaNo = fca == null ? null : fca.getFcaId();
        if (StringUtils.isBlank(fcaNo)) {
            throw new ServiceException("Failed to find fca: " + fca);
        }
        try
        {
            java.io.File baseDir = new java.io.File(documentDirectory + '/' + fcaNo);
            if (LOG.isDebugEnabled()) LOG.debug("documentDirectory=" + baseDir.getCanonicalPath());
            if (!baseDir.exists() && !baseDir.mkdir()) {
                throw new ServiceException("Failed to create documentDirectory: " + baseDir.getCanonicalPath());
            }
            java.io.File fromFile = StringUtils.isBlank(oldName) ? null : new java.io.File(baseDir, oldName);
            java.io.File toFile = StringUtils.isBlank(newName) ? null : new java.io.File(baseDir, newName);
            // delete
            if (toFile == null && fromFile.exists()) {
            	if (fromFile.delete()) {
                	return fromFile.getCanonicalPath();
                }
                throw new ServiceException("Failed to delete [" + fromFile.getCanonicalPath() + "]");
            }
            // rename
            if (fromFile != null && toFile != null && fromFile.exists()) {
            	if (fromFile.renameTo(toFile)) {
            		return toFile.getCanonicalPath();
            	}
                throw new ServiceException("Failed to rename [" + fromFile.getCanonicalPath() + "] to [" + toFile.getCanonicalPath() + "]");
            }
            // add
            if (toFile != null && !toFile.exists()) {
            	if (toFile.mkdir()) {
            		return toFile.getCanonicalPath();
            	}
                throw new ServiceException("Failed to create: " + toFile.getCanonicalPath());
            }
            throw new ServiceException("Unhandled combination oldName: " + oldName + ", newName: " + newName);
        }
        catch (ServiceException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
	 * @see au.gov.qld.fire.jms.service.DocumentService#uploadFile(au.gov.qld.fire.jms.domain.Fca, java.lang.String, javax.activation.DataHandler)
	 */
	public void uploadFile(Fca fca, String dir, DataHandler dataHandler)
	    throws ServiceException
	{
        String fcaNo = fca == null ? null : fca.getFcaId();
        if (StringUtils.isBlank(fcaNo)) {
            throw new ServiceException("Failed to find fca: " + fca);
        }
        try
        {
            java.io.File baseDir = new java.io.File(documentDirectory + '/' + fcaNo + '/' + dir);
            java.io.File file = new java.io.File(baseDir, dataHandler.getName());
            if (!file.exists() && !file.createNewFile()) {
                throw new ServiceException("Failed to create: " + file.getCanonicalPath());
            }
    		InputStream input = dataHandler.getInputStream();
		    au.gov.qld.fire.util.IOUtils.copy(input, file);
		    input.close();
        }
        catch (ServiceException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
	}

	/* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.DocumentService#saveReport(au.gov.qld.fire.domain.document.Template)
     */
    public void saveReport(Template template) throws ServiceException
    {
        if (template == null)
        {
            throw new ServiceException("template is required");
        }
        try
        {
            TemplateTypeEnum templateType = template.getTemplateTypeEnum();
            if (TemplateTypeEnum.REPORT_CRYSTAL.equals(templateType))
            {
                reportService.saveReport(template);
            }
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.DocumentService#createDocument(au.gov.qld.fire.jms.domain.document.Template, au.gov.qld.fire.jms.domain.action.BaseAction)
     */
    public Document createActionDocument(Template template, BaseAction baseAction)
        throws ServiceException
    {
        if (template == null) {
            throw new ServiceException("template is required");
        }
        try
        {
            TemplateTypeEnum templateType = template.getTemplateTypeEnum();
            ContentTypeEnum contentType = template.getContentTypeEnum();
            if (contentType == null) {
                throw new ServiceException("Unhandled Template contentType: " + template.getContentType());
            }
            // try to use existing document
            Document document = null; // baseAction == null ? null : baseAction.getDocument();
            if (document == null) {
                document = new Document();
            }
            document.setContentType(contentType.getContentType());
            document.setName(template.getName());
            document.setDescription(baseAction == null ? null : baseAction.getMessage());

            File file = baseAction == null ? null : baseAction.getFile();
            byte[] content = template.getContent();
            if (TemplateTypeEnum.PDF_FORM.equals(templateType)) {
                createPdfForm(template, file.getId(), document);
            } else if (ContentTypeEnum.isBinary(contentType)) {
                // skip binary documents, eg crystal reports
                document.setContent(content);
            } else {
                User user = ThreadLocalUtils.getUser();
                user = user == null ? null : userDao.findById(user.getId()); // refresh user
                String data = updateParameters(new String(content), user, file);
                document.setContent(data.getBytes());
            }
            return document;
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.DocumentService#updateParameters(java.lang.String, au.gov.qld.fire.jms.domain.security.User, au.gov.qld.fire.jms.domain.file.File)
     */
    public String updateParameters(String text, User user, File file)
    {
        file = file == null ? null : fileDao.findById(file.getId()); // reload file
        return updateParameters(text, user, file, "file.");
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.DocumentService#updateParameters(java.lang.String, au.gov.qld.fire.jms.domain.security.User, au.gov.qld.fire.jms.domain.job.JobRequest)
     */
    public String updateParameters(String text, User user, JobRequest jobRequest)
    {
        return updateParameters(text, user, jobRequest, "jobRequest.");
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.service.impl.DocumentServiceImpl#getReportParameters(au.gov.qld.fire.domain.document.Template)
     */
    @Override
    public String[] getReportParameters(Template reportTemplate) throws ServiceException
    {
        TemplateTypeEnum templateType = reportTemplate.getTemplateTypeEnum();

        // XSLT-FO Template to create PDF document
        if (TemplateTypeEnum.REPORT_FOP.equals(templateType))
        {
            return super.getReportParameters(reportTemplate);
        }
        if (TemplateTypeEnum.REPORT_CRYSTAL.equals(templateType))
        {
            return reportService.getReportParameters(reportTemplate);
        }
        throw new ServiceException("Unhandled templateType: " + templateType);
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.DocumentService#createReportDocument(au.gov.qld.fire.jms.domain.document.Template, au.gov.qld.fire.jms.domain.report.ReportSearchCriteria)
     */
    public Document createReportDocument(Template template, ReportSearchCriteria criteria)
        throws ServiceException
    {
        if (template == null)
        {
            throw new ServiceException("reportTemplate is required");
        }
        if (criteria == null)
        {
            throw new ServiceException("criteria is required");
        }
        try
        {
            TemplateTypeEnum templateType = template.getTemplateTypeEnum();
            // crystal
            if (TemplateTypeEnum.REPORT_CRYSTAL.equals(templateType))
            {
                return createCrystal(template, criteria);
            }
            //
            Document document = new Document();
            document.setName(template.getName());
            document.setDescription(template.getDescription());
            if (TemplateTypeEnum.PDF_FORM.equals(templateType))
            {
                createPdfForm(template, criteria.getFileId(), document);
                return document;
            }
            // XSLT-FO Template to create PDF document
            if (TemplateTypeEnum.REPORT_FOP.equals(templateType))
            {
                createXslFo(template, criteria, document);
                return document;
            }
            throw new ServiceException("Unhandled templateType: " + templateType);
        }
        catch (ServiceException e)
        {
            //LOG.error(e.getMessage(), e);
            throw e;
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    private Document createCrystal(Template template, ReportSearchCriteria criteria) throws Exception
    {
        criteria.loadCustomParameters();
        // convert to base class
        au.gov.qld.fire.domain.report.ReportSearchCriteria crystalCriteria = new au.gov.qld.fire.domain.report.ReportSearchCriteria();
        BeanUtils.copyProperties(criteria, crystalCriteria, new String[0]);
        // crystal module will process customParameters only
        return reportService.createReportDocument(template, crystalCriteria);
    }

    private void createPdfForm(Template template, Long fileId, Document document) throws Exception
    {
        // produce PDF
        java.io.File pdfFile = java.io.File.createTempFile(template.getCode(), ".pdf");
        OutputStream output = new FileOutputStream(pdfFile);
        try {
            File file = fileDao.findById(fileId); // refresh file
            InputStream dataStream = null;
            if (pdfRequreXml) {
                OM89Form form = ConvertUtils.convert(file, null);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                Result result = new StreamResult(baos);
                marshaller.marshal(form, result);
                dataStream = new ByteArrayInputStream(baos.toByteArray());
            }
            fromPdfTemplate(template, file, dataStream, output);
        } finally {
            IOUtils.closeQuietly(output);
        }
        // save generated PDF to document
        InputStream source = new FileInputStream(pdfFile);
        try {
            document.setContent(IOUtils.toByteArray(source));
            document.setContentType(ContentTypeEnum.APPLICATION_PDF.getContentType());
        } finally {
            IOUtils.closeQuietly(source);
            if (!pdfFile.delete()) {
                LOG.warn("Could not delete: " + pdfFile);
            }
        }
    }

    private void createXslFo(Template template, ReportSearchCriteria criteria, Document document) throws Exception
    {
        final String reportCode = template.getCode();
        // 1). get report data in xml format
        java.io.File reportFile = java.io.File.createTempFile(reportCode, ".xml");
        OutputStream result = new FileOutputStream(reportFile);
        try {
            String sqlQuery = template.getSqlQuery();
            // add data (if any)
            // workGroup
            Long workGroupId = criteria.getWorkGroupId();
            if (workGroupId != null && workGroupId > 0L) {
                criteria.setWorkGroup(workGroupDao.findById(workGroupId));
            }
            // supplier
            Long supplierId = criteria.getSupplierId();
            if (supplierId != null && supplierId > 0L) {
                criteria.setSupplier(supplierDao.findById(supplierId));
            }
            // actionCodeIds
            Long[] actionCodeIds = criteria.getActionCodeIds();
            if (!ArrayUtils.isEmpty(actionCodeIds)) {
                ActionCode[] actionCodes = new ActionCode[actionCodeIds.length];
                for (int i = 0; i < actionCodeIds.length; i++) {
                    actionCodes[i] = actionCodeDao.findById(actionCodeIds[i]);
                }
                criteria.setActionCodes(actionCodes);
            }
            // nextCompletedActionCodeId
            Long nextCompletedActionCodeId = criteria.getNextCompletedActionCodeId();
            if (nextCompletedActionCodeId != null && nextCompletedActionCodeId > 0) {
                criteria.setNextCompletedActionCode(actionCodeDao.findById(nextCompletedActionCodeId));
            }            
            //
            reportDao.getReportData(reportCode, sqlQuery == null ? reportCode : sqlQuery, criteria, result);
        } finally {
            IOUtils.closeQuietly(result);
        }

        // 2). transform raw xmlReport (optional - if <reportname>.transform.xslt exists)
        InputStream transformation = getClass().getResourceAsStream(REPORT_DIR + reportCode + ".transform.xslt");
        if (transformation != null) {
            InputStream source = new FileInputStream(reportFile);
            java.io.File transformedReportFile = java.io.File.createTempFile(reportCode, ".transform.xml");
            result = new FileOutputStream(transformedReportFile);
            try {
                XmlUtils.transform(new StreamSource(source), new StreamResult(result),
                    new StreamSource(transformation));
            } finally {
                IOUtils.closeQuietly(source);
                IOUtils.closeQuietly(result);
                IOUtils.closeQuietly(transformation);
            }
            // rename
            if (!transformedReportFile.renameTo(reportFile)) {
                throw new ServiceException("Could not rename: " + transformedReportFile + " to " + reportFile);
            }
        }

        // 3). Produce fo file via xslt-fo transformation
        InputStream source = new FileInputStream(reportFile);
        java.io.File foFile = java.io.File.createTempFile(reportCode, ".fo");
        result = new FileOutputStream(foFile);
        transformation = new ByteArrayInputStream(template.getContent());
        try {
            XmlUtils.transform(new StreamSource(source), new StreamResult(result),
                new StreamSource(transformation), criteria.getCustomParameters());
            if (LOG.isDebugEnabled()) LOG.debug("Success creating Report Data FO!");
        } finally {
            IOUtils.closeQuietly(source);
            IOUtils.closeQuietly(result);
            IOUtils.closeQuietly(transformation);
            if (!LOG.isDebugEnabled() && !reportFile.delete()) {
                LOG.warn("Could not delete: " + reportFile);
            }
        }

        // 4). Produce PDF from fo file (using FOP)
        source = new FileInputStream(foFile);
        java.io.File pdfFile = java.io.File.createTempFile(reportCode, ".pdf");
        result = new FileOutputStream(pdfFile);
        try {
            FoUtils.transformFo2Pdf(source, result);
            if (LOG.isDebugEnabled()) LOG.debug("Success creating Report Data PDF!");
        } finally {
            IOUtils.closeQuietly(source);
            if (!foFile.delete()) {
                LOG.warn("Could not delete: " + foFile);
            }
        }

        // 5). save generated pdf to document
        source = new FileInputStream(pdfFile);
        try {
            document.setContent(IOUtils.toByteArray(source));
            document.setContentType(ContentTypeEnum.APPLICATION_PDF.getContentType());
        } finally {
            IOUtils.closeQuietly(source);
            if (!LOG.isDebugEnabled() && !pdfFile.delete()) {
                LOG.warn("Could not delete: " + pdfFile);
            }
        }
    }

}