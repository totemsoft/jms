package au.gov.qld.fire.service;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import au.gov.qld.fire.domain.document.Document;
import au.gov.qld.fire.domain.document.Template;
import au.gov.qld.fire.domain.refdata.TemplateTypeEnum;
import au.gov.qld.fire.domain.report.ReportDoc;
import au.gov.qld.fire.domain.security.User;

/*
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Service
public interface DocumentService
{

    /**
     * Parameter replacement.
     * @param text content document (template)
     * @param user reference properties, eg %[user.contact.fullName]%
     * @return
     */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    String updateParameters(String text, User user);

    /**
     * 
     * @param reportTemplate
     * @return
     * @throws ServiceException
     */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    String[] getReportParameters(Template reportTemplate) throws ServiceException;

    /**
     * 
     * @param documentId
     * @return
     * @throws ServiceException
     */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    Document findDocumentById(Long documentId) throws ServiceException;

    /**
     * 
     * @param reportDoc
     * @throws ServiceException
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    void saveReportDoc(ReportDoc reportDoc) throws ServiceException;

    /**
     * All ReportDoc(s) created using this template by logged user.
     * @param template
     * @return
     * @throws ServiceException
     */
    List<ReportDoc> findReportDocs(Template template) throws ServiceException;

    /**
     * 
     * @param reportDocId
     * @return
     * @throws ServiceException
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    ReportDoc findReportDocById(Long reportDocId) throws ServiceException;

    /**
     * 
     * @param reportDoc
     * @throws ServiceException
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    void deleteReportDoc(ReportDoc reportDoc) throws ServiceException;

    /**
     * FILLING OUT INTERACTIVE FORMS
     * http://itextpdf.com/book/chapter.php?id=8
     * http://examples.itextpdf.com/src/part2/chapter08/ReaderEnabledForm.java
     * @param templateType
     * @param template
     * @param params
     * @throws ServiceException
     */
    void readDocument(TemplateTypeEnum templateType, InputStream template, Map<String, Object> params)
    	throws ServiceException;

    /**
     * @param templateType
     * @param templateStream
     * @param dataMap - template parameters
     * @param dataStream - xml feed
     * @param output
     * @throws ServiceException
     */
    //@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    void writeDocument(TemplateTypeEnum templateType, InputStream templateStream, Map<String, Object> dataMap, InputStream dataStream, OutputStream output)
        throws ServiceException;

}