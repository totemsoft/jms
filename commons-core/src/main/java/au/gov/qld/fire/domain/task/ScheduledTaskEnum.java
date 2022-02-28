package au.gov.qld.fire.domain.task;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public enum ScheduledTaskEnum
{

	// TODO: add reference table?
	none(null),
	isolationImporter("Isolation History"),
	uaaInvoiceImporter("UAA Invoice History"),
	uaaIncidentImporter("UAA Incident History"),
	om89FormImporter("OM89 Form"),
	;

    /** Logger */
    private static final Logger LOG = Logger.getLogger(ScheduledTaskEnum.class);

	private final String description;

	private ScheduledTaskEnum(String description)
	{
		this.description = description;
	}

	public int getId()
	{
		return ordinal();
	}

	/**
	 * for flexible task/importer name (single word)
	 * @return
	 */
	public String getName()
	{
		return name();
	}

	public String getDescription()
	{
		return description;
	}

	/* (non-Javadoc)
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString()
	{
		return getName();
	}

    /**
     *
     * @param name
     * @return
     */
    public static ScheduledTaskEnum findByName(String name)
    {
    	if (StringUtils.isBlank(name)) {
    		return null;
    	}
        for (ScheduledTaskEnum item : values()) {
            if (item.getName().equals(name)) {
                return item;
            }
        }
        LOG.warn("Unhandled ScheduledTaskEnum [" + name + "]");
        return null;
    }

}