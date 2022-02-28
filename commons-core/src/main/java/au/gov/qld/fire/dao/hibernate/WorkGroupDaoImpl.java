package au.gov.qld.fire.dao.hibernate;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.dao.WorkGroupDao;
import au.gov.qld.fire.domain.refdata.WorkGroup;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class WorkGroupDaoImpl extends BaseDaoImpl<WorkGroup> implements WorkGroupDao
{

	private static final String findAll = "workGroupDao.findAll";
	private static final String findAllActive = "workGroupDao.findAllActive";

    @CacheEvict(value = {findAll, findAllActive}, allEntries = true)
	public void saveOrUpdate(WorkGroup entity) throws DaoException {
		super.saveOrUpdate(entity);
	}

    @CacheEvict(value = {findAll, findAllActive}, allEntries = true)
	public void save(WorkGroup entity) throws DaoException {
		super.save(entity);
	}

    @CacheEvict(value = {findAll, findAllActive}, allEntries = true)
	public void merge(WorkGroup entity) throws DaoException {
		super.merge(entity);
	}

    /* (non-Javadoc)
	 * @see au.gov.qld.fire.dao.hibernate.BaseDaoImpl#findAllOrderBy()
	 */
	@Override
	protected String findAllOrderBy() {
		return "ORDER BY name";
	}

	/**
	 * 
	 * @param entities
	 * @return
	 */
	private List<WorkGroup> initialize(List<WorkGroup> entities) {
		//final PersistenceUtil jpaUtil = Persistence.getPersistenceUtil();
    	for (WorkGroup entity : entities) {
    		//initialize(entity.getCreatedBy());
    		//initialize(entity.getUpdatedBy());
    	}
		return entities;
	}

    /* (non-Javadoc)
	 * @see au.gov.qld.fire.dao.hibernate.BaseDaoImpl#findAll()
	 */
    @Cacheable(value = findAll)
	public List<WorkGroup> findAll() throws DaoException {
		return initialize(super.findAll());
	}

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.dao.hibernate.BaseDaoImpl#findAllActive()
	 */
    @Cacheable(value = findAllActive)
	public List<WorkGroup> findAllActive() throws DaoException {
		return initialize(super.findAllActive());
	}

	/* (non-Javadoc)
     * @see au.gov.qld.fire.jms.dao.WorkGroupDao#findWorkGroup(java.lang.String)
     */
    public List<String> findWorkGroup(String workGroup) throws DaoException
    {
        return findValueLike("workGroup.findWorkGroup", workGroup);
    }

}