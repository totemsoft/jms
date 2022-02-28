package au.gov.qld.fire.jms.dao;

import au.gov.qld.fire.dao.BaseDao;
import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.jms.domain.refdata.AseType;

/**
 * AseType DAO pattern.
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public interface AseTypeDao extends BaseDao<AseType>
{

    /**
     * Find entity by name (unique).
     * @param name
     * @return
     * @throws DaoException
     */
    AseType findByName(String name) throws DaoException;

}