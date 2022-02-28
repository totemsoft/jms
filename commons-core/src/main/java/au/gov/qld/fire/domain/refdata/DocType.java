package au.gov.qld.fire.domain.refdata;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.ArrayUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import au.gov.qld.fire.domain.Auditable;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Entity
@Table(name = "DOC_TYPE")
@Cache(region = "au.gov.qld.fire.domain.refdata.DocType", usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class DocType extends Auditable<Long>
{

    /** serialVersionUID */
    private static final long serialVersionUID = -3883899326526919808L;

    /**
     * DOC_TYPE_IGNORE
     */
    protected static final String[] DOC_TYPE_IGNORE = (String[]) ArrayUtils.addAll(Auditable.IGNORE,
        new String[] {"docTypeId"});

    private String name;

    /**
     * 
     */
    public DocType()
    {
        super();
    }

    /**
     * @param id
     */
    public DocType(Long id)
    {
        super(id);
    }

    /** 
     *            @hibernate.id
     *             generator-class="assigned"
     *             type="java.lang.Long"
     *             column="DOC_TYPE_ID"
     *         
     */
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "DOC_TYPE_ID")
    public Long getDocTypeId()
    {
        return super.getId();
    }

    public void setDocTypeId(Long docTypeId)
    {
        super.setId(docTypeId);
    }

    /** 
     *            @hibernate.property
     *             column="NAME"
     *             length="50"
     *             not-null="true"
     *         
     */
    @Column(name = "NAME")
    public String getName()
    {
        return this.name;
    }

    public void setName(String description)
    {
        this.name = description;
    }

}