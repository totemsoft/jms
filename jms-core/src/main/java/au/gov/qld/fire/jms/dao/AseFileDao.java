package au.gov.qld.fire.jms.dao;

import java.util.List;

import au.gov.qld.fire.dao.BaseDao;
import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.jms.domain.ase.AseFile;
import au.gov.qld.fire.jms.domain.refdata.AseType;

/**
 * AseFile DAO pattern.
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public interface AseFileDao extends BaseDao<AseFile>
{

    /**
     * Find entities by aseType.
     * @param aseType
     * @return
     * @throws DaoException
     */
    List<AseFile> findByAseType(AseType aseType) throws DaoException;

}