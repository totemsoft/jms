package au.gov.qld.fire.jms.domain.refdata;

import org.apache.log4j.Logger;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public enum FileTypeEnum
{

	NONE(""),
	ASE_KEY("ASE Key"),
	MAIL_REGISTER("Mail Register"),
	;

    /** Logger */
    private static final Logger LOG = Logger.getLogger(FileTypeEnum.class);

	private final String name;

	private FileTypeEnum(String name)
	{
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public String getId()
	{
		return super.toString();
	}

    /**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

    /**
     *
     * @param name
     * @return
     */
    public static FileTypeEnum findByName(String name)
    {
        for (FileTypeEnum item : values())
        {
            if (item.name.equals(name))
            {
                return item;
            }
        }
        LOG.warn("Unhandled FileTypeEnum [" + name + "]");
        return null;
    }

}