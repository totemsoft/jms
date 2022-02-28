package au.gov.qld.fire.service;

import org.apache.commons.lang.exception.ExceptionUtils;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class ServiceException extends RuntimeException
{
    /** serialVersionUID */
    private static final long serialVersionUID = -5565152049274977727L;

    /**
     * @param message
     */
    public ServiceException(String message)
    {
        super(message);
    }

    /**
     * @param message
     * @param cause
     */
    public ServiceException(Throwable cause)
    {
        super(ExceptionUtils.getRootCauseMessage(cause), cause);
    }

    /**
     * @param message
     * @param cause
     */
    public ServiceException(String message, Throwable cause)
    {
        super(message + ": " + ExceptionUtils.getRootCauseMessage(cause), cause);
    }

}