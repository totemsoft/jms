package au.gov.qld.fire.dao.hibernate;

import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.dao.MailTypeDao;
import au.gov.qld.fire.domain.refdata.MailType;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class MailTypeDaoImpl extends BaseDaoImpl<MailType> implements MailTypeDao
{

	private static final String findAll = "mailTypeDao.findAll";
	private static final String findAllActive = "mailTypeDao.findAllActive";

	@Override
    @CacheEvict(value = {findAll, findAllActive}, allEntries = true)
	public void saveOrUpdate(MailType entity) throws DaoException {
		super.saveOrUpdate(entity);
	}

	@Override
    @CacheEvict(value = {findAll, findAllActive}, allEntries = true)
	public void save(MailType entity) throws DaoException {
		super.save(entity);
	}

	@Override
    @CacheEvict(value = {findAll, findAllActive}, allEntries = true)
	public void merge(MailType entity) throws DaoException {
		super.merge(entity);
	}

	private List<MailType> initialize(List<MailType> entities) {
		//final PersistenceUtil jpaUtil = Persistence.getPersistenceUtil();
    	for (MailType entity : entities) {
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
	public List<MailType> findAll() throws DaoException {
		return initialize(super.findAll());
	}

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.dao.hibernate.BaseDaoImpl#findAllActive()
	 */
	@Override
    @Cacheable(value = findAllActive)
	public List<MailType> findAllActive() throws DaoException {
		return initialize(super.findAllActive());
	}

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.dao.hibernate.BaseDaoImpl#findAllOrderBy()
	 */
	@Override
	protected String findAllOrderBy() {
		return "ORDER BY name";
	}

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.dao.MailTypeDao#findByName(java.lang.String)
	 */
	public MailType findByName(String name) throws DaoException
	{
        try
        {
        	return (MailType) getEntityManager()
        		.createNamedQuery("mailType.findByName")
        		.setParameter("name", name)
        		.getSingleResult();
        }
        catch (NoResultException e)
        {
            return null;
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
	}

}