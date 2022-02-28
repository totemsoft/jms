package au.gov.qld.fire.crystal.web;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

import com.crystaldecisions.sdk.occa.report.application.ReportClientDocument;
import com.crystaldecisions.sdk.occa.report.document.PaperSize;
import com.crystaldecisions.sdk.occa.report.document.PaperSource;
import com.crystaldecisions.sdk.occa.report.document.PrintReportOptions;
import com.crystaldecisions.sdk.occa.report.document.PrinterDuplex;
import com.crystaldecisions.sdk.occa.report.exportoptions.CharacterSeparatedValuesExportFormatOptions;
import com.crystaldecisions.sdk.occa.report.exportoptions.DataOnlyExcelExportFormatOptions;
import com.crystaldecisions.sdk.occa.report.exportoptions.EditableRTFExportFormatOptions;
import com.crystaldecisions.sdk.occa.report.exportoptions.ExportOptions;
import com.crystaldecisions.sdk.occa.report.exportoptions.PDFExportFormatOptions;
import com.crystaldecisions.sdk.occa.report.exportoptions.RTFWordExportFormatOptions;
import com.crystaldecisions.sdk.occa.report.exportoptions.ReportExportFormat;
import com.crystaldecisions.sdk.occa.report.lib.ReportSDKException;
import com.crystaldecisions.sdk.occa.report.lib.ReportSDKExceptionBase;

/**
 * Crystal Reports Java Helper Sample
 */
@Component
public class CRExportHelper {
    
    /**
     * Exports a report to PDF
     * 
     * @param clientDoc        The reportClientDocument representing the report being used
     * @param response        The HttpServletResponse object
     * @param startPage        Starting page
     * @param endPage        Ending page
     * @param attachment    true to prompts for open or save; false opens the report
     *                         in the specified format after exporting.
     * @throws ReportSDKExceptionBase
     * @throws IOException
     */
    public void exportPDF(ReportClientDocument clientDoc, HttpServletResponse response, boolean attachment) throws ReportSDKExceptionBase, IOException {
        // PDF export allows page range export. The following routine ensures
        // that the requested page range is valid
        PDFExportFormatOptions  pdfOptions = new PDFExportFormatOptions();
        ExportOptions exportOptions = new ExportOptions();
        exportOptions.setExportFormatType(ReportExportFormat.PDF);        
        exportOptions.setFormatOptions(pdfOptions);

        export(clientDoc, exportOptions, response, attachment, "application/pdf", "pdf");
    }
    
    /**
     * Exports a report to PDF for a range of pages
     * 
     * @param clientDoc        The reportClientDocument representing the report being used
     * @param response        The HttpServletResponse object
     * @param startPage        Starting page
     * @param endPage        Ending page
     * @param attachment    true to prompts for open or save; false opens the report
     *                         in the specified format after exporting.
     * @throws ReportSDKExceptionBase
     * @throws IOException
     */
    public void exportPDF(ReportClientDocument clientDoc, HttpServletResponse response, ServletContext  context, int startPage, int endPage,boolean attachment) throws ReportSDKExceptionBase, IOException {
        // PDF export allows page range export. The following routine ensures
        // that the requested page range is valid
        PDFExportFormatOptions  pdfOptions = new PDFExportFormatOptions();
        pdfOptions.setStartPageNumber(startPage);
        pdfOptions.setEndPageNumber(endPage);
        ExportOptions exportOptions = new ExportOptions();
        exportOptions.setExportFormatType(ReportExportFormat.PDF);        
        exportOptions.setFormatOptions(pdfOptions);

        export(clientDoc, exportOptions, response, attachment, "application/pdf", "pdf");

    }
    
