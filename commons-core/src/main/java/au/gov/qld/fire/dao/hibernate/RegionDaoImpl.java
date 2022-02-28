package au.gov.qld.fire.dao.hibernate;

import java.util.List;

import javax.persistence.NoResultException;

import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.dao.RegionDao;
import au.gov.qld.fire.domain.SearchCriteria;
import au.gov.qld.fire.domain.location.Region;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class RegionDaoImpl extends BaseDaoImpl<Region> implements RegionDao
{

    /* (non-Javadoc)
	 * @see au.gov.qld.fire.dao.hibernate.BaseDaoImpl#findAllOrderBy()
	 */
	@Override
	protected String findAllOrderBy() {
		return "ORDER BY name";
	}

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.dao.RegionDao#findByName(java.lang.String)
     */
    public Region findByName(String name) throws DaoException
    {
        try
        {
        	return (Region) getEntityManager()
        		.createNamedQuery("region.findByName")
        		.setParameter("name", name)
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

    /* (non-Javadoc)
	 * @see au.gov.qld.fire.dao.RegionDao#findByCode(java.lang.String)
	 */
	public Region findByCode(String code) throws DaoException
	{
        try
        {
        	return (Region) getEntityManager()
        		.createNamedQuery("region.findByCode")
        		.setParameter("code", code)
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

	/* (non-Javadoc)
     * @see au.gov.qld.fire.jms.dao.RegionDao#findRegion(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    public List<String> findRegion(String regionLike) throws DaoException
    {
        try
        {
        	return getEntityManager()
        		.createNamedQuery("region.findRegion")
        		.setMaxResults(SearchCriteria.DEFAULT_MAX)
        		.setParameter("regionLike", getValueLike(regionLike))
        		.getResultList();
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

}