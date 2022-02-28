package au.gov.qld.fire.jms.web.module.finance;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import au.gov.qld.fire.jms.domain.finance.Invoice;
import au.gov.qld.fire.jms.web.module.AbstractValidatorForm;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class ExternalInvoiceForm extends AbstractValidatorForm<Object>
{

	/** serialVersionUID */
	private static final long serialVersionUID = 8726401884064785234L;

    public static final String BEAN_NAME = "externalInvoiceForm";

    private Long id;

    private Invoice entity;

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.jms.web.module.AbstractValidatorForm#reset(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request)
	{
		super.reset(mapping, request);
	}

	/**
	 * @return the id
	 */
	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return the entity
	 */
	public Invoice getEntity()
	{
		if (entity == null)
		{
			entity = new Invoice();
		}
		return entity;
	}

	public void setEntity(Invoice entity)
	{
		Invoice.setNullable(entity);
		this.entity = entity;
	}

}