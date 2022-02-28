package au.gov.qld.fire.domain;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public abstract class AbstractRequest<T> extends Request
{

	/** ids */
    @JsonProperty
    private T[] ids;

	public T[] getIds() {
		return ids;
	}

	public void setIds(T[] ids) {
		this.ids = ids;
	}

}