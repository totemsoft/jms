package au.gov.qld.fire.domain.refdata;

import org.apache.log4j.Logger;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public enum DocTypeEnum
{

    FORM(1L, "Form"),
    AGREE(2L, "Agree"),
    LETTER(3L, "Letter"),
    EMAIL(4L, "Email"),
    FAX(5L, "Fax"),
    REPORT_FOP(6L, "Report (xslt-fo)"),
    REPORT_CRYSTAL(7L, "Report (crystal)"), ;

    /** Logger */
    private static final Logger LOG = Logger.getLogger(DocTypeEnum.class);

    /** id. */
    private Long id;

    /** description. */
    private String description;

    /**
     * Ctor.
     * @param code
     * @param type
     */
    private DocTypeEnum(Long id, String description)
    {
        this.id = id;
        this.description = description;
    }

    /**
     * @return the code
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
     * @return the description
     */
    public String getDescription()
    {
        return description;
    }

	/**
	 * @return the code
	 */
	public String getCode()
	{
		return name();
	}

    /**
     *
     * @param docTypeId
     * @return
     */
    public static DocTypeEnum findByDocTypeId(Long docTypeId)
    {
        for (DocTypeEnum item : values())
        {
            if (item.id.equals(docTypeId))
            {
                return item;
            }
        }
        LOG.warn("Unhandled docTypeId [" + docTypeId + "]");
        return null;
    }

}