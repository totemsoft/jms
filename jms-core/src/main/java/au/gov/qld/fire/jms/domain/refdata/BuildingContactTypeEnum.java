package au.gov.qld.fire.jms.domain.refdata;

import org.apache.log4j.Logger;


/**
 * BuildingContact Types.
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public enum BuildingContactTypeEnum
{

    OWNER(1L, "Owner"), // day-and-night
    @Deprecated DAYTIME(2L, "Daytime"),
    @Deprecated AFTER_HOURS(3L, "After Hours"),
    SECURITY(4L, "Security Company"),
    FIRE(5L, "Fire Maintenence Company"),
    ;

    /** Logger */
    private static final Logger LOG = Logger.getLogger(BuildingContactTypeEnum.class);

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
    private BuildingContactTypeEnum(Long id, String name)
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
     * @param buildingContactTypeId
     * @return
     */
    public static BuildingContactTypeEnum findByBuildingContactTypeId(Long buildingContactTypeId)
    {
        for (BuildingContactTypeEnum item : values())
        {
            if (item.id.equals(buildingContactTypeId))
            {
                return item;
            }
        }
        LOG.warn("Unhandled buildingContactTypeId [" + buildingContactTypeId + "]");
        return null;
    }

}
