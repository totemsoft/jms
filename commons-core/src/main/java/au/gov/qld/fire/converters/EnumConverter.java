package au.gov.qld.fire.converters;

import org.apache.commons.beanutils.Converter;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class EnumConverter<T extends Enum<T>> implements Converter
{

    /** Used for logging. */
    private static final Logger LOG = Logger.getLogger(EnumConverter.class);

	/* (non-Javadoc)
     * @see org.apache.commons.beanutils.Converter#convert(java.lang.Class, java.lang.Object)
     */
    public Object convert(Class enumType, Object value)
    {
        if (LOG.isDebugEnabled()) LOG.debug("type: " + enumType + ", value: " + value);
        if (value == null || StringUtils.isBlank(value.toString())) {
            return null;
        }
        String source = value.toString().replace(' ', '_').toUpperCase().trim();
        return Enum.valueOf(enumType, source);
    }

}