package au.gov.qld.fire.jms.dao.hibernate;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.dao.hibernate.BaseDaoImpl;
import au.gov.qld.fire.jms.dao.JobTypeDao;
import au.gov.qld.fire.jms.domain.refdata.JobType;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class JobTypeDaoImpl extends BaseDaoImpl<JobType> implements JobTypeDao
{

	private static final String findAll = "jobTypeDao.findAll";
	private static final String findAllActive = "jobTypeDao.findAllActive";

    @CacheEvict(value = {findAll, findAllActive}, allEntries = true)
	public void saveOrUpdate(JobType entity) throws DaoException {
		super.saveOrUpdate(entity);
	}

    @CacheEvict(value = {findAll, findAllActive}, allEntries = true)
	public void save(JobType entity) throws DaoException {
		super.save(entity);
	}

    @CacheEvict(value = {findAll, findAllActive}, allEntries = true)
	public void merge(JobType entity) throws DaoException {
		super.merge(entity);
	}

    /* (non-Javadoc)
	 * @see au.gov.qld.fire.dao.hibernate.BaseDaoImpl#findAllOrderBy()
	 */
	@Override
	protected String findAllOrderBy() {
		return "ORDER BY name";
	}

	private List<JobType> initialize(List<JobType> entities) {
		//final PersistenceUtil jpaUtil = Persistence.getPersistenceUtil();
    	for (JobType entity : entities) {
    		initialize(entity.getActionCode());
    		initialize(entity.getCloseJobSecurityGroup());
    		initialize(entity.getSecurityGroup());
    		initialize(entity.getWorkGroup());
    		initialize(entity.getCreatedBy());
    		initialize(entity.getUpdatedBy());
    	}
		return entities;
	}

    /* (non-Javadoc)
	 * @see au.gov.qld.fire.dao.hibernate.BaseDaoImpl#findAll()
	 */
    @Cacheable(value = findAll)
	public List<JobType> findAll() throws DaoException {
		return initialize(super.findAll());
	}

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.dao.hibernate.BaseDaoImpl#findAllActive()
	 */
    @Cacheable(value = findAllActive)
	public List<JobType> findAllActive() throws DaoException {
		return initialize(super.findAllActive());
	}

	/* (non-Javadoc)
     * @see au.gov.qld.fire.jms.dao.JobTypeDao#findByName(java.lang.String)
     */
    public JobType findByName(String name) throws DaoException
    {
        try
        {
        	return (JobType) getEntityManager()
        	    .createNamedQuery("jobType.findByName")
                .setParameter("name", name)
                .getSingleResult();
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

}