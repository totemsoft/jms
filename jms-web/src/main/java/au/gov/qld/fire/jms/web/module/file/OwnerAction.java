package au.gov.qld.fire.jms.web.module.file;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import au.gov.qld.fire.domain.location.State;
import au.gov.qld.fire.jms.domain.ConvertUtils;
import au.gov.qld.fire.jms.domain.entity.Owner;
import au.gov.qld.fire.jms.domain.file.File;
import au.gov.qld.fire.jms.domain.refdata.OwnerTypeEnum;
import au.gov.qld.fire.jms.web.module.AbstractDispatchAction;
import au.gov.qld.fire.service.ServiceException;
import au.gov.qld.fire.web.SessionConstants;
import au.gov.qld.fire.web.WebUtils;

/*
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class OwnerAction extends AbstractDispatchAction
{

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.web.module.AbstractDispatchAction#populateRequest(org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected void populateRequest(ActionForm form, HttpServletRequest request) throws Exception
    {
        // set references
        request.setAttribute(SessionConstants.SALUTATIONS, getEntityService().findSalutations());
        request.setAttribute(SessionConstants.STATES, getEntityService().findStates());
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.web.module.AbstractDispatchAction#populateForm(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected void populateForm(ActionMapping mapping, ActionForm form, HttpServletRequest request)
        throws Exception
    {
        FileForm myform = (FileForm) form;
        Owner entity;
    	Long ownerTypeId = WebUtils.getOwnerTypeId(request);
    	if (ownerTypeId == null) {
    		File file = myform.getEntity();
    		Owner defaultOwner = file.getDefaultOwner();
    		if (defaultOwner == null) {
    			defaultOwner = new Owner(OwnerTypeEnum.OWNER);
    		}
    		ownerTypeId = defaultOwner.getOwnerType().getOwnerTypeId();
    	}
        if (OwnerTypeEnum.OWNER.ordinal() == ownerTypeId) {
        	entity = myform.getOwner();
        	if (entity == null) {
        		entity = new Owner(OwnerTypeEnum.OWNER);
        	}
            request.setAttribute(SessionConstants.OWNER, "owner");
            request.setAttribute(SessionConstants.TITLE, "Owner Contact Details");
        }
        else if (OwnerTypeEnum.BODY_CORPORATE.ordinal() == ownerTypeId) {
        	entity = myform.getBodyCorporate();
        	if (entity == null) {
        		entity = new Owner(OwnerTypeEnum.BODY_CORPORATE);
        	}
            request.setAttribute(SessionConstants.OWNER, "bodyCorporate");
            request.setAttribute(SessionConstants.TITLE, "Body Corporate Contact Details");
        }
        else if (OwnerTypeEnum.PROPERTY_MANAGER.ordinal() == ownerTypeId) {
        	entity = myform.getPropertyManager();
        	if (entity == null) {
        		entity = new Owner(OwnerTypeEnum.PROPERTY_MANAGER);
        	}
            request.setAttribute(SessionConstants.OWNER, "propertyManager");
            request.setAttribute(SessionConstants.TITLE, "Property Manager Contact Details");
        }
        else if (OwnerTypeEnum.TENANT.ordinal() == ownerTypeId) {
        	entity = myform.getTenant();
        	if (entity == null) {
        		entity = new Owner(OwnerTypeEnum.TENANT);
        	}
            request.setAttribute(SessionConstants.OWNER, "tenant");
            request.setAttribute(SessionConstants.TITLE, "Tenant Contact Details");
        }
        else if (OwnerTypeEnum.ALTERNATE.ordinal() == ownerTypeId) {
        	entity = myform.getAlternate();
        	if (entity == null) {
        		entity = new Owner(OwnerTypeEnum.ALTERNATE);
        	}
            request.setAttribute(SessionConstants.OWNER, "alternate");
            request.setAttribute(SessionConstants.TITLE, "Alternate Contact Details");
        }
        else {
        	throw new ServiceException("Invalid ownerTypeId=" + ownerTypeId);
        }
        // reload entity
    	if (entity.getId() != null) {
    		entity = getFileService().findOwnerById(entity.getId());
        }
        // get parent (if copy requested)
    	Long copyId = WebUtils.getLongId(request, "copyId");
		Owner copyOwner = copyId != null ? getFileService().findOwnerById(copyId) : null;
    	if (copyOwner != null) {
    		ConvertUtils.copyProperties(copyOwner, entity);
    	}
        // set for edit
    	myform.setEditOwner(entity);
        // set default state (if empty)
        if (entity.getAddress().getState().getId() == null) {
            String stateDefault = WebUtils.getStateDefault(request.getSession());
            entity.getAddress().setState(new State(stateDefault));
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
            File file = myform.getEntity();
            Owner owner = myform.getEditOwner();
            // parent file owner of same type
            if (!file.equals(owner.getFile()))
            {
            	// parent used, lets copy it to child
                owner.setFile(file);
                owner.setNextOwner(null);
            }
            // save changes (if any)
            getFileService().saveOwner(owner);

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