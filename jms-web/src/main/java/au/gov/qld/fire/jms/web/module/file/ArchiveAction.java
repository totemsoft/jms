package au.gov.qld.fire.jms.web.module.file;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import au.gov.qld.fire.jms.domain.file.Archive;
import au.gov.qld.fire.jms.domain.file.FileArchiveSearchCriteria;
import au.gov.qld.fire.jms.web.module.AbstractDispatchAction;
import au.gov.qld.fire.util.DateUtils;
import au.gov.qld.fire.web.SessionConstants;
import au.gov.qld.fire.web.WebUtils;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class ArchiveAction extends AbstractDispatchAction
{

    /* (non-Javadoc)
	 * @see au.gov.qld.fire.jms.web.module.AbstractDispatchAction#populateRequest(org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected void populateRequest(ActionForm form, HttpServletRequest request) throws Exception
	{

	}

	/* (non-Javadoc)
     * @see au.gov.qld.fire.jms.web.module.AbstractDispatchAction#populateForm(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected void populateForm(ActionMapping mapping, ActionForm form, HttpServletRequest request)
        throws Exception
    {
    	ArchiveForm myform = (ArchiveForm) form;
    	Long id = WebUtils.getLongId(request);
    	Archive entity = getFileService().findArchive(id);
        entity = entity == null ? new Archive() : entity;
    	myform.setEntity(entity);
    	if (entity.getId() != null)
    	{
            request.setAttribute(SessionConstants.ENTITY, myform.getEntity());
    	}
    }

    public ActionForward find(ActionMapping mapping, ActionForm form, HttpServletRequest request,
        HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE find().. ");
        try
        {
        	FileArchiveSearchCriteria criteria = new FileArchiveSearchCriteria();
            String date = request.getParameter("date");
            criteria.setDate(DateUtils.parse(date, DateUtils.DD_MM_YYYY));
            String fileId = request.getParameter(SessionConstants.FILE_ID);
            criteria.setFileId(NumberUtils.isDigits(fileId) ? new Long(fileId) : null);
            String fcaId = request.getParameter(SessionConstants.FCA_ID);
            criteria.setFcaId(fcaId);
            String sapCustNo = request.getParameter("sapCustNo");
            criteria.setSapCustNo(NumberUtils.isDigits(sapCustNo) ? new Long(sapCustNo) : null);
            List<Archive> entities = getFileService().findArchives(criteria);
            request.setAttribute(SessionConstants.ENTITIES, entities);
            return mapping.getInputForward();
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            return mapping.getInputForward();
        }
    }

    public ActionForward view(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE view()..");
        try
        {
        	populateForm(mapping, form, request);
            return findForward(mapping, VIEW);
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            return mapping.getInputForward();
        }
    }

    public ActionForward edit(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        {
            LOG.debug("INSIDE edit().. ");
            try
            {
            	populateForm(mapping, form, request);
            	//populateRequest(form, request);
                return findForward(mapping, EDIT);
            }
            catch (Exception e)
            {
                saveErrors(request, response, toActionErrors(e));
                return findForward(mapping, EDIT);
            }
        }
    }

    public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
        HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE save()..");
        try
        {
            ArchiveForm myform = (ArchiveForm) form;
            ActionErrors errors = form.validate(mapping, request);
            if (!errors.isEmpty())
            {
                saveErrors(request, response, errors);
                //populateRequest(form, request);
                return findForward(mapping, EDIT);
            }
            Archive entity = myform.getEntity();
            getFileService().saveArchive(entity);
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