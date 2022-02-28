package au.gov.qld.fire.dao;

import org.springframework.stereotype.Repository;



/**
 * DAO pattern.
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Repository
public interface JdbcDao
{

    /**
     * 
     * @param query
     * @return
     * @throws DaoException
     */
    Object uniqueResult(String query) throws DaoException;

    /**
     * 
     * @param query
     * @return
     * @throws DaoException
     */
    int executeUpdate(String query) throws DaoException;

}