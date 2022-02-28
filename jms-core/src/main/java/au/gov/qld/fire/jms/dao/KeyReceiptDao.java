package au.gov.qld.fire.jms.dao;

import java.util.List;

import au.gov.qld.fire.dao.BaseDao;
import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.jms.domain.KeyReceipt;
import au.gov.qld.fire.jms.domain.file.File;

/**
 * KeyReceipt DAO pattern.
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public interface KeyReceiptDao extends BaseDao<KeyReceipt>
{

    /**
     * Find entities by file.
     * @param file
     * @return
     * @throws DaoException
     */
    List<KeyReceipt> findByFile(File file) throws DaoException;

    /**
     * Find entity by unique key.
     * @param keyReceiptNo
     * @return
     * @throws DaoException
     */
    KeyReceipt findByKeyReceiptNo(String keyReceiptNo) throws DaoException;

}