package au.gov.qld.fire.jms.dao;

import au.gov.qld.fire.dao.BaseDao;
import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.jms.domain.refdata.JobType;

/**
 * JobType DAO pattern.
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public interface JobTypeDao extends BaseDao<JobType>
{

    /**
     * Find entity by name (unique).
     * @param name
     * @return
     * @throws DaoException
     */
    JobType findByName(String name) throws DaoException;

}