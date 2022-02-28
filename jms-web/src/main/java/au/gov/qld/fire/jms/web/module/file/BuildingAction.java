package au.gov.qld.fire.jms.web.module.file;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import au.gov.qld.fire.domain.location.State;
import au.gov.qld.fire.jms.domain.building.Building;
import au.gov.qld.fire.jms.domain.refdata.BuildingClassification;
import au.gov.qld.fire.jms.web.module.AbstractDispatchAction;
import au.gov.qld.fire.web.SessionConstants;
import au.gov.qld.fire.web.WebUtils;

/*
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class BuildingAction extends AbstractDispatchAction
{

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.web.module.AbstractDispatchAction#populateRequest(org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected void populateRequest(ActionForm form, HttpServletRequest request) throws Exception
    {
        request.setAttribute(SessionConstants.STATES, getEntityService().findStates());
        request.setAttribute(SessionConstants.BUILDING_CLASSIFICATIONS, getEntityService().findBuildingClassificationsActive());
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.web.module.AbstractDispatchAction#populateForm(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected void populateForm(ActionMapping mapping, ActionForm form, HttpServletRequest request)
        throws Exception
    {
        FileForm myform = (FileForm) form;
        // refresh
        Building entity = myform.getBuilding();
        entity = getFileService().findBuildingById(entity.getId());
        myform.setBuilding(entity);
        // set default state (if empty)
        if (entity.getAddress().getState().getId() == null)
        {
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

    public ActionForward addBuildingClassification(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE addBuildingClassification()..");
        try
        {
            //set form data
            FileForm myform = (FileForm) form;
            //
            BuildingClassification buildingClassification = new BuildingClassification();
            myform.getBuilding().getClassifications().add(buildingClassification);
            //
            populateRequest(form, request);
            return mapping.getInputForward();
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            //populateForm(mapping, form, request);
            populateRequest(form, request);
            return mapping.getInputForward();
        }
    }

    public ActionForward removeBuildingClassification(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE removeBuildingClassification()..");
        try
        {
            //get index to remove
            int index = WebUtils.getIndex(request);
            //set form data
            FileForm myform = (FileForm) form;
            BuildingClassification buildingClassification = myform.getBuilding().getClassifications().get(index);
            myform.getBuilding().getClassifications().remove(buildingClassification);
            //
            populateRequest(form, request);
            return mapping.getInputForward();
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            //populateForm(mapping, form, request);
            populateRequest(form, request);
            return mapping.getInputForward();
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
            Building entity = myform.getBuilding();
            //save changes (if any)
            getFileService().saveBuilding(entity);

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