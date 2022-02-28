package au.gov.qld.fire.jms.web.module.file;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import au.gov.qld.fire.domain.supplier.Supplier;
import au.gov.qld.fire.jms.domain.ase.AseFile;
import au.gov.qld.fire.jms.domain.file.File;
import au.gov.qld.fire.jms.web.module.AbstractDispatchAction;
import au.gov.qld.fire.web.SessionConstants;

/*
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class FcaAseAction extends AbstractDispatchAction
{

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.web.module.AbstractDispatchAction#populateRequest(org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected void populateRequest(ActionForm form, HttpServletRequest request) throws Exception
    {
        //set references
        request.setAttribute(SessionConstants.ASE_TYPES, getEntityService().findAseTypesActive());
        request.setAttribute(SessionConstants.ASE_CONN_TYPES, getEntityService().findAseConnTypesActive());
        request.setAttribute(SessionConstants.SITE_TYPES, getEntityService().findSiteTypesActive());
        request.setAttribute(SessionConstants.SUPPLIERS, getSupplierService().findSuppliers());
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.web.module.AbstractDispatchAction#populateForm(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected void populateForm(ActionMapping mapping, ActionForm form, HttpServletRequest request)
        throws Exception
    {
        FileForm myform = (FileForm) form;
        setEntity(myform, request);
        AseFile aseFile = myform.getAseFile();
        // Supplier
        if (aseFile.getSupplier() == null)
        {
        	aseFile.setSupplier(new Supplier());
        }
    }

    /**
     * Present edit entity form.
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward edit(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE edit()..");
        try
        {
            populateForm(mapping, form, request);
            populateRequest(form, request);
            return mapping.getInputForward();
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            populateRequest(form, request);
            return mapping.getInputForward();
        }
    }

    /**
     * Update/Insert new user.
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
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

            FileForm myform = (FileForm) form;
            File entity = myform.getEntity();
            // save changes (if any)
            getFileService().saveAseFile(entity.getAseFile(), entity.getSiteType());

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