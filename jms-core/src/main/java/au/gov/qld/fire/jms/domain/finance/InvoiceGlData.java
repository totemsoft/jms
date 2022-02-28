package au.gov.qld.fire.jms.domain.finance;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import au.gov.qld.fire.domain.Auditable;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Entity
@Table(name = "INVOICE_GL_DATA")
public class InvoiceGlData extends Auditable<Long>
{

	/** serialVersionUID */
	private static final long serialVersionUID = 9130889299581343970L;

	private Invoice invoice;

	private String transactionType = "AR";

	private String rowType = "ITM";

	private int sequence;

    private Long glAccount;

    private Long costCenter;

	private String taxCode = "SZ";

	private BigDecimal amount;

	private String debitCredit = "DR";

	private String text = "CHARGE FOR UNWANTED ALARM ACTIVATION";

	private String assignment;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "INVOICE_GL_DATA_ID")
    public Long getInvoiceGlDataId()
    {
        return super.getId();
    }

    public void setInvoiceGlDataId(Long invoiceGlDataId)
    {
        super.setId(invoiceGlDataId);
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

	@Column(name = "GL_ACCOUNT", nullable = false)
	public Long getGlAccount()
	{
		return glAccount;
	}

	public void setGlAccount(Long glAccount)
	{
		this.glAccount = glAccount;
	}

	@Column(name = "COST_CENTER", nullable = false)
	public Long getCostCenter()
	{
		return costCenter;
	}

	public void setCostCenter(Long costCenter)
	{
		this.costCenter = costCenter;
	}

	@Column(name = "TAX_CODE", nullable = false)
	public String getTaxCode()
	{
		return taxCode;
	}

	public void setTaxCode(String taxCode)
	{
		this.taxCode = taxCode;
	}

	@Column(name = "AMOUNT", nullable = false)
	public BigDecimal getAmount()
	{
		return amount;
	}

	public void setAmount(BigDecimal amount)
	{
		this.amount = amount;
	}

	@Column(name = "DR_CR", nullable = false)
	public String getDebitCredit()
	{
		return debitCredit;
	}

	public void setDebitCredit(String debitCredit)
	{
		this.debitCredit = debitCredit;
	}

	@Column(name = "ITEM_TEXT", nullable = false)
	public String getText()
	{
		return text;
	}

	public void setText(String text)
	{
		this.text = text;
	}

	@Column(name = "ASSIGNMENT", nullable = true)
	public String getAssignment()
	{
		return assignment;
	}

	public void setAssignment(String assignment)
	{
		this.assignment = assignment;
	}

}