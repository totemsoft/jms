package au.gov.qld.fire.jms.domain.mail;

import org.apache.log4j.Logger;

/**
 * Mail Status.
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public enum MailStatusEnum
{
    NONE(null, "None"),
    SENT(1, "Sent"),
    RECEIVED(2, "Received"),
    RTS(3, "RTS"),
    ;

    /** Logger */
    private static final Logger LOG = Logger.getLogger(MailStatusEnum.class);

    private Integer id;

    private String name;

    /**
     * Ctor.
     * @param id
     * @param name
     */
    private MailStatusEnum(Integer id, String name)
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

    public static MailStatusEnum findByName(String name)
    {
        for (MailStatusEnum item : values()) {
            if (item.name.equals(name)) {
                return item;
            }
        }
        LOG.warn("Unhandled name [" + name + "]");
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