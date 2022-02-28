package au.gov.qld.fire.dao;

import org.springframework.stereotype.Repository;

import au.gov.qld.fire.domain.refdata.DocType;

/**
 * DocType DAO pattern.
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Repository
public interface DocTypeDao extends BaseDao<DocType>
{

    /**
     * Find entity by name (unique).
     * @param name
     * @return
     * @throws DaoException
     */
    DocType findByName(String name) throws DaoException;

}