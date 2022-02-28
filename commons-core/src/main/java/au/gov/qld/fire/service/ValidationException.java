package au.gov.qld.fire.service;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class ValidationException extends ServiceException
{

	/** serialVersionUID */
	private static final long serialVersionUID = 5792567421867960813L;

    /**
     * @param message
     */
    public ValidationException(String message)
    {
        super(message);
    }

    /**
     * @param cause
     */
    public ValidationException(Throwable cause)
    {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     */
    public ValidationException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
