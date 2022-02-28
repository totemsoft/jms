package au.gov.qld.fire.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import au.gov.qld.fire.domain.refdata.SupplierTypeEnum;
import au.gov.qld.fire.domain.supplier.Supplier;
import au.gov.qld.fire.domain.supplier.SupplierSearchCriteria;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public interface SupplierService
{

    /**
     * Search entity code/name by name like 'supplierName%'
     * 
     * @param supplierName
     * @return
     * @throws ServiceException
     */
    List<String> findSupplierName(String supplierName) throws ServiceException;

    /**
     * Search entity code/name by name like 'supplierContact%'
     * 
     * @param supplierContact
     * @return
     * @throws ServiceException
     */
    List<String> findSupplierContact(String supplierContact) throws ServiceException;

    /**
     * Search entity code/name by name like 'supplierPhone%'
     * 
     * @param supplierPhone
     * @return
     * @throws ServiceException
     */
    List<String> findSupplierPhone(String supplierPhone) throws ServiceException;

    /**
     * Find all active Suppliers.
     * @return
     * @throws ServiceException
     */
    List<Supplier> findSuppliers() throws ServiceException;

    /**
     * Find all active Suppliers by criteria.
     * @param criteria
     * @return
     * @throws ServiceException
     */
    List<Supplier> findSuppliers(SupplierSearchCriteria criteria) throws ServiceException;

    /**
     * 
     * @param id
     * @return
     * @throws ServiceException
     */
    Supplier findSupplierById(Long id) throws ServiceException;

    /**
     * 
     * @param supplier
     * @throws ServiceException
     */
    void saveSupplier(Supplier supplier) throws ServiceException;

    /**
     * eg Filter by 'ASE' (the supplier type of ASE Suppliers).
     * @param supplierType
     * @return
     * @throws ServiceException
     */
    List<Supplier> findSuppliers(SupplierTypeEnum supplierType) throws ServiceException;

}