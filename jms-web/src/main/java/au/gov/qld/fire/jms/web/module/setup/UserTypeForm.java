package au.gov.qld.fire.jms.web.module.setup;

import au.gov.qld.fire.domain.refdata.UserType;
import au.gov.qld.fire.jms.web.module.AbstractValidatorForm;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class UserTypeForm extends AbstractValidatorForm<UserType>
{

    /** serialVersionUID */
    private static final long serialVersionUID = 3636888390320497268L;

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.web.module.AbstractValidatorForm#setEntity(java.lang.Object)
     */
    @Override
    public void setEntity(UserType entity)
    {
        super.setEntity(entity);
        //optional
    }

}