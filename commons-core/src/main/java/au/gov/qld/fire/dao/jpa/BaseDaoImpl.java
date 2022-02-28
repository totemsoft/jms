package au.gov.qld.fire.dao.jpa;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import au.gov.qld.fire.dao.BaseDao;
import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.domain.Auditable;
import au.gov.qld.fire.domain.security.User;
import au.gov.qld.fire.util.ThreadLocalUtils;


/**
 * TODO: <T extends BaseModel<ID>>
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public abstract class BaseDaoImpl<T> implements BaseDao<T>
{

    /** logger. */
    protected final Log LOG = LogFactory.getLog(getClass());

    /** persistentClass. */
    private final Class<T> persistentClass;

    @PersistenceContext//(unitName = "jms")
    private EntityManager entityManager;

    protected EntityManager getEntityManager()
    {
        return entityManager;
    }

    /**
     * 
     */
    @SuppressWarnings("unchecked")
    protected BaseDaoImpl()
    {
        Type type = getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType)
        {
            ParameterizedType pType = (ParameterizedType) type;
            this.persistentClass = (Class<T>) pType.getActualTypeArguments()[0];
        }
        else
        {
            this.persistentClass = null;
        }
    }

	public void delete(T entity) throws DaoException {
		throw new DaoException("Not implemented for " + this.getClass().getName());
	}

    public void delete(Auditable<?> entity) throws DaoException
    {
        if (entity == null)
        {
            return;
        }
        try
        {
            //get current user/date
            final Date now = ThreadLocalUtils.getDate();
            final User user = ThreadLocalUtils.getUser();
//            entity.setDeletedDate(now);
//            entity.setDeletedBy(user);
//            entity.setLogicallyDeleted(true);
            getEntityManager().persist(entity);
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

    public void unDelete(Auditable<?> entity) throws DaoException
    {
        if (entity == null)
        {
            return;
        }
        try
        {
//            entity.setDeletedDate(null);
//            entity.setDeletedBy(null);
//            entity.setLogicallyDeleted(null);
            getEntityManager().persist(entity);
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.dao.BaseDao#findAll()
     */
    public List<T> findAll() throws DaoException
    {
        throw new DaoException("No implementation [findAll]");
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.dao.BaseDao#findActive()
     */
    public List<T> findAllActive() throws DaoException
    {
        throw new DaoException("No implementation [findActive]");
    }

    public T findById(Serializable id) throws DaoException
    {
        try
        {
            return id == null ? null : getEntityManager().find(persistentClass, id);
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
	 * @see au.gov.qld.fire.dao.BaseDao#saveOrUpdate(java.lang.Object)
	 */
	public void saveOrUpdate(T entity) throws DaoException
	{
        try
        {
            getEntityManager().persist(entity);
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
	}

    public void save(T entity) throws DaoException
    {
        try
        {
            getEntityManager().persist(entity);
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

    public void merge(T entity) throws DaoException
    {
        try
        {
            getEntityManager().merge(entity);
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

	/**
     * set query parameters
     * @param qry
     * @param parameters
     */
    protected void updateQuery(org.hibernate.Query qry, Map<String, Object> parameters)
    {
        Iterator<Entry<String, Object>> iter = parameters.entrySet().iterator();
        while (iter.hasNext())
        {
            Entry<String, Object> entry = iter.next();
            qry.setParameter(entry.getKey(), entry.getValue());
        }
    }
    protected void updateQuery(javax.persistence.Query qry, Map<String, Object> parameters)
    {
        Iterator<Entry<String, Object>> iter = parameters.entrySet().iterator();
        while (iter.hasNext())
        {
            Entry<String, Object> entry = iter.next();
            qry.setParameter(entry.getKey(), entry.getValue());
        }
    }

    /**
     * 
     * @param s
     * @return
     */
    protected static String nullIfBlank(String s)
    {
        return StringUtils.isBlank(s) ? null : s;
    }

    /**
     * 
     * @param s
     * @return
     */
    protected static String nullIfBlankLike(String s)
    {
        return StringUtils.isBlank(s) ? null : s + "%";
    }

}