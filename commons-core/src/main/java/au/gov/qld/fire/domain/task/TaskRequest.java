package au.gov.qld.fire.domain.task;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Component;

import au.gov.qld.fire.converters.TextHttpInputMessage;
import au.gov.qld.fire.converters.TextHttpOutputMessage;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Component
public class TaskRequest
{

    @JsonIgnore
    private static HttpMessageConverter<TaskRequest> messageConverter;

    public void setMessageConverter(HttpMessageConverter<TaskRequest> messageConverter)
    {
    	TaskRequest.messageConverter = messageConverter;
    }

	@JsonProperty
	private String cronExpression; // @see CronSequenceGenerator

    /**
	 * @return the cronExpression
	 */
	public String getCronExpression()
	{
		return cronExpression;
	}

	public void setCronExpression(String cronExpression)
	{
		this.cronExpression = cronExpression;
	}

	public byte[] toByteArray() throws IOException
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        messageConverter.write(this, MediaType.APPLICATION_JSON, new TextHttpOutputMessage(baos));
        return baos.toByteArray();
    }

    public static TaskRequest fromByteArray(byte[] data) throws IOException
    {
        return messageConverter.read(TaskRequest.class, new TextHttpInputMessage(data));
    }

}