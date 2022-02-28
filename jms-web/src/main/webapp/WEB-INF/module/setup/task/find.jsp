<?xml version="1.0" encoding="UTF-8"?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:bean="http://struts.apache.org/tags-bean"
    xmlns:c="http://java.sun.com/jsp/jstl/core"
    xmlns:fn="http://java.sun.com/jsp/jstl/functions"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    version="2.0">

{
"columnDefs": [
{"key": "id", "label": "", "hidden": true, "className": "width0"},
{"key": "name", "label": "Name", "hidden": true},
{"key": "description", "label": "Task", "sortable": true, "resizeable": true},
{"key": "cronExpression", "label": "Cron Expression", "sortable": true, "resizeable": true},
{"key": "active", "label": "Active", "sortable": true, "formatter": "checkbox", "disabled": true},
{"key": "run", "label": "Run", "formatter": "link"},
{"key": "import", "label": "Import", "formatter": "link"}
],

"rows": [<c:forEach items="${entities}" var="entity" varStatus="status">
{"id": "${entity.id}",
"name": "${entity.name}",
"description": "${entity.description}",
"cronExpression": "${entity.taskRequest.cronExpression}",
"active": ${entity.active},
"run": "<c:if test='${entity.active}'>javascript:YAHOO.jms.sendGetRequest('setup/scheduledTask.do?dispatch=runTask&amp;entity.scheduledTaskId=${entity.id}');run</c:if>",
"import": "javascript:YAHOO.jms.sendGetRequest('setup/scheduledTask.do?dispatch=importData&amp;entity.scheduledTaskId=${entity.id}', null, 'Import', null, 450, 150);import"
}<c:if test="${not status.last}">,</c:if>
</c:forEach>],

"filters": [],

"actions": {
"findAction": "setup/scheduledTask.do?dispatch=find",
"viewAction": "setup/scheduledTask.do?dispatch=view",
"newAction":  "setup/scheduledTask.do?dispatch=edit", "newHeight" : 200,
"editAction": "setup/scheduledTask.do?dispatch=edit", "editHeight": 200
}
}
</jsp:root>