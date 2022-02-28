package au.gov.qld.fire.domain.location;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.codehaus.jackson.annotate.JsonProperty;

import au.gov.qld.fire.domain.Auditable;

/**
 * @hibernate.class table="ADDRESS" dynamic-update="true"
 *
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Entity
@Table(name = "ADDRESS")
public class Address extends Auditable<Long>
{

    /** serialVersionUID */
    private static final long serialVersionUID = -611372599149774051L;

    /**
     * ADDRESS_IGNORE
     */
    public static final String[] IGNORE = (String[]) ArrayUtils.addAll(Auditable.IGNORE,
        new String[] {"addressId", "state"});

    private boolean sameAs;

    private String addrLine1;

    private String addrLine2;

    private String suburb;

    private String postcode;

    private State state;

    /**
     * 
     */
    public Address()
    {
        super();
    }

    /**
     * @param id
     */
    public Address(Long id)
    {
        super(id);
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.domain.Auditable#clone()
     */
    @Override
    public Object clone() throws CloneNotSupportedException
    {
        Address address = (Address) super.clone();
        address.state = new State(state.getId());
        return address;
    }

    /** 
     *            @hibernate.id
     *             generator-class="sequence"
     *             type="java.lang.Long"
     *             column="ADDRESS_ID"
     *         
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ADDRESS_ID")
    public Long getAddressId()
    {
        return super.getId();
    }

    public void setAddressId(Long addressId)
    {
        super.setId(addressId);
    }

    /**
     * @return the sameAs
     */
    @Transient
    public boolean isSameAs()
    {
        return sameAs;
    }

    /**
     * @param sameAs the sameAs to set
     */
    public void setSameAs(boolean sameAs)
    {
        this.sameAs = sameAs;
    }

    /** 
     *            @hibernate.property
     *             column="ADDR_LINE_1"
     *             length="255"
     *             not-null="true"
     *         
     */
    @Column(name = "ADDR_LINE_1")
    @JsonProperty
    public String getAddrLine1()
    {
        return this.addrLine1;
    }

    public void setAddrLine1(String addrLine1)
    {
        this.addrLine1 = StringUtils.trimToNull(addrLine1);
    }

    /** 
     *            @hibernate.property
     *             column="ADDR_LINE_2"
     *             length="255"
     *         
     */
    @Column(name = "ADDR_LINE_2")
    @JsonProperty
    public String getAddrLine2()
    {
        return this.addrLine2;
    }

    public void setAddrLine2(String addrLine2)
    {
        this.addrLine2 = StringUtils.trimToNull(addrLine2);
    }

    /** 
     *            @hibernate.property
     *             column="SUBURB"
     *             length="255"
     *             not-null="true"
     *         
     */
    @Column(name = "SUBURB")
    @JsonProperty
    public String getSuburb()
    {
        return this.suburb;
    }

    public void setSuburb(String suburb)
    {
        this.suburb = StringUtils.trimToNull(suburb);
    }

    /** 
     *            @hibernate.property
     *             column="POSTCODE"
     *             length="10"
     *             not-null="true"
     *         
     */
    @Column(name = "POSTCODE")
    @JsonProperty
    public String getPostcode()
    {
        return this.postcode;
    }

    public void setPostcode(String postcode)
    {
        this.postcode = StringUtils.trimToNull(postcode);
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STATE")
    @JsonProperty
    public State getState()
    {
        if (this.state == null) {
            this.state = new State();
        }
        return this.state;
    }

    public void setState(State state)
    {
        this.state = state == null ? null : (State) state.clone();
        //this.state = new State(state.getId());
        //this.state = state;
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.domain.BaseModel#equals(java.lang.Object)
     */
    public boolean equalsAddress(Address other)
    {
        if (other != null)
        {
            //use "other.getId()" as this instance can be hibernate enhancer
            return new EqualsBuilder().append(this.getAddrLine1(), other.getAddrLine1()).append(
                this.getAddrLine2(), other.getAddrLine2()).append(this.getSuburb(),
                other.getSuburb()).append(this.getPostcode(), other.getPostcode()).append(
                this.getState(), other.getState()).isEquals();
        }
        return false;
    }

    /**
     * Calculated
     * @return
     */
    @Transient
    public String getAddrLine()
    {
        return (StringUtils.isBlank(getAddrLine1()) ? "" : getAddrLine1() + "")
            + (StringUtils.isBlank(getAddrLine2()) ? "" : ",\n" + getAddrLine2() + "");
    }

    /**
     * Calculated
     * @return
     */
    @Transient
    public String getSuburbStatePostcode()
    {
        return (StringUtils.isBlank(getSuburb()) ? "" : ", " + getSuburb() + " ")
            + (getState() == null || StringUtils.isBlank(getState().getState()) ? "" : getState().getState() + " ")
            + (StringUtils.isBlank(getPostcode()) ? "" : getPostcode());
    }

    /**
     * Calculated
     * @return fullAddress
     */
    @Transient
    public String getFullAddress()
    {
        return (StringUtils.isBlank(getAddrLine1()) ? "" : getAddrLine1() + "")
            + (StringUtils.isBlank(getAddrLine2()) ? "" : ",\n" + getAddrLine2() + "")
            + (StringUtils.isBlank(getSuburb()) ? "" : ",\n" + getSuburb() + " ")
            + (getState() == null || StringUtils.isBlank(getState().getState()) ? "" : getState().getState() + " ")
            + (StringUtils.isBlank(getPostcode()) ? "" : getPostcode());
    }

    /**
     * Calculated
     * @return fullAddressJson
     */
    @Transient
    public String getFullAddressJson()
    {
        return StringUtils.replace(getFullAddress(), "\n", " ");
    }

}