package au.gov.qld.fire.jms.web.module.report;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import au.gov.qld.fire.domain.document.Document;
import au.gov.qld.fire.domain.document.Template;
import au.gov.qld.fire.domain.refdata.ContentTypeEnum;
import au.gov.qld.fire.domain.refdata.TemplateTypeEnum;
import au.gov.qld.fire.domain.report.ReportDoc;
import au.gov.qld.fire.domain.security.User;
import au.gov.qld.fire.domain.supplier.Supplier;
import au.gov.qld.fire.jms.domain.report.ReportSearchCriteria;
import au.gov.qld.fire.jms.web.module.AbstractDispatchAction;
import au.gov.qld.fire.util.ThreadLocalUtils;
import au.gov.qld.fire.web.SessionConstants;
import au.gov.qld.fire.web.WebUtils;

/*
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class ReportAction extends AbstractDispatchAction
{

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.web.module.AbstractDispatchAction#populateRequest(org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected void populateRequest(ActionForm form, HttpServletRequest request) throws Exception
    {
        //set references
        request.setAttribute(SessionConstants.WORK_GROUPS, getEntityService().findWorkGroupsActive());
        request.setAttribute(SessionConstants.ACTION_CODES, getEntityService().findActionCodesActive());
        //Limit Search by Supplier Name
        User user = getUserService().findUserById(ThreadLocalUtils.getUser().getId());
        Supplier supplier = user.getSupplier();
        if (supplier == null)
        {
            request.setAttribute(SessionConstants.SUPPLIERS, getSupplierService().findSuppliers());
        }
        //
        ReportForm myform = (ReportForm) form;
        String[] reportParameters = getDocumentService().getReportParameters(myform.getEntity());
        if (reportParameters != null) {
            Properties parameters = new Properties();
            for (String reportParameter : reportParameters)
            {
                parameters.setProperty(reportParameter, "true");
            }
            request.setAttribute("parameters", parameters);
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.web.module.AbstractDispatchAction#populateForm(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected void populateForm(ActionMapping mapping, ActionForm form, HttpServletRequest request)
        throws Exception
    {
        //get entity id
        ReportForm myform = (ReportForm) form;
        Long templateId = WebUtils.getLongId(request);
        if (templateId == null)
        {
            templateId = myform.getEntity().getId();
        }
        Template entity = getEntityService().findTemplateById(templateId);
        myform.setEntity(entity);
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
            List<Template> entities = new ArrayList<Template>();
            entities.addAll(getEntityService().findTemplates(TemplateTypeEnum.REPORT_CRYSTAL));
            entities.addAll(getEntityService().findTemplates(TemplateTypeEnum.REPORT_FOP));
            entities.addAll(getEntityService().findTemplates(TemplateTypeEnum.PDF_FORM));
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
            populateRequest(form, request);

            return mapping.getInputForward();
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            return mapping.getInputForward();
        }
    }

    /**
     * Find reports for logged user by template.
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward findReportDoc(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE findReportDoc()..");
        try
        {
            populateForm(mapping, form, request);

            ReportForm myform = (ReportForm) form;
            List<ReportDoc> reportDocs = getDocumentService().findReportDocs(myform.getEntity());
            myform.setReportDocs(reportDocs);

            return mapping.getInputForward();
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            return mapping.getInputForward();
        }
    }

    /**
     * Present view entity form.
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward viewReportDoc(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE viewReportDoc()..");
        try
        {
            Long reportDocId = WebUtils.getLongId(request);
            ReportDoc reportDoc = getDocumentService().findReportDocById(reportDocId);
            ReportForm myform = (ReportForm) form;
            Template reportTemplate = reportDoc.getTemplate();
            myform.setEntity(reportTemplate);
            myform.setReportDoc(reportDoc);

            return mapping.getInputForward();
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            return mapping.getInputForward();
        }
    }

    /**
     * Generate report.
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward generateReportDoc(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE generateReportDoc()..");
        try
        {
            ReportForm myform = (ReportForm) form;
            Template entity = myform.getEntity();
            ReportSearchCriteria criteria = myform.getCriteria();
            //Limit Search by Supplier Name
            User user = getUserService().findUserById(ThreadLocalUtils.getUser().getId());
            Supplier supplier = user.getSupplier();
            if (supplier != null)
            {
                criteria.setSupplierId(supplier.getId());
            }

            //create report document
            Document document = getDocumentService().createReportDocument(entity, criteria);
            ReportDoc reportDoc = myform.getReportDoc();
            reportDoc.setDocument(document);
            //and save
            getDocumentService().saveReportDoc(reportDoc);

            return findForwardSuccess(mapping);
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
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
    public ActionForward downloadReportDoc(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE downloadReportDoc()..");
        try
        {
            //get reportDocId
            Long reportDocId = WebUtils.getLongId(request);
            ReportDoc reportDoc = getDocumentService().findReportDocById(reportDocId);
            Document document = reportDoc.getDocument();

            ContentTypeEnum contentType = document.getContentTypeEnum();
            String contentName = document.getName() + "." + document.getId() + "."
                + (contentType == null ? document.getContentType() : contentType.getDefaultExt());
            WebUtils.downloadContent(response, document.getContent(), document.getContentType(), contentName);

            return null;
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            return findForwardError(mapping);
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
    public ActionForward deleteReportDoc(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        LOG.debug("INSIDE deleteReportDoc()..");
        try
        {
            ReportForm myform = (ReportForm) form;
            ReportDoc reportDoc = myform.getReportDoc();
            getDocumentService().deleteReportDoc(reportDoc);

            return findForwardSuccess(mapping);
            //return mapping.findForward(SUCCESS);
        }
        catch (Exception e)
        {
            saveErrors(request, response, toActionErrors(e));
            return mapping.getInputForward();
        }
    }

}