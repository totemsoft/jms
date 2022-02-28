package au.gov.qld.fire.domain.report;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang.ArrayUtils;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import au.gov.qld.fire.domain.document.BaseDoc;
import au.gov.qld.fire.domain.document.Template;

/**
 * @hibernate.class table="REPORT_DOC" dynamic-update="true"
 *
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Entity
@Table(name = "REPORT_DOC")
public class ReportDoc extends BaseDoc
{

    /** serialVersionUID */
    private static final long serialVersionUID = -1749794714491866993L;

    /**
     * REPORT_DOC_IGNORE
     */
    public static final String[] IGNORE = (String[]) ArrayUtils.addAll(BaseDoc.IGNORE,
        new String[] {"reportDocId", "template"});

    private Template template;

    /**
     * 
     */
    public ReportDoc()
    {
        super();
    }

    /**
     * @param id
     */
    public ReportDoc(Long id)
    {
        super(id);
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.domain.BaseModel#getId()
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REPORT_DOC_ID")
    public Long getReportDocId()
    {
        return super.getId();
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.domain.BaseModel#setId(java.lang.Object)
     */
    public void setReportDocId(Long reportDocId)
    {
        super.setId(reportDocId);
    }

    /** 
     *            @hibernate.many-to-one
     *            @hibernate.column name="TEMPLATE_ID"         
     *         
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEMPLATE_ID")
    @NotFound(action = NotFoundAction.IGNORE) // template can be logically deleted
    public Template getTemplate()
    {
        return template;
    }

    public void setTemplate(Template template)
    {
        this.template = template;
    }

}