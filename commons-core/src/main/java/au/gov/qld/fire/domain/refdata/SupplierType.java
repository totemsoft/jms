package au.gov.qld.fire.domain.refdata;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.ArrayUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import au.gov.qld.fire.domain.Auditable;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Entity
@Table(name = "SUPPLIER_TYPE")
@Cache(region = "au.gov.qld.fire.domain.refdata.SupplierType", usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class SupplierType extends Auditable<Long>
{

    /** serialVersionUID */
    private static final long serialVersionUID = -7755520167498426988L;

    public static final String[] IGNORE = (String[]) ArrayUtils.addAll(Auditable.IGNORE,
        new String[] {"supplierTypeId"});

    private String name;

    private boolean chargeable = true;

    /**
     * 
     */
    public SupplierType()
    {
        super();
    }

    /**
     * @param id
     */
    public SupplierType(Long id)
    {
        super(id);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SUPPLIER_TYPE_ID")
    public Long getSupplierTypeId()
    {
        return super.getId();
    }

    public void setSupplierTypeId(Long supplierTypeId)
    {
        super.setId(supplierTypeId);
    }

    @Column(name = "NAME", length = 100, nullable = false)
    public String getName()
    {
        return this.name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Column(name = "CHARGEABLE", nullable = false)
    @Type(type = "yes_no")
	public boolean isChargeable()
	{
		return chargeable;
	}

	public void setChargeable(boolean chargeable)
	{
		this.chargeable = chargeable;
	}

}