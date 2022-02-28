package au.gov.qld.fire.domain.document;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang.ArrayUtils;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;

import au.gov.qld.fire.domain.Auditable;
import au.gov.qld.fire.domain.IAuditable;
import au.gov.qld.fire.domain.refdata.ContentTypeEnum;
import au.gov.qld.fire.domain.refdata.TemplateType;
import au.gov.qld.fire.domain.refdata.TemplateTypeEnum;

/**
 * @hibernate.class table="TEMPLATE" dynamic-update="true"
 *
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Entity
@Table(name = "TEMPLATE")
@Where(clause = IAuditable.WHERE)
public class Template extends Auditable<Long>
{

    /** serialVersionUID */
    private static final long serialVersionUID = 3392797500032317282L;

    /** IGNORE */
    public static final String[] IGNORE = (String[]) ArrayUtils.addAll(Auditable.IGNORE,
        new String[] {"templateId", "templateType", "config"});

    private TemplateType templateType;

    private String code;

    private String name;

    private String description;

    private String location;

    private byte[] content;

    private String contentType;

    private Date uploadDate;

    private String sqlQuery;

    private Document config;

    /**
     * 
     */
    public Template()
    {
        super();
    }

    /**
     * @param id
     */
    public Template(Long id)
    {
        super(id);
    }

    /** 
     *            @hibernate.id
     *             generator-class="sequence"
     *             type="java.lang.Long"
     *             column="TEMPLATE_ID"
     *         
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TEMPLATE_ID")
    public Long getTemplateId()
    {
        return super.getId();
    }

    public void setTemplateId(Long templateId)
    {
        super.setId(templateId);
    }

    /* (non-Javadoc)
	 * @see au.gov.qld.fire.domain.BaseModel#getLogicallyDeleted()
	 */
    @Override
    @Column(name = "LOGICALLY_DELETED", nullable = true)
    @Type(type = "yes_no")
	protected Boolean getLogicallyDeleted()
    {
		return super.getLogicallyDeleted();
	}

	/* (non-Javadoc)
     * @see au.gov.qld.fire.jms.domain.BaseModel#setLogicallyDeleted(java.lang.Boolean)
     */
    @Override
    public void setLogicallyDeleted(Boolean logicallyDeleted)
    {
        super.setLogicallyDeleted(logicallyDeleted);
    }

    /** 
     *            @hibernate.property
     *             column="CODE"
     *             length="10"
     *         
     */
    @Column(name = "CODE")
    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    /** 
     *            @hibernate.property
     *             column="NAME"
     *             length="100"
     *             not-null="true"
     *         
     */
    @Column(name = "NAME")
    public String getName()
    {
        return this.name;
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

    @Lob() @Basic(fetch = FetchType.LAZY)
    @Column(name = "CONTENT", nullable = true, columnDefinition = "IMAGE")
    @org.hibernate.annotations.Type(type = "au.gov.qld.fire.dao.hibernate.BlobByteArrayType")
    public byte[] getContent()
    {
        return this.content;
    }

    public void setContent(byte[] content)
    {
        this.content = content;
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

    /** 
     *            @hibernate.many-to-one
     *             not-null="true"
     *            @hibernate.column name="TEMPLATE_TYPE_ID"         
     *         
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEMPLATE_TYPE_ID")
    public TemplateType getTemplateType()
    {
        if (templateType == null)
        {
            templateType = new TemplateType();   
        }
        return this.templateType;
    }

    public void setTemplateType(TemplateType templateType)
    {
        this.templateType = templateType;
    }

    @Transient
    public TemplateTypeEnum getTemplateTypeEnum()
    {
    	return templateType == null ? null : TemplateTypeEnum.findByTemplateTypeId(templateType.getId());
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
     * @return the sqlQuery
     */
    @Column(name = "SQL_QUERY")
    public String getSqlQuery()
    {
        return sqlQuery;
    }

    public void setSqlQuery(String sqlQuery)
    {
        this.sqlQuery = sqlQuery;
    }

    @OneToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "CONFIG_DOCUMENT_ID", unique = true, nullable = true)
	public Document getConfig()
	{
		return config;
	}

	public void setConfig(Document config)
	{
		this.config = config;
	}

}