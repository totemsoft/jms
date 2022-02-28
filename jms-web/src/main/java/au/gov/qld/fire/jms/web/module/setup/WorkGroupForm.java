package au.gov.qld.fire.jms.web.module.setup;

import au.gov.qld.fire.domain.refdata.WorkGroup;
import au.gov.qld.fire.jms.web.module.AbstractValidatorForm;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class WorkGroupForm extends AbstractValidatorForm<WorkGroup>
{

    /** serialVersionUID */
    private static final long serialVersionUID = -7030097913032011717L;

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.web.module.AbstractValidatorForm#setEntity(java.lang.Object)
     */
    @Override
    public void setEntity(WorkGroup entity)
    {
        super.setEntity(entity);
        //optional
    }

}