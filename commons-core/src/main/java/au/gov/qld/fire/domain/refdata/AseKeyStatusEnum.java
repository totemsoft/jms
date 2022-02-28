package au.gov.qld.fire.domain.refdata;

import org.apache.log4j.Logger;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public enum AseKeyStatusEnum
{

	NONE(""),
	ASSIGNED("Assigned"),
	CANCELLED("Cancelled"),
	FAULT("Fault"),
	LOST("Lost"),
	REASSIGNED("Reassigned"),
	;

    /** Logger */
    private static final Logger LOG = Logger.getLogger(AseKeyStatusEnum.class);

	private final String name;

	private AseKeyStatusEnum(String name)
	{
		this.name = name;
	}

	/**
	 * @return the id
	 */
	public int getId()
	{
		return ordinal();
	}

    /**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @return the name
	 */
	public String getCode()
	{
		return name();
	}

    /**
     *
     * @param name
     * @return
     */
    public static AseKeyStatusEnum findByName(String name)
    {
        for (AseKeyStatusEnum item : values())
        {
            if (item.name.equals(name))
            {
                return item;
            }
        }
        LOG.warn("Unhandled AseKeyStatus [" + name + "]");
        return null;
    }

}