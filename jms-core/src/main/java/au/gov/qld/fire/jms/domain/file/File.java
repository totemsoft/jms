package au.gov.qld.fire.jms.domain.file;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.ArrayUtils;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;

import au.gov.qld.fire.converters.TextHttpInputMessage;
import au.gov.qld.fire.converters.TextHttpOutputMessage;
import au.gov.qld.fire.domain.Auditable;
import au.gov.qld.fire.domain.entity.Company;
import au.gov.qld.fire.domain.entity.Contact;
import au.gov.qld.fire.jms.domain.Fca;
import au.gov.qld.fire.jms.domain.KeyReceipt;
import au.gov.qld.fire.jms.domain.KeyReg;
import au.gov.qld.fire.jms.domain.action.FileAction;
import au.gov.qld.fire.jms.domain.ase.AlarmPanel;
import au.gov.qld.fire.jms.domain.ase.AseFile;
import au.gov.qld.fire.jms.domain.building.Building;
import au.gov.qld.fire.jms.domain.building.BuildingContact;
import au.gov.qld.fire.jms.domain.building.BuildingContact.BuildingContactComparator;
import au.gov.qld.fire.jms.domain.entity.Owner;
import au.gov.qld.fire.jms.domain.isolation.IsolationHistory;
import au.gov.qld.fire.jms.domain.job.Job;
import au.gov.qld.fire.jms.domain.mail.MailBatchFile;
import au.gov.qld.fire.jms.domain.mail.MailMethodEnum;
import au.gov.qld.fire.jms.domain.mail.MailRegister;
import au.gov.qld.fire.jms.domain.refdata.ActionType;
import au.gov.qld.fire.jms.domain.refdata.BuildingContactType;
import au.gov.qld.fire.jms.domain.refdata.BuildingContactTypeEnum;
import au.gov.qld.fire.jms.domain.refdata.FileStatus;
import au.gov.qld.fire.jms.domain.refdata.FileStatusEnum;
import au.gov.qld.fire.jms.domain.refdata.FileTypeEnum;
import au.gov.qld.fire.jms.domain.refdata.OwnerTypeEnum;
import au.gov.qld.fire.jms.domain.refdata.SiteType;
import au.gov.qld.fire.jms.domain.sap.Rfi;
import au.gov.qld.fire.jms.domain.sap.SapHeader;

@Entity
@Table(name = "FILES")
//@Where(clause = "FILE_TYPE_ID IS NULL")
public class File extends Auditable<Long>
{

    /** serialVersionUID */
    private static final long serialVersionUID = 8943115639034343720L;

    /**
     * FILE_IGNORE
     */
    public static final String[] IGNORE = (String[]) ArrayUtils.addAll(Auditable.IGNORE,
        new String[] {"fileId", "fileStatus"});

    private FileTypeEnum fileType;

    private FileStatus fileStatus;

    private SiteType siteType;

    private boolean noMailOut;

    private MailMethodEnum mailMethod;

    private SapHeader sapHeader;

    private List<Rfi> rfis;

    private List<FileDoc> fileDocs;

    private List<Job> jobs;

    private List<FileAction> fileActions;

    private List<Building> buildings;

    private List<KeyReceipt> keyReceipts;

    private List<FileDocChkList> fileDocChkLists;

    private List<BuildingContact> buildingContacts;

    private List<AlarmPanel> alarmPanels;

    private Fca fca;

    private List<KeyReg> keyRegs;

    private List<Owner> owners;

    private List<AseFile> aseFiles;

    private List<MailRegister> mailRegisters;

    private List<IsolationHistory> isolations;

    private FileAudit fileAudit;

    private List<MailBatchFile> mailBatchFiles;

    public File()
    {
        super();
    }

