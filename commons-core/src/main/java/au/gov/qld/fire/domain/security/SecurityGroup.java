package au.gov.qld.fire.domain.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.ArrayUtils;

import au.gov.qld.fire.domain.Auditable;
import au.gov.qld.fire.domain.ConvertUtils;

/**
 * @hibernate.class table="SECURITY_GROUP" dynamic-update="true"
 *
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Entity
@Table(name = "SECURITY_GROUP")
@org.hibernate.annotations.GenericGenerator(name = "increment-strategy", strategy = "increment")
public class SecurityGroup extends Auditable<Long>
{

    /** serialVersionUID */
    private static final long serialVersionUID = -7400700587626329044L;

    /** IGNORE */
    public static final String[] IGNORE = (String[]) ArrayUtils.addAll(
        Auditable.IGNORE, new String[] {"securityGroupId", "systemFunctions"});

    private String name;

    private String description;

    private List<SystemFunction> systemFunctions;

    /**
     * 
     */
    public SecurityGroup()
    {
        super();
    }

    /**
     * @param id
     */
    public SecurityGroup(Long id)
    {
        super(id);
    }

    /** 
     *            @hibernate.id
     *             generator-class="assigned"
     *             type="java.lang.Long"
     *             column="SECURITY_GROUP_ID"
     *         
     */
    @Id
    @GeneratedValue(generator = "increment-strategy") //(strategy = GenerationType.TABLE)
    @Column(name = "SECURITY_GROUP_ID")
    public Long getSecurityGroupId()
    {
        return super.getId();
    }

    public void setSecurityGroupId(Long securityGroupId)
    {
        super.setId(securityGroupId);
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

    public void setName(String name)
    {
        this.name = name;
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

    /** 
     *            @hibernate.bag
     *             lazy="true"
     *             inverse="true"
     *             cascade="none"
     *            @hibernate.collection-key
     *             column="SECURITY_GROUP_ID"
     *            @hibernate.collection-many-to-many
     *             class="au.gov.qld.fire.jms.hibernate.SystemFunction"
     *         
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "SYSTEM_ACCESS",
        joinColumns = @JoinColumn(name = "SECURITY_GROUP_ID", referencedColumnName = "SECURITY_GROUP_ID"),
        inverseJoinColumns = @JoinColumn(name = "SYSTEM_FUNCTION_ID", referencedColumnName = "SYSTEM_FUNCTION_ID"))
    //@WhereJoinTable(clause = "LOGICALLY_DELETED IS NULL")
    public List<SystemFunction> getSystemFunctions()
    {
        if (systemFunctions == null)
        {
            systemFunctions = new ArrayList<SystemFunction>();
        }
        return this.systemFunctions;
    }

    public void setSystemFunctions(List<SystemFunction> systemFunctions)
    {
        this.systemFunctions = systemFunctions;
    }

    /**
     * 
     * @return
     */
    @Transient
    public Map<String, List<SystemFunction>> getSystemFunctionsMap()
    {
        return ConvertUtils.toMap(getSystemFunctions());
    }

    /**
     * @param systemFunction
     */
    public void add(SystemFunction systemFunction)
    {
        //systemFunction.setSecurityGroup(this);
        add(getSystemFunctions(), systemFunction);
    }

}