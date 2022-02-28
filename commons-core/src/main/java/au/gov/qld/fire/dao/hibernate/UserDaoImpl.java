package au.gov.qld.fire.dao.hibernate;

import java.util.List;

import javax.persistence.NoResultException;

import org.apache.commons.lang.StringUtils;

import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.dao.UserDao;
import au.gov.qld.fire.domain.security.User;
import au.gov.qld.fire.domain.security.UserSearchCriteria;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao
{

	public User initialize(User entity) {
		//final PersistenceUtil jpaUtil = Persistence.getPersistenceUtil();
		initialize(entity.getContact());
		//initialize(entity.getCreatedBy());
		//initialize(entity.getUpdatedBy());
		return entity;
	}

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.dao.UserDao#findAll(au.gov.qld.fire.jms.domain.security.UserSearchCriteria)
     */
    @SuppressWarnings("unchecked")
    public List<User> findAllByCriteria(UserSearchCriteria criteria) throws DaoException
    {
        try
        {
            String securityGroups = criteria.getSecurityGroups();
            String userTypes = criteria.getUserTypes();
            String login = criteria.getLogin();
            String[] names = StringUtils.split(login);
            String firstName = names != null && names.length >= 1 ? names[0] : null;
            String surname = names != null && names.length >= 2 ? names[1] : null;
            if (surname == null)
            {
            	firstName = null;
            }
            else
            {
            	login = null;
            }
            return getEntityManager()
            	.createNamedQuery("user.findAllByCriteria")
	            .setParameter("securityGroups", securityGroups)
	            .setParameter("userTypes", userTypes)
	            .setParameter("login", getValueLike(login))
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
     * @see au.gov.qld.fire.jms.dao.UserDao#findByLogin(java.lang.String)
     */
    public User findByLogin(String login) throws DaoException
    {
    	return findByLogin(login, false);
    }

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.dao.UserDao#findByLogin(java.lang.String, boolean)
	 */
	public User findByLogin(String login, boolean lazyLoad) throws DaoException
	{
        try
        {
        	User result = (User) getEntityManager()
        		.createNamedQuery("user.findByLogin")
	            .setParameter("login", login)
	            .getSingleResult();
        	if (lazyLoad) {
        		result = initialize(result);
        	}
        	return result;
        }
        catch (NoResultException e)
        {
        	LOG.warn("No user for login [" + login + "] found.");
            return null;
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
	}

}