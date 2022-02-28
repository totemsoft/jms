package au.gov.qld.fire.domain.refdata;

import org.apache.log4j.Logger;


/**
 * Company Types.
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public enum CompanyTypeEnum
{
    NONE(null, "None"),
    SECURITY(1L, "Security Company"),
    FIRE(2L, "Fire Maintenence Company"),
    ;

    /** Logger */
    private static final Logger LOG = Logger.getLogger(CompanyTypeEnum.class);

    /** id. */
    private Long id;

    /** name. */
    private String name;

    /**
     * Ctor.
     * @param id
     * @param name
     */
    private CompanyTypeEnum(Long id, String name)
    {
        this.id = id;
        this.name = name;
    }

    /**
     * @return id
     */
    public Long getId()
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

    /**
     *
     * @param buildingContactTypeId
     * @return
     */
    public static CompanyTypeEnum findByBuildingContactTypeId(Long companyTypeId)
    {
        for (CompanyTypeEnum item : values())
        {
            if (item.id.equals(companyTypeId))
            {
                return item;
            }
        }
        LOG.warn("Unhandled companyTypeId [" + companyTypeId + "]");
        return null;
    }

}
