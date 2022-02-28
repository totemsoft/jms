package au.gov.qld.fire.jms.domain.ase;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Parameter;

import au.gov.qld.fire.domain.Auditable;
import au.gov.qld.fire.domain.entity.Contact;
import au.gov.qld.fire.domain.location.Address;
import au.gov.qld.fire.domain.refdata.AseKeyStatusEnum;
import au.gov.qld.fire.domain.refdata.PaymentMethodEnum;
import au.gov.qld.fire.domain.refdata.SentMethodEnum;
import au.gov.qld.fire.domain.supplier.Supplier;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Entity
@Table(name = "ASE_KEY_ORDER")
public class AseKeyOrder extends Auditable<Long>
{

    /** serialVersionUID */
	private static final long serialVersionUID = 1503121739105241316L;

    public static final String[] IGNORE = (String[]) ArrayUtils.addAll(Auditable.IGNORE,
        new String[] {"supplier", "contact", "address"});

    private String orderNo;

    private Date receivedDate;

    private String comment;

    private Supplier supplier;

    // We can receive orders from different people at the Alarm Company.
    // The order needs to reflect the contact who submitted the form,
    // as this is the first point of contact if there are any issues.
    private Contact contact;

    // delivery address
    private Address address;

    private SentMethodEnum sentMethod = SentMethodEnum.AUSTRALIA_POST;

    private String sentReferenceNo;

    private PaymentMethodEnum paymentMethod;

    private BigDecimal keyPrice;

    private BigDecimal postagePrice;

    private List<AseKey> aseKeys;

    /**
     * 
     */
    public AseKeyOrder()
    {
        super();
    }

    /**
     * @param id
     */
    public AseKeyOrder(Long id)
    {
        super(id);
    }

    @TableGenerator(name = "aseKeyOrderGenerator",
//        table = "HIBERNATE_SEQUENCES",
//        pkColumnName = "SEQUENCE_NAME",
//        valueColumnName = "NEXT_VAL",
        allocationSize = 1,
        pkColumnValue = "ASE_KEY_ORDER"
    )
    @org.hibernate.annotations.GenericGenerator(name = "aseKeyOrderGenerator",
        strategy = "org.hibernate.id.enhanced.TableGenerator",
        parameters = {
//            @Parameter(name = "table_name", value = "HIBERNATE_SEQUENCES"),
//            @Parameter(name = "segment_column_name", value = "SEQUENCE_NAME"),
//            @Parameter(name = "value_column_name", value = "NEXT_VAL"),
            @Parameter(name = "increment_size", value = "1"),
            @Parameter(name = "segment_value", value = "ASE_KEY_ORDER")
        }
    )
    @Id
    @GeneratedValue(generator = "aseKeyOrderGenerator")
    @Column(name = "ASE_KEY_ORDER_ID")
    public Long getAseKeyOrderId()
    {
        return super.getId();
    }
    public void setAseKeyOrderId(Long aseKeyOrderId)
    {
        super.setId(aseKeyOrderId);
    }

    @Column(name = "ORDER_NO", nullable = true)
	public String getOrderNo()
	{
    	if (StringUtils.isBlank(orderNo) && getAseKeyOrderId() != null) {
    		orderNo = getAseKeyOrderId().toString();
    	}
		return orderNo;
	}
	public void setOrderNo(String orderNo)
	{
		this.orderNo = orderNo;
	}

    @Temporal(TemporalType.DATE)
    @Column(name = "RECEIVED_DATE", nullable = false)
	public Date getReceivedDate()
	{
		return receivedDate;
	}
	public void setReceivedDate(Date receivedDate)
	{
		this.receivedDate = receivedDate;
	}

    @Column(name = "COMMENT", nullable = true)
    public String getComment()
    {
		return comment;
	}
	public void setComment(String comment)
	{
		this.comment = comment;
	}

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SUPPLIER_ID", nullable = false)
    public Supplier getSupplier()
    {
        return supplier;
    }
    public void setSupplier(Supplier supplier)
    {
        this.supplier = supplier;
    }

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "CONTACT_ID", nullable = false, unique = true)
    public Contact getContact()
    {
    	if (contact == null) {
    		contact = new Contact();
    	}
        return contact;
    }
    public void setContact(Contact contact)
    {
        this.contact = contact;
    }

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "ADDRESS_ID", nullable = false, unique = true)
    public Address getAddress()
    {
    	if (address == null) {
    		address = new Address();
    	}
		return address;
	}
	public void setAddress(Address address)
	{
		this.address = address;
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

    @Column(name = "KEY_PRICE", nullable = false)
	public BigDecimal getKeyPrice()
	{
		return keyPrice;
	}
	public void setKeyPrice(BigDecimal keyPrice)
	{
		this.keyPrice = keyPrice;
	}

    @Column(name = "POSTAGE_PRICE", nullable = true)
	public BigDecimal getPostagePrice()
    {
		//if (!SentMethodEnum.AUSTRALIA_POST.equals(sentMethod)) {
		//	return BigDecimal.ZERO.setScale(2);
		//}
		return (postagePrice == null ? BigDecimal.TEN : postagePrice).setScale(2);
	}
	public void setPostagePrice(BigDecimal postagePrice)
	{
		this.postagePrice = postagePrice;
	}

    @Transient
	public int getTotalNo()
	{
    	return getAseKeys().size();
	}

    @Transient
	public BigDecimal getTotalPrice()
	{
    	BigDecimal result = BigDecimal.ZERO;
		if (supplier == null || !supplier.getSupplierType().isChargeable()) {
			return result;
		}
		if (keyPrice == null || aseKeys == null || aseKeys.isEmpty()) {
			return result;
		}
		//result = keyPrice.multiply(new BigDecimal(aseKeys.size()));
		for (AseKey aseKey : aseKeys) {
			if (AseKeyStatusEnum.ASSIGNED.equals(aseKey.getStatus())) {
				result = result.add(keyPrice);
			}
		}
		if (getPostagePrice() != null) {
			result = result.add(getPostagePrice());
		}
		return result;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "order", cascade = CascadeType.ALL)
    //@Where(clause = "LOGICALLY_DELETED IS NULL")
	public List<AseKey> getAseKeys()
	{
        if (aseKeys == null) {
        	aseKeys = new ArrayList<AseKey>();
        }
		return aseKeys;
	}

	public void setAseKeys(List<AseKey> aseKeys)
	{
		this.aseKeys = aseKeys;
	}

	@Transient
	public Date getSentCustomerDate()
	{
		for (AseKey aseKey : getAseKeys()) {
			if (aseKey.getSentCustomerDate() != null) {
				return aseKey.getSentCustomerDate();
			}
		}
		return null;
	}

}