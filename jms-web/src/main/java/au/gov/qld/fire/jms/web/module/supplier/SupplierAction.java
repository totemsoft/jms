package au.gov.qld.fire.jms.web.module.supplier;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import au.gov.qld.fire.domain.location.Address;
import au.gov.qld.fire.domain.location.State;
import au.gov.qld.fire.domain.supplier.Supplier;
import au.gov.qld.fire.domain.supplier.SupplierSearchCriteria;
import au.gov.qld.fire.jms.web.module.AbstractDispatchAction;
import au.gov.qld.fire.web.SessionConstants;
import au.gov.qld.fire.web.WebUtils;

/*
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class SupplierAction extends AbstractDispatchAction
{


    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.web.module.AbstractDispatchAction#populateRequest(org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected void populateRequest(ActionForm form, HttpServletRequest request) throws Exception
    {
        //set references
        request.setAttribute(SessionConstants.SALUTATIONS, getEntityService().findSalutations());
        request.setAttribute(SessionConstants.STATES, getEntityService().findStates());
        request.setAttribute(SessionConstants.REGIONS, getEntityService().findRegionsActive());
        request.setAttribute(SessionConstants.SUPPLIER_TYPES, getEntityService().findSupplierTypes());
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.web.module.AbstractDispatchAction#populateForm(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected void populateForm(ActionMapping mapping, ActionForm form, HttpServletRequest request) throws Exception
    {
        //get entity id
        Long id = WebUtils.getLongId(request);
        //
        SupplierForm myform = (SupplierForm) form;
        Supplier entity;
        if (id == null)
        {
            entity = new Supplier();
            //set default state for a new address and postAddress
            String stateDefault = WebUtils.getStateDefault(request.getSession());
            entity.getAddress().setState(new State(stateDefault));
            entity.getPostAddress().setState(new State(stateDefault));
        }
        else
        {
            entity = getSupplierService().findSupplierById(id);
            //update SameAs checkbox for existing postAddress
            Address address = entity.getAddress();
            Address postAddress = entity.getPostAddress();
            postAddress.setSameAs(address.equalsAddress(postAddress));
        }
        myform.setEntity(entity);
    }

    /**
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward find(ActionMapping mapping, ActionForm form, HttpServletRequest request,
        HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE find().. ");
        try
        {
            String supplierName = request.getParameter(SessionConstants.SUPPLIER_NAME);
            String supplierContact = request.getParameter(SessionConstants.SUPPLIER_CONTACT);
            String supplierPhone = request.getParameter(SessionConstants.SUPPLIER_PHONE);

            SupplierSearchCriteria criteria = new SupplierSearchCriteria();
            criteria.setSupplierName(supplierName);
            criteria.setSupplierContact(supplierContact);
            criteria.setSupplierPhone(supplierPhone);
            List<Supplier> entities = getSupplierService().findSuppliers(criteria);

            request.setAttribute(SessionConstants.ENTITIES, entities);

            return mapping.getInputForward();
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            return findForwardError(mapping);//mapping.getInputForward();
        }
    }

    /**
     * Present view entity form.
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward view(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE view()..");
        try
        {
            populateForm(mapping, form, request);
            return mapping.getInputForward();
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            return mapping.getInputForward();
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
    public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
        HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE edit()..");
        try
        {
            populateRequest(form, request);
            populateForm(mapping, form, request);
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
     * Present edit entity form.
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward changeSameAsAddress(ActionMapping mapping, ActionForm form, HttpServletRequest request,
        HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE changeSameAsAddress()..");
        try
        {
            SupplierForm myform = (SupplierForm) form;
            Supplier entity = myform.getEntity();
            Address postAddress = entity.getPostAddress();
            if (postAddress.isSameAs())
            {
                Address newPostAddress = (Address) entity.getAddress().clone();
                newPostAddress.setAddressId(postAddress.getAddressId());
                newPostAddress.setSameAs(true);
                entity.setPostAddress(newPostAddress);
            }

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
     * Update/Insert new entity.
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
            SupplierForm myform = (SupplierForm) form;
            Supplier entity = myform.getEntity();
            Address postAddress = entity.getPostAddress();
            if (postAddress.isSameAs() && !postAddress.equalsAddress(entity.getAddress()))
            {
                Address newPostAddress = (Address) entity.getAddress().clone();
                newPostAddress.setAddressId(postAddress.getAddressId());
                entity.setPostAddress(newPostAddress);
            }
            
            ActionErrors errors = form.validate(mapping, request);
            if (!errors.isEmpty())
            {
                saveErrors(request, response, errors);
                populateRequest(form, request);
                return findForwardError(mapping);
            }

            //save changes (if any)
            getSupplierService().saveSupplier(entity);

            response.getWriter().write("{\"supplierId\":" + entity.getSupplierId() + "}");
            response.getWriter().close();
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