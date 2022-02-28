package au.gov.qld.fire.jms.dao.hibernate;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.dao.hibernate.BaseDaoImpl;
import au.gov.qld.fire.jms.dao.AseKeyPriceDao;
import au.gov.qld.fire.jms.domain.ase.AseKeyPrice;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class AseKeyPriceDaoImpl extends BaseDaoImpl<AseKeyPrice> implements AseKeyPriceDao
{

	private static final String findAll = "aseKeyPriceDao.findAll";

	@Override
    @CacheEvict(value = {findAll}, allEntries = true)
	public void saveOrUpdate(AseKeyPrice entity) throws DaoException {
		super.saveOrUpdate(entity);
	}

	@Override
    @CacheEvict(value = {findAll}, allEntries = true)
	public void save(AseKeyPrice entity) throws DaoException {
		super.save(entity);
	}

	@Override
    @CacheEvict(value = {findAll}, allEntries = true)
	public void merge(AseKeyPrice entity) throws DaoException {
		super.merge(entity);
	}

	private List<AseKeyPrice> initialize(List<AseKeyPrice> entities) {
		//final PersistenceUtil jpaUtil = Persistence.getPersistenceUtil();
    	for (AseKeyPrice entity : entities) {
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
	public List<AseKeyPrice> findAll() throws DaoException {
		return initialize(super.findAll());
	}

    /* (non-Javadoc)
	 * @see au.gov.qld.fire.dao.hibernate.BaseDaoImpl#findAllOrderBy()
	 */
	@Override
	protected String findAllOrderBy() {
		return "ORDER BY aseKeyPriceId";
	}

}