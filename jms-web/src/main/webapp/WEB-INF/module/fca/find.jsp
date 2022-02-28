<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:bean="http://struts.apache.org/tags-bean"
    xmlns:c="http://java.sun.com/jsp/jstl/core"
    xmlns:fn="http://java.sun.com/jsp/jstl/functions"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    version="2.0">
<jsp:directive.page contentType="application/json;charset=UTF-8"/>
<c:set var="dquot">"</c:set>
<c:set var="squot">'</c:set>
{
"menuItems": [],

"columnDefs": [
{"key": "id", "label": "", "formatter": "YAHOO.widget.DataTable.formatCheckbox", "hidden": true},
{"key": "fcaId", "label": "FCA No", "sortable": true, "resizeable": true},
{"key": "fileId", "label": "File No", "sortable": true, "resizeable": true},
{"key": "fileStatus", "label": "File Status", "sortable": true, "resizeable": true},
{"key": "buildingName", "label": "Building Name", "sortable": true, "resizeable": true}
],

"fields": [
"id", "fcaId", "fileId", "fileStatus", "buildingName"
],

"rows": [<c:forEach items="${entities}" var="entity" varStatus="status">
{"id": "${entity.fcaId}",
"fcaId": "${entity.fcaId}",
"fileId": "${entity.file.fileId}",
"fileStatus": "${entity.file.fileStatus.name}",
"buildingName": "${fn:replace(entity.file.building.name, dquot, squot)}"}<c:if test="${not status.last}">,</c:if>
</c:forEach>],

"filters": [
{"name": "region", "label": "<bean:message key='label.region.name' />", "values": [<c:forEach items="${regions}" var="item" varStatus="status">{"label":"${item.name}","value":"${item.id}"}<c:if test="${not status.last}">,</c:if></c:forEach>]},
{"name": "area", "label": "<bean:message key='label.area.name' />", "valuesUrl": "findFilter.do?dispatch=findArea"},
{"name": "station", "label": "<bean:message key='label.station.name' />", "valuesUrl": "findFilter.do?dispatch=findStation"},
{"name": "unassignedFca", "label": "<bean:message key='label.fca.unassigned' bundle='fca' />", "values": ["true", "false"]},
{"name": "subPanel", "label": "<bean:message key='label.fca.subPanel' bundle='fca' />", "values": ["true", "false"]},
{"name": "fcaId", "label": "<bean:message key='label.fca' />", "valuesUrl": "findFilter.do?dispatch=findFcaNo"}
],

"actions": {
"findAction": "fca/findFca.do?dispatch=find", "newTitle": "FCA Number Control",
"viewAction": "fca/viewFca.do?dispatch=view",
"newAction":  "",
"editAction": "fca/editFca.do?dispatch=edit", "editScript": null, "editTitle": "Assign FCA Number"
}
}
</jsp:root>