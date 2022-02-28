package au.gov.qld.fire.jms.dao.hibernate;

import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.dao.hibernate.BaseDaoImpl;
import au.gov.qld.fire.jms.dao.ActionOutcomeDao;
import au.gov.qld.fire.jms.domain.action.ActionOutcome;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class ActionOutcomeDaoImpl extends BaseDaoImpl<ActionOutcome> implements ActionOutcomeDao
{

	private static final String findAll = "actionOutcomeDao.findAll";

    @CacheEvict(value = {findAll}, allEntries = true)
	public void saveOrUpdate(ActionOutcome entity) throws DaoException {
		super.saveOrUpdate(entity);
	}

    @CacheEvict(value = {findAll}, allEntries = true)
	public void save(ActionOutcome entity) throws DaoException {
		super.save(entity);
	}

    @CacheEvict(value = {findAll}, allEntries = true)
	public void merge(ActionOutcome entity) throws DaoException {
		super.merge(entity);
	}

    /* (non-Javadoc)
	 * @see au.gov.qld.fire.dao.hibernate.BaseDaoImpl#findAllOrderBy()
	 */
	@Override
	protected String findAllOrderBy() {
		return "ORDER BY name";
	}

	private List<ActionOutcome> initialize(List<ActionOutcome> entities) {
		//final PersistenceUtil jpaUtil = Persistence.getPersistenceUtil();
    	for (ActionOutcome entity : entities) {
    		initialize(entity.getCreatedBy());
    		initialize(entity.getUpdatedBy());
    	}
		return entities;
	}

    /* (non-Javadoc)
	 * @see au.gov.qld.fire.dao.hibernate.BaseDaoImpl#findAll()
	 */
    @Cacheable(value = findAll)
	public List<ActionOutcome> findAll() throws DaoException {
		return initialize(super.findAll());
	}

	/* (non-Javadoc)
     * @see au.gov.qld.fire.jms.dao.ActionOutcomeDao#findByName(java.lang.String)
     */
    public ActionOutcome findByName(String name) throws DaoException
    {
        try
        {
            return (ActionOutcome) getEntityManager()
                .createNamedQuery("actionOutcome.findByName")
                .setParameter("name", name)
                .getSingleResult();
        }
        catch (NoResultException ignore)
        {
            return null;
        }
    }

}