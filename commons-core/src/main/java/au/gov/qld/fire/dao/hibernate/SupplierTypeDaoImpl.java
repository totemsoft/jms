package au.gov.qld.fire.dao.hibernate;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.dao.SupplierTypeDao;
import au.gov.qld.fire.domain.refdata.SupplierType;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class SupplierTypeDaoImpl extends BaseDaoImpl<SupplierType> implements SupplierTypeDao
{

	private static final String findAll = "supplierTypeDao.findAll";
	private static final String findAllActive = "supplierTypeDao.findAllActive";

	@Override
    @CacheEvict(value = {findAll, findAllActive}, allEntries = true)
	public void saveOrUpdate(SupplierType entity) throws DaoException {
		super.saveOrUpdate(entity);
	}

	@Override
    @CacheEvict(value = {findAll, findAllActive}, allEntries = true)
	public void save(SupplierType entity) throws DaoException {
		super.save(entity);
	}

	@Override
    @CacheEvict(value = {findAll, findAllActive}, allEntries = true)
	public void merge(SupplierType entity) throws DaoException {
		super.merge(entity);
	}

	private List<SupplierType> initialize(List<SupplierType> entities) {
		//final PersistenceUtil jpaUtil = Persistence.getPersistenceUtil();
    	for (SupplierType entity : entities) {
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
	public List<SupplierType> findAll() throws DaoException {
		return initialize(super.findAll());
	}

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.dao.hibernate.BaseDaoImpl#findAllActive()
	 */
	@Override
    @Cacheable(value = findAllActive)
	public List<SupplierType> findAllActive() throws DaoException {
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
     * @see au.gov.qld.fire.jms.dao.SupplierTypeDao#findByName(java.lang.String)
     */
    public SupplierType findByName(String name) throws DaoException
    {
        try
        {
        	return (SupplierType) getEntityManager()
                .createNamedQuery("supplierType.findByName")
                .setParameter("name", name)
                .getSingleResult();
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

}