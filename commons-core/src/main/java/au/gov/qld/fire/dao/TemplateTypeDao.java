package au.gov.qld.fire.dao;

import org.springframework.stereotype.Repository;

import au.gov.qld.fire.domain.refdata.TemplateType;

/**
 * TemplateType DAO pattern.
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Repository
public interface TemplateTypeDao extends BaseDao<TemplateType>
{

    /**
     * Find entity by name (unique).
     * @param name
     * @return
     * @throws DaoException
     */
    TemplateType findByName(String name) throws DaoException;

}