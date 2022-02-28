package au.gov.qld.fire.dao.hibernate;

import java.util.List;

import au.gov.qld.fire.dao.ContactDao;
import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.domain.entity.Contact;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class ContactDaoImpl extends BaseDaoImpl<Contact> implements ContactDao
{

	/* (non-Javadoc)
     * @see au.gov.qld.fire.jms.dao.ContactDao#findByEmail(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    public List<Contact> findByEmail(String email) throws DaoException
    {
        try
        {
        	return getEntityManager()
        		.createNamedQuery("contact.findByEmail")
        		.setParameter("email", email)
        		.getResultList();
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

}