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
{"key": "name", "label": "Name", "sortable":true, "resizeable": false, "className": "width100"},
{"key": "code", "label": "Code", "sortable":true},
{"key": "active", "label": "Active", "sortable":true, "formatter": "checkbox", "disabled": true}
],

"rows": [<c:forEach items="${entities}" var="entity" varStatus="status">
{"id": "${entity.id}",
"name": "${entity.name}",
"code": "${entity.code}",
"active": "${entity.active}"}<c:if test="${not status.last}">,</c:if>
</c:forEach>],

"filters": [],

"actions": {
"findAction": "setup/findRegion.do?dispatch=find",
"viewAction": "setup/viewRegion.do?dispatch=view",
"newAction":  "setup/editRegion.do?dispatch=edit",
"editAction": "setup/editRegion.do?dispatch=edit"}
}
</jsp:root>