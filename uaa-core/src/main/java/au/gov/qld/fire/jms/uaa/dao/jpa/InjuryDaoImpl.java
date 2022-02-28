package au.gov.qld.fire.jms.uaa.dao.jpa;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.domain.SearchCriteria;
import au.gov.qld.fire.jms.uaa.dao.InjuryDao;
import au.gov.qld.fire.jms.uaa.domain.IncidentSearchCriteria;
import au.gov.qld.fire.jms.uaa.domain.Injury;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class InjuryDaoImpl extends UaaBaseDaoImpl<Injury> implements InjuryDao
{

	public Injury findByUniqueKey(String fireCallNo, Integer injuryNo) throws DaoException {
		try {
			Map<String, Object> parameters = new Hashtable<String, Object>();
			String sql = "FROM Injury WHERE fireCallNo = :fireCallNo";
			parameters.put("fireCallNo", fireCallNo);
			if (injuryNo == null) {
				sql += " AND injuryNo IS NULL";
			} else {
				sql += " AND injuryNo = :injuryNo";
				parameters.put("injuryNo", injuryNo);
			}
			Query qry = getEntityManager().createQuery(sql);
			updateQuery(qry, parameters);
			return (Injury) qry.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Injury> findByCriteria(IncidentSearchCriteria criteria) throws DaoException {
		return getEntityManager()
		    .createQuery("FROM Injury")
		    .setMaxResults(SearchCriteria.DEFAULT_MAX)
		    .getResultList();
	}

}