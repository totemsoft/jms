package au.gov.qld.fire.jms.domain.refdata;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;


/**
 * Owner Types
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public enum OwnerTypeEnum
{
	NONE(null, "N/A"),
	OWNER(1L, "Owner"),
	BODY_CORPORATE(2L, "Body Corporate"),
	PROPERTY_MANAGER(3L, "Property Manager"),
	TENANT(4L, "Tenant"),
	ALTERNATE(5L, "Alternate")
	;

    private static final Logger LOG = Logger.getLogger(OwnerTypeEnum.class);

    private final Long id;

    private final String name;

    /**
     * Ctor.
     * @param value
     * @param id
     * @param name
     */
    private OwnerTypeEnum(Long id, String name)
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

    public static OwnerTypeEnum findById(Long id)
    {
        for (OwnerTypeEnum item : values()) {
            if (item.id != null && item.id.equals(id)) {
                return item;
            }
        }
        LOG.warn("Unhandled id [" + id + "]");
        return null;
    }

    /**
     * CXF JAXRS checks for static fromString(String s) method, so types with no valueOf(String) factory methods can also be dealt with (http://cxf.apache.org/docs/jax-rs-basics.html)
     * @param s
     * @return
     */
    public static OwnerTypeEnum fromString(String s)
    {
    	if (StringUtils.isNotBlank(s)) {
    		if (NumberUtils.isNumber(s)) {
    			return findById(Long.valueOf(s));
    		}
    		return valueOf(s);
    	}
        return NONE; 
    }

}