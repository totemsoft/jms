package au.gov.qld.fire.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonProperty;

/*
 * Base class for all domain objects.
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@MappedSuperclass
public abstract class BaseModel<T> implements IBase<T>, Serializable, Cloneable
{

    /** serialVersionUID */
    private static final long serialVersionUID = 2766302496000475786L;

    public static final String[] IGNORE = {ID, LOGICALLY_DELETED};

    /** identity persistent field */
    @Transient
    protected T id;

    @Transient
    private boolean active = true;

    @Transient
    private Boolean logicallyDeleted = null;

    /**
     * 
     */
    protected BaseModel()
    {
        super();
    }

    /**
     * @param id
     */
    protected BaseModel(T id)
    {
        this.id = id;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#clone()
     */
    @SuppressWarnings("unchecked")
    @Override
    protected Object clone() throws CloneNotSupportedException
    {
        BaseModel<T> baseModel = (BaseModel<T>) super.clone();
        baseModel.id = null;
        baseModel.logicallyDeleted = null;
        return baseModel;
    }

    /**
     *
     * @return
     */
    @Transient
    @JsonProperty
    public T getId()
    {
        if (id instanceof Number && ((Number) id).longValue() == 0L)
        {
            id = null;
        }
        if (id instanceof String && ((String) id).trim().length() == 0)
        {
            id = null;
        }
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(T id)
    {
        this.id = id;
    }

    /** 
     *            @hibernate.property
     *             column="ACTIVE"
     *             length="1"
     *             not-null="true"
     *         
     */
    @Transient
    protected boolean isActive()
    {
        return this.active;
    }

    protected void setActive(boolean active)
    {
        this.active = active;
    }

    /** 
     *            @hibernate.property
     *             column="LOGICALLY_DELETED"
     *             length="1"
     *         
     */
    @Transient
    protected Boolean getLogicallyDeleted()
    {
        return this.logicallyDeleted;
    }

    protected void setLogicallyDeleted(Boolean logicallyDeleted)
    {
        this.logicallyDeleted = logicallyDeleted;
    }

    /**
     * Have to write this code as alternative to Set<T>
     * @param list
     * @param entity
     */
    @SuppressWarnings("unchecked")
    protected void add(List list, BaseModel entity)
    {
        if (list.contains(entity))
        {
            int index = list.indexOf(entity);
            list.set(index, entity);
        }
        else
        {
            list.add(entity);
        }
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @SuppressWarnings("unchecked")
    public boolean equals(Object other)
    {
        if (this == other)
        {
            return true; // super implementation
        }
        if (other != null)
        {
            Class clazz = getClass();
            Class otherClazz = other.getClass();
            if (clazz.isAssignableFrom(otherClazz) || otherClazz.isAssignableFrom(clazz))
            {
                BaseModel<T> castOther = (BaseModel<T>) other;
                //use "castOther.getId()" as this instance can be hibernate enhancer
                if (this.getId() != null && castOther.getId() != null)
                {
                    return new EqualsBuilder().append(this.getId(), castOther.getId()).isEquals();
                }
            }
        }
        return false;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    public int hashCode()
    {
        return getId() == null ? super.hashCode() : new HashCodeBuilder().append(getId()).toHashCode();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        return new ToStringBuilder(this).append("id", getId()).toString();
    }

    /**
     * Helper method
     * @param value
     * @return
     */
    protected final Long toLong(Long value)
    {
    	return value != null && value == 0L ? null : value;
    }

    protected final Integer toInteger(Integer value)
    {
    	return value != null && value == 0 ? null : value;
    }

}