    public File(Long id)
    {
        super(id);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FILE_ID")
    @JsonProperty
    public Long getFileId()
    {
        return super.getId();
    }

    public void setFileId(Long fileId)
    {
        super.setId(fileId);
    }

	@Column(name = "FILE_TYPE_ID")
    @Enumerated(EnumType.ORDINAL)
	public FileTypeEnum getFileType()
	{
		return fileType;
	}

	public void setFileType(FileTypeEnum fileType)
	{
		this.fileType = fileType;
	}

	/** 
     *            @hibernate.many-to-one
     *             not-null="true"
     *            @hibernate.column name="FILE_STATUS_ID"         
     *         
     */
    @ManyToOne//(fetch = FetchType.LAZY)
    @JoinColumn(name = "FILE_STATUS_ID", nullable = false)
    public FileStatus getFileStatus()
    {
        if (fileStatus == null)
        {
            fileStatus = new FileStatus(FileStatusEnum.CONNECTED);
        }
        return this.fileStatus;
    }

    public void setFileStatus(FileStatus fileStatus)
    {
        this.fileStatus = fileStatus;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SITE_TYPE_ID", nullable = true)
	public SiteType getSiteType()
	{
		return siteType;
	}

	public void setSiteType(SiteType siteType)
	{
		this.siteType = siteType;
	}

    @Column(name = "NO_MAIL_OUT", nullable = false)
    @Type(type = "yes_no")
	public boolean isNoMailOut()
	{
		return noMailOut;
	}

	public void setNoMailOut(boolean noMailOut)
	{
		this.noMailOut = noMailOut;
	}

	@Column(name = "MAIL_METHOD_ID")
    @Enumerated(EnumType.ORDINAL)
    public MailMethodEnum getMailMethod()
	{
		return mailMethod;
	}

	public void setMailMethod(MailMethodEnum mailMethod)
	{
		this.mailMethod = mailMethod;
	}

	@Transient
    public Integer getMailMethodId()
	{
		return getMailMethod() == null ? null : getMailMethod().getId();
	}

	public void setMailMethodId(Integer mailMethodId)
	{
		MailMethodEnum mailMethod = MailMethodEnum.findById(mailMethodId);
		setMailMethod(mailMethod);
	}

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "file", cascade = CascadeType.DETACH)
    @JsonProperty
    public SapHeader getSapHeader()
    {
        return this.sapHeader;
    }

    public void setSapHeader(SapHeader sapHeader)
    {
        if (sapHeader != null)
        {
            sapHeader.setFile(this);
        }
        this.sapHeader = sapHeader;
    }

    /** 
     *            @hibernate.bag
     *             lazy="true"
     *             inverse="true"
     *             cascade="none"
     *            @hibernate.collection-key
     *             column="FILE_ID"
     *            @hibernate.collection-one-to-many
     *             class="au.gov.qld.fire.jms.hibernate.Rfi"
     *         
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "file", cascade = CascadeType.DETACH)
    public List<Rfi> getRfis()
    {
        if (rfis == null)
        {
            rfis = new ArrayList<Rfi>();
        }
        return this.rfis;
    }

    public void setRfis(List<Rfi> rfis)
    {
        this.rfis = rfis;
    }

    public void add(Rfi rfi)
    {
        rfi.setFile(this);
        getRfis().add(rfi);
    }

    /** 
     *            @hibernate.bag
     *             lazy="true"
     *             inverse="true"
     *             cascade="none"
     *            @hibernate.collection-key
     *             column="FILE_ID"
     *            @hibernate.collection-one-to-many
     *             class="au.gov.qld.fire.jms.hibernate.FileDoc"
     *         
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "file", cascade = CascadeType.DETACH)
    public List<FileDoc> getFileDocs()
    {
        if (fileDocs == null)
        {
            fileDocs = new ArrayList<FileDoc>();
        }
        return this.fileDocs;
    }

    public void setFileDocs(List<FileDoc> fileDocs)
    {
        this.fileDocs = fileDocs;
    }

    public void add(FileDoc fileDoc)
    {
        fileDoc.setFile(this);
        getFileDocs().add(fileDoc);
    }

    /** 
     *            @hibernate.bag
     *             lazy="true"
     *             inverse="true"
     *             cascade="none"
     *            @hibernate.collection-key
     *             column="FILE_ID"
     *            @hibernate.collection-one-to-many
     *             class="au.gov.qld.fire.jms.hibernate.Job"
     *         
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "file", cascade = CascadeType.DETACH)
    public List<Job> getJobs()
    {
        if (jobs == null)
        {
            jobs = new ArrayList<Job>();   
        }
        return this.jobs;
    }

    public void setJobs(List<Job> jobs)
    {
        this.jobs = jobs;
    }

    /**
     * Calculated property (either open or closed).
     * @return
     */
    @Transient
    public String getJobStatus()
    {
        for (Job job : getJobs())
        {
            if (job.isStatus()) //open
            {
                return "Open";
            }
        }
        //no job open
        return "Closed";
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "file", cascade = CascadeType.DETACH)
    @Where(clause = "LOGICALLY_DELETED IS NULL")
    public List<FileAction> getFileActions()
    {
        if (fileActions == null)
        {
            fileActions = new ArrayList<FileAction>();
        }
        return this.fileActions;
    }

    public void setFileActions(List<FileAction> fileActions)
    {
        this.fileActions = fileActions;
    }

    public void add(FileAction fileAction)
    {
        fileAction.setFile(this);
        getFileActions().add(fileAction);
    }

    /** 
     *            @hibernate.bag
     *             lazy="true"
     *             inverse="true"
     *             cascade="none"
     *            @hibernate.collection-key
     *             column="FILE_ID"
     *            @hibernate.collection-one-to-many
     *             class="au.gov.qld.fire.jms.hibernate.Building"
     *         
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "file", cascade = CascadeType.DETACH)
    protected List<Building> getBuildings()
    {
        if (buildings == null)
        {
            buildings = new ArrayList<Building>();   
        }
        return this.buildings;
    }

    protected void setBuildings(List<Building> buildings)
    {
        this.buildings = buildings;
    }

    protected void add(Building building)
    {
        building.setFile(this);
        getBuildings().clear();
        getBuildings().add(building);
    }

    /**
     * one-to-one relationship
     * @return
     */
    @Transient
    @JsonProperty
    public Building getBuilding()
    {
        return getBuildings().isEmpty() ? null : getBuildings().get(0);
    }

    public void setBuilding(Building building)
    {
        add(building);
    }

    /** 
     *            @hibernate.bag
     *             lazy="true"
     *             inverse="true"
     *             cascade="none"
     *            @hibernate.collection-key
     *             column="FILE_ID"
     *            @hibernate.collection-one-to-many
     *             class="au.gov.qld.fire.jms.hibernate.KeyReceipt"
     *         
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "file", cascade = CascadeType.DETACH)
    protected List<KeyReceipt> getKeyReceipts()
    {
        if (keyReceipts == null)
        {
            keyReceipts = new ArrayList<KeyReceipt>();
        }
        return this.keyReceipts;
    }

    protected void setKeyReceipts(List<KeyReceipt> keyReceipts)
    {
        this.keyReceipts = keyReceipts;
    }

    protected void add(KeyReceipt keyReceipt)
    {
        keyReceipt.setFile(this);
        getKeyReceipts().clear();
        getKeyReceipts().add(keyReceipt);
    }

    /**
     * one-to-one relationship
     * @return
     */
    @Transient
    public KeyReceipt getKeyReceipt()
    {
        return getKeyReceipts().isEmpty() ? null : getKeyReceipts().get(0);
    }

    public void setKeyReceipt(KeyReceipt keyReceipt)
    {
        add(keyReceipt);
    }

    /** 
     *            @hibernate.bag
     *             lazy="true"
     *             inverse="true"
     *             cascade="none"
     *            @hibernate.collection-key
     *             column="FILE_ID"
     *            @hibernate.collection-one-to-many
     *             class="au.gov.qld.fire.jms.hibernate.FileDocChkList"
     *         
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "file", cascade = CascadeType.DETACH)
    public List<FileDocChkList> getFileDocChkLists()
    {
        if (fileDocChkLists == null)
        {
            fileDocChkLists = new ArrayList<FileDocChkList>();
        }
        return this.fileDocChkLists;
    }

    public void setFileDocChkLists(List<FileDocChkList> fileDocChkLists)
    {
        this.fileDocChkLists = fileDocChkLists;
    }

    public void add(FileDocChkList fileDocChkList)
    {
        fileDocChkList.setFile(this);
        getFileDocChkLists().add(fileDocChkList);
    }

    /** 
     *            @hibernate.bag
     *             lazy="true"
     *             inverse="true"
     *             cascade="none"
     *            @hibernate.collection-key
     *             column="FILE_ID"
     *            @hibernate.collection-one-to-many
     *             class="au.gov.qld.fire.jms.hibernate.BuildingContact"
     *         
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "file", cascade = CascadeType.DETACH)
    @JsonProperty
    public List<BuildingContact> getBuildingContacts()
    {
        if (buildingContacts == null)
        {
            buildingContacts = new ArrayList<BuildingContact>();
        }
        return this.buildingContacts;
    }

    public void setBuildingContacts(List<BuildingContact> buildingContacts)
    {
        this.buildingContacts = buildingContacts;
    }

    public void add(BuildingContact buildingContact)
    {
        buildingContact.setFile(this);
        List<BuildingContact> buildingContacts = getBuildingContacts();
        if (!buildingContacts.isEmpty() && buildingContact.getPriority() == 0 && buildingContact.isActive()) {
        	buildingContact.setPriority(buildingContacts.size() + 1);
        }
        buildingContacts.add(buildingContact);
    }

    private List<BuildingContact> findBuildingContacts(final BuildingContactTypeEnum buildingContactType)
    {
    	final Predicate predicate = new Predicate() {
			public boolean evaluate(Object o) {
				BuildingContactType type = ((BuildingContact) o).getBuildingContactType();
		        return type != null && buildingContactType.getId().equals(type.getId());
			}
		};
        List<BuildingContact> result = new ArrayList<BuildingContact>();
    	CollectionUtils.select(getBuildingContacts(), predicate, result);
    	Collections.sort(result, BuildingContactComparator.getInstance());
        return result;
    }

    /**
     * Calculated.
     * @return
     */
    @Transient
    public List<BuildingContact> getOwnerBuildingContacts()
    {
        return findBuildingContacts(BuildingContactTypeEnum.OWNER);
    }

    @Deprecated
    @Transient
    public List<BuildingContact> getAfterHoursBuildingContacts()
    {
        return findBuildingContacts(BuildingContactTypeEnum.AFTER_HOURS);
    }

    @Deprecated
    @Transient
    public List<BuildingContact> getDaytimeBuildingContacts()
    {
        return findBuildingContacts(BuildingContactTypeEnum.DAYTIME);
    }

    @Transient
    public List<BuildingContact> getSecurityBuildingContacts()
    {
    	List<BuildingContact> result = findBuildingContacts(BuildingContactTypeEnum.SECURITY);
    	for (BuildingContact buildingContact : result)
    	{
    		if (buildingContact.getCompany() == null)
    		{
                buildingContact.setCompany(Company.SECURITY);
    		}
    	}
        return result;
    }

    @Transient
    public List<BuildingContact> getFireBuildingContacts()
    {
    	List<BuildingContact> result = findBuildingContacts(BuildingContactTypeEnum.FIRE);
    	for (BuildingContact buildingContact : result)
    	{
    		if (buildingContact.getCompany() == null)
    		{
                buildingContact.setCompany(Company.FIRE);
    		}
    	}
        return result;
    }

    /** 
     *            @hibernate.bag
     *             lazy="true"
     *             inverse="true"
     *             cascade="none"
     *            @hibernate.collection-key
     *             column="FILE_ID"
     *            @hibernate.collection-one-to-many
     *             class="au.gov.qld.fire.jms.hibernate.AlarmPanel"
     *         
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "file", cascade = CascadeType.DETACH)
    //@org.hibernate.annotations.NotFound(action = org.hibernate.annotations.NotFoundAction.IGNORE)
    protected List<AlarmPanel> getAlarmPanels()
    {
        if (alarmPanels == null)
        {
            alarmPanels = new ArrayList<AlarmPanel>();
        }
        return this.alarmPanels;
    }

    protected void setAlarmPanels(List<AlarmPanel> alarmPanels)
    {
        this.alarmPanels = alarmPanels;
    }

    protected void add(AlarmPanel alarmPanel)
    {
        alarmPanel.setFile(this);
        getAlarmPanels().clear();
        getAlarmPanels().add(alarmPanel);
    }

    /**
     * one-to-one relationship
     * @return
     */
    @Transient
    public AlarmPanel getAlarmPanel()
    {
        return getAlarmPanels().isEmpty() ? null : getAlarmPanels().get(0);
    }

    public void setAlarmPanel(AlarmPanel alarmPanel)
    {
        add(alarmPanel);
    }

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "file", cascade = CascadeType.DETACH)
    public Fca getFca()
    {
    	if (fca != null && !this.equals(fca.getFile()))
    	{
            fca.setFile(this);
    	}
        return fca;
    }

