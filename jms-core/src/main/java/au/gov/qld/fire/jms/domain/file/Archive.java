package au.gov.qld.fire.jms.domain.file;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.ArrayUtils;

import au.gov.qld.fire.domain.Auditable;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Entity
@Table(name = "ARCHIVE")
public class Archive extends Auditable<Long>
{

    /** serialVersionUID */
	private static final long serialVersionUID = -8607612718961129119L;

	/** IGNORE */
    public static final String[] IGNORE = (String[]) ArrayUtils.addAll(
        Auditable.IGNORE, new String[] {"archiveId", "files"});

    private String code;

    private String name;

    private String location;

    private String description;

    private Date dateFrom;

    private Date dateTo;

    private List<FileArchive> files;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ARCHIVE_ID")
    public Long getArchiveId()
    {
        return super.getId();
    }

    public void setArchiveId(Long archiveId)
    {
    	super.setId(archiveId);
    }

    @Column(name = "BOX_CODE", nullable = false, unique = true)
	public String getCode()
    {
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

    @Column(name = "BOX_NAME", nullable = false)
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

    @Column(name = "BOX_LOCATION")
	public String getLocation()
	{
		return location;
	}

	public void setLocation(String location)
	{
		this.location = location;
	}

    @Column(name = "BOX_DESCRIPTION")
	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_FROM", nullable = false)
	public Date getDateFrom()
	{
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom)
	{
		this.dateFrom = dateFrom;
	}

    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_TO", nullable = true)
	public Date getDateTo()
	{
		return dateTo;
	}

	public void setDateTo(Date dateTo)
	{
		this.dateTo = dateTo;
	}

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "archive")
	public List<FileArchive> getFiles()
	{
        if (files == null)
        {
        	files = new ArrayList<FileArchive>();
        }
		return files;
	}

	public void setFiles(List<FileArchive> files)
	{
		this.files = files;
	}

}