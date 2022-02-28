package au.gov.qld.fire.jms.domain.finance;

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
@Table(name = "INVOICE_TEXT")
public class InvoiceText extends Auditable<Long>
{

	/** serialVersionUID */
	private static final long serialVersionUID = 7244000893712437128L;

	private Invoice invoice;

	private String transactionType = "AR";

	private String rowType = "ILT";

	private int sequence;

	private String text;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "INVOICE_TEXT_ID")
    public Long getInvoiceTextId()
    {
        return super.getId();
    }

    public void setInvoiceTextId(Long invoiceTextId)
    {
        super.setId(invoiceTextId);
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

	@Column(name = "ITEM_TEXT", nullable = false)
	public String getText()
	{
		return text;
	}

	public void setText(String text)
	{
		this.text = text;
	}

}