<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:bean="http://struts.apache.org/tags-bean"
    xmlns:c="http://java.sun.com/jsp/jstl/core"
    xmlns:fn="http://java.sun.com/jsp/jstl/functions"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    xmlns:argus-html="/WEB-INF/tld/argus-html.tld"
    version="2.0">
<jsp:directive.page contentType="application/json;charset=UTF-8"/>
{
"menuItems": [],

"columnDefs": [
{"key": "id", "label": "", "className": "width0", "hidden": true},
{"key": "fileId", "label": "File No", "width": 50, "sortable": true},
{"key": "fcaId", "label": "FCA No", "width": 50, "sortable": true, "formatter": "link"},
{"key": "buildingName", "label": "Building Name", "sortable": true, "resizeable": true},
{"key": "buildingAddress", "label": "Building Address", "sortable": true, "resizeable": true},
{"key": "supplierName", "label": "Supplier", "sortable": true, "resizeable": true},
{"key": "ownerName", "label": "Owner Name", "sortable": true, "resizeable": true}
],

"rows": [<c:forEach items="${entities}" var="entity" varStatus="status">
{"id": ${entity.fileId},
"fileId": "${entity.fileId}",
"fcaId": "javascript:YAHOO.jms.editFile(${entity.fileId});${entity.fcaId}",
"buildingName": "<argus-html:json text='${entity.buildingName}' />",
"buildingAddress": "<argus-html:json text='${entity.buildingAddress}' />",
"supplierName": "<argus-html:json text='${entity.supplierName}' />",
"ownerName": "<argus-html:json text='${entity.ownerName}' />"}<c:if test="${not status.last}">,</c:if>
</c:forEach>],

"pagination": {
"rowsPerPageOptions": [10, 50, 100, 200, 500, 1000],
"rowsPerPage": 100
},

"filters": [
{"name": "fileId", "label": "<bean:message key='label.file' />", "valuesUrl": "findFilter.do?dispatch=findFileNo"},
{"name": "fcaId", "label": "<bean:message key='label.fca' />", "valuesUrl": "findFilter.do?dispatch=findFcaNo"},
{"name": "buildingName", "label": "<bean:message key='label.building.name' />", "valuesUrl": "findFilter.do?dispatch=findBuildingName"},
{"name": "buildingAddress", "label": "<bean:message key='label.building.address' />", "valuesUrl": "findFilter.do?dispatch=findBuildingAddress"},
{"name": "ownerName", "label": "<bean:message key='label.owner.name' />", "valuesUrl": "findFilter.do?dispatch=findOwnerName"},
{"name": "ownerContact", "label": "<bean:message key='label.owner.contact' />", "valuesUrl": "findFilter.do?dispatch=findOwnerContact"},
{"name": "supplierName", "label": "<bean:message key='label.supplier.name' />", "valuesUrl": "findFilter.do?dispatch=findSupplierName"},
{"name": "disconnectedFile", "label": "<bean:message key='label.file.disconnected' bundle='file' />", "values": ["true", "false"]}
],

"actions": {
"findAction": "file/findFile.do?dispatch=find",
"viewAction": "",
"newAction":  "file/newFile.do?dispatch=create&amp;view=detailView&amp;readOnly=false", "newScript": "scripts/module/file/edit.js",
"editAction": "file/editFile.do?dispatch=edit&amp;view=detailView", "editScript": "scripts/module/file/edit.js"
}
}
</jsp:root>