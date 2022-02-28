package au.gov.qld.fire.domain.refdata;

import org.apache.log4j.Logger;


/**
 * Supplier Types.
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public enum SupplierTypeEnum
{

    TELCO(1L, "Telco"),
    FIRE_ALARM_PANEL(2L, "Fire Alarm Panel"),
    ASE(3L, "ASE"),
    ASE_INSTALLATION(4L, "ASE Installation"),
    ;

    /** Logger */
    private static final Logger LOG = Logger.getLogger(SupplierTypeEnum.class);

    private Long id;

    private String name;

    private SupplierTypeEnum(Long id, String name)
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
        return super.toString() + " [" + name + "]";
    }

    /**
     *
     * @param supplierTypeId
     * @return
     */
    public static SupplierTypeEnum findBySupplierTypeId(Long supplierTypeId)
    {
        for (SupplierTypeEnum item : values())
        {
            if (item.id.equals(supplierTypeId))
            {
                return item;
            }
        }
        LOG.warn("Unhandled supplierTypeId [" + supplierTypeId + "]");
        return null;
    }

}
