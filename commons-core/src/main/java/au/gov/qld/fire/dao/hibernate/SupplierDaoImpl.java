package au.gov.qld.fire.dao.hibernate;

import java.util.List;

import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.dao.SupplierDao;
import au.gov.qld.fire.domain.refdata.SupplierType;
import au.gov.qld.fire.domain.supplier.Supplier;
import au.gov.qld.fire.domain.supplier.SupplierSearchCriteria;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class SupplierDaoImpl extends BaseDaoImpl<Supplier> implements SupplierDao
{

	/* (non-Javadoc)
     * @see au.gov.qld.fire.dao.hibernate.BaseDaoImpl#findAllOrderBy()
     */
    @Override
    protected String findAllOrderBy() {
        return "ORDER BY name";
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.dao.SupplierDao#findSupplierContact(java.lang.String)
     */
    public List<String> findSupplierContact(String supplierContact) throws DaoException
    {
        return findValueLike("supplier.findSupplierContact", supplierContact);
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.dao.SupplierDao#findSupplierName(java.lang.String)
     */
    public List<String> findSupplierName(String supplierName) throws DaoException
    {
        return findValueLike("supplier.findSupplierName", supplierName);
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.dao.SupplierDao#findSupplierPhone(java.lang.String)
     */
    public List<String> findSupplierPhone(String supplierPhone) throws DaoException
    {
        return findValueLike("supplier.findSupplierPhone", supplierPhone);
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.dao.SupplierDao#findByABN(java.lang.String)
     */
    public Supplier findByABN(String abn) throws DaoException
    {
        try
        {
            return (Supplier) getEntityManager()
                .createNamedQuery("supplier.findByABN")
                .setParameter("abn", abn)
                .getSingleResult();
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.dao.SupplierDao#findByLegalName(java.lang.String)
     */
    public Supplier findByLegalName(String legalName) throws DaoException
    {
        try
        {
            return (Supplier) getEntityManager()
                .createNamedQuery("supplier.findByLegalName")
                .setParameter("legalName", legalName)
                .getSingleResult();
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.dao.SupplierDao#findByName(java.lang.String)
     */
    public Supplier findByName(String name) throws DaoException
    {
        try
        {
            return (Supplier) getEntityManager()
                .createNamedQuery("supplier.findByName")
                .setParameter("name", name)
                .getSingleResult();
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.dao.SupplierDao#findBySupplierType(au.gov.qld.fire.jms.domain.refdata.SupplierType)
     */
    @SuppressWarnings("unchecked")
    public List<Supplier> findBySupplierType(SupplierType supplierType) throws DaoException
    {
        try
        {
            return getEntityManager()
                .createNamedQuery("supplier.findBySupplierType")
                .setParameter("supplierType", supplierType)
                .getResultList();
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.dao.SupplierDao#findByCriteria(au.gov.qld.fire.jms.domain.supplier.SupplierSearchCriteria)
     */
    @SuppressWarnings("unchecked")
    public List<Supplier> findByCriteria(SupplierSearchCriteria criteria) throws DaoException
    {
        try
        {
            return getEntityManager()
                .createNamedQuery(criteria.isAse() ? "supplier.findByCriteriaASE" : "supplier.findByCriteria")
                //.setMaxResults(SearchCriteria.DEFAULT_MAX)
                .setParameter("supplierName", getValueLike(criteria.getSupplierName()))
                .setParameter("supplierContact", getValueLike(criteria.getSupplierContact()))
                .setParameter("supplierPhone", getValueLike(criteria.getSupplierPhone()))
                .setParameter("active", criteria.getActive())
                .getResultList();
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

}