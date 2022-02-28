package au.gov.qld.fire.jms.web.module.setup;

import au.gov.qld.fire.domain.security.SystemFunction;
import au.gov.qld.fire.jms.web.module.AbstractValidatorForm;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class SystemFunctionForm extends AbstractValidatorForm<SystemFunction>
{

	/** serialVersionUID */
	private static final long serialVersionUID = 1666272855325261457L;

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.web.module.AbstractValidatorForm#setEntity(java.lang.Object)
     */
    @Override
    public void setEntity(SystemFunction entity)
    {
        super.setEntity(entity);
        //optional
    }

}