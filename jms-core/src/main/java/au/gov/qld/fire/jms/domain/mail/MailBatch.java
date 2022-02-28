package au.gov.qld.fire.jms.domain.mail;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.NaturalId;

import au.gov.qld.fire.domain.Auditable;
import au.gov.qld.fire.jms.domain.action.FileAction;
import au.gov.qld.fire.jms.domain.action.JobAction;
import au.gov.qld.fire.jms.domain.refdata.ActionCode;
import au.gov.qld.fire.util.ThreadLocalUtils;

@Entity
@Table(name = "MAIL_BATCH")
public class MailBatch extends Auditable<Long>
{

	/** serialVersionUID */
	private static final long serialVersionUID = 409979731220309669L;

	private String name;

	private String description;

	private List<MailBatchFile> files;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MAIL_BATCH_ID")
    public Long getMailBatchId()
    {
        return super.getId();
    }

    public void setMailBatchId(Long mailBatchId)
    {
        super.setId(mailBatchId);
    }

	/**
	 * @return the name
	 */
    @NaturalId
    @Column(name = "NAME", length = 50, nullable = false)
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * Helper method to generate unique batch name
	 * @param actionCode
	 * @param dateFormat
	 * @return
	 */
	public static String generateName(ActionCode actionCode, DateFormat dateFormat)
	{
        return actionCode.getCode() + "_" + dateFormat.format(ThreadLocalUtils.getDate());
	}

	/**
	 * @return the description
	 */
    @Column(name = "DESCRIPTION", length = 250, nullable = true)
	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	/**
	 * @return the files
	 */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "mailBatch", cascade = CascadeType.ALL)
	public List<MailBatchFile> getFiles()
	{
		if (files == null)
		{
			files = new ArrayList<MailBatchFile>();
		}
		return files;
	}

	public void setFiles(List<MailBatchFile> files)
	{
		this.files = files;
	}

	public void add(MailBatchFile item)
	{
		item.setMailBatch(this);
		getFiles().add(item);
	}

	@Transient
	public int getCompletedPercent()
	{
		int total = getFiles().size();
		if (total == 0) {
			return 0;
		}
		int completed = 0;
		for (MailBatchFile mbf : getFiles()) {
			FileAction fa = mbf.getFileAction();
			JobAction ja = mbf.getJobAction();
			if (fa != null) {
				if (fa.isCompleted()) {
					completed++;
				}
			}
			else if (ja != null) {
				if (ja.isCompleted()) {
					completed++;
				}
			}
		}
		return completed * 100 / total;
	}
}