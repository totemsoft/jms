package au.gov.qld.fire.jms.domain.ase;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import au.gov.qld.fire.domain.Auditable;
import au.gov.qld.fire.domain.supplier.Supplier;
import au.gov.qld.fire.jms.domain.file.File;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Entity
@IdClass(AlarmPanelPK.class)
@Table(name = "ALARM_PANEL")
public class AlarmPanel extends Auditable<AlarmPanelPK>
{

    /** serialVersionUID */
    private static final long serialVersionUID = 4644566255116879819L;

    /** FILE property name */
    public static final String FILE = "file";

    /** SUPPLIER property name */
    public static final String SUPPLIER = "supplier";

    /** identifier field */
    private Long fileId;

    /** identifier field */
    private Long supplierId;

    private String alrmSystType;

    private String alrmManuf;

    private String alrmModel;

    private String panelLoc;

    private Long subPanelQty;

    private Long dthermQty;

    private Long dsmokeQty;

    private Long dcombustQty;

    private Long duvQty;

    private Long dinfredQty;

    private Long dmulticQty;

    private Long dsprinkQty;

    private Long dairsampQty;

    private Long dmancallQty;

    private Long dotherQty;

    private String dotherDefine;

    private String sfsDualDetect;

    private String sfsAircon;

    private String sfsElevator;

    private String sfsEvacctrl;

    private String sfsFiredoors;

    private String sfsBooster;

    private String sfsDrypipe;

    private String sfsOther;

    private String mimcLoc;

    private String mimicLayout;

    private String areaCircdes;

    private String mods;

    private File file;

    private Supplier supplier;

    @Transient
    public AlarmPanelPK getId()
    {
        if (super.getId() == null)
        {
            setId(new AlarmPanelPK(getFileId(), getSupplierId()));
        }
        return super.getId();
    }

    @Id
	@Column(name = "FILE_ID", nullable = false)
    public Long getFileId()
    {
        return this.fileId;
    }

    public void setFileId(Long fileId)
    {
        this.fileId = fileId;
    }

    @Id
	@Column(name = "SUPPLIER_ID", nullable = false)
    public Long getSupplierId()
    {
        return this.supplierId;
    }

    public void setSupplierId(Long supplierId)
    {
        this.supplierId = supplierId;
    }

    /** 
     *            @hibernate.property
     *             column="ALRM_SYST_TYPE"
     *             length="255"
     *             not-null="false"
     *         
     */
	@Column(name = "ALRM_SYST_TYPE", nullable = true)
    public String getAlrmSystType()
    {
        return this.alrmSystType;
    }

    public void setAlrmSystType(String alrmSystType)
    {
        this.alrmSystType = alrmSystType;
    }

    /** 
     *            @hibernate.property
     *             column="ALRM_MANUF"
     *             length="255"
     *             not-null="false"
     *         
     */
	@Column(name = "ALRM_MANUF", nullable = true)
    public String getAlrmManuf()
    {
        return this.alrmManuf;
    }

    public void setAlrmManuf(String alrmManuf)
    {
        this.alrmManuf = alrmManuf;
    }

    /** 
     *            @hibernate.property
     *             column="ALRM_MODEL"
     *             length="255"
     *             not-null="false"
     *         
     */
	@Column(name = "ALRM_MODEL", nullable = true)
    public String getAlrmModel()
    {
        return this.alrmModel;
    }

    public void setAlrmModel(String alrmModel)
    {
        this.alrmModel = alrmModel;
    }

    /** 
     *            @hibernate.property
     *             column="PANEL_LOC"
     *             length="255"
     *             not-null="false"
     *         
     */
	@Column(name = "PANEL_LOC", nullable = true)
    public String getPanelLoc()
    {
        return this.panelLoc;
    }

    public void setPanelLoc(String panelLoc)
    {
        this.panelLoc = panelLoc;
    }

    /** 
     *            @hibernate.property
     *             column="SUB_PANEL_QTY"
     *             length="11"
     *             not-null="false"
     *         
     */
	@Column(name = "SUB_PANEL_QTY", nullable = true)
    public Long getSubPanelQty()
    {
        return this.subPanelQty;
    }

    public void setSubPanelQty(Long subPanelQty)
    {
        this.subPanelQty = subPanelQty;
    }

    /** 
     *            @hibernate.property
     *             column="DTHERM_QTY"
     *             length="11"
     *             not-null="false"
     *         
     */
	@Column(name = "DTHERM_QTY", nullable = true)
    public Long getDthermQty()
    {
        return this.dthermQty;
    }

    public void setDthermQty(Long dthermQty)
    {
        this.dthermQty = dthermQty;
    }

    /** 
     *            @hibernate.property
     *             column="DSMOKE_QTY"
     *             length="11"
     *             not-null="false"
     *         
     */
	@Column(name = "DSMOKE_QTY", nullable = true)
    public Long getDsmokeQty()
    {
        return this.dsmokeQty;
    }

