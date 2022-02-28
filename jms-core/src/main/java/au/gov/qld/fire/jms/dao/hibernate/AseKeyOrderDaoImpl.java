package au.gov.qld.fire.jms.dao.hibernate;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.dao.hibernate.BaseDaoImpl;
import au.gov.qld.fire.domain.SearchCriteria;
import au.gov.qld.fire.jms.dao.AseKeyOrderDao;
import au.gov.qld.fire.jms.domain.ase.AseKeyOrder;
import au.gov.qld.fire.jms.domain.ase.AseKeySearchCriteria;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class AseKeyOrderDaoImpl extends BaseDaoImpl<AseKeyOrder> implements AseKeyOrderDao
{

    /* (non-Javadoc)
	 * @see au.gov.qld.fire.dao.hibernate.BaseDaoImpl#saveOrUpdate(java.lang.Object)
	 */
	@Override
	public void saveOrUpdate(AseKeyOrder entity) throws DaoException {
        try
        {
        	super.saveOrUpdate(entity);
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
	}

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.jms.dao.AseFileDao#findByCriteria(au.gov.qld.fire.jms.domain.ase.AseKeyOrderSearchCriteria)
	 */
    @SuppressWarnings("unchecked")
	public List<AseKeyOrder> findByCriteria(AseKeySearchCriteria criteria) throws DaoException
	{
        try {
        	String[] names = StringUtils.split(criteria.getContactName());
        	String firstName = names != null && names.length > 0 ? names[0] : null;
        	if (StringUtils.contains(firstName, '*')) {
        		firstName = firstName.replace('*', '%');
        	}
        	String surname = names != null && names.length > 1 ? names[1] : null;
        	if (StringUtils.contains(surname, '*')) {
        		surname = surname.replace('*', '%');
        	}
        	return getEntityManager()
           		.createNamedQuery("aseKeyOrder.findByCriteria")
        		.setMaxResults(criteria.getMaxResults() > 0 ? criteria.getMaxResults() : SearchCriteria.MAX)
                .setParameter("orderNo", getValueLike(criteria.getOrderNo()))
                .setParameter("supplierName", getValueLike(criteria.getSupplierName()))
                .setParameter("firstName", getValueLike(firstName))
                .setParameter("surname", getValueLike(surname))
        		.getResultList();
        }
        catch (Exception e) {
            throw new DaoException(e.getMessage(), e);
        }
	}

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.jms.dao.AseKeyOrderDao#findOrderNo(java.lang.String)
	 */
    @SuppressWarnings("unchecked")
	public List<String> findOrderNo(String orderNo) throws DaoException
	{
        try {
        	return getEntityManager()
        		.createNamedQuery("aseKeyOrder.findOrderNo")
       			.setMaxResults(SearchCriteria.DEFAULT_MAX)
                .setParameter("orderNo", getValueLike(orderNo))
        		.getResultList();
        }
        catch (Exception e) {
            throw new DaoException(e.getMessage(), e);
        }
	}

}