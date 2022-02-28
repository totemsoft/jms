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
{"key": "module", "label": "Module", "sortable":true, "resizeable":false},
{"key": "name", "label": "Name", "sortable":true, "resizeable":false},
{"key": "query", "label": "Query", "sortable":false, "resizeable":false},
{"key": "description", "label": "Description", "sortable":false, "resizeable":true}
],

"fields": [
"id", "module", "name", "query", "description"
],

"rows": [<c:forEach items="${entities}" var="entity" varStatus="status">
{"id": "${entity.id}",
"module": "${entity.module}",
"name": "${entity.name}",
"query": "${entity.query}",
"description": "${entity.description}"
}<c:if test="${not status.last}">,</c:if>
</c:forEach>],

"filters": [],

"actions": {
"findAction": "setup/systemFunction.do?dispatch=find",
"viewAction": "setup/systemFunction.do?dispatch=view",
"editAction": "setup/systemFunction.do?dispatch=edit", "editHeight": 200
}
}
</jsp:root>