    public void setDsmokeQty(Long dsmokeQty)
    {
        this.dsmokeQty = dsmokeQty;
    }

    /** 
     *            @hibernate.property
     *             column="DCOMBUST_QTY"
     *             length="11"
     *             not-null="false"
     *         
     */
	@Column(name = "DCOMBUST_QTY", nullable = true)
    public Long getDcombustQty()
    {
        return this.dcombustQty;
    }

    public void setDcombustQty(Long dcombustQty)
    {
        this.dcombustQty = dcombustQty;
    }

    /** 
     *            @hibernate.property
     *             column="DUV_QTY"
     *             length="11"
     *             not-null="false"
     *         
     */
	@Column(name = "DUV_QTY", nullable = true)
    public Long getDuvQty()
    {
        return this.duvQty;
    }

    public void setDuvQty(Long duvQty)
    {
        this.duvQty = duvQty;
    }

    /** 
     *            @hibernate.property
     *             column="DINFRED_QTY"
     *             length="11"
     *             not-null="false"
     *         
     */
	@Column(name = "DINFRED_QTY", nullable = true)
    public Long getDinfredQty()
    {
        return this.dinfredQty;
    }

    public void setDinfredQty(Long dinfredQty)
    {
        this.dinfredQty = dinfredQty;
    }

    /** 
     *            @hibernate.property
     *             column="DMULTIC_QTY"
     *             length="11"
     *             not-null="false"
     *         
     */
	@Column(name = "DMULTIC_QTY", nullable = true)
    public Long getDmulticQty()
    {
        return this.dmulticQty;
    }

    public void setDmulticQty(Long dmulticQty)
    {
        this.dmulticQty = dmulticQty;
    }

    /** 
     *            @hibernate.property
     *             column="DSPRINK_QTY"
     *             length="11"
     *             not-null="false"
     *         
     */
	@Column(name = "DSPRINK_QTY", nullable = true)
    public Long getDsprinkQty()
    {
        return this.dsprinkQty;
    }

    public void setDsprinkQty(Long dsprinkQty)
    {
        this.dsprinkQty = dsprinkQty;
    }

    /** 
     *            @hibernate.property
     *             column="DAIRSAMP_QTY"
     *             length="11"
     *             not-null="false"
     *         
     */
	@Column(name = "DAIRSAMP_QTY", nullable = true)
    public Long getDairsampQty()
    {
        return this.dairsampQty;
    }

    public void setDairsampQty(Long dairsampQty)
    {
        this.dairsampQty = dairsampQty;
    }

    /** 
     *            @hibernate.property
     *             column="DMANCALL_QTY"
     *             length="11"
     *             not-null="false"
     *         
     */
	@Column(name = "DMANCALL_QTY", nullable = true)
    public Long getDmancallQty()
    {
        return this.dmancallQty;
    }

    public void setDmancallQty(Long dmancallQty)
    {
        this.dmancallQty = dmancallQty;
    }

    /** 
     *            @hibernate.property
     *             column="DOTHER_QTY"
     *             length="11"
     *             not-null="false"
     *         
     */
	@Column(name = "DOTHER_QTY", nullable = true)
    public Long getDotherQty()
    {
        return this.dotherQty;
    }

    public void setDotherQty(Long dotherQty)
    {
        this.dotherQty = dotherQty;
    }

    /** 
     *            @hibernate.property
     *             column="DOTHER_DEFINE"
     *             length="255"
     *             not-null="false"
     *         
     */
	@Column(name = "DOTHER_DEFINE", nullable = true)
    public String getDotherDefine()
    {
        return this.dotherDefine;
    }

    public void setDotherDefine(String dotherDefine)
    {
        this.dotherDefine = dotherDefine;
    }

    /** 
     *            @hibernate.property
     *             column="SFS_DUAL_DETECT"
     *             length="2147483647"
     *             not-null="false"
     *         
     */
	@Column(name = "SFS_DUAL_DETECT", nullable = true)
    public String getSfsDualDetect()
    {
        return this.sfsDualDetect;
    }

    public void setSfsDualDetect(String sfsDualDetect)
    {
        this.sfsDualDetect = sfsDualDetect;
    }

    /** 
     *            @hibernate.property
     *             column="SFS_AIRCON"
     *             length="2147483647"
     *             not-null="false"
     *         
     */
	@Column(name = "SFS_AIRCON", nullable = true)
    public String getSfsAircon()
    {
        return this.sfsAircon;
    }

    public void setSfsAircon(String sfsAircon)
    {
        this.sfsAircon = sfsAircon;
    }

    /** 
     *            @hibernate.property
     *             column="SFS_ELEVATOR"
     *             length="2147483647"
     *             not-null="false"
     *         
     */
	@Column(name = "SFS_ELEVATOR", nullable = true)
    public String getSfsElevator()
    {
        return this.sfsElevator;
    }

