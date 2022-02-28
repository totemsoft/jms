package au.gov.qld.fire.jms.web.module.file;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import au.gov.qld.fire.jms.domain.file.File;
import au.gov.qld.fire.jms.web.module.AbstractDispatchAction;
import au.gov.qld.fire.web.SessionConstants;
import au.gov.qld.fire.web.WebUtils;

public class FileAuditAction extends AbstractDispatchAction
{

    /* (non-Javadoc)
	 * @see au.gov.qld.fire.jms.web.module.AbstractDispatchAction#populateRequest(org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected void populateRequest(ActionForm form, HttpServletRequest request)	throws Exception
	{
        request.setAttribute(SessionConstants.STATES, getEntityService().findStates());
        request.setAttribute(SessionConstants.SALUTATIONS, getEntityService().findSalutations());
	}

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.web.module.AbstractDispatchAction#populateForm(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected void populateForm(ActionMapping mapping, ActionForm form, HttpServletRequest request) throws Exception
    {
    	FileAuditForm myform = (FileAuditForm) form;
        // get entity id
    	Long id = WebUtils.getLongId(request);
    	File auditFile = getFileService().findFileAuditById(id);
    	File originalFile = getFileService().findFileById(id);
    	if (auditFile == null) {
    		auditFile = originalFile;
    	}
    	myform.setEntity(auditFile);
    	request.setAttribute("readonly", false); // to enable fields edit
        request.setAttribute("original", originalFile); // to display original value (as tooltip)
        populatePriorities(request, "ownerBuildingContactsPriorities", myform.getOwnerBuildingContacts());
        populatePriorities(request, "securityBuildingContactsPriorities", myform.getSecurityBuildingContacts());
    }

    public ActionForward find(ActionMapping mapping, ActionForm form, HttpServletRequest request,
        HttpServletResponse response) throws Exception
    {
        try
        {
        	populateRequest(form, request);
        	populateForm(mapping, form, request);
            return mapping.getInputForward();
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            return mapping.getInputForward();
        }
    }

    public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
        HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE save()..");
        try
        {
            ActionErrors errors = form.validate(mapping, request);
            if (!errors.isEmpty())
            {
                saveErrors(request, response, errors);
                populateRequest(form, request);
                return findForwardError(mapping);
            }

            FileAuditForm myform = (FileAuditForm) form;
            // save changes (if any)
            getFileService().saveFileAudit(myform.getEntity());

            return findForwardSuccess(mapping);
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            populateRequest(form, request);
            return findForwardError(mapping);
        }
    }

}