package au.gov.qld.fire.jms.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import au.gov.qld.fire.jms.web.module.AbstractBaseAction;
import au.gov.qld.fire.web.SessionConstants;

/**
 *
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class LogoutAction extends AbstractBaseAction
{

    /* (non-Javadoc)
     * @see org.apache.struts.action.Action#execute(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        // default - invalidate session and let filter redirect.
        request.getSession(false).invalidate();
        return mapping.findForward(SUCCESS);
    }

    public ActionForward logout(ActionMapping mapping, ActionForm form, HttpServletRequest request,
        HttpServletResponse response) throws Exception
    {
        String requestedUrl = (String) request.getParameter(SessionConstants.REQUESTED_URL);
    	return execute(mapping, form, request, response);
    }

}