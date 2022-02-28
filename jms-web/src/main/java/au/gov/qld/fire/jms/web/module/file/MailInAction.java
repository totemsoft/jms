package au.gov.qld.fire.jms.web.module.file;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import au.gov.qld.fire.domain.refdata.ContentTypeEnum;
import au.gov.qld.fire.jms.domain.action.MailSearchCriteria;
import au.gov.qld.fire.jms.domain.mail.MailBatchFile;
import au.gov.qld.fire.jms.domain.mail.MailStatusEnum;
import au.gov.qld.fire.web.SessionConstants;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class MailInAction extends MailBaseAction
{

	/* (non-Javadoc)
	 * @see au.gov.qld.fire.jms.web.module.file.MailBaseAction#populateRequest(org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected void populateRequest(ActionForm form, HttpServletRequest request) throws Exception
	{
		super.populateRequest(form, request);
	    final MailStatusEnum[] MAIL_STATUS = new MailStatusEnum[] {
	    	MailStatusEnum.SENT,
	    	MailStatusEnum.RECEIVED,
	    	MailStatusEnum.RTS
	    };
    	request.setAttribute(SessionConstants.STATUSES, MAIL_STATUS);
	}

	protected List<MailBatchFile> find(ActionForm form) throws Exception
    {
		MailForm myform = (MailForm) form;
        MailSearchCriteria criteria = myform.getCriteria();
        if (myform.isRejected()) {
        	criteria.setStatus(MailStatusEnum.RTS);
        } else {
    	    final MailStatusEnum[] MAIL_STATUS = new MailStatusEnum[] {
		    	MailStatusEnum.SENT,
		    	MailStatusEnum.RECEIVED,
		    };
            criteria.setStatus(MAIL_STATUS);
        }
        List<MailBatchFile> result = getActionService().findMailBatchFile(criteria);
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

    public ActionForward export(ActionMapping mapping, ActionForm form, HttpServletRequest request,
        HttpServletResponse response) throws Exception
    {
        final ContentTypeEnum contentType = ContentTypeEnum.APPLICATION_CSV;
        try
        {
//            String contentName = getContentName("mailIn.action.", contentType);
//            ByteArrayOutputStream contentStream = new ByteArrayOutputStream();
//            // add entities to contentStream
//            getActionService().export(find(form), contentStream, contentType);
//            WebUtils.downloadContent(response, contentStream, contentType.getContentType(), contentName);
            return null;
        }
        catch (Exception e)
        {
        	saveExceptionJson(response, e);
            return null;
        }
    }

}