package au.gov.qld.fire.dao.hibernate;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.dao.LeaveTypeDao;
import au.gov.qld.fire.domain.refdata.LeaveType;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class LeaveTypeDaoImpl extends BaseDaoImpl<LeaveType> implements LeaveTypeDao
{

	private static final String findAll = "leaveTypeDao.findAll";
	private static final String findAllActive = "leaveTypeDao.findAllActive";

	@Override
    @CacheEvict(value = {findAll, findAllActive}, allEntries = true)
	public void saveOrUpdate(LeaveType entity) throws DaoException {
		super.saveOrUpdate(entity);
	}

	@Override
    @CacheEvict(value = {findAll, findAllActive}, allEntries = true)
	public void save(LeaveType entity) throws DaoException {
		super.save(entity);
	}

	@Override
    @CacheEvict(value = {findAll, findAllActive}, allEntries = true)
	public void merge(LeaveType entity) throws DaoException {
		super.merge(entity);
	}

	private List<LeaveType> initialize(List<LeaveType> entities) {
		//final PersistenceUtil jpaUtil = Persistence.getPersistenceUtil();
    	for (LeaveType entity : entities) {
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
	public List<LeaveType> findAll() throws DaoException {
		return initialize(super.findAll());
	}

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.dao.hibernate.BaseDaoImpl#findAllActive()
	 */
	@Override
    @Cacheable(value = findAllActive)
	public List<LeaveType> findAllActive() throws DaoException {
		return initialize(super.findAllActive());
	}

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.dao.hibernate.BaseDaoImpl#findAllOrderBy()
	 */
	@Override
	protected String findAllOrderBy() {
		return "ORDER BY name";
	}

}