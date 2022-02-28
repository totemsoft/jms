package au.gov.qld.fire.jms.domain.user;

import java.util.ArrayList;
import java.util.List;

import au.gov.qld.fire.domain.refdata.WorkGroup;
import au.gov.qld.fire.domain.security.SystemFunction;
import au.gov.qld.fire.jms.domain.action.ActionWorkflow;
import au.gov.qld.fire.jms.domain.refdata.JobType;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class WorkflowRegister
{

	private WorkGroup workGroup;

	private JobType jobType;

	private List<SystemFunction> systemFunctions = new ArrayList<SystemFunction>();

	private List<ActionWorkflow> actionWorkflows = new ArrayList<ActionWorkflow>();

	public WorkflowRegister(WorkGroup workGroup)
	{
		this.workGroup = workGroup;
	}

	public WorkflowRegister(JobType jobType)
	{
		this.jobType = jobType;
	}

	/**
	 * @return the workGroup
	 */
	public WorkGroup getWorkGroup()
	{
		return workGroup;
	}

	/**
	 * @return the jobType
	 */
	public JobType getJobType()
	{
		return jobType;
	}

	/**
	 * @return the systemFunctions
	 */
	public List<SystemFunction> getSystemFunctions()
	{
		return systemFunctions;
	}

	public void add(SystemFunction item)
	{
		if (!systemFunctions.contains(item)) {
			systemFunctions.add(item);
		}
	}

	/**
	 * @return the actionWorkflows
	 */
	public List<ActionWorkflow> getActionWorkflows()
	{
		return actionWorkflows;
	}

	public void add(ActionWorkflow item)
	{
		if (!actionWorkflows.contains(item)) {
			actionWorkflows.add(item);
		}
	}

}