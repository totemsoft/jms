package au.gov.qld.fire.jms.web.module.setup;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import au.gov.qld.fire.jms.domain.action.ActionOutcome;
import au.gov.qld.fire.jms.web.module.AbstractValidatorForm;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class ActionOutcomeForm extends AbstractValidatorForm<ActionOutcome>
{

    /** serialVersionUID */
    private static final long serialVersionUID = 6284775520124863318L;

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.web.module.AbstractValidatorForm#reset(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
     */
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request)
    {
        if (getEntity() != null)
        {
            getEntity().setFixed(false);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.web.module.AbstractValidatorForm#setEntity(java.lang.Object)
     */
    @Override
    public void setEntity(ActionOutcome entity)
    {
        super.setEntity(entity);
        //optional
    }

}