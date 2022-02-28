package au.gov.qld.fire.jms.web.module.user;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import au.gov.qld.fire.domain.refdata.WorkGroup;
import au.gov.qld.fire.jms.domain.user.WorkflowRegister;
import au.gov.qld.fire.jms.web.module.AbstractValidatorForm;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class WorkflowRegisterForm extends AbstractValidatorForm<WorkflowRegister>
{

    /** serialVersionUID */
	private static final long serialVersionUID = 7113401919194881700L;

	/* (non-Javadoc)
     * @see org.apache.struts.validator.ValidatorForm#reset(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
     */
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request)
    {
        super.reset(mapping, request);
        setEntity(new WorkflowRegister(new WorkGroup()));
    }

}