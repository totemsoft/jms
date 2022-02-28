package au.gov.qld.fire.jms.domain.mail;

import java.util.Date;

import au.gov.qld.fire.domain.SearchCriteria;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class MailRegisterSearchCriteria extends SearchCriteria
{

	/** serialVersionUID */
	private static final long serialVersionUID = -5029153819995077516L;

	private Date date;

	private String fcaNo;

	private String sapCustNo;

	private Boolean mailIn;

	private Boolean rts;

    private Long mailTypeId;

    private String mailRegisterNo;

    private String contactName;

    public MailRegisterSearchCriteria()
	{
        setMaxResults(DEFAULT_MAX);
	}

	/**
	 * @return the date
	 */
	public Date getDate()
	{
		return date;
	}

	public void setDate(Date date)
	{
		this.date = date;
	}

	/**
	 * @return the fcaNo
	 */
	public String getFcaNo()
	{
		return fcaNo;
	}

	public void setFcaNo(String fcaNo)
	{
		this.fcaNo = fcaNo;
	}

	/**
	 * @return the sapCustNo
	 */
	public String getSapCustNo()
	{
		return sapCustNo;
	}

	public void setSapCustNo(String sapCustNo)
	{
		this.sapCustNo = sapCustNo;
	}

	/**
	 * @return the mailIn
	 */
	public Boolean getMailIn()
	{
		return mailIn;
	}

	public void setMailIn(Boolean mailIn)
	{
		this.mailIn = mailIn;
	}

	/**
	 * @return the rts
	 */
	public Boolean getRts()
	{
		if (!Boolean.TRUE.equals(mailIn))
		{
			rts = null; // rts is defined for Incoming Mail ONLY 
		}
		return rts;
	}

	public void setRts(Boolean rts)
	{
		this.rts = rts;
	}

	/**
	 * @return the mailTypeId
	 */
	public Long getMailTypeId()
	{
		return mailTypeId;
	}

	public void setMailTypeId(Long mailTypeId)
	{
		this.mailTypeId = mailTypeId;
	}

	/**
	 * @return the mailRegisterNo
	 */
	public String getMailRegisterNo()
	{
		return mailRegisterNo;
	}

	public void setMailRegisterNo(String mailRegisterNo)
	{
		this.mailRegisterNo = mailRegisterNo;
	}

	/**
	 * @return the contactName
	 */
	public String getContactName()
	{
		return contactName;
	}

	public void setContactName(String contactName)
	{
		this.contactName = contactName;
	}

}