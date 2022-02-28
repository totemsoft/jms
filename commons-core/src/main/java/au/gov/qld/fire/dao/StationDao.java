package au.gov.qld.fire.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import au.gov.qld.fire.domain.location.Area;
import au.gov.qld.fire.domain.location.Region;
import au.gov.qld.fire.domain.location.Station;

/**
 * Station DAO pattern.
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Repository
public interface StationDao extends BaseDao<Station>
{

    /**
     * 
     * @param stationLike
     * @param region
     * @param area
     * @return
     * @throws DaoException
     */
    List<String> findStationByRegionArea(String stationLike, String region, String area) throws DaoException;

    /**
     * 
     * @param area
     * @param stationName
     * @return
     * @throws DaoException
     */
    Station findByAreaStation(Area area, String stationName) throws DaoException;

    /**
     * 
     * @param region
     * @return
     * @throws DaoException
     */
    List<Station> findByRegion(Region region) throws DaoException;

}