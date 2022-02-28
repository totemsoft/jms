package au.gov.qld.fire.jms.domain.isolation;

import org.apache.commons.lang.builder.ToStringBuilder;

import au.gov.qld.fire.domain.Request;

/**
 * CS Number,ASE/FCA,Site Name,Address,City,Postcode,Isolation Time,De-Isolation
 * Time,Time Isolated (HH:MM:SS),Input,Details,Key Details (if available)
 * 
 * 01071228,10005-01,Qld Nickel Unloading Facility,Benwell RD,SOUTH
 * TOWNSVILLE,4810,17/08/2012 01:37:54 PM,17/08/2012 01:53:14
 * PM,00:15:20,1,10005-01 Front of Bldg,Key# 66000003F4A16A14 - Shannon O'Connor
 * Chubb Townsville
 * 
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class IsolationHistoryRequest extends Request
{

	// CS Number
	private String csNumber;

	// ASE/FCA
	private String fcaId;

	// Site Name
	private String siteName;

	// Address
	private String address;

	// City
	private String suburb;

	// Postcode
	private String postcode;

	// Isolation Time
	private String isolationDate;
	
	// De-Isolation Time
	private String deIsolationDate;

	// Time Isolated (HH:MM:SS)
	private String timeIsolated;

	// Input
	private String input;

	// Details
	private String details;

	// Key Details (if available)
	private String keyDetails;

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return new ToStringBuilder(this).append("fcaId", fcaId).append("input", input).append("isolationDate", isolationDate).toString();
	}

	/**
	 * @return the csNumber
	 */
	public String getCsNumber()
	{
		return csNumber;
	}

	public void setCsNumber(String csNumber)
	{
		this.csNumber = csNumber;
	}

	/**
	 * @return the fcaId
	 */
	public String getFcaId()
	{
		return fcaId;
	}

	public void setFcaId(String fcaId)
	{
		this.fcaId = fcaId;
	}

	/**
	 * @return the siteName
	 */
	public String getSiteName()
	{
		return siteName;
	}

	public void setSiteName(String siteName)
	{
		this.siteName = siteName;
	}

	/**
	 * @return the address
	 */
	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	/**
	 * @return the suburb
	 */
	public String getSuburb()
	{
		return suburb;
	}

	public void setSuburb(String suburb)
	{
		this.suburb = suburb;
	}

	/**
	 * @return the postcode
	 */
	public String getPostcode()
	{
		return postcode;
	}

	public void setPostcode(String postcode)
	{
		this.postcode = postcode;
	}

	/**
	 * @return the isolationDate
	 */
	public String getIsolationDate()
	{
		return isolationDate;
	}

	public void setIsolationDate(String isolationDate)
	{
		this.isolationDate = isolationDate;
	}

	/**
	 * @return the deIsolationDate
	 */
	public String getDeIsolationDate()
	{
		return deIsolationDate;
	}

	public void setDeIsolationDate(String deIsolationDate)
	{
		this.deIsolationDate = deIsolationDate;
	}

	/**
	 * @return the timeIsolated
	 */
	public String getTimeIsolated()
	{
		return timeIsolated;
	}

	public void setTimeIsolated(String timeIsolated)
	{
		this.timeIsolated = timeIsolated;
	}

	/**
	 * @return the input
	 */
	public String getInput()
	{
		return input;
	}

	public void setInput(String input)
	{
		this.input = input;
	}

	/**
	 * @return the details
	 */
	public String getDetails()
	{
		return details;
	}

	public void setDetails(String details)
	{
		this.details = details;
	}

	/**
	 * @return the keyDetails
	 */
	public String getKeyDetails()
	{
		return keyDetails;
	}

	public void setKeyDetails(String keyDetails)
	{
		this.keyDetails = keyDetails;
	}

}