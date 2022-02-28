package au.gov.qld.fire.jms.domain.building;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonProperty;

import au.gov.qld.fire.domain.Auditable;
import au.gov.qld.fire.domain.location.Address;
import au.gov.qld.fire.jms.domain.file.File;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Entity
@Table(name = "BUILDING_ALT")
//@Cache(region = "au.gov.qld.fire.jms.domain.building.BuildingAlt", usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class BuildingAlt extends Auditable<Long>
{

	/** serialVersionUID */
	private static final long serialVersionUID = -3684407482522943997L;

    private File file;

    private String name;

    private Address address;

    private Character type; // TODO: use enum

    /**
     * 
     */
    public BuildingAlt()
    {
        super();
    }

    /**
     * @param id
     */
    public BuildingAlt(Long id)
    {
        super(id);
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.domain.BaseModel#getId()
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BUILDING_ALT_ID")
    public Long getBuildingAltId()
    {
        return super.getId();
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.domain.BaseModel#setId(java.lang.Object)
     */
    public void setBuildingAltId(Long buildingAltId)
    {
        super.setId(buildingAltId);
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

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "ADDRESS_ID", nullable = true)
    @JsonProperty
    public Address getAddress()
    {
        return this.address;
    }

    public void setAddress(Address address)
    {
        this.address = address;
    }

    @Column(name = "TYPE", nullable = false, length = 1)
    @JsonProperty
	public Character getType()
	{
		return type;
	}

	public void setType(Character type)
	{
		this.type = type;
	}

}