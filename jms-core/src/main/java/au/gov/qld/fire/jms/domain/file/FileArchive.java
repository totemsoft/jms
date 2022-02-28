package au.gov.qld.fire.jms.domain.file;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.lang.ArrayUtils;

import au.gov.qld.fire.domain.Auditable;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Entity
@Table(name = "FILE_ARCHIVE")
@org.hibernate.annotations.GenericGenerator(name = "assigned-strategy", strategy = "assigned")
public class FileArchive extends Auditable<Long>
{

    /** serialVersionUID */
	private static final long serialVersionUID = 6224128566782762822L;

	/** IGNORE */
    public static final String[] IGNORE = (String[]) ArrayUtils.addAll(
        Auditable.IGNORE, new String[] {"fileArchiveId", "file", "archive"});

    private File file;

    private Archive archive;

    public FileArchive()
    {
		super();
	}

	public FileArchive(Long fileArchiveId)
	{
		super(fileArchiveId);
	}

	public FileArchive(Long fileArchiveId, Archive archive)
	{
		super(fileArchiveId);
		this.archive = archive;
	}

	@Id
    //@GeneratedValue(strategy = GenerationType.TABLE)
    @GeneratedValue(generator = "assigned-strategy")
	@Column(name = "FILE_ID")
    public Long getFileArchiveId()
    {
        return super.getId();
    }

    public void setFileArchiveId(Long fileArchiveId)
    {
    	super.setId(fileArchiveId);
    }

    @OneToOne
	@JoinColumn(name = "FILE_ID", insertable = false, updatable = false)
    public File getFile()
    {
        return file;
    }

    public void setFile(File file)
    {
		this.file = file;
    }

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ARCHIVE_ID", nullable = false)
	public Archive getArchive()
	{
		return archive;
	}

	public void setArchive(Archive archive)
	{
		this.archive = archive;
	}

}