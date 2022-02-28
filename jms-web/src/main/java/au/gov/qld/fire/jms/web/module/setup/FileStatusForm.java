package au.gov.qld.fire.jms.web.module.setup;

import au.gov.qld.fire.jms.domain.refdata.FileStatus;
import au.gov.qld.fire.jms.web.module.AbstractValidatorForm;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class FileStatusForm extends AbstractValidatorForm<FileStatus>
{

    /** serialVersionUID */
    private static final long serialVersionUID = -8322543476591966864L;

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.web.module.AbstractValidatorForm#setEntity(java.lang.Object)
     */
    @Override
    public void setEntity(FileStatus entity)
    {
        super.setEntity(entity);
        //optional
    }

}