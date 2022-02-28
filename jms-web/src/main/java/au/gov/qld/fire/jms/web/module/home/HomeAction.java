package au.gov.qld.fire.jms.web.module.home;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import au.gov.qld.fire.jms.domain.job.ActiveJob;
import au.gov.qld.fire.jms.domain.job.JobSearchCriteria;
import au.gov.qld.fire.jms.web.module.AbstractDispatchAction;
import au.gov.qld.fire.web.SessionConstants;
import au.gov.qld.fire.web.WebUtils;

/*
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class HomeAction extends AbstractDispatchAction
{

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.web.module.AbstractDispatchAction#populateForm(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected void populateForm(ActionMapping mapping, ActionForm form, HttpServletRequest request)
        throws Exception
    {
        //form is session bound (id can be null, eg just created)
        HomeForm myform = (HomeForm) form;
        ActiveJob entity = myform.getEntity();
        //get entity id
        Long id = WebUtils.getLongId(request);
        if (id == null && entity != null)
        {
            //id = entity.getId();
        }
        //refresh
        if (id != null)
        {
            //entity = getJobService().findJobById(id);
            //myform.setEntity(entity);
        }
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
            String fileId = request.getParameter(SessionConstants.FILE_ID);
            String fcaId = request.getParameter(SessionConstants.FCA_ID);
            String buildingId = request.getParameter(SessionConstants.BUILDING_ID);
            String buildingName = request.getParameter(SessionConstants.BUILDING_NAME);

            JobSearchCriteria criteria = new JobSearchCriteria();
            criteria.setFileNo(fileId);
            criteria.setFcaNo(fcaId);
            criteria.setBuildingId(NumberUtils.toLong(buildingId, 0L));
            criteria.setBuildingName(buildingName);
            List<ActiveJob> entities = getJobService().findActiveJobs(criteria);

            request.setAttribute(SessionConstants.ENTITIES, entities);

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
            populateForm(mapping, form, request);
            return mapping.getInputForward();
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            return mapping.getInputForward();
        }
    }

}