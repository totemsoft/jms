package au.gov.qld.fire.dao.hibernate;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.dao.UserTypeDao;
import au.gov.qld.fire.domain.refdata.UserType;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class UserTypeDaoImpl extends BaseDaoImpl<UserType> implements UserTypeDao
{

	private static final String findAll = "userTypeDao.findAll";
	private static final String findAllActive = "userTypeDao.findAllActive";

	@Override
    @CacheEvict(value = {findAll, findAllActive}, allEntries = true)
	public void saveOrUpdate(UserType entity) throws DaoException {
		super.saveOrUpdate(entity);
	}

	@Override
    @CacheEvict(value = {findAll, findAllActive}, allEntries = true)
	public void save(UserType entity) throws DaoException {
		super.save(entity);
	}

	@Override
    @CacheEvict(value = {findAll, findAllActive}, allEntries = true)
	public void merge(UserType entity) throws DaoException {
		super.merge(entity);
	}

//	private List<UserType> initialize(List<UserType> entities) {
//		//final PersistenceUtil jpaUtil = Persistence.getPersistenceUtil();
//    	for (UserType entity : entities) {
//    		initialize(entity.getCreatedBy());
//    		initialize(entity.getUpdatedBy());
//    	}
//		return entities;
//	}

    /* (non-Javadoc)
	 * @see au.gov.qld.fire.dao.hibernate.BaseDaoImpl#findAll()
	 */
	@Override
    @Cacheable(value = findAll)
	public List<UserType> findAll() throws DaoException {
		return super.findAll();
	}

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.dao.hibernate.BaseDaoImpl#findAllActive()
	 */
	@Override
    @Cacheable(value = findAllActive)
	public List<UserType> findAllActive() throws DaoException {
		return super.findAllActive();
	}

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.dao.hibernate.BaseDaoImpl#findAllOrderBy()
	 */
	@Override
	protected String findAllOrderBy() {
		return "ORDER BY name";
	}

}