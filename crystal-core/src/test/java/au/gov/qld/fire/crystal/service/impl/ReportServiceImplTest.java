package au.gov.qld.fire.crystal.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import au.gov.qld.fire.BaseTestCase;
import au.gov.qld.fire.dao.TemplateDao;
import au.gov.qld.fire.dao.TemplateTypeDao;
import au.gov.qld.fire.domain.document.Document;
import au.gov.qld.fire.domain.document.Template;
import au.gov.qld.fire.domain.refdata.TemplateType;
import au.gov.qld.fire.domain.refdata.TemplateTypeEnum;
import au.gov.qld.fire.domain.report.ReportSearchCriteria;
import au.gov.qld.fire.service.ReportService;

/**
 *
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class ReportServiceImplTest extends BaseTestCase
{

    @Autowired private ReportService reportService;

    @Autowired private TemplateDao templateDao;

    @Autowired private TemplateTypeDao templateTypeDao;

    //@Ignore
    public final void testSaveReport() throws Exception
    {
        //Template reportTemplate = new Template();
        //reportService.saveReport(reportTemplate);
    }

    public final void testCreateReportDocument() throws Exception
    {
        TemplateType templateType = templateTypeDao.findById(TemplateTypeEnum.REPORT_CRYSTAL.getId());
        List<Template> reportTemplates = templateDao.findByTemplateType(templateType);
        ReportSearchCriteria criteria = new ReportSearchCriteria();
        for (Template reportTemplate : reportTemplates)
        {
            Document document = reportService.createReportDocument(reportTemplate, criteria);
            assertNotNull(document);
        }
    }

}