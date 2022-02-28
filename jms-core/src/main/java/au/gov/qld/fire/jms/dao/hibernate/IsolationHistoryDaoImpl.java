package au.gov.qld.fire.jms.dao.hibernate;

import java.util.List;

import javax.persistence.NoResultException;

import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.dao.hibernate.BaseDaoImpl;
import au.gov.qld.fire.domain.SearchCriteria;
import au.gov.qld.fire.jms.dao.IsolationHistoryDao;
import au.gov.qld.fire.jms.domain.isolation.IsolationHistory;
import au.gov.qld.fire.jms.domain.isolation.IsolationHistorySearchCriteria;

/*
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class IsolationHistoryDaoImpl extends BaseDaoImpl<IsolationHistory> implements IsolationHistoryDao
{

    /* (non-Javadoc)
	 * @see au.gov.qld.fire.jms.dao.IsolationHistoryDao#findByCriteria(au.gov.qld.fire.jms.domain.isolation.IsolationHistorySearchCriteria)
	 */
	@SuppressWarnings("unchecked")
	public List<IsolationHistory> findByCriteria(IsolationHistorySearchCriteria criteria) throws DaoException
	{
        try
        {
        	return getEntityManager()
        		.createNamedQuery("isolationHistory.findByCriteria")
        		.setMaxResults(criteria.getMaxResults() > 0 ? criteria.getMaxResults() : SearchCriteria.MAX)
        		.setParameter("fcaNo", getValueLike(criteria.getFcaId()))
        		.setParameter("regionId", criteria.getRegionId())
        		.setParameter("area", getValueLike(criteria.getArea()))
        		.setParameter("station", getValueLike(criteria.getStation()))
        		.setParameter("isolationDate", criteria.getIsolationDate())
        		.setParameter("deIsolationDate", criteria.getDeIsolationDate())
        		.getResultList();
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
	}

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.jms.dao.IsolationHistoryDao#findByCriteria(au.gov.qld.fire.jms.domain.isolation.IsolationHistorySearchCriteria)
	 */
	public IsolationHistory findUniqueByCriteria(IsolationHistorySearchCriteria criteria) throws DaoException
	{
        try
        {
        	return (IsolationHistory) getEntityManager()
        		.createNamedQuery("isolationHistory.findUniqueByCriteria")
        		.setParameter("fcaNo", criteria.getFcaId())
        		.setParameter("input", criteria.getInput())
        		.setParameter("isolationDate", criteria.getIsolationDate())
        		.setParameter("deIsolationDate", criteria.getDeIsolationDate())
        		.getSingleResult();
        }
        catch (NoResultException e)
        {
        	return null;
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
	}

}