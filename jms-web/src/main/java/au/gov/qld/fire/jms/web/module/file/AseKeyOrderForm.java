package au.gov.qld.fire.jms.web.module.file;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import au.gov.qld.fire.domain.supplier.Supplier;
import au.gov.qld.fire.jms.domain.ase.AseKey;
import au.gov.qld.fire.jms.domain.ase.AseKeyOrder;
import au.gov.qld.fire.jms.web.module.AbstractValidatorForm;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class AseKeyOrderForm extends AbstractValidatorForm<AseKeyOrder>
{

	/** serialVersionUID */
	private static final long serialVersionUID = -523937867829410603L;

	public static final String BEAN_NAME = "aseKeyOrderForm";

	/** current/selected for edit */
	private AseKey aseKey;

    /* (non-Javadoc)
     * @see org.apache.struts.validator.ValidatorForm#reset(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
     */
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request)
    {
        super.reset(mapping, request);
        //this.aseKey = null;
    }

    /* (non-Javadoc)
     * @see org.apache.struts.validator.ValidatorForm#validate(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
     */
    @Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
    {
        ActionErrors errors = super.validate(mapping, request);
        //
        return errors;
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.web.module.AbstractValidatorForm#setEntity(java.lang.Object)
     */
    @Override
    public void setEntity(AseKeyOrder entity)
    {
        super.setEntity(entity);
    	if (entity != null) {
        	if (entity.getSupplier() == null) {
        		entity.setSupplier(new Supplier());
        	}
    	}
    }

	public AseKey getAseKey()
	{
		if (aseKey == null) {
			aseKey = new AseKey();
		}
		return aseKey;
	}

	public void setAseKey(AseKey aseKey)
	{
		this.aseKey = aseKey;
	}

}