<?xml version="1.0" encoding="UTF-8"?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:bean="http://struts.apache.org/tags-bean"
    xmlns:c="http://java.sun.com/jsp/jstl/core"
    xmlns:fn="http://java.sun.com/jsp/jstl/functions"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    version="2.0">

    <c:set var="yes" value="yes" />
    <c:set var="no" value="no" />

{
"columnDefs": [
{"key": "id", "label": "", "formatter": "YAHOO.widget.DataTable.formatCheckbox", "hidden": true},
{"key": "name", "label": "Outcome", "sortable":true, "resizeable":true},
{"key": "createdBy", "label": "Created By", "sortable":true},
{"key": "createdDate", "label": "Created Date", "sortable":true}
],

"rows": [<c:forEach items="${entities}" var="entity" varStatus="status">
{"id": "${entity.id}",
"name": "${entity.name}",
"createdBy": "${entity.createdBy.login}",
"createdDate": "<fmt:formatDate value='${entity.createdDate}' pattern='${datePattern}'/>"
}<c:if test="${not status.last}">,</c:if>
</c:forEach>],

"filters": [],

"actions": {
"findAction": "setup/findActionOutcome.do?dispatch=find",
"viewAction": "setup/viewActionOutcome.do?dispatch=view",
"newAction":  "setup/editActionOutcome.do?dispatch=edit",
"editAction": "setup/editActionOutcome.do?dispatch=edit"}
}
</jsp:root>