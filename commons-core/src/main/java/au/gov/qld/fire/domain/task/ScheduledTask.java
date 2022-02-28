package au.gov.qld.fire.domain.task;

import java.io.IOException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Type;

import au.gov.qld.fire.domain.Auditable;

/**
 *
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Entity
@Table(name = "SCHEDULED_TASK")
//@Where(clause = Auditable.WHERE)
public class ScheduledTask extends Auditable<Long>
{

    /** serialVersionUID */
	private static final long serialVersionUID = 4744149443721032633L;

    /**
     * IGNORE
     */
    public static final String[] IGNORE = (String[]) ArrayUtils.addAll(Auditable.IGNORE,
        new String[] {"scheduledTaskId", "taskRequest"});

    private String name;

    private String request;

    @Transient
    private TaskRequest taskRequest;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SCHEDULED_TASK_ID", nullable = false)
	public Long getScheduledTaskId()
    {
        return super.getId();
	}

	public void setScheduledTaskId(Long scheduledTaskId)
	{
        super.setId(scheduledTaskId);
	}

    @Column(name = "TASK_NAME", nullable = false, length = 50)
	public String getName()
    {
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	@Transient
	public String getDescription()
    {
		ScheduledTaskEnum ste = ScheduledTaskEnum.findByName(name);
		return ste == null ? name : ste.getDescription();
	}

    @Column(name = "REQUEST", nullable = true, length = 512)
	public String getRequest()
    {
		return request;
	}

	public void setRequest(String request)
	{
		this.request = request;
	}

    @Transient
	public TaskRequest getTaskRequest()
    {
        if (taskRequest == null)
        {
            try
            {
               	taskRequest = StringUtils.isBlank(request) ? new TaskRequest() : TaskRequest.fromByteArray(request.getBytes());
            }
            catch (IOException ignore)
            {
            	taskRequest = new TaskRequest();
            }
        }
		return taskRequest;
	}

	public void setTaskRequest(TaskRequest taskRequest)
	{
		this.taskRequest = taskRequest;
	}

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.domain.BaseModel#isActive()
	 */
	@Override
    @Column(name = "ACTIVE", nullable = false)
    @Type(type = "yes_no")
	public boolean isActive()
	{
		return super.isActive();
	}

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.domain.BaseModel#setActive(boolean)
	 */
	@Override
	public void setActive(boolean active)
	{
		super.setActive(active);
	}

}