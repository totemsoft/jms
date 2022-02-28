package au.gov.qld.fire.jms.service;

import java.util.List;

import javax.inject.Inject;

import au.gov.qld.fire.BaseTestCase;
import au.gov.qld.fire.dao.TemplateDao;
import au.gov.qld.fire.domain.document.Document;
import au.gov.qld.fire.domain.document.Template;
import au.gov.qld.fire.domain.refdata.TemplateType;
import au.gov.qld.fire.domain.refdata.TemplateTypeEnum;
import au.gov.qld.fire.jms.dao.FileActionDao;
import au.gov.qld.fire.jms.domain.action.FileAction;
import au.gov.qld.fire.jms.domain.refdata.ActionTypeEnum;
import au.gov.qld.fire.jms.domain.report.ReportSearchCriteria;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@apollosoft.net)
 */
public class DocumentServiceTest extends BaseTestCase
{

	@Inject private DocumentService documentService;

	@Inject private FileActionDao fileActionDao;

	@Inject private TemplateDao templateDao;

    /**
     * Test method for {@link au.gov.qld.fire.jms.service.DocumentService#updateParameters(java.lang.String, au.gov.qld.fire.jms.domain.security.User, au.gov.qld.fire.jms.domain.file.File)}.
     */
    public final void testUpdateParameters()
    {
        try
        {

        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link au.gov.qld.fire.jms.service.DocumentService#createActionDocument(au.gov.qld.fire.domain.document.Template, au.gov.qld.fire.jms.domain.action.BaseAction)}.
     */
    public final void testCreateActionDocument()
    {
        try
        {
            TemplateTypeEnum templateTypeEnum = TemplateTypeEnum.LETTER;
            TemplateType templateType = new TemplateType(templateTypeEnum.getId());
            List<Template> templates = templateDao.findByTemplateType(templateType);
            List<FileAction> actions = fileActionDao.findAll();
            FileAction action = null;
            for (FileAction a : actions) {
                if (ActionTypeEnum.LETTER.getId().equals(a.getActionCode().getActionType().getId())) {
                    action = a;
                    break;
                }
            }
            for (Template template : templates) {
                Document document = documentService.createActionDocument(template, action);
                assertNotNull(document);
            }
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link au.gov.qld.fire.jms.service.DocumentService#createReportDocument(au.gov.qld.fire.domain.document.Template, au.gov.qld.fire.jms.domain.report.ReportSearchCriteria)}.
     */
    public final void testCreateReportDocument()
    {
        try
        {
            TemplateTypeEnum templateTypeEnum = TemplateTypeEnum.REPORT_FOP;
            TemplateType templateType = new TemplateType(templateTypeEnum.getId());
            List<Template> templates = templateDao.findByTemplateType(templateType);
            ReportSearchCriteria criteria = new ReportSearchCriteria();
            for (Template template : templates)
            {
                documentService.createReportDocument(template, criteria);
            }
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link au.gov.qld.fire.jms.service.DocumentService#createReportDocument(au.gov.qld.fire.domain.document.Template, au.gov.qld.fire.jms.domain.report.ReportSearchCriteria)}.
     */
    public final void testReplace()
    {
        try
        {
            String paramRegex = "file.afterHoursBuildingContacts[0].buildingContactType.name";
            String replacement = "$a$b$c";
            //
            if (paramRegex.indexOf('[') >= 0)
            {
                paramRegex = paramRegex.replace("[", "\\[");
            }
            if (paramRegex.indexOf(']') >= 0)
            {
                paramRegex = paramRegex.replace("]", "\\]");
            }
            //A dollar sign ($) may be included as a literal in the replacement string by preceding it with a backslash (\$).
            if (replacement.indexOf('$') >= 0)
            {
                replacement = replacement.replace("$", "\\$");
            }
            //text = text.replaceAll(DELIM_START_REGEX + paramRegex + DELIM_END_REGEX, replacement);
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            fail(e.getMessage());
        }
    }

}