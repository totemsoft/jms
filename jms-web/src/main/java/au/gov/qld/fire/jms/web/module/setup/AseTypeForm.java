package au.gov.qld.fire.jms.web.module.setup;

import au.gov.qld.fire.jms.domain.refdata.AseType;
import au.gov.qld.fire.jms.web.module.AbstractValidatorForm;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class AseTypeForm extends AbstractValidatorForm<AseType>
{

    /** serialVersionUID */
    private static final long serialVersionUID = 3271431639642419938L;

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.web.module.AbstractValidatorForm#setEntity(java.lang.Object)
     */
    @Override
    public void setEntity(AseType entity)
    {
        super.setEntity(entity);
        //optional
    }

}