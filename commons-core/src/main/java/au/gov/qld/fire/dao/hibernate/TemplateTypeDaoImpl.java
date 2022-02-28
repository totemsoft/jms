package au.gov.qld.fire.dao.hibernate;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.dao.TemplateTypeDao;
import au.gov.qld.fire.domain.refdata.TemplateType;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class TemplateTypeDaoImpl extends BaseDaoImpl<TemplateType> implements TemplateTypeDao
{

	private static final String findAll = "templateTypeDao.findAll";
	private static final String findAllActive = "templateTypeDao.findAllActive";

	@Override
    @CacheEvict(value = {findAll, findAllActive}, allEntries = true)
	public void saveOrUpdate(TemplateType entity) throws DaoException {
		super.saveOrUpdate(entity);
	}

	@Override
    @CacheEvict(value = {findAll, findAllActive}, allEntries = true)
	public void save(TemplateType entity) throws DaoException {
		super.save(entity);
	}

	@Override
    @CacheEvict(value = {findAll, findAllActive}, allEntries = true)
	public void merge(TemplateType entity) throws DaoException {
		super.merge(entity);
	}

	private List<TemplateType> initialize(List<TemplateType> entities) {
		//final PersistenceUtil jpaUtil = Persistence.getPersistenceUtil();
    	for (TemplateType entity : entities) {
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
	public List<TemplateType> findAll() throws DaoException {
		return initialize(super.findAll());
	}

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.dao.hibernate.BaseDaoImpl#findAllActive()
	 */
	@Override
    @Cacheable(value = findAllActive)
	public List<TemplateType> findAllActive() throws DaoException {
		return initialize(super.findAllActive());
	}

	/* (non-Javadoc)
     * @see au.gov.qld.fire.jms.dao.TemplateTypeDao#findByName(java.lang.String)
     */
    public TemplateType findByName(String name) throws DaoException
    {
        try
        {
        	return (TemplateType) getEntityManager()
        	    .createNamedQuery("templateType.findByName")
                .setParameter("name", name)
                .getSingleResult();
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

}