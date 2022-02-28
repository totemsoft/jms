package au.gov.qld.fire.jms.domain.building;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.ArrayUtils;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.annotations.Type;

import au.gov.qld.fire.domain.Auditable;
import au.gov.qld.fire.domain.location.Address;
import au.gov.qld.fire.domain.security.User;
import au.gov.qld.fire.jms.domain.file.File;
import au.gov.qld.fire.jms.domain.refdata.BuildingClassification;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Entity
@Table(name = "BUILDING")
//@Cache(region = "au.gov.qld.fire.jms.domain.building.Building", usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class Building extends Auditable<Long>
{

    /** serialVersionUID */
    private static final long serialVersionUID = -8676684326654605913L;

    /** IGNORE */
    public static final String[] IGNORE = (String[]) ArrayUtils.addAll(Auditable.IGNORE, new String[]
	    {"address", "archBy", "classifications", "file", "nextBuilding"});

    private String name;

    private String lotPlanNumber;

    private String description;

    private String usage;

    private boolean keyRequired;

    private User archBy;

    private Date archDate;

    private File file;

    private List<BuildingClassification> classifications;

    private Address address;

    private Building nextBuilding;

    private BuildingAlt escadBuilding;

    private BuildingAlt mastermindBuilding;

    /**
     * 
     */
    public Building()
    {
        super();
    }

    /**
     * @param id
     */
    public Building(Long id)
    {
        super(id);
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.domain.BaseModel#getId()
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BUILDING_ID")
    public Long getBuildingId()
    {
        return super.getId();
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.domain.BaseModel#setId(java.lang.Object)
     */
    public void setBuildingId(Long id)
    {
        super.setId(id);
    }

    @Column(name = "NAME", nullable = false, length = 100)
    @JsonProperty
    public String getName()
    {
        return this.name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Column(name = "LOT_PLAN_NUMBER", nullable = true, length = 30)
    @JsonProperty
    public String getLotPlanNumber()
    {
		return lotPlanNumber;
	}

	public void setLotPlanNumber(String lotPlanNumber)
	{
		this.lotPlanNumber = lotPlanNumber;
	}

	@Column(name = "DESCRIPTION", nullable = true, length = 1024)
    public String getDescription()
    {
        return this.description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    @Column(name = "USAGE", nullable = true, length = 1024)
    public String getUsage()
    {
        return this.usage;
    }

    public void setUsage(String usage)
    {
        this.usage = usage;
    }

    /** 
     *            @hibernate.property
     *             column="KEY_REQUIRED"
     *             length="1"
     *             not-null="true"
     *         
     */
    @Column(name = "KEY_REQUIRED", nullable = false)
    @Type(type = "yes_no")
    public boolean isKeyRequired()
    {
        return keyRequired;
    }

    public void setKeyRequired(boolean keyRequired)
    {
        this.keyRequired = keyRequired;
    }

    /** 
     *            @hibernate.many-to-one
     *            @hibernate.column name="ARCH_BY"         
     *         
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ARCH_BY", nullable = true)
    public User getArchBy()
    {
        return this.archBy;
    }

    public void setArchBy(User archBy)
    {
        this.archBy = archBy;
    }

    /** 
     *            @hibernate.property
     *             column="ARCH_DATE"
     *             length="23"
     *         
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "ARCH_DATE", nullable = true)
    public Date getArchDate()
    {
        return this.archDate;
    }

    public void setArchDate(Date archDate)
    {
        this.archDate = archDate;
    }

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
     *            @hibernate.bag
     *             lazy="true"
     *             inverse="true"
     *             cascade="none"
     *            @hibernate.collection-key
     *             column="BUILDING_CLASSIFICATION_ID"
     *            @hibernate.collection-many-to-many
     *             class="au.gov.qld.fire.jms.domain.refdata.BuildingClassification"
     *         
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "BUILDING_BCLASSIFICATION",
        joinColumns = @JoinColumn(name = "BUILDING_ID", referencedColumnName = "BUILDING_ID"),
        inverseJoinColumns = @JoinColumn(name = "BUILDING_CLASSIFICATION_ID", referencedColumnName = "BUILDING_CLASSIFICATION_ID"))
    //@WhereJoinTable(clause = "LOGICALLY_DELETED IS NULL")
	public List<BuildingClassification> getClassifications()
	{
    	if (classifications == null)
    	{
    		classifications = new ArrayList<BuildingClassification>();
    	}
		return classifications;
	}

	public void setClassifications(List<BuildingClassification> classifications)
	{
		this.classifications = classifications;
	}

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "ADDRESS_ID", nullable = false)
    @JsonProperty
    public Address getAddress()
    {
        if (address == null)
        {
            address = new Address();
        }
        return this.address;
    }

    public void setAddress(Address address)
    {
        this.address = address;
    }

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "NEXT_BUILDING_ID", nullable = true)
    public Building getNextBuilding()
    {
        return this.nextBuilding;
    }

    public void setNextBuilding(Building nextBuilding)
    {
        this.nextBuilding = nextBuilding;
    }

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "ESCAD_BUILDING_ID", nullable = true)
	public BuildingAlt getEscadBuilding()
	{
		return escadBuilding;
	}

	public void setEscadBuilding(BuildingAlt escadBuilding)
	{
		this.escadBuilding = escadBuilding;
	}

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "MASTERMIND_BUILDING_ID", nullable = true)
	public BuildingAlt getMastermindBuilding()
	{
		return mastermindBuilding;
	}

	public void setMastermindBuilding(BuildingAlt mastermindBuilding)
	{
		this.mastermindBuilding = mastermindBuilding;
	}

}