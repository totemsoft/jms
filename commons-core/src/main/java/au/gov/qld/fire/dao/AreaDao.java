package au.gov.qld.fire.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import au.gov.qld.fire.domain.location.Area;
import au.gov.qld.fire.domain.location.Region;

/**
 * Area DAO pattern.
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Repository
public interface AreaDao extends BaseDao<Area>
{

    /**
     * 
     * @param areaLike
     * @param regionName
     * @return
     * @throws DaoException
     */
    List<String> findAreaByRegion(String areaLike, String regionName) throws DaoException;

    /**
     * 
     * @param region
     * @param areaName
     * @return
     * @throws DaoException
     */
    Area findByRegionArea(Region region, String areaName) throws DaoException;

}