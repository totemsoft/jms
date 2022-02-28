package au.gov.qld.fire.jms.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.dao.hibernate.BaseDaoImpl;
import au.gov.qld.fire.jms.dao.ArchiveDao;
import au.gov.qld.fire.jms.domain.file.ActiveArchive;
import au.gov.qld.fire.jms.domain.file.Archive;
import au.gov.qld.fire.jms.domain.file.FileArchiveSearchCriteria;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class ArchiveDaoImpl extends BaseDaoImpl<Archive> implements ArchiveDao
{

    @SuppressWarnings("unchecked")
    public List<Archive> findByCriteria(FileArchiveSearchCriteria criteria) throws DaoException
    {
        try
        {
            List<ActiveArchive> data = getEntityManager()
            	.createNamedQuery("archive.findByCriteria")
                .setParameter("date", criteria.getDate())
                .setParameter("fileId", criteria.getFileId())
                .setParameter("fcaId", criteria.getFcaId())
                .setParameter("sapCustNo", criteria.getSapCustNo())
                .getResultList();
            List<Archive> result = new ArrayList<Archive>();
            for (ActiveArchive aa : data)
            {
            	result.add(findById(aa.getId()));
            }
            return result;
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.jms.dao.FileArchiveDao#findByCode(java.lang.String)
	 */
	public Archive findByCode(String archiveCode) throws DaoException
	{
        try
        {
        	return (Archive) getEntityManager()
                .createNamedQuery("archive.findByCode")
                .setParameter("code", archiveCode)
                .getSingleResult();
        }
        catch (NoResultException e)
        {
            //return null; // error, find existing code
        	throw new DaoException("No Archive found for archive code: " + archiveCode);
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
	}

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.jms.dao.FileArchiveDao#findByCodeLike(java.lang.String)
	 */
    @SuppressWarnings("unchecked")
	public List<Archive> findByCodeLike(String archiveCode) throws DaoException
	{
        try
        {
        	return getEntityManager()
                .createNamedQuery("archive.findByCodeLike")
                .setParameter("code", getValueLike(archiveCode))
                .getResultList();
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
	}

}