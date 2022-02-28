package au.gov.qld.fire.domain.document;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import au.gov.qld.fire.domain.Auditable;
import au.gov.qld.fire.domain.refdata.ContentTypeEnum;

/**
 * @hibernate.class table="DOCUMENT" dynamic-update="true"
 *
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Entity
@Table(name = "DOCUMENT")
public class Document extends Auditable<Long> implements IDocument
{

    /** serialVersionUID */
    private static final long serialVersionUID = 6703484059070755463L;

    public static final String[] IGNORE = (String[]) ArrayUtils.addAll(
        Auditable.IGNORE, new String[] {"documentId"});

    private String name;

    private String description;

    private byte[] content;

    private String contentType;

    /**
     * 
     */
    public Document()
    {
        super();
    }

    public Document(Long id)
    {
        super(id);
    }

    public Document(String name, byte[] content, String contentType)
    {
		this.name = name;
		this.content = content;
		this.contentType = contentType;
	}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DOCUMENT_ID")
    public Long getDocumentId()
    {
        return super.getId();
    }

    public void setDocumentId(Long documentId)
    {
        super.setId(documentId);
    }

    /** 
     *            @hibernate.property
     *             column="NAME"
     *             length="100"
     *             not-null="true"
     *         
     */
    @Column(name = "NAME", nullable = false)
    public String getName()
    {
        if (StringUtils.isBlank(name)) {
            name = "TBD";
        }
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
    @Column(name = "DESCRIPTION", nullable = true)
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
//    	if (ArrayUtils.isEmpty(content))
//    	{
//    		content = null;
//    	}
        return this.content;
    }

    public void setContent(byte[] content)
    {
        this.content = content;
    }

    /**
     * Calculated property
     * @return
     */
    @Transient
    public int getContentLength()
    {
        return content == null ? 0 : content.length;
    }

    public void setContentLength(int contentLength)
    {
        //do nothing
    }

    /** 
     *            @hibernate.property
     *             column="CONTENT_TYPE"
     *             length="60"
     *         
     */
    @Column(name = "CONTENT_TYPE")
    public String getContentType()
    {
        return this.contentType;
    }

    public void setContentType(String contentType)
    {
        this.contentType = contentType;
    }

    @Transient
    public ContentTypeEnum getContentTypeEnum()
    {
    	return ContentTypeEnum.findByContentType(contentType);
    }

}