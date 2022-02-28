package au.gov.qld.fire.domain.refdata;

import org.apache.log4j.Logger;


/**
 * 
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public enum DateOptionEnum
{

    NONE(null, "N/A"),
    BEFORE(1, "Before"),
    AFTER(2, "After"),
    BETWEEN(3, "Between"),
    ;

    /** Logger */
    private static final Logger LOG = Logger.getLogger(DateOptionEnum.class);

    private Integer id;

    private String name;

    private DateOptionEnum(Integer id, String name)
    {
        this.id = id;
        this.name = name;
    }

    /**
     * @return id
     */
    public Integer getId()
    {
        return id;
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

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString()
    {
        return super.toString() + " [" + name + "]";
    }

    public static DateOptionEnum findById(Integer id)
    {
        for (DateOptionEnum item : values()) {
            if (item.id == id) {
                return item;
            }
            if (item.id == null) {
                continue;
            }
            if (item.id.equals(id)) {
                return item;
            }
        }
        //LOG.warn("Unhandled id [" + id + "]");
        return null;
    }

}