package au.gov.qld.fire.jms.dao;

import java.util.Date;
import java.util.List;

import au.gov.qld.fire.dao.BaseDao;
import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.jms.domain.ase.AseChange;
import au.gov.qld.fire.jms.domain.ase.AseFile;
import au.gov.qld.fire.jms.domain.file.FileSearchCriteria;

/**
 * AseChange DAO pattern.
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public interface AseChangeDao extends BaseDao<AseChange>
{

    /**
     * Find entities by aseFile.
     * @param aseFile
     * @return
     * @throws DaoException
     */
    List<AseChange> findByAseFile(AseFile aseFile) throws DaoException;

    /**
     * Find entities by dateChange.
     * @param criteria
     * @param dateChange
     * @return
     * @throws DaoException
     */
    List<AseChange> findByDateChange(FileSearchCriteria criteria, Date dateChange) throws DaoException;

}