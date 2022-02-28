<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:bean="http://struts.apache.org/tags-bean"
    xmlns:c="http://java.sun.com/jsp/jstl/core"
    xmlns:fn="http://java.sun.com/jsp/jstl/functions"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    version="2.0">
<jsp:directive.page contentType="application/json;charset=UTF-8"/>
{
"menuItems": [],

"columnDefs": [
{"key": "id", "label": "", "className": "width0", "hidden": true},
{"key": "fileId", "label": "File No", "width": 50, "sortable": true},
{"key": "fcaId", "label": "FCA No", "width": 50, "sortable": true, "formatter": "link"},
{"key": "jobType", "label": "Alarm Status", "sortable": true, "resizeable": true},
{"key": "jobStartDate", "label": "Job Start Date", "sortable": true, "resizeable": true},
{"key": "nextActionDate", "label": "Next Action Date", "sortable": true, "resizeable": true},
{"key": "nextAction", "label": "Next Action", "sortable": true, "resizeable": true},
{"key": "requestedBy", "label": "Requested By", "sortable": true, "resizeable": true}
],

"rows": [<c:forEach items="${entities}" var="entity" varStatus="status">
{"id": ${entity.fileId},
"fileId": "${entity.fileId}",
"fcaId": "javascript:YAHOO.jms.editFile(${entity.fileId});${entity.fcaId}",
"jobType": "${entity.jobType}",
"jobStartDate": "<fmt:formatDate value='${entity.jobStartDate}' pattern='${datePattern}'/>",
"nextActionDate": "<fmt:formatDate value='${entity.nextActionDate}' pattern='${datePattern}'/>",
"nextAction": "${entity.nextAction}",
"requestedBy": "${entity.requestedBy}"}<c:if test="${not status.last}">,</c:if>
</c:forEach>],

"filters": [
{"name": "fileId", "label": "<bean:message key='label.file' />", "valuesUrl": "findFilter.do?dispatch=findFileNo"},
{"name": "fcaId", "label": "<bean:message key='label.fca' />", "valuesUrl": "findFilter.do?dispatch=findFcaNo"},
{"name": "buildingName", "label": "<bean:message key='label.building' />", "valuesUrl": "findFilter.do?dispatch=findBuildingName"}
],

"actions": {
"findAction": "home/findHome.do?dispatch=find",
"viewAction": "",
"newAction":  "",
"editAction": "file/editFile.do?dispatch=edit&amp;view=detailView", "editScript": "scripts/module/file/edit.js"
}
}
</jsp:root>