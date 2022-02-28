package au.gov.qld.fire.jms.dao;

import java.util.List;

import au.gov.qld.fire.dao.BaseDao;
import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.jms.domain.file.File;
import au.gov.qld.fire.jms.domain.file.FileDoc;

/**
 * FileDoc DAO pattern.
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public interface FileDocDao extends BaseDao<FileDoc>
{

    /**
     * Find entities by file.
     * @param file
     * @return
     * @throws DaoException
     */
    List<FileDoc> findByFile(File file) throws DaoException;

}