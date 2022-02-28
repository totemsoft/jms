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
{"key": "name", "label": "Job Type", "sortable":true, "resizeable":true},
{"key": "securityGroup", "label": "Security Group", "sortable":true, "resizeable":true},
{"key": "actionCode", "label": "Action Code", "sortable":true, "resizeable":true},
{"key": "closeJobSecurityGroup", "label": "Close Job Security Group", "sortable":true, "resizeable":true},
{"key": "workGroup", "label": "Work Group", "sortable":true, "resizeable":true}
],

"fields": [
"id", "name", "securityGroup", "actionCode", "closeJobSecurityGroup", "workGroup"
],

"rows": [<c:forEach items="${entities}" var="entity" varStatus="status">
{"id": "${entity.id}",
"name": "${entity.name}",
"securityGroup": "${entity.securityGroup.name}",
"actionCode": "${entity.actionCode.code}",
"closeJobSecurityGroup": "${entity.closeJobSecurityGroup.name}",
"workGroup": "${entity.workGroup.name}"}<c:if test="${not status.last}">,</c:if>
</c:forEach>],

"filters": [],

"actions": {
"findAction": "setup/findJobType.do?dispatch=find",
"viewAction": "setup/viewJobType.do?dispatch=view",
"newAction":  "setup/editJobType.do?dispatch=edit",
"editAction": "setup/editJobType.do?dispatch=edit"}
}
</jsp:root>