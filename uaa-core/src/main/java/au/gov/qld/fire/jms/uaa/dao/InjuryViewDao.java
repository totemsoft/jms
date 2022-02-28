package au.gov.qld.fire.jms.uaa.dao;

import java.util.List;

import au.gov.qld.fire.dao.BaseDao;
import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.jms.uaa.domain.IncidentSearchCriteria;
import au.gov.qld.fire.jms.uaa.domain.firu.InjuryView;
import au.gov.qld.fire.jms.uaa.domain.firu.InjuryViewPK;

/**
 * Address DAO pattern.
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public interface InjuryViewDao extends BaseDao<InjuryView>
{

	/**
	 * 
	 * @param fireCallNo
	 * @param injuryNo
	 * @return
	 * @throws DaoException
	 */
	InjuryView findById(InjuryViewPK id) throws DaoException;

	/**
	 * 
	 * @param criteria
	 * @return
	 * @throws DaoException
	 */
    List<InjuryView> findByCriteria(IncidentSearchCriteria criteria) throws DaoException;

}