package au.gov.qld.fire.jms.dao;

import java.util.List;

import au.gov.qld.fire.dao.BaseDao;
import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.jms.domain.isolation.IsolationHistory;
import au.gov.qld.fire.jms.domain.isolation.IsolationHistorySearchCriteria;

/**
 * IsolationHistory DAO pattern.
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public interface IsolationHistoryDao extends BaseDao<IsolationHistory>
{

    /**
     * 
     * @param criteria
     * @return
     * @throws DaoException
     */
    List<IsolationHistory> findByCriteria(IsolationHistorySearchCriteria criteria) throws DaoException;

    /**
     * 
     * @param criteria
     * @return
     * @throws DaoException
     */
    IsolationHistory findUniqueByCriteria(IsolationHistorySearchCriteria criteria) throws DaoException;

}