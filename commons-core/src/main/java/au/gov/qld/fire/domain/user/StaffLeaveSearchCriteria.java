package au.gov.qld.fire.domain.user;

import java.util.Date;

import au.gov.qld.fire.domain.SearchCriteria;
import au.gov.qld.fire.domain.security.User;
import au.gov.qld.fire.domain.user.StaffLeave.StatusEnum;

/*
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class StaffLeaveSearchCriteria extends SearchCriteria {

	/** serialVersionUID */
	private static final long serialVersionUID = 2720763961947393969L;

	private User user;

	private Date date;

	private StatusEnum status;


	/**
	 * @return the user
	 */
	public User getUser()
	{
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user)
	{
		this.user = user;
	}

	/**
	 * @return the date
	 */
	public Date getDate()
	{
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date)
	{
		this.date = date;
	}

	/**
	 * @return the status
	 */
	public StatusEnum getStatus()
	{
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(StatusEnum status)
	{
		this.status = status;
	}

}