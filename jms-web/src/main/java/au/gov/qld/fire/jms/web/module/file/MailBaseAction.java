package au.gov.qld.fire.jms.web.module.file;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import au.gov.qld.fire.domain.refdata.DateOptionEnum;
import au.gov.qld.fire.jms.domain.action.MailSearchCriteria;
import au.gov.qld.fire.jms.domain.mail.MailStatusEnum;
import au.gov.qld.fire.jms.domain.refdata.ActionCode;
import au.gov.qld.fire.jms.domain.refdata.ActionTypeEnum;
import au.gov.qld.fire.jms.domain.refdata.OwnerTypeEnum;
import au.gov.qld.fire.jms.web.module.AbstractDispatchAction;
import au.gov.qld.fire.web.SessionConstants;
import au.gov.qld.fire.web.WebUtils;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public abstract class MailBaseAction extends AbstractDispatchAction
{

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.web.module.AbstractDispatchAction#populateRequest(org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected void populateRequest(ActionForm form, HttpServletRequest request)
        throws Exception
    {
        request.setAttribute(SessionConstants.CRITERIA, form);
        List<ActionCode> actionCodes = new ArrayList<ActionCode>();
        for (ActionTypeEnum actionType : MailSearchCriteria.ACTION_TYPE) {
        	List<ActionCode> items = getEntityService().findActionCodeByActionType(actionType);
        	// filter action code(s) where one of templates is pdfForm
        	for (ActionCode item : items) {
        		item = getEntityService().findActionCodeById(item.getId());
        		if (item.isBulkMail() && item.isPdfForm()) {
                    actionCodes.add(item);
        		}
        	}
        }
        request.setAttribute(SessionConstants.ACTION_CODES, actionCodes);
        request.setAttribute(SessionConstants.REGIONS, getEntityService().findRegionsActive());
        request.setAttribute(SessionConstants.DATE_OPTIONS, DateOptionEnum.values());
        request.setAttribute(SessionConstants.OWNER_TYPES, OwnerTypeEnum.values());
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.web.module.AbstractDispatchAction#populateForm(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected void populateForm(ActionMapping mapping, ActionForm form, HttpServletRequest request)
        throws Exception
    {

    }

	public ActionForward saveStatus(ActionMapping mapping, ActionForm form, HttpServletRequest request,
        HttpServletResponse response) throws Exception
    {
        try
        {
        	Long id = WebUtils.getLongId(request);
        	String value = WebUtils.getStringValue(request);
        	MailStatusEnum status = MailStatusEnum.findByName(value);
        	getActionService().saveMailStatus(id, status);
            return null;
        }
        catch (Exception e)
        {
        	saveExceptionJson(response, e);
            return null;
        }
    }

}