package au.gov.qld.fire.jms.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import au.gov.qld.fire.dao.AreaDao;
import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.dao.RegionDao;
import au.gov.qld.fire.dao.StationDao;
import au.gov.qld.fire.dao.hibernate.BaseDaoImpl;
import au.gov.qld.fire.domain.BasePair;
import au.gov.qld.fire.domain.SearchCriteria;
import au.gov.qld.fire.domain.location.Area;
import au.gov.qld.fire.domain.location.Region;
import au.gov.qld.fire.domain.location.Station;
import au.gov.qld.fire.jms.dao.FcaDao;
import au.gov.qld.fire.jms.domain.Fca;
import au.gov.qld.fire.jms.domain.fca.FcaSearchCriteria;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class FcaDaoImpl extends BaseDaoImpl<Fca> implements FcaDao
{

    /** regionDao */
	@Autowired private RegionDao regionDao;

    /** areaDao */
	@Autowired private AreaDao areaDao;

    /** stationDao */
	@Autowired private StationDao stationDao;

    /* (non-Javadoc)
	 * @see au.gov.qld.fire.jms.dao.FcaDao#findById(java.lang.String)
	 */
	public Fca findById(String fcaNo) throws DaoException
	{
		return super.findById(fcaNo);
	}

	/* (non-Javadoc)
     * @see au.gov.qld.fire.jms.dao.FcaDao#findFca(java.lang.String, boolean)
     */
    @SuppressWarnings("unchecked")
    public List<BasePair> findFca(String fcaNoLike, boolean unassignedFca) throws DaoException
    {
        try
        {
        	List<Object[]> entities = getEntityManager()
        		.createNamedQuery("fca.findFcaNo")
      			.setMaxResults(SearchCriteria.DEFAULT_MAX)
	            .setParameter("fcaNo", getValueLike(fcaNoLike))
	            .setParameter("unassignedFca", unassignedFca)
	            .getResultList();
            List<BasePair> result = new ArrayList<BasePair>();
            for (Object[] entity : entities) {
            	String fcaNo = (String) entity[0];
            	Number fileId = (Number) entity[1];
            	result.add(new BasePair(fileId == null ? 0L : fileId.longValue(), fcaNo));
            }
            return result;
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.dao.FcaDao#findByCriteria(au.gov.qld.fire.jms.domain.fca.FcaSearchCriteria)
     */
    @SuppressWarnings("unchecked")
    public List<Fca> findByCriteria(FcaSearchCriteria criteria) throws DaoException
    {
        try
        {
        	boolean subPanel = criteria.isSubPanel();
            String fcaNo = criteria.getFcaNo();
            if (subPanel && fcaNo != null)
            {
            	int idx = fcaNo.indexOf('-');
            	if (idx > 0)
            	{
                	fcaNo = fcaNo.substring(0, idx + 1);
            	}
            }
            fcaNo = StringUtils.isBlank(fcaNo) ? null : fcaNo + "%";
            Region region = null;
            String regionName = StringUtils.isBlank(criteria.getRegion()) ? null : criteria.getRegion();
            if (regionName != null)
            {
                region = regionDao.findByName(regionName);
            }
            Area area = null;
            String areaName = StringUtils.isBlank(criteria.getArea()) ? null : criteria.getArea();
            if (areaName != null)
            {
                area = areaDao.findByRegionArea(region, areaName);
            }
            Station station = null;
            String stationName = StringUtils.isBlank(criteria.getStation()) ? null : criteria.getStation();
            if (stationName != null)
            {
                station = stationDao.findByAreaStation(area, stationName);
            }
            if (criteria.isUnassignedFca())
            {
            	return getEntityManager()
            		.createNamedQuery("fca.findByCriteriaUnassigned")
        		    .setMaxResults(criteria.getMaxResults() > 0 ? criteria.getMaxResults() : SearchCriteria.MAX)
                    .setParameter("fcaNo", fcaNo)
                    .setParameter("subPanel", subPanel)
                    .setParameter("region", region)
                    .getResultList();
            }
            return getEntityManager()
                .createNamedQuery("fca.findByCriteriaAssigned")
                .setMaxResults(criteria.getMaxResults())
                .setParameter("fcaNo", fcaNo)
                .setParameter("subPanel", subPanel)
                .setParameter("region", region)
                .setParameter("area", area)
                .setParameter("station", station)
                .getResultList();
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.dao.FcaDao#findByFileId(java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    public List<Fca> findByFileId(Long fileId) throws DaoException
    {
        try
        {
        	return getEntityManager()
        	    .createNamedQuery("fca.findByFileId")
                .setParameter("fileId", fileId)
                .getResultList();
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

	public int clearFileId(Long fileId) throws DaoException
	{
        try
        {
            return getEntityManager()
                .createQuery("UPDATE Fca SET file = NULL WHERE file.fileId = :fileId")
                .setParameter("fileId", fileId)
                .executeUpdate();
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
	}

}