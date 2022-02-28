package au.gov.qld.fire.jms.dao;

import java.util.List;

import au.gov.qld.fire.dao.BaseDao;
import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.domain.BasePair;
import au.gov.qld.fire.jms.domain.ase.AseChangeSearchCriteria;
import au.gov.qld.fire.jms.domain.file.ActiveAseFile;
import au.gov.qld.fire.jms.domain.file.ActiveFile;
import au.gov.qld.fire.jms.domain.file.File;
import au.gov.qld.fire.jms.domain.file.FileSearchCriteria;

/**
 * File DAO pattern.
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public interface FileDao extends BaseDao<File>
{

    /**
     * Find entities by fileStatus.
     * @param id
     * @return
     * @throws DaoException
     */
    List<File> findByStatus(Long fileStatus) throws DaoException;

    /**
     * 
     * @param sapCustNo
     * @return
     * @throws DaoException
     */
	File findBySapCustNo(Long sapCustNo) throws DaoException;

    /**
     * 
     * @param fileNo
     * @return
     * @throws DaoException
     */
    List<BasePair> findFileNo(String fileNo) throws DaoException;

    /**
     * 
     * @param criteria
     * @return
     * @throws DaoException
     */
    List<ActiveFile> findFilesByCriteria(FileSearchCriteria criteria) throws DaoException;

    /**
     * 
     * @param criteria
     * @return
     * @throws DaoException
     */
    List<ActiveAseFile> findAseChangeFiles(AseChangeSearchCriteria criteria) throws DaoException;

}