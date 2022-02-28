package au.gov.qld.fire.dao.hibernate;

import java.util.Collections;
import java.util.List;

import javax.persistence.NoResultException;

import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.dao.StationDao;
import au.gov.qld.fire.domain.SearchCriteria;
import au.gov.qld.fire.domain.location.Area;
import au.gov.qld.fire.domain.location.Region;
import au.gov.qld.fire.domain.location.Station;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class StationDaoImpl extends BaseDaoImpl<Station> implements StationDao
{

    /* (non-Javadoc)
	 * @see au.gov.qld.fire.dao.hibernate.BaseDaoImpl#findAllOrderBy()
	 */
	@Override
	protected String findAllOrderBy() {
		return "ORDER BY area.region.name, area.name, name";
	}

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.dao.StationDao#findStationByRegionArea(java.lang.String, java.lang.String, java.lang.String)
     */
    @SuppressWarnings("unchecked")
    public List<String> findStationByRegionArea(String stationLike, String region, String area)
        throws DaoException
    {
        try
        {
        	return getEntityManager()
        		.createNamedQuery("station.findStationByRegionArea")
        		.setMaxResults(SearchCriteria.DEFAULT_MAX)
	            .setParameter("stationLike", getValueLike(stationLike))
	            .setParameter("region", region)
	            .setParameter("area", area)
	            .getResultList();
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.dao.StationDao#findByAreaStation(au.gov.qld.fire.jms.domain.location.Area, java.lang.String)
     */
    public Station findByAreaStation(Area area, String stationName)
        throws DaoException
    {
        try
        {
        	return (Station) getEntityManager()
        		.createNamedQuery("station.findByAreaStation")
	            .setParameter("area", area)
	            .setParameter("name", stationName)
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
     * @see au.gov.qld.fire.jms.dao.StationDao#findByRegion(au.gov.qld.fire.jms.domain.location.Region)
     */
    @SuppressWarnings("unchecked")
    public List<Station> findByRegion(Region region) throws DaoException
    {
        if (region == null || region.getId() == null)
        {
            return Collections.emptyList();
        }
        try
        {
        	return getEntityManager()
        		.createNamedQuery("station.findByRegion")
        		.setMaxResults(SearchCriteria.DEFAULT_MAX)
	            .setParameter("region", region)
	            .getResultList();
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

}