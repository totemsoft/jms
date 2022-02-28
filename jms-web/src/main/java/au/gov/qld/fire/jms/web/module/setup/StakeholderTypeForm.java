package au.gov.qld.fire.jms.web.module.setup;

import au.gov.qld.fire.jms.domain.refdata.StakeHolderType;
import au.gov.qld.fire.jms.web.module.AbstractValidatorForm;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class StakeholderTypeForm extends AbstractValidatorForm<StakeHolderType>
{

    /** serialVersionUID */
    private static final long serialVersionUID = -1232396910807287424L;

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.web.module.AbstractValidatorForm#setEntity(java.lang.Object)
     */
    @Override
    public void setEntity(StakeHolderType entity)
    {
        super.setEntity(entity);
        //optional
    }

}