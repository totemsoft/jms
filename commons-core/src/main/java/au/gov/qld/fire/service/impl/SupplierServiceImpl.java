package au.gov.qld.fire.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import au.gov.qld.fire.dao.SupplierDao;
import au.gov.qld.fire.domain.refdata.SupplierType;
import au.gov.qld.fire.domain.refdata.SupplierTypeEnum;
import au.gov.qld.fire.domain.supplier.Supplier;
import au.gov.qld.fire.domain.supplier.SupplierSearchCriteria;
import au.gov.qld.fire.domain.ConvertUtils;
import au.gov.qld.fire.service.ServiceException;
import au.gov.qld.fire.service.SupplierService;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class SupplierServiceImpl implements SupplierService
{

    /** logger. */
    private static final Logger LOG = Logger.getLogger(SupplierServiceImpl.class);

    @Autowired private SupplierDao supplierDao;

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.SupplierService#findSupplierContact(java.lang.String)
     */
    public List<String> findSupplierContact(String supplierContact) throws ServiceException
    {
        try
        {
            return supplierDao.findSupplierContact(supplierContact);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.SupplierService#findSupplierName(java.lang.String)
     */
    public List<String> findSupplierName(String supplierName) throws ServiceException
    {
        try
        {
            return supplierDao.findSupplierName(supplierName);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.SupplierService#findSupplierPhone(java.lang.String)
     */
    public List<String> findSupplierPhone(String supplierPhone) throws ServiceException
    {
        try
        {
            return supplierDao.findSupplierPhone(supplierPhone);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.SupplierService#findSuppliers()
     */
    public List<Supplier> findSuppliers() throws ServiceException
    {
        try
        {
            return supplierDao.findAll();
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.SupplierService#findSuppliers(au.gov.qld.fire.jms.domain.supplier.SupplierSearchCriteria)
     */
    public List<Supplier> findSuppliers(SupplierSearchCriteria criteria) throws ServiceException
    {
        try
        {
            return supplierDao.findByCriteria(criteria);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

	/* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.SupplierService#findSupplierById(java.lang.String)
     */
    public Supplier findSupplierById(Long id) throws ServiceException
    {
        try
        {
            return id == null ? null : supplierDao.findById(id);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.SupplierService#saveSupplier(au.gov.qld.fire.jms.domain.supplier.Supplier)
     */
    public void saveSupplier(Supplier supplier) throws ServiceException
    {
        try
        {
            //optional
            if (supplier.getRegion() == null || supplier.getRegion().getId() == null)
            {
                supplier.setRegion(null); //all Region(s)
            }

            //find by id
            Long id = supplier.getId();
            Supplier entity = null;
            if (id != null)
            {
                entity = supplierDao.findById(id);
            }

            // and copy
            boolean newEntity = entity == null;
            if (newEntity)
            {
                entity = supplier;
                if (LOG.isDebugEnabled())
                    LOG.debug("New entity: " + entity);
            }
            else
            {
                ConvertUtils.copyProperties(supplier, entity);
            }

            //save
            supplierDao.saveOrUpdate(entity);
            if (LOG.isDebugEnabled())
                LOG.debug("Saved: " + entity);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.service.SupplierService#findAseSuppliers(SupplierTypeEnum)
     */
    public List<Supplier> findSuppliers(SupplierTypeEnum supplierTypeEnum) throws ServiceException
    {
        try
        {
            SupplierType supplierType = new SupplierType(supplierTypeEnum.getId());
            return supplierDao.findBySupplierType(supplierType);
        }
        catch (Exception e)
        {
            //LOG.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

}