package au.gov.qld.fire.jms.web.module.file;

import java.io.ByteArrayOutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import au.gov.qld.fire.domain.refdata.ContentTypeEnum;
import au.gov.qld.fire.jms.domain.action.ActionRequest;
import au.gov.qld.fire.jms.domain.action.BaseActionTodo;
import au.gov.qld.fire.jms.domain.action.MailSearchCriteria;
import au.gov.qld.fire.jms.domain.mail.MailBatch;
import au.gov.qld.fire.jms.domain.mail.MailStatusEnum;
import au.gov.qld.fire.web.SessionConstants;
import au.gov.qld.fire.web.WebUtils;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class MailOutAction extends MailBaseAction
{

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.jms.web.module.file.MailBaseAction#populateRequest(org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected void populateRequest(ActionForm form, HttpServletRequest request) throws Exception
	{
		super.populateRequest(form, request);
	}

    private List<BaseActionTodo> find(ActionForm form) throws Exception
    {
		MailForm myform = (MailForm) form;
        MailSearchCriteria criteria = myform.getCriteria();
        if (myform.isRejected()) {
        	criteria.setStatus(MailStatusEnum.RTS);
        } else {
        	final MailStatusEnum[] MAIL_STATUS = new MailStatusEnum[] {
            	MailStatusEnum.NONE,
            	MailStatusEnum.SENT,
            	MailStatusEnum.RECEIVED,
            };
        	criteria.setStatus(MAIL_STATUS);
        }
        List<BaseActionTodo> result = getActionService().findMailOutAction(criteria);
        myform.setActionCode(criteria.getActionCode());
        return result;
    }

	public ActionForward find(ActionMapping mapping, ActionForm form, HttpServletRequest request,
        HttpServletResponse response) throws Exception
    {
        try
        {
            request.setAttribute(SessionConstants.ENTITIES, find(form));
            populateRequest(form, request);
            return mapping.getInputForward();
        }
        catch (Exception e)
        {
        	saveExceptionJson(response, e);
            return null;
        }
    }

    public ActionForward findBatch(ActionMapping mapping, ActionForm form, HttpServletRequest request,
        HttpServletResponse response) throws Exception
    {
        try
        {
            MailForm myform = (MailForm) form;
            MailSearchCriteria criteria = myform.getCriteria();
            List<MailBatch> result = getActionService().findMailBatch(criteria);
            request.setAttribute(SessionConstants.ENTITIES, result);
        	final MailStatusEnum[] MAIL_STATUS = new MailStatusEnum[] {
            	MailStatusEnum.NONE,
            	MailStatusEnum.SENT,
            };
        	request.setAttribute(SessionConstants.STATUSES, MAIL_STATUS);
            return findForward(mapping, "findBatch");
        }
        catch (Exception e)
        {
        	saveExceptionJson(response, e);
            return null;
        }
    }

    public ActionForward findBatchFiles(ActionMapping mapping, ActionForm form, HttpServletRequest request,
        HttpServletResponse response) throws Exception
    {
        try
        {
            MailForm myform = (MailForm) form;
            MailSearchCriteria criteria = new MailSearchCriteria();
            criteria.setBatchId(myform.getBatchId());
        	final MailStatusEnum[] MAIL_STATUS = new MailStatusEnum[] {
            	MailStatusEnum.NONE,
            	MailStatusEnum.SENT,
            };
            criteria.setStatus(MAIL_STATUS);
            request.setAttribute(SessionConstants.ENTITIES, getActionService().findMailBatchFile(criteria));
            return findForward(mapping, "findBatchFiles");
        }
        catch (Exception e)
        {
        	saveExceptionJson(response, e);
            return null;
        }
    }

    public ActionForward createBatch(ActionMapping mapping, ActionForm form, HttpServletRequest request,
        HttpServletResponse response) throws Exception
    {
        try
        {
            String payload = getPayload(request);
            ActionRequest actionRequest = ActionRequest.fromByteArray(payload.getBytes());
            // create batch of incomplete actions
            MailBatch mailBatch = getActionService().createMailBatch(actionRequest.getActionCodeId(), actionRequest.getFileIds());
            request.setAttribute("batchId", mailBatch.getMailBatchId());
            request.setAttribute(SessionConstants.ENTITIES, find(form));
            return findForward(mapping, FIND);
        }
        catch (Exception e)
        {
        	saveExceptionJson(response, e);
            return null;
        }
    }

    public ActionForward completeBatch(ActionMapping mapping, ActionForm form, HttpServletRequest request,
        HttpServletResponse response) throws Exception
    {
        try
        {
            String payload = getPayload(request);
            ActionRequest actionRequest = ActionRequest.fromByteArray(payload.getBytes());
            // complete batch of incomplete actions and send email notification
            getActionService().notifyCompletedActions(getActionService().completeMailBatch(actionRequest.getBatchId(), actionRequest.getFileIds()));
            return findBatch(mapping, form, request, response);
        }
        catch (Exception e)
        {
        	saveExceptionJson(response, e);
            return null;
        }
    }

    public ActionForward export(ActionMapping mapping, ActionForm form, HttpServletRequest request,
        HttpServletResponse response) throws Exception
    {
        final ContentTypeEnum contentType = ContentTypeEnum.APPLICATION_CSV;
        try
        {
            String contentName = getContentName("mailOut.action.", contentType);
            ByteArrayOutputStream contentStream = new ByteArrayOutputStream();
            // add entities to contentStream
            getActionService().export(find(form), contentStream, contentType);
            WebUtils.downloadContent(response, contentStream, contentType.getContentType(), contentName);
            return null;
        }
        catch (Exception e)
        {
        	saveExceptionJson(response, e);
            return null;
        }
    }

}