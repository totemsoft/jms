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
{"key": "fileId", "label": "File ID", "sortable": true, "resizeable": true},
{"key": "jobId", "label": "Job No", "sortable": true, "resizeable": true},
{"key": "jobType", "label": "Job Type", "sortable": true, "resizeable": true},
{"key": "jobStartDate", "label": "Job Start Date", "sortable": true, "resizeable": true},
{"key": "nextActionDate", "label": "Next Action Date", "sortable": true, "resizeable": true},
{"key": "nextAction", "label": "Next Action", "sortable": true, "resizeable": true},
{"key": "requestedBy", "label": "Requested By", "sortable": true, "resizeable": true},
{"key": "workGroup", "label": "Work Group", "sortable": true, "resizeable": true}
],

"rows": [<c:forEach items="${entities}" var="entity" varStatus="status">
{"id": ${entity.jobId},
"fileId": "${entity.fileId}",
"jobId": "${entity.jobId}",
"jobType": "${entity.jobType}",
"jobStartDate": "<fmt:formatDate value='${entity.jobStartDate}' pattern='${datePattern}'/>",
"nextActionDate": "<fmt:formatDate value='${entity.nextActionDate}' pattern='${datePattern}'/>",
"nextAction": "${entity.nextAction}",
"requestedBy": "${entity.requestedBy}",
"workGroup": "${entity.workGroup}"}<c:if test="${not status.last}">,</c:if>
</c:forEach>],

"filters": [
{"name": "jobId", "label": "<bean:message key='label.job' />", "valuesUrl": "findFilter.do?dispatch=findJobNo"},
{"name": "jobType", "label": "<bean:message key='label.jobType' />", "values": [<c:forEach items="${jobTypes}" var="item" varStatus="status">
"${item.name}"<c:if test="${not status.last}">,</c:if>
</c:forEach>]},
{"name": "workGroup", "label": "<bean:message key='label.workGroup' />", "values": [<c:forEach items="${workGroups}" var="item" varStatus="status">
"${item.name}"<c:if test="${not status.last}">,</c:if>
</c:forEach>]}
],

"actions": {
"findAction": "job/findJob.do?dispatch=find",
"viewAction": "job/viewJob.do?dispatch=view",
"newAction":  "job/newJob.do?dispatch=create", "newHeight": 500,
"editAction": "job/editJob.do?dispatch=edit&amp;view=detailView", "editScript": "scripts/module/job/edit.js"
}
}
</jsp:root>