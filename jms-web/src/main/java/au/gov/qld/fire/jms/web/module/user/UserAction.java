package au.gov.qld.fire.jms.web.module.user;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import au.gov.qld.fire.domain.refdata.WorkGroup;
import au.gov.qld.fire.domain.security.User;
import au.gov.qld.fire.domain.security.UserSearchCriteria;
import au.gov.qld.fire.jms.web.module.AbstractDispatchAction;
import au.gov.qld.fire.web.SessionConstants;
import au.gov.qld.fire.web.WebUtils;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class UserAction extends AbstractDispatchAction
{

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.web.module.AbstractDispatchAction#populateRequest(org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected void populateRequest(ActionForm form, HttpServletRequest request) throws Exception
    {
        // set references
        request.setAttribute(SessionConstants.SALUTATIONS, getEntityService().findSalutations());
        request.setAttribute(SessionConstants.SECURITY_GROUPS, getEntityService().findSecurityGroups());
        request.setAttribute(SessionConstants.USER_TYPES, getEntityService().findUserTypes());
        request.setAttribute(SessionConstants.SUPPLIERS, getSupplierService().findSuppliers());
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.web.module.AbstractDispatchAction#populateForm(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected void populateForm(ActionMapping mapping, ActionForm form, HttpServletRequest request) throws Exception
    {
        // update user details
        UserForm myform = (UserForm) form;
        Long id = WebUtils.getLongId(request);
        User entity;
        if (id == null)
        {
        	entity = null;
            myform.reset(mapping, request);
        }
        else
        {
            entity = getUserService().findUserById(id);
            myform.setEntity(entity);
        }
        //
        Long[] workGroupIds = myform.getUserWorkGroups();
        final List<WorkGroup> workGroups = getEntityService().findWorkGroupsActive();
        List<WorkGroup> availableWorkGroups = new ArrayList<WorkGroup>(workGroups);
        List<WorkGroup> userWorkGroups = entity == null ? new ArrayList<WorkGroup>() : entity.getWorkGroups();
        if (!userWorkGroups.isEmpty())
        {
            availableWorkGroups.removeAll(userWorkGroups);
        }
        else if (workGroupIds != null && workGroupIds.length > 0)
        {
        	Arrays.sort(workGroupIds);
            for (Iterator<WorkGroup> iter = availableWorkGroups.iterator(); iter.hasNext();)
            {
                WorkGroup item = iter.next();
                if (Arrays.binarySearch(workGroupIds, item.getId()) >= 0)
                {
                    userWorkGroups.add(item);
                    iter.remove();
                }
            }
        }
        request.setAttribute("availableWorkGroups", availableWorkGroups);
        request.setAttribute("userWorkGroups", userWorkGroups);
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
    public ActionForward find(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE find().. ");
        try
        {
            String securityGroups = request.getParameter(SessionConstants.SECURITY_GROUPS);
            String userTypes = request.getParameter(SessionConstants.USER_TYPES);

            List<User> entities;
            if (StringUtils.isBlank(userTypes) && StringUtils.isBlank(securityGroups))
            {
                entities = getUserService().findUsers();
            }
            else
            {
                UserSearchCriteria criteria = new UserSearchCriteria();
                criteria.setSecurityGroups(securityGroups);
                criteria.setUserTypes(userTypes);
                entities = getUserService().findUsers(criteria);
            }

            request.setAttribute(SessionConstants.ENTITIES, entities);

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
            //
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
    public ActionForward edit(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE edit()..");
        try
        {
            populateRequest(form, request);
            populateForm(mapping, form, request);
            //
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
        UserForm myform = (UserForm) form;
        try
        {
            ActionErrors errors = myform.validate(mapping, request);
            if (!errors.isEmpty())
            {
                saveErrors(request, response, errors);
                populateRequest(myform, request);
                return findForwardError(mapping);
            }

            User entity = myform.getEntity();
            // save changes (if any)
            getUserService().saveUser(
            	entity, myform.getContact(), myform.getUserWorkGroups(), myform.isReissuePassword());

            return findForwardSuccess(mapping);
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            populateRequest(myform, request);
            return findForwardError(mapping);
        }
    }

}