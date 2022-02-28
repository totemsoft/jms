package au.gov.qld.fire.jms.dao.hibernate;

import java.util.List;

import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.dao.hibernate.BaseDaoImpl;
import au.gov.qld.fire.jms.dao.AseFileDao;
import au.gov.qld.fire.jms.domain.ase.AseFile;
import au.gov.qld.fire.jms.domain.refdata.AseType;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class AseFileDaoImpl extends BaseDaoImpl<AseFile> implements AseFileDao
{

    /* (non-Javadoc)
	 * @see au.gov.qld.fire.dao.hibernate.BaseDaoImpl#saveOrUpdate(java.lang.Object)
	 */
	@Override
	public void saveOrUpdate(AseFile entity) throws DaoException {
        try
        {
        	entity.getAseConn().setAseFile(entity);
        	super.saveOrUpdate(entity);
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
	}

	/* (non-Javadoc)
     * @see au.gov.qld.fire.jms.dao.AseFileDao#findByAseType(au.gov.qld.fire.jms.domain.refdata.AseType)
     */
    @SuppressWarnings("unchecked")
    public List<AseFile> findByAseType(AseType aseType) throws DaoException
    {
        try
        {
        	return getEntityManager()
        		.createNamedQuery("aseFile.findByAseType")
                .setParameter("aseType", aseType)
                .getResultList();
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

}