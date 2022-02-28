package au.gov.qld.fire.jms.dao;

import java.util.List;

import au.gov.qld.fire.dao.BaseDao;
import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.jms.domain.building.Building;

/**
 * Building DAO pattern.
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public interface BuildingDao extends BaseDao<Building>
{

    /**
     * Find entity by name (unique).
     * @param name
     * @return
     * @throws DaoException
     */
    Building findByName(String name) throws DaoException;

    /**
     * 
     * @param name
     * @return
     * @throws DaoException
     */
	List<Building> findByNameLike(String name) throws DaoException;

    /**
     * 
     * @param buildingName
     * @return
     * @throws DaoException
     */
    List<String> findBuildingName(String buildingName) throws DaoException;

    /**
     * 
     * @param buildingSuburb
     * @return
     * @throws DaoException
     */
    List<String> findBuildingSuburb(String buildingSuburb) throws DaoException;

}