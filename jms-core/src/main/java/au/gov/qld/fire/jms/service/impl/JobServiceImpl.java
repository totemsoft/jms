package au.gov.qld.fire.jms.service.impl;

import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import au.gov.qld.fire.dao.TemplateDao;
import au.gov.qld.fire.domain.MailData;
import au.gov.qld.fire.domain.document.TemplateEnum;
import au.gov.qld.fire.domain.security.User;
import au.gov.qld.fire.jms.dao.ActionCodeDao;
import au.gov.qld.fire.jms.dao.FcaDao;
import au.gov.qld.fire.jms.dao.FileDao;
import au.gov.qld.fire.jms.dao.JobDao;
import au.gov.qld.fire.jms.dao.JobDocDao;
import au.gov.qld.fire.jms.dao.JobRequestDao;
import au.gov.qld.fire.jms.dao.JobTypeDao;
import au.gov.qld.fire.jms.domain.ConvertUtils;
import au.gov.qld.fire.jms.domain.Fca;
import au.gov.qld.fire.jms.domain.action.JobAction;
import au.gov.qld.fire.jms.domain.action.JobActionRequest;
import au.gov.qld.fire.jms.domain.file.File;
import au.gov.qld.fire.jms.domain.job.ActiveJob;
import au.gov.qld.fire.jms.domain.job.ActiveJobRequest;
import au.gov.qld.fire.jms.domain.job.Job;
import au.gov.qld.fire.jms.domain.job.JobDoc;
import au.gov.qld.fire.jms.domain.job.JobRequest;
import au.gov.qld.fire.jms.domain.job.JobRequestSearchCriteria;
import au.gov.qld.fire.jms.domain.job.JobSearchCriteria;
import au.gov.qld.fire.jms.domain.refdata.ActionCode;
import au.gov.qld.fire.jms.domain.refdata.ActionTypeEnum;
import au.gov.qld.fire.jms.domain.refdata.JobType;
import au.gov.qld.fire.jms.service.ActionService;
import au.gov.qld.fire.jms.service.DocumentService;
import au.gov.qld.fire.jms.service.JobService;
import au.gov.qld.fire.service.EmailService;
import au.gov.qld.fire.service.ServiceException;
import au.gov.qld.fire.service.ValidationException;
import au.gov.qld.fire.util.DateUtils;
import au.gov.qld.fire.util.ThreadLocalUtils;

