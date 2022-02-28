package au.gov.qld.fire.jms.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.ArrayUtils;
import org.hibernate.annotations.Type;

import au.gov.qld.fire.domain.Auditable;
import au.gov.qld.fire.domain.BasePair;
import au.gov.qld.fire.domain.location.Area;
import au.gov.qld.fire.domain.location.Region;
import au.gov.qld.fire.domain.location.Station;
import au.gov.qld.fire.jms.domain.file.File;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Entity
@Table(name = "FCA")
@org.hibernate.annotations.GenericGenerator(name = "assigned-strategy", strategy = "assigned")
public class Fca extends Auditable<String>
{

    /** serialVersionUID */
    private static final long serialVersionUID = 8251558007575453514L;

    /** IGNORE */
    public static final String[] IGNORE = (String[]) ArrayUtils.addAll(Auditable.IGNORE,
        new String[] {"fcaId", "region", "area", "station", "file", "parent"});

    private Region region;

    private Area area;

    private Station station;

    private Long fileId;

    private File file;

    private boolean subPanel;

    private Fca parent;

    private List<Fca> children;

    /** TODO: make persistent field */
    @Transient
    private String comment;

    /**
     * 
     */
    public Fca()
    {
        super();
    }

    /**
     * @param id
     */
    public Fca(String id)
    {
        super(id);
    }

    @Id
    @GeneratedValue(generator = "assigned-strategy")
    @Column(name = "FCA_NO")
    public String getFcaId()
    {
        return super.getId();
    }

    public void setFcaId(String fcaId)
    {
        super.setId(fcaId);
    }

    /** 
     *            @hibernate.many-to-one
     *             not-null="true"
     *            @hibernate.column name="REGION_ID"         
     *         
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REGION_ID", nullable = true)
    public Region getRegion()
    {
        return this.region;
    }

    public void setRegion(Region region)
    {
        this.region = region;
    }

    @Column(name = "FILE_ID", nullable = true, insertable = false, updatable = false)
	public Long getFileId() {
		return fileId;
	}

	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FILE_ID", nullable = true)
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
     *            @hibernate.column name="AREA_ID"         
     *         
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AREA_ID", nullable = true)
    public Area getArea()
    {
        return area;
    }

    public void setArea(Area area)
    {
        this.area = area;
    }

    /** 
     *            @hibernate.many-to-one
     *             not-null="true"
     *            @hibernate.column name="STATION_CODE"         
     *         
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STATION_CODE", nullable = true)
    public Station getStation()
    {
        return this.station;
    }

    public void setStation(Station station)
    {
        this.station = station;
    }

    @Column(name = "SUB_PANEL", nullable = false)
    @Type(type = "yes_no")
    public boolean isSubPanel()
    {
        return subPanel;
    }

    public void setSubPanel(boolean subPanel)
    {
        this.subPanel = subPanel;
    }

    /**
	 * @return the parent
	 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_FCA_NO", nullable = true)
	public Fca getParent()
	{
		return parent;
	}

	public void setParent(Fca parent)
	{
		this.parent = parent;
	}

	/**
	 * @return the children
	 */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
	public List<Fca> getChildren()
	{
		if (children == null)
		{
			children = new ArrayList<Fca>();
		}
		return children;
	}

	public void setChildren(List<Fca> children)
	{
		this.children = children;
	}

    @Transient
    public String getComment()
    {
        return comment;
    }

    public void setComment(String comment)
    {
        this.comment = comment;
    }

    /**
     * 
     * @param fca
     * @return
     */
    public static BasePair toBasePair(Fca fca)
    {
		return fca == null ? new BasePair() : new BasePair(fca.getFile() == null ? null : fca.getFile().getId(), fca.getId());
    }

}