package au.gov.qld.fire.jms.dao.hibernate;

import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.dao.hibernate.BaseDaoImpl;
import au.gov.qld.fire.jms.dao.ActionCodeDao;
import au.gov.qld.fire.jms.domain.refdata.ActionCode;
import au.gov.qld.fire.jms.domain.refdata.ActionCodeSearchCriteria;
import au.gov.qld.fire.jms.domain.refdata.ActionType;
import au.gov.qld.fire.jms.domain.refdata.ActionTypeEnum;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class ActionCodeDaoImpl extends BaseDaoImpl<ActionCode> implements ActionCodeDao
{

	private static final String findAll = "actionCodeDao.findAll";
	private static final String findAllActive = "actionCodeDao.findAllActive";
	private static final String findAllByActionType = "actionCodeDao.findAllByActionType";
	private static final String findAllByJobType = "actionCodeDao.findAllByJobType";
	private static final String findAllByActionTypeJobType = "actionCodeDao.findAllByActionTypeJobType";

	@Override
    @CacheEvict(value = {findAll, findAllActive, findAllByActionType, findAllByJobType, findAllByActionTypeJobType}, allEntries = true)
	public void saveOrUpdate(ActionCode entity) throws DaoException {
		super.saveOrUpdate(entity);
	}

	@Override
    @CacheEvict(value = {findAll, findAllActive, findAllByActionType, findAllByJobType, findAllByActionTypeJobType}, allEntries = true)
	public void save(ActionCode entity) throws DaoException {
		super.save(entity);
	}

	@Override
    @CacheEvict(value = {findAll, findAllActive, findAllByActionType, findAllByJobType, findAllByActionTypeJobType}, allEntries = true)
	public void merge(ActionCode entity) throws DaoException {
		super.merge(entity);
	}

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.dao.hibernate.BaseDaoImpl#findAllOrderBy()
	 */
	@Override
	protected String findAllOrderBy()
	{
		return "ORDER BY actionType.name, code";
	}

    /* (non-Javadoc)
	 * @see au.gov.qld.fire.jms.dao.ActionCodeDao#findByIdInitialise(java.lang.Long)
	 */
	public ActionCode findByIdInitialise(Long id) throws DaoException
	{
        try
        {
    		return initialize(findById(id));
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
	}

	private ActionCode initialize(ActionCode entity) {
		//final PersistenceUtil jpaUtil = Persistence.getPersistenceUtil();
		initialize(entity.getActionType());
		initialize(entity.getJobType());
		initialize(entity.getTemplate());
		initialize(entity.getDocumentTemplate());
		initialize(entity.getWorkGroup());
		initialize(entity.getArea());
		initialize(entity.getCreatedBy());
		initialize(entity.getUpdatedBy());
		return entity;
	}

	private List<ActionCode> initialize(List<ActionCode> entities) {
		//final PersistenceUtil jpaUtil = Persistence.getPersistenceUtil();
    	for (ActionCode entity : entities) {
    		initialize(entity);
    	}
		return entities;
	}

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.dao.hibernate.BaseDaoImpl#findAll()
	 */
	@Override
    @Cacheable(value = findAll)
	public List<ActionCode> findAll() throws DaoException {
		return initialize(super.findAll());
	}

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.dao.hibernate.BaseDaoImpl#findAllActive()
	 */
	@Override
    @Cacheable(value = findAllActive)
	public List<ActionCode> findAllActive() throws DaoException {
		return initialize(super.findAllActive());
	}

	/* (non-Javadoc)
     * @see au.gov.qld.fire.jms.dao.ActionCodeDao#findByCode(java.lang.String)
     */
    public ActionCode findByCode(String code) throws DaoException
    {
        try
        {
            return (ActionCode) getEntityManager()
                .createNamedQuery("actionCode.findByCode")
                .setParameter("code", code)
                .getSingleResult();
        }
        catch (NoResultException ignore)
        {
            return null;
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.dao.ActionCodeDao#findAllByCriteria(au.gov.qld.fire.jms.domain.refdata.ActionCodeSearchCriteria)
     */
    @SuppressWarnings("unchecked")
    public List<ActionCode> findAllByCriteria(ActionCodeSearchCriteria criteria) throws DaoException
    {
        try
        {
            return getEntityManager()
                .createNamedQuery("actionCode.findAllByCriteria")
                .setParameter("active", criteria.getActive())
                .setParameter("actionTypes", criteria.getActionTypes())
                .getResultList();
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.dao.ActionCodeDao#findAllByActionType(au.gov.qld.fire.jms.domain.refdata.ActionType)
     */
    @SuppressWarnings("unchecked")
    @Cacheable(value = findAllByActionType, key = "#actionType.id")
    public List<ActionCode> findAllByActionType(ActionType actionType) throws DaoException
    {
        try
        {
        	return getEntityManager()
        	    .createNamedQuery("actionCode.findAllByActionType")
                .setParameter("actionTypeId", actionType.getId())
                .getResultList();
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.dao.ActionCodeDao#findAllByActionType(au.gov.qld.fire.jms.domain.refdata.ActionTypeEnum)
     */
    @Cacheable(value = findAllByActionType, key = "#actionType.id")
    public List<ActionCode> findAllByActionType(ActionTypeEnum actionType) throws DaoException
    {
        return findAllByActionType(new ActionType(actionType.getId()));
    }

    /* (non-Javadoc)
	 * @see au.gov.qld.fire.jms.dao.ActionCodeDao#findAllByActionTypeJobType(au.gov.qld.fire.jms.domain.refdata.ActionTypeEnum, java.lang.Long)
	 */
    @SuppressWarnings("unchecked")
    @Cacheable(value = findAllByActionTypeJobType, key = "#actionType.id + '_' + #jobTypeId")
	public List<ActionCode> findAllByActionTypeJobType(ActionTypeEnum actionType, Long jobTypeId) throws DaoException
	{
        try
        {
        	return getEntityManager()
        	    .createNamedQuery("actionCode.findAllByActionTypeJobType")
                .setParameter("actionTypeId", actionType.getId())
                .setParameter("jobTypeId", jobTypeId)
                .getResultList();
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
	}

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.jms.dao.ActionCodeDao#findAllByJobType(java.lang.Long)
	 */
    @SuppressWarnings("unchecked")
    @Cacheable(value = findAllByJobType, key = "#jobTypeId")
	public List<ActionCode> findAllByJobType(Long jobTypeId) throws DaoException
	{
        try
        {
        	return getEntityManager()
        	    .createNamedQuery("actionCode.findAllByJobType")
                .setParameter("jobTypeId", jobTypeId)
                .getResultList();
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
	}

	/* (non-Javadoc)
     * @see au.gov.qld.fire.jms.dao.ActionCodeDao#findActionCode(java.lang.String)
     */
    public List<String> findActionCode(String actionCode) throws DaoException
    {
        return findValueLike("actionCode.findActionCode", actionCode);
    }

}