    public void setFca(Fca fca)
    {
        this.fca = fca;
    }

    /** 
     *            @hibernate.bag
     *             lazy="true"
     *             inverse="true"
     *             cascade="none"
     *            @hibernate.collection-key
     *             column="FILE_ID"
     *            @hibernate.collection-one-to-many
     *             class="au.gov.qld.fire.jms.hibernate.KeyReg"
     *         
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "file", cascade = CascadeType.DETACH)
    public List<KeyReg> getKeyRegs()
    {
        if (keyRegs == null)
        {
            keyRegs = new ArrayList<KeyReg>();   
        }
        return this.keyRegs;
    }

    public void setKeyRegs(List<KeyReg> keyRegs)
    {
        this.keyRegs = keyRegs;
    }

    public void add(KeyReg keyReg)
    {
        keyReg.setFile(this);
        getKeyRegs().add(keyReg);
    }

    /** 
     *            @hibernate.bag
     *             lazy="true"
     *             inverse="true"
     *             cascade="none"
     *            @hibernate.collection-key
     *             column="FILE_ID"
     *            @hibernate.collection-one-to-many
     *             class="au.gov.qld.fire.jms.hibernate.Owner"
     *         
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "file", cascade = CascadeType.DETACH)
    @JsonProperty
    public List<Owner> getOwners()
    {
        if (owners == null)
        {
            owners = new ArrayList<Owner>();
        }
        return this.owners;
    }

    public void setOwners(List<Owner> owners)
    {
        this.owners = owners;
    }

    public void add(Owner owner)
    {
    	if (owner.getFile() == null)
    	{
            owner.setFile(this); // do not re-assign owner file
    	}
        if (!getOwners().contains(owner))
        {
            getOwners().add(owner);
        }
    }

    /**
     * Calculated methods
     * @return
     */
    @Transient
    public Owner getDefaultOwner()
    {
    	for (Owner item : getOwners()) {
    		if (item.isDefaultContact()) {
    			return item;
    		}
    	}
        return null; // new Owner(OwnerTypeEnum.NONE)
    }
    @Transient
    public OwnerTypeEnum getDefaultOwnerType()
    {
    	Owner owner = getDefaultOwner();
    	Long ownerTypeId = owner == null ? null : owner.getOwnerType().getId();
        return ownerTypeId == null ? OwnerTypeEnum.NONE : OwnerTypeEnum.findById(ownerTypeId);
    }
    @Transient
    public String getDefaultDestination(ActionType actionType)
    {
    	Owner o = getDefaultOwner();
        if (o != null) {
            Contact c = o.getContact();
            if (actionType.isCall()) {return c.getPhone();}
            if (actionType.isEmail()) {return c.getEmail();}
            if (actionType.isSms()) {return c.getMobile();}
    	}
        return null;
    }
    @Transient
    public Owner getOwnerByType(OwnerTypeEnum type)
    {
    	for (Owner item : getOwners()) {
    		if (item.getOwnerType().getId() == type.ordinal()) {
    			return item;
    		}
    	}
        return null;
    }
    @Transient
    public Owner getOwnerById(Long ownerId)
    {
    	for (Owner item : getOwners()) {
    		if (item.getOwnerId().equals(ownerId)) {
    			return item;
    		}
    	}
        return null;
    }
    @Transient
    public Owner getOwner()
    {
        return getOwnerByType(OwnerTypeEnum.OWNER);
    }
    @Transient
    public Owner getBodyCorporate()
    {
        return getOwnerByType(OwnerTypeEnum.BODY_CORPORATE);
    }
    @Transient
    public Owner getPropertyManager()
    {
        return getOwnerByType(OwnerTypeEnum.PROPERTY_MANAGER);
    }
    @Transient
    public Owner getTenant()
    {
        return getOwnerByType(OwnerTypeEnum.TENANT);
    }
    @Transient
    public Owner getAlternate()
    {
        return getOwnerByType(OwnerTypeEnum.ALTERNATE);
    }

