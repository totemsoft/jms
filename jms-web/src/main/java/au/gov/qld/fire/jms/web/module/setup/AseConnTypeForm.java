package au.gov.qld.fire.jms.web.module.setup;

import au.gov.qld.fire.jms.domain.refdata.AseConnType;
import au.gov.qld.fire.jms.web.module.AbstractValidatorForm;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class AseConnTypeForm extends AbstractValidatorForm<AseConnType>
{

    /** serialVersionUID */
    private static final long serialVersionUID = 995265156333617848L;

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.web.module.AbstractValidatorForm#setEntity(java.lang.Object)
     */
    @Override
    public void setEntity(AseConnType entity)
    {
        super.setEntity(entity);
        //optional
    }

}