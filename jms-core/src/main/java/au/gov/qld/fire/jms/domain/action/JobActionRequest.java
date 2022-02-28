package au.gov.qld.fire.jms.domain.action;

import java.util.List;

import au.gov.qld.fire.domain.document.Document;
import au.gov.qld.fire.jms.domain.file.File;
import au.gov.qld.fire.jms.domain.mail.MailBatch;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class JobActionRequest extends BaseActionRequest
{

	private final JobAction jobAction;

	public JobActionRequest(File file, JobAction jobAction, Long actionCodeId,
		List<FileAction> defaultActions, List<FileAction> nextActions,
		List<Document> attachments, MailBatch mailBatch)
	{
		super(file, actionCodeId, defaultActions, nextActions, attachments, mailBatch);
		this.jobAction = jobAction;
	}

	/**
	 * @return the fileAction
	 */
	public JobAction getJobAction()
	{
		return jobAction;
	}

}