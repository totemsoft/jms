package au.gov.qld.fire.jms.service.impl;

import javax.inject.Inject;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import au.gov.qld.fire.jms.dao.ActionWorkflowDao;
import au.gov.qld.fire.jms.domain.action.ActionOutcome;
import au.gov.qld.fire.jms.domain.action.ActionWorkflow;
import au.gov.qld.fire.jms.domain.refdata.ActionCode;
import au.gov.qld.fire.jms.service.EntityServiceRS;
import au.gov.qld.fire.service.ServiceException;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class EntityServiceRSImpl implements EntityServiceRS
{

    /** logger. */
    private static final Logger LOG = Logger.getLogger(EntityServiceRSImpl.class);

    @Inject private ActionWorkflowDao actionWorkflowDao;

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.jms.service.EntityServiceRS#active(java.lang.Long, java.lang.Long, java.lang.Long, boolean)
	 */
	public Long active(Long actionWorkflowId, Long actionCodeId, Long outcomeId, boolean value)
	    throws ServiceException
	{
        try {
        	ActionWorkflow actionWorkflow = actionWorkflowDao.findById(actionWorkflowId);
        	if (actionWorkflow == null) {
        		return 0L;
        	}
        	actionWorkflow.setActive(value);
        	actionWorkflowDao.saveOrUpdate(actionWorkflow);
            return actionWorkflow.getActionWorkflowId();
        }
        catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new ServiceException(ExceptionUtils.getRootCauseMessage(e));
        }
	}

}