package au.gov.qld.fire.jms.service.impl;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import au.com.bytecode.opencsv.CSVWriter;
import au.gov.qld.fire.dao.BaseDao;
import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.dao.TemplateDao;
import au.gov.qld.fire.dao.UserDao;
import au.gov.qld.fire.domain.MailData;
import au.gov.qld.fire.domain.document.Document;
import au.gov.qld.fire.domain.document.Template;
import au.gov.qld.fire.domain.document.TemplateEnum;
import au.gov.qld.fire.domain.refdata.ContentTypeEnum;
import au.gov.qld.fire.domain.refdata.TemplateTypeEnum;
import au.gov.qld.fire.domain.security.User;
import au.gov.qld.fire.jms.dao.ActionCodeDao;
import au.gov.qld.fire.jms.dao.ActionOutcomeDao;
import au.gov.qld.fire.jms.dao.ActionWorkflowDao;
import au.gov.qld.fire.jms.dao.ArchiveDao;
import au.gov.qld.fire.jms.dao.FileActionDao;
import au.gov.qld.fire.jms.dao.FileArchiveDao;
import au.gov.qld.fire.jms.dao.FileDao;
import au.gov.qld.fire.jms.dao.FileStatusDao;
import au.gov.qld.fire.jms.dao.JobActionDao;
import au.gov.qld.fire.jms.dao.JobDao;
import au.gov.qld.fire.jms.dao.MailBatchDao;
import au.gov.qld.fire.jms.dao.MailBatchFileDao;
import au.gov.qld.fire.jms.dao.RfiDao;
import au.gov.qld.fire.jms.domain.action.ActionOutcome;
import au.gov.qld.fire.jms.domain.action.ActionWorkflow;
import au.gov.qld.fire.jms.domain.action.BaseAction;
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
import au.gov.qld.fire.jms.domain.file.Archive;
import au.gov.qld.fire.jms.domain.file.File;
import au.gov.qld.fire.jms.domain.file.FileArchive;
import au.gov.qld.fire.jms.domain.job.Job;
import au.gov.qld.fire.jms.domain.job.JobSearchCriteria;
import au.gov.qld.fire.jms.domain.mail.MailBatch;
import au.gov.qld.fire.jms.domain.mail.MailBatchFile;
import au.gov.qld.fire.jms.domain.mail.MailStatusData;
import au.gov.qld.fire.jms.domain.mail.MailStatusEnum;
import au.gov.qld.fire.jms.domain.refdata.ActionCode;
import au.gov.qld.fire.jms.domain.refdata.ActionType;
import au.gov.qld.fire.jms.domain.refdata.ActionTypeEnum;
import au.gov.qld.fire.jms.domain.refdata.FileStatus;
import au.gov.qld.fire.jms.domain.sap.Rfi;
import au.gov.qld.fire.jms.rule.BusinessRule;
import au.gov.qld.fire.jms.rule.RuleException;
import au.gov.qld.fire.jms.service.ActionService;
import au.gov.qld.fire.jms.service.DocumentService;
import au.gov.qld.fire.jms.service.EntityService;
import au.gov.qld.fire.service.EmailService;
import au.gov.qld.fire.service.ServiceException;
import au.gov.qld.fire.service.SmsService;
import au.gov.qld.fire.service.ValidationException;
import au.gov.qld.fire.util.DateUtils;
import au.gov.qld.fire.util.Formatter;
import au.gov.qld.fire.util.ThreadLocalUtils;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class ActionServiceImpl implements ActionService
{

    /** logger. */
    private static final Logger LOG = Logger.getLogger(ActionServiceImpl.class);

    @Autowired private ActionCodeDao actionCodeDao;

    @Autowired private ActionOutcomeDao actionOutcomeDao;

    @Autowired private ActionWorkflowDao actionWorkflowDao;

    @Autowired private ArchiveDao archiveDao;

    @Autowired private FileDao fileDao;

    @Autowired private FileActionDao fileActionDao;

    @Autowired private FileArchiveDao fileArchiveDao;

    @Autowired private FileStatusDao fileStatusDao;

    @Autowired private JobDao jobDao;

    @Autowired private JobActionDao jobActionDao;

    @Autowired private MailBatchDao mailBatchDao;

    @Autowired private MailBatchFileDao mailBatchFileDao;

    @Autowired private RfiDao rfiDao;

    @Autowired private TemplateDao templateDao;

    @Autowired private UserDao userDao;

    @Autowired private BusinessRule businessRule;

    @Autowired private DocumentService documentService;

    @Autowired private EntityService entityService;

    @Autowired private EmailService emailService;

    @Autowired private SmsService smsService;

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.ActionService#findFileActionById(java.lang.Long)
     */
    public FileAction findFileActionById(Long id) throws ServiceException
    {
        try
        {
            return fileActionDao.findById(id);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.ActionService#findFileActionsByFile(au.gov.qld.fire.jms.domain.file.File)
     */
    public List<FileAction> findFileActionsByFile(File file, ActionType actionType) throws ServiceException
    {
        try
        {
            if (file == null || file.getId() == null)
            {
                return Collections.emptyList();
            }
            return fileActionDao.findByFileActionType(file, actionType);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.ActionService#findJobActionById(java.lang.Long)
     */
    public JobAction findJobActionById(Long id) throws ServiceException
    {
        try
        {
            return jobActionDao.findById(id);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.ActionService#findJobActionsByFile(au.gov.qld.fire.jms.domain.file.File)
     */
    public List<JobAction> findJobActionsByFile(File file, ActionType actionType) throws ServiceException
    {
        try
        {
            if (file == null || file.getId() == null)
            {
                return Collections.emptyList();
            }
            return jobActionDao.findByFileActionType(file, actionType);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.ActionService#findJobActionTodoByDueDate(JobSearchCriteria, java.util.Date)
     */
    public List<JobAction> findJobActionTodoByDueDate(JobSearchCriteria criteria, Date dueDate) throws ServiceException
    {
        try
        {
            return jobActionDao.findJobActionTodoByDueDate(criteria, dueDate);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    private List<BaseActionTodo> findActionToDo(TodoSearchCriteria criteria)
        throws ServiceException
    {
        List<BaseActionTodo> result = new ArrayList<BaseActionTodo>();
        result.addAll(findFileActionToDo(criteria));
        result.addAll(findJobActionToDo(criteria));
        return result;
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.ActionService#findCallAction(au.gov.qld.fire.jms.domain.action.CallSearchCriteria)
     */
    public List<BaseActionTodo> findCallAction(CallSearchCriteria criteria)
        throws ServiceException
    {
        return findActionToDo(criteria);
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.ActionService#findEnquiryAction(au.gov.qld.fire.jms.domain.action.EnquirySearchCriteria)
     */
    public List<BaseActionTodo> findEnquiryAction(EnquirySearchCriteria criteria)
        throws ServiceException
    {
        return findActionToDo(criteria);
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.ActionService#findMailBatch(java.lang.Long)
     */
    @Override
    public MailBatch findMailBatch(Long batchId) throws ServiceException
    {
        try
        {
            return mailBatchDao.findById(batchId);
        }
        catch (Exception e)
        {
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.ActionService#findMailBatch(au.gov.qld.fire.jms.domain.action.MailOutSearchCriteria)
     */
    public List<MailBatch> findMailBatch(MailSearchCriteria criteria)
        throws ServiceException
    {
        try
        {
            return mailBatchDao.findByCriteria(criteria);
        }
        catch (Exception e)
        {
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.ActionService#findMailBatchFile(au.gov.qld.fire.jms.domain.action.MailSearchCriteria)
     */
    public List<MailBatchFile> findMailBatchFile(MailSearchCriteria criteria)
        throws ServiceException
    {
        try
        {
            return mailBatchFileDao.findByCriteria(criteria);
        }
        catch (Exception e)
        {
            throw new ServiceException(e);
        }
    }

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.jms.service.ActionService#saveMailStatus(java.lang.Long, au.gov.qld.fire.jms.domain.mail.MailBatchStatusEnum)
	 */
	public void saveMailStatus(Long id, MailStatusEnum status) throws ServiceException
	{
        try
        {
        	MailBatchFile mbf = mailBatchFileDao.findById(id);
        	if (status.equals(mbf.getStatus())) {
        		return;
        	}
        	FileAction fa = mbf.getFileAction();
        	if (!fa.isCompleted()) {
        		throw new ServiceException("Complete Batch Item before changing status.");
        	}
        	Date today = DateUtils.getCurrentDate();
        	MailStatusData msd = mbf.getMailStatus();
    		msd.setStatus(status);
    		msd.setStatusDate(today);
    		// create matching outcome actions (completed)
    		ActionCode actionCode = fa.getActionCode();
    		if (MailStatusEnum.RECEIVED.equals(status) || MailStatusEnum.RTS.equals(status)) {
    			String outcomeName = actionCode.getCode() + ' ' + status.getName();
    			ActionOutcome actionOutcome = actionOutcomeDao.findByName(outcomeName);
    			if (actionOutcome != null) {
                    List<ActionWorkflow> actionWorkflows = entityService.findActionWorkflowByActionCodeOutcome(actionCode, actionOutcome);
                    for (ActionWorkflow aw : actionWorkflows) {
                        ActionCode ac = aw.getNextActionCode();
                        FileAction a = new FileAction();
                        a.setActionCode(ac);
                        a.setFile(fa.getFile());
                        a.setParentFileAction(fa);
                        a.setNotation(ac.getNotation());
                        a.setDueDate(DateUtils.addDays(today, aw.getNextDueDays()));
                        a.setCompletedBy(ThreadLocalUtils.getUser());
                        a.setCompletedDate(today);
                        fileActionDao.save(a);
                    }
    			} else {
    				LOG.warn("No outcome [" + outcomeName + "] found, creating new one. Please configure it for [" + actionCode.getCode() + "] actionCode.");
    				actionOutcome = new ActionOutcome();
    				actionOutcome.setName(outcomeName);
    				actionOutcome.setFixed(true);
    				actionOutcomeDao.save(actionOutcome);
    			}
    		}
        }
        catch (ServiceException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            throw new ServiceException(e);
        }
	}

	/* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.ActionService#findMailOutAction(au.gov.qld.fire.jms.domain.action.MailOutSearchCriteria)
     */
    public List<BaseActionTodo> findMailOutAction(MailSearchCriteria criteria)
        throws ServiceException
    {
        try
        {
            List<BaseActionTodo> result = new ArrayList<BaseActionTodo>();
            result.addAll(fileActionDao.findMailOutActionByCriteria(criteria));
            return result;
        }
        catch (Exception e)
        {
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.ActionService#createMailBatch(java.util.List)
     */
    public MailBatch createMailBatch(Long actionCodeId, Long[] fileIds)
        throws ServiceException
    {
        if (actionCodeId == null) {
        	throw new ValidationException("No Action Code selected.");
        }
        if (ArrayUtils.isEmpty(fileIds)) {
        	throw new ValidationException("No FCA(s) selected.");
        }
        try
        {
            ActionCode actionCode = actionCodeDao.findById(actionCodeId);
            MailBatch result = new MailBatch();
            result.setName(MailBatch.generateName(actionCode, DateUtils.YYYYMMDD_HHMMSS));
            for (Long fileId : fileIds)
            {
                File f = fileDao.findById(fileId);
                FileAction a = saveFileAction(new FileActionRequest(f, actionCodeId, result));
                MailBatchFile mbf = new MailBatchFile();
                mbf.setFile(f);
                mbf.setFileAction(a);
                result.add(mbf);
            }
            mailBatchDao.save(result);
            return result;
        }
        catch (ServiceException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    private void addToMailBatch(File file, FileAction fa, ActionCode actionCode)
    {
    	String name = MailBatch.generateName(actionCode, DateUtils.YYYYMMDD);
    	MailBatch mb = mailBatchDao.findByName(name);
    	if (mb == null) {
    		mb = new MailBatch();
    		mb.setName(name);
    	} else {
    		// check if this action was already added to this batch
    		for (MailBatchFile item : mb.getFiles()) {
    			if (item.getFileAction().equals(fa)) {
    				return;
    			}
    			if (item.getFile().equals(file)) {
    				throw new ValidationException("File #" + file.getId() + " already added to today's batch #" + mb.getId());
    			}
    		}
    	}
        MailBatchFile mbf = new MailBatchFile();
        mbf.setFile(file);
        mbf.setFileAction(fa);
        //mbf.setJobAction(ja);
        mb.add(mbf);
        mailBatchDao.save(mb);
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.ActionService#completeMailBatch(java.lang.Long, java.lang.Long[])
     */
    public List<FileAction> completeMailBatch(Long batchId, Long[] fileIds) throws ServiceException
    {
    	final Date now = ThreadLocalUtils.getDate();
        try
        {
            if (fileIds.length > 1) {
                Arrays.sort(fileIds);
            }
            List<FileAction> result = new ArrayList<FileAction>();
            MailBatch mailBatch = mailBatchDao.findById(batchId);
            for (MailBatchFile mbf : mailBatch.getFiles()) {
                File f = mbf.getFile();
                if (Arrays.binarySearch(fileIds, f.getId()) < 0) {
                    continue;
                }
                FileAction a = mbf.getFileAction();
                saveBaseAction(f, a, null, null, null, mailBatch, fileActionDao);
                ActionCode actionCode = actionCodeDao.findById(a.getActionCode().getActionCodeId());
                if (actionCode.getActionType().isEmail()) {
                	MailStatusData msd = mbf.getMailStatus();
                	msd.setStatus(MailStatusEnum.SENT);
                	msd.setSentDate(now);
                }
                result.add(a);
            }
            return result;
        }
        catch (ServiceException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /**
     * 
     * @param d
     * @return
     * @throws IOException
     */
    private java.io.File toFile(File file, Document d) throws IOException
    {
        if (d == null || StringUtils.isBlank(d.getName()))
        {
            return null;
        }
        java.io.File f = new java.io.File(getTmpDir(), file.getId() + "." + d.getName() + '.' + d.getContentTypeEnum().getDefaultExt());
        if (!f.exists() && !f.createNewFile())
        {
            throw new ServiceException("FAILED to create: " + f);
        }
        InputStream input = new ByteArrayInputStream(d.getContent());
        OutputStream output = new FileOutputStream(f);
        try
        {
            IOUtils.copy(input, output);
            return f;
        }
        finally
        {
            input.close();
            output.close();
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.ActionService#notifyCompletedActions(java.util.List)
     */
    public void notifyCompletedActions(List<FileAction> actions) throws ServiceException
    {
        final Date now = ThreadLocalUtils.getDate();
        try
        {
            // reload user
            final User user = userDao.findById(ThreadLocalUtils.getUser().getId());
            // reload action(s)
            for (ListIterator<FileAction> iter = actions.listIterator(); iter.hasNext(); )
            {
                FileAction a = iter.next();
                a = fileActionDao.findById(a.getId());
                iter.set(a);
            }
            //
            MailData mailData = new MailData();
            mailData.setTo(user.getContact().getEmail());
            mailData.setSubject("[JMS] Completed Actions");
            // generate email body from template
            InputStream content = null;
            try
            {
                content = templateDao.getTemplateContent(TemplateEnum.EMAIL_COMPLETED_ACTIONS);
                Map<String, Object> params = new Hashtable<String, Object>();
                params.put("actions", actions);
                ByteArrayOutputStream output = new ByteArrayOutputStream();
                documentService.writeDocument(TemplateTypeEnum.EMAIL, content, params, null, output);
                mailData.setText(output.toString());
            }
            finally
            {
                IOUtils.closeQuietly(content);
            }
            // save action document to attachment file
            List<java.io.File> attachments = new ArrayList<java.io.File>();
            for (FileAction action : actions)
            {
                java.io.File f = toFile(action.getFile(), action.getAttachment());
                if (f != null)
                {
                    attachments.add(f);
                }
            }
            boolean zip = true;
            java.io.File zipFile = null;
            if (zip)
            {
                String destination = DateUtils.YYYYMMDD_HHMMSS.format(now) + '.' + ContentTypeEnum.APPLICATION_ZIP.getDefaultExt();
                zipFile = new java.io.File(getTmpDir(), destination);
                if (!zipFile.exists() && !zipFile.createNewFile())
                {
                    throw new ServiceException("FAILED to create: " + zipFile);
                }
                mailData.setAttachmentsZip(attachments.toArray(new java.io.File[0]), zipFile);
            }
            else
            {
                mailData.setAttachments(attachments.toArray(new java.io.File[0]));
            }
            emailService.sendMail(mailData);
            // delete generated files
            for (java.io.File attachment : attachments)
            {
                if (!attachment.delete()) LOG.error("Failed to delete: " + attachment);
            }
            if (zipFile != null)
            {
                if (!zipFile.delete()) LOG.error("Failed to delete: " + zipFile);
            }
        }
        catch (ServiceException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.ActionService#findFileActionTodo(au.gov.qld.fire.jms.domain.action.TodoSearchCriteria)
     */
    public List<FileActionTodo> findFileActionToDo(TodoSearchCriteria criteria)
        throws ServiceException
    {
        try
        {
            return fileActionDao.findFileActionToDoByCriteria(criteria);
        }
        catch (Exception e)
        {
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.ActionService#findJobActionTodo(au.gov.qld.fire.jms.domain.action.TodoSearchCriteria)
     */
    public List<JobActionTodo> findJobActionToDo(TodoSearchCriteria criteria)
        throws ServiceException
    {
        try
        {
            return jobActionDao.findJobActionToDoByCriteria(criteria);
        }
        catch (Exception e)
        {
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.ActionService#saveFileAction(au.gov.qld.fire.jms.domain.action.FileActionRequest)
     */
    public FileAction saveFileAction(FileActionRequest request) throws ServiceException
    {
        try
        {
            File file = request.getFile();
            // save file to archive (if applicable)
            if (file.getFileStatus().isArchived()) {
                String archiveCode = request.getArchive().getCode();
                Archive archive = archiveDao.findByCode(archiveCode);
                if (archive == null) {
                    throw new ServiceException("No archive found [" + archiveCode + "].");
                }
                FileArchive fileArchive = fileArchiveDao.findById(file.getId());
                if (fileArchive == null) {
                    fileArchiveDao.save(new FileArchive(file.getId(), archive));
                }
                else {
                    if (fileArchive.getArchive().equals(archive)) {
                        throw new ServiceException("File already archived.");
                    }
                    else {
                        throw new ServiceException("File already archived in [" + archive.getCode() + "].");
                    }
                }
            }
            //
            FileAction a = request.getFileAction();
            if (a == null) {
                // non-completed action
                Long actionCodeId = request.getActionCodeId();
                if (actionCodeId == null) {
                    throw new ServiceException("No action code specified.");
                }
                a = new FileAction();
                a.setFile(file);
                a.setActionCode(actionCodeDao.findById(actionCodeId));
                //a.setCompletedBy(ThreadLocalUtils.getUser());
                //a.setCompletedDate(ThreadLocalUtils.getDate());
            }
            // save action
            List<FileAction> defaultActions = request.getDefaultActions();
            List<FileAction> nextActions = request.getNextActions();
            List<Document> attachments = request.getAttachments();
            saveBaseAction(file, a, defaultActions, nextActions, attachments, request.getMailBatch(), fileActionDao);
            return a;
        }
        catch (ServiceException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.FileService#saveJobAction(JobActionRequest)
     */
    public JobAction saveJobAction(JobActionRequest request) throws ServiceException
    {
        try
        {
            File file = request.getFile();
            //
            JobAction a = request.getJobAction();
            if (a == null)
            {
                Long actionCodeId = request.getActionCodeId();
                if (actionCodeId == null)
                {
                    throw new ServiceException("No action code specified.");
                }
                a = new JobAction();
                a.setFile(file);
                a.setActionCode(new ActionCode(actionCodeId));
                a.setCompletedBy(ThreadLocalUtils.getUser());
                a.setCompletedDate(ThreadLocalUtils.getDate());
            }
            // save action
            List<FileAction> defaultActions = request.getDefaultActions();
            List<FileAction> nextActions = request.getNextActions();
            List<Document> attachments = request.getAttachments();
            // save job (if new)
            Job job = a.getJob();
            if (job.getId() == null)
            {
                //set file (just in case we forget to do it in UI)
                job.setFile(file);
                job.setFca(file.getFca());
                if (job.getFca() == null || job.getFca().getId() == null)
                {
                    job.setFca(null);
                }
                jobDao.saveOrUpdate(job);
            }
            saveBaseAction(file, a, defaultActions, nextActions, attachments, request.getMailBatch(), jobActionDao);
            return a;
        }
        catch (ServiceException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.ActionService#closeJobAction(au.gov.qld.fire.jms.domain.file.File, au.gov.qld.fire.jms.domain.action.JobAction, java.util.List)
     */
    public void closeJobAction(File file, JobAction jobAction, List<FileAction> defaultActions)
        throws ServiceException
    {
        try
        {
            //update job
            Job job = jobAction.getJob();
            job.setStatus(false);
            job.setCloseReason(jobAction.getNotation());
            jobDao.saveOrUpdate(job);
            
            saveBaseAction(file, jobAction, defaultActions, null, null, null, jobActionDao);
        }
        catch (ServiceException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /**
     * 
     * @param baseAction
     * @throws DaoException 
     * @throws RuleException 
     */
    private void completeBaseAction(BaseAction baseAction) throws DaoException, RuleException
    {
        User user = ThreadLocalUtils.getUser();
        Date date = ThreadLocalUtils.getDate();
        // common properties
        baseAction.setCompletedBy(user);
        baseAction.setCompletedDate(date);
        ActionCode actionCode = baseAction.getActionCode();
        // file/job action specific
        if (baseAction instanceof JobAction)
        {
            //check if this is close job action request
            JobAction jobAction = jobActionDao.findById(baseAction.getId());
            if (ActionTypeEnum.JOB_CLOSE.getId().equals(actionCode.getActionType().getId()))
            {
                // refresh user
                user = userDao.findById(user.getId());
                // refresh job
                Job job = jobAction.getJob();
                job = jobDao.findById(job.getId());

                //business rules
                businessRule.canCloseJob(user, job, jobAction);

                // finally close job
                job.setCompletedBy(user);
                job.setCompletedDate(date);
                job.setStatus(false); // not active
                jobDao.saveOrUpdate(job);
            }
        }
        else if (baseAction instanceof FileAction)
        {
            // do nothing
            //FileAction fileAction = fileActionDao.findById(baseAction.getId());
        }
    }

    /**
     * 
     * @param file
     * @param baseAction
     * @param defaultActions
     * @param nextActions
     * @return document (if created as result of action processing)
     * @throws ServiceException
     * @throws RuleException 
     * @throws IOException 
     */
    @SuppressWarnings("unchecked")
    private void saveBaseAction(File file, BaseAction baseAction, List<FileAction> defaultActions,
        List<FileAction> nextActions, List<Document> attachments, MailBatch mailBatch, BaseDao baseDao) throws ServiceException, RuleException, IOException
    {
        if (defaultActions == null) {
            defaultActions = Collections.emptyList();
        }
        if (nextActions == null) {
            nextActions = Collections.emptyList();
        }
        //
    	FileAction fa = null;
    	JobAction ja = null;
        if (baseAction instanceof FileAction) {
            fa = (FileAction) baseAction;
        } else if (baseAction instanceof JobAction) {
            ja = (JobAction) baseAction;
        }
        // set some optional data
        final ActionCode actionCode = actionCodeDao.findById(baseAction.getActionCode().getActionCodeId());
        ActionType actionType = actionCode.getActionType();
        if (StringUtils.isBlank(baseAction.getNotation())) {
            baseAction.setNotation(actionCode.getNotation());
        }
        if (baseAction.getDueDate() == null) {
            baseAction.setDueDate(DateUtils.getCurrentDate());
        }
        if (baseAction.getActionOutcome() != null && baseAction.getActionOutcome().getActionOutcomeId() == null) {
            baseAction.setActionOutcome(null);
        }
        // generate document (if action code has template associated)
        if (baseAction.getDocument() == null) {
            Template template = actionCode.getTemplate();
            if (template != null) {
                baseAction.setDocument(documentService.createActionDocument(template, baseAction));
            }
        }
        if (baseAction.getAttachment() == null) {
            Template template = actionCode.getDocumentTemplate();
            if (template != null) {
                baseAction.setAttachment(documentService.createActionDocument(template, baseAction));
            }
        }
        // completed action, has to execute associated action (eg email, sms or generate document)
        // quick actions are created as completed
        ActionTypeEnum actionTypeEnum = ActionTypeEnum.findByActionTypeId(actionType.getId());
        // more validation and default(s)
        if (StringUtils.isBlank(baseAction.getSubject()) && actionType.isEmail()) {
            baseAction.setSubject(actionCode.getNotation());
        }
        if (StringUtils.isBlank(baseAction.getDestination()) && (actionType.isEmail() || actionType.isCall() || actionType.isSms())) {
            baseAction.setDestination(file.getDefaultDestination(actionType));
            if (StringUtils.isBlank(baseAction.getDestination())) {
                throw new ServiceException("No default email destination 'to' found for file #" + file.getId());
            }
        }
        // action can be new 
        boolean completeAction = baseAction.getId() != null || baseAction.getCompletedDate() != null;
        if (completeAction) {
            if (ActionTypeEnum.CALL.equals(actionTypeEnum)) {
                // do nothing
            }
            else if (ActionTypeEnum.DIARY.equals(actionTypeEnum)) {
                // do nothing
            }
            else if (ActionTypeEnum.MESSAGE.equals(actionTypeEnum)) {
                // do nothing
            }
            else if (ActionTypeEnum.LETTER.equals(actionTypeEnum)) {
                // do nothing
            }
            else if (ActionTypeEnum.RFI.equals(actionTypeEnum)) {
                // create/save rfi
                Rfi rfi = new Rfi();
                rfi.setDescription(baseAction.getNotation());
                rfi.setFile(file);
                rfi.setFileAction(fa);
                rfiDao.saveOrUpdate(rfi);
            }
            else if (ActionTypeEnum.EMAIL.equals(actionTypeEnum)) {
                Document attachment = baseAction.getAttachment();
                if (attachment != null) {
                    if (attachments == null) {
                        attachments = new ArrayList<Document>();
                    }
                    attachments.add(attachment);
                }
                sendEmail(baseAction, attachments);
            }
            else if (ActionTypeEnum.SMS.equals(actionTypeEnum)) {
                smsService.sendText(baseAction.getDestination(), baseAction.getMessage());
            }
            else if (ActionTypeEnum.JOB.equals(actionTypeEnum)) {
                // do nothing here
                // we save Job in saveJobAction(..)
            }
            else if (ActionTypeEnum.FILE_STATUS.equals(actionTypeEnum)) {
                // update file status
                FileStatus fileStatus = fileStatusDao.findById(baseAction.getLinkId());
                file.setFileStatus(fileStatus);
                fileDao.saveOrUpdate(file);
            }
            // complete existing action
            completeBaseAction(baseAction);
        }
        else {
            if (ActionTypeEnum.FILE_STATUS.equals(actionTypeEnum)) {
                // store file status on action
                baseAction.setLinkId(baseAction.getFile().getFileStatus().getId());
            }
        }

        // save action
        baseDao.saveOrUpdate(baseAction);

        // save defaultActions (from actionCode selected outcome)
        for (FileAction defaultAction : defaultActions) {
            defaultAction.setFile(file);
            if (fa != null) {
                defaultAction.setParentFileAction(fa);
            }
            if (StringUtils.isBlank(defaultAction.getNotation())) {
                ActionCode defaultActionCode = actionCodeDao.findById(defaultAction.getActionCode().getActionCodeId());
                defaultAction.setNotation(defaultActionCode.getNotation());
            }
            fileActionDao.saveOrUpdate(defaultAction);
        }

        // save nextActions
        for (FileAction nextAction : nextActions) {
            nextAction.setFile(file);
            if (fa != null) {
                nextAction.setParentFileAction(fa);
            } else if (ja != null) {
                nextAction.setParentJobAction(ja);
            }
            if (StringUtils.isBlank(nextAction.getNotation())) {
                ActionCode nextActionCode = actionCodeDao.findById(nextAction.getActionCode().getActionCodeId());
                nextAction.setNotation(nextActionCode.getNotation());
            }
            fileActionDao.saveOrUpdate(nextAction);
        }

        // save fileAction to mailBatch (TODO: what about jobAction ???)
        if (mailBatch == null && fa != null && actionCode.isPdfForm()) {
			addToMailBatch(file, fa, actionCode);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.ActionService#deleteFileAction(au.gov.qld.fire.jms.domain.action.FileAction)
     */
    public void deleteFileAction(FileAction fileAction) throws ServiceException
    {
        deleteBaseAction(fileAction, fileActionDao);
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.ActionService#deleteJobAction(au.gov.qld.fire.jms.domain.action.JobAction)
     */
    public void deleteJobAction(JobAction jobAction) throws ServiceException
    {
        deleteBaseAction(jobAction, jobActionDao);
    }

    /**
     * 
     * @param baseAction
     * @param baseDao
     * @throws ServiceException
     */
    @SuppressWarnings("unchecked")
    public void deleteBaseAction(BaseAction baseAction, BaseDao baseDao) throws ServiceException
    {
        try
        {
            baseDao.delete(baseAction);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.ActionService#export(java.util.List, java.io.OutputStream, au.gov.qld.fire.jms.domain.refdata.ContentTypeEnum)
     */
    public void export(List<? extends BaseActionTodo> entities, OutputStream contentStream,
        ContentTypeEnum contentType) throws ServiceException
    {
        if (entities.isEmpty())
        {
            return;
        }

        try
        {
            if (ContentTypeEnum.isCsv(contentType))
            {
                export2csv(entities, contentStream);
            }
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /**
     * 
     * @param action
     * @param documents
     * @throws ServiceException
     * @throws IOException
     */
    private void sendEmail(BaseAction action, List<Document> documents)
        throws ServiceException, IOException
    {
        // send email
        MailData mailData = new MailData();
        //mailData.setFrom(from);
        mailData.setTo(action.getDestination());
        mailData.setSubject(action.getSubject());
        // text
        String mailText = action.getNotation();
        Document document = action.getDocument();
        if (document != null && document.getContent() != null) {
            if (ContentTypeEnum.APPLICATION_HTML.equals(document.getContentTypeEnum())) {
                mailText = new String(document.getContent());
            }
        }
        mailData.setText(mailText);
        // attachment(s)
        List<java.io.File> attachments = new ArrayList<java.io.File>();
        if (CollectionUtils.isNotEmpty(documents)) {
            for (Document d : documents) {
                java.io.File f = toFile(action.getFile(), d);
                if (f != null) {
                    attachments.add(f);
                }
            }
            mailData.setAttachments(attachments.toArray(new java.io.File[0]));
        }
        // send
        emailService.sendMail(mailData);
        // delete generated files
        for (java.io.File attachment : attachments) {
            if (!attachment.delete()) LOG.error("Failed to delete: " + attachment);
        }
    }

    /**
     * 
     * @param entities
     * @param contentStream
     * @throws IOException 
     * @throws FinalizedException 
     */
    private void export2csv(List<? extends BaseActionTodo> entities, OutputStream contentStream) throws Exception
    {
        boolean isJobAction = !entities.isEmpty() && entities.get(0) instanceof JobActionTodo;
        Writer writer = new BufferedWriter(new OutputStreamWriter(contentStream));
        CSVWriter csvWriter = new CSVWriter(writer, ',');
        // header
        List<String> header = new ArrayList<String>();
        header.add("Action ID");
        header.add("File No");
        header.add("FCA No");
        header.add("Destination");
        header.add("Contact Name");
        header.add("Next Action Date");
        header.add("Next Action");
        header.add("Work Group");
        header.add("Completed By");
        header.add("Completed Date");
        if (isJobAction)
        {
            header.add("Job No");
            header.add("Job Type");
            header.add("Job Start Date");
        }
        header.add("Details");
        csvWriter.writeNext(header.toArray(new String[0]));
        // data
        for (BaseActionTodo entity : entities)
        {
            if (entity.getId() == null)
            {
                continue;
            }
            List<String> row = new ArrayList<String>();
            row.add("" + entity.getId());
            row.add("" + entity.getFileId());
            row.add(entity.getFcaId());
            row.add(entity.getDestination());
            row.add(entity.getContact() == null ? null : entity.getContact().getName());
            row.add(Formatter.formatDate(entity.getNextActionDate()));
            row.add(entity.getNextAction());
            row.add(entity.getWorkGroup());
            row.add(entity.getCompletedBy());
            row.add(Formatter.formatDate(entity.getCompletedDate()));
            if (isJobAction)
            {
                JobActionTodo todo = (JobActionTodo) entity;
                row.add("" + todo.getJobId());
                row.add(todo.getJobType());
                row.add(Formatter.formatDate(todo.getJobStartDate()));
            }
            row.add(entity.getNotation());
            csvWriter.writeNext(row.toArray(new String[0]));
        }
        csvWriter.close();
    }


    /**
     * @return the tmpDir
     */
    public static String getTmpDir()
    {
        return System.getProperty("java.io.tmpdir");
    }

}