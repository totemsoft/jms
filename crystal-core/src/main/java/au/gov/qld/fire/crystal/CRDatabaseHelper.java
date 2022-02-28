package au.gov.qld.fire.crystal;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.crystaldecisions.sdk.occa.report.application.DBOptions;
import com.crystaldecisions.sdk.occa.report.application.DataDefController;
import com.crystaldecisions.sdk.occa.report.application.DatabaseController;
import com.crystaldecisions.sdk.occa.report.application.ReportClientDocument;
import com.crystaldecisions.sdk.occa.report.data.ConnectionInfo;
import com.crystaldecisions.sdk.occa.report.data.FieldDisplayNameType;
import com.crystaldecisions.sdk.occa.report.data.Fields;
import com.crystaldecisions.sdk.occa.report.data.IConnectionInfo;
import com.crystaldecisions.sdk.occa.report.data.ParameterField;
import com.crystaldecisions.sdk.occa.report.data.ParameterFieldDiscreteValue;
import com.crystaldecisions.sdk.occa.report.data.ParameterFieldRangeValue;
import com.crystaldecisions.sdk.occa.report.data.RangeValueBoundType;
import com.crystaldecisions.sdk.occa.report.data.Values;
import com.crystaldecisions.sdk.occa.report.document.PaperSize;
import com.crystaldecisions.sdk.occa.report.document.PaperSource;
import com.crystaldecisions.sdk.occa.report.document.PrintReportOptions;
import com.crystaldecisions.sdk.occa.report.document.PrinterDuplex;
import com.crystaldecisions.sdk.occa.report.lib.PropertyBag;
import com.crystaldecisions.sdk.occa.report.lib.PropertyBagHelper;
import com.crystaldecisions.sdk.occa.report.lib.ReportSDKException;

/**
 * Crystal Reports Java Helper Sample
 */
@Component
public class CRDatabaseHelper
{

    /** logger. */
    //private static final Logger LOG = Logger.getLogger(CRDatabaseHelper.class);

    /** The JNDI name */
    private String jndiName;

    /** ${crystal.reportlocation} directory */
    private String reportDirectory;

    public CRDatabaseHelper()
    {
        super();
    }

    public CRDatabaseHelper(String jndiName)
    {
        this.jndiName = jndiName;
    }

    /**
     * @param jndiName the jndiName to set
     */
    public void setJndiName(String jndiName)
    {
        this.jndiName = jndiName;
    }

    /**
     * @param reportDirectory the reportDirectory to set
     */
    public void setReportDirectory(String reportDirectory)
    {
        // handle windows "C\:\\" format
        if (reportDirectory.contains("\\:\\"))
        {
            reportDirectory = reportDirectory.replace("\\:\\", ":");
        }
        this.reportDirectory = reportDirectory;
    }

    /**
     * Save report as *.rpt file into reportDirectory
     * @param reportName
     * @param reportVersion
     * @param reportContent
     * @throws IOException
     */
    public void saveReport(String reportName, long reportVersion, byte[] reportContent) throws IOException
    {
        File dir = new File(reportDirectory);
        if (!dir.exists())
        {
            if (!dir.mkdirs())
            {
                throw new IOException("ReportDirectory does not exist or could not be created: " + dir.toString() + " [" + reportDirectory + "]");
            }
        }
        File file = new File(dir, reportName + ".rpt");
        File fileVersion = new File(dir, "." + reportName + ".rpt." + reportVersion); // hidden file
        if (file.exists())
        {
            if (fileVersion.exists())
            {
                return; // same file
            }
        }
        // overwrite file
        if (!file.exists() || !fileVersion.exists())
        {
            if (file.exists())
            {
//                if (!file.delete())
//                {
//                    throw new IOException("Failed to delete old report file: " + file.getCanonicalPath());
//                }
            }
            if (!file.exists() && !file.createNewFile())
            {
                throw new IOException("Failed to create new report file: " + file.getCanonicalPath());
            }
            OutputStream os = new FileOutputStream(file);
            try
            {
                os.write(reportContent);
                os.flush();
            }
            finally
            {
                //IOUtils.closeQuietly(os);
                os.close();
            }
        }
        // create fileVersion
        if (!fileVersion.exists())
        {
            if (!fileVersion.createNewFile())
            {
                throw new IOException("Failed to create report version file: " + fileVersion.getCanonicalPath());
            }
        }
    }

