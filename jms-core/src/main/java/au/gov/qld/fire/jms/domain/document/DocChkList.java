package au.gov.qld.fire.jms.domain.document;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Type;

import au.gov.qld.fire.domain.Auditable;
import au.gov.qld.fire.domain.document.Template;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Entity
@Table(name = "DOC_CHK_LIST")
public class DocChkList extends Auditable<Long>
{

    /** serialVersionUID */
    private static final long serialVersionUID = 5444652973550090503L;

    /** TEMPLATE property name */
    public static final String TEMPLATE = "template";

    private Date startDate;

    private Date endDate;

    private Template template;

    public DocChkList()
    {
        super();
    }

    public DocChkList(Long id)
    {
        super(id);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DOC_CHK_LIST_ID")
    public Long getDocChkListId()
    {
        return super.getId();
    }

    public void setDocChkListId(Long id)
    {
        super.setId(id);
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.domain.BaseModel#isActive()
     */
    @Override
    @Column(name = "ACTIVE", nullable = false)
    @Type(type = "yes_no")
    public boolean isActive()
    {
        return super.isActive();
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.domain.BaseModel#setActive(boolean)
     */
    @Override
    public void setActive(boolean active)
    {
        super.setActive(active);
    }

    /** 
     *            @hibernate.property
     *             column="START_DATE"
     *             length="23"
     *             not-null="true"
     *         
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "START_DATE", nullable = true)
    public Date getStartDate()
    {
        return this.startDate;
    }

    public void setStartDate(Date startDate)
    {
        this.startDate = startDate;
    }

    /** 
     *            @hibernate.property
     *             column="END_DATE"
     *             length="23"
     *             not-null="true"
     *         
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "END_DATE", nullable = true)
    public Date getEndDate()
    {
        return this.endDate;
    }

    public void setEndDate(Date endDate)
    {
        this.endDate = endDate;
    }

    /** 
     *            @hibernate.many-to-one
     *             not-null="true"
     *            @hibernate.column name="TEMPLATE_ID"         
     *         
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEMPLATE_ID", nullable = false)
    @NotFound(action = NotFoundAction.IGNORE) // template can be logically deleted
    public Template getTemplate()
    {
        if (template == null)
        {
            template = new Template();
        }
        return this.template;
    }

    public void setTemplate(Template template)
    {
        this.template = template;
    }

}