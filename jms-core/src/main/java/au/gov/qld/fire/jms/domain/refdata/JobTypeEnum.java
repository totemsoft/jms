package au.gov.qld.fire.jms.domain.refdata;

import org.apache.log4j.Logger;


/**
 * Job Types.
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public enum JobTypeEnum
{

    DATA(1L, "Data"),
    FAULT(2L, "Fault"),
    ALARM_CONNECTION(3L, "Alarm Connection"),
    ALARM_DISCONNECTION(4L, "Alarm Disconnection"),
    ALARM_TRANSFER(5L, "Alarm Transfer"),
    ;

    private static final Logger LOG = Logger.getLogger(JobTypeEnum.class);

    private final Long id;

    private final String name;

    /**
     * Ctor.
     * @param value
     * @param id
     * @param name
     */
    private JobTypeEnum(Long id, String name)
    {
        this.id = id;
        this.name = name;
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
     * @param jobTypeId
     * @return
     */
    public static JobTypeEnum findByJobTypeId(Long jobTypeId)
    {
        for (JobTypeEnum item : values())
        {
            if (item.id.equals(jobTypeId))
            {
                return item;
            }
        }
        LOG.warn("Unhandled jobTypeId [" + jobTypeId + "]");
        return null;
    }

}
