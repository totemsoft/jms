package au.gov.qld.fire.domain.refdata;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import au.gov.qld.fire.domain.Auditable;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Entity
@Table(name = "TEMPLATE_TYPE")
@Cache(region = "au.gov.qld.fire.domain.refdata.TemplateType", usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class TemplateType extends Auditable<Long>
{

    /** serialVersionUID */
    private static final long serialVersionUID = -3911827863094796046L;

    private String name;

    /**
     * 
     */
    public TemplateType()
    {
        super();
    }

    /**
     * @param id
     */
    public TemplateType(Long id)
    {
        super(id);
    }

    /** 
     *            @hibernate.id
     *             generator-class="sequence"
     *             type="java.lang.Long"
     *             column="TEMPLATE_TYPE_ID"
     *         
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TEMPLATE_TYPE_ID")
    public Long getTemplateTypeId()
    {
        return super.getId();
    }

    public void setTemplateTypeId(Long templateTypeId)
    {
        super.setId(templateTypeId);
    }

    /** 
     *            @hibernate.property
     *             column="NAME"
     *             length="255"
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
     * Check if this is letter type (used in JSP).
     * @return
     */
    @Transient
    public boolean isLetter()
    {
        return TemplateTypeEnum.LETTER.getId().equals(getId());
    }

    /**
     * Check if this is fax type (used in JSP).
     * @return
     */
    @Transient
    public boolean isFax()
    {
        return TemplateTypeEnum.FAX.getId().equals(getId());
    }

    /**
     * Check if this is email type (used in JSP).
     * @return
     */
    @Transient
    public boolean isEmail()
    {
        return TemplateTypeEnum.EMAIL.getId().equals(getId());
    }

    /**
     * Check if this is report type (used in JSP)
     * eg /WEB-INF/module/setup/template/edit.jsp to display edit sql panel
     * @return
     */
    @Transient
    public boolean isReportFOP()
    {
        return TemplateTypeEnum.REPORT_FOP.getId().equals(getId());
    }
    @Transient
    public boolean isReportCrystal()
    {
        return TemplateTypeEnum.REPORT_CRYSTAL.getId().equals(getId());
    }

}