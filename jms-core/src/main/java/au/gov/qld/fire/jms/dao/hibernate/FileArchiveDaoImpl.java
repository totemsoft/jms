package au.gov.qld.fire.jms.dao.hibernate;

import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.dao.hibernate.BaseDaoImpl;
import au.gov.qld.fire.jms.dao.FileArchiveDao;
import au.gov.qld.fire.jms.domain.file.FileArchive;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class FileArchiveDaoImpl extends BaseDaoImpl<FileArchive> implements FileArchiveDao
{

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.jms.dao.FileArchiveDao#save(au.gov.qld.fire.jms.domain.file.FileArchive)
	 */
	@Override
	public void save(FileArchive fileArchive) throws DaoException
	{
        try
        {
        	getEntityManager().merge(fileArchive);
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
	}

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.jms.dao.FileArchiveDao#delete(au.gov.qld.fire.jms.domain.file.FileArchive)
	 */
	@Override
	public void delete(FileArchive fileArchive) throws DaoException
	{
        try
        {
        	getEntityManager().remove(fileArchive);
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
	}

}