package au.gov.qld.fire.domain.security;

import org.apache.commons.lang.StringUtils;

import au.gov.qld.fire.domain.SearchCriteria;

/*
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class UserSearchCriteria extends SearchCriteria
{

	/** serialVersionUID */
	private static final long serialVersionUID = 2258577049298889589L;

	private Long userId;

	private String login;

    private String userTypes;

    private String securityGroups;

	/**
	 * @return the userId
	 */
	public Long getUserId()
	{
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Long userId)
	{
		this.userId = userId;
	}

    /**
	 * @return the login
	 */
	public String getLogin()
	{
        if (StringUtils.isBlank(login))
        {
        	login = null;
        }
		return login;
	}

	/**
	 * @param login the login to set
	 */
	public void setLogin(String userName)
	{
		this.login = userName;
	}

	/**
     * @return the userTypes
     */
    public String getUserTypes()
    {
        if (StringUtils.isBlank(userTypes))
        {
            userTypes = null;
        }
        return userTypes;
    }

    /**
     * @param userTypes the userTypes to set
     */
    public void setUserTypes(String userTypes)
    {
        this.userTypes = userTypes;
    }

    /**
     * @return the securityGroups
     */
    public String getSecurityGroups()
    {
        if (StringUtils.isBlank(securityGroups))
        {
            securityGroups = null;
        }
        return securityGroups;
    }

    /**
     * @param securityGroups the securityGroups to set
     */
    public void setSecurityGroups(String securityGroups)
    {
        this.securityGroups = securityGroups;
    }

}