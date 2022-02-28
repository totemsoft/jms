package au.gov.qld.fire.domain.security;

import java.util.Comparator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.builder.ToStringBuilder;

import au.gov.qld.fire.domain.Auditable;
import au.gov.qld.fire.domain.BaseModel;

/**
 * @hibernate.class table="SYSTEM_FUNCTION" dynamic-update="true"
 *
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Entity
@Table(name = "SYSTEM_FUNCTION")
public class SystemFunction extends BaseModel<Long>
{

    /** serialVersionUID */
    private static final long serialVersionUID = 4329326466970105618L;

    /**
     * IGNORE
     */
    public static final String[] IGNORE = (String[]) ArrayUtils.addAll(Auditable.IGNORE,
        new String[] {"systemFunctionId"});

    private String module;

    private String name;

    private String query;

    private String description;

    /**
     * 
     */
    public SystemFunction()
    {
        super();
    }

    /**
     * @param id
     */
    public SystemFunction(Long id)
    {
        super(id);
    }

    /** 
     *            @hibernate.id
     *             generator-class="assigned"
     *             type="java.lang.Long"
     *             column="SYSTEM_FUNCTION_ID"
     *         
     */
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "SYSTEM_FUNCTION_ID")
    public Long getSystemFunctionId()
    {
        return super.getId();
    }

    public void setSystemFunctionId(Long id)
    {
        super.setId(id);
    }

    /** 
     *            @hibernate.property
     *             column="MODULE"
     *             length="50"
     *         
     */
    @Column(name = "MODULE")
    public String getModule()
    {
        return module;
    }

    public void setModule(String module)
    {
        this.module = module;
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
     *             column="QUERY"
     *             length="255"
     *         
     */
    @Column(name = "QUERY")
    public String getQuery()
    {
        return query;
    }

    public void setQuery(String query)
    {
        this.query = query;
    }

    /** 
     *            @hibernate.property
     *             column="DESCRIPTION"
     *             length="255"
     *             not-null="true"
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

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.domain.BaseModel#toString()
     */
    @Override
    public String toString()
    {
        return new ToStringBuilder(this).append("id", getId()).append("module", getModule())
            .append("name", getName()).toString();
    }

    /** ============================================================================
     * SystemFunction Comparator by module
     */
    public static class SystemFunctionComparator implements Comparator<SystemFunction>
    {
        /* (non-Javadoc)
         * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
         */
        public int compare(SystemFunction sf1, SystemFunction sf2)
        {
            String m1 = sf1.getModule();
            String m2 = sf2.getModule();
            int m = m1.compareTo(m2);
            String p1 = sf1.getName();
            String p2 = sf2.getName();
            int p = p1.compareTo(p2);
            return m == 0 ? p : m;
        }
    }

}