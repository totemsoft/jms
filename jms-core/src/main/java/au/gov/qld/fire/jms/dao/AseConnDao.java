package au.gov.qld.fire.jms.dao;

import java.util.List;

import au.gov.qld.fire.dao.BaseDao;
import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.jms.domain.ase.AseConn;
import au.gov.qld.fire.jms.domain.refdata.AseConnType;

/**
 * AseConn DAO pattern.
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public interface AseConnDao extends BaseDao<AseConn>
{

    /**
     * Find entities by aseConnType.
     * @param aseConnType
     * @return
     * @throws DaoException
     */
    List<AseConn> findByAseConnType(AseConnType aseConnType) throws DaoException;

}