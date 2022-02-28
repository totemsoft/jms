<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:bean="http://struts.apache.org/tags-bean"
    xmlns:c="http://java.sun.com/jsp/jstl/core"
    xmlns:fn="http://java.sun.com/jsp/jstl/functions"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    version="2.0">

    <c:set var="yes" value="yes" />
    <c:set var="no" value="" />

{
"menuItems": [],

"columnDefs": [
{"key": "id", "label": "", "formatter": "YAHOO.widget.DataTable.formatCheckbox", "hidden": true},
{"key": "supplierId", "label": "Supplier ID", "sortable": true, "resizeable": true},
{"key": "name", "label": "Supplier Name", "sortable": true, "resizeable": true},
{"key": "type", "label": "Supplier Type", "sortable": true, "resizeable": true},
{"key": "fullName", "label": "Supplier Contact", "sortable": true, "resizeable": true},
{"key": "phone", "label": "Telephone", "sortable": true, "resizeable": true},
{"key": "fax", "label": "Fax", "sortable": true, "resizeable": true},
{"key": "email", "label": "Email", "sortable": true, "resizeable": true},
{"key": "active", "label": "Active", "sortable": true}
],

"fields": [
"id", "supplierId", "name", "type", "fullName", "phone", "fax", "email", "active"
],

"rows": [<c:forEach items="${entities}" var="entity" varStatus="status">
{"id": ${entity.supplierId},
"supplierId": "${entity.supplierId}",
"name": "${entity.name}",
"type": "${entity.supplierType.name}",
"fullName": "${entity.contact.fullName}",
"phone": "${entity.contact.phone}",
"fax": "${entity.contact.fax}",
"email": "${entity.contact.email}",
"active": "${entity.active ? yes : no}"}<c:if test="${not status.last}">,</c:if>
</c:forEach>],

"filters": [
{"name": "supplierName", "label": "<bean:message key='label.supplier.name' bundle='supplier' />", "valuesUrl": "findFilter.do?dispatch=findSupplierName"},
{"name": "supplierContact", "label": "<bean:message key='label.supplier.contact' bundle='supplier' />", "valuesUrl": "findFilter.do?dispatch=findSupplierContact"},
{"name": "supplierPhone", "label": "<bean:message key='label.supplier.phone' bundle='supplier' />", "valuesUrl": "findFilter.do?dispatch=findSupplierPhone"}
],

"actions": {
"findAction": "supplier/findSupplier.do?dispatch=find",
"viewAction": "supplier/viewSupplier.do?dispatch=view",
"newAction":  "supplier/editSupplier.do?dispatch=edit",
"editAction": "supplier/editSupplier.do?dispatch=edit"
}
}
</jsp:root>