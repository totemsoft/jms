package au.gov.qld.fire.jms.domain.mail;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import au.gov.qld.fire.domain.Auditable;
import au.gov.qld.fire.jms.domain.action.FileAction;
import au.gov.qld.fire.jms.domain.action.JobAction;
import au.gov.qld.fire.jms.domain.file.File;

@Entity
@Table(name = "MAIL_BATCH_FILE")
public class MailBatchFile extends Auditable<Long>
{

    /** serialVersionUID */
	private static final long serialVersionUID = -2662162696793672827L;

	private MailBatch mailBatch;

    private File file;

    private FileAction fileAction;

    private JobAction jobAction;

    private MailStatusData mailStatus;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MAIL_BATCH_FILE_ID")
    public Long getMailBatchFileId()
    {
        return super.getId();
    }

    public void setMailBatchFileId(Long mailBatchFileId)
    {
        super.setId(mailBatchFileId);
    }

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MAIL_BATCH_ID", nullable = false, insertable = true, updatable = false)
	public MailBatch getMailBatch()
	{
		return mailBatch;
	}

	public void setMailBatch(MailBatch mailBatch)
	{
		this.mailBatch = mailBatch;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FILE_ID", nullable = false, insertable = true, updatable = false)
	public File getFile()
	{
		return file;
	}

	public void setFile(File file)
	{
		this.file = file;
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

	@Embedded
	public MailStatusData getMailStatus()
	{
		if (mailStatus == null) {
			mailStatus = new MailStatusData();
		}
		return mailStatus;
	}

	public void setMailStatus(MailStatusData mailStatus)
	{
		this.mailStatus = mailStatus;
	}

	@Transient
	public MailStatusEnum getStatus()
	{
		return getMailStatus().getStatus();
	}

	@Transient
	public Date getStatusDate()
	{
		return getMailStatus().getStatusDate();
	}

}