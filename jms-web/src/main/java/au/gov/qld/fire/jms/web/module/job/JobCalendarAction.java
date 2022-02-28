package au.gov.qld.fire.jms.web.module.job;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import au.gov.qld.fire.jms.domain.job.JobCalendar;
import au.gov.qld.fire.jms.domain.job.JobCalendarItem;
import au.gov.qld.fire.jms.domain.job.JobSearchCriteria;
import au.gov.qld.fire.jms.web.module.AbstractDispatchAction;
import au.gov.qld.fire.web.SessionConstants;
import au.gov.qld.fire.web.WebUtils;

/*
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class JobCalendarAction extends AbstractDispatchAction
{

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.web.module.AbstractDispatchAction#populateRequest(org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected void populateRequest(ActionForm form, HttpServletRequest request) throws Exception
    {
        //set references
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.web.module.AbstractDispatchAction#populateForm(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected void populateForm(ActionMapping mapping, ActionForm form, HttpServletRequest request)
        throws Exception
    {
        //Search by FCA, FileNo or Building Name (%)
        String fileId = request.getParameter(SessionConstants.FILE_ID);
        String fcaId = request.getParameter(SessionConstants.FCA_ID);

        JobSearchCriteria criteria = new JobSearchCriteria();
        criteria.setFileNo(fileId);
        criteria.setFcaNo(fcaId);

        JobCalendar entity = new JobCalendar(WebUtils.getYear(request), WebUtils.getMonth(request));
        for (List<JobCalendarItem> items : entity.getItems())
        {
            for (JobCalendarItem item : items)
            {
                if (item != null)
                {
                    item.setItems(getActionService().findJobActionTodoByDueDate(criteria, item.getDate()));
                }
            }
        }
        request.setAttribute(SessionConstants.ENTITIES, entity.getItems());
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
     * Present print.
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward print(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE print()..");
        try
        {
            //populateForm(mapping, form, request);
            return null;
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            return null;
        }
    }

}