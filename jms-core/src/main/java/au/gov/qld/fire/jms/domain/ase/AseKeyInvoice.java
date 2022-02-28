package au.gov.qld.fire.jms.domain.ase;

import java.math.BigDecimal;
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

import org.apache.commons.lang.ArrayUtils;

import au.gov.qld.fire.domain.Auditable;
import au.gov.qld.fire.domain.security.User;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Entity
@Table(name = "ASE_KEY_INVOICE")
public class AseKeyInvoice extends Auditable<Long>
{

    /** serialVersionUID */
	private static final long serialVersionUID = -5853671584578001911L;

	/**
     * USER_IGNORE
     */
    public static final String[] IGNORE = (String[]) ArrayUtils.addAll(Auditable.IGNORE,
        new String[] {"invoiceId", "authorisedBy"});

    private String invoiceNo;

    private Date invoiceDate;

    private BigDecimal invoiceAmount;

    private User authorisedBy;

    private Date authorisedDate;

    /**
     * 
     */
    public AseKeyInvoice()
    {
        super();
    }

    /**
     * @param id
     */
    public AseKeyInvoice(Long id)
    {
        super(id);
    }

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

    /** 
     *            @hibernate.property
     *             column="INVOICE_NO"
     *             length="20"
     *             not-null="false"
     *         
     */
    @Column(name = "INVOICE_NO", nullable = true)
	public String getInvoiceNo()
	{
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo)
	{
		this.invoiceNo = invoiceNo;
	}

    /** 
     *            @hibernate.property
     *             column="INVOICE_DATEv"
     *             length="23"
     *             not-null="false"
     *         
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "INVOICE_DATE", nullable = true)
	public Date getInvoiceDate()
	{
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate)
	{
		this.invoiceDate = invoiceDate;
	}

    /** 
     *            @hibernate.property
     *             column="INVOICE_AMOUNT"
     *             not-null="false"
     *         
     */
    @Column(name = "INVOICE_AMOUNT", nullable = true)
	public BigDecimal getInvoiceAmount()
	{
		return invoiceAmount;
	}

	public void setInvoiceAmount(BigDecimal invoiceAmount)
	{
		this.invoiceAmount = invoiceAmount;
	}

    /**
     *            @hibernate.many-to-one
     *             not-null="false"
     *            @hibernate.column name="AUTHORISED_BY"
     *
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AUTHORISED_BY", nullable = true)
	public User getAuthorisedBy()
	{
		return authorisedBy;
	}

	public void setAuthorisedBy(User authorisedBy)
	{
		this.authorisedBy = authorisedBy;
	}

    /** 
     *            @hibernate.property
     *             column="AUTHORISED_DATE"
     *             length="23"
     *             not-null="false"
     *         
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "AUTHORISED_DATE", nullable = true)
	public Date getAuthorisedDate()
	{
		return authorisedDate;
	}

	public void setAuthorisedDate(Date authorisedDate)
	{
		this.authorisedDate = authorisedDate;
	}

}