    /** 
     *            @hibernate.bag
     *             lazy="true"
     *             inverse="true"
     *             cascade="none"
     *            @hibernate.collection-key
     *             column="FILE_ID"
     *            @hibernate.collection-one-to-many
     *             class="au.gov.qld.fire.jms.hibernate.AseFile"
     *         
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "file", cascade = CascadeType.DETACH)
    protected List<AseFile> getAseFiles()
    {
        if (aseFiles == null)
        {
            aseFiles = new ArrayList<AseFile>();
        }
        return this.aseFiles;
    }

    protected void setAseFiles(List<AseFile> aseFiles)
    {
        this.aseFiles = aseFiles;
    }

    protected void add(AseFile aseFile)
    {
        aseFile.setFile(this);
        getAseFiles().clear();
        getAseFiles().add(aseFile);
    }

    /**
     * one-to-one relationship
     * @return
     */
    @Transient
    public AseFile getAseFile()
    {
        return getAseFiles().isEmpty() ? null : getAseFiles().get(0);
    }

    public void setAseFile(AseFile aseFile)
    {
        add(aseFile);
    }

    /** 
     *            @hibernate.bag
     *             lazy="true"
     *             inverse="true"
     *             cascade="none"
     *            @hibernate.collection-key
     *             column="FILE_ID"
     *            @hibernate.collection-one-to-many
     *             class=" au.gov.qld.fire.jms.domain.mail.MailRegister"
     *         
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "file", cascade = CascadeType.DETACH)
	public List<MailRegister> getMailRegisters()
	{
        if (mailRegisters == null)
        {
        	mailRegisters = new ArrayList<MailRegister>();
        }
		return mailRegisters;
	}

    protected void setMailRegisters(List<MailRegister> mailRegisters)
	{
		this.mailRegisters = mailRegisters;
	}

	/**
	 * @return the isolations
	 */
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinTable(name = "FCA",
        joinColumns = @JoinColumn(name = "FILE_ID", referencedColumnName = "FILE_ID"),
        inverseJoinColumns = @JoinColumn(name = "FCA_NO", referencedColumnName = "FCA_NO"))
	public List<IsolationHistory> getIsolations()
	{
        if (isolations == null)
        {
        	isolations = new ArrayList<IsolationHistory>();
        }
		return isolations;
	}

