package au.gov.qld.fire.jms.dao.hibernate;

import java.util.List;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;

import au.gov.qld.fire.dao.AddressDao;
import au.gov.qld.fire.dao.ContactDao;
import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.dao.hibernate.BaseDaoImpl;
import au.gov.qld.fire.domain.location.Region;
import au.gov.qld.fire.jms.dao.StakeHolderDao;
import au.gov.qld.fire.jms.domain.entity.StakeHolder;
import au.gov.qld.fire.jms.domain.refdata.StakeHolderType;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class StakeHolderDaoImpl extends BaseDaoImpl<StakeHolder> implements StakeHolderDao
{

	@Autowired private ContactDao contactDao;

	@Autowired private AddressDao addressDao;

    /* (non-Javadoc)
	 * @see au.gov.qld.fire.dao.hibernate.BaseDaoImpl#saveOrUpdate(java.lang.Object)
	 */
	@Override
	public void saveOrUpdate(StakeHolder entity) throws DaoException {
        try
        {
        	contactDao.saveOrUpdate(entity.getContact());
        	addressDao.saveOrUpdate(entity.getAddress());
        	super.saveOrUpdate(entity);
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
	}

	/* (non-Javadoc)
     * @see au.gov.qld.fire.jms.dao.StakeHolderDao#findByStakeHolderType(au.gov.qld.fire.jms.domain.refdata.StakeHolderType)
     */
    @SuppressWarnings("unchecked")
    public List<StakeHolder> findByStakeHolderType(StakeHolderType stakeHolderType)
        throws DaoException
    {
        try
        {
            Query qry = getSession().getNamedQuery("stakeHolder.findByStakeHolderType");
            qry.setParameter("stakeHolderType", stakeHolderType);
            return (List<StakeHolder>) qry.list();
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.dao.StakeHolderDao#findByRegion(au.gov.qld.fire.jms.domain.location.Region)
     */
    @SuppressWarnings("unchecked")
    public List<StakeHolder> findByRegion(Region region) throws DaoException
    {
        try
        {
            Query qry = getSession().getNamedQuery("stakeHolder.findByRegion");
            qry.setParameter("region", region);
            return (List<StakeHolder>) qry.list();
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

}