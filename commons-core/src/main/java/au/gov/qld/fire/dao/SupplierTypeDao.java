package au.gov.qld.fire.dao;

import org.springframework.stereotype.Repository;

import au.gov.qld.fire.domain.refdata.SupplierType;

/**
 * SupplierType DAO pattern.
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Repository
public interface SupplierTypeDao extends BaseDao<SupplierType>
{

    /**
     * Find entity by name (unique).
     * @param name
     * @return
     * @throws DaoException
     */
    SupplierType findByName(String name) throws DaoException;

}