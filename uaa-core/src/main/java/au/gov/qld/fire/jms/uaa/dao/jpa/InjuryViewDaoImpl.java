package au.gov.qld.fire.jms.uaa.dao.jpa;

import java.util.List;

import javax.persistence.Query;

import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.jms.uaa.dao.InjuryViewDao;
import au.gov.qld.fire.jms.uaa.domain.IncidentSearchCriteria;
import au.gov.qld.fire.jms.uaa.domain.firu.InjuryView;
import au.gov.qld.fire.jms.uaa.domain.firu.InjuryViewPK;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class InjuryViewDaoImpl extends UaaBaseDaoImpl<InjuryView> implements InjuryViewDao
{

	@Override
	public InjuryView findById(InjuryViewPK id) throws DaoException {
		return super.findById(id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<InjuryView> findByCriteria(IncidentSearchCriteria criteria) throws DaoException {
		Query qry = getEntityManager().createQuery("FROM InjuryView");
		return qry.getResultList();
	}

}