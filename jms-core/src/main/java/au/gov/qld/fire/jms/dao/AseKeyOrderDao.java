package au.gov.qld.fire.jms.dao;

import java.util.List;

import au.gov.qld.fire.dao.BaseDao;
import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.jms.domain.ase.AseKeyOrder;
import au.gov.qld.fire.jms.domain.ase.AseKeySearchCriteria;

/**
 * AseKey DAO pattern.
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public interface AseKeyOrderDao extends BaseDao<AseKeyOrder>
{

    /**
     * 
     * @param criteria
     * @return
     * @throws DaoException
     */
	List<AseKeyOrder> findByCriteria(AseKeySearchCriteria criteria) throws DaoException;

	List<String> findOrderNo(String orderNo) throws DaoException;

}