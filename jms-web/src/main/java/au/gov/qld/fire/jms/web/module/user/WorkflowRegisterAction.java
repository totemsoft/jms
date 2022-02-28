package au.gov.qld.fire.jms.web.module.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import au.gov.qld.fire.jms.web.module.AbstractDispatchAction;
import au.gov.qld.fire.web.SessionConstants;
import au.gov.qld.fire.web.WebUtils;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class WorkflowRegisterAction extends AbstractDispatchAction
{

    public ActionForward find(ActionMapping mapping, ActionForm form, HttpServletRequest request,
        HttpServletResponse response) throws Exception
    {
        try
        {
            return findForward(mapping, FIND);
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            return findForward(mapping, ERROR);
        }
    }

    public ActionForward findByWorkGroup(ActionMapping mapping, ActionForm form, HttpServletRequest request,
        HttpServletResponse response) throws Exception
    {
        try
        {
            request.setAttribute(SessionConstants.ENTITIES, getEntityService().findWorkflowRegistersByWorkGroup(true));
            return findForward(mapping, "findByWorkGroup");
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            return findForward(mapping, ERROR);
        }
    }
    public ActionForward viewByWorkGroup(ActionMapping mapping, ActionForm form, HttpServletRequest request,
        HttpServletResponse response) throws Exception
    {
        try
        {
        	Long workGroupId = WebUtils.getLongId(request);
            request.setAttribute(SessionConstants.ENTITY, getEntityService().findWorkflowRegisterByWorkGroup(workGroupId));
            return findForward(mapping, "viewByWorkGroup");
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            return findForward(mapping, ERROR);
        }
    }

    public ActionForward findByJobType(ActionMapping mapping, ActionForm form, HttpServletRequest request,
        HttpServletResponse response) throws Exception
    {
        try
        {
            request.setAttribute(SessionConstants.ENTITIES, getEntityService().findWorkflowRegistersByJobType(true));
            return findForward(mapping, "findByJobType");
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            return findForward(mapping, ERROR);
        }
    }
    public ActionForward viewByJobType(ActionMapping mapping, ActionForm form, HttpServletRequest request,
        HttpServletResponse response) throws Exception
    {
        try
        {
        	Long jobTypeId = WebUtils.getLongId(request);
            request.setAttribute(SessionConstants.ENTITY, getEntityService().findWorkflowRegisterByJobType(jobTypeId));
            return findForward(mapping, "viewByJobType");
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            return findForward(mapping, ERROR);
        }
    }

}