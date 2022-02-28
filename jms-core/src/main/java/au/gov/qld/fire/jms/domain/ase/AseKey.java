package au.gov.qld.fire.jms.domain.ase;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang.ArrayUtils;

import au.gov.qld.fire.domain.Auditable;
import au.gov.qld.fire.domain.entity.Contact;
import au.gov.qld.fire.domain.refdata.AseKeyStatusEnum;
import au.gov.qld.fire.domain.refdata.PaymentMethodEnum;
import au.gov.qld.fire.domain.refdata.SentMethodEnum;
import au.gov.qld.fire.domain.supplier.Supplier;
import au.gov.qld.fire.jms.domain.file.File;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Entity
@Table(name = "ASE_KEY")
public class AseKey extends Auditable<Long>
{

    /** serialVersionUID */
    private static final long serialVersionUID = 3920188504932234826L;

    public static final String[] IGNORE = (String[]) ArrayUtils.addAll(Auditable.IGNORE,
        new String[] {"file", "invoice", "supplier", "order", "contact"});

    private File file; // PK

    private String aseKeyNo;

    private String licenseNo;

    private AseKeyOrder order;

    private Contact contact;

    private Supplier supplier;

    private SentMethodEnum sentMethod;

    private String sentReferenceNo;

    private PaymentMethodEnum paymentMethod;

    private Date sentAdtDate;

    private Date sentCustomerDate;

    private AseKeyStatusEnum status = AseKeyStatusEnum.ASSIGNED;

    private AseKeyInvoice invoice;

    /**
     * 
     */
    public AseKey()
    {
        super();
    }

    /**
     * @param id
     */
    public AseKey(Long id)
    {
        super(id);
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.domain.BaseModel#getId()
     */
    @Override
    @Transient
    public Long getId()
    {
        // we set AseKey PK to File PK
        if (super.getId() == null && file != null)
        {
            setId(file.getFileId());
        }
        return super.getId();
    }

    @Id
    @Column(name = "ASE_KEY_ID")
    public Long getAseKeyId()
    {
        return getId();
    }

    public void setAseKeyId(Long aseKeyId)
    {
        setId(aseKeyId);
    }

    /** 
     * TODO: make PK
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FILE_ID", nullable = false)
    public File getFile()
    {
        return this.file;
    }

    public void setFile(File file)
    {
        this.file = file;
    }

    @Column(name = "ASE_KEY_NO", nullable = true)
    public String getAseKeyNo()
    {
        return this.aseKeyNo;
    }

    public void setAseKeyNo(String aseKeyNo)
    {
        this.aseKeyNo = aseKeyNo;
    }

    @Column(name = "LICENSE_NO", nullable = true)
	public String getLicenseNo()
	{
		return licenseNo;
	}

	public void setLicenseNo(String licenseNo)
	{
		this.licenseNo = licenseNo;
	}

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ASE_KEY_ORDER_ID", nullable = true)
	public AseKeyOrder getOrder()
	{
		return order;
	}

	public void setOrder(AseKeyOrder order)
	{
		this.order = order;
	}

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "CONTACT_ID", nullable = true, unique = true)
	public Contact getContact()
	{
		return contact;
	}

	public void setContact(Contact contact)
	{
		this.contact = contact;
	}

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SUPPLIER_ID", nullable = true)
    public Supplier getSupplier()
    {
        return supplier;
    }

    public void setSupplier(Supplier supplier)
    {
        this.supplier = supplier;
    }

    @Column(name = "SENT_METHOD_ID")
    @Enumerated(EnumType.ORDINAL)
	public SentMethodEnum getSentMethod()
	{
		return sentMethod;
	}

	public void setSentMethod(SentMethodEnum sentMethod)
	{
		this.sentMethod = sentMethod;
	}

    @Column(name = "SENT_REFERENCE_NO", nullable = true)
	public String getSentReferenceNo()
	{
		return sentReferenceNo;
	}

	public void setSentReferenceNo(String sentReferenceNo)
	{
		this.sentReferenceNo = sentReferenceNo;
	}

    @Column(name = "PAYMENT_METHOD_ID")
    @Enumerated(EnumType.ORDINAL)
	public PaymentMethodEnum getPaymentMethod()
	{
		return paymentMethod;
	}

	public void setPaymentMethod(PaymentMethodEnum paymentMethod)
	{
		this.paymentMethod = paymentMethod;
	}

    @Temporal(TemporalType.DATE)
    @Column(name = "SENT_ADT_DATE", nullable = true)
	public Date getSentAdtDate()
	{
		return sentAdtDate;
	}

	public void setSentAdtDate(Date sentAdtDate)
	{
		this.sentAdtDate = sentAdtDate;
	}

    @Temporal(TemporalType.DATE)
    @Column(name = "SENT_CUSTOMER_DATE", nullable = true)
	public Date getSentCustomerDate()
	{
		return sentCustomerDate;
	}

	public void setSentCustomerDate(Date sentCustomerDate)
	{
		this.sentCustomerDate = sentCustomerDate;
	}

    @Column(name = "STATUS_ID")
    @Enumerated(EnumType.ORDINAL)
	public AseKeyStatusEnum getStatus()
	{
		return status;
	}

	public void setStatus(AseKeyStatusEnum status)
	{
		this.status = status;
	}

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INVOICE_ID", nullable = true)
	public AseKeyInvoice getInvoice()
	{
		return invoice;
	}

	public void setInvoice(AseKeyInvoice invoice)
	{
		this.invoice = invoice;
	}

}