    public void setSfsElevator(String sfsElevator)
    {
        this.sfsElevator = sfsElevator;
    }

    /** 
     *            @hibernate.property
     *             column="SFS_EVACCTRL"
     *             length="2147483647"
     *             not-null="false"
     *         
     */
	@Column(name = "SFS_EVACCTRL", nullable = true)
    public String getSfsEvacctrl()
    {
        return this.sfsEvacctrl;
    }

    public void setSfsEvacctrl(String sfsEvacctrl)
    {
        this.sfsEvacctrl = sfsEvacctrl;
    }

    /** 
     *            @hibernate.property
     *             column="SFS_FIREDOORS"
     *             length="2147483647"
     *             not-null="false"
     *         
     */
	@Column(name = "SFS_FIREDOORS", nullable = true)
    public String getSfsFiredoors()
    {
        return this.sfsFiredoors;
    }

    public void setSfsFiredoors(String sfsFiredoors)
    {
        this.sfsFiredoors = sfsFiredoors;
    }

    /** 
     *            @hibernate.property
     *             column="SFS_BOOSTER"
     *             length="2147483647"
     *             not-null="false"
     *         
     */
	@Column(name = "SFS_BOOSTER", nullable = true)
    public String getSfsBooster()
    {
        return this.sfsBooster;
    }

    public void setSfsBooster(String sfsBooster)
    {
        this.sfsBooster = sfsBooster;
    }

    /** 
     *            @hibernate.property
     *             column="SFS_DRYPIPE"
     *             length="2147483647"
     *             not-null="false"
     *         
     */
	@Column(name = "SFS_DRYPIPE", nullable = true)
    public String getSfsDrypipe()
    {
        return this.sfsDrypipe;
    }

    public void setSfsDrypipe(String sfsDrypipe)
    {
        this.sfsDrypipe = sfsDrypipe;
    }

    /** 
     *            @hibernate.property
     *             column="SFS_OTHER"
     *             length="2147483647"
     *             not-null="false"
     *         
     */
	@Column(name = "SFS_OTHER", nullable = true)
    public String getSfsOther()
    {
        return this.sfsOther;
    }

    public void setSfsOther(String sfsOther)
    {
        this.sfsOther = sfsOther;
    }

    /** 
     *            @hibernate.property
     *             column="MIMC_LOC"
     *             length="2147483647"
     *             not-null="false"
     *         
     */
	@Column(name = "MIMC_LOC", nullable = true)
    public String getMimcLoc()
    {
        return this.mimcLoc;
    }

    public void setMimcLoc(String mimcLoc)
    {
        this.mimcLoc = mimcLoc;
    }

    /** 
     *            @hibernate.property
     *             column="MIMIC_LAYOUT"
     *             length="2147483647"
     *             not-null="false"
     *         
     */
	@Column(name = "MIMIC_LAYOUT", nullable = true)
    public String getMimicLayout()
    {
        return this.mimicLayout;
    }

    public void setMimicLayout(String mimicLayout)
    {
        this.mimicLayout = mimicLayout;
    }

    /** 
     *            @hibernate.property
     *             column="AREA_CIRCDES"
     *             length="2147483647"
     *             not-null="false"
     *         
     */
	@Column(name = "AREA_CIRCDES", nullable = true)
    public String getAreaCircdes()
    {
        return this.areaCircdes;
    }

    public void setAreaCircdes(String areaCircdes)
    {
        this.areaCircdes = areaCircdes;
    }

    /** 
     *            @hibernate.property
     *             column="MODS"
     *             length="2147483647"
     *             not-null="false"
     *         
     */
	@Column(name = "MODS", nullable = true)
    public String getMods()
    {
        return this.mods;
    }

    public void setMods(String mods)
    {
        this.mods = mods;
    }

    /** 
     *            @hibernate.many-to-one
     *             update="false"
     *             insert="false"
     *         
     *            @hibernate.column
     *             name="FILE_ID"
     *         
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FILE_ID", nullable = false, insertable = false, updatable = false)
    public File getFile()
    {
        return this.file;
    }

    public void setFile(File file)
    {
        if (getFileId() == null)
        {
            setFileId(file.getId());
        }
        if (getId().getFileId() == null)
        {
            getId().setFileId(file.getId());
        }
        this.file = file;
    }

    /** 
     *            @hibernate.many-to-one
     *             update="false"
     *             insert="false"
     *         
     *            @hibernate.column
     *             name="SUPPLIER_ID"
     *         
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SUPPLIER_ID", nullable = false, insertable = false, updatable = false)
    public Supplier getSupplier()
    {
        return this.supplier;
    }

    public void setSupplier(Supplier supplier)
    {
        if (getSupplierId() == null)
        {
            setSupplierId(supplier.getId());
        }
        if (getId().getSupplierId() == null)
        {
            getId().setSupplierId(supplier.getId());
        }
        this.supplier = supplier;
    }

}