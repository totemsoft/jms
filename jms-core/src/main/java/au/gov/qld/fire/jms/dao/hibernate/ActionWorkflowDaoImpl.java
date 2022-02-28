package au.gov.qld.fire.jms.dao.hibernate;

import java.util.List;

import org.hibernate.Query;

import au.gov.qld.fire.dao.DaoException;
import au.gov.qld.fire.dao.hibernate.BaseDaoImpl;
import au.gov.qld.fire.jms.dao.ActionWorkflowDao;
import au.gov.qld.fire.jms.domain.action.ActionOutcome;
import au.gov.qld.fire.jms.domain.action.ActionWorkflow;
import au.gov.qld.fire.jms.domain.refdata.ActionCode;

/*
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class ActionWorkflowDaoImpl extends BaseDaoImpl<ActionWorkflow> implements ActionWorkflowDao
{

    /* (non-Javadoc)
	 * @see au.gov.qld.fire.dao.hibernate.BaseDaoImpl#findAllOrderBy()
	 */
	@Override
	protected String findAllOrderBy() {
		return "ORDER BY actionOutcome.name";
	}

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.dao.ActionWorkflowDao#findByActionCode(au.gov.qld.fire.jms.domain.refdata.ActionCode)
     */
    @SuppressWarnings("unchecked")
    public List<ActionWorkflow> findByActionCode(ActionCode actionCode) throws DaoException
    {
        try
        {
        	return getEntityManager()
        	    .createNamedQuery("actionWorkflow.findByActionCode")
                .setParameter("actionCode", actionCode)
                .getResultList();
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.dao.ActionWorkflowDao#findActionWorkflowByActionCodeOutcome(au.gov.qld.fire.jms.domain.refdata.ActionCode, au.gov.qld.fire.jms.domain.action.ActionOutcome)
     */
    @SuppressWarnings("unchecked")
    public List<ActionWorkflow> findByActionCodeOutcome(ActionCode actionCode,
        ActionOutcome actionOutcome) throws DaoException
    {
        try
        {
            Query qry = getSession().getNamedQuery("actionWorkflow.findByActionCodeOutcome");
            qry.setParameter("actionCode", actionCode);
            qry.setParameter("actionOutcome", actionOutcome);
            return qry.list();
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

}