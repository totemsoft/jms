package au.gov.qld.fire.jms.domain.mail;

import java.util.Date;

import javax.persistence.CascadeType;
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

import org.apache.commons.lang.ArrayUtils;
import org.hibernate.annotations.Type;

import au.gov.qld.fire.domain.Auditable;
import au.gov.qld.fire.domain.entity.Contact;
import au.gov.qld.fire.domain.location.Address;
import au.gov.qld.fire.domain.refdata.MailType;
import au.gov.qld.fire.domain.refdata.WorkGroup;
import au.gov.qld.fire.domain.security.User;
import au.gov.qld.fire.jms.domain.action.FileAction;
import au.gov.qld.fire.jms.domain.action.JobAction;
import au.gov.qld.fire.jms.domain.file.File;
import au.gov.qld.fire.util.DateUtils;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Entity
@Table(name = "MAIL_REGISTER")
public class MailRegister extends Auditable<Long>
{

	/** serialVersionUID */
	private static final long serialVersionUID = 24535540918602006L;

    public static final String[] IGNORE = (String[]) ArrayUtils.addAll(Auditable.IGNORE,
        new String[] {"file", "contact", "address", "workGroup", "user", "fileAction", "jobAction"});

    private File file; // PK

    private MailType mailType;

    private Date date = DateUtils.getCurrentDate();

    private String mailRegisterNo;

    private String details;

    private boolean mailIn;

    private boolean rts;

    private Contact contact;

    private Address address;

    private WorkGroup workGroup;

    private User user;

    private FileAction fileAction;

    private JobAction jobAction;

    public MailRegister()
    {
        super();
    }

    public MailRegister(Long id)
    {
        super(id);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MAIL_REGISTER_ID")
    public Long getMailRegisterId()
    {
        return super.getId();
    }

    public void setMailRegisterId(Long mailRegisterId)
    {
    	super.setId(mailRegisterId);
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FILE_ID", nullable = false)
    public File getFile()
    {
        if (file == null)
        {
            file = new File();
        }
        return this.file;
    }

    public void setFile(File file)
    {
        this.file = file;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MAIL_TYPE_ID", nullable = true)
	public MailType getMailType()
	{
		return mailType;
	}

	public void setMailType(MailType mailType)
	{
		this.mailType = mailType;
	}

    @Temporal(TemporalType.DATE)
    @Column(name = "MAIL_REGISTER_DATE", nullable = true)
	public Date getDate()
	{
		return date;
	}

	public void setDate(Date date)
	{
		this.date = date;
	}

    @Column(name = "MAIL_REGISTER_NO", nullable = true, length = 50)
    public String getMailRegisterNo()
    {
        return this.mailRegisterNo;
    }

    public void setMailRegisterNo(String mailRegisterNo)
    {
        this.mailRegisterNo = mailRegisterNo;
    }

    @Column(name = "MAIL_REGISTER_DETAILS", nullable = true, length = 512)
	public String getDetails()
	{
		return details;
	}

	public void setDetails(String details)
	{
		this.details = details;
	}

    @Column(name = "MAIL_IN", nullable = false)
    @Type(type = "yes_no")
	public boolean isMailIn()
	{
		return mailIn;
	}

	public void setMailIn(boolean mailIn)
	{
		this.mailIn = mailIn;
	}

	/**
	 * @return the rts - Return to Sender
	 */
    @Column(name = "RTS", nullable = false)
    @Type(type = "yes_no")
	public boolean isRts()
	{
		return rts;
	}

	public void setRts(boolean rts)
	{
		this.rts = rts;
	}

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "CONTACT_ID", nullable = true)
	public Contact getContact()
	{
		return contact;
	}

	public void setContact(Contact fromContact)
	{
		this.contact = fromContact;
	}

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "ADDRESS_ID", nullable = true)
	public Address getAddress()
	{
		return address;
	}

	public void setAddress(Address fromAddress)
	{
		this.address = fromAddress;
	}

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WORK_GROUP_ID", nullable = true)
	public WorkGroup getWorkGroup()
	{
		return workGroup;
	}

	public void setWorkGroup(WorkGroup workGroup)
	{
		this.workGroup = workGroup;
	}

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = true)
	public User getUser()
	{
		return user;
	}

	public void setUser(User user)
	{
		this.user = user;
	}

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FILE_ACTION_ID", nullable = true)
	public FileAction getFileAction()
	{
		return fileAction;
	}

	public void setFileAction(FileAction fileAction)
	{
		this.fileAction = fileAction;
	}

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "JOB_ACTION_ID", nullable = true)
	public JobAction getJobAction()
	{
		return jobAction;
	}

	public void setJobAction(JobAction jobAction)
	{
		this.jobAction = jobAction;
	}

}