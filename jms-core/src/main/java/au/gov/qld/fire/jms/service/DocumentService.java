package au.gov.qld.fire.jms.service;

import java.util.List;

import javax.activation.DataHandler;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import au.gov.qld.fire.domain.document.Document;
import au.gov.qld.fire.domain.document.FcaDocument;
import au.gov.qld.fire.domain.document.Template;
import au.gov.qld.fire.domain.security.User;
import au.gov.qld.fire.jms.domain.Fca;
import au.gov.qld.fire.jms.domain.action.BaseAction;
import au.gov.qld.fire.jms.domain.file.File;
import au.gov.qld.fire.jms.domain.job.JobRequest;
import au.gov.qld.fire.jms.domain.report.ReportSearchCriteria;
import au.gov.qld.fire.service.ServiceException;

/*
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public interface DocumentService extends au.gov.qld.fire.service.DocumentService
{

	/**
	 * 
	 * @param fca
	 * @return
	 * @throws ServiceException
	 */
	List<FcaDocument> findFcaDocuments(Fca fca) throws ServiceException;

	String updateName(Fca fca, String oldName, String newName) throws ServiceException;

	void uploadFile(Fca fca, String dir, DataHandler dataHandler) throws ServiceException;

    /**
     * Parameter replacement.
     * @param text content document (template)
     * @param user reference properties, eg %[user.contact.fullName]%
     * @param file reference properties (can be null), eg %[file.owner.contact.fullName]%
     * @return
     */
    String updateParameters(String text, User user, File file);

    /**
     * Parameter replacement.
     * @param text content document (template)
     * @param user reference properties, eg %[user.contact.fullName]%
     * @param jobRequest reference properties (can be null), eg %[file.owner.contact.fullName]%
     * @return
     */
    String updateParameters(String text, User user, JobRequest jobRequest);

    /**
     * 
     * @param template
     * @throws ServiceException
     */
    void saveReport(Template template) throws ServiceException;

    /**
     * Create action document based on template and using baseAction data.
     * @param template Document template.
     * @param baseAction - optional
     * @return
     * @throws ServiceException
     */
    Document createActionDocument(Template template, BaseAction baseAction) throws ServiceException;

    /**
     * Create report document based on template and using report criteria.
     * @param template Report template.
     * @param criteria Report criteria.
     * @return
     * @throws ServiceException
     */
    Document createReportDocument(Template template, ReportSearchCriteria criteria)
        throws ServiceException;

}