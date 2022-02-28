package au.gov.qld.fire.jms.domain.ase;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.ArrayUtils;

import au.gov.qld.fire.domain.Auditable;
import au.gov.qld.fire.domain.supplier.Supplier;
import au.gov.qld.fire.jms.domain.file.File;
import au.gov.qld.fire.jms.domain.refdata.AseType;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Entity
@Table(name = "ASE_FILE")
@org.hibernate.annotations.GenericGenerator(name = "assigned-strategy", strategy = "assigned")
public class AseFile extends Auditable<Long>
{

    /** serialVersionUID */
    private static final long serialVersionUID = 3920188504932234826L;

    public static final String[] IGNORE = (String[]) ArrayUtils.addAll(Auditable.IGNORE,
        new String[] {"file", "aseChange", "aseChanges", "aseConn", "aseType", "subPanels", "supplier", "suppliers"});

    private String aseSerialNo;

    private File file;

    private AseType aseType;

    private List<AseChange> aseChanges;

    private List<Supplier> suppliers;

    private List<SubPanel> subPanels;

    private AseConn aseConn;

    /**
     * 
     */
    public AseFile()
    {
        super();
    }

    /**
     * @param id
     */
    public AseFile(Long id)
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
        // we set AseFile PK to File PK
        if (super.getId() == null && file != null)
        {
            setId(file.getFileId());
        }
        return super.getId();
    }

    @Id
    @GeneratedValue(generator = "assigned-strategy")
    @Column(name = "ASE_FILE_ID")
    public Long getAseFileId()
    {
        return getId();
    }

    public void setAseFileId(Long aseFileId)
    {
        setId(aseFileId);
    }

    /** 
     *            @hibernate.property
     *             column="ASE_SERIAL_NO"
     *             length="50"
     *             not-null="true"
     *         
     */
    @Column(name = "ASE_SERIAL_NO", nullable = false)
    public String getAseSerialNo()
    {
        return this.aseSerialNo;
    }

    public void setAseSerialNo(String aseSerialNo)
    {
        this.aseSerialNo = aseSerialNo;
    }

    /** 
     *            @hibernate.many-to-one
     *             not-null="true"
     *            @hibernate.column name="FILE_ID"         
     *         
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

    /** 
     *            @hibernate.many-to-one
     *             not-null="true"
     *            @hibernate.column name="ASE_TYPE_ID"         
     *         
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ASE_TYPE_ID", nullable = false)
    public AseType getAseType()
    {
        return this.aseType;
    }

    public void setAseType(AseType aseType)
    {
        this.aseType = aseType;
    }

    /** 
     *            @hibernate.bag
     *             lazy="true"
     *             inverse="true"
     *             cascade="none"
     *            @hibernate.collection-key
     *             column="ASE_FILE_ID"
     *            @hibernate.collection-one-to-many
     *             class="au.gov.qld.fire.jms.hibernate.AseChange"
     *         
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "aseFile")
    protected List<AseChange> getAseChanges()
    {
        if (aseChanges == null)
        {
            aseChanges = new ArrayList<AseChange>();
        }
        return this.aseChanges;
    }

    protected void setAseChanges(List<AseChange> aseChanges)
    {
        this.aseChanges = aseChanges;
    }

    protected void add(AseChange aseChange)
    {
        getAseChanges().clear();
        if (aseChange != null)
        {
            aseChange.setAseFile(this);
            add(getAseChanges(), aseChange);
        }
    }

    /**
     * @deprecated - not used TODO: will be removed in future
     * one-to-one relationship
     * @return
     */
    @Transient
    public AseChange getAseChange()
    {
        //return getAseChanges().isEmpty() ? null : getAseChanges().get(0);
    	return null;
    }

    /**
     * @deprecated - not used TODO: will be removed in future
     * @param aseChange
     */
    public void setAseChange(AseChange aseChange)
    {
        add(aseChange);
    }

    /** 
     *            @hibernate.bag
     *             lazy="true"
     *             inverse="true"
     *             cascade="none"
     *            @hibernate.collection-key
     *             column="ASE_FILE_ID"
     *            @hibernate.collection-one-to-many
     *             class="au.gov.qld.fire.jms.hibernate.Supplier"
     *         
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "ASE_SUPPLIER",
        joinColumns = @JoinColumn(name = "ASE_FILE_ID", referencedColumnName = "ASE_FILE_ID"),
        inverseJoinColumns = @JoinColumn(name = "SUPPLIER_ID", referencedColumnName = "SUPPLIER_ID"))
    //@WhereJoinTable(clause = "LOGICALLY_DELETED IS NULL")
    protected List<Supplier> getSuppliers()
    {
        if (suppliers == null)
        {
            suppliers = new ArrayList<Supplier>();
        }
        return this.suppliers;
    }

    protected void setSuppliers(List<Supplier> suppliers)
    {
        this.suppliers = suppliers;
    }

    /**
     * many-to-many relationship (via ASE_SUPPLIER table), but only one Supplier per AseFile
     * @param supplier
     */
    protected void add(Supplier supplier)
    {
        //supplier.setAseFile(this);
        getSuppliers().clear();
        if (supplier != null)
        {
            getSuppliers().add(supplier);
        }
    }

    /**
     * @return
     */
    @Transient
    public Supplier getSupplier()
    {
        return getSuppliers().isEmpty() ? null : getSuppliers().get(0);
    }

    /**
     * @param supplier
     */
    public void setSupplier(Supplier supplier)
    {
        add(supplier);
    }

    
    /** 
     *            @hibernate.bag
     *             lazy="true"
     *             inverse="true"
     *             cascade="none"
     *            @hibernate.collection-key
     *             column="ASE_FILE_ID"
     *            @hibernate.collection-one-to-many
     *             class="au.gov.qld.fire.jms.hibernate.SubPanel"
     *         
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "aseFile", cascade = {CascadeType.ALL})
    @OrderBy("subPanelOrderId")
    public List<SubPanel> getSubPanels()
    {
        if (subPanels == null)
        {
            subPanels = new ArrayList<SubPanel>();
        }
        return this.subPanels;
    }

    public void setSubPanels(List<SubPanel> subPanels)
    {
        this.subPanels = subPanels;
    }

    public void add(SubPanel subPanel)
    {
        subPanel.setAseFile(this);
        add(getSubPanels(), subPanel);
    }

    /** 
     *            @hibernate.many-to-one
     *             not-null="true"
     *            @hibernate.column name="ASE_FILE_ID"         
     *         
     */
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "aseFile", cascade = CascadeType.ALL)
    public AseConn getAseConn()
    {
        if (aseConn == null)
        {
            aseConn = new AseConn();
        }
        return this.aseConn;
    }

    public void setAseConn(AseConn aseConn)
    {
        if (aseConn != null)
        {
            aseConn.setAseFile(this);
        }
        this.aseConn = aseConn;
    }

}