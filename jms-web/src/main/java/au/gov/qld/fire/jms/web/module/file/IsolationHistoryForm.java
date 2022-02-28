package au.gov.qld.fire.jms.web.module.file;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import au.gov.qld.fire.jms.domain.Fca;
import au.gov.qld.fire.jms.domain.file.File;
import au.gov.qld.fire.jms.domain.isolation.IsolationHistory;
import au.gov.qld.fire.jms.web.module.setup.AbstractUploadForm;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class IsolationHistoryForm extends AbstractUploadForm<IsolationHistory>
{

	/** serialVersionUID */
	private static final long serialVersionUID = 1808289876288510547L;

	public static final String BEAN_NAME = "isolationHistoryForm";

    /* (non-Javadoc)
     * @see org.apache.struts.validator.ValidatorForm#reset(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
     */
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request)
    {
        super.reset(mapping, request);
    }

    /* (non-Javadoc)
     * @see org.apache.struts.validator.ValidatorForm#validate(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
     */
    @Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
    {
        ActionErrors errors = super.validate(mapping, request);
        //
        return errors;
    }

	/* (non-Javadoc)
     * @see au.gov.qld.fire.jms.web.module.AbstractValidatorForm#setEntity(java.lang.Object)
     */
    @Override
    public void setEntity(IsolationHistory entity)
    {
        if (entity.getFca() == null)
        {
        	entity.setFca(new Fca());
        }
        if (entity.getFca().getFile() == null)
        {
        	entity.getFca().setFile(new File());
        }
        super.setEntity(entity);
    }

}