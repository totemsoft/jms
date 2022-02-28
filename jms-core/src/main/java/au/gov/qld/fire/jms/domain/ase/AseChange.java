package au.gov.qld.fire.jms.domain.ase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import au.gov.qld.fire.domain.Auditable;
import au.gov.qld.fire.domain.supplier.Supplier;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Entity
@Table(name = "ASE_CHANGE")
public class AseChange extends Auditable<Long>
{

    /** serialVersionUID */
    private static final long serialVersionUID = 1823944645841863955L;

    /** ASE_FILE property name */
    public static final String ASE_FILE = "aseFile";

    /** ASE_CHANGE_SUPPLIERS property name */
    public static final String ASE_CHANGE_SUPPLIERS = "aseChangeSuppliers";

    private Date dateChange;

    private AseFile aseFile;

    private List<AseChangeSupplier> aseChangeSuppliers;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ASE_CHANGE_ID")
    public Long getAseChangeId()
    {
        return super.getId();
    }

    public void setAseChangeId(Long id)
    {
        super.setId(id);
    }

    /** 
     *            @hibernate.property
     *             column="DATE_CHANGE"
     *             length="23"
     *             not-null="true"
     *         
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_CHANGE", nullable = false)
    public Date getDateChange()
    {
        return this.dateChange;
    }

    public void setDateChange(Date dateChange)
    {
        this.dateChange = dateChange;
    }

    /** 
     *            @hibernate.many-to-one
     *             not-null="true"
     *            @hibernate.column name="ASE_FILE_ID"         
     *         
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ASE_FILE_ID", nullable = false)
    public AseFile getAseFile()
    {
        if (aseFile == null)
        {
            aseFile = new AseFile();   
        }
        return this.aseFile;
    }

    public void setAseFile(AseFile aseFile)
    {
        this.aseFile = aseFile;
    }

    /** 
     *            @hibernate.bag
     *             lazy="true"
     *             inverse="true"
     *             cascade="none"
     *            @hibernate.collection-key
     *             column="ASE_CHANGE_ID"
     *            @hibernate.collection-one-to-many
     *             class="au.gov.qld.fire.jms.hibernate.AseChangeSupplier"
     *         
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "aseChange")
    public List<AseChangeSupplier> getAseChangeSuppliers()
    {
        if (aseChangeSuppliers == null)
        {
            aseChangeSuppliers = new ArrayList<AseChangeSupplier>();
        }
        return this.aseChangeSuppliers;
    }

    public void setAseChangeSuppliers(List<AseChangeSupplier> aseChangeSuppliers)
    {
        this.aseChangeSuppliers = aseChangeSuppliers;
    }

    /**
     * @param aseChangeSupplier
     */
    public void add(AseChangeSupplier aseChangeSupplier)
    {
        aseChangeSupplier.setAseChange(this);
        getAseChangeSuppliers().add(aseChangeSupplier);
    }

    /**
     * @param aseChangeSupplier
     */
    public void remove(AseChangeSupplier aseChangeSupplier)
    {
        aseChangeSupplier.setAseChange(null);
        getAseChangeSuppliers().remove(aseChangeSupplier);
    }

    /**
     * Calculated
     * ASE Installation or Carrier Installations or Both that are incomplete
     * @return
     */
    @Transient
    public boolean isAseChangeCompleted()
    {
        for (AseChangeSupplier aseChangeSupplier : getAseChangeSuppliers())
        {
            if (!aseChangeSupplier.isCompleted())
            {
                return false;
            }
        }
        return getAseChangeSuppliers().size() > 0;
    }

    /**
     * Calculated
     * @param supplierName
     * @return
     */
    public boolean hasSupplier(String supplierName)
    {
        for (AseChangeSupplier aseChangeSupplier : getAseChangeSuppliers())
        {
            Supplier supplier = aseChangeSupplier.getSupplier();
            if (supplier != null && supplier.getName().equals(supplierName))
            {
                return true;
            }
        }
        return false;
    }

}