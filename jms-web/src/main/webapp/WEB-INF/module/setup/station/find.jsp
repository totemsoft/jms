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
{"key": "stationId", "label": "Code", "sortable":true, "resizeable":true},
{"key": "name", "label": "Name", "sortable":true, "resizeable":true},
{"key": "area", "label": "Area", "sortable":true, "resizeable":true},
{"key": "region", "label": "Region", "sortable":true, "resizeable":true},
{"key": "active", "label": "Active", "sortable":true, "formatter": "checkbox", "disabled": true}
],

"fields": [
"id", "stationId", "name", "area", "region", "active"
],

"rows": [<c:forEach items="${entities}" var="entity" varStatus="status">
{"id": "${entity.id}",
"stationId": "${entity.stationId}",
"name": "${entity.name}",
"area": "${entity.area.name}",
"region": "${entity.area.region.name}",
"active": "${entity.active}"}<c:if test="${not status.last}">,</c:if>
</c:forEach>],

"filters": [],

"actions": {
"findAction": "setup/findStation.do?dispatch=find",
"viewAction": "setup/viewStation.do?dispatch=view",
"newAction":  "setup/editStation.do?dispatch=edit",
"editAction": "setup/editStation.do?dispatch=edit"}
}
</jsp:root>