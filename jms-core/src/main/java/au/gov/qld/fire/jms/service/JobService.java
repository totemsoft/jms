package au.gov.qld.fire.jms.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import au.gov.qld.fire.jms.domain.job.ActiveJob;
import au.gov.qld.fire.jms.domain.job.ActiveJobRequest;
import au.gov.qld.fire.jms.domain.job.Job;
import au.gov.qld.fire.jms.domain.job.JobDoc;
import au.gov.qld.fire.jms.domain.job.JobRequest;
import au.gov.qld.fire.jms.domain.job.JobRequestSearchCriteria;
import au.gov.qld.fire.jms.domain.job.JobSearchCriteria;
import au.gov.qld.fire.service.ServiceException;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public interface JobService
{

    /**
     * Search entity code/name by name like 'name%'
     * @param jobNo
     * @return
     * @throws ServiceException
     */
    List<String> findJobNo(String jobNo) throws ServiceException;

    /**
     * Search entity code/name by name like 'name%'
     * @param jobRequestNo
     * @return
     * @throws ServiceException
     */
    List<String> findJobRequestNo(String jobRequestNo) throws ServiceException;

    /**
     * 
     * @param id
     * @return
     * @throws ServiceException
     */
    Job findJobById(Long id) throws ServiceException;

    /**
     * 
     * @param job
     * @throws ServiceException
     */
    void saveJob(Job job) throws ServiceException;

    /**
     * 
     * @param jobId
     * @param jobDocs
     * @throws ServiceException
     */
    void saveJobDoc(Long jobId, List<JobDoc> jobDocs) throws ServiceException;

    /**
     * 
     * @param id
     * @return
     * @throws ServiceException
     */
    JobRequest findJobRequestById(Long id) throws ServiceException;

    /**
     * 
     * @param jobRequest
     * @throws ServiceException
     */
    void saveJobRequest(JobRequest jobRequest) throws ServiceException;

    /**
     * 
     * @param jobRequest
     * @throws ServiceException
     */
    void acceptJobRequest(JobRequest jobRequest) throws ServiceException;

    /**
     * 
     * @param jobRequest
     * @throws ServiceException
     */
    void rejectJobRequest(JobRequest jobRequest) throws ServiceException;

    /**
     * 
     * @param criteria
     * @return
     * @throws ServiceException
     */
    List<ActiveJob> findActiveJobs(JobSearchCriteria criteria) throws ServiceException;

    /**
     * 
     * @param criteria
     * @return
     * @throws ServiceException
     */
    List<ActiveJobRequest> findActiveJobRequests(JobRequestSearchCriteria criteria) throws ServiceException;

    /**
     * 
     * @param jobDocId
     * @param moveJobId
     * @throws ServiceException
     */
    void moveJobDoc(Long jobDocId, Long moveJobId) throws ServiceException;

}