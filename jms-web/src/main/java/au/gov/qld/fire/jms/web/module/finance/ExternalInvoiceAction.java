package au.gov.qld.fire.jms.web.module.finance;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import au.gov.qld.fire.jms.domain.finance.Invoice;
import au.gov.qld.fire.jms.domain.finance.InvoiceSearchCriteria;
import au.gov.qld.fire.jms.domain.finance.InvoiceType.InvoiceTypeEnum;
import au.gov.qld.fire.jms.web.module.AbstractDispatchAction;
import au.gov.qld.fire.web.SessionConstants;
import au.gov.qld.fire.web.WebUtils;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class ExternalInvoiceAction extends AbstractDispatchAction
{

    /* (non-Javadoc)
	 * @see au.gov.qld.fire.jms.web.module.AbstractDispatchAction#populateRequest(org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest)
	 */
	protected void populateRequest(ActionForm form, HttpServletRequest request)
	    throws Exception
	{
        //ExternalInvoiceForm myform = (ExternalInvoiceForm) form;
	}

	/* (non-Javadoc)
     * @see au.gov.qld.fire.jms.web.module.AbstractDispatchAction#populateForm(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest)
     */
    protected void populateForm(ActionMapping mapping, ActionForm form, HttpServletRequest request)
        throws Exception
    {
        //ExternalInvoiceForm myform = (ExternalInvoiceForm) form;
    }

    /*
     * Tab1 load action
     */
    public ActionForward find(ActionMapping mapping, ActionForm form, HttpServletRequest request,
        HttpServletResponse response) throws Exception
    {
        try
        {
        	Long invoiceTypeId = new Long(InvoiceTypeEnum.QUOTE.ordinal()); // hard-coded: assessment
        	Long invoiceAreaId = WebUtils.getLongId(request, "invoiceAreaId");
        	//
        	List<Integer> fiscalYears = getInvoiceService().findFiscalYears();
            request.setAttribute("fiscalYears", fiscalYears);
        	//
        	InvoiceSearchCriteria criteria = new InvoiceSearchCriteria();
        	Integer fiscalYear = WebUtils.getYear(request);
        	if (fiscalYear <= 0)
        	{
        		fiscalYear = fiscalYears.isEmpty() ? null : fiscalYears.get(0);
        	}
        	criteria.setFiscalYear(fiscalYear);
        	criteria.setInvoiceTypeId(invoiceTypeId.intValue());
        	criteria.setInvoiceAreaId(invoiceAreaId);
        	List<Invoice> entities = getInvoiceService().findInvoiceByCriteria(criteria);
            request.setAttribute(SessionConstants.ENTITIES, entities);
            return mapping.getInputForward();
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            return mapping.getInputForward();
        }
    }

    public ActionForward addBatch(ActionMapping mapping, ActionForm form, HttpServletRequest request,
        HttpServletResponse response) throws Exception
    {
        try
        {
        	Long invoiceTypeId = new Long(InvoiceTypeEnum.QUOTE.ordinal()); // hard-coded: assessment
        	Long invoiceAreaId = WebUtils.getLongId(request, "invoiceAreaId");
        	/*InvoiceBatch batch = */getInvoiceService().createBatch(invoiceTypeId, invoiceAreaId);
        	return mapping.findForward("findReload");
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            return findForwardError(mapping);
        }
    }

    public ActionForward addInvoice(ActionMapping mapping, ActionForm form, HttpServletRequest request,
        HttpServletResponse response) throws Exception
    {
    	ExternalInvoiceForm myform = (ExternalInvoiceForm) form;
        try
        {
        	Long batchId = myform.getId();
        	Invoice invoice = getInvoiceService().createInvoice(batchId);
        	myform.setId(invoice.getId());
            return mapping.findForward("editInvoiceReload");
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            return findForwardError(mapping);
        }
    }

    public ActionForward editInvoice(ActionMapping mapping, ActionForm form, HttpServletRequest request,
        HttpServletResponse response) throws Exception
    {
    	ExternalInvoiceForm myform = (ExternalInvoiceForm) form;
        try
        {
        	Long invoiceId = myform.getId();
        	Invoice invoice = getInvoiceService().findInvoiceById(invoiceId);
        	myform.setEntity(invoice);
            return mapping.findForward("editInvoice");
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            return findForwardError(mapping);
        }
    }

    public ActionForward viewInvoice(ActionMapping mapping, ActionForm form, HttpServletRequest request,
        HttpServletResponse response) throws Exception
    {
    	ExternalInvoiceForm myform = (ExternalInvoiceForm) form;
        try
        {
        	Long invoiceId = myform.getId();
        	Invoice invoice = getInvoiceService().findInvoiceById(invoiceId);
        	request.setAttribute(SessionConstants.ENTITY, invoice);
            return mapping.findForward("viewInvoice");
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            return findForwardError(mapping);
        }
    }

    public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
        HttpServletResponse response) throws Exception
    {
    	ExternalInvoiceForm myform = (ExternalInvoiceForm) form;
        try
        {
            ActionErrors errors = form.validate(mapping, request);
            if (!errors.isEmpty())
            {
                saveErrors(request, response, errors);
                populateRequest(form, request);
                return findForwardError(mapping);
            }
            Invoice invoice = myform.getEntity();
            getInvoiceService().saveInvoice(invoice);
            //
            return null; // mapping.findForward("findReload");
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            populateRequest(form, request);
            populateForm(mapping, form, request);
            return findForwardError(mapping);
        }
    }

}