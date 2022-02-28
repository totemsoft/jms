package au.gov.qld.fire.jms.dao;

import java.util.List;

import au.gov.qld.fire.dao.BaseDao;
import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.jms.domain.KeyReg;
import au.gov.qld.fire.jms.domain.file.File;

/**
 * KeyReg DAO pattern.
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public interface KeyRegDao extends BaseDao<KeyReg>
{

    /**
     * Find entities by file.
     * @param file
     * @return
     * @throws DaoException
     */
    List<KeyReg> findByFile(File file) throws DaoException;

}