package au.gov.qld.fire.jms.web.module.setup;

import au.gov.qld.fire.jms.domain.refdata.SiteType;
import au.gov.qld.fire.jms.web.module.AbstractValidatorForm;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class SiteTypeForm extends AbstractValidatorForm<SiteType>
{

	/** serialVersionUID */
	private static final long serialVersionUID = -4070595854087230174L;

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.web.module.AbstractValidatorForm#setEntity(java.lang.Object)
     */
    @Override
    public void setEntity(SiteType entity)
    {
        super.setEntity(entity);
        //optional
    }

}