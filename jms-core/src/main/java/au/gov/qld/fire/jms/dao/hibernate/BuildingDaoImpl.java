package au.gov.qld.fire.jms.dao.hibernate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import au.gov.qld.fire.dao.AddressDao;
import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.dao.hibernate.BaseDaoImpl;
import au.gov.qld.fire.jms.dao.BuildingDao;
import au.gov.qld.fire.jms.domain.building.Building;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class BuildingDaoImpl extends BaseDaoImpl<Building> implements BuildingDao
{

	@Autowired private AddressDao addressDao;

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.dao.hibernate.BaseDaoImpl#saveOrUpdate(java.lang.Object)
	 */
	@Override
	public void saveOrUpdate(Building entity) throws DaoException {
        try
        {
        	addressDao.saveOrUpdate(entity.getAddress());
        	super.saveOrUpdate(entity);
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
	}

	/* (non-Javadoc)
     * @see au.gov.qld.fire.jms.dao.BuildingDao#findByName(java.lang.String)
     */
    public Building findByName(String name) throws DaoException
    {
        try
        {
            return (Building) getEntityManager()
                .createNamedQuery("building.findByName")
                .setParameter("name", name)
                .getSingleResult();
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
	 * @see au.gov.qld.fire.jms.dao.BuildingDao#findByNameLike(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<Building> findByNameLike(String name) throws DaoException
	{
        try
        {
            return getEntityManager()
                .createNamedQuery("building.findByNameLike")
                .setParameter("name", getValueLike(name))
                .setMaxResults(MAX_RESULT)
                .getResultList();
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
	}

	/* (non-Javadoc)
     * @see au.gov.qld.fire.jms.dao.BuildingDao#findBuildingName(java.lang.String)
     */
    public List<String> findBuildingName(String buildingName) throws DaoException
    {
        return findValueLike("building.findBuildingName", buildingName);
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.dao.BuildingDao#findBuildingSuburb(java.lang.String)
     */
    public List<String> findBuildingSuburb(String buildingSuburb) throws DaoException
    {
        return findValueLike("building.findBuildingSuburb", buildingSuburb);
    }

}