package au.gov.qld.fire.domain.task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import au.gov.qld.fire.domain.Auditable;
import au.gov.qld.fire.domain.refdata.TaskStatusEnum;
import au.gov.qld.fire.util.BeanUtils;

/**
 *
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Entity
@Table(name = "SCHEDULED_TASK_HISTORY")
//@Where(clause = Auditable.WHERE)
public class ScheduledTaskHistory extends Auditable<Long>
{

    /** serialVersionUID */
	private static final long serialVersionUID = 8169388133018566520L;

	/**
     * IGNORE
     */
    public static final String[] IGNORE = (String[]) ArrayUtils.addAll(Auditable.IGNORE,
        new String[] {"scheduledTaskHistoryId", "response", "scheduledTask", "status", "taskRequest"});

    private ScheduledTask scheduledTask;

    private String name;

    private String request;

    private String response;

    private TaskStatusEnum status;

    private List<ScheduledTaskHistoryItem> items;

    public ScheduledTaskHistory()
    {
    	super();
	}

    public ScheduledTaskHistory(ScheduledTask scheduledTask)
    {
    	BeanUtils.copyProperties(scheduledTask, this, ScheduledTask.IGNORE);
    	this.setScheduledTask(scheduledTask);
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SCHEDULED_TASK_HISTORY_ID", nullable = false)
	public Long getScheduledTaskHistoryId()
    {
        return super.getId();
	}

	public void setScheduledTaskHistoryId(Long scheduledTaskHistoryId)
	{
        super.setId(scheduledTaskHistoryId);
	}

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SCHEDULED_TASK_ID", nullable = false)
	public ScheduledTask getScheduledTask()
	{
		return scheduledTask;
	}

	public void setScheduledTask(ScheduledTask scheduledTask)
	{
		this.scheduledTask = scheduledTask;
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
        try
        {
        	return StringUtils.isBlank(request) ? new TaskRequest() : TaskRequest.fromByteArray(request.getBytes());
        }
        catch (IOException ignore)
        {
        	return new TaskRequest();
        }
	}

	@Column(name = "RESPONSE", nullable = true, length = 512)
	public String getResponse()
    {
		return response;
	}

	public void setResponse(String response)
	{
		this.response = response;
	}

    @Column(name = "STATUS", nullable = true, length = 1)
    @Enumerated(EnumType.STRING)
	public TaskStatusEnum getStatus()
    {
		return status;
	}

	public void setStatus(TaskStatusEnum status)
	{
		this.status = status;
	}

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "scheduledTaskHistory", cascade = CascadeType.ALL)
	public List<ScheduledTaskHistoryItem> getItems()
	{
		if (items == null)
		{
			items = new ArrayList<ScheduledTaskHistoryItem>();
		}
		return items;
	}

	public void setItems(List<ScheduledTaskHistoryItem> items)
	{
		this.items = items;
	}

	public void add(ScheduledTaskHistoryItem item)
	{
		item.setScheduledTaskHistory(this);
		getItems().add(item);
	}

}