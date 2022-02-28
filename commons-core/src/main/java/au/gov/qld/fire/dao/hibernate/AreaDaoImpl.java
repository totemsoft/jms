package au.gov.qld.fire.dao.hibernate;

import java.util.List;

import javax.persistence.NoResultException;

import au.gov.qld.fire.dao.AreaDao;
import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.domain.SearchCriteria;
import au.gov.qld.fire.domain.location.Area;
import au.gov.qld.fire.domain.location.Region;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class AreaDaoImpl extends BaseDaoImpl<Area> implements AreaDao
{

    /* (non-Javadoc)
	 * @see au.gov.qld.fire.dao.hibernate.BaseDaoImpl#findAllOrderBy()
	 */
	@Override
	protected String findAllOrderBy() {
		return "ORDER BY region.name, name";
	}

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.dao.AreaDao#findByRegionArea(au.gov.qld.fire.jms.domain.location.Region, java.lang.String)
     */
    public Area findByRegionArea(Region region, String areaName) throws DaoException
    {
        try
        {
        	return (Area) getEntityManager()
        		.createNamedQuery("area.findByRegionArea")
	            .setParameter("region", region)
	            .setParameter("name", areaName)
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
     * @see au.gov.qld.fire.jms.dao.AreaDao#findAreaByRegion(java.lang.String, java.lang.String)
     */
    @SuppressWarnings("unchecked")
    public List<String> findAreaByRegion(String areaLike, String regionName) throws DaoException
    {
        try
        {
        	return getEntityManager()
        		.createNamedQuery("area.findAreaByRegion")
	    		.setMaxResults(SearchCriteria.DEFAULT_MAX)
	            .setParameter("areaLike", getValueLike(areaLike))
	            .setParameter("region", regionName)
	            .getResultList();
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

}