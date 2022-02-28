package au.gov.qld.fire.domain.task;

import au.gov.qld.fire.domain.SearchCriteria;
import au.gov.qld.fire.domain.refdata.TaskStatusEnum;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class ScheduledTaskHistorySearchCriteria extends SearchCriteria
{

	/** serialVersionUID */
	private static final long serialVersionUID = -1800229784925278744L;

	private ScheduledTaskEnum task;

    private TaskStatusEnum status;

    public ScheduledTaskHistorySearchCriteria()
	{
        setMaxResults(DEFAULT_MAX);
	}

	/**
	 * @return the task
	 */
	public ScheduledTaskEnum getTask()
	{
		return task;
	}

	public void setTask(ScheduledTaskEnum task)
	{
		this.task = task;
	}

	/**
	 * @return the status
	 */
	public TaskStatusEnum getStatus()
	{
		return status;
	}

	public void setStatus(TaskStatusEnum status)
	{
		this.status = status;
	}

}