    /**
     * Exports a report to RTF
     * 
     * @param clientDoc        The reportClientDocument representing the report being used
     * @param response        The HttpServletResponse object
     * @param attachment    true to prompts for open or save; false opens the report
     *                         in the specified format after exporting.
     * @throws ReportSDKExceptionBase
     * @throws IOException
     */
    public void exportRTF(ReportClientDocument clientDoc, HttpServletResponse response, boolean attachment) throws ReportSDKExceptionBase, IOException {
        // RTF export allows page range export. The following routine ensures
        // that the requested page range is valid
        RTFWordExportFormatOptions  rtfOptions = new RTFWordExportFormatOptions();
        ExportOptions exportOptions = new ExportOptions();
        exportOptions.setExportFormatType(ReportExportFormat.RTF);        
        exportOptions.setFormatOptions(rtfOptions);

        export(clientDoc, exportOptions, response, attachment, "text/rtf", "rtf");
    }
    
    /**
     * Exports a report to RTF for a range of pages
     * 
     * @param clientDoc        The reportClientDocument representing the report being used
     * @param response        The HttpServletResponse object
     * @param startPage        Starting page
     * @param endPage        Ending page.  
     * @param attachment    true to prompts for open or save; false opens the report
     *                         in the specified format after exporting.
     * @throws ReportSDKExceptionBase
     * @throws IOException
     */
    public void exportRTF(ReportClientDocument clientDoc, HttpServletResponse response, ServletContext  context, int startPage, int endPage, boolean attachment) throws ReportSDKExceptionBase, IOException {
        // RTF export allows page range export. The following routine ensures
        // that the requested page range is valid
        RTFWordExportFormatOptions  rtfOptions = new RTFWordExportFormatOptions();
        rtfOptions.setStartPageNumber(startPage);
        rtfOptions.setEndPageNumber(endPage);
        ExportOptions exportOptions = new ExportOptions();
        exportOptions.setExportFormatType(ReportExportFormat.RTF);        
        exportOptions.setFormatOptions(rtfOptions);

        export(clientDoc, exportOptions, response, attachment, "text/rtf", "rtf");
    }

    /**
     * Exports a report to RTF
     * 
     * @param clientDoc     The reportClientDocument representing the report being used
     * @param response      The HttpServletResponse object
     * @param attachment    true to prompts for open or save; false opens the report
     *                      in the specified format after exporting.
     * @throws ReportSDKExceptionBase
     * @throws IOException
     */
    public void exportRTFEditable(ReportClientDocument clientDoc, HttpServletResponse response, boolean attachment) throws ReportSDKExceptionBase, IOException {
        // RTF export allows page range export. The following routine ensures
        // that the requested page range is valid
        EditableRTFExportFormatOptions  rtfOptions = new EditableRTFExportFormatOptions();
        ExportOptions exportOptions = new ExportOptions();
        exportOptions.setExportFormatType(ReportExportFormat.editableRTF);      
        exportOptions.setFormatOptions(rtfOptions);

        export(clientDoc, exportOptions, response, attachment, "text/rtf", "rtf");
    }    
    
    /**
     * Exports a report to RTF for a range of pages
     * 
     * @param clientDoc     The reportClientDocument representing the report being used
     * @param response      The HttpServletResponse object
     * @param startPage     Starting page
     * @param endPage       Ending page.  
     * @param attachment    true to prompts for open or save; false opens the report
     *                      in the specified format after exporting.
     * @throws ReportSDKExceptionBase
     * @throws IOException
     */
    public void exportRTFEditable(ReportClientDocument clientDoc, HttpServletResponse response, ServletContext  context, int startPage, int endPage,boolean attachment) throws ReportSDKExceptionBase, IOException {
        // RTF export allows page range export. The following routine ensures
        // that the requested page range is valid
        EditableRTFExportFormatOptions  rtfOptions = new EditableRTFExportFormatOptions();
        rtfOptions.setStartPageNumber(startPage);
        rtfOptions.setEndPageNumber(endPage);
        ExportOptions exportOptions = new ExportOptions();
        exportOptions.setExportFormatType(ReportExportFormat.editableRTF);      
        exportOptions.setFormatOptions(rtfOptions);

        export(clientDoc, exportOptions, response, attachment, "text/rtf", "rtf");
    }
    
