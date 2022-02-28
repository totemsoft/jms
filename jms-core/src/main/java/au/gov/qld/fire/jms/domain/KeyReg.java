package au.gov.qld.fire.jms.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import au.gov.qld.fire.domain.Auditable;
import au.gov.qld.fire.jms.domain.file.File;


/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Entity
@Table(name = "KEY_REG")
public class KeyReg extends Auditable<Long>
{

    /** serialVersionUID */
    private static final long serialVersionUID = 6476847976635938360L;

    /** FILE property name */
    public static final String FILE = "file";

    private String keyNo;

    private String ownerKeyId;

    private String lockType;

    private String lockLocation;

    private File file;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "KEY_REG_ID")
    public Long getKeyRegId()
    {
        return super.getId();
    }

    public void setKeyRegId(Long keyRegId)
    {
        super.setId(keyRegId);
    }

    /** 
     *            @hibernate.property
     *             column="KEY_NO"
     *             length="10"
     *             not-null="true"
     *         
     */
    @Column(name = "KEY_NO", nullable = false)
    public String getKeyNo()
    {
        return this.keyNo;
    }

    public void setKeyNo(String keyNo)
    {
        this.keyNo = keyNo;
    }

    /** 
     *            @hibernate.property
     *             column="OWNER_KEY_ID"
     *             length="20"
     *             not-null="true"
     *         
     */
    @Column(name = "OWNER_KEY_ID", nullable = false)
    public String getOwnerKeyId()
    {
        return this.ownerKeyId;
    }

    public void setOwnerKeyId(String ownerKeyId)
    {
        this.ownerKeyId = ownerKeyId;
    }

    /** 
     *            @hibernate.property
     *             column="LOCK_TYPE"
     *             length="100"
     *             not-null="true"
     *         
     */
    @Column(name = "LOCK_TYPE", nullable = false)
    public String getLockType()
    {
        return this.lockType;
    }

    public void setLockType(String lockType)
    {
        this.lockType = lockType;
    }

    /** 
     *            @hibernate.property
     *             column="LOCK_LOCATION"
     *             length="150"
     *             not-null="true"
     *         
     */
    @Column(name = "LOCK_LOCATION", nullable = false)
    public String getLockLocation()
    {
        return this.lockLocation;
    }

    public void setLockLocation(String lockLocation)
    {
        this.lockLocation = lockLocation;
    }

    /** 
     *            @hibernate.many-to-one
     *             not-null="true"
     *            @hibernate.column name="FILE_ID"         
     *         
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FILE_ID", nullable = false)
    public File getFile()
    {
        return this.file;
    }

    public void setFile(File file)
    {
        this.file = file;
    }

}