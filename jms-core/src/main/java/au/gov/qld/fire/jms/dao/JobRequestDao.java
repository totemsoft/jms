package au.gov.qld.fire.jms.dao;

import java.util.List;

import au.gov.qld.fire.dao.BaseDao;
import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.jms.domain.job.ActiveJobRequest;
import au.gov.qld.fire.jms.domain.job.JobRequest;
import au.gov.qld.fire.jms.domain.job.JobRequestSearchCriteria;
import au.gov.qld.fire.jms.domain.refdata.JobType;

/**
 * JobRequest DAO pattern.
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public interface JobRequestDao extends BaseDao<JobRequest>
{

    /**
     * Find entities by jobType.
     * @param jobType
     * @return
     * @throws DaoException
     */
    List<JobRequest> findByJobType(JobType jobType) throws DaoException;

    /**
     * 
     * @param criteria
     * @return
     * @throws DaoException
     */
    List<ActiveJobRequest> findActiveJobRequestByCriteria(JobRequestSearchCriteria criteria)
        throws DaoException;

    /**
     * 
     * @param jobRequestNo
     * @return
     * @throws DaoException
     */
    List<String> findJobRequestNo(String jobRequestNo) throws DaoException;

}