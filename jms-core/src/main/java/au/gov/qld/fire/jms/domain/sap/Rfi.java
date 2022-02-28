package au.gov.qld.fire.jms.domain.sap;

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

import au.gov.qld.fire.domain.Auditable;
import au.gov.qld.fire.domain.security.User;
import au.gov.qld.fire.jms.domain.action.FileAction;
import au.gov.qld.fire.jms.domain.file.File;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Entity
@Table(name = "RFI")
public class Rfi extends Auditable<Long>
{

    /** serialVersionUID */
    private static final long serialVersionUID = -5526771760949867677L;

    /** SAP_INV_COMP_BY property name */
    public static final String SAP_INV_COMP_BY = "sapInvCompBy";

    /** RFI_DEL_BY property name */
    public static final String RFI_DEL_BY = "rfiDelBy";

    /** FILE property name */
    public static final String FILE = "file";

    /** FILE_ACTION property name */
    public static final String FILE_ACTION = "fileAction";

    private String description;

    private String sapInvNo;

    private Date sapInvDate;

    private User sapInvCompBy;

    private User rfiDelBy;

    private Date rfiDelDate;

    private File file;

    private FileAction fileAction;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RFI_ID")
    public Long getRfiId()
    {
        return super.getId();
    }

    public void setRfiId(Long rfiId)
    {
        super.setId(rfiId);
    }

    @Column(name = "DESCRIPTION", nullable = true)
    public String getDescription()
    {
        return this.description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    @Column(name = "SAP_INV_NO", nullable = true)
    public String getSapInvNo()
    {
        return this.sapInvNo;
    }

    public void setSapInvNo(String sapInvNo)
    {
        this.sapInvNo = sapInvNo;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "SAP_INV_DATE", nullable = true)
    public Date getSapInvDate()
    {
        return this.sapInvDate;
    }

    public void setSapInvDate(Date sapInvDate)
    {
        this.sapInvDate = sapInvDate;
    }

    /** 
     *            @hibernate.many-to-one
     *             not-null="true"
     *            @hibernate.column name="SAP_INV_COMP_BY"
     *         
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SAP_INV_COMP_BY", nullable = true)
    public User getSapInvCompBy()
    {
        return this.sapInvCompBy;
    }

    public void setSapInvCompBy(User sapInvCompBy)
    {
        this.sapInvCompBy = sapInvCompBy;
    }

    /** 
     *            @hibernate.many-to-one
     *             not-null="true"
     *            @hibernate.column name="RFI_DEL_BY"
     *         
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RFI_DEL_BY", nullable = true)
    public User getRfiDelBy()
    {
        return this.rfiDelBy;
    }

    public void setRfiDelBy(User rfiDelBy)
    {
        this.rfiDelBy = rfiDelBy;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "RFI_DEL_DATE", nullable = true)
    public Date getRfiDelDate()
    {
        return this.rfiDelDate;
    }

    public void setRfiDelDate(Date rfiDelDate)
    {
        this.rfiDelDate = rfiDelDate;
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

    /** 
     *            @hibernate.many-to-one
     *             not-null="true"
     *            @hibernate.column name="FILE_ACTION_ID"         
     *         
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FILE_ACTION_ID", nullable = false)
    public FileAction getFileAction()
    {
        return this.fileAction;
    }

    public void setFileAction(FileAction fileAction)
    {
        this.fileAction = fileAction;
    }

}