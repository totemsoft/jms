package au.gov.qld.fire.jms.web.module.uaa;

import au.gov.qld.fire.jms.domain.finance.Invoice;
import au.gov.qld.fire.jms.uaa.domain.IncidentSearchCriteria;
import au.gov.qld.fire.jms.web.module.setup.AbstractUploadForm;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class UaaIncidentForm extends AbstractUploadForm<Invoice>
{

	/** serialVersionUID */
	private static final long serialVersionUID = 8989626879973656478L;

	public static final String BEAN_NAME = "uaaIncidentForm";

	private IncidentSearchCriteria criteria = new IncidentSearchCriteria();

	public IncidentSearchCriteria getCriteria()
	{
		return criteria;
	}

}