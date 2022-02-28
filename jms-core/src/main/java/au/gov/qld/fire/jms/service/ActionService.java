package au.gov.qld.fire.jms.service;

import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import au.gov.qld.fire.domain.refdata.ContentTypeEnum;
import au.gov.qld.fire.jms.domain.action.BaseActionTodo;
import au.gov.qld.fire.jms.domain.action.CallSearchCriteria;
import au.gov.qld.fire.jms.domain.action.EnquirySearchCriteria;
import au.gov.qld.fire.jms.domain.action.FileAction;
import au.gov.qld.fire.jms.domain.action.FileActionRequest;
import au.gov.qld.fire.jms.domain.action.FileActionTodo;
import au.gov.qld.fire.jms.domain.action.JobAction;
import au.gov.qld.fire.jms.domain.action.JobActionRequest;
import au.gov.qld.fire.jms.domain.action.JobActionTodo;
import au.gov.qld.fire.jms.domain.action.MailSearchCriteria;
import au.gov.qld.fire.jms.domain.action.TodoSearchCriteria;
import au.gov.qld.fire.jms.domain.file.File;
import au.gov.qld.fire.jms.domain.job.JobSearchCriteria;
import au.gov.qld.fire.jms.domain.mail.MailBatch;
import au.gov.qld.fire.jms.domain.mail.MailBatchFile;
import au.gov.qld.fire.jms.domain.mail.MailStatusEnum;
import au.gov.qld.fire.jms.domain.refdata.ActionType;
import au.gov.qld.fire.service.ServiceException;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public interface ActionService
{

	/**
	 * 
	 * @param criteria
	 * @return
	 * @throws ServiceException
	 */
	List<BaseActionTodo> findCallAction(CallSearchCriteria criteria) throws ServiceException;
	List<BaseActionTodo> findEnquiryAction(EnquirySearchCriteria criteria) throws ServiceException;
    List<BaseActionTodo> findMailOutAction(MailSearchCriteria criteria) throws ServiceException;

    MailBatch findMailBatch(Long batchId) throws ServiceException;
	List<MailBatch> findMailBatch(MailSearchCriteria criteria) throws ServiceException;
	List<MailBatchFile> findMailBatchFile(MailSearchCriteria criteria) throws ServiceException;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	void saveMailStatus(Long id, MailStatusEnum status) throws ServiceException;

	/**
     * 
     * @param actionCodeId
     * @param fileIds
     * @return
     * @throws ServiceException
     */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	MailBatch createMailBatch(Long actionCodeId, Long[] fileIds) throws ServiceException;
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	List<FileAction> completeMailBatch(Long batchId, Long[] fileIds) throws ServiceException;

    /**
     * Email notification with optional attachments zipped (if Letter action type)
     * @param actions
     * @throws ServiceException
     */
    void notifyCompletedActions(List<FileAction> actions) throws ServiceException;

    /**
     * 
     * @param criteria
     * @return
     * @throws ServiceException
     */
    List<FileActionTodo> findFileActionToDo(TodoSearchCriteria criteria) throws ServiceException;

    /**
     * 
     * @param criteria
     * @return
     * @throws ServiceException
     */
    List<JobActionTodo> findJobActionToDo(TodoSearchCriteria criteria) throws ServiceException;

    /**
     * 
     * @param id
     * @return
     * @throws ServiceException
     */
    FileAction findFileActionById(Long id) throws ServiceException;

    /**
     * 
     * @param file
     * @return
     * @throws ServiceException
     */
	List<FileAction> findFileActionsByFile(File file, ActionType actionType) throws ServiceException;

	/**
     * 
     * @param request
     * @return
     * @throws ServiceException
     */
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
	FileAction saveFileAction(FileActionRequest request) throws ServiceException;

    /**
     * Logically delete.
     * @param fileAction
     * @throws ServiceException
     */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    void deleteFileAction(FileAction fileAction) throws ServiceException;

    /**
     * 
     * @param id
     * @return
     * @throws ServiceException
     */
    JobAction findJobActionById(Long id) throws ServiceException;

    /**
     * 
     * @param file
     * @return
     * @throws ServiceException
     */
	List<JobAction> findJobActionsByFile(File file, ActionType actionType) throws ServiceException;

    /**
     * 
     * @param criteria
     * @param dueDate
     * @return
     * @throws ServiceException
     */
    List<JobAction> findJobActionTodoByDueDate(JobSearchCriteria criteria, Date dueDate)
        throws ServiceException;

    /**
     * 
     * @param request
     * @return
     * @throws ServiceException
     */
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
    JobAction saveJobAction(JobActionRequest request) throws ServiceException;

    /**
     * Save request to close job.
     * @param file
     * @param jobAction
     * @param defaultActions
     * @throws ServiceException
     */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    void closeJobAction(File file, JobAction jobAction, List<FileAction> defaultActions)
        throws ServiceException;

    /**
     * Logically delete.
     * @param jobAction
     * @throws ServiceException
     */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    void deleteJobAction(JobAction jobAction) throws ServiceException;

    /**
     * 
     * @param entities
     * @param contentStream
     * @param contentType
     * @throws ServiceException
     */
    void export(List<? extends BaseActionTodo> entities, OutputStream contentStream,
        ContentTypeEnum contentType) throws ServiceException;

}