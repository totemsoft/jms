package au.gov.qld.fire.jms.domain.action;

import java.util.Date;

import au.gov.qld.fire.domain.refdata.DateOptionEnum;
import au.gov.qld.fire.jms.domain.mail.MailStatusEnum;
import au.gov.qld.fire.jms.domain.refdata.ActionTypeEnum;
import au.gov.qld.fire.jms.domain.refdata.OwnerTypeEnum;


/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class MailSearchCriteria extends TodoSearchCriteria
{

	/** serialVersionUID */
	private static final long serialVersionUID = -2802716354308820625L;

	/** ACTION_TYPE */
    public static final ActionTypeEnum[] ACTION_TYPE = {
        ActionTypeEnum.LETTER,
        ActionTypeEnum.EMAIL
    };

    private Long batchId;

    /** search files where there are no items found on or after this date */
    private DateOptionEnum sentDateOption;
    private Date sentDateFrom;
    private Date sentDateTo;

    private DateOptionEnum receivedDateOption;
    private Date receivedDateFrom;
    private Date receivedDateTo;

    private Long[] fileIds;

    private MailStatusEnum[] status;

    private boolean rejected;

    private boolean doNotMail;

    private OwnerTypeEnum ownerType;
    private Long ownerId;
    private Long ownerIdContact;

    /**
	 * @return the batchId
	 */
	public Long getBatchId()
	{
		return batchId;
	}

	public void setBatchId(Long batchId)
	{
		this.batchId = toLong(batchId);
	}

	/**
	 * @return the sentDateOption
	 */
	public DateOptionEnum getSentDateOption()
	{
		if (sentDateOption == null) {
			sentDateOption = DateOptionEnum.NONE;
		}
		return sentDateOption;
	}

	public void setSentDateOption(DateOptionEnum sentDateOption)
	{
		this.sentDateOption = sentDateOption;
	}

	/**
	 * @return the sentDateFrom
	 */
	public Date getSentDateFrom()
	{
		return sentDateFrom;
	}

	public void setSentDateFrom(Date sentDateFrom)
	{
		this.sentDateFrom = sentDateFrom;
	}

	/**
	 * @return the sentDateTo
	 */
	public Date getSentDateTo()
	{
		return sentDateTo;
	}

	public void setSentDateTo(Date sentDateTo)
	{
		this.sentDateTo = sentDateTo;
	}

	/**
	 * @return the receivedDateOption
	 */
	public DateOptionEnum getReceivedDateOption()
	{
		if (receivedDateOption == null) {
			receivedDateOption = DateOptionEnum.NONE;
		}
		return receivedDateOption;
	}

	public void setReceivedDateOption(DateOptionEnum receivedDateOption)
	{
		this.receivedDateOption = receivedDateOption;
	}

	/**
	 * @return the receivedDateFrom
	 */
	public Date getReceivedDateFrom()
	{
		return receivedDateFrom;
	}

	public void setReceivedDateFrom(Date receivedDateFrom)
	{
		this.receivedDateFrom = receivedDateFrom;
	}

	/**
	 * @return the receivedDateTo
	 */
	public Date getReceivedDateTo()
	{
		return receivedDateTo;
	}

	public void setReceivedDateTo(Date receivedDateTo)
	{
		this.receivedDateTo = receivedDateTo;
	}

	/**
	 * @return the fileIds
	 */
	public Long[] getFileIds()
	{
		return fileIds;
	}

	public void setFileIds(Long[] fileIds)
	{
		this.fileIds = fileIds;
	}

	/**
	 * @return the status
	 */
	public MailStatusEnum[] getStatus()
	{
		return status;
	}

	public void setStatus(MailStatusEnum... status)
	{
		this.status = status;
	}

	/**
	 * @return the rejected
	 */
	public boolean isRejected()
	{
		return rejected;
	}

	public void setRejected(boolean rejected)
	{
		this.rejected = rejected;
	}

	/**
	 * @return the doNotMail
	 */
	public boolean isDoNotMail()
	{
		return doNotMail;
	}

	public void setDoNotMail(boolean doNotMail)
	{
		this.doNotMail = doNotMail;
	}

	/**
	 * @return the ownerType
	 */
	public OwnerTypeEnum getOwnerType()
	{
		return ownerType;
	}

	public void setOwnerType(OwnerTypeEnum ownerType)
	{
		this.ownerType = ownerType;
	}

	/**
	 * @return the ownerId
	 */
	public Long getOwnerId()
	{
		return ownerId;
	}

	public void setOwnerId(Long ownerId)
	{
		this.ownerId = toLong(ownerId);
	}

	/**
	 * @return the ownerIdContact
	 */
	public Long getOwnerIdContact()
	{
		return ownerIdContact;
	}

	public void setOwnerIdContact(Long ownerIdContact)
	{
		this.ownerIdContact = toLong(ownerIdContact);
	}

}