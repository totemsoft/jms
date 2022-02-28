package au.gov.qld.fire.jms.domain.refdata;

import org.apache.log4j.Logger;


/**
 * File Status(s).
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public enum FileStatusEnum
{

    CONNECTED(1L, "Connected"),
    DISCONNECTED(2L, "Disconnected"),
    ARCHIVED(3L, "Archived"),
    PENDING(4L, "Pending"),
    ;

    /** Logger */
    private static final Logger LOG = Logger.getLogger(FileStatusEnum.class);

    private Long id;

    private String name;

    /**
     * Ctor.
     * @param id
     * @param name
     */
    private FileStatusEnum(Long id, String name)
    {
        this.id = id;
        this.name = name;
    }

    /**
     * @return the value
     */
    public String getValue()
    {
        return name();
    }

    /**
     * @return id
     */
    public Long getId()
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

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString()
    {
        return super.toString() + " [" + name + "]";
    }

    /**
     *
     * @param fileStatusId
     * @return
     */
    public static FileStatusEnum findByFileStatusId(Long fileStatusId)
    {
        for (FileStatusEnum item : values())
        {
            if (item.id.equals(fileStatusId))
            {
                return item;
            }
        }
        LOG.warn("Unhandled fileStatusId [" + fileStatusId + "]");
        return null;
    }

}
