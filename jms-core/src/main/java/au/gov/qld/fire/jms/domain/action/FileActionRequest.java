package au.gov.qld.fire.jms.domain.action;

import java.util.List;

import au.gov.qld.fire.domain.document.Document;
import au.gov.qld.fire.jms.domain.file.Archive;
import au.gov.qld.fire.jms.domain.file.File;
import au.gov.qld.fire.jms.domain.mail.MailBatch;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class FileActionRequest extends BaseActionRequest
{

	private final FileAction fileAction;

    private final Archive archive;

	public FileActionRequest(File file, FileAction fileAction, Long actionCodeId,
		List<FileAction> defaultActions, List<FileAction> nextActions,
		List<Document> attachments, Archive archive, MailBatch mailBatch)
	{
		super(file, actionCodeId, defaultActions, nextActions, attachments, mailBatch);
		this.fileAction = fileAction;
		this.archive = archive;
	}

	public FileActionRequest(File file, Long actionCodeId, MailBatch mailBatch)
	{
		this(file, null, actionCodeId, null, null, null, null, mailBatch);
	}

	/**
	 * @return the fileAction
	 */
	public FileAction getFileAction()
	{
		return fileAction;
	}

	/**
	 * @return the archive
	 */
	public Archive getArchive()
	{
		return archive;
	}

}