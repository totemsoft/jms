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

import au.gov.qld.fire.domain.ConvertUtils;
import au.gov.qld.fire.domain.entity.Contact;
import au.gov.qld.fire.domain.location.State;
import au.gov.qld.fire.domain.refdata.AseKeyStatusEnum;
import au.gov.qld.fire.domain.refdata.ContentTypeEnum;
import au.gov.qld.fire.domain.refdata.PaymentMethodEnum;
import au.gov.qld.fire.domain.refdata.SentMethodEnum;
import au.gov.qld.fire.domain.supplier.Supplier;
import au.gov.qld.fire.jms.domain.ase.AseKey;
import au.gov.qld.fire.jms.domain.ase.AseKeyOrder;
import au.gov.qld.fire.jms.domain.ase.AseKeySearchCriteria;
import au.gov.qld.fire.jms.web.module.AbstractDispatchAction;
import au.gov.qld.fire.web.SessionConstants;
import au.gov.qld.fire.web.WebUtils;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class AseKeyOrderAction extends AbstractDispatchAction
{

    /* (non-Javadoc)
	 * @see au.gov.qld.fire.jms.web.module.AbstractDispatchAction#populateRequest(org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected void populateRequest(ActionForm form, HttpServletRequest request) throws Exception
	{
        request.setAttribute(SessionConstants.SALUTATIONS, getEntityService().findSalutations());
        request.setAttribute(SessionConstants.STATES, getEntityService().findStates());
		request.setAttribute("sentMethods", ArrayUtils.remove(SentMethodEnum.values(), 0));
		request.setAttribute("paymentMethods", ArrayUtils.remove(PaymentMethodEnum.values(), 0));
		request.setAttribute("statuses", ArrayUtils.remove(AseKeyStatusEnum.values(), 0));
        request.setAttribute("keyPrices", getEntityService().findAseKeyPrice());
	}

	/* (non-Javadoc)
     * @see au.gov.qld.fire.jms.web.module.AbstractDispatchAction#populateForm(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected void populateForm(ActionMapping mapping, ActionForm form, HttpServletRequest request)
        throws Exception
    {
    	AseKeyOrderForm myform = (AseKeyOrderForm) form;
        // get entity id
    	Long id = WebUtils.getLongId(request);
    	AseKeyOrder entity = getFileService().findAseKeyOrder(id);
    	entity = entity == null ? new AseKeyOrder() : entity;
    	myform.setEntity(entity);
    	if (entity.getId() != null) {
            request.setAttribute(SessionConstants.ENTITY, entity);
    	}
        // set default state (if empty)
        if (entity.getAddress().getState().getId() == null) {
            String stateDefault = WebUtils.getStateDefault(request.getSession());
            entity.getAddress().setState(new State(stateDefault));
        }
    }

    public ActionForward find(ActionMapping mapping, ActionForm form, HttpServletRequest request,
        HttpServletResponse response) throws Exception
    {
        try
        {
            List<AseKeyOrder> entities = find(request);
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
        try
        {
        	populateForm(mapping, form, request);
            return findForward(mapping, VIEW);
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            return mapping.getInputForward();
        }
    }

    public ActionForward edit(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        try
        {
        	populateForm(mapping, form, request);
        	populateRequest(form, request);
            return findForward(mapping, EDIT);
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
        	populateRequest(form, request);
            return mapping.getInputForward();
        }
    }

    public ActionForward addAseKey(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        try {
        	AseKeyOrderForm myform = (AseKeyOrderForm) form;
        	AseKeyOrder entity = myform.getEntity();
        	AseKey aseKey = new AseKey();
    		aseKey.setOrder(entity);
    		aseKey.setSupplier(entity.getSupplier());
    		if (aseKey.getContact() == null) {
    			aseKey.setContact(new Contact());
    			ConvertUtils.copyProperties(entity.getContact(), aseKey.getContact());
    		}
    		aseKey.setPaymentMethod(entity.getPaymentMethod());
    		aseKey.setSentMethod(entity.getSentMethod());
    		aseKey.setSentReferenceNo(entity.getSentReferenceNo());
        	myform.setAseKey(aseKey);
        	populateRequest(form, request);
            return findForward(mapping, "editAseKey");
        }
        catch (Exception e) {
            saveErrors(request, response, toActionErrors(e));
        	populateRequest(form, request);
            return mapping.getInputForward();
        }
    }

    public ActionForward editAseKey(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        try {
        	AseKeyOrderForm myform = (AseKeyOrderForm) form;
        	AseKey aseKey = myform.getAseKey();
       		aseKey = getFileService().findAseKey(aseKey.getAseKeyId());
        	myform.setAseKey(aseKey);
        	populateRequest(form, request);
            return findForward(mapping, "editAseKey");
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
        try
        {
        	AseKeyOrderForm myform = (AseKeyOrderForm) form;
        	//
        	AseKeyOrder entity = myform.getEntity();
        	Supplier supplier = entity.getSupplier();
        	if (supplier != null && supplier.getId() != null) {
        		supplier = getSupplierService().findSupplierById(supplier.getId());
        	} else {
        		supplier = new Supplier();
        	}
    		entity.setSupplier(supplier);
    		// set order contact (default one from supplier)
    		Contact contact = entity.getContact();
    		Contact supplierContact = supplier.getContact();
    		if (contact.isEmpty() && !supplierContact.isEmpty()) {
        		ConvertUtils.copyProperties(supplierContact, contact);
    		}
        	//
            populateRequest(form, request);
            return findForward(mapping, EDIT);
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            populateRequest(form, request);
            return mapping.getInputForward();
        }
    }

    public ActionForward detailView(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        try
        {
        	populateForm(mapping, form, request);
        	populateRequest(form, request);
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
        final ContentTypeEnum contentType = ContentTypeEnum.APPLICATION_CSV;
        try
        {
            List<AseKeyOrder> entities = find(request);
            String contentName = getContentName("aseKeyOrder", contentType);
            ByteArrayOutputStream contentStream = new ByteArrayOutputStream();
            // add entities to contentStream
            getFileService().exportAseKeyOrder(entities, contentStream, contentType);
            WebUtils.downloadContent(response, contentStream, contentType.getContentType(), contentName);
            return null;
        }
        catch (Exception e)
        {
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
            AseKeyOrderForm myform = (AseKeyOrderForm) form;
            AseKeyOrder entity = myform.getEntity();
            // save changes (if any)
            getFileService().saveAseKeyOrder(entity);
            return findForwardSuccess(mapping);
        }
        catch (Exception e) {
            saveErrors(request, response, toActionErrors(e));
            populateRequest(form, request);
            return findForwardError(mapping);
        }
    }

    public ActionForward saveAseKey(ActionMapping mapping, ActionForm form, HttpServletRequest request,
        HttpServletResponse response) throws Exception
    {
        try {
            ActionErrors errors = form.validate(mapping, request);
            if (!errors.isEmpty()) {
                saveErrors(request, response, errors);
                populateRequest(form, request);
                //return findForwardError(mapping);
                return findForward(mapping, "editAseKey");
            }
            AseKeyOrderForm myform = (AseKeyOrderForm) form;
            AseKey aseKey = myform.getAseKey();
            // save changes (if any)
            getFileService().saveAseKey(aseKey);
            return findForwardSuccess(mapping);
        }
        catch (Exception e) {
            saveErrors(request, response, toActionErrors(e));
            populateRequest(form, request);
            return findForwardError(mapping);
        }
    }

    private List<AseKeyOrder> find(HttpServletRequest request) throws Exception
    {
        AseKeySearchCriteria criteria = new AseKeySearchCriteria();
        criteria.setOrderNo(request.getParameter("orderNo"));
        criteria.setSupplierName(request.getParameter(SessionConstants.SUPPLIER_NAME));
        criteria.setContactName(request.getParameter("contactName"));
        return getFileService().findAseKeyOrder(criteria);
    }

}