    /**
     * Changes the DataSource for each Table
     * @param clientDoc     The reportClientDocument representing the report being used
     * @throws ReportSDKException
     */
    public void changeDataSource(ReportClientDocument clientDoc) throws ReportSDKException
    {
        // @param reportName    "" for main report, name of subreport for subreport, null for all reports
        //String reportName = clientDoc.getReportDocument().getName();
        changeDataSource(clientDoc,
            null, null,
            null, null, null, null,
            //"jms", "Passw0rd", "jdbc:jtds:sqlserver://localhost:1433/JMS_DEV2", "net.sourceforge.jtds.jdbc.Driver",
            jndiName
        );
    }

    /**
     * Changes the DataSource for a specific Table
     * @param clientDoc The reportClientDocument representing the report being used
     * @param reportName    "" for main report, name of subreport for subreport, null for all reports
     * @param tableName        name of table to change.  null for all tables.
     * @param username  The DB logon user name
     * @param password  The DB logon password
     * @param connectionURL  The connection URL
     * @param driverName    The driver Name
     * @param jndiName        The JNDI name
     * @throws ReportSDKException
     */
    private void changeDataSource(ReportClientDocument clientDoc,
        String reportName, String tableName,
        String username, String password, String connectionURL, String driverName,
        String jndiName) throws ReportSDKException
    {
        DatabaseController dbController = clientDoc.getDatabaseController();
        IConnectionInfo oldConnectionInfo = dbController.getConnectionInfos(null).getConnectionInfo(0);
        IConnectionInfo newConnectionInfo = new ConnectionInfo();
        setAttributes(newConnectionInfo,
            reportName, tableName,
            username, password, connectionURL, driverName,
            jndiName);
        // The Fields collection that contains parameterFields from the report. This parameter is used to specify parameter values for stored procedures.
        Fields parameterFields = null;
        int options = DBOptions._useDefault;
        // 2012-03-30 00:33:00,689 INFO  [org.jboss.resource.connectionmanager.CachedConnectionManager] (ajp-127.0.0.1-18009-11) Closing a connection for you.  Please close them yourself: org.jboss.resource.adapter.jdbc.jdk6.WrappedConnectionJDK6
        dbController.replaceConnection(oldConnectionInfo, newConnectionInfo, parameterFields, options);
/*
        ITable origTable = null;
        ITable newTable = null;
        // Obtain collection of tables from this database controller
        if (reportName == null || reportName.equals("")) {
            Tables tables = clientDoc.getDatabaseController().getDatabase().getTables();
            for(int i = 0;i < tables.size();i++){
                origTable = tables.getTable(i);
                if (tableName == null || origTable.getName().equals(tableName)) {
                    newTable = (ITable)origTable.clone(true);

                    // We set the Fully qualified name to the Table Alias to keep the method generic
                    // This workflow may not work in all scenarios and should likely be customized to work
                    // in the developer's specific situation. The end result of this statement will be to strip
                    // the existing table of it's db specific identifiers. For example Xtreme.dbo.Customer becomes just Customer
                    newTable.setQualifiedName(origTable.getAlias());

                    // Change properties that are different from the original datasource
                    // For example, if the table name has changed you will be required
                    // to change it during this routine
                    // table.setQualifiedName(TABLE_NAME_QUALIFIER);

                    // Change connection information properties
                    setAttributes(newTable.getConnectionInfo(),
                        reportName, tableName,
                        username, password, connectionURL, driverName,
                        jndiName);

                    // Update the table information
                    LOG.info("[" + jndiName + "] databaseController.setTableLocation :: origTable=" +
                        origTable.getQualifiedName() + ", newTable=" + newTable.getQualifiedName());
                    //dbController.setTableLocation(origTable, newTable);
                }
            }
        }
        // Next loop through all the subreports and pass in the same information.
        // You may consider creating a separate method which accepts
        if (reportName == null || !(reportName.equals(""))) {
            IStrings subNames = clientDoc.getSubreportController().getSubreportNames();
            for (int subNum=0;subNum<subNames.size();subNum++) {
                Tables tables = clientDoc.getSubreportController().getSubreport(subNames.getString(subNum)).getDatabaseController().getDatabase().getTables();
                for(int i = 0;i < tables.size();i++){
                    origTable = tables.getTable(i);
                    if (tableName == null || origTable.getName().equals(tableName)) {
                        newTable = (ITable)origTable.clone(true);

                        // We set the Fully qualified name to the Table Alias to keep the method generic
                        // This workflow may not work in all scenarios and should likely be customized to work
                        // in the developer's specific situation. The end result of this statement will be to strip
                        // the existing table of it's db specific identifiers. For example Xtreme.dbo.Customer becomes just Customer
                        newTable.setQualifiedName(origTable.getAlias());

                        // Change properties that are different from the original datasource
                        // table.setQualifiedName(TABLE_NAME_QUALIFIER);

                        // Change connection information properties
                        setAttributes(newTable.getConnectionInfo(),
                            reportName, tableName,
                            username, password, connectionURL, driverName,
                            jndiName);

                        // Update the table information
                        clientDoc.getSubreportController().getSubreport(subNames.getString(subNum)).getDatabaseController().setTableLocation(origTable, newTable);
                    }
                }
            }
        }
*/
    }

