package au.gov.qld.fire.dao.hibernate;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import au.gov.qld.fire.dao.BaseDao;
import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.domain.BaseModel;
import au.gov.qld.fire.domain.SearchCriteria;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public abstract class BaseDaoImpl<T extends BaseModel<?>> implements BaseDao<T>
{

	/** logger. */
    protected final Logger LOG = Logger.getLogger(getClass());

    protected static final int MAX_RESULT = 300;

    protected static final String resultIsNull = "#result == null";

    /** persistentClass. */
    private final Class<T> persistentClass;

    @PersistenceContext//(unitName = "jms")
    private EntityManager entityManager;

    protected EntityManager getEntityManager() {
		return entityManager;
	}

    /**
     * @deprecated
     * @return
     */
	protected final Session getSession()
	{
		//return (Session) entityManager.getDelegate();
		return entityManager.unwrap(Session.class);
		//return entityManager.unwrap(SessionImplementor.class);
	}

	/**
	 * Force initialization of a proxy or persistent collection.
	 * @param proxy - a persistable object, proxy, persistent collection or null
	 * @throws HibernateException
	 */
	protected final void initialize(Object proxy) throws HibernateException
	{
		if (proxy != null && !Hibernate.isInitialized(proxy)) {
			Hibernate.initialize(proxy);
		}
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

    /**
     * 
     * @see au.gov.qld.fire.dao.BaseDao#saveOrUpdate(java.lang.Object)
     */
    public void saveOrUpdate(T entity) throws DaoException
    {
    	if (entity == null)
    	{
    		return;
    	}
        try
        {
            //getSession().saveOrUpdate(entity);
        	if (entity.getId() == null)
        	{
            	getEntityManager().persist(entity);
        	}
        	else
        	{
            	getEntityManager().merge(entity);
        	}
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
	 * @see au.gov.qld.fire.dao.BaseDao#save(java.lang.Object)
	 */
	//@Override
	public void save(T entity) throws DaoException
	{
    	if (entity == null)
    	{
    		return;
    	}
        try
        {
            getEntityManager().persist(entity);
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
	}

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.dao.BaseDao#merge(java.lang.Object)
	 */
	//@Override
	public void merge(T entity) throws DaoException
	{
    	if (entity == null)
    	{
    		return;
    	}
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
     * @see au.gov.qld.fire.dao.BaseDao#delete(java.lang.Object)
     */
    public void delete(T entity) throws DaoException
    {
        if (entity == null)
        {
            return;
        }
        try
        {
            //getEntityManager().remove(entity);
            throw new IllegalAccessException("Could not be deleted: " + entity);
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
	 * @see au.gov.qld.fire.dao.BaseDao#detach(java.lang.Object)
	 */
	public void detach(T entity) throws DaoException
	{
		if (entity != null) {
			getEntityManager().detach(entity);
		}
	}

    /* (non-Javadoc)
	 * @see au.gov.qld.fire.dao.BaseDao#refresh(java.lang.Object)
	 */
	public void refresh(T entity) throws DaoException
	{
		if (entity != null) {
			getEntityManager().refresh(entity);
		}
	}

	/* (non-Javadoc)
     * @see au.gov.qld.fire.dao.BaseDao#findById(java.io.Serializable)
     */
    public final T findById(Serializable id) throws DaoException
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

    /**
     * 
     * @return
     */
    protected String findAllOrderBy()
    {
    	return "";
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.dao.BaseDao#findAll()
     */
    @SuppressWarnings("unchecked")
    public List<T> findAll() throws DaoException
    {
        try
        {
            return (List<T>) getEntityManager()
            	.createQuery("FROM " + persistentClass.getName() + " " + findAllOrderBy())
                .getResultList();
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.dao.BaseDao#findAllActive()
     */
    @SuppressWarnings("unchecked")
    public List<T> findAllActive() throws DaoException
    {
        try
        {
            return (List<T>) getEntityManager()
            	.createQuery("FROM " + persistentClass.getName() + " WHERE active = true " + findAllOrderBy())
                .getResultList();
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
     * @param value
     * @return
     */
    protected String getValueLike(String value)
    {
    	return StringUtils.isBlank(value) ? null : (value.endsWith("%") ? value : value + "%");
    }

    protected Character getYesNo(Boolean value)
    {
    	return value == null ? null : (value ? 'Y' : 'N');
    }

    /**
     * Search entity code/name by value like 'value%'
     * @param query
     * @param value
     * @return
     * @throws DaoException
     */
    @SuppressWarnings("unchecked")
    protected List<String> findValueLike(String query, String value) throws DaoException
    {
        try
        {
        	if (query.indexOf(' ') > 0)
        	{
        		return getEntityManager()
        			.createQuery(query)
        			.setMaxResults(SearchCriteria.DEFAULT_MAX)
        			.setParameter("value", getValueLike(value))
        			.getResultList();
        	}
        	// TODO: distinguish between native and hql name query
            return (List<String>) getSession()
            	.getNamedQuery(query)
       			.setMaxResults(SearchCriteria.DEFAULT_MAX)
            	.setParameter("value", getValueLike(value))
            	.list();
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

    /**
     * Get SQL Query.
     * @param query
     * @return
     */
    protected Query getQuery(String query)
    {
        try
        {
            if (query.indexOf(' ') >= 0)
            {
                return getSession().createSQLQuery(query);
            }
            return getSession().getNamedQuery(query);
        }
        catch (Exception e)
        {
            LOG.info("No query found with this name: " + query);
            return null; // no query exists
        }
    }

}