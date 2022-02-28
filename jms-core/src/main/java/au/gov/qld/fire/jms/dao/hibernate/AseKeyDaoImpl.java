package au.gov.qld.fire.jms.dao.hibernate;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.dao.hibernate.BaseDaoImpl;
import au.gov.qld.fire.domain.SearchCriteria;
import au.gov.qld.fire.domain.refdata.AseKeyStatusEnum;
import au.gov.qld.fire.jms.dao.AseKeyDao;
import au.gov.qld.fire.jms.dao.AseKeyInvoiceDao;
import au.gov.qld.fire.jms.dao.FileDao;
import au.gov.qld.fire.jms.domain.ase.AseKey;
import au.gov.qld.fire.jms.domain.ase.AseKeySearchCriteria;
import au.gov.qld.fire.jms.domain.file.File;
import au.gov.qld.fire.jms.domain.refdata.FileTypeEnum;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class AseKeyDaoImpl extends BaseDaoImpl<AseKey> implements AseKeyDao
{

	@Autowired private FileDao fileDao;
	@Autowired private AseKeyInvoiceDao invoiceDao;

    /* (non-Javadoc)
	 * @see au.gov.qld.fire.jms.dao.AseKeyDao#findById(java.lang.Long)
	 */
	@Override
	public AseKey findById(Long id) throws DaoException
	{
		AseKey entity = super.findById(id);
		initialize(entity.getFile());
		initialize(entity.getOrder());
		initialize(entity.getSupplier());
		initialize(entity.getContact());
		return entity;
	}

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.dao.hibernate.BaseDaoImpl#saveOrUpdate(java.lang.Object)
	 */
	@Override
	public void saveOrUpdate(AseKey entity) throws DaoException {
        try
        {
        	if (entity.getFile() == null)
        	{
        		entity.setFile(new File());
        	}
        	if (entity.getFile().getId() == null)
        	{
        		if (entity.getFile().getFileType() == null)
        		{
        			entity.getFile().setFileType(FileTypeEnum.ASE_KEY);
        		}
        		fileDao.save(entity.getFile());
        	}
        	invoiceDao.saveOrUpdate(entity.getInvoice());
        	super.saveOrUpdate(entity);
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
	}

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.jms.dao.AseFileDao#findByCriteria(au.gov.qld.fire.jms.domain.ase.AseKeySearchCriteria)
	 */
    @SuppressWarnings("unchecked")
	public List<AseKey> findByCriteria(AseKeySearchCriteria criteria) throws DaoException
	{
        try {
        	String[] names = StringUtils.split(criteria.getContactName());
        	String firstName = names != null && names.length > 0 ? names[0] : null;
        	if (StringUtils.contains(firstName, '*')) {
        		firstName = firstName.replace('*', '%');
        	}
        	String surname = names != null && names.length > 1 ? names[1] : null;
        	if (StringUtils.contains(surname, '*')) {
        		surname = surname.replace('*', '%');
        	}
        	AseKeyStatusEnum status = criteria.getStatus();
        	return getEntityManager()
           		.createNamedQuery("aseKey.findByCriteria")
        		.setMaxResults(criteria.getMaxResults() > 0 ? criteria.getMaxResults() : SearchCriteria.MAX)
                .setParameter("orderNo", getValueLike(criteria.getOrderNo()))
                .setParameter("aseKeyNo", getValueLike(criteria.getAseKeyNo()))
                .setParameter("supplierName", getValueLike(criteria.getSupplierName()))
                .setParameter("firstName", getValueLike(firstName))
                .setParameter("surname", getValueLike(surname))
                .setParameter("status", status != null && status.getId() > 0 ? status.getId() : null)
        		.getResultList();
        }
        catch (Exception e) {
            throw new DaoException(e.getMessage(), e);
        }
	}

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.jms.dao.AseKeyDao#findAseKeyNo(java.lang.String)
	 */
    @SuppressWarnings("unchecked")
	public List<String> findAseKeyNo(String aseKeyNo) throws DaoException
	{
        try {
        	return getEntityManager()
        		.createNamedQuery("aseKey.findAseKeyNo")
       			.setMaxResults(SearchCriteria.DEFAULT_MAX)
                .setParameter("aseKeyNo", getValueLike(aseKeyNo))
        		.getResultList();
        }
        catch (Exception e) {
            throw new DaoException(e.getMessage(), e);
        }
	}

}