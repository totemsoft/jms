package au.gov.qld.fire.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import au.gov.qld.fire.domain.security.User;
import au.gov.qld.fire.domain.security.UserSearchCriteria;

/**
 * User DAO pattern.
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Repository
public interface UserDao extends BaseDao<User>
{

    /**
     * Find entity by login.
     * @param login
     * @return
     * @throws DaoException
     */
    User findByLogin(String login) throws DaoException;
    User findByLogin(String login, boolean lazyLoad) throws DaoException;

    /**
     * Finder:
     * @return
     * @throws DaoException
     */
    public List<User> findAllByCriteria(UserSearchCriteria criteria) throws DaoException;

}