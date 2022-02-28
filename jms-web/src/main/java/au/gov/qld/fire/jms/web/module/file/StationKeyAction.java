package au.gov.qld.fire.jms.web.module.file;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import au.gov.qld.fire.domain.location.Area;
import au.gov.qld.fire.domain.location.Region;
import au.gov.qld.fire.domain.location.Station;
import au.gov.qld.fire.jms.domain.Fca;
import au.gov.qld.fire.jms.domain.KeyReg;
import au.gov.qld.fire.jms.domain.file.File;
import au.gov.qld.fire.jms.web.module.AbstractDispatchAction;
import au.gov.qld.fire.web.SessionConstants;
import au.gov.qld.fire.web.WebUtils;

/*
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class StationKeyAction extends AbstractDispatchAction
{

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.web.module.AbstractDispatchAction#populateRequest(org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected void populateRequest(ActionForm form, HttpServletRequest request) throws Exception
    {
        FileForm myform = (FileForm) form;
        File entity = myform.getEntity();
        Fca fca = entity == null ? null : entity.getFca();
        Region region = fca == null ? null : fca.getRegion();
        request.setAttribute(SessionConstants.STATIONS, getEntityService().findStationsByRegion(
            region));
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.web.module.AbstractDispatchAction#populateForm(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected void populateForm(ActionMapping mapping, ActionForm form, HttpServletRequest request)
        throws Exception
    {
        FileForm myform = (FileForm) form;
        setEntity(myform, request);
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
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward changeStation(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE changeStation()..");
        try
        {
            //set form data
            FileForm myform = (FileForm) form;
            File file = myform.getEntity();
            Fca fca = file.getFca();
            //refresh Fca (station change)
            getFcaService().refreshFca(fca);
            if (fca.getStation() == null)
            {
                fca.setStation(new Station());
            }
            if (fca.getArea() == null)
            {
                fca.setArea(new Area());
            }
            //
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
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward addKeyReg(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE addKeyReg()..");
        try
        {
            //set form data
            FileForm myform = (FileForm) form;
            File file = myform.getEntity();
            file.add(new KeyReg());
            //
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
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward removeKeyReg(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE removeKeyReg()..");
        try
        {
            //get index to remove
            int index = WebUtils.getIndex(request);
            //set form data
            FileForm myform = (FileForm) form;
            File file = myform.getEntity();
            KeyReg keyReg = file.getKeyRegs().get(index);
            file.getKeyRegs().remove(keyReg);
            //
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
     * Save changes (if any).
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
            // save Fca changes
            getFcaService().saveFca(file.getFca());
            // save KeyReceipt/KeyReg(s) changes
            getFileService().saveKeyReceipt(file.getFileId(),
            	file.getBuilding().isKeyRequired(), file.getKeyReceipt(), file.getKeyRegs());
            // save Building changes (should exist by now) - entity.building.keyRequired
            //getFileService().saveBuilding(file.getBuilding());

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