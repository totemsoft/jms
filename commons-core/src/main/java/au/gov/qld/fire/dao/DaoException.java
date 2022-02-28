package au.gov.qld.fire.dao;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class DaoException extends RuntimeException
{

    /** serialVersionUID */
    private static final long serialVersionUID = 2185943182971347004L;

    /**
     * @param message
     */
    public DaoException(String message)
    {
        super(message);
    }

    /**
     * @param cause
     */
    public DaoException(Throwable cause)
    {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     */
    public DaoException(String message, Throwable cause)
    {
        super(message, cause);
    }

}