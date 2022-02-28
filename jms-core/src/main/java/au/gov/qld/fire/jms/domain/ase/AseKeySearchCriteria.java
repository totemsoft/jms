package au.gov.qld.fire.jms.domain.ase;

import au.gov.qld.fire.domain.SearchCriteria;
import au.gov.qld.fire.domain.refdata.AseKeyStatusEnum;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class AseKeySearchCriteria extends SearchCriteria
{

    /** serialVersionUID */
	private static final long serialVersionUID = 3355170158091214517L;

    private String aseKeyNo;

    private String orderNo;

	private String supplierName;

	private String contactName;

	private AseKeyStatusEnum status;

	public AseKeySearchCriteria()
	{
        setMaxResults(DEFAULT_MAX);
	}

	/**
	 * @return the aseKeyNo
	 */
	public String getAseKeyNo()
	{
		return aseKeyNo;
	}

	public void setAseKeyNo(String aseKeyNo)
	{
		this.aseKeyNo = aseKeyNo;
	}

	/**
	 * @return the orderNo
	 */
	public String getOrderNo()
	{
		return orderNo;
	}

	public void setOrderNo(String orderNo)
	{
		this.orderNo = orderNo;
	}

	/**
	 * @return the supplierName
	 */
	public String getSupplierName()
	{
		return supplierName;
	}

	public void setSupplierName(String supplierName)
	{
		this.supplierName = supplierName;
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

	/**
	 * @return the status
	 */
	public AseKeyStatusEnum getStatus()
	{
		return status;
	}

	public void setStatus(AseKeyStatusEnum status)
	{
		this.status = status;
	}

}