    /**
     * Exports a report to Excel (Data Only)
     * 
     * @param clientDoc     The reportClientDocument representing the report being used
     * @param response      The HttpServletResponse object
     * @param attachment    true to prompts for open or save; false opens the report
     *                      in the specified format after exporting.
     * @throws ReportSDKExceptionBase
     * @throws IOException
     */
    public void exportExcelDataOnly(ReportClientDocument clientDoc, HttpServletResponse response, boolean attachment) throws ReportSDKExceptionBase, IOException {
        DataOnlyExcelExportFormatOptions excelOptions = new DataOnlyExcelExportFormatOptions();
        ExportOptions exportOptions = new ExportOptions();
        exportOptions.setExportFormatType(ReportExportFormat.recordToMSExcel);      
        exportOptions.setFormatOptions(excelOptions );

        export(clientDoc, exportOptions, response, attachment, "application/excel", "xls");
    }

    /**
     * Exports a report to CSV
     * 
     * @param clientDoc        The reportClientDocument representing the report being used
     * @param response        The HttpServletResponse object
     * @param attachment    true to prompts for open or save; false opens the report
     *                         in the specified format after exporting.
     * @throws ReportSDKExceptionBase
     * @throws IOException
     */
    public void exportCSV(ReportClientDocument clientDoc, HttpServletResponse response, boolean attachment) throws ReportSDKExceptionBase, IOException {
        CharacterSeparatedValuesExportFormatOptions csvOptions = new CharacterSeparatedValuesExportFormatOptions();
        csvOptions.setSeparator(",");
        csvOptions.setDelimiter("\n");
        ExportOptions exportOptions = new ExportOptions();
        exportOptions.setExportFormatType(ReportExportFormat.characterSeparatedValues);        
        exportOptions.setFormatOptions(csvOptions);

        export(clientDoc, exportOptions, response, attachment, "text/csv", "csv");
    }
    
    /**
     * Exports a report to a specified format
     * 
     * @param clientDoc       The reportClientDocument representing the report being used
     * @param exportOptions   Export options
     * @param response        The response object to write to
     * @param attachment      True to prompts for open or save; false opens the report
     *                        in the specified format after exporting.
     * @param mimeType        MIME type of the format being exported
     * @param extension       file extension of the format (e.g., "pdf" for Acrobat)
     * @throws ReportSDKExceptionBase
     * @throws IOException
     */
    private void export(ReportClientDocument clientDoc, ExportOptions exportOptions, HttpServletResponse response, boolean attachment, String mimeType, String extension)
        throws ReportSDKExceptionBase, IOException {
        InputStream is = null;
        try {
            response.setContentType(mimeType);
            if (attachment)
            {
                String name = clientDoc.getReportSource().getReportTitle();
                if (name == null)
                {
                    name = "CrystalReportViewer";
                }
                else
                {
                    name = name.replaceAll("\"", "");
                }
                response.setHeader("Content-Disposition",
                    "attachment; filename=\"" + name + "." + extension + "\"");
            }
            OutputStream os = response.getOutputStream();
            is = new BufferedInputStream(clientDoc.getPrintOutputController().export(exportOptions));
            IOUtils.copy(is, os);
        } finally {
            IOUtils.closeQuietly(is);
        }
    }
    
    /**
     * Prints to the server printer
     * 
     * @param clientDoc        The reportClientDocument representing the report being used
     * @param printerName    Name of printer used to print the report
     * @throws ReportSDKException 
     */
    public void printToServer(ReportClientDocument clientDoc,String printerName)throws ReportSDKException {
        PrintReportOptions printOptions = new PrintReportOptions();
        // Note: Printer with the <printer name> below must already be
        // configured.
        printOptions.setPrinterName(printerName);
        printOptions.setJobTitle("Sample Print Job from Crystal Reports.");
        printOptions.setPrinterDuplex(PrinterDuplex.useDefault);
        printOptions.setPaperSource(PaperSource.auto);
        printOptions.setPaperSize(PaperSize.paperLetter);
        printOptions.setNumberOfCopies(1);
        printOptions.setCollated(false);

        // Print report
        clientDoc.getPrintOutputController().printReport(printOptions);
    }

}