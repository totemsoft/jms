package au.gov.qld.fire.jms.web.module.file;

import java.io.ByteArrayOutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import au.gov.qld.fire.domain.refdata.AseKeyStatusEnum;
import au.gov.qld.fire.domain.refdata.ContentTypeEnum;
import au.gov.qld.fire.domain.refdata.PaymentMethodEnum;
import au.gov.qld.fire.domain.refdata.SentMethodEnum;
import au.gov.qld.fire.domain.supplier.Supplier;
import au.gov.qld.fire.jms.domain.ase.AseKey;
import au.gov.qld.fire.jms.domain.ase.AseKeySearchCriteria;
import au.gov.qld.fire.jms.domain.file.File;
import au.gov.qld.fire.jms.web.module.AbstractDispatchAction;
import au.gov.qld.fire.web.SessionConstants;
import au.gov.qld.fire.web.WebUtils;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class AseKeyAction extends AbstractDispatchAction
{

    /* (non-Javadoc)
	 * @see au.gov.qld.fire.jms.web.module.AbstractDispatchAction#populateRequest(org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected void populateRequest(ActionForm form, HttpServletRequest request) throws Exception
	{
		request.setAttribute("sentMethods", ArrayUtils.remove(SentMethodEnum.values(), 0));
		request.setAttribute("paymentMethods", ArrayUtils.remove(PaymentMethodEnum.values(), 0));
	}

	/* (non-Javadoc)
     * @see au.gov.qld.fire.jms.web.module.AbstractDispatchAction#populateForm(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected void populateForm(ActionMapping mapping, ActionForm form, HttpServletRequest request)
        throws Exception
    {
    	AseKeyForm myform = (AseKeyForm) form;
        // get entity id
    	Long id = WebUtils.getLongId(request);
    	AseKey entity = getFileService().findAseKey(id);
    	entity = entity == null ? new AseKey() : entity;
    	myform.setEntity(entity);
    	if (entity.getId() != null) {
            request.setAttribute(SessionConstants.ENTITY, entity);
    	}
    }

    public ActionForward find(ActionMapping mapping, ActionForm form, HttpServletRequest request,
        HttpServletResponse response) throws Exception
    {
        try
        {
    		request.setAttribute("statuses", ArrayUtils.remove(AseKeyStatusEnum.values(), 0));
            List<AseKey> entities = find(request);
            request.setAttribute(SessionConstants.ENTITIES, entities);
            return mapping.getInputForward();
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            return mapping.getInputForward();
        }
    }

    public ActionForward view(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        try {
        	populateForm(mapping, form, request);
            return findForward(mapping, VIEW);
        }
        catch (Exception e) {
            saveErrors(request, response, toActionErrors(e));
            return mapping.getInputForward();
        }
    }

    public ActionForward edit(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        try {
        	populateForm(mapping, form, request);
        	populateRequest(form, request);
            return findForward(mapping, EDIT);
        }
        catch (Exception e) {
            saveErrors(request, response, toActionErrors(e));
        	populateRequest(form, request);
            return mapping.getInputForward();
        }
    }

    public ActionForward reload(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        try {
        	AseKeyForm myform = (AseKeyForm) form;
        	//
        	AseKey entity = myform.getEntity();
        	Supplier supplier = entity.getSupplier();
        	if (supplier != null && supplier.getId() != null) {
        		supplier = getSupplierService().findSupplierById(supplier.getId());
        	} else {
        		supplier = new Supplier();
        	}
    		entity.setSupplier(supplier);
        	//
            populateRequest(form, request);
            return findForward(mapping, EDIT);
        }
        catch (Exception e) {
            saveErrors(request, response, toActionErrors(e));
            populateRequest(form, request);
            return mapping.getInputForward();
        }
    }

    public ActionForward detailView(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        try {
        	populateForm(mapping, form, request);
        	populateRequest(form, request);
        	//
            FileForm fileForm = FileForm.getInstance(request);
        	AseKeyForm myform = (AseKeyForm) form;
            File entity = myform.getEntity().getFile();
            fileForm.setEntity(entity);
            //CRM Actions To Do
            //CRM Completed Actions
            populateFileActions(entity, request);
            //Job Actions To Do
            //Job Completed Actions
            populateJobActions(entity.getJobs(), request);
            //
            return findForward(mapping, "detailView");
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
        	populateRequest(form, request);
            return mapping.getInputForward();
        }
    }

    public ActionForward export(ActionMapping mapping, ActionForm form, HttpServletRequest request,
        HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE export()..");
        final ContentTypeEnum contentType = ContentTypeEnum.APPLICATION_CSV;
        try
        {
            List<AseKey> entities = find(request);
            String contentName = getContentName("aseKey", contentType);
            ByteArrayOutputStream contentStream = new ByteArrayOutputStream();
            // add entities to contentStream
            getFileService().exportAseKey(entities, contentStream, contentType);
            WebUtils.downloadContent(response, contentStream, contentType.getContentType(), contentName);
            return null;
        }
        catch (Exception e) {
            saveErrors(request, response, toActionErrors(e));
            return null;
        }
    }

    public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
        HttpServletResponse response) throws Exception
    {
        try {
            ActionErrors errors = form.validate(mapping, request);
            if (!errors.isEmpty()) {
                saveErrors(request, response, errors);
                populateRequest(form, request);
                //return findForwardError(mapping);
                return findForward(mapping, EDIT);
            }

            AseKeyForm myform = (AseKeyForm) form;
            AseKey entity = myform.getEntity();
            // save changes (if any)
            getFileService().saveAseKey(entity);

            return findForwardSuccess(mapping);
        }
        catch (Exception e) {
            saveErrors(request, response, toActionErrors(e));
            populateRequest(form, request);
            return findForwardError(mapping);
        }
    }

    private List<AseKey> find(HttpServletRequest request) throws Exception
    {
        AseKeySearchCriteria criteria = new AseKeySearchCriteria();
        criteria.setOrderNo(request.getParameter("orderNo"));
        criteria.setAseKeyNo(request.getParameter("aseKeyNo"));
        criteria.setSupplierName(request.getParameter(SessionConstants.SUPPLIER_NAME));
        criteria.setContactName(request.getParameter("contactName"));
        criteria.setStatus(AseKeyStatusEnum.findByName(request.getParameter("status")));
        return getFileService().findAseKey(criteria);
    }

}