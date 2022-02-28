package au.gov.qld.fire.jms.dao.hibernate;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.dao.hibernate.BaseDaoImpl;
import au.gov.qld.fire.jms.dao.BuildingContactTypeDao;
import au.gov.qld.fire.jms.domain.refdata.BuildingContactType;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class BuildingContactTypeDaoImpl extends BaseDaoImpl<BuildingContactType> implements BuildingContactTypeDao
{

	private static final String findAll = "buildingContactTypeDao.findAll";
	private static final String findAllActive = "buildingContactTypeDao.findAllActive";

	@Override
    @CacheEvict(value = {findAll, findAllActive}, allEntries = true)
	public void saveOrUpdate(BuildingContactType entity) throws DaoException {
		super.saveOrUpdate(entity);
	}

	@Override
    @CacheEvict(value = {findAll, findAllActive}, allEntries = true)
	public void save(BuildingContactType entity) throws DaoException {
		super.save(entity);
	}

	@Override
    @CacheEvict(value = {findAll, findAllActive}, allEntries = true)
	public void merge(BuildingContactType entity) throws DaoException {
		super.merge(entity);
	}

	private List<BuildingContactType> initialize(List<BuildingContactType> entities) {
		//final PersistenceUtil jpaUtil = Persistence.getPersistenceUtil();
    	for (BuildingContactType entity : entities) {
    		initialize(entity.getCreatedBy());
    		initialize(entity.getUpdatedBy());
    	}
		return entities;
	}

    /* (non-Javadoc)
	 * @see au.gov.qld.fire.dao.hibernate.BaseDaoImpl#findAll()
	 */
	@Override
    @Cacheable(value = findAll)
	public List<BuildingContactType> findAll() throws DaoException {
		return initialize(super.findAll());
	}

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.dao.hibernate.BaseDaoImpl#findAllActive()
	 */
	@Override
    @Cacheable(value = findAllActive)
	public List<BuildingContactType> findAllActive() throws DaoException {
		return initialize(super.findAllActive());
	}

    /* (non-Javadoc)
	 * @see au.gov.qld.fire.dao.hibernate.BaseDaoImpl#findAllOrderBy()
	 */
	@Override
	protected String findAllOrderBy() {
		return "ORDER BY actionOutcome.name";
	}

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.dao.BuildingContactTypeDao#findByName(java.lang.String)
     */
    public BuildingContactType findByName(String name) throws DaoException
    {
        try
        {
        	return (BuildingContactType) getEntityManager()
        	    .createNamedQuery("buildingContactType.findByName")
                .setParameter("name", name)
                .getSingleResult();
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

}