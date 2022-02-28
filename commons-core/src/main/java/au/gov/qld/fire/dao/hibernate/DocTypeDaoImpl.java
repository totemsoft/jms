package au.gov.qld.fire.dao.hibernate;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.dao.DocTypeDao;
import au.gov.qld.fire.domain.refdata.DocType;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class DocTypeDaoImpl extends BaseDaoImpl<DocType> implements DocTypeDao
{

	private static final String findAll = "docTypeDao.findAll";
	private static final String findAllActive = "docTypeDao.findAllActive";

	@Override
    @CacheEvict(value = {findAll, findAllActive}, allEntries = true)
	public void saveOrUpdate(DocType entity) throws DaoException {
		super.saveOrUpdate(entity);
	}

	@Override
    @CacheEvict(value = {findAll, findAllActive}, allEntries = true)
	public void save(DocType entity) throws DaoException {
		super.save(entity);
	}

	@Override
    @CacheEvict(value = {findAll, findAllActive}, allEntries = true)
	public void merge(DocType entity) throws DaoException {
		super.merge(entity);
	}

	private List<DocType> initialize(List<DocType> entities) {
		//final PersistenceUtil jpaUtil = Persistence.getPersistenceUtil();
    	for (DocType entity : entities) {
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
	public List<DocType> findAll() throws DaoException {
		return initialize(super.findAll());
	}

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.dao.hibernate.BaseDaoImpl#findAllActive()
	 */
	@Override
    @Cacheable(value = findAllActive)
	public List<DocType> findAllActive() throws DaoException {
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
     * @see au.gov.qld.fire.jms.dao.DocTypeDao#findByName(java.lang.String)
     */
    public DocType findByName(String name) throws DaoException
    {
        try
        {
        	return (DocType) getEntityManager()
                .createNamedQuery("docType.findByName")
        	    .setParameter("name", name)
        	    .getSingleResult();
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

}