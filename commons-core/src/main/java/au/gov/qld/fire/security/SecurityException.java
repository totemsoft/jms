package au.gov.qld.fire.security;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class SecurityException extends Exception
{
    /** serialVersionUID */
    private static final long serialVersionUID = 8856472728431982071L;

    /**
     * @param message
     */
    public SecurityException(String message)
    {
        super(message);
    }

    /**
     * @param cause
     */
    public SecurityException(Throwable cause)
    {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     */
    public SecurityException(String message, Throwable cause)
    {
        super(message, cause);
    }

}