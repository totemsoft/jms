package au.gov.qld.fire.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import au.gov.qld.fire.domain.location.Region;

/**
 * Region DAO pattern.
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Repository
public interface RegionDao extends BaseDao<Region>
{

    /**
     * Find entity by name (unique).
     * @param name
     * @return
     * @throws DaoException
     */
    Region findByName(String name) throws DaoException;

    /**
     * Find entity by code (unique).
     * @param code
     * @return
     * @throws DaoException
     */
    Region findByCode(String code) throws DaoException;

    /**
     * 
     * @param regionLike
     * @return
     * @throws DaoException
     */
    List<String> findRegion(String regionLike) throws DaoException;

}