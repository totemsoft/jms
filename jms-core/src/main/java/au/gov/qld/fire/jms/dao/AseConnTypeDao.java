package au.gov.qld.fire.jms.dao;

import au.gov.qld.fire.dao.BaseDao;
import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.jms.domain.refdata.AseConnType;

/**
 * AseConnType DAO pattern.
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public interface AseConnTypeDao extends BaseDao<AseConnType>
{

    /**
     * Find entity by name (unique).
     * @param name
     * @return
     * @throws DaoException
     */
    AseConnType findByName(String name) throws DaoException;

}