package au.gov.qld.fire.jms.domain.refdata;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import au.gov.qld.fire.domain.Auditable;
import au.gov.qld.fire.domain.refdata.WorkGroup;
import au.gov.qld.fire.domain.security.SecurityGroup;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Entity
@Table(name = "JOB_TYPE")
@Cache(region = "au.gov.qld.fire.jms.domain.refdata.JobType", usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class JobType extends Auditable<Long>
{

    /** serialVersionUID */
    private static final long serialVersionUID = 4165700029736193286L;

    /** SECURITY_GROUP property name */
    public static final String SECURITY_GROUP = "securityGroup";

    /** ACTION_CODE property name */
    public static final String ACTION_CODE = "actionCode";

    /** CLOSE_JOB_SECURITY_GROUP property name */
    public static final String CLOSE_JOB_SECURITY_GROUP = "closeJobSecurityGroup";

    /** WORK_GROUP property name */
    public static final String WORK_GROUP = "workGroup";

    /** persistent field */
    private String name;

    /** persistent field */
    private SecurityGroup securityGroup;

    /** persistent field */
    private ActionCode actionCode;

    /** persistent field */
    private SecurityGroup closeJobSecurityGroup;

    /** persistent field */
    private WorkGroup workGroup;

    /**
     * 
     */
    public JobType()
    {
        super();
    }

    /**
     * @param id
     */
    public JobType(Long id)
    {
        super(id);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "JOB_TYPE_ID")
    public Long getJobTypeId()
    {
        return super.getId();
    }

    public void setJobTypeId(Long id)
    {
        super.setId(id);
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
        return this.name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    /** 
     *            @hibernate.property
     *             column="SECURITY_GROUP_ID"
     *             length="5"
     *         
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SECURITY_GROUP_ID", nullable = true)
    public SecurityGroup getSecurityGroup()
    {
        return this.securityGroup;
    }

    public void setSecurityGroup(SecurityGroup securityGroup)
    {
        this.securityGroup = securityGroup;
    }

    /** 
     *            @hibernate.many-to-one
     *             not-null="true"
     *            @hibernate.column name="ACTION_CODE_ID"         
     *         
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ACTION_CODE_ID", nullable = true)
    public ActionCode getActionCode()
    {
        return this.actionCode;
    }

    public void setActionCode(ActionCode actionCode)
    {
        this.actionCode = actionCode;
    }

    /** 
     *            @hibernate.many-to-one
     *             not-null="true"
     *            @hibernate.column name="CLOSE_JOB_SECURITY_GROUP_ID"         
     *         
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CLOSE_JOB_SECURITY_GROUP_ID", nullable = true)
    public SecurityGroup getCloseJobSecurityGroup()
    {
        return this.closeJobSecurityGroup;
    }

    public void setCloseJobSecurityGroup(
        SecurityGroup closeJobSecurityGroup)
    {
        this.closeJobSecurityGroup = closeJobSecurityGroup;
    }

    /** 
     *            @hibernate.many-to-one
     *             not-null="true"
     *            @hibernate.column name="WORK_GROUP_ID"         
     *         
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WORK_GROUP_ID", nullable = true)
    public WorkGroup getWorkGroup()
    {
        return this.workGroup;
    }

    public void setWorkGroup(WorkGroup workGroup)
    {
        this.workGroup = workGroup;
    }

}