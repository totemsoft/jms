package au.gov.qld.fire.dao.hibernate;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.dao.JdbcDao;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class JdbcDaoImpl implements JdbcDao
{

    /** logger. */
    //private final static Logger LOG = Logger.getLogger(JdbcDaoImpl.class);

    /** EntityManager. */
    @PersistenceContext//(unitName = "jms")
    private EntityManager entityManager;

    /* (non-Javadoc)
     * @see au.gov.qld.fire.dao.JdbcDao#uniqueResult(java.lang.String)
     */
    public Object uniqueResult(String query) throws DaoException
    {
        List<?> result = entityManager.createNativeQuery(query)
        	.setMaxResults(1)
        	.getResultList();
        return result.isEmpty() ? null : result.get(0);
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.dao.JdbcDao#executeUpdate(java.lang.String)
     */
    public int executeUpdate(String query) throws DaoException
    {
        return entityManager.createNativeQuery(query).executeUpdate();
    }

}