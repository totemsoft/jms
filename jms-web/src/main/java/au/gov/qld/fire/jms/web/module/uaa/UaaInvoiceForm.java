package au.gov.qld.fire.jms.web.module.uaa;

import au.gov.qld.fire.jms.domain.finance.Invoice;
import au.gov.qld.fire.jms.domain.finance.InvoiceSearchCriteria;
import au.gov.qld.fire.jms.web.module.setup.AbstractUploadForm;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class UaaInvoiceForm extends AbstractUploadForm<Invoice>
{

	/** serialVersionUID */
	private static final long serialVersionUID = 8989626879973656478L;

	public static final String BEAN_NAME = "uaaInvoiceForm";

	private InvoiceSearchCriteria criteria = new InvoiceSearchCriteria();

	public InvoiceSearchCriteria getCriteria()
	{
		return criteria;
	}

}