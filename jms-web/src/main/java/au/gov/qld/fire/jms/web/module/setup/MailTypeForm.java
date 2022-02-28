package au.gov.qld.fire.jms.web.module.setup;

import au.gov.qld.fire.domain.refdata.MailType;
import au.gov.qld.fire.jms.web.module.AbstractValidatorForm;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class MailTypeForm extends AbstractValidatorForm<MailType>
{

	/** serialVersionUID */
	private static final long serialVersionUID = -1009439177869919134L;

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.web.module.AbstractValidatorForm#setEntity(java.lang.Object)
     */
    @Override
    public void setEntity(MailType entity)
    {
        super.setEntity(entity);
        //optional
    }

}