package au.gov.qld.fire.jms.dao;

import java.util.List;

import au.gov.qld.fire.dao.BaseDao;
import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.jms.domain.action.ActionOutcome;
import au.gov.qld.fire.jms.domain.action.ActionWorkflow;
import au.gov.qld.fire.jms.domain.refdata.ActionCode;

/**
 * ActionWorkflow DAO pattern.
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public interface ActionWorkflowDao extends BaseDao<ActionWorkflow>
{

    /**
     * Find entities by actionCode.
     * @param actionCode
     * @return
     * @throws DaoException
     */
    List<ActionWorkflow> findByActionCode(ActionCode actionCode) throws DaoException;

    /**
     * 
     * @param actionCode
     * @param actionOutcome
     * @return
     * @throws DaoException
     */
    List<ActionWorkflow> findByActionCodeOutcome(ActionCode actionCode,
        ActionOutcome actionOutcome) throws DaoException;

}