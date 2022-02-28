package au.gov.qld.fire.jms.domain.finance;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import au.gov.qld.fire.domain.Auditable;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Entity
@Table(name = "INVOICE_HEADER")
public class InvoiceHeader extends Auditable<Long>
{

	/** serialVersionUID */
	private static final long serialVersionUID = -1670723440234250973L;

	private Invoice invoice;

	private String transactionType = "AR";

	private String rowType = "HDR";

	private int sequence;

	private Integer companyCode = 1121;

	private Date documentDate;

	private Date postDate;

	private String documentType = "ZD";

	private String currency = "AUD";

	private String reference;

	private String text;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "INVOICE_HEADER_ID")
    public Long getInvoiceHeaderId()
    {
        return super.getId();
    }

    public void setInvoiceHeaderId(Long invoiceHeaderId)
    {
        super.setId(invoiceHeaderId);
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INVOICE_ID", nullable = false)
	public Invoice getInvoice()
	{
		return invoice;
	}

	public void setInvoice(Invoice invoice)
	{
		this.invoice = invoice;
	}

	@Column(name = "TRANSACTION_TYPE", nullable = false)
    public String getTransactionType()
    {
		return transactionType;
	}

	public void setTransactionType(String transactionType)
	{
		this.transactionType = transactionType;
	}

	@Column(name = "ROW_TYPE", nullable = false)
	public String getRowType()
	{
		return rowType;
	}

	public void setRowType(String rowType)
	{
		this.rowType = rowType;
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

	@Column(name = "COMPANY_CODE", nullable = false)
	public Integer getCompanyCode()
	{
		return companyCode;
	}

	public void setCompanyCode(Integer companyCode)
	{
		this.companyCode = companyCode;
	}

	@Temporal(TemporalType.DATE)
    @Column(name = "DOCUMENT_DATE", nullable = true)
	public Date getDocumentDate()
	{
		return documentDate;
	}

	public void setDocumentDate(Date documentDate)
	{
		this.documentDate = documentDate;
	}

	@Temporal(TemporalType.DATE)
    @Column(name = "POST_DATE", nullable = true)
	public Date getPostDate()
	{
		return postDate;
	}

	public void setPostDate(Date postDate)
	{
		this.postDate = postDate;
	}

	@Column(name = "DOCUMENT_TYPE", nullable = false)
	public String getDocumentType()
	{
		return documentType;
	}

	public void setDocumentType(String documentType)
	{
		this.documentType = documentType;
	}

	@Column(name = "CURRENCY", nullable = false)
	public String getCurrency()
	{
		return currency;
	}

	public void setCurrency(String currency)
	{
		this.currency = currency;
	}

	@Column(name = "REFERENCE", nullable = false)
	public String getReference()
	{
		return reference;
	}

	public void setReference(String reference)
	{
		this.reference = reference;
	}

	@Column(name = "HEADER_TEXT", nullable = false)
	public String getText()
	{
		return text;
	}

	public void setText(String text)
	{
		this.text = text;
	}

}