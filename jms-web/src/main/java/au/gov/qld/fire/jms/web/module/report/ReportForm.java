package au.gov.qld.fire.jms.web.module.report;

import java.util.ArrayList;
import java.util.List;

import au.gov.qld.fire.domain.document.Template;
import au.gov.qld.fire.domain.refdata.DocTypeEnum;
import au.gov.qld.fire.domain.report.ReportDoc;
import au.gov.qld.fire.jms.domain.report.ReportSearchCriteria;
import au.gov.qld.fire.jms.web.module.AbstractValidatorForm;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class ReportForm extends AbstractValidatorForm<Template>
{

    /** serialVersionUID */
    private static final long serialVersionUID = -5848347887873269396L;

    /** reportDoc criteria */
    private ReportSearchCriteria criteria;

    /** reportDoc - new to create */
    private ReportDoc reportDoc;

    /** reportDoc - created by this user */
    private List<ReportDoc> reportDocs;

    /* (non-Javadoc)
     * @see au.gov.qld.fire.jms.web.module.AbstractValidatorForm#setEntity(java.lang.Object)
     */
    @Override
    public void setEntity(Template entity)
    {
        super.setEntity(entity);
        //
        criteria = new ReportSearchCriteria();
        //
        reportDoc = new ReportDoc();
        reportDoc.setTemplate(entity);
        reportDoc.setDocTypeEnum(DocTypeEnum.REPORT_FOP);
        reportDoc.setName(entity.getName());
        reportDoc.setDescription(entity.getDescription());
        //
        reportDocs = new ArrayList<ReportDoc>();
    }

    /**
     * @return the criteria
     */
    public ReportSearchCriteria getCriteria()
    {
        return criteria;
    }

    /**
     * @param criteria the criteria to set
     */
    public void setCriteria(ReportSearchCriteria criteria)
    {
        this.criteria = criteria;
    }

    /**
     * @return the reportDoc
     */
    public ReportDoc getReportDoc()
    {
        return reportDoc;
    }

    /**
     * @param reportDoc the reportDoc to set
     */
    public void setReportDoc(ReportDoc reportDoc)
    {
        this.reportDoc = reportDoc;
    }

    /**
     * @return the reportDocs
     */
    public List<ReportDoc> getReportDocs()
    {
        return reportDocs;
    }

    /**
     * @param reportDocs the reportDocs to set
     */
    public void setReportDocs(List<ReportDoc> reportDocs)
    {
        this.reportDocs = reportDocs;
    }

}