package au.gov.qld.fire.jms.web.module.file;

import java.io.ByteArrayOutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import au.gov.qld.fire.domain.refdata.ContentTypeEnum;
import au.gov.qld.fire.jms.domain.action.BaseActionTodo;
import au.gov.qld.fire.jms.domain.action.EnquirySearchCriteria;
import au.gov.qld.fire.jms.web.module.AbstractDispatchAction;
import au.gov.qld.fire.util.ThreadLocalUtils;
import au.gov.qld.fire.web.SessionConstants;
import au.gov.qld.fire.web.WebUtils;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class EnquiryAction extends AbstractDispatchAction
{

    /* (non-Javadoc)
	 * @see au.gov.qld.fire.jms.web.module.AbstractDispatchAction#populateRequest(org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected void populateRequest(ActionForm form, HttpServletRequest request)	throws Exception
	{
        request.setAttribute(SessionConstants.ACTION_CODES,
            getEntityService().findActionCodeByActionType(EnquirySearchCriteria.ACTION_TYPE));
	}

	/* (non-Javadoc)
     * @see au.gov.qld.fire.jms.web.module.AbstractDispatchAction#populateForm(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected void populateForm(ActionMapping mapping, ActionForm form, HttpServletRequest request)
        throws Exception
    {

    }

    public ActionForward find(ActionMapping mapping, ActionForm form, HttpServletRequest request,
        HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE find().. ");
        try
        {
        	List<BaseActionTodo> entities = find(request);
            request.setAttribute(SessionConstants.ENTITIES, entities);
            String completed = request.getParameter(SessionConstants.COMPLETED);
            request.setAttribute(SessionConstants.COMPLETED, Boolean.valueOf(completed));

        	populateRequest(form, request);
            return mapping.getInputForward();
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            return mapping.getInputForward();
        }
    }

    public ActionForward export(ActionMapping mapping, ActionForm form, HttpServletRequest request,
        HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE export()..");
        final ContentTypeEnum contentType = ContentTypeEnum.APPLICATION_CSV;
        try
        {
            List<BaseActionTodo> entities = find(request);
            String contentName = getContentName("enquiry.action.", contentType);
            ByteArrayOutputStream contentStream = new ByteArrayOutputStream();
            // add entities to contentStream
            getActionService().export(entities, contentStream, contentType);
            WebUtils.downloadContent(response, contentStream, contentType.getContentType(), contentName);

            return null;
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            return null;
        }
    }

    private List<BaseActionTodo> find(HttpServletRequest request) throws Exception
    {
        String fileId = request.getParameter(SessionConstants.FILE_ID);
        String fcaId = request.getParameter(SessionConstants.FCA_ID);
        String completed = request.getParameter(SessionConstants.COMPLETED);
        String workGroup = request.getParameter(SessionConstants.WORK_GROUP);
        String actionCode = request.getParameter(SessionConstants.ACTION_CODE);
        long actionCodeId = NumberUtils.toLong(request.getParameter(SessionConstants.ACTION_CODE_ID), 0);
        Long responsibleUserId = ThreadLocalUtils.getUser().getId();

        EnquirySearchCriteria criteria = new EnquirySearchCriteria();
        criteria.setFileNo(fileId);
        criteria.setFcaNo(fcaId);
        criteria.setCompleted(Boolean.valueOf(completed));
        criteria.setWorkGroup(workGroup);
        criteria.setActionCode(actionCode);
        criteria.setActionCodeId(actionCodeId > 0 ? actionCodeId : null);
        criteria.setResponsibleUserId(responsibleUserId);
        return getActionService().findEnquiryAction(criteria);
    }

}