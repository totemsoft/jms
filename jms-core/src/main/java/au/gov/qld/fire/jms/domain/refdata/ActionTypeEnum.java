package au.gov.qld.fire.jms.domain.refdata;

import org.apache.log4j.Logger;

/**
 * Action Types.
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public enum ActionTypeEnum
{

    MESSAGE("MESSAGE", 1L, "Message"),
    CALL("CALL", 2L, "Call"),
    EMAIL("EMAIL", 3L, "Email"),
    LETTER("LETTER", 4L, "Letter"),
    SMS("SMS", 5L, "SMS"),
    DIARY("DIARY", 6L, "Diary"),
    RFI("RFI", 7L, "RFI"),
    FILE_STATUS("FILE_STATUS", 8L, "File Status"),
    JOB("JOB", 9L, "Job"),
    JOB_CLOSE("JOB_CLOSE", 10L, "Job Close"),
    ;

    /** Logger */
    private static final Logger LOG = Logger.getLogger(ActionTypeEnum.class);

    /** value. */
    private String value;

    /** id. */
    private Long id;

    /** name. */
    private String name;

    /**
     * Ctor.
     * @param value
     * @param id
     * @param name
     */
    private ActionTypeEnum(String value, Long id, String name)
    {
        this.value = value;
        this.id = id;
        this.name = name;
    }

    /**
     * @return the value
     */
    public String getValue()
    {
        return value;
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

    /**
     * Calculated read only property
     * @return
     */
    public static boolean isCall(Long actionTypeId)
    {
        return CALL.getId().equals(actionTypeId);
    }

    public static boolean isEmail(Long actionTypeId)
    {
        return EMAIL.getId().equals(actionTypeId);
    }

    public static boolean isSms(Long actionTypeId)
    {
        return SMS.getId().equals(actionTypeId);
    }

    public static boolean isLetter(Long actionTypeId)
    {
        return LETTER.getId().equals(actionTypeId);
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
     * @param actionTypeId
     * @return
     */
    public static ActionTypeEnum findByActionTypeId(Long actionTypeId)
    {
        if (actionTypeId != null && actionTypeId > 0L)
        {
            for (ActionTypeEnum item : values())
            {
                if (item.id.equals(actionTypeId))
                {
                    return item;
                }
            }
            LOG.warn("Unhandled actionTypeId [" + actionTypeId + "]");
        }
        return null;
    }

}