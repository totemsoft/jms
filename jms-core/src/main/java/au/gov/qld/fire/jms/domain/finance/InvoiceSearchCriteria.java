package au.gov.qld.fire.jms.domain.finance;

import au.gov.qld.fire.domain.SearchCriteria;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class InvoiceSearchCriteria extends SearchCriteria
{

	/** serialVersionUID */
	private static final long serialVersionUID = 3573246031682443051L;

	private Integer fiscalYear;

	private Integer invoiceTypeId;

	private Long invoiceAreaId;

	/**
	 * @return the fiscalYear
	 */
	public Integer getFiscalYear()
	{
		return fiscalYear;
	}

	public void setFiscalYear(Integer fiscalYear)
	{
		this.fiscalYear = fiscalYear;
	}

	/**
	 * @return the invoiceTypeId
	 */
	public Integer getInvoiceTypeId()
	{
		return invoiceTypeId;
	}

	public void setInvoiceTypeId(Integer invoiceTypeId)
	{
		this.invoiceTypeId = invoiceTypeId;
	}

	/**
	 * @return the invoiceAreaId
	 */
	public Long getInvoiceAreaId()
	{
		return invoiceAreaId;
	}

	public void setInvoiceAreaId(Long invoiceAreaId)
	{
		this.invoiceAreaId = invoiceAreaId;
	}

}