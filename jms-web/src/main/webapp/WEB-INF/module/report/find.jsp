<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:bean="http://struts.apache.org/tags-bean"
    xmlns:c="http://java.sun.com/jsp/jstl/core"
    xmlns:fn="http://java.sun.com/jsp/jstl/functions"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    version="2.0">

{
"tabs": ["tab1"],

"menuItems": [],

"columnDefs": [
{"key": "id", "label": "", "formatter": "YAHOO.widget.DataTable.formatCheckbox", "hidden": true},
{"key": "code", "label": "Code", "sortable":true, "resizeable":true},
{"key": "name", "label": "Name", "sortable":true, "resizeable":true},
{"key": "templateType", "label": "Report Type", "sortable":true, "resizeable":true},
{"key": "contentType", "label": "Content Type", "sortable":true, "resizeable":true},
{"key": "generate", "label": "", "formatter": "link"},
{"key": "list", "label": "", "formatter": "link"}
],

"rows": [<c:forEach items="${entities}" var="entity" varStatus="status">
{"id": "${entity.id}",
"code": "${entity.code}",
"name": "${entity.name}",
"templateType": "${entity.templateType.name}",
"contentType": "${entity.contentType}",
"generate": "javascript:YAHOO.jms.sendGetRequest('report/generateReportDoc.do?id=${entity.id}', 'scripts/common.js', 'Generate Report');generate",
"list": "javascript:YAHOO.jms.sendGetRequest('report/findReportDoc.do?id=${entity.id}', null, 'User Reports');list"}<c:if test="${not status.last}">,</c:if>
</c:forEach>],

"filters": [],

"actions": {
"findAction": "report/findReport.do",
"helpAction": "report/helpReport.do", "helpTitle": "Report Help",
"viewAction": "",
"newAction":  "",
"editAction": ""
}
}
</jsp:root>