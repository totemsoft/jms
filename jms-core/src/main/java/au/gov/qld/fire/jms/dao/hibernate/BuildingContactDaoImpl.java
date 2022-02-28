package au.gov.qld.fire.jms.dao.hibernate;

import java.util.List;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;

import au.gov.qld.fire.dao.ContactDao;
import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.dao.hibernate.BaseDaoImpl;
import au.gov.qld.fire.jms.dao.BuildingContactDao;
import au.gov.qld.fire.jms.domain.building.BuildingContact;
import au.gov.qld.fire.jms.domain.refdata.BuildingContactType;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class BuildingContactDaoImpl extends BaseDaoImpl<BuildingContact> implements BuildingContactDao
{

	@Autowired private ContactDao contactDao;

    /* (non-Javadoc)
	 * @see au.gov.qld.fire.dao.hibernate.BaseDaoImpl#saveOrUpdate(java.lang.Object)
	 */
	@Override
	public void saveOrUpdate(BuildingContact entity) throws DaoException {
        try
        {
        	contactDao.saveOrUpdate(entity.getContact());
        	super.saveOrUpdate(entity);
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
	}

	/* (non-Javadoc)
     * @see au.gov.qld.fire.jms.dao.BuildingContactDao#findByBuildingContactType(au.gov.qld.fire.jms.domain.refdata.BuildingContactType)
     */
    @SuppressWarnings("unchecked")
    public List<BuildingContact> findByBuildingContactType(BuildingContactType buildingContactType)
        throws DaoException
    {
        try
        {
            Query qry = getEntityManager().createQuery(
            	"FROM BuildingContact WHERE buildingContactType = :buildingContactType");
            qry.setParameter("buildingContactType", buildingContactType);
            return (List<BuildingContact>) qry.getResultList();
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.dao.hibernate.BaseDaoImpl#delete(java.lang.Object)
     */
    @Override
    public void delete(BuildingContact entity) throws DaoException
    {
        if (entity == null)
        {
            return;
        }
        try
        {
        	getSession().delete(entity);
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

}