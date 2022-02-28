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
@Table(name = "INVOICE_AREA")
public class InvoiceArea extends Auditable<Long>
{

	/** serialVersionUID */
	private static final long serialVersionUID = 1199802173481484663L;

	private String name;

	private String description;

	public InvoiceArea()
	{
		super();
	}

	public InvoiceArea(Long id)
	{
		super(id);
	}

	@Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "INVOICE_AREA_ID")
    public Long getInvoiceAreaId()
    {
        return super.getId();
    }

    public void setInvoiceAreaId(Long invoiceAreaId)
    {
        super.setId(invoiceAreaId);
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

    @Column(name = "DESCRIPTION", nullable = false)
    public String getDescription()
    {
        return this.description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

}