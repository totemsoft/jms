package au.gov.qld.fire.jms.dao;

import java.util.List;

import au.gov.qld.fire.dao.BaseDao;
import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.jms.domain.building.BuildingContact;
import au.gov.qld.fire.jms.domain.refdata.BuildingContactType;

/**
 * BuildingContact DAO pattern.
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public interface BuildingContactDao extends BaseDao<BuildingContact>
{

    /**
     * Find entities by buildingContactType.
     * @param buildingContactType
     * @return
     * @throws DaoException
     */
    List<BuildingContact> findByBuildingContactType(BuildingContactType buildingContactType)
        throws DaoException;

}