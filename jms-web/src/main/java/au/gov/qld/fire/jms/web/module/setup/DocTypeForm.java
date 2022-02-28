package au.gov.qld.fire.jms.web.module.setup;

import au.gov.qld.fire.domain.refdata.DocType;
import au.gov.qld.fire.jms.web.module.AbstractValidatorForm;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class DocTypeForm extends AbstractValidatorForm<DocType>
{

    /** serialVersionUID */
    private static final long serialVersionUID = 2168749743248401876L;

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.web.module.AbstractValidatorForm#setEntity(java.lang.Object)
     */
    @Override
    public void setEntity(DocType entity)
    {
        super.setEntity(entity);
        //optional
    }

}