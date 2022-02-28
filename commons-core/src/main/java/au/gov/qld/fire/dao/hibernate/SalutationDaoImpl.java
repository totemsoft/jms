package au.gov.qld.fire.dao.hibernate;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.dao.SalutationDao;
import au.gov.qld.fire.domain.refdata.Salutation;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class SalutationDaoImpl extends BaseDaoImpl<Salutation> implements SalutationDao
{

	private static final String findAll = "salutationDao.findAll";
	private static final String findAllActive = "salutationDao.findAllActive";

	@Override
    @CacheEvict(value = {findAll, findAllActive}, allEntries = true)
	public void saveOrUpdate(Salutation entity) throws DaoException {
		super.saveOrUpdate(entity);
	}

	@Override
    @CacheEvict(value = {findAll, findAllActive}, allEntries = true)
	public void save(Salutation entity) throws DaoException {
		super.save(entity);
	}

	@Override
    @CacheEvict(value = {findAll, findAllActive}, allEntries = true)
	public void merge(Salutation entity) throws DaoException {
		super.merge(entity);
	}

	private List<Salutation> initialize(List<Salutation> entities) {
		//final PersistenceUtil jpaUtil = Persistence.getPersistenceUtil();
    	for (Salutation entity : entities) {
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
	public List<Salutation> findAll() throws DaoException
	{
		return initialize(super.findAll());
	}

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.dao.hibernate.BaseDaoImpl#findAllActive()
	 */
	@Override
    @Cacheable(value = findAllActive)
	public List<Salutation> findAllActive() throws DaoException
	{
		return initialize(super.findAllActive());
	}

}