package au.gov.qld.fire.crystal.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import au.gov.qld.fire.crystal.CRDatabaseHelper;
import au.gov.qld.fire.domain.document.Template;
import au.gov.qld.fire.domain.refdata.TemplateTypeEnum;
import au.gov.qld.fire.service.EntityService;
import au.gov.qld.fire.web.SessionConstants;

import com.crystaldecisions.report.web.viewer.CrystalReportViewer;
import com.crystaldecisions.sdk.occa.report.application.OpenReportOptions;
import com.crystaldecisions.sdk.occa.report.application.ReportClientDocument;

/**
 * 
 * @author vchibaev (Valeri SHIBAEV)
 */
@Controller
@SessionAttributes({"dispatch", "reportName"})
public class ReportViewerController {

    @Autowired private CRDatabaseHelper crDatabaseHelper;

    @Autowired private CRExportHelper crExportHelper;

    @Autowired private EntityService entityService;

    //@Autowired
    //private ReportService reportService;

    @RequestMapping(value = "/reportViewer.do", params = "!dispatch")
    public String main(
        HttpServletRequest request, HttpServletResponse response,
        ModelMap model) throws Exception
    {
        String dispatch = (String) model.get("dispatch");
        String reportName = (String) model.get("reportName");
        if ("view".equals(dispatch))
        {
            return view(reportName, request, response, model);
        }
//        if ("viewTaglib".equals(dispatch))
//        {
//            return viewTaglib(reportName, request, response, model);
//        }
        // default
        List<Template> entities = entityService.findTemplates(TemplateTypeEnum.REPORT_CRYSTAL);
        model.addAttribute(SessionConstants.ENTITIES, entities);
        return "main";
    }

    @RequestMapping(value = "/reportViewer.do", params = "dispatch=view")
    public String view(
        @RequestParam(value = "reportName", required = false) String reportName,
        HttpServletRequest request, HttpServletResponse response,
        ModelMap model) throws Exception
    {
        if (reportName == null)
        {
            reportName = (String) model.get("reportName");
        }
        else
        {
            model.addAttribute("reportName", reportName);
        }
        CrystalReportViewer viewer = new CrystalReportViewer();
        try
        {
            viewer.setDisplayGroupTree(false);
            viewer.setOwnPage(true);
            viewer.setHasExportButton(true);
            viewer.setHasPrintButton(true);
            viewer.setHasRefreshButton(true);
            viewer.setHasToggleGroupTreeButton(false);
            viewer.setHasLogo(true);
            viewer.setEnableLogonPrompt(false);
            //
            ReportClientDocument reportClientDoc = new ReportClientDocument();
            reportClientDoc.open(reportName + ".rpt", OpenReportOptions._openAsReadOnly);
            crDatabaseHelper.changeDataSource(reportClientDoc);
            request.getSession().setAttribute("reportSource", reportClientDoc.getReportSource());
            //
            viewer.setReportSource(reportClientDoc.getReportSource());
            viewer.processHttpRequest(request, response, request.getSession().getServletContext(), null); 
        }
        finally
        {
            //viewer.dispose();
        }
        model.addAttribute("dispatch", "view");
        return null;//"viewer";
    }

    @RequestMapping(value = "/reportViewer.do", params = "dispatch=export")
    public String export(
        @RequestParam(value = "reportName", required = false) String reportName,
        HttpServletRequest request, HttpServletResponse response,
        ModelMap model) throws Exception
    {
        if (reportName == null)
        {
            reportName = (String) model.get("reportName");
        }
        else
        {
            model.addAttribute("reportName", reportName);
        }
        ReportClientDocument reportClientDoc = new ReportClientDocument();
        reportClientDoc.open(reportName + ".rpt", OpenReportOptions._openAsReadOnly);
        crDatabaseHelper.changeDataSource(reportClientDoc);
        request.getSession().setAttribute("reportSource", reportClientDoc.getReportSource());
        crExportHelper.exportPDF(reportClientDoc, response, true);
        return null;
    }

//    @RequestMapping(value = "/reportViewer.do", params = "dispatch=viewTaglib")
//    public String viewTaglib(
//        @RequestParam(value = "reportName", required = false) String reportName,
//        HttpServletRequest request, HttpServletResponse response,
//        ModelMap model) throws Exception
//    {
//        if (reportName == null)
//        {
//            reportName = (String) model.get("reportName");
//        }
//        else
//        {
//            model.addAttribute("reportName", reportName);
//        }
//        ReportClientDocument reportClientDoc = new ReportClientDocument();
//        reportClientDoc.open(reportName + ".rpt", OpenReportOptions._openAsReadOnly);
//        //crDatabaseHelper.changeDataSource(reportClientDoc);
//        request.getSession().setAttribute("reportSource", reportClientDoc.getReportSource());
//        return "viewer";
//    }

}