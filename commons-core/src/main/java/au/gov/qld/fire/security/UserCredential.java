package au.gov.qld.fire.security;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public final class UserCredential extends AbstractName
{

    /** entity id */
    private Long userId;

    /**
     * 
     * @param name
     * @param userId
     */
    public UserCredential(String name, Long userId)
    {
        super(name);
        this.userId = userId;
    }

    /**
     * @return the userId
     */
    public Long getUserId()
    {
        return userId;
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.security.AbstractName#toString()
     */
    @Override
    public String toString()
    {
        return new ToStringBuilder(this).append("name", getName()).append("userId", userId)
            .toString();
    }

}