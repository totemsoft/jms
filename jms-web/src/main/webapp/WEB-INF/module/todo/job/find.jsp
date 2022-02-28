<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:bean="http://struts.apache.org/tags-bean"
    xmlns:c="http://java.sun.com/jsp/jstl/core"
    xmlns:fn="http://java.sun.com/jsp/jstl/functions"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    version="2.0">

{
"menuItems": [],

"columnDefs": [
{"key": "id", "label": "", "formatter": "YAHOO.widget.DataTable.formatCheckbox", "hidden": true},
{"key": "jobId", "label": "Job No", "sortable": true, "resizeable": true},
{"key": "fileId", "label": "File No", "sortable": true, "resizeable": true},
{"key": "fcaId", "label": "FCA No", "sortable": true, "resizeable": true},
{"key": "jobType", "label": "Job Type", "sortable": true, "resizeable": true},
{"key": "nextActionDate", "label": "Next Action Date", "sortable": true, "resizeable": true},
{"key": "nextAction", "label": "Next Action", "sortable": true, "resizeable": true},
{"key": "workGroup", "label": "Work Group", "sortable": true, "resizeable": true}
],

"fields": [
"id", "jobId", "fileId", "fcaId", "jobType", "nextActionDate", "nextAction", "workGroup"
],

"rows": [<c:forEach items="${entities}" var="entity" varStatus="status">
{"id": ${entity.jobId},
"jobId": "${entity.jobId}",
"fileId": "${entity.fileId}",
"fcaId": "${entity.fcaId}",
"jobType": "${entity.jobType}",
"nextActionDate": "<fmt:formatDate value='${entity.nextActionDate}' pattern='${datePattern}'/>",
"nextAction": "${entity.nextAction}",
"workGroup": "${entity.workGroup}"}<c:if test="${not status.last}">,</c:if>
</c:forEach>],

"filters": [
{"name": "fileId", "label": "<bean:message key='label.file' />", "valuesUrl": "findFilter.do?dispatch=findFileNo"},
{"name": "workGroup", "label": "<bean:message key='label.workGroup' />", "values": [<c:forEach items="${workGroups}" var="item" varStatus="status">
"${item.name}"<c:if test="${not status.last}">,</c:if>
</c:forEach>]},
{"name": "actionCode", "label": "<bean:message key='label.actionCode' />", "valuesUrl": "findFilter.do?dispatch=findActionCode"}
],

"actions": {
"findAction": "todo/findTodoJob.do?dispatch=find",
"exportAction": "todo/findTodoJob.do?dispatch=export",
"viewAction": "",
"newAction":  "",
"editAction": "job/editJob.do?dispatch=edit&amp;view=detailView", "editScript": "scripts/module/job/edit.js"
}
}
</jsp:root>