    /**
     * Change connection information properties
     * @param connectionInfo
     */
    private PropertyBag setAttributes(IConnectionInfo connectionInfo,
        String reportName, String tableName,
        String username, String password, String connectionURL, String driverName,
        String jndiName) throws ReportSDKException
    {
        // Set new table connection property attributes
        PropertyBag propertyBag = new PropertyBag();

        // Overwrite any existing properties with updated values
        propertyBag.put(PropertyBagHelper.CONNINFO_SSO_ENABLED, false);
        propertyBag.put(PropertyBagHelper.CONNINFO_SERVER_TYPE, "JDBC (JNDI)");
        propertyBag.put("Use JDBC", true);
        propertyBag.put(PropertyBagHelper.CONNINFO_DATABASE_DLL, "crdb_jdbc.dll");
        propertyBag.put(PropertyBagHelper.JNDI_OPTIONALNAME, jndiName);
        propertyBag.put(PropertyBagHelper.CONNINFO_JDBC_CONNECTION_URL, connectionURL);
        propertyBag.put(PropertyBagHelper.CONNINFO_JDBC_DATABASECLASSNAME, driverName);
        // propertyBag.put(PropertyBagHelper.CONNINFO_SERVER_NAME, SERVER_NAME); //Optional property
        // propertyBag.put(PropertyBagHelper.CONNINFO_CONNECTION_STRING, CONNECTION_STRING); //Optional property
        //propertyBag.put(PropertyBagHelper.CONNINFO_DATABASE_NAME, databaseName); //Optional property
        // propertyBag.put(PropertyBagHelper.CONNINFO_URI, URI); //Optional property
        connectionInfo.setAttributes(propertyBag);

        // Set database username and password
        // NOTE: Even if the username and password properties do not change when switching databases, the
        // database password is *not* saved in the report and must be set at runtime if the database is secured.
        connectionInfo.setUserName(username);
        connectionInfo.setPassword(password);
        //
        return propertyBag;
    }

    /**
     * Passes a populated java.sql.Resultset object to a Table object
     * 
     * @param clientDoc The reportClientDocument representing the report being used
     * @param rs        The java.sql.Resultset used to populate the Table
     * @param tableAlias    The alias of the table
     * @param reportName    The name of the subreport.  If tables in the main report
     *                         is to be used, "" should be passed
     * @throws ReportSDKException
     */
    public void passResultSet(ReportClientDocument clientDoc, java.sql.ResultSet rs,
            String tableAlias, String reportName) throws ReportSDKException {
        DatabaseController dbController = clientDoc.getDatabaseController();
        if (StringUtils.isBlank(reportName))
            dbController.setDataSource(rs, tableAlias,tableAlias);
        else
            clientDoc.getSubreportController().getSubreport(reportName).getDatabaseController().setDataSource(rs, tableAlias,tableAlias);

    }

    /**
     * Passes a populated collection of a Java class to a Table object
     * 
     * @param clientDoc     The reportClientDocument representing the report being used
     * @param dataSet        The java.sql.Resultset used to populate the Table
     * @param className        The fully-qualified class name of the POJO objects being passed
     * @param tableAlias        The alias of the table
     * @param reportName    The name of the subreport.  If tables in the main report
     *                         is to be used, "" should be passed
     * @throws ReportSDKException
     */
    public void passPOJO(ReportClientDocument clientDoc, Collection dataSet, 
            String className, String tableAlias, String reportName) throws ReportSDKException,ClassNotFoundException{
        DatabaseController dbController = clientDoc.getDatabaseController();
        if(reportName.equals(""))
            dbController.setDataSource(dataSet, Class.forName(className),tableAlias,tableAlias);
        else
            clientDoc.getSubreportController().getSubreport(reportName).getDatabaseController().setDataSource(dataSet, Class.forName(className),tableAlias,tableAlias);

    }

