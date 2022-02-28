package au.gov.qld.fire.jms.dao.hibernate;

import java.util.List;

import javax.persistence.NoResultException;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import au.gov.qld.fire.dao.AddressDao;
import au.gov.qld.fire.dao.ContactDao;
import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.dao.hibernate.BaseDaoImpl;
import au.gov.qld.fire.jms.dao.OwnerDao;
import au.gov.qld.fire.jms.domain.entity.Owner;
import au.gov.qld.fire.jms.domain.refdata.OwnerTypeEnum;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class OwnerDaoImpl extends BaseDaoImpl<Owner> implements OwnerDao
{

	@Autowired private ContactDao contactDao;

	@Autowired private AddressDao addressDao;

    /* (non-Javadoc)
	 * @see au.gov.qld.fire.dao.hibernate.BaseDaoImpl#saveOrUpdate(java.lang.Object)
	 */
	@Override
	public void saveOrUpdate(Owner entity) throws DaoException {
        try
        {
        	//contactDao.saveOrUpdate(entity.getContact());
        	//addressDao.saveOrUpdate(entity.getAddress());
        	super.saveOrUpdate(entity);
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
	}

	/* (non-Javadoc)
     * @see au.gov.qld.fire.jms.dao.OwnerDao#findByLegalName(java.lang.String)
     */
    public Owner findByLegalName(String legalName) throws DaoException
    {
        try
        {
            return (Owner) getEntityManager()
                .createNamedQuery("owner.findByLegalName")
                .setParameter("legalName", legalName)
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
	 * @see au.gov.qld.fire.jms.dao.OwnerDao#findByLegalNameLike(au.gov.qld.fire.jms.domain.refdata.OwnerTypeEnum, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<Owner> findByLegalNameLike(OwnerTypeEnum ownerType, String legalName) throws DaoException
	{
        try
        {
            return getEntityManager()
                .createNamedQuery("owner.findByLegalNameLike")
                .setParameter("ownerTypeId", ownerType == null || ownerType.equals(OwnerTypeEnum.NONE) ? null : ownerType.getId())
                .setParameter("legalName", getValueLike(legalName))
                .getResultList();
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
	}

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.jms.dao.OwnerDao#findByContactName(au.gov.qld.fire.jms.domain.refdata.OwnerTypeEnum, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<Owner> findByContactName(OwnerTypeEnum ownerType, String contactName) throws DaoException
	{
        try
        {
        	String[] names = StringUtils.split(contactName);
        	String firstName = names.length > 0 ? names[0] : null;
        	if (StringUtils.contains(firstName, '*')) {
        		firstName = firstName.replace('*', '%');
        	}
        	String surname = names.length > 1 ? names[1] : null;
        	if (StringUtils.contains(surname, '*')) {
        		surname = surname.replace('*', '%');
        	}
            return getEntityManager()
                .createNamedQuery("owner.findByContactName")
                .setParameter("ownerTypeId", ownerType == null || ownerType.equals(OwnerTypeEnum.NONE) ? null : ownerType.getId())
                .setParameter("firstName", getValueLike(firstName))
                .setParameter("surname", getValueLike(surname))
                .getResultList();
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
	}

	/* (non-Javadoc)
     * @see au.gov.qld.fire.jms.dao.OwnerDao#findLegalName(java.lang.String)
     */
    public List<String> findLegalName(String legalName) throws DaoException
    {
        return findValueLike("owner.findLegalName", legalName);
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.dao.OwnerDao#findContactSurname(java.lang.String)
     */
    public List<String> findContactSurname(String contactSurname) throws DaoException
    {
        return findValueLike("owner.findContactSurname", contactSurname);
    }

}