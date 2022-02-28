package au.gov.qld.fire.jms.domain.building;

import java.util.Comparator;

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
import javax.persistence.Transient;

import org.apache.commons.lang.ArrayUtils;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.annotations.Type;

import au.gov.qld.fire.domain.Auditable;
import au.gov.qld.fire.domain.document.Document;
import au.gov.qld.fire.domain.entity.Company;
import au.gov.qld.fire.domain.entity.Contact;
import au.gov.qld.fire.jms.domain.file.File;
import au.gov.qld.fire.jms.domain.refdata.BuildingContactType;
import au.gov.qld.fire.jms.domain.refdata.BuildingContactTypeEnum;

/**
 * @hibernate.class table="BUILDING_CONTACT" dynamic-update="true"
 */
@Entity
@Table(name = "BUILDING_CONTACT")
public class BuildingContact extends Auditable<Long>
{

    /** serialVersionUID */
    private static final long serialVersionUID = -7943246077402270651L;

    public static final String[] IGNORE = (String[]) ArrayUtils.addAll(
        Auditable.IGNORE, new String[] {"buildingContactId", "file", "buildingContactType", "company", "contact", "document"});

    private int priority;

    private File file;

    private BuildingContactType buildingContactType;

    private Contact contact;

    private Company company;

    private Document document;

    private String notes;

    public BuildingContact()
    {
        super();
    }

    public BuildingContact(Long id)
    {
        super(id);
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.domain.BaseModel#getId()
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BUILDING_CONTACT_ID")
    public Long getBuildingContactId()
    {
        return super.getId();
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.domain.BaseModel#setId(java.lang.Object)
     */
    public void setBuildingContactId(Long id)
    {
        super.setId(id);
    }

    /**
	 * @return the priority
	 */
    @Column(name = "PRIORITY", nullable = false)
    @JsonProperty
	public int getPriority()
    {
		return priority;
	}

    public void setPriority(int priority)
    {
		this.priority = priority;
	}

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.domain.BaseModel#isActive()
     */
    @Override
    @Column(name = "ACTIVE", nullable = false)
    @Type(type = "yes_no")
    @JsonProperty
    public boolean isActive()
    {
        return super.isActive();
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.domain.BaseModel#setActive(boolean)
     */
    @Override
    public void setActive(boolean active)
    {
        super.setActive(active);
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BUILDING_CONTACT_TYPE_ID", nullable = false)
    @JsonProperty
    public BuildingContactType getBuildingContactType()
    {
        return this.buildingContactType;
    }

    public void setBuildingContactType(BuildingContactType buildingContactType)
    {
        this.buildingContactType = buildingContactType;
    }

    /**
     * Calculated property.
     * @return
     */
    @Transient
    public boolean isOwner()
    {
        return buildingContactType != null
            && BuildingContactTypeEnum.OWNER.getId().equals(buildingContactType.getId());
    }
    @Deprecated
    @Transient
    public boolean isDaytime()
    {
        return buildingContactType != null
            && BuildingContactTypeEnum.DAYTIME.getId().equals(buildingContactType.getId());
    }
    @Deprecated
    @Transient
    public boolean isAfterHours()
    {
        return buildingContactType != null
            && BuildingContactTypeEnum.AFTER_HOURS.getId().equals(buildingContactType.getId());
    }
    @Transient
    public boolean isSecurity()
    {
        return buildingContactType != null
            && BuildingContactTypeEnum.SECURITY.getId().equals(buildingContactType.getId());
    }
    @Transient
    public boolean isFire()
    {
        return buildingContactType != null
            && BuildingContactTypeEnum.FIRE.getId().equals(buildingContactType.getId());
    }

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "CONTACT_ID", nullable = false)
    //@org.hibernate.annotations.Fetch(FetchMode.JOIN)
    @JsonProperty
    public Contact getContact()
    {
    	if (contact == null)
    	{
    		contact = new Contact();
    	}
        return contact;
    }

    public void setContact(Contact contact)
    {
        this.contact = contact;
    }

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL) // no Company UI
    @JoinColumn(name = "COMPANY_ID", nullable = true)
    //@org.hibernate.annotations.Fetch(FetchMode.JOIN)
    @JsonProperty
    public Company getCompany()
    {
		return company;
	}

	public void setCompany(Company company)
	{
		this.company = company;
	}

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "DOCUMENT_ID")
	public Document getDocument()
    {
		return document;
	}

	public void setDocument(Document document)
	{
		this.document = document;
	}

    @Column(name = "NOTES")
	public String getNotes()
    {
		return notes;
	}

	public void setNotes(String notes)
	{
		this.notes = notes;
	}

	/**
	 * Sort by priority
	 */
	public static class BuildingContactComparator implements Comparator<BuildingContact>
	{
		private static final BuildingContactComparator instance = new BuildingContactComparator();
		public static final BuildingContactComparator getInstance() {return instance;}
		public int compare(BuildingContact bc1, BuildingContact bc2) {
			if (bc1.getPriority() == 0) return 1;
			if (bc2.getPriority() == 0) return -1;
			return bc1.getPriority() - bc2.getPriority();
		}
	}

}