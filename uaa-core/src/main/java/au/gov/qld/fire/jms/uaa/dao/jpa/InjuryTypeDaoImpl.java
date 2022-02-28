package au.gov.qld.fire.jms.uaa.dao.jpa;

import java.util.List;

import javax.persistence.NoResultException;

import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.jms.uaa.dao.InjuryTypeDao;
import au.gov.qld.fire.jms.uaa.domain.refdata.InjuryType;
import au.gov.qld.fire.service.ServiceException;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class InjuryTypeDaoImpl extends UaaBaseDaoImpl<InjuryType> implements InjuryTypeDao
{

	public InjuryType findByName(String name) throws ServiceException {
		try {
			return (InjuryType) getEntityManager()
			    .createQuery("FROM InjuryType WHERE name = :name")
			    .setParameter("name", name)
			    .setMaxResults(1)
			    .getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<InjuryType> findAll(Boolean multiple) throws DaoException {
		return getEntityManager()
		    .createQuery("FROM InjuryType WHERE :multiple IS NULL OR multiple = :multiple")
		    .setParameter("multiple", multiple)
		    .getResultList();
	}

}