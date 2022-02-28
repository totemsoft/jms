package au.gov.qld.fire.domain;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public abstract class AbstractResponse
{
    @JsonProperty
	private String message;

	protected AbstractResponse()
	{
		super();
	}

	protected AbstractResponse(String message)
	{
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}