package au.gov.qld.fire.converters;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.beanutils.Converter;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import au.gov.qld.fire.util.DateUtils;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class DateConverter implements Converter
{

    /** Used for logging. */
    private static final Logger LOG = Logger.getLogger(DateConverter.class);

    /**
     * Date formats to check.
     */
    private static final SimpleDateFormat[] ACCEPTED_DATE_FORMATS =
    {
        DateUtils.DD_MM_YYYY_HH_mm_ss,
        DateUtils.DD_MM_YYYY_HH_mm,
        DateUtils.DD_MM_YYYY,
        DateUtils.D_M_YYYY,
        DateUtils.YYYY_MM_DD,
        DateUtils.YYYY_M_D,
    };

    /* (non-Javadoc)
     * @see org.apache.commons.beanutils.Converter#convert(java.lang.Class, java.lang.Object)
     */
    //@SuppressWarnings("unchecked")
    public Object convert(Class type, Object value)
    {
        if (LOG.isDebugEnabled()) LOG.debug("type: " + type + ", value: " + value);

        if (value == null || StringUtils.isBlank(value.toString()))
        {
            return null;
        }

        for (SimpleDateFormat formatter : ACCEPTED_DATE_FORMATS)
        {
            try
            {
                //formatter.setLenient(true);
                if (Date.class.isAssignableFrom(type))
                {
                    return formatter.parse((String) value);
                }
                if (String.class.equals(type))
                {
                    return formatter.format((Date) value);
                }
            }
            catch (Exception e)
            {
                //if (LOG.isDebugEnabled()) LOG.debug(e.getMessage());
                //throw new ConversionException(e);
            }
        }
       	LOG.warn("Unhandled class: " + type + " for value: " + value);
        return null;
    }

}