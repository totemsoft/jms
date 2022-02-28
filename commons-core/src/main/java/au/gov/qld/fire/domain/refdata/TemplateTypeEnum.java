package au.gov.qld.fire.domain.refdata;

import org.apache.log4j.Logger;


/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public enum TemplateTypeEnum
{

    LETTER(1L, "Letter"),
    EMAIL(2L, "Email"),
    FAX(3L, "Fax"),
    REPORT_FOP(4L, "Report (xslt-fo)"),
    REPORT_CRYSTAL(5L, "Report (crystal)"),
    EXPORT(6L, "Export"),
    PDF_FORM(7L, "PDF Form"),
    ;

    /** Logger */
    private static final Logger LOG = Logger.getLogger(TemplateTypeEnum.class);

    private Long id;

    private String description;

    /**
     * Ctor.
     * @param id
     * @param description
     */
    private TemplateTypeEnum(Long id, String description)
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
     * @param templateTypeId
     * @return
     */
    public static TemplateTypeEnum findByTemplateTypeId(Long templateTypeId)
    {
        for (TemplateTypeEnum item : values())
        {
            if (item.id.equals(templateTypeId))
            {
                return item;
            }
        }
        LOG.warn("Unhandled templateTypeId [" + templateTypeId + "]");
        return null;
    }

    public boolean isPdfForm()
    {
    	return PDF_FORM.equals(this);
    }

}