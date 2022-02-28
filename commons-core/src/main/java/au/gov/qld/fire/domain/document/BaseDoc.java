package au.gov.qld.fire.domain.document;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.ArrayUtils;

import au.gov.qld.fire.domain.Auditable;
import au.gov.qld.fire.domain.refdata.DocType;
import au.gov.qld.fire.domain.refdata.DocTypeEnum;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@MappedSuperclass
public abstract class BaseDoc extends Auditable<Long>
{

    /** serialVersionUID */
    private static final long serialVersionUID = -1486389704087019992L;

    /**
     * BASE_DOC_IGNORE
     */
    public static final String[] IGNORE = (String[]) ArrayUtils.addAll(Auditable.IGNORE,
        new String[] {"docType", "document"});

    private DocType docType;

    private String name;

    private String description;

    private String location;

    private Document document;

    private Date uploadDate;

    /**
     * 
     */
    protected BaseDoc()
    {
        super();
    }

    /**
     * @param id
     */
    protected BaseDoc(Long id)
    {
        super(id);
    }

    /** 
     *            @hibernate.property
     *             column="NAME"
     *             length="100"
     *         
     */
    @Column(name = "NAME")
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    /** 
     *            @hibernate.property
     *             column="DESCRIPTION"
     *             length="255"
     *         
     */
    @Column(name = "DESCRIPTION")
    public String getDescription()
    {
        return this.description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    /** 
     *            @hibernate.property
     *             column="LOCATION"
     *             length="255"
     *         
     */
    @Column(name = "LOCATION")
    public String getLocation()
    {
        return this.location;
    }

    public void setLocation(String location)
    {
        this.location = location;
    }

    /** 
     *            @hibernate.many-to-one
     *             not-null="true"
     *            @hibernate.column name="DOC_TYPE_ID"         
     *         
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DOC_TYPE_ID")
    public DocType getDocType()
    {
        if (docType == null)
        {
            docType = new DocType();
        }
        return this.docType;
    }

    public void setDocType(DocType docType)
    {
        this.docType = docType;
    }

    public void setDocTypeEnum(DocTypeEnum docTypeEnum)
    {
        this.docType = new DocType(docTypeEnum.getId());
    }

    /** 
     *            @hibernate.property
     *             column="UPLOAD_DATE"
     *             length="23"
     *         
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPLOAD_DATE")
    public Date getUploadDate()
    {
        return this.uploadDate;
    }

    public void setUploadDate(Date uploadDate)
    {
        this.uploadDate = uploadDate;
    }

    /** 
     *            @hibernate.many-to-one
     *             not-null="true"
     *            @hibernate.column name="DOCUMENT_ID"         
     *         
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "DOCUMENT_ID")
    public Document getDocument()
    {
        return document;
    }

    public void setDocument(Document document)
    {
        this.document = document;
    }

}