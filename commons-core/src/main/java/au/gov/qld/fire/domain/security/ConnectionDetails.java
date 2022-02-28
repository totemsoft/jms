package au.gov.qld.fire.domain.security;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/**
 * Connection Details.
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class ConnectionDetails
{

    private String host;

    private String port;

    private String username;

    private String password;

    /**
     * @return the host
     */
    public String getHost()
    {
        return host;
    }

    public void setHost(String host)
    {
        this.host = host;
    }

    /**
     * @return the port
     */
    public String getPort()
    {
        return port;
    }

    public void setPort(String port)
    {
        this.port = port;
    }

    /**
     * @return the username
     */
    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object other)
    {
        if (other != null) {
            ConnectionDetails castOther = (ConnectionDetails) other;
            return new EqualsBuilder()
                .append(this.host, castOther.host)
                .append(this.port, castOther.port)
                .append(this.username, castOther.username)
                .append(this.password, castOther.password)
                .isEquals();
        }
        return false;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    public int hashCode()
    {
        return new HashCodeBuilder()
            .append(host)
            .append(port)
            .append(username)
            .append(password)
            .toHashCode();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        return new ToStringBuilder(this)
            .append("host", host)
            .append("port", port)
            .append("username", username)
            //.append("password", password)
            .toString();
    }

}