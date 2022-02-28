package au.gov.qld.fire.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import au.gov.qld.fire.domain.entity.Contact;

/**
 * Contact DAO pattern.
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Repository
public interface ContactDao extends BaseDao<Contact>
{

    /**
     * Find entity by email.
     * @param email
     * @return
     * @throws DaoException
     */
    List<Contact> findByEmail(String email) throws DaoException;

}