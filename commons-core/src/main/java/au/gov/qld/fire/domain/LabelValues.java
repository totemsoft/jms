package au.gov.qld.fire.domain;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Create convenient alias for Pair<Long, String>, see labelValue.jsp
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class LabelValues
{

	@JsonProperty
	private List<LabelValue> records;

	/**
	 * @return the records
	 */
	public List<LabelValue> getRecords()
	{
		if (records == null) {
			records = new ArrayList<LabelValue>();
		}
		return records;
	}

	public void setRecords(List<LabelValue> records)
	{
		this.records = records;
	}

}