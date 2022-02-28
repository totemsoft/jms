package au.gov.qld.fire.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.apache.commons.lang.ArrayUtils;
import org.hibernate.annotations.OptimisticLockType;

import au.gov.qld.fire.domain.security.User;

/**
 * Base class for all auditable domain objects.
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@MappedSuperclass
@EntityListeners({
    AuditableListener.class
})
@org.hibernate.annotations.Entity(mutable = true, optimisticLock = OptimisticLockType.VERSION)
//@Where(clause = IAuditable.WHERE)
public abstract class Auditable<T> extends BaseModel<T> implements IAuditable
{

    /** serialVersionUID */
    private static final long serialVersionUID = 2503793227245015562L;

    public static final String[] IGNORE = (String[]) ArrayUtils.addAll(BaseModel.IGNORE,
        new String[] {CREATED_BY, CREATED_DATE, MODIFIED_BY, MODIFIED_DATE});

    private User createdBy;

    private Date createdDate;

    private User updatedBy;

    private Date updatedDate;

    private long lockVersion;

    /**
     * 
     */
    protected Auditable()
    {
        super();
    }

    /**
     * @param id
     */
    protected Auditable(T id)
    {
        super(id);
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.domain.BaseModel#clone()
     */
    @SuppressWarnings("unchecked")
    @Override
    protected Object clone() throws CloneNotSupportedException
    {
        Auditable<T> auditable = (Auditable<T>) super.clone();
        auditable.createdBy = null;
        auditable.createdDate = null;
        auditable.updatedBy = null;
        auditable.updatedDate = null;
        auditable.lockVersion = 0L;
        return auditable;
    }

    @ManyToOne//(fetch = FetchType.LAZY)
    @JoinColumn(name = "CREATED_BY", nullable = true, insertable = true, updatable = false)
    public User getCreatedBy()
    {
        return this.createdBy;
    }

    public void setCreatedBy(User createdBy)
    {
        this.createdBy = createdBy;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE", nullable = false, insertable = true, updatable = false)
    public Date getCreatedDate()
    {
        return this.createdDate;
    }

    public void setCreatedDate(Date createdDate)
    {
        this.createdDate = createdDate;
    }

    @ManyToOne//(fetch = FetchType.LAZY)
    @JoinColumn(name = "UPDATED_BY", nullable = true, insertable = false, updatable = true)
    public User getUpdatedBy()
    {
        return this.updatedBy;
    }

    public void setUpdatedBy(User updatedBy)
    {
        this.updatedBy = updatedBy;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE", nullable = true, insertable = false, updatable = true)
    public Date getUpdatedDate()
    {
        return this.updatedDate;
    }

    public void setUpdatedDate(Date updatedDate)
    {
        this.updatedDate = updatedDate;
    }

    @Transient
    public Date getLastDate()
    {
    	return getUpdatedDate() == null ? getCreatedDate() : getUpdatedDate();
    }

    @Transient
    public User getLastBy()
    {
    	return getUpdatedBy() == null ? getCreatedBy() : getUpdatedBy();
    }

    @Version
    @Column(name = "LOCK_VERSION", nullable = false)
    public long getLockVersion()
    {
        return this.lockVersion;
    }

    protected void setLockVersion(long lockVersion)
    {
        this.lockVersion = lockVersion;
    }

}