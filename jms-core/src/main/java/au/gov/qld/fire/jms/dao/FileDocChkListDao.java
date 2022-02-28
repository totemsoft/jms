package au.gov.qld.fire.jms.dao;

import java.util.List;

import au.gov.qld.fire.dao.BaseDao;
import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.jms.domain.file.File;
import au.gov.qld.fire.jms.domain.file.FileDocChkList;

/**
 * FileDocChkList DAO pattern.
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public interface FileDocChkListDao extends BaseDao<FileDocChkList>
{

    /**
     * Find entities by file.
     * @param file
     * @return
     * @throws DaoException
     */
    List<FileDocChkList> findByFile(File file) throws DaoException;

}