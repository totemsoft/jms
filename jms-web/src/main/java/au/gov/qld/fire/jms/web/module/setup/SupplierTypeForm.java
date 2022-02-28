package au.gov.qld.fire.jms.web.module.setup;

import au.gov.qld.fire.domain.refdata.SupplierType;
import au.gov.qld.fire.jms.web.module.AbstractValidatorForm;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class SupplierTypeForm extends AbstractValidatorForm<SupplierType>
{

    /** serialVersionUID */
    private static final long serialVersionUID = -3801730758306452746L;

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.web.module.AbstractValidatorForm#setEntity(java.lang.Object)
     */
    @Override
    public void setEntity(SupplierType entity)
    {
        super.setEntity(entity);
        //optional
    }

}