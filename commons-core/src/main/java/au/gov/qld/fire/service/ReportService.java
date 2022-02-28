package au.gov.qld.fire.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import au.gov.qld.fire.domain.document.Document;
import au.gov.qld.fire.domain.document.Template;
import au.gov.qld.fire.domain.report.ReportSearchCriteria;

/**
 *
 * :: VERY IMPORTANT :: No Transaction Here ::
 * SAP Crystal Reports
 * reports.sdk.JRCCommunicationAdapter :  detected an exception: Unexpected database connector error
 *     at com.crystaldecisions.reports.queryengine.Table.u7(SourceFile:2409)
 * 
 * @author vchibaev (Valeri SHIBAEV)
 */
@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
public interface ReportService
{
    /**
     * 
     * @param reportTemplate
     * @throws ServiceException
     */
    void saveReport(Template reportTemplate) throws ServiceException;

    /**
     * 
     * @param reportTemplate
     * @throws ServiceException
     * @return
     */
    String[] getReportParameters(Template reportTemplate) throws ServiceException;

    /**
     * Create report document based on template and using report criteria.
     * @param reportTemplate Report template.
     * @param criteria Report criteria.
     * @return
     * @throws ServiceException
     */
    Document createReportDocument(Template reportTemplate, ReportSearchCriteria criteria) throws ServiceException;

}