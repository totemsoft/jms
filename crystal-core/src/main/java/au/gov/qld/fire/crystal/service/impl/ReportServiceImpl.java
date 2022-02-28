package au.gov.qld.fire.crystal.service.impl;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import au.gov.qld.fire.crystal.CRDatabaseHelper;
import au.gov.qld.fire.domain.document.Document;
import au.gov.qld.fire.domain.document.Template;
import au.gov.qld.fire.domain.refdata.ContentTypeEnum;
import au.gov.qld.fire.domain.report.ReportSearchCriteria;
import au.gov.qld.fire.service.ReportService;
import au.gov.qld.fire.service.ServiceException;

import com.crystaldecisions.sdk.occa.report.application.OpenReportOptions;
import com.crystaldecisions.sdk.occa.report.application.ParameterFieldController;
import com.crystaldecisions.sdk.occa.report.application.ReportClientDocument;
import com.crystaldecisions.sdk.occa.report.data.Fields;
import com.crystaldecisions.sdk.occa.report.data.IParameterField;
import com.crystaldecisions.sdk.occa.report.data.ParameterField;
import com.crystaldecisions.sdk.occa.report.data.ParameterFieldDiscreteValue;
import com.crystaldecisions.sdk.occa.report.data.ParameterValueRangeKind;
import com.crystaldecisions.sdk.occa.report.exportoptions.ExportOptions;
import com.crystaldecisions.sdk.occa.report.exportoptions.PDFExportFormatOptions;
import com.crystaldecisions.sdk.occa.report.exportoptions.ReportExportFormat;

/**
 * @author vchibaev (Valeri SHIBAEV)
 */
public class ReportServiceImpl implements ReportService
{

    /** logger. */
    private static final Logger LOG = Logger.getLogger(ReportServiceImpl.class);

    //@Autowired private TemplateDao templateDao;

    @Autowired private CRDatabaseHelper crDatabaseHelper;

    //@Autowired private CRExportHelper crExportHelper;

    /* (non-Javadoc)
     * @see au.gov.qld.fire.service.ReportService#saveReport(au.gov.qld.fire.domain.document.Template)
     */
    public void saveReport(Template reportTemplate) throws ServiceException
    {
        String reportName = reportTemplate.getCode();
        long reportVersion = reportTemplate.getLockVersion();
        byte[] reportContent = reportTemplate.getContent();
        // save to ${crystal.reportlocation} directory
        try
        {
            crDatabaseHelper.saveReport(reportName, reportVersion, reportContent);
        }
        catch (IOException e)
        {
            LOG.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage());
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.service.ReportService#getReportParameters(au.gov.qld.fire.domain.document.Template)
     */
    public String[] getReportParameters(Template reportTemplate)
        throws ServiceException
    {
        try
        {
            ReportClientDocument clientDoc = new ReportClientDocument();
            String reportName = reportTemplate.getCode() + ".rpt";
            clientDoc.open(reportName, OpenReportOptions._openAsReadOnly);
            //crDatabaseHelper.changeDataSource(clientDoc);
            //
            // get parameters (if any)
            Fields<IParameterField> parameterFields = clientDoc.getDataDefController().getDataDefinition().getParameterFields();
            List<String> result = new ArrayList<String>();
            for (IParameterField parameterField : parameterFields)
            {
                String name = parameterField.getName();
                ParameterValueRangeKind kind = parameterField.getValueRangeKind();
                if (ParameterValueRangeKind.discrete == kind)
                {
                    result.add(name);
                }
                else
                {
                    LOG.warn("Unhandled ParameterValueRangeKind: " + kind + ", name=" + name);
                }
            }
            return result.toArray(new String[0]);
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage());
        }
    }

    /* (non-Javadoc)
     * @see au.gov.qld.fire.service.ReportService#createReportDocument(au.gov.qld.fire.domain.document.Template, au.gov.qld.fire.domain.report.ReportSearchCriteria)
     */
    public Document createReportDocument(Template reportTemplate, ReportSearchCriteria criteria)
        throws ServiceException
    {
        //
        //saveReport(reportTemplate);
        //
        Document document = new Document();
        document.setName(reportTemplate.getName());
        document.setDescription(reportTemplate.getDescription());
        document.setContentType(ContentTypeEnum.APPLICATION_PDF.getContentType());
        //
        InputStream is = null;
        try
        {
            ReportClientDocument clientDoc = new ReportClientDocument();
            String reportName = reportTemplate.getCode() + ".rpt";
            clientDoc.open(reportName, OpenReportOptions._openAsReadOnly);
            crDatabaseHelper.changeDataSource(clientDoc);
            //
            // set parameters (if any)
            Fields<IParameterField> parameterFields = clientDoc.getDataDefController().getDataDefinition().getParameterFields();
            for (IParameterField parameterField : parameterFields)
            {
                String name = parameterField.getName();
                // Make a copy of the parameter
                ParameterField newParameterField = new ParameterField();
                parameterField.copyTo(newParameterField, true);
                newParameterField.getCurrentValues().removeAllElements();
                //
                ParameterValueRangeKind kind = parameterField.getValueRangeKind();
                if (ParameterValueRangeKind.discrete == kind)
                {
                    String value = criteria.getCustomParameters().getProperty(name);
                    ParameterFieldDiscreteValue discreteValue = new ParameterFieldDiscreteValue();
                    discreteValue.setValue(value);
                    newParameterField.getCurrentValues().add(discreteValue);
                    // modify the original parameter field
                    ParameterFieldController paramController = clientDoc.getDataDefController().getParameterFieldController();
                    paramController.modify(parameterField, newParameterField);
                }
                else
                {
                    LOG.warn("Unhandled ParameterValueRangeKind: " + kind + ", name=" + name);
                }
            }
            //
            // PDF export allows page range export. The following routine ensures that the requested page range is valid
            PDFExportFormatOptions pdfOptions = new PDFExportFormatOptions();
            //pdfOptions.setStartPageNumber(startPage);
            //pdfOptions.setEndPageNumber(endPage);
            ExportOptions exportOptions = new ExportOptions();
            exportOptions.setExportFormatType(ReportExportFormat.PDF);
            exportOptions.setFormatOptions(pdfOptions);
            is = new BufferedInputStream(clientDoc.getPrintOutputController().export(exportOptions));
            document.setContent(IOUtils.toByteArray(is));
            return document;
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage());
        }
        finally
        {
            IOUtils.closeQuietly(is);
        }
    }

}