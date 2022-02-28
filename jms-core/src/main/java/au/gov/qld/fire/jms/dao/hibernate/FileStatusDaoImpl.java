package au.gov.qld.fire.jms.dao.hibernate;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.dao.hibernate.BaseDaoImpl;
import au.gov.qld.fire.jms.dao.FileStatusDao;
import au.gov.qld.fire.jms.domain.refdata.FileStatus;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class FileStatusDaoImpl extends BaseDaoImpl<FileStatus> implements FileStatusDao
{

	private static final String findAll = "fileStatusDao.findAll";
	//private static final String findAllActive = "fileStatusDao.findAllActive";

    @CacheEvict(value = {findAll}, allEntries = true)
	public void saveOrUpdate(FileStatus entity) throws DaoException {
		super.saveOrUpdate(entity);
	}

    @CacheEvict(value = {findAll}, allEntries = true)
	public void save(FileStatus entity) throws DaoException {
		super.save(entity);
	}

    @CacheEvict(value = {findAll}, allEntries = true)
	public void merge(FileStatus entity) throws DaoException {
		super.merge(entity);
	}

    /* (non-Javadoc)
	 * @see au.gov.qld.fire.dao.hibernate.BaseDaoImpl#findAllOrderBy()
	 */
	@Override
	protected String findAllOrderBy() {
		return "ORDER BY id"; // name
	}

//	private List<FileStatus> initialize(List<FileStatus> entities) {
//		//final PersistenceUtil jpaUtil = Persistence.getPersistenceUtil();
//    	for (FileStatus entity : entities) {
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
	public List<FileStatus> findAll() throws DaoException
	{
		return super.findAll();
	}

}