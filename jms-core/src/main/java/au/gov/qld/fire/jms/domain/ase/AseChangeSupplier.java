package au.gov.qld.fire.jms.domain.ase;

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
import javax.persistence.Transient;

import org.hibernate.annotations.Type;

import au.gov.qld.fire.domain.Auditable;
import au.gov.qld.fire.domain.supplier.Supplier;
import au.gov.qld.fire.jms.domain.refdata.AseConnType;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Entity
@Table(name = "ASE_CHANGE_SUPPLIER")
public class AseChangeSupplier extends Auditable<Long>
{

    /** serialVersionUID */
    private static final long serialVersionUID = -7587980927007411377L;

    private String notation;

    private Date dateCompleted;

    private AseConnType aseConnType;

    private AseChange aseChange;

    private Supplier supplier;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ASE_CHANGE_SUPPLIER_ID")
    public Long getAseChangeSupplierId()
    {
        return super.getId();
    }

    public void setAseChangeSupplierId(Long id)
    {
        super.setId(id);
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.domain.BaseModel#getLogicallyDeleted()
     */
    @Override
    @Column(name = "LOGICALLY_DELETED", nullable = true)
    @Type(type = "yes_no")
    public Boolean getLogicallyDeleted()
    {
        return super.getLogicallyDeleted();
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.domain.BaseModel#setLogicallyDeleted(java.lang.Boolean)
     */
    @Override
    public void setLogicallyDeleted(Boolean logicallyDeleted)
    {
        super.setLogicallyDeleted(logicallyDeleted);
    }

    /** 
     *            @hibernate.property
     *             column="NOTATION"
     *             length="255"
     *         
     */
    @Column(name = "NOTATION", nullable = false)
    public String getNotation()
    {
        return this.notation;
    }

    public void setNotation(String notation)
    {
        this.notation = notation;
    }

    /**
     *            @hibernate.property
     *             column="DATE_COMPLETED"
     *             length="23"
     *
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_COMPLETED", nullable = true)
    public Date getDateCompleted()
    {
        return this.dateCompleted;
    }

    public void setDateCompleted(Date dateCompleted)
    {
        this.dateCompleted = dateCompleted;
    }

    /**
     * @return the aseConnType
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ASE_CONN_TYPE_ID", nullable = false)
    public AseConnType getAseConnType()
    {
        if (aseConnType == null)
        {
            aseConnType = new AseConnType();
        }
        return aseConnType;
    }

    public void setAseConnType(AseConnType aseConnType)
    {
        this.aseConnType = aseConnType;
    }

    /** 
     *                @hibernate.property
     *                 column="ASE_CHANGE_ID"
     *                 length="11"
     *             
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ASE_CHANGE_ID", nullable = false)
    public AseChange getAseChange()
    {
        return this.aseChange;
    }

    public void setAseChange(AseChange aseChange)
    {
        this.aseChange = aseChange;
    }

    /** 
     *                @hibernate.property
     *                 column="SUPPLIER_ID"
     *                 length="11"
     *             
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SUPPLIER_ID", nullable = false)
    public Supplier getSupplier()
    {
        if (supplier == null)
        {
            supplier = new Supplier();
        }
        return this.supplier;
    }

    public void setSupplier(Supplier supplier)
    {
        this.supplier = supplier;
    }

    /**
     * Calculated
     * @return
     */
    @Transient
    public boolean isCompleted()
    {
        return getDateCompleted() != null;
    }

}