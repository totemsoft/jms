package au.gov.qld.fire.jms.dao;

import java.util.List;

import au.gov.qld.fire.dao.BaseDao;
import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.jms.domain.file.File;
import au.gov.qld.fire.jms.domain.sap.SapHeader;

/**
 * SapHeader DAO pattern.
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public interface SapHeaderDao extends BaseDao<SapHeader>
{

    /**
     * Find entity by file.
     * @param file
     * @return
     * @throws DaoException
     */
    SapHeader findByFile(File file) throws DaoException;

    /**
     * 
     * @param sapCustNo
     * @return
     * @throws DaoException
     */
    List<String> findSapCustNo(String sapCustNo) throws DaoException;

}