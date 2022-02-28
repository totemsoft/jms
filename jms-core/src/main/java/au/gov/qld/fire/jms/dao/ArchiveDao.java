package au.gov.qld.fire.jms.dao;

import java.util.List;

import au.gov.qld.fire.dao.BaseDao;
import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.jms.domain.file.Archive;
import au.gov.qld.fire.jms.domain.file.FileArchiveSearchCriteria;

/**
 * Archive DAO pattern.
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public interface ArchiveDao extends BaseDao<Archive>
{

    /**
     * 
     * @param criteria
     * @return
     * @throws DaoException
     */
    List<Archive> findByCriteria(FileArchiveSearchCriteria criteria) throws DaoException;

    /**
     * 
     * @param archiveCode
     * @return
     * @throws DaoException
     */
	Archive findByCode(String archiveCode) throws DaoException;

	/**
     * 
     * @param archiveCode
     * @return
     * @throws DaoException
     */
	List<Archive> findByCodeLike(String archiveCode) throws DaoException;

}