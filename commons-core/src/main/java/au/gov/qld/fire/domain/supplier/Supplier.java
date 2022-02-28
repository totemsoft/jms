package au.gov.qld.fire.domain.supplier;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.ArrayUtils;

import au.gov.qld.fire.domain.AbstractBusiness;
import au.gov.qld.fire.domain.refdata.SupplierType;
import au.gov.qld.fire.domain.refdata.SupplierTypeEnum;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Entity
@Table(name = "SUPPLIER")
public class Supplier extends AbstractBusiness
{

	/** serialVersionUID */
	private static final long serialVersionUID = -2326026039781378789L;

    public static final String[] IGNORE = (String[]) ArrayUtils.addAll(AbstractBusiness.IGNORE,
        new String[] {"supplierId", "masterSupplier", "supplierType", "supplierTypeMatch"});

    private String serviceDescription;

    private SupplierType supplierType;

    private List<SupplierType> supplierTypeMatch;

    private Supplier masterSupplier;

    public Supplier()
    {
        super();
    }

    public Supplier(Long id)
    {
        super(id);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SUPPLIER_ID")
    public Long getSupplierId()
    {
        return super.getId();
    }

    public void setSupplierId(Long supplierId)
    {
        super.setId(supplierId);
    }

    @Column(name = "SERVICE_DESCRIPTION")
    public String getServiceDescription()
    {
        return this.serviceDescription;
    }

    public void setServiceDescription(String serviceDescription)
    {
        this.serviceDescription = serviceDescription;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "SUPPLIER_TYPE_ID")
    public SupplierType getSupplierType()
    {
        if (supplierType == null)
        {
            supplierType = new SupplierType();
        }
        return this.supplierType;
    }

    public void setSupplierType(SupplierType supplierType)
    {
        this.supplierType = supplierType;
    }

    /**
     * Check for supplier type
     * @return
     */
    @Transient
    public boolean isAseInstallation()
    {
        return SupplierTypeEnum.ASE_INSTALLATION.getId().equals(getSupplierType().getId());
    }

    @Transient
    public boolean isTelco()
    {
        return SupplierTypeEnum.TELCO.getId().equals(getSupplierType().getId());
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MASTER_SUPPLIER_ID")
    public Supplier getMasterSupplier()
    {
        return this.masterSupplier;
    }

    public void setMasterSupplier(Supplier supplier)
    {
        this.masterSupplier = supplier;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "SUPPLIER_TYPE_MATCH",
        joinColumns = @JoinColumn(name = "SUPPLIER_ID", referencedColumnName = "SUPPLIER_ID"),
        inverseJoinColumns = @JoinColumn(name = "SUPPLIER_TYPE_ID", referencedColumnName = "SUPPLIER_TYPE_ID"))
    public List<SupplierType> getSupplierTypeMatch()
    {
        if (supplierTypeMatch == null)
        {
            supplierTypeMatch = new ArrayList<SupplierType>();
        }
        return supplierTypeMatch;
    }

    public void setSupplierTypeMatch(List<SupplierType> supplierTypeMatch)
    {
        this.supplierTypeMatch = supplierTypeMatch;
    }

    /**
     * @param supplierType
     */
    public void add(SupplierType supplierType)
    {
        //supplierType.setSupplier(this);
        add(getSupplierTypeMatch(), supplierType);
    }

}