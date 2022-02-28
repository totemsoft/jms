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
{"key": "name", "label": "Name", "maxAutoWidth": 300, "sortable":true, "resizeable":true},
{"key": "chargeable", "label": "Chargeable", "maxAutoWidth": 30, "sortable": true, "formatter": "checkbox", "disabled": true}
],

"rows": [<c:forEach items="${entities}" var="entity" varStatus="status">
{"id": "${entity.id}",
"name": "${entity.name}",
"chargeable": "${entity.chargeable}"
}<c:if test="${not status.last}">,</c:if>
</c:forEach>],

"filters": [],

"actions": {
"findAction": "setup/findSupplierType.do?dispatch=find",
"viewAction": "setup/viewSupplierType.do?dispatch=view",
"newAction":  "setup/editSupplierType.do?dispatch=edit",
"editAction": "setup/editSupplierType.do?dispatch=edit"}
}
</jsp:root>