package au.gov.qld.fire.jms.domain.mail;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * Mail Status.
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
@Embeddable
public class MailStatusData
{

    private MailStatusEnum status;

    private Date sentDate;

    private Date receivedDate;

    private Date rejectedDate;

    @Enumerated
    @Column(name = "STATUS_ID", nullable = true)
	public MailStatusEnum getStatus()
	{
		return status;
	}

	public void setStatus(MailStatusEnum status)
	{
		this.status = status;
	}

	@Transient
	public boolean isSent()
	{
		return MailStatusEnum.SENT.equals(getStatus());
	}
	@Transient
	public boolean isReceived()
	{
		return MailStatusEnum.RECEIVED.equals(getStatus());
	}
	@Transient
	public boolean isRejected()
	{
		return MailStatusEnum.RTS.equals(getStatus());
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

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "REJECTED_DATE", nullable = true)
	public Date getRejectedDate()
	{
		return rejectedDate;
	}

	public void setRejectedDate(Date rejectedDate)
	{
		this.rejectedDate = rejectedDate;
	}

	@Transient
	public Date getStatusDate()
	{
		if (isSent()) return sentDate;
		if (isReceived()) return receivedDate;
		if (isRejected()) return rejectedDate;
		return null;
	}

	public void setStatusDate(Date statusDate)
	{
		if (isSent()) this.sentDate = statusDate;
		if (isReceived()) this.receivedDate = statusDate;
		if (isRejected()) this.rejectedDate = statusDate;
	}

}