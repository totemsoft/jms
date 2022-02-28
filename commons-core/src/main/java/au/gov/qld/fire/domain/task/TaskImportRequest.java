package au.gov.qld.fire.domain.task;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class TaskImportRequest
{

	private ScheduledTaskEnum task;

    private String contentName;

    private byte[] content;

    private String contentType;

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
	 * @return the contentName
	 */
	public String getContentName()
	{
		return contentName;
	}

	public void setContentName(String contentName)
	{
		this.contentName = contentName;
	}

	/**
	 * @return the content
	 */
	public byte[] getContent()
	{
		return content;
	}

	public void setContent(byte[] content)
	{
		this.content = content;
	}

	/**
	 * @return the contentType
	 */
	public String getContentType()
	{
		return contentType;
	}

	public void setContentType(String contentType)
	{
		this.contentType = contentType;
	}

}