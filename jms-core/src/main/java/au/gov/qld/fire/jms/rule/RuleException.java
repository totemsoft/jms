package au.gov.qld.fire.jms.rule;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class RuleException extends Exception
{

    /** serialVersionUID */
    private static final long serialVersionUID = 2285091642079668461L;

    /**
     * @param message
     */
    public RuleException(String message)
    {
        super(message);
    }

    /**
     * @param cause
     */
    public RuleException(Throwable cause)
    {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     */
    public RuleException(String message, Throwable cause)
    {
        super(message, cause);
    }

}