<?xml version="1.0" encoding="UTF-8"?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:bean="http://struts.apache.org/tags-bean"
    xmlns:c="http://java.sun.com/jsp/jstl/core"
    xmlns:fn="http://java.sun.com/jsp/jstl/functions"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    version="2.0">

{
"columnDefs": [
{"key": "id", "name": "id", "label": "", "formatter": "YAHOO.widget.DataTable.formatCheckbox", "hidden": true},
{"key": "actionCodeId", "name": "actionCodeId", "label": "", "hidden": true},
{"key": "outcomeId", "name": "outcomeId", "label": "", "hidden": true},
{"key": "action", "label": "Action", "sortable": true, "resizeable": true},
{"key": "actionOutcome", "label": "Outcome", "sortable": true, "resizeable": true},
{"key": "nextActionCode", "label": "Next Action Code", "sortable": false, "resizeable": true},
{"key": "nextDueDays", "label": "Next Due Days", "className": "width10em number", "sortable": false, "resizeable": false},
{"key": "active", "name": "active", "label": "Active", "className": "width1em center", "sortable": true, "formatter": "checkbox", "disabled": false, "onchange": "YAHOO.jms.entity.actionWorkflow.onchangeActive"}
],

"rows": [<c:forEach items="${entities}" var="entity" varStatus="status">
{"id": "${entity.id}",
"actionCodeId": "${entity.actionCode.id}",
"outcomeId": "${entity.actionOutcome.id}",
"action": "${entity.actionCode.code}",
"actionOutcome": "${entity.actionOutcome.name}",
"nextActionCode": "${entity.nextActionCode.code}",
"nextDueDays": ${entity.nextDueDays},
"active": "${entity.id gt 0 ? entity.active : ''}"
}<c:if test="${not status.last}">,</c:if>
</c:forEach>],

"filters": [
{"name": "jobType", "label": "<bean:message key='label.jobType' />", "forceSelection": true, "value": "${jobType}", "hiddenValue": "${jobTypeId}", "values": [<c:forEach items="${jobTypes}" var="item" varStatus="status">
{"label":"${item.name}","value":"${item.id}"}<c:if test="${not status.last}">,</c:if>
</c:forEach>]},
{"name": "actionCode", "label": "<bean:message key='label.actionCode' />", "forceSelection": true, "value": "${actionCode}", "hiddenValue": "${actionCodeId}", "values": [<c:forEach items="${actionCodes}" var="item" varStatus="status">
{"label":"${item.code}","value":"${item.id}"}<c:if test="${not status.last}">,</c:if>
</c:forEach>]},
{"name": "active", "label": "<bean:message key='option.active' />", "value": ${active}, "values": ["true", "false"]}
],

"actions": {
"findAction": "setup/findActionWorkflow.do?dispatch=find",
"viewAction": "setup/viewActionWorkflow.do?dispatch=view",
"newAction":  "",
"editAction": "setup/editActionWorkflow.do?dispatch=edit"}
}
</jsp:root>