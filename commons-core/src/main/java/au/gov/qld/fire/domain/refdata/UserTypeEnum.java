package au.gov.qld.fire.domain.refdata;

import org.apache.log4j.Logger;


/**
 * User Type(s):
 * System
 * Administrator
 *
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public enum UserTypeEnum
{
    SYSTEM(0L, "System"),
    ADMINISTRATOR(1L, "Administrator"),
	;

    /** Logger */
    private static final Logger LOG = Logger.getLogger(UserTypeEnum.class);

    private Long id;

    private String name;

	private UserTypeEnum(Long id, String name) {
        this.id = id;
		this.name = name;
	}

    /**
     * @return the id
     */
    public Long getId()
    {
        return id;
    }

    /**
	 * @return description
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @return the code
	 */
	public String getCode()
	{
		return name();
	}

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString()
    {
        return super.toString() + " [id:" + id + "]" + "[name:" + name + "]";
    }

    /**
     *
     * @param id
     * @return
     */
    public static UserTypeEnum findById(Long id)
    {
        for (UserTypeEnum item : values())
        {
            if (item.id.equals(id))
            {
                return item;
            }
        }
        LOG.warn("Unknown id [" + id + "]");
        return null;
    }

}