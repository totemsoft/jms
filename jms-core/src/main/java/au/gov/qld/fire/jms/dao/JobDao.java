package au.gov.qld.fire.jms.dao;

import java.util.List;

import au.gov.qld.fire.dao.BaseDao;
import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.jms.domain.job.ActiveJob;
import au.gov.qld.fire.jms.domain.job.Job;
import au.gov.qld.fire.jms.domain.job.JobSearchCriteria;
import au.gov.qld.fire.jms.domain.refdata.JobType;

/**
 * Job DAO pattern.
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public interface JobDao extends BaseDao<Job>
{

    /**
     * Find entities by jobType.
     * @param jobType
     * @return
     * @throws DaoException
     */
    List<Job> findByJobType(JobType jobType) throws DaoException;

    /**
     * 
     * @param criteria
     * @return
     * @throws DaoException
     */
    List<ActiveJob> findActiveJobByCriteria(JobSearchCriteria criteria) throws DaoException;

    /**
     * 
     * @param jobNo
     * @return
     * @throws DaoException
     */
    List<String> findJobNo(String jobNo) throws DaoException;

}