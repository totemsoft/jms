package au.gov.qld.fire.jms.domain.finance;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;

import au.gov.qld.fire.domain.Auditable;
import au.gov.qld.fire.domain.location.Region;
import au.gov.qld.fire.jms.domain.Fca;
import au.gov.qld.fire.jms.domain.refdata.Brigade;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Entity
@Table(name = "INVOICE")
public class Invoice extends Auditable<Long>
{

	/** serialVersionUID */
	private static final long serialVersionUID = -5728189746839694143L;

	private InvoiceType invoiceType;

	private InvoiceArea invoiceArea;

	private InvoiceBatch invoiceBatch;

	private Integer fiscalYear;

	private Region region;

	private Brigade brigade;

	private String receiptBatch;

	private Date dateReceived;

	private Date dateActioned;

	private String jobReference;

	private Date incidentDate;

    private Long sapCustNo;

	private boolean sapCustCreate;

	private boolean sapCustChange;

	private boolean sapCustUnblock;

	private InvoiceHeader header;

	private InvoiceData data;

	private List<InvoiceGlData> invoiceGlData;

	private InvoiceText text;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "INVOICE_ID")
    public Long getInvoiceId()
    {
        return super.getId();
    }

    public void setInvoiceId(Long invoiceId)
    {
        super.setId(invoiceId);
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INVOICE_BATCH_ID", nullable = true)
	public InvoiceBatch getInvoiceBatch()
	{
		return invoiceBatch;
	}

	public void setInvoiceBatch(InvoiceBatch invoiceBatch)
	{
		this.invoiceBatch = invoiceBatch;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REGION_ID", nullable = true)
	public Region getRegion()
	{
		return region;
	}

	public void setRegion(Region region)
	{
		this.region = region;
	}

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BRIGADE_ID", nullable = true)
	public Brigade getBrigade()
	{
		return brigade;
	}

	public void setBrigade(Brigade brigade)
	{
		this.brigade = brigade;
	}

    @Column(name = "RECEIPT_BATCH", nullable = true)
	public String getReceiptBatch()
	{
		return receiptBatch;
	}

	public void setReceiptBatch(String receiptBatch)
	{
		this.receiptBatch = receiptBatch;
	}

    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_RECEIVED", nullable = false)
	public Date getDateReceived()
	{
		return dateReceived;
	}

	public void setDateReceived(Date dateReceived)
	{
		this.dateReceived = dateReceived;
	}

    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_ACTIONED", nullable = true)
	public Date getDateActioned()
	{
		return dateActioned;
	}

	public void setDateActioned(Date dateActioned)
	{
		this.dateActioned = dateActioned;
	}

    @Column(name = "JOB_REFERENCE", nullable = true)
	public String getJobReference()
	{
		return jobReference;
	}

	public void setJobReference(String jobReference)
	{
		this.jobReference = jobReference;
	}

    @Temporal(TemporalType.DATE)
    @Column(name = "INCIDENT_DATE", nullable = true)
	public Date getIncidentDate()
	{
		return incidentDate;
	}

	public void setIncidentDate(Date incidentDate)
	{
		this.incidentDate = incidentDate;
	}

    @Column(name = "SAP_CUST_NO", nullable = true)
	public Long getSapCustNo()
	{
		return sapCustNo;
	}

	public void setSapCustNo(Long sapCustNo)
	{
		this.sapCustNo = sapCustNo;
	}

    @Column(name = "SAP_CUST_CREATE", nullable = false)
	public boolean isSapCustCreate()
	{
		return sapCustCreate;
	}

	public void setSapCustCreate(boolean sapCustCreate)
	{
		this.sapCustCreate = sapCustCreate;
	}

    @Column(name = "SAP_CUST_CHANGE", nullable = false)
	public boolean isSapCustChange()
	{
		return sapCustChange;
	}

	public void setSapCustChange(boolean sapCustChange)
	{
		this.sapCustChange = sapCustChange;
	}

    @Column(name = "SAP_CUST_UNBLOCK", nullable = false)
	public boolean isSapCustUnblock()
	{
		return sapCustUnblock;
	}

	public void setSapCustUnblock(boolean sapCustUnblock)
	{
		this.sapCustUnblock = sapCustUnblock;
	}

    @OneToOne(mappedBy = "invoice", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public InvoiceHeader getHeader()
	{
		return header;
	}

	public void setHeader(InvoiceHeader header)
	{
		if (header != null && header.getInvoice() == null) header.setInvoice(this);
		this.header = header;
	}

    @OneToOne(mappedBy = "invoice", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public InvoiceData getData()
	{
		return data;
	}

	public void setData(InvoiceData data)
	{
		if (data != null && data.getInvoice() == null) data.setInvoice(this);
		this.data = data;
	}

	@OneToMany(mappedBy = "invoice", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public List<InvoiceGlData> getInvoiceGlData()
	{
		if (invoiceGlData == null)
		{
			invoiceGlData = new ArrayList<InvoiceGlData>();
		}
		return invoiceGlData;
	}

	public void setInvoiceGlData(List<InvoiceGlData> invoiceGlData)
	{
		this.invoiceGlData = invoiceGlData;
	}

	public void add(InvoiceGlData d)
	{
		if (d.getInvoice() == null) d.setInvoice(this);
		getInvoiceGlData().add(d);
	}

	@Transient
	public InvoiceGlData getGlData(int index)
	{
        while (getInvoiceGlData().size() <= index)
        {
        	InvoiceGlData d = new InvoiceGlData();
        	add(d);
        }
        return getInvoiceGlData().get(index);
	}

	public void setGlData(int index, InvoiceGlData glData)
	{
		//
	}

	@OneToOne(mappedBy = "invoice", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public InvoiceText getText()
	{
		return text;
	}

	public void setText(InvoiceText text)
	{
		if (text != null && text.getInvoice() == null) text.setInvoice(this);
		this.text = text;
	}

	@Transient
	public static void setNullable(Invoice entity)
	{
		if (entity.getInvoiceBatch() == null)
		{
			entity.setInvoiceBatch(new InvoiceBatch());
		}
		if (entity.getInvoiceBatch().getInvoiceType() == null)
		{
			entity.getInvoiceBatch().setInvoiceType(new InvoiceType());
		}
		if (entity.getBrigade() == null)
		{
			entity.setBrigade(new Brigade());
		}
		if (entity.getData() == null)
		{
			entity.setData(new InvoiceData());
		}
		if (entity.getHeader() == null)
		{
			entity.setHeader(new InvoiceHeader());
		}
		if (entity.getRegion() == null)
		{
			entity.setRegion(new Region());
		}
		if (entity.getText() == null)
		{
			entity.setText(new InvoiceText());
		}
	}

	@Transient
	public static void unsetNullable(Invoice entity)
	{
		if (entity.getInvoiceBatch().getInvoiceType() != null && entity.getInvoiceBatch().getInvoiceType().getId() == null)
		{
			entity.getInvoiceBatch().setInvoiceType(null);
		}
		if (entity.getInvoiceBatch() != null && entity.getInvoiceBatch().getId() == null)
		{
			entity.setInvoiceBatch(null);
		}
		if (entity.getBrigade() != null && entity.getBrigade().getId() == null)
		{
			entity.setBrigade(null);
		}
		if (entity.getRegion() != null && entity.getRegion().getId() == null)
		{
			entity.setRegion(null);
		}
	}

}