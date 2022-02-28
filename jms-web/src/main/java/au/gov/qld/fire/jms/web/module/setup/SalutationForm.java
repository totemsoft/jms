package au.gov.qld.fire.jms.web.module.setup;

import au.gov.qld.fire.domain.refdata.Salutation;
import au.gov.qld.fire.jms.web.module.AbstractValidatorForm;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class SalutationForm extends AbstractValidatorForm<Salutation>
{

    /** serialVersionUID */
    private static final long serialVersionUID = -790762773791907307L;

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.web.module.AbstractValidatorForm#setEntity(java.lang.Object)
     */
    @Override
    public void setEntity(Salutation entity)
    {
        super.setEntity(entity);
        //optional
    }

}