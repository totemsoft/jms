package au.gov.qld.fire.domain.refdata;

import org.apache.log4j.Logger;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public enum SentMethodEnum
{

	NONE(""),
	AUSTRALIA_POST("Australia Post"),
	PICKUP("Pickup"),
	;

    /** Logger */
    private static final Logger LOG = Logger.getLogger(SentMethodEnum.class);

    private final String name;

	private SentMethodEnum(String name)
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
    public static SentMethodEnum findByName(String name)
    {
        for (SentMethodEnum item : values())
        {
            if (item.name.equals(name))
            {
                return item;
            }
        }
        LOG.warn("Unhandled SentMethod [" + name + "]");
        return null;
    }

}