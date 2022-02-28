package au.gov.qld.fire.jms.dao;

import java.util.List;

import au.gov.qld.fire.dao.BaseDao;
import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.jms.domain.job.Job;
import au.gov.qld.fire.jms.domain.job.JobDoc;

/**
 * JobDoc DAO pattern.
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public interface JobDocDao extends BaseDao<JobDoc>
{

    /**
     * Find entities by job.
     * @param job
     * @return
     * @throws DaoException
     */
    List<JobDoc> findByJob(Job job) throws DaoException;

}