    /**
     * Passes a single discrete parameter value to a report parameter
     * 
     * @param clientDoc        The reportClientDocument representing the report being used
     * @param reportName    The name of the subreport.  If tables in the main report
     *                         is to be used, "" should be passed
     * @param parameterName    The name of the parameter
     * @param newValue        The new value of the parameter 
     * @throws ReportSDKException
     */
    public void addDiscreteParameterValue(ReportClientDocument clientDoc, String reportName, String parameterName, Object newValue) throws ReportSDKException{
        DataDefController dataDefController = null;
        if(reportName.equals(""))
            dataDefController = clientDoc.getDataDefController();
        else
            dataDefController = clientDoc.getSubreportController().getSubreport(reportName).getDataDefController();

        ParameterFieldDiscreteValue newDiscValue = new ParameterFieldDiscreteValue();
        newDiscValue.setValue(newValue);

        ParameterField paramField = (ParameterField)dataDefController.getDataDefinition().getParameterFields().findField(parameterName, FieldDisplayNameType.fieldName, Locale.getDefault());
        boolean multiValue = paramField.getAllowMultiValue();

        if(multiValue) {
            Values newVals = (Values)paramField.getCurrentValues().clone(true);
            newVals.add(newDiscValue);
            clientDoc.getDataDefController().getParameterFieldController().setCurrentValue(reportName, parameterName ,newVals);
        } else {
            clientDoc.getDataDefController().getParameterFieldController().setCurrentValue(reportName, parameterName , newValue);
        }
    }

    /**
     * Passes multiple discrete parameter values to a report parameter
     * 
     * @param clientDoc        The reportClientDocument representing the report being used
     * @param reportName    The name of the subreport.  If tables in the main report
     *                         is to be used, "" should be passed
     * @param parameterName    The name of the parameter
     * @param newValues        An array of new values to get set on the parameter
     * @throws ReportSDKException
     */
    public void addDiscreteParameterValue(ReportClientDocument clientDoc, String reportName, String parameterName, Object[] newValues) throws ReportSDKException{
        clientDoc.getDataDefController().getParameterFieldController().setCurrentValues(reportName, parameterName ,newValues);
    }

    /**
     * Passes a single range parameter value to a report parameter.  The range is assumed to
     * be inclusive on beginning and end.
     * 
     * @param clientDoc        The reportClientDocument representing the report being used
     * @param reportName    The name of the subreport.  If tables in the main report
     *                         is to be used, "" should be passed
     * @param parameterName    The name of the parameter
     * @param beginValue    The value of the beginning of the range
     * @param endValue        The value of the end of the range
     * @throws ReportSDKException
     */
    public void addRangeParameterValue(ReportClientDocument clientDoc, String reportName, String parameterName, Object beginValue, Object endValue) throws ReportSDKException{
        addRangeParameterValue(clientDoc, reportName, parameterName, beginValue, RangeValueBoundType.inclusive, endValue, RangeValueBoundType.inclusive);
    }

    /**
     * Passes multiple range parameter values to a report parameter.
     *
     * This overload of the addRangeParameterValue will only work if the
     * parameter is setup to accept multiple values.
     * 
     * If the Parameter does not accept multiple values then it is expected that
     * this version of the method will return an error
     * 
     * @param clientDoc        The reportClientDocument representing the report being used
     * @param reportName    The name of the subreport.  If tables in the main report
     *                         is to be used, "" should be passed
     * @param parameterName    The name of the parameter
     * @param beginValues    Array of beginning values.  Must be same length as endValues.
     * @param endValues        Array of ending values.  Must be same length as beginValues.
     * @throws ReportSDKException
     */
    public void addRangeParameterValue(ReportClientDocument clientDoc, String reportName, String parameterName, Object[] beginValues, Object[] endValues) throws ReportSDKException{
        addRangeParameterValue(clientDoc, reportName, parameterName, beginValues, RangeValueBoundType.inclusive, endValues, RangeValueBoundType.inclusive);
    }
    
