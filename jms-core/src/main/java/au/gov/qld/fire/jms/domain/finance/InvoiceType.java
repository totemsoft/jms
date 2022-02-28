package au.gov.qld.fire.jms.domain.finance;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import au.gov.qld.fire.domain.Auditable;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Entity
@Table(name = "INVOICE_TYPE")
public class InvoiceType extends Auditable<Long>
{

	/** serialVersionUID */
	private static final long serialVersionUID = 2350845631665338849L;

	/** form MYOB Sales Register */
	public static enum InvoiceTypeEnum {NONE, QUOTE, ORDER, OPEN_INVOICE, RETURN_CREDIT, CLOSED_INVOICE};

	private String name;

	public InvoiceType()
	{
		super();
	}

	public InvoiceType(Long id)
	{
		super(id);
	}

	@Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "INVOICE_TYPE_ID")
    public Long getInvoiceTypeId()
    {
        return super.getId();
    }

    public void setInvoiceTypeId(Long invoiceTypeId)
    {
        super.setId(invoiceTypeId);
    }

    @Column(name = "NAME", nullable = false)
    public String getName()
    {
        return this.name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

}