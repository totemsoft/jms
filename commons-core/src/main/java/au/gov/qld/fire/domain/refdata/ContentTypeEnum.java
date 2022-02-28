package au.gov.qld.fire.domain.refdata;

import org.apache.log4j.Logger;

/**
 * Global MIME Types.
 * http://www.sfsu.edu/training/mimetype.htm
 * http://www.w3schools.com/media/media_mimeref.asp
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public enum ContentTypeEnum
{

    //UNKNOWN("unknown", "unknown"),

    /** application/msword */
    APPLICATION_MSWORD("application/msword", "doc"),
    /** application/rtf */
    APPLICATION_RTF("application/rtf", "rtf"),
    /** application/octet-stream */
    APPLICATION_OCTET_STREAM("application/octet-stream", "rtf"),
    /** text/richtext */
    TEXT_RICHTEXT("text/richtext", "rtf"),
    /** text/rtf */
    TEXT_RTF("text/rtf", "rtf"),

    /** application/xml */
    APPLICATION_XML("application/xml", "xml"),
    /** text/xml */
    TEXT_XML("text/xml", "xml"),

    /** application/html */
    APPLICATION_HTML("application/html", "html"),
    /** text/html */
    TEXT_HTML("text/html", "html"),

    /** application/txt */
    APPLICATION_TXT("application/txt", "txt"),
    /** text/plain */
    //TEXT_PLAIN("text/plain", "txt"),

    /** application/pdf */
    APPLICATION_PDF("application/pdf", "pdf"),

    /** text/csv */
    TEXT_CSV("text/csv", "csv"),
    /** application/csv */
    APPLICATION_CSV("application/csv", "csv"),
    /** application/msexcel */
    APPLICATION_MSEXCEL("application/vnd.ms-excel", "xls"),

    /** application/zip */
    APPLICATION_ZIP("application/zip", "zip"),
    APPLICATION_XZIP("application/x-zip", "zip"),

    PROPERTIES("application/txt", "properties"), // application/octet-stream
    ;

    /** Logger */
    private static final Logger LOG = Logger.getLogger(ContentTypeEnum.class);

    /** contentType. */
    private String contentType;

    /** defaultExt. */
    private String defaultExt;

    /**
     * Ctor.
     * @param contentType
     */
    private ContentTypeEnum(String contentType, String defaultExt)
    {
        this.contentType = contentType;
        this.defaultExt = defaultExt;
    }

    /**
     * @return the value
     */
    public String getValue()
    {
        return name();
    }

    /**
     * @return contentType
     */
    public String getContentType()
    {
        return contentType;
    }

    /**
     * @return the defaultExt
     */
    public String getDefaultExt()
    {
        return defaultExt;
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
        return super.toString() + " [" + contentType + "]";
    }

    /**
     *
     * @param contentType
     * @return
     */
    public static ContentTypeEnum findByContentType(String contentType)
    {
        for (ContentTypeEnum item : values()) {
            if (item.contentType.equals(contentType)) {
                return item;
            }
        }
        //LOG.warn("Unhandled contentType [" + contentType + "]");
        return null;
    }

    public static ContentTypeEnum findByExt(String ext)
    {
        for (ContentTypeEnum item : values()) {
            if (item.defaultExt.equals(ext)) {
                return item;
            }
        }
        //LOG.warn("Unhandled extension [" + ext + "]");
        return APPLICATION_TXT;
    }

	public boolean isPdf()
	{
		return APPLICATION_PDF.getContentType().equalsIgnoreCase(contentType);
	}

	public static boolean isHtml(String contentType)
    {
		return APPLICATION_HTML.getContentType().equalsIgnoreCase(contentType) ||
		    TEXT_HTML.getContentType().equalsIgnoreCase(contentType);
    }
    public static boolean isHtml(ContentTypeEnum contentType)
    {
		return APPLICATION_HTML.equals(contentType);
    }

	public static boolean isBinary(String contentType)
    {
		return APPLICATION_OCTET_STREAM.getContentType().equalsIgnoreCase(contentType);
    }
    public static boolean isBinary(ContentTypeEnum contentType)
    {
		return APPLICATION_OCTET_STREAM.equals(contentType);
    }

    public static boolean isExcel(String contentType)
    {
		return APPLICATION_MSEXCEL.getContentType().equalsIgnoreCase(contentType) ||
		    APPLICATION_OCTET_STREAM.getContentType().equalsIgnoreCase(contentType);
    }
    public static boolean isExcel(ContentTypeEnum contentType)
    {
		return APPLICATION_MSEXCEL.equals(contentType) ||
		    APPLICATION_OCTET_STREAM.equals(contentType);
    }

    public static boolean isCsv(String contentType)
    {
		return APPLICATION_CSV.getContentType().equalsIgnoreCase(contentType) ||
		    TEXT_CSV.getContentType().equalsIgnoreCase(contentType);
    }
    public static boolean isCsv(ContentTypeEnum contentType)
    {
		return APPLICATION_CSV.equals(contentType) ||
		    TEXT_CSV.equals(contentType);
    }

    public static boolean isXml(String contentType)
    {
		return APPLICATION_XML.getContentType().equalsIgnoreCase(contentType) ||
			TEXT_XML.getContentType().equalsIgnoreCase(contentType);
    }
    public static boolean isXml(ContentTypeEnum contentType)
    {
		return APPLICATION_XML.equals(contentType) ||
		    TEXT_XML.equals(contentType);
    }

    public static boolean isZip(String contentType)
    {
		return APPLICATION_ZIP.getContentType().equalsIgnoreCase(contentType);
    }
    public static boolean isZip(ContentTypeEnum contentType)
    {
		return APPLICATION_ZIP.equals(contentType);
    }

}