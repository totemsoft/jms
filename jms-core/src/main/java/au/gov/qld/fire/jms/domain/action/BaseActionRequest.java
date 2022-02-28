package au.gov.qld.fire.jms.domain.action;

import java.util.List;

import au.gov.qld.fire.domain.document.Document;
import au.gov.qld.fire.jms.domain.file.File;
import au.gov.qld.fire.jms.domain.mail.MailBatch;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public abstract class BaseActionRequest
{
	private final File file;

	private Long actionCodeId;

	private final List<FileAction> defaultActions;

	private final List<FileAction> nextActions;

	private final List<Document> attachments;

    private final MailBatch mailBatch;

	public BaseActionRequest(File file, Long actionCodeId,
			List<FileAction> defaultActions, List<FileAction> nextActions,
			List<Document> attachments, MailBatch mailBatch)
	{
		this.file = file;
		this.actionCodeId = actionCodeId;
		this.defaultActions = defaultActions;
		this.nextActions = nextActions;
		this.attachments = attachments;
		this.mailBatch = mailBatch;
	}

	/**
	 * @return the file
	 */
	public File getFile()
	{
		return file;
	}

	/**
	 * @return the actionCodeId
	 */
	public Long getActionCodeId()
	{
		return actionCodeId;
	}

	/**
	 * @return the defaultActions
	 */
	public List<FileAction> getDefaultActions()
	{
		return defaultActions;
	}

	/**
	 * @return the nextActions
	 */
	public List<FileAction> getNextActions()
	{
		return nextActions;
	}

	/**
	 * @return the attachments
	 */
	public List<Document> getAttachments()
	{
		return attachments;
	}

	/**
	 * @return the mailBatch
	 */
	public MailBatch getMailBatch()
	{
		return mailBatch;
	}

}