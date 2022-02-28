package au.gov.qld.fire.jms.domain.action;

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
public class ActionRequest
{

    @JsonIgnore
    private static HttpMessageConverter<ActionRequest> messageConverter;

    public void setMessageConverter(HttpMessageConverter<ActionRequest> messageConverter)
    {
    	ActionRequest.messageConverter = messageConverter;
    }

	@JsonProperty private Long batchId;

	@JsonProperty private Long actionCodeId;

	@JsonProperty private Long[] fileIds;

	/**
	 * @return the batchId
	 */
	public Long getBatchId()
	{
		return batchId;
	}

	public void setBatchId(Long batchId)
	{
		this.batchId = batchId;
	}

	/**
	 * @return actionCodeId
	 */
	public Long getActionCodeId()
	{
		return actionCodeId;
	}

	public void setActionCodeId(Long actionCodeId)
	{
		this.actionCodeId = actionCodeId;
	}

	/**
	 * @return fileIds
	 */
	public Long[] getFileIds()
	{
		return fileIds;
	}

	public void setFileIds(Long[] fileIds)
	{
		this.fileIds = fileIds;
	}

	public byte[] toByteArray() throws IOException
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        messageConverter.write(this, MediaType.APPLICATION_JSON, new TextHttpOutputMessage(baos));
        return baos.toByteArray();
    }

    public static ActionRequest fromByteArray(byte[] data) throws IOException
    {
        return messageConverter.read(ActionRequest.class, new TextHttpInputMessage(data));
    }

}