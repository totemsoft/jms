package au.gov.qld.fire.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import au.gov.qld.fire.domain.location.Address;

/**
 * Address DAO pattern.
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Repository
public interface AddressDao extends BaseDao<Address>
{

    /**
     * Find entities by criteria [..].
     * @param addrLine1
     * @param addrLine2
     * @param postcode
     * @param suburb
     * @param state
     * @return
     * @throws DaoException
     */
    List<Address> findByAddrLinePostcodeSuburb(String addrLine1, String addrLine2,
        String postcode, String suburb, String state) throws DaoException;

}