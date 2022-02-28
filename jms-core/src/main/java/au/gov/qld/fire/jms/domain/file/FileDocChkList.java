package au.gov.qld.fire.jms.domain.file;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;

import au.gov.qld.fire.domain.Auditable;
import au.gov.qld.fire.jms.domain.document.DocChkList;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Entity
@IdClass(FileDocChkListPK.class)
@Table(name = "FILE_DOC_CHK_LIST")
public class FileDocChkList extends Auditable<FileDocChkListPK>
{

    /** serialVersionUID */
    private static final long serialVersionUID = 5568298213059369294L;

    /** FILE property name */
    public static final String FILE = "file";

    /** DOC_CHK_LIST property name */
    public static final String DOC_CHK_LIST = "docChkList";

    /** identifier field */
    private Long fileId;

    /** identifier field */
    private Long docChkListId;

    private boolean docOnFile;

    private File file;

    private DocChkList docChkList;

    public FileDocChkList()
    {
        setId(new FileDocChkListPK());
    }

    public FileDocChkList(FileDocChkListPK id)
    {
        super(id);
    }

    @Transient
    public FileDocChkListPK getId()
    {
        if (super.getId() == null)
        {
            super.setId(new FileDocChkListPK(getFileId(), getDocChkListId()));
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
	@Column(name = "DOC_CHK_LIST_ID", nullable = false)
    public Long getDocChkListId()
    {
        return this.docChkListId;
    }

    public void setDocChkListId(Long docChkListId)
    {
        this.docChkListId = docChkListId;
    }

    /** 
     *            @hibernate.property
     *             column="DOC_ON_FILE"
     *             length="1"
     *             not-null="true"
     *         
     */
    @Column(name = "DOC_ON_FILE", nullable = false)
    @Type(type = "yes_no")
    public boolean isDocOnFile()
    {
        return this.docOnFile;
    }

    public void setDocOnFile(boolean docOnFile)
    {
        this.docOnFile = docOnFile;
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
        if (getId().getFileId() == null && file != null)
        {
            getId().setFileId(file.getFileId());
        }
        this.file = file;
    }

    /** 
     *            @hibernate.many-to-one
     *             update="false"
     *             insert="false"
     *         
     *            @hibernate.column
     *             name="DOC_CHK_LIST_ID"
     *         
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DOC_CHK_LIST_ID", nullable = false, insertable = false, updatable = false)
    public DocChkList getDocChkList()
    {
        if (docChkList == null)
        {
            docChkList = new DocChkList();
        }
        return this.docChkList;
    }

    public void setDocChkList(DocChkList docChkList)
    {
        if (getId().getDocChkListId() == null && docChkList != null)
        {
            getId().setDocChkListId(docChkList.getDocChkListId());
        }
        this.docChkList = docChkList;
    }

}