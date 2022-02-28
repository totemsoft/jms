package au.gov.qld.fire.jms.domain.refdata;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import au.gov.qld.fire.domain.BaseModel;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Entity
@Table(name = "FILE_STATUS")
//@org.hibernate.annotations.GenericGenerator(name = "assigned-strategy", strategy = "assigned")
@Cache(region = "au.gov.qld.fire.jms.domain.refdata.FileStatus", usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class FileStatus extends BaseModel<Long>
{

    /** serialVersionUID */
    private static final long serialVersionUID = -7393668312532643599L;

    /** persistent field */
    private String name;

    /**
     * 
     */
    public FileStatus()
    {
        super();
    }

    /**
     * @param id
     */
    public FileStatus(Long id)
    {
        super(id);
    }

    /**
     * @param fileStatusEnum
     */
    public FileStatus(FileStatusEnum fileStatusEnum)
    {
        super(fileStatusEnum.getId());
        name = fileStatusEnum.getName();
    }

    @Id
    //@GeneratedValue(generator = "assigned-strategy")
    @Column(name = "FILE_STATUS_ID")
    public Long getFileStatusId()
    {
        return super.getId();
    }

    public void setFileStatusId(Long id)
    {
        super.setId(id);
    }

    /**
     * Calculated
     * @return
     */
    @Transient
    public boolean isConnected()
    {
    	return FileStatusEnum.CONNECTED.getId().equals(getId());
    }
    @Transient
    public boolean isDisconnected()
    {
    	return FileStatusEnum.DISCONNECTED.getId().equals(getId());
    }
    @Transient
    public boolean isArchived()
    {
    	return FileStatusEnum.ARCHIVED.getId().equals(getId());
    }
    @Transient
    public boolean isPending()
    {
    	return FileStatusEnum.PENDING.getId().equals(getId());
    }

    /** 
     *            @hibernate.property
     *             column="NAME"
     *             length="50"
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

}