	protected void setIsolations(List<IsolationHistory> isolations)
	{
		this.isolations = isolations;
	}

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "file"/*, cascade = CascadeType.DETACH*/)
    //@Where(clause = "AUDIT_STATUS_ID = 1") // AuditStatusEnum.ACTIVE
	public FileAudit getFileAudit()
    {
		return fileAudit;
	}

	public void setFileAudit(FileAudit fileAudit)
	{
		this.fileAudit = fileAudit;
	}

	@Transient
	public boolean isFileAuditActive()
	{
		return getFileAudit() != null && getFileAudit().isActive();
	}
	@Transient
	public boolean isFileAuditApproved()
	{
		return getFileAudit() != null && getFileAudit().isApproved();
	}
	@Transient
	public boolean isFileAuditRejected()
	{
		return getFileAudit() != null && getFileAudit().isRejected();
	}

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "file", cascade = CascadeType.DETACH)
	@OrderBy("mailBatchFileId DESC")
    public List<MailBatchFile> getMailBatchFiles()
	{
        if (mailBatchFiles == null)
        {
        	mailBatchFiles = new ArrayList<MailBatchFile>();
        }
		return mailBatchFiles;
	}

	public void setMailBatchFiles(List<MailBatchFile> mailBatchFiles)
	{
		this.mailBatchFiles = mailBatchFiles;
	}

	@Transient
	public MailBatchFile getLastMailBatchFile()
	{
		return getMailBatchFiles().isEmpty() ? null : getMailBatchFiles().get(0);
	}

	/**
     * 
     * @param data
     * @param converter
     * @return
     * @throws Exception
     */
    public static File convert(byte[] data, HttpMessageConverter<File> converter) throws Exception
    {
        if (ArrayUtils.isEmpty(data)) {
            return new File();
        }
        return converter.read(File.class, new TextHttpInputMessage(data));
    }

    /**
     * 
     * @param value
     * @param converter
     * @return
     * @throws Exception
     */
    public static byte[] convert(File value, HttpMessageConverter<File> converter) throws Exception
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        converter.write(value, MediaType.APPLICATION_JSON, new TextHttpOutputMessage(baos));
        return baos.toByteArray();
    }

}