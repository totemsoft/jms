package au.gov.qld.fire.jms.dao;

import java.util.List;

import au.gov.qld.fire.dao.BaseDao;
import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.jms.domain.refdata.ActionCode;
import au.gov.qld.fire.jms.domain.refdata.ActionCodeSearchCriteria;
import au.gov.qld.fire.jms.domain.refdata.ActionType;
import au.gov.qld.fire.jms.domain.refdata.ActionTypeEnum;

/**
 * ActionCode DAO pattern.
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public interface ActionCodeDao extends BaseDao<ActionCode>
{

	/**
	 * 
	 * @param id
	 * @return
	 * @throws DaoException
	 */
	ActionCode findByIdInitialise(Long id) throws DaoException;

    /**
     * Find entity by code (unique).
     * @param code
     * @return
     * @throws DaoException
     */
    ActionCode findByCode(String code) throws DaoException;

    /**
     * 
     * @return
     * @throws DaoException
     */
    List<ActionCode> findAllActive() throws DaoException;

    /**
     * 
     * @param criteria
     * @return
     * @throws DaoException
     */
    List<ActionCode> findAllByCriteria(ActionCodeSearchCriteria criteria) throws DaoException;

    /**
     * 
     * @param actionType
     * @return
     * @throws DaoException
     */
    List<ActionCode> findAllByActionType(ActionType actionType) throws DaoException;

    /**
     * 
     * @param actionType
     * @return
     * @throws DaoException
     */
    List<ActionCode> findAllByActionType(ActionTypeEnum actionType) throws DaoException;
    List<ActionCode> findAllByActionTypeJobType(ActionTypeEnum actionType, Long jobTypeId) throws DaoException;
    List<ActionCode> findAllByJobType(Long jobTypeId) throws DaoException;

    /**
     * 
     * @param actionCode
     * @return
     * @throws DaoException
     */
    List<String> findActionCode(String actionCode) throws DaoException;

}