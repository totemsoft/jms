package au.gov.qld.fire.domain.task;

import au.gov.qld.fire.domain.SearchCriteria;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class ScheduledTaskSearchCriteria extends SearchCriteria
{

	/** serialVersionUID */
	private static final long serialVersionUID = 5588862560667400747L;

	private ScheduledTaskEnum task;

    private Boolean active;

    public ScheduledTaskSearchCriteria()
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
	 * @return the active
	 */
	public Boolean getActive()
	{
		return active;
	}

	public void setActive(Boolean active)
	{
		this.active = active;
	}

}