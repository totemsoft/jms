package au.gov.qld.fire.jms.domain.ase;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import au.gov.qld.fire.domain.Auditable;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Entity
@Table(name = "ASE_KEY_PRICE")
@org.hibernate.annotations.Entity(mutable = false)
@Cache(region = "au.gov.qld.fire.jms.domain.ase.AseKeyPrice", usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class AseKeyPrice extends Auditable<Long>
{

	/** serialVersionUID */
	private static final long serialVersionUID = -9063010683211356748L;

    private String name;

    private String description;

    private BigDecimal keyPrice;

    /**
     * 
     */
    public AseKeyPrice()
    {
        super();
    }

    /**
     * @param id
     */
    public AseKeyPrice(Long id)
    {
        super(id);
    }

    @Id
    @Column(name = "ASE_KEY_PRICE_ID")
    public Long getAseKeyPriceId()
    {
        return super.getId();
    }

    public void setAseKeyPriceId(Long aseKeyPriceId)
    {
        super.setId(aseKeyPriceId);
    }

    @Column(name = "NAME", nullable = false)
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

    @Column(name = "DESCRIPTION", nullable = true)
	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
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

	@Transient
	public String getKeyPriceName()
	{
		return name + " - " + keyPrice;
	}

}