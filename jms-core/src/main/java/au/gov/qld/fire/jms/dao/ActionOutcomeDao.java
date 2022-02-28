package au.gov.qld.fire.jms.dao;

import au.gov.qld.fire.dao.BaseDao;
import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.jms.domain.action.ActionOutcome;

/**
 * ActionOutcome DAO pattern.
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public interface ActionOutcomeDao extends BaseDao<ActionOutcome>
{

    /**
     * Find entity by name (unique).
     * @param name
     * @return
     * @throws DaoException
     */
    ActionOutcome findByName(String name) throws DaoException;

}