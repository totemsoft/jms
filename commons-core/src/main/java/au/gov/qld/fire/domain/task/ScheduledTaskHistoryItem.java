package au.gov.qld.fire.domain.task;

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

import org.hibernate.annotations.NaturalId;

import au.gov.qld.fire.domain.BaseModel;

/**
 *
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Entity
@Table(name = "SCHEDULED_TASK_HISTORY_ITEM")
public class ScheduledTaskHistoryItem extends BaseModel<Long>
{

	/** serialVersionUID */
	private static final long serialVersionUID = -3573322519451769633L;

    private ScheduledTaskHistory scheduledTaskHistory;

	private String itemId;

	private Date sentDate;

	private Date receivedDate;

	private String from;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SCHEDULED_TASK_HISTORY_ITEM_ID", nullable = false)
	public Long getScheduledTaskHistoryItemId()
    {
        return super.getId();
	}

	public void setScheduledTaskHistoryItemId(Long scheduledTaskHistoryItemId)
	{
        super.setId(scheduledTaskHistoryItemId);
	}

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SCHEDULED_TASK_HISTORY_ID", nullable = false)
	public ScheduledTaskHistory getScheduledTaskHistory()
	{
		return scheduledTaskHistory;
	}

	public void setScheduledTaskHistory(ScheduledTaskHistory scheduledTaskHistory)
	{
		this.scheduledTaskHistory = scheduledTaskHistory;
	}

	@NaturalId
	@Column(name = "ITEM_ID", nullable = false, length = 50)
	public String getItemId()
    {
		return itemId;
	}

	public void setItemId(String itemId)
	{
		this.itemId = itemId;
	}

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "SENT_DATE", nullable = true)
	public Date getSentDate()
    {
		return sentDate;
	}

	public void setSentDate(Date sentDate)
	{
		this.sentDate = sentDate;
	}

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "RECEIVED_DATE", nullable = true)
	public Date getReceivedDate()
    {
		return receivedDate;
	}

	public void setReceivedDate(Date receivedDate)
	{
		this.receivedDate = receivedDate;
	}

	@Column(name = "ITEM_FROM", nullable = true, length = 250)
	public String getFrom()
	{
		return from;
	}

	public void setFrom(String from)
	{
		this.from = from;
	}

}