package au.gov.qld.fire.jms.domain.file;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import au.gov.qld.fire.domain.Auditable;
import au.gov.qld.fire.domain.document.Document;
import au.gov.qld.fire.domain.refdata.AuditStatusEnum;

@Entity
@Table(name = "FILE_AUDIT")
@Audited
public class FileAudit extends Auditable<Long>
{

    /** serialVersionUID */
	private static final long serialVersionUID = -8458192280317065202L;

    private File file; // PK

	private String name;

    private String description;

    private byte[] content;

    private Document document;

    private AuditStatusEnum status = AuditStatusEnum.ACTIVE;

    public FileAudit()
    {
		super();
	}

	public FileAudit(Long id)
	{
		super(id);
	}

	public FileAudit(File file)
	{
		setFile(file);
	}

	/* (non-Javadoc)
     * @see au.gov.qld.fire.jms.domain.BaseModel#getId()
     */
    @Override
    @Id
    @Column(name = "FILE_ID")
    public Long getId()
    {
        return super.getId();
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FILE_ID", nullable = false, insertable = false, updatable = false)
    //@NotAudited
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    public File getFile()
    {
        return this.file;
    }

    public void setFile(File file)
    {
        this.file = file;
        setId(file.getId());
    }

    @Column(name = "NAME", nullable = true, length = 100)
    public String getName()
    {
        return this.name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Column(name = "DESCRIPTION", nullable = true, length = 255)
    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    @Lob() @Basic(fetch = FetchType.LAZY)
    @Column(name = "CONTENT", nullable = false, columnDefinition = "IMAGE")
    @org.hibernate.annotations.Type(type = "au.gov.qld.fire.dao.hibernate.BlobByteArrayType")
    public byte[] getContent()
	{
		return content;
	}

    public void setContent(byte[] content)
	{
		this.content = content;
	}

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "DOCUMENT_ID", nullable = true)
    //@NotAudited
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	public Document getDocument()
    {
		return document;
	}

	public void setDocument(Document document)
	{
		this.document = document;
	}

    @Enumerated
    @Column(name = "AUDIT_STATUS_ID", nullable = false)
	public AuditStatusEnum getStatus()
	{
		return status;
	}

	public void setStatus(AuditStatusEnum status)
	{
		this.status = status;
	}

	@Transient
	public boolean isActive()
	{
		return AuditStatusEnum.ACTIVE.equals(getStatus());
	}
	@Transient
	public boolean isApproved()
	{
		return AuditStatusEnum.APPROVED.equals(getStatus());
	}
	@Transient
	public boolean isRejected()
	{
		return AuditStatusEnum.REJECTED.equals(getStatus());
	}

}