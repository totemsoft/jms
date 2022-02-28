package au.gov.qld.fire.domain.refdata;

/**
 * Audit Status.
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public enum AuditStatusEnum
{
    NONE(0, "None"),
    ACTIVE(1, "Active"),
    APPROVED(2, "Approved"),
    REJECTED(3, "Rejected"),
    ;

    /** Logger */
    //private static final Logger LOG = Logger.getLogger(AuditStatusEnum.class);

    private int id;

    private String name;

    /**
     * Ctor.
     * @param id
     * @param name
     */
    private AuditStatusEnum(int id, String name)
    {
        this.id = id;
        this.name = name;
    }

    /**
     * @return id
     */
    public int getId()
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

}