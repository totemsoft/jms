package au.gov.qld.fire.jms.web.module.fca;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import au.gov.qld.fire.jms.domain.Fca;
import au.gov.qld.fire.jms.domain.file.File;
import au.gov.qld.fire.jms.web.module.AbstractValidatorForm;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class FcaForm extends AbstractValidatorForm<Fca>
{

    /** serialVersionUID */
    private static final long serialVersionUID = -6208306509733165817L;

    /* (non-Javadoc)
     * @see org.apache.struts.validator.ValidatorForm#reset(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
     */
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request)
    {
        //do nothing (all fca numbers have to be pre-loaded via sql)
        //super.reset(mapping, request);
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.web.module.AbstractValidatorForm#setEntity(java.lang.Object)
     */
    @Override
    public void setEntity(Fca entity)
    {
        super.setEntity(entity);
        if (entity.getFile() == null)
        {
            entity.setFile(new File());
        }
    }

}