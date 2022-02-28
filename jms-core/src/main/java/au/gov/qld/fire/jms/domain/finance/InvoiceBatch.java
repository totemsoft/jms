package au.gov.qld.fire.jms.domain.finance;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import au.gov.qld.fire.domain.Auditable;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Entity
@Table(name = "INVOICE_BATCH")
public class InvoiceBatch extends Auditable<Long>
{

	/** serialVersionUID */
	private static final long serialVersionUID = -8416715557158708060L;

	private InvoiceType invoiceType;

	private InvoiceArea invoiceArea;

	private Integer fiscalYear;

	private Integer month;

	private Integer day;

	private int sequence;

	private List<Invoice> invoices;

	public InvoiceBatch()
	{
		super();
	}

	public InvoiceBatch(Long invoiceBatchId)
	{
		super(invoiceBatchId);
	}

	@Id
    @Column(name = "INVOICE_BATCH_ID")
    public Long getInvoiceBatchId()
    {
        return super.getId();
    }

    public void setInvoiceBatchId(Long invoiceBatchId)
    {
        super.setId(invoiceBatchId);
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INVOICE_TYPE_ID", nullable = false)
	public InvoiceType getInvoiceType()
	{
		return invoiceType;
	}

	public void setInvoiceType(InvoiceType invoiceType)
	{
		this.invoiceType = invoiceType;
	}

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INVOICE_AREA_ID", nullable = false)
	public InvoiceArea getInvoiceArea()
	{
    	if (invoiceArea == null)
    	{
    		invoiceArea = new InvoiceArea();
    	}
		return invoiceArea;
	}

	public void setInvoiceArea(InvoiceArea invoiceArea)
	{
		this.invoiceArea = invoiceArea;
	}

    @Column(name = "FISCAL_YEAR", nullable = false)
	public Integer getFiscalYear()
	{
		return fiscalYear;
	}

	public void setFiscalYear(Integer fiscalYear)
	{
		this.fiscalYear = fiscalYear;
	}

    @Column(name = "MONTH", nullable = false)
	public Integer getMonth()
	{
		return month;
	}

	public void setMonth(Integer month)
	{
		this.month = month;
	}

    @Column(name = "DAY", nullable = false)
	public Integer getDay()
	{
		return day;
	}

	public void setDay(Integer day)
	{
		this.day = day;
	}

    @Column(name = "SEQUENCE", nullable = false)
	public int getSequence()
	{
		return sequence;
	}

	public void setSequence(int sequence)
	{
		this.sequence = sequence;
	}

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "invoiceBatch")
	public List<Invoice> getInvoices()
	{
		if (invoices == null)
		{
			invoices = new ArrayList<Invoice>();
		}
		return invoices;
	}

	public void setInvoices(List<Invoice> invoices)
	{
		this.invoices = invoices;
	}

}