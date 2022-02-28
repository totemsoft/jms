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
{"key": "fileId", "label": "File No", "sortable": true, "resizeable": true},
{"key": "fcaId", "label": "FCA No", "sortable": true, "resizeable": true},
{"key": "buildingName", "label": "Building Name", "sortable": true, "resizeable": true},
{"key": "buildingAddress", "label": "Building Address", "sortable": true, "resizeable": true},
{"key": "supplierName", "label": "Supplier", "sortable": true, "resizeable": true},
{"key": "aseChangeDate", "label": "ASE Change Date &amp; Time", "sortable": true, "resizeable": true}
],

"fields": [
"id", "fileId", "fcaId", "buildingName", "buildingAddress", "supplierName", "aseChangeDate"
],

"rows": [<c:forEach items="${entities}" var="entity" varStatus="status">
{"id": ${entity.fileId},
"fileId": "${entity.fileId}",
"fcaId": "${entity.fcaId}",
"buildingName": "${fn:replace(entity.building.name, dquot, squot)}",
"buildingAddress": "${entity.buildingAddress}",
"supplierName": "${entity.supplierName}",
"aseChangeDate": "<fmt:formatDate value='${entity.aseChangeDate}' pattern='dd/MM/yyyy HH:mm' />"}<c:if test="${not status.last}">,</c:if>
</c:forEach>],

"filters": [
{"name": "fileId", "label": "<bean:message key='label.file' />", "valuesUrl": "findFilter.do?dispatch=findFileNo"},
{"name": "fcaId", "label": "<bean:message key='label.fca' />", "valuesUrl": "findFilter.do?dispatch=findFcaNo"},
{"name": "buildingName", "label": "<bean:message key='label.building.name' />", "valuesUrl": "findFilter.do?dispatch=findBuildingName"}
],

"actions": {
"findAction": "file/findAseChange.do?dispatch=find",
"viewAction": "file/viewAseChange.do?dispatch=view",
"newAction":  "",
"editAction": "file/editAseChange.do?dispatch=edit", "editScript": "scripts/common.js", "editTitle": "ASE Change Over - Supplier Scheduling"
}
}
</jsp:root>