package au.gov.qld.fire.jms.domain.ase;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import au.gov.qld.fire.domain.Auditable;
import au.gov.qld.fire.jms.domain.refdata.AseConnType;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Entity
@Table(name = "ASE_CONN")
@org.hibernate.annotations.GenericGenerator(name = "assigned-strategy", strategy = "assigned")
public class AseConn extends Auditable<Long>
{

    /** serialVersionUID */
    private static final long serialVersionUID = 728333859612527367L;

    /** ASE_CONN_TYPE property name */
    public static final String ASE_CONN_TYPE = "aseConnType";

    /** SEC_ASE_CONN_TYPE property name */
    public static final String SEC_ASE_CONN_TYPE = "secAseConnType";

    /** ASE_FILE property name */
    public static final String ASE_FILE = "aseFile";

    private AseConnType aseConnType;

    private String priRef;

    private AseConnType secAseConnType;

    private String secRef;

    private AseFile aseFile;

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.domain.BaseModel#getId()
     */
    @Override
    @Transient
    public Long getId()
    {
        if (super.getId() == null && aseFile != null)
        {
            super.setId(aseFile.getId());
        }
        return super.getId();
    }

    @Id
    @GeneratedValue(generator = "assigned-strategy")
    @Column(name = "ASE_FILE_ID")
    public Long getAseConnId()
    {
        return getId();
    }

    public void setAseConnId(Long aseFileId)
    {
        setId(aseFileId);
    }

    /** 
     *            @hibernate.property
     *             column="PRI_REF"
     *             length="100"
     *             not-null="true"
     *         
     */
    @Column(name = "PRI_REF", nullable = false)
    public String getPriRef()
    {
        return this.priRef;
    }

    public void setPriRef(String priRef)
    {
        this.priRef = priRef;
    }

    /** 
     *            @hibernate.property
     *             column="SEC_ASE_CONN_TYPE_ID"
     *             length="11"
     *         
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SEC_ASE_CONN_TYPE_ID", nullable = false)
    public AseConnType getSecAseConnType()
    {
        if (secAseConnType == null)
        {
            secAseConnType = new AseConnType();   
        }
        return this.secAseConnType;
    }

    public void setSecAseConnType(AseConnType secAseConnType)
    {
        this.secAseConnType = secAseConnType;
    }

    /** 
     *            @hibernate.property
     *             column="SEC_REF"
     *             length="100"
     *             not-null="true"
     *         
     */
    @Column(name = "SEC_REF", nullable = false)
    public String getSecRef()
    {
        return this.secRef;
    }

    public void setSecRef(String secRef)
    {
        this.secRef = secRef;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ASE_CONN_TYPE_ID", nullable = false)
    public AseConnType getAseConnType()
    {
        if (aseConnType == null)
        {
            aseConnType = new AseConnType();   
        }
        return this.aseConnType;
    }

    public void setAseConnType(AseConnType aseConnType)
    {
        this.aseConnType = aseConnType;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ASE_FILE_ID", nullable = false, insertable = false, updatable = false)
    public AseFile getAseFile()
    {
        return this.aseFile;
    }

    public void setAseFile(AseFile aseFile)
    {
        this.aseFile = aseFile;
    }

}