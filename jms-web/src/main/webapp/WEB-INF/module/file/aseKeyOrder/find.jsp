<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:bean="http://struts.apache.org/tags-bean"
    xmlns:c="http://java.sun.com/jsp/jstl/core"
    xmlns:fn="http://java.sun.com/jsp/jstl/functions"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    version="2.0">
<jsp:directive.page contentType="application/json;charset=UTF-8"/>

{
"menuItems": [],

"columnDefs": [
{"key": "id", "label": "", "hidden": true},
{"key": "orderNo", "label": "Order No", "sortable": true, "formatter": "link"},
{"key": "receivedDate", "label": "Received", "sortable": true, "resizeable": true},
{"key": "supplier", "label": "Company Name", "sortable": true, "resizeable": true},
{"key": "contactName", "label": "Contact Name", "sortable": true, "resizeable": true},
{"key": "sentCustomerDate", "label": "Key Sent to Customer", "sortable": true, "resizeable": true}
],

"rows": [<c:forEach items="${entities}" var="entity" varStatus="status">
{"id": ${entity.id},
"orderNo": "javascript:YAHOO.jms.file.aseKeyOrder.editDetailView(${entity.id});${entity.id}",
"receivedDate": "<fmt:formatDate value='${entity.receivedDate}' pattern='${datePattern}' />",
"supplier": "${entity.supplier.name}",
"contactName": "${entity.contact.name}",
"sentCustomerDate": "<fmt:formatDate value='${entity.sentCustomerDate}' pattern='${datePattern}' />"
}<c:if test="${not status.last}">,</c:if>
</c:forEach>],

"filters": [
{"name": "orderNo", "label": "<bean:message key='label.aseKeyOrder.orderNo' bundle='file' />", "valuesUrl": "findFilter.do?dispatch=findAseKeyOrderNo"},
{"name": "supplierName", "label": "<bean:message key='label.aseKeyOrder.supplier' bundle='file' />", "valuesUrl": "findFilter.do?dispatch=findSupplierName&amp;ase=false"},
{"name": "contactName", "label": "<bean:message key='label.aseKeyOrder.contact.name' bundle='file' />", "title": "<bean:message key='label.aseKeyOrder.contact.name.title' bundle='file' />", "valuesUrl": "oauth/file/aseKeyOrderContactName"}
],

"actions": {
"findAction": "file/aseKeyOrder.do?dispatch=find",
"newAction": "file/aseKeyOrder.do?dispatch=edit", "newHeight": 330,
"editAction": "file/aseKeyOrder.do?dispatch=detailView&amp;view=detailView", "editScript": "scripts/module/file/edit.js",
"exportAction": "file/aseKeyOrder.do?dispatch=export"
}
}
</jsp:root>