package au.gov.qld.fire.jms.web;

import javax.security.auth.login.AccountExpiredException;
import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import au.gov.qld.fire.domain.security.User;
import au.gov.qld.fire.jms.web.module.AbstractDispatchAction;
import au.gov.qld.fire.jms.web.module.user.UserForm;
import au.gov.qld.fire.web.SessionConstants;
import au.gov.qld.fire.web.util.LoginUtils;

/*
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class LoginAction extends AbstractDispatchAction
{

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.web.module.AbstractDispatchAction#populateForm(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected void populateForm(ActionMapping mapping, ActionForm form, HttpServletRequest request) throws Exception
    {
        //UserForm myform = (UserForm) form;
        //myform.setPassword(null); // reset password
    }

    public ActionForward login(ActionMapping mapping, ActionForm form, HttpServletRequest request,
        HttpServletResponse response) throws Exception
    {
        UserForm myform = (UserForm) form;
        try
        {
            ActionErrors errors = form.validate(mapping, request);
            if (!errors.isEmpty())
            {
                LOG.error(errors);
                saveErrors(request, errors);
                populateForm(mapping, form, request);
                return mapping.getInputForward();
            }

            //login (JAAS)
            LoginUtils.login(request, myform.getJ_username(), myform.getPassword().toCharArray());

            String requestedUrl = (String) request.getParameter(SessionConstants.REQUESTED_URL);
            if (requestedUrl != null)
            {
                return new ActionForward(requestedUrl, true);
            }
            return mapping.findForward(SUCCESS);
        }
        catch (AccountExpiredException e)
        {
            LOG.warn(e.getMessage());
            //STEP 1 - add parameter
            ActionForward forward = new ActionForward(mapping.findForward("changePassword"));
            String path = forward.getPath();
            forward.setPath(path + "?entity.login=" + myform.getEntity().getLogin());
            return forward;
        }
        catch (LoginException e)
        {
            saveErrors(request, toActionErrors(e));
            return mapping.getInputForward();
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            saveErrors(request, toActionErrors(e));
            return mapping.getInputForward();
        }
    }

    public ActionForward changePassword(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE changePassword..");

        UserForm myform = (UserForm) form;
        try
        {
            ActionErrors errors = form.validate(mapping, request);
            if (!errors.isEmpty())
            {
                LOG.error(errors);
                saveErrors(request, errors);
                populateForm(mapping, form, request);
                return mapping.getInputForward();
            }

            //change user password
            User user = myform.getEntity();
            String oldPassword = myform.getPassword();
            String newPassword = myform.getPasswordNew();
            user = getUserService().changePassword(user.getLogin(), oldPassword, newPassword);

            //login (JAAS)
            LoginUtils.login(request, user.getLogin(), newPassword.toCharArray());

            //STEP 2 - add parameter to forward
            ActionForward forward = new ActionForward(mapping.findForward("changePassword"));
            String path = forward.getPath();
            forward.setPath(path + "?id=" + user.getId());
            return forward;
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            saveErrors(request, toActionErrors(e));
            return mapping.getInputForward();
        }
    }

    public ActionForward logout(ActionMapping mapping, ActionForm form, HttpServletRequest request,
        HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE logout..");

        // logout (JAAS)
        LoginUtils.logout(request);

        return mapping.findForward(SUCCESS);
    }

}