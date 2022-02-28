<?xml version="1.0" encoding="utf-8"?>
<jsp:root
    xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:c="http://java.sun.com/jsp/jstl/core"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    xmlns:crviewer="/crystal-tags-reportviewer.tld"
    version="2.0">
    <body>
		<crviewer:viewer reportSourceType="reportingComponent"
		    viewerName="${reportName}Viewer"
			reportSourceVar="${reportName}"
			displayGroupTree="false"
	        isOwnPage="true"
	        displayToolbarExportButton="true"
	        displayToolbarPrintButton="true"
	        displayToolbarRefreshButton="true"
	        displayToolbarToggleTreeButton="false"
	        displayToolbarCrystalLogo="true"
	        allowDatabaseLogonPrompting="true">
	        <crviewer:report reportName="${reportName}.rpt" />
		</crviewer:viewer>
    </body>
</jsp:root>