    /**
     * Passes a single range parameter value to a report parameter
     * 
     * @param clientDoc        The reportClientDocument representing the report being used
     * @param reportName    The name of the subreport.  If tables in the main report
     *                         is to be used, "" should be passed
     * @param parameterName    The name of the parameter
     * @param beginValue    The value of the beginning of the range
     * @param lowerBoundType    The inclusion/exclusion range of the start of range.
     * @param endValue        The value of the end of the range
     * @param upperBoundType    The inclusion/exclusion range of the end of range.
     * @throws ReportSDKException
     */
    public void addRangeParameterValue(ReportClientDocument clientDoc, String reportName, String parameterName, Object beginValue, RangeValueBoundType lowerBoundType,Object endValue, RangeValueBoundType upperBoundType) throws ReportSDKException{
        DataDefController dataDefController = null;
        if(reportName.equals(""))
            dataDefController = clientDoc.getDataDefController();
        else
            dataDefController = clientDoc.getSubreportController().getSubreport(reportName).getDataDefController();

        ParameterFieldRangeValue newRangeValue = new ParameterFieldRangeValue();
        newRangeValue.setBeginValue(beginValue);
        newRangeValue.setLowerBoundType(lowerBoundType);
        newRangeValue.setEndValue(endValue);
        newRangeValue.setUpperBoundType(upperBoundType);

        ParameterField paramField = (ParameterField)dataDefController.getDataDefinition().getParameterFields().findField(parameterName, FieldDisplayNameType.fieldName, Locale.getDefault());
        boolean multiValue = paramField.getAllowMultiValue();

        if (multiValue) {
            Values newVals = (Values)paramField.getCurrentValues().clone(true);
            newVals.add(newRangeValue);
            clientDoc.getDataDefController().getParameterFieldController().setCurrentValue(reportName, parameterName , newVals);
        } else {
            clientDoc.getDataDefController().getParameterFieldController().setCurrentValue(reportName, parameterName , newRangeValue);
        }
    }

    /**
     * Passes multiple range parameter values to a report parameter.
     *
     * This overload of the addRangeParameterValue will only work if the
     * parameter is setup to accept multiple values.
     * 
     * If the Parameter does not accept multiple values then it is expected that
     * this version of the method will return an error
     * 
     * @param clientDoc        The reportClientDocument representing the report being used
     * @param reportName    The name of the subreport.  If tables in the main report
     *                         is to be used, "" should be passed
     * @param parameterName    The name of the parameter
     * @param beginValues    Array of beginning values.  Must be same length as endValues.
     * @param lowerBoundType    The inclusion/exclusion range of the start of range.
     * @param endValues        Array of ending values.  Must be same length as beginValues.
     * @param upperBoundType    The inclusion/exclusion range of the end of range.
     * 
     * @throws ReportSDKException
     */
    public void addRangeParameterValue(ReportClientDocument clientDoc, String reportName, String parameterName, Object[] beginValues,RangeValueBoundType lowerBoundType, Object[] endValues, RangeValueBoundType upperBoundType) throws ReportSDKException{
        // it is expected that the beginValues array is the same size as the
        // endValues array
        ParameterFieldRangeValue[] newRangeValues = new ParameterFieldRangeValue[beginValues.length];
        for(int i=0;i<beginValues.length;i++){
            newRangeValues[i] = new ParameterFieldRangeValue();
            newRangeValues[i].setBeginValue(beginValues[i]);
            newRangeValues[i].setLowerBoundType(lowerBoundType);
            newRangeValues[i].setEndValue(endValues[i]);
            newRangeValues[i].setUpperBoundType(upperBoundType);
        }
        clientDoc.getDataDefController().getParameterFieldController().setCurrentValues(reportName, parameterName , newRangeValues);

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
    
    /**
     * Prints a range of pages to the server printer
     * 
     * @param clientDoc        The reportClientDocument representing the report being used
     * @param printerName    Name of printer used to print the report
     * @param startPage        Starting page
     * @param endPage        Ending page.
     * @throws ReportSDKException 
     */
    public void printToServer(ReportClientDocument clientDoc,String printerName,int startPage, int endPage)throws ReportSDKException {
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
        PrintReportOptions.PageRange printPageRange = new PrintReportOptions.PageRange(startPage,endPage);
        printOptions.addPrinterPageRange(printPageRange);

        // Print report
        clientDoc.getPrintOutputController().printReport(printOptions);
    }

}