package au.gov.qld.fire.domain;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Create convenient alias for Pair<Long, String>, see labelValue.jsp
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class LabelValue extends Pair<Long, String> {

	/** serialVersionUID */
	private static final long serialVersionUID = 790138233081468720L;

    public LabelValue(Long key, String value) {
        super(key, value);
    }

    public LabelValue(Long key) {
        super(key, key == null ? null : "" + key);
    }

    public LabelValue() {
        super();
    }

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.domain.Pair#getKey()
	 */
	@JsonProperty("value")
	public Long getKey()
	{
		return super.getKey();
	}

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.domain.Pair#getValue()
	 */
	@JsonProperty("label")
	public String getValue()
	{
		return super.getValue();
	}

}