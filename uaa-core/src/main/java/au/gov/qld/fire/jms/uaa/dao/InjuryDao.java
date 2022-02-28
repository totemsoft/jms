package au.gov.qld.fire.jms.uaa.dao;

import java.util.List;

import au.gov.qld.fire.dao.BaseDao;
import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.jms.uaa.domain.Injury;
import au.gov.qld.fire.jms.uaa.domain.IncidentSearchCriteria;

/**
 * Address DAO pattern.
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public interface InjuryDao extends BaseDao<Injury>
{

	/**
	 * 
	 * @param fireCallNo
	 * @param injuryNo
	 * @return
	 * @throws DaoException
	 */
    Injury findByUniqueKey(String fireCallNo, Integer injuryNo) throws DaoException;

    /**
	 * 
	 * @param criteria
	 * @return
	 * @throws DaoException
	 */
    List<Injury> findByCriteria(IncidentSearchCriteria criteria) throws DaoException;

}