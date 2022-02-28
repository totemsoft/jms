package au.gov.qld.fire.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import au.gov.qld.fire.domain.refdata.SupplierType;
import au.gov.qld.fire.domain.supplier.Supplier;
import au.gov.qld.fire.domain.supplier.SupplierSearchCriteria;

/**
 * Supplier DAO pattern.
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Repository
public interface SupplierDao extends BaseDao<Supplier>
{

    /**
     * Search entity code/name by name like 'supplierContact%'
     * 
     * @param supplierContact
     * @return
     * @throws DaoException
     */
    List<String> findSupplierContact(String supplierContact) throws DaoException;

    /**
     * Search entity code/name by name like 'supplierName%'
     * 
     * @param supplierName
     * @return
     * @throws DaoException
     */
    List<String> findSupplierName(String supplierName) throws DaoException;

    /**
     * Search entity code/name by name like 'supplierPhone%'
     * 
     * @param supplierPhone
     * @return
     * @throws DaoException
     */
    List<String> findSupplierPhone(String supplierPhone) throws DaoException;

    /**
     * Find entity by name (unique).
     * @param name
     * @return
     * @throws DaoException
     */
    Supplier findByName(String name) throws DaoException;

    /**
     * Find entity by legalName (unique).
     * @param legalName
     * @return
     * @throws DaoException
     */
    Supplier findByLegalName(String legalName) throws DaoException;

    /**
     * Find entity by abn (unique).
     * @param abn
     * @return
     * @throws DaoException
     */
    Supplier findByABN(String abn) throws DaoException;

    /**
     * Find entities by supplierType.
     * @param supplierType
     * @return
     * @throws DaoException
     */
    List<Supplier> findBySupplierType(SupplierType supplierType) throws DaoException;

    /**
     * Find entities by criteria.
     * @param criteria
     * @return
     * @throws DaoException
     */
    List<Supplier> findByCriteria(SupplierSearchCriteria criteria) throws DaoException;

}