package au.gov.qld.fire.jms.web.module.setup;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionMapping;

import au.gov.qld.fire.jms.domain.action.ActionWorkflow;
import au.gov.qld.fire.jms.web.module.AbstractValidatorForm;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class ActionWorkflowForm extends AbstractValidatorForm<ActionWorkflow>
{

    /** serialVersionUID */
    private static final long serialVersionUID = -444140084726677451L;

    private Long jobTypeId;
    private String jobType;

    private Long actionCodeId;
    private String actionCode;

    private Long outcomeId;

    private boolean active = true;

    /** backend entities */
    private List<ActionWorkflow> actionWorkflows;

    /** backend entities */
    private List<ActionWorkflow> actionWorkflowsDelete;

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.web.module.AbstractValidatorForm#reset(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
     */
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request)
    {
        //do nothing (this form is refreshed via wizard like style: add/remove action)
        //super.reset(mapping, request);
    }

	/**
	 * @return the jobTypeId
	 */
	public Long getJobTypeId()
	{
		if ((jobTypeId != null && jobTypeId == 0L) || StringUtils.isEmpty(jobType)) {
			jobTypeId = null;
		}
		return jobTypeId;
	}

	public void setJobTypeId(Long jobTypeId)
	{
		this.jobTypeId = jobTypeId;
	}

	/**
	 * @return the jobType
	 */
	public String getJobType()
	{
		return jobType;
	}

	public void setJobType(String jobType)
	{
		this.jobType = jobType;
	}

	/**
	 * @return the actionCodeId
	 */
	public Long getActionCodeId()
	{
		if ((actionCodeId != null && actionCodeId == 0L) || StringUtils.isEmpty(actionCode)) {
			actionCodeId = null;
		}
		return actionCodeId;
	}

	public void setActionCodeId(Long actionCodeId)
	{
		this.actionCodeId = actionCodeId;
	}

	/**
	 * @return the actionCode
	 */
	public String getActionCode()
	{
		return actionCode;
	}

	public void setActionCode(String actionCode)
	{
		this.actionCode = actionCode;
	}

	/**
	 * @return the outcomeId
	 */
	public Long getOutcomeId()
	{
		return outcomeId;
	}

	public void setOutcomeId(Long outcomeId)
	{
		this.outcomeId = outcomeId;
	}

	/**
	 * @return the active
	 */
	public boolean isActive()
	{
		return active;
	}

	public void setActive(boolean active)
	{
		this.active = active;
	}

	/**
     * @return the actionWorkflows
     */
    public List<ActionWorkflow> getActionWorkflows()
    {
        if (actionWorkflows == null)
        {
            actionWorkflows = new ArrayList<ActionWorkflow>();
        }
        return actionWorkflows;
    }

    /**
     * @param actionWorkflows the actionWorkflows to set
     */
    public void setActionWorkflows(List<ActionWorkflow> actionWorkflows)
    {
        this.actionWorkflows = actionWorkflows;
    }

    /**
     * @return the actionWorkflowsDelete
     */
    public List<ActionWorkflow> getActionWorkflowsDelete()
    {
        if (actionWorkflowsDelete == null)
        {
            actionWorkflowsDelete = new ArrayList<ActionWorkflow>();
        }
        return actionWorkflowsDelete;
    }

    /**
     * 
     * @param index
     * @return
     */
    public ActionWorkflow getActionWorkflow(int index)
    {
        while (getActionWorkflows().size() <= index)
        {
            ActionWorkflow actionWorkflow = new ActionWorkflow();
            actionWorkflow.setActionCode(getEntity().getActionCode());
            actionWorkflow.setActionOutcome(getEntity().getActionOutcome());
            getActionWorkflows().add(actionWorkflow);
        }
        return getActionWorkflows().get(index);
    }

    /**
     * 
     * @param actionWorkflow
     */
    public void add(ActionWorkflow actionWorkflow)
    {
        actionWorkflow.setActionCode(getEntity().getActionCode());
        actionWorkflow.setActionOutcome(getEntity().getActionOutcome());
        getActionWorkflows().add(actionWorkflow);
    }

    /**
     * 
     * @param actionWorkflow
     */
    public void remove(ActionWorkflow actionWorkflow)
    {
        getActionWorkflows().remove(actionWorkflow);
        //saved entity - has to be deleted
        if (actionWorkflow.getActionWorkflowId() != null)
        {
            getActionWorkflowsDelete().add(actionWorkflow);
        }
    }

}