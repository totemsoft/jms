<?xml version="1.0" encoding="UTF-8"?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:bean="http://struts.apache.org/tags-bean"
    xmlns:c="http://java.sun.com/jsp/jstl/core"
    xmlns:fn="http://java.sun.com/jsp/jstl/functions"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    version="2.0">
{
"columnDefs": [
{"key": "id", "label": "", "formatter": "YAHOO.widget.DataTable.formatCheckbox", "hidden": true},
{"key": "code", "label": "Action Code", "sortable":true, "resizeable":true},
{"key": "notation", "label": "Default Notation", "sortable":true, "resizeable":true},
{"key": "actionType", "label": "Action Type", "sortable":true, "resizeable":true},
{"key": "jobType", "label": "Job Type", "sortable":true, "resizeable":true},
{"key": "workGroup", "label": "Work Group", "sortable":true, "resizeable":true},
{"key": "bulkMail", "label": "Bulk Mail", "sortable":true, "formatter": "checkbox", "disabled": true},
{"key": "active", "label": "Active", "sortable":true, "formatter": "checkbox", "disabled": true}
],

"rows": [<c:forEach items="${entities}" var="entity" varStatus="status">
{"id": "${entity.id}",
"code": "${entity.code}",
"notation": "${entity.notation}",
"actionType":" ${entity.actionType.name}",
"jobType":" ${entity.jobType.name}",
"workGroup":" ${entity.workGroup.name}",
"bulkMail": ${entity.bulkMail},
"active": "${entity.active}"
}<c:if test="${not status.last}">,</c:if>
</c:forEach>],

"filters": [
{"name": "actionTypes", "label": "Action Type", "values": [<c:forEach items="${actionTypes}" var="item" varStatus="status">
"${item.name}"<c:if test="${not status.last}">,</c:if>
</c:forEach>]}
],

"actions": {
"findAction": "setup/findActionCode.do?dispatch=find",
"viewAction": "setup/viewActionCode.do?dispatch=view",
"newAction":  "setup/editActionCode.do?dispatch=edit", "newWidth": 0.75, "newHeight": 300,
"editAction": "setup/editActionCode.do?dispatch=edit", "editWidth": 0.75, "editHeight": 300}
}
</jsp:root>