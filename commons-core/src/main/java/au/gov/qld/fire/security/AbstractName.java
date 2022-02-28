package au.gov.qld.fire.security;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
abstract class AbstractName
{

    /** Principal name */
    private String name;

    /**
     * 
     * @param name
     */
    protected AbstractName(String name)
    {
        this.name = name;
    }

    /* (non-Javadoc)
     * @see java.security.Principal#getName()
     */
    public String getName()
    {
        return name;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object other)
    {
        if (other != null && other.getClass().equals(getClass()))
        {
            AbstractName castOther = (AbstractName) other;
            return new EqualsBuilder().append(this.name, castOther.name).isEquals();
        }
        return false;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        return new HashCodeBuilder().append(name).toHashCode();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return new ToStringBuilder(this).append("name", name).toString();
    }

}