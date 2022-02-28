package au.gov.qld.fire.jms.web.module.file;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import au.gov.qld.fire.domain.document.Document;
import au.gov.qld.fire.domain.document.Template;
import au.gov.qld.fire.domain.refdata.ContentTypeEnum;
import au.gov.qld.fire.domain.security.User;
import au.gov.qld.fire.domain.supplier.Supplier;
import au.gov.qld.fire.domain.document.TemplateEnum;
import au.gov.qld.fire.jms.domain.ase.AseChangeCalendar;
import au.gov.qld.fire.jms.domain.ase.AseChangeCalendarItem;
import au.gov.qld.fire.jms.domain.file.FileSearchCriteria;
import au.gov.qld.fire.jms.domain.report.ReportSearchCriteria;
import au.gov.qld.fire.jms.web.module.AbstractDispatchAction;
import au.gov.qld.fire.util.DateUtils;
import au.gov.qld.fire.util.ThreadLocalUtils;
import au.gov.qld.fire.web.SessionConstants;
import au.gov.qld.fire.web.WebUtils;

/*
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class AseChangeCalendarAction extends AbstractDispatchAction
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
        //Limit Search by Supplier Name
        User user = getUserService().findUserById(ThreadLocalUtils.getUser().getId());
        Supplier supplier = user.getSupplier();
        String supplierName = supplier == null ? null : supplier.getName();

        FileSearchCriteria criteria = new FileSearchCriteria();
        criteria.setFileNo(fileId);
        criteria.setSupplierName(supplierName);

        AseChangeCalendar entity = new AseChangeCalendar(WebUtils.getYear(request), WebUtils
            .getMonth(request));
        for (List<AseChangeCalendarItem> items : entity.getItems())
        {
            for (AseChangeCalendarItem item : items)
            {
                if (item != null)
                {
                    item.setItems(getFileService().findAseChangeByDateChange(criteria,
                        item.getDate()));
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
     * Present print.
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward print(ActionMapping mapping, ActionForm form, HttpServletRequest request,
        HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE print()..");
        try
        {
            int year = WebUtils.getYear(request);
            int month = WebUtils.getMonth(request);
            //calendar day selection (can be empty/null for whole month)
            Long id = WebUtils.getLongId(request);

            int day = id == null ? 1 : id.intValue();
            Date dateStart = DateUtils.D_M_YYYY.parse(day + "/" + (month + 1) + "/" + year);
            Date dateEnd = id == null ? DateUtils.addMonths(dateStart, 1) : DateUtils.addDays(
                dateStart, 1);

            ReportSearchCriteria criteria = new ReportSearchCriteria();
            criteria.setDateStart(dateStart);
            criteria.setDateEnd(dateEnd);
            //Limit Search by Supplier Name
            User user = getUserService().findUserById(ThreadLocalUtils.getUser().getId());
            Supplier supplier = user.getSupplier();
            criteria.setSupplierId(supplier == null ? null : supplier.getId());
            //create document (do not save to db)
            Template reportTemplate = getEntityService().getTemplate(TemplateEnum.REPORT_ASE_CHANGE);
            Document document = getDocumentService().createReportDocument(reportTemplate, criteria);

            ContentTypeEnum contentType = ContentTypeEnum.APPLICATION_PDF;
            String contentName = document.getName() + "." + document.getId() + "." + contentType.getDefaultExt();
            WebUtils.downloadContent(response, document.getContent(), contentType.getContentType(), contentName);

            return null;
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            return findForwardError(mapping);
        }
    }

}