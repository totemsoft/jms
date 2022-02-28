package au.gov.qld.fire.jms.dao;

import au.gov.qld.fire.dao.BaseDao;
import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.jms.domain.file.FileArchive;

/**
 * Archive DAO pattern.
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public interface FileArchiveDao extends BaseDao<FileArchive>
{

    /**
     * 
     * @param fileArchive
	 * @throws DaoException
     */
	void save(FileArchive fileArchive) throws DaoException;

	/**
	 * 
	 * @param fileArchive
	 * @throws DaoException
	 */
	void delete(FileArchive fileArchive) throws DaoException;

}