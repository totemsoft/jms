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
@Table(name = "INVOICE_DATA")
public class InvoiceData extends Auditable<Long>
{

	/** serialVersionUID */
	private static final long serialVersionUID = -8981509539932970441L;

	private Invoice invoice;

	private String transactionType = "AR";

	private String rowType = "ITM";

	private int sequence;

	private BigDecimal amount;

	private String debitCredit = "DR";

	private String text = "CHARGE FOR UNWANTED ALARM ACTIVATION";

	private String assignment;

    private Long sapCustNo;

	private String paymentTerm;

	private String dunningArea = "06";

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "INVOICE_DATA_ID")
    public Long getInvoiceDataId()
    {
        return super.getId();
    }

    public void setInvoiceDataId(Long invoiceDataId)
    {
        super.setId(invoiceDataId);
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

	@Column(name = "SAP_CUST_NO", nullable = false)
	public Long getSapCustNo()
	{
		return sapCustNo;
	}

	public void setSapCustNo(Long sapCustNo)
	{
		this.sapCustNo = sapCustNo;
	}

	@Column(name = "PAYMENT_TERM", nullable = false)
	public String getPaymentTerm()
	{
		return paymentTerm;
	}

	public void setPaymentTerm(String paymentTerm)
	{
		this.paymentTerm = paymentTerm;
	}

	@Column(name = "DUNNING_AREA", nullable = false)
	public String getDunningArea()
	{
		return dunningArea;
	}

	public void setDunningArea(String dunningArea)
	{
		this.dunningArea = dunningArea;
	}

}