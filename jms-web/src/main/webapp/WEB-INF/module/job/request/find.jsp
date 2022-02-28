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
{"key": "jobRequestId", "label": "Job Request No", "sortable": true, "resizeable": true},
{"key": "jobType", "label": "Job Type", "sortable": true, "resizeable": true},
{"key": "requestedDate", "label": "Date Requested", "sortable": true, "resizeable": true},
{"key": "requestedBy", "label": "Requested By", "sortable": true, "resizeable": true},
{"key": "requestedEmail", "label": "Requested Email", "sortable": true, "resizeable": true, "formatter": "email"},
{"key": "link", "label": "Job Link", "sortable": true, "resizeable": true, "formatter": "link"},
{"key": "workGroup", "label": "Work Group", "sortable": true, "resizeable": true},
{"key": "accept", "label": "Accept", "formatter": "link"},
{"key": "decline", "label": "Decline", "formatter": "link"}
],

"rows": [<c:forEach items="${entities}" var="entity" varStatus="status">
{"id": ${entity.jobRequestId},
"jobRequestId": "${entity.jobRequestId}",
"jobType": "${entity.jobType}",
"requestedDate": "<fmt:formatDate value='${entity.requestedDate}' pattern='${datePattern}'/>",
"requestedBy": "${entity.requestedBy}",
"requestedEmail": "${entity.requestedEmail}",
"link": "${entity.link}",
"workGroup": "${entity.workGroup}",
"accept": "javascript:YAHOO.jms.sendGetRequest('job/acceptJobRequest.do?id=${entity.jobRequestId}', null, 'Accept Pending Job');ok",
"decline": "javascript:YAHOO.jms.sendGetRequest('job/rejectJobRequest.do?id=${entity.jobRequestId}', null, 'Reject Pending Job');reject"}<c:if test="${not status.last}">,</c:if>
</c:forEach>],

"filters": [
{"name": "jobRequestId", "label": "<bean:message key='label.jobRequest' />", "valuesUrl": "findFilter.do?dispatch=findJobRequestNo"},
{"name": "jobType", "label": "<bean:message key='label.jobType' />", "values": [<c:forEach items="${jobTypes}" var="item" varStatus="status">
"${item.name}"<c:if test="${not status.last}">,</c:if>
</c:forEach>]},
{"name": "workGroup", "label": "<bean:message key='label.workGroup' />", "values": [<c:forEach items="${workGroups}" var="item" varStatus="status">
"${item.name}"<c:if test="${not status.last}">,</c:if>
</c:forEach>]}
],

"actions": {
"findAction": "job/findJobRequest.do?dispatch=find",
"viewAction": "job/viewJobRequest.do?dispatch=view",
"newAction":  "job/editJobRequest.do?dispatch=edit",
"editAction": "job/editJobRequest.do?dispatch=edit"
}
}
</jsp:root>