/*
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class JobServiceImpl implements JobService
{

    /** logger. */
    private static final Logger LOG = Logger.getLogger(JobServiceImpl.class);

    @Autowired private ActionCodeDao actionCodeDao;

    @Autowired private FcaDao fcaDao;

    @Autowired private FileDao fileDao;

    @Autowired private JobDao jobDao;

    @Autowired private JobDocDao jobDocDao;

    @Autowired private JobRequestDao jobRequestDao;

    @Autowired private JobTypeDao jobTypeDao;

    @Autowired private TemplateDao templateDao;

    @Autowired private ActionService actionService;

    @Autowired private DocumentService documentService;

    @Autowired private EmailService emailService;

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.FileService#findJobNo(java.lang.String)
     */
    public List<String> findJobNo(String jobNo) throws ServiceException
    {
        try
        {
            return jobDao.findJobNo(jobNo);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.JobService#findJobRequestNo(java.lang.String)
     */
    public List<String> findJobRequestNo(String jobRequestNo) throws ServiceException
    {
        try
        {
            return jobRequestDao.findJobRequestNo(jobRequestNo);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.JobService#findJobById(java.lang.Long)
     */
    public Job findJobById(Long id) throws ServiceException
    {
        try
        {
            return jobDao.findById(id);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.JobService#saveJob(au.gov.qld.fire.jms.domain.job.Job)
     */
    public void saveJob(Job job) throws ServiceException
    {
        boolean newEntity = false;
        try
        {
            // validate
            Long fileId = job.getFile().getId();
            File file = fileDao.findById(fileId);
            if (file == null)
            {
                throw new ValidationException("Invalid fileNo: " + fileId);
            }

            // find by id
            Long id = job.getId();
            Job entity = null;
            if (id != null)
            {
                entity = jobDao.findById(id);
            }

            // and copy
            newEntity = entity == null;
            if (newEntity)
            {
                entity = new Job();
                if (LOG.isDebugEnabled())
                    LOG.debug("New entity: " + entity);
            }
            ConvertUtils.copyProperties(job, entity);

            // optional
            if (entity.getFca() == null || entity.getFca().getId() == null)
            {
                entity.setFca(file.getFca());
            }

            // save job
            jobDao.saveOrUpdate(entity);

            // save job action
            if (newEntity)
            {
                JobType jobType = entity.getJobType();
                // refresh jobType
                jobType = jobTypeDao.findById(jobType.getId());
                // get actionCode
                ActionCode actionCode = jobType.getActionCode();
                if (actionCode == null)
                {
                    // ActionCode(s)
                    List<ActionCode> actionCodes = actionCodeDao.findAllByActionType(ActionTypeEnum.JOB);
                    if (actionCodes.isEmpty())
                    {
                        throw new ServiceException("JobType has No ActionCode set and No ActionCode with ActionType='Job' found.");
                    }
                    actionCode = actionCodes.get(0);
                }
                JobAction jobAction = new JobAction();
                jobAction.setJob(entity);
                jobAction.setActionCode(actionCode);
                jobAction.setNotation(actionCode.getNotation());
                jobAction.setDueDate(DateUtils.getCurrentDate());
                jobAction.setDestination(entity.getRequestedEmail());
                actionService.saveJobAction(new JobActionRequest(file, jobAction, actionCode.getId(), null, null, null, null));
            }
        }
        catch (ValidationException e) {
        	throw e;
        }
        catch (Exception e) {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.JobService#saveJobDoc(java.lang.Long, java.util.List)
     */
    public void saveJobDoc(Long jobId, List<JobDoc> jobDocs) throws ServiceException
    {
        try
        {
            //find by id
            Job entity = jobDao.findById(jobId);

            //get old JobDoc(s)
            List<JobDoc> oldJobDocs = entity.getJobDocs();
            //save
            for (JobDoc jobDoc : jobDocs)
            {
                int index = oldJobDocs.indexOf(jobDoc);
                if (index >= 0)
                {
                    //old updated
                    JobDoc oldJobDoc = oldJobDocs.get(index);
                    oldJobDocs.remove(index);
                    ConvertUtils.copyProperties(jobDoc, oldJobDoc);
                    jobDocDao.saveOrUpdate(oldJobDoc);
                }
                else
                {
                    //new added
                    jobDocDao.saveOrUpdate(jobDoc);
                }
            }
            //delete
            for (JobDoc jobDoc : oldJobDocs)
            {
                jobDocDao.delete(jobDoc);
            }

        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.JobService#findJobRequestById(java.lang.Long)
     */
    public JobRequest findJobRequestById(Long id) throws ServiceException
    {
        try
        {
            return jobRequestDao.findById(id);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.JobService#saveJobRequest(au.gov.qld.fire.jms.domain.job.JobRequest)
     */
    public void saveJobRequest(JobRequest jobRequest) throws ServiceException
    {
        try
        {
            // validate
            String fcaId = jobRequest.getFca().getId();
            Fca fca = fcaDao.findById(fcaId);
            if (fca == null)
            {
                throw new ValidationException("Invalid fcaNo: " + fcaId);
            }
            if (fca.getFile() == null || fca.getFile().getId() == null)
            {
                throw new ValidationException("Unassigned fcaNo: " + fcaId);
            }

            //find by id
            Long id = jobRequest.getId();
            JobRequest entity = null;
            if (id != null)
            {
                entity = jobRequestDao.findById(id);
            }

            // and copy
            boolean newEntity = entity == null;
            if (newEntity)
            {
                entity = jobRequest;
                if (LOG.isDebugEnabled())
                    LOG.debug("New entity: " + entity);
            }
            else
            {
                ConvertUtils.copyProperties(jobRequest, entity);
            }

            //optional

            //save
            jobRequestDao.saveOrUpdate(entity);
            if (LOG.isDebugEnabled())
                LOG.debug("Saved: " + entity);
        }
        catch (ValidationException e) {
        	throw e;
        }
        catch (Exception e) {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.JobService#acceptJobRequest(au.gov.qld.fire.jms.domain.job.JobRequest)
     */
    public void acceptJobRequest(JobRequest jobRequest) throws ServiceException
    {
        try
        {
            //find by id
            Long id = jobRequest.getId();
            JobRequest entity = jobRequestDao.findById(id);

            //accept jobRequest
            entity.setAccepted(true);
            entity.setAcceptedBy(ThreadLocalUtils.getUser());
            entity.setAcceptedDate(ThreadLocalUtils.getDate());
            jobRequestDao.saveOrUpdate(entity);
            if (LOG.isDebugEnabled())
                LOG.debug("Saved: " + entity);

            //save job
            Job job = new Job(entity);
            saveJob(job);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.JobService#rejectJobRequest(au.gov.qld.fire.jms.domain.job.JobRequest)
     */
    public void rejectJobRequest(JobRequest jobRequest) throws ServiceException
    {
        try
        {
            //find by id
            Long id = jobRequest.getId();
            JobRequest entity = jobRequestDao.findById(id);

            //reject jobRequest
            entity.setAccepted(false);
            entity.setAcceptedBy(ThreadLocalUtils.getUser());
            entity.setAcceptedDate(ThreadLocalUtils.getDate());
            entity.setRejectReason(jobRequest.getRejectReason());
            //save
            jobRequestDao.saveOrUpdate(entity);
            if (LOG.isDebugEnabled())
                LOG.debug("Saved: " + entity);

            //auto-mail to requestor
            User user = ThreadLocalUtils.getUser();
            InputStream content = null;
            try
            {
                content = templateDao.getTemplateContent(TemplateEnum.EMAIL_REJECT_JOB_REQUEST);
                MailData mailData = createMailData(content, user, entity);
                //update to/from email
                mailData.setTo(entity.getRequestedEmail());
                mailData.setFrom(user.getContact().getEmail());
                //send
                emailService.sendMail(mailData);
            }
            finally
            {
                IOUtils.closeQuietly(content);
            }
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.JobService#findActiveJobs(au.gov.qld.fire.jms.domain.job.JobSearchCriteria)
     */
    //@SuppressWarnings("unchecked")
    public List<ActiveJob> findActiveJobs(JobSearchCriteria criteria) throws ServiceException
    {
        try
        {
            //            //enforce at least some criteria.
            //            if (StringUtils.isBlank(criteria.getFileNo())
            //                && StringUtils.isBlank(criteria.getFcaNo())
            //                && StringUtils.isBlank(criteria.getBuildingName())
            //                && (criteria.getActionCodeId() == null)
            //                && StringUtils.isBlank(criteria.getActionCode())
            //                && StringUtils.isBlank(criteria.getWorkGroup())
            //                && (criteria.getWorkGroupId() == null)
            //                && StringUtils.isBlank(criteria.getJobNo())
            //                && StringUtils.isBlank(criteria.getJobType())
            //                && (criteria.getJobTypeId() == null))
            //            {
            //                return Collections.EMPTY_LIST;
            //            }
            return jobDao.findActiveJobByCriteria(criteria);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.JobService#findActiveJobRequests(au.gov.qld.fire.jms.domain.job.JobRequestSearchCriteria)
     */
    //@SuppressWarnings("unchecked")
    public List<ActiveJobRequest> findActiveJobRequests(JobRequestSearchCriteria criteria)
        throws ServiceException
    {
        try
        {
            //            //enforce at least some criteria.
            //            if (StringUtils.isBlank(criteria.getJobRequestNo())
            //                && StringUtils.isBlank(criteria.getJobType())
            //                && (criteria.getJobTypeId() == null)
            //                && StringUtils.isBlank(criteria.getWorkGroup())
            //                && (criteria.getWorkGroupId() == null))
            //            {
            //                return Collections.EMPTY_LIST;
            //            }
            return jobRequestDao.findActiveJobRequestByCriteria(criteria);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.JobService#moveJobDoc(java.lang.Long, java.lang.Long)
     */
    public void moveJobDoc(Long jobDocId, Long moveJobId) throws ServiceException
    {
        try
        {
            //find by id
            JobDoc jobDoc = jobDocDao.findById(jobDocId);

            //get file to move
            Job moveJob = jobDao.findById(moveJobId);
            //validation
            if (moveJob == null)
            {
                throw new ServiceException("No Job to move found");
            }

            Job job = jobDoc.getJob();
            if (moveJob.equals(job))
            {
                return;
            }

            //save
            jobDoc.setJob(moveJob);
            jobDocDao.saveOrUpdate(jobDoc);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.EmailService#createMailData(java.io.InputStream, au.gov.qld.fire.jms.domain.security.User, au.gov.qld.fire.jms.domain.job.JobRequest)
     */
    private MailData createMailData(InputStream content, User user, JobRequest jobRequest)
        throws ServiceException
    {
        try
        {
            MailData data = new MailData(content);
            data.setTo(user.getContact().getEmail());
            String text = data.getText();
            text = documentService.updateParameters(text, user, jobRequest);
            data.setText(text);

            return data;
        }
        catch (Exception e)
        {
            throw new ServiceException("Failed to create MailData: " + e.getMessage(), e);
        }
    }

}