package au.gov.qld.fire.jms.domain.mail;

import org.apache.log4j.Logger;

/**
 * Mail Status.
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public enum MailMethodEnum
{
    NONE(null, "None"),
    EMAIL(1, "Email"),
    POST(2, "Post"),
    ;

    /** Logger */
    private static final Logger LOG = Logger.getLogger(MailMethodEnum.class);

    private Integer id;

    private String name;

    /**
     * Ctor.
     * @param id
     * @param name
     */
    private MailMethodEnum(Integer id, String name)
    {
        this.id = id;
        this.name = name;
    }

    /**
     * @return id
     */
    public Integer getId()
    {
        return id; // ordinal();
    }

    /**
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    public static MailMethodEnum findByName(String name)
    {
        for (MailMethodEnum item : values()) {
            if (item.name.equals(name)) {
                return item;
            }
        }
        LOG.warn("Unhandled name [" + name + "]");
        return null;
    }

	public static MailMethodEnum findById(Integer id)
	{
        for (MailMethodEnum item : values()) {
            if (item.id != null && item.id.equals(id)) {
                return item;
            }
        }
        LOG.warn("Unhandled id [" + id + "]");
        return null;
	}

	/* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString()
    {
        return super.toString() + " [" + name + "]";
    }

}