<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:bean="http://struts.apache.org/tags-bean"
    xmlns:c="http://java.sun.com/jsp/jstl/core"
    xmlns:fn="http://java.sun.com/jsp/jstl/functions"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    version="2.0">
<jsp:directive.page contentType="application/json;charset=UTF-8"/>

<jsp:useBean id="fileForm" scope="session" class="au.gov.qld.fire.jms.web.module.file.FileForm" />

{
"menuItems": [],

"columnDefs": [
{"key": "id", "label": "", "hidden": true},
{"key": "orderNo", "label": "Order No", "sortable": true, "formatter": "link"},
{"key": "supplier", "label": "Company Name", "sortable": true, "resizeable": true},
{"key": "contactName", "label": "Key Holder", "sortable": true, "resizeable": true},
{"key": "aseKeyNo", "label": "Key Number", "sortable": true, "formatter": "link"},
{"key": "sentCustomerDate", "label": "Key Sent to Customer", "sortable": true, "resizeable": true},
{"key": "status", "label": "Status", "sortable": true}
],

"rows": [<c:forEach items="${entities}" var="entity" varStatus="status">
{"id": ${entity.id},
"orderNo": "javascript:YAHOO.jms.file.aseKeyOrder.editDetailView(${entity.order.id});${entity.order.id}",
"supplier": "${entity.supplier.name}",
"contactName": "${entity.contact.name}",
"aseKeyNo": "javascript:YAHOO.jms.file.aseKey.editDetailView(${entity.id});${entity.aseKeyNo}",
"sentCustomerDate": "<fmt:formatDate value='${entity.sentCustomerDate}' pattern='${datePattern}' />",
"status": "${entity.status.name}"
}<c:if test="${not status.last}">,</c:if>
</c:forEach>],

"filters": [
{"name": "orderNo", "label": "<bean:message key='label.aseKey.orderNo' bundle='file' />", "valuesUrl": "findFilter.do?dispatch=findAseKeyOrderNo"},
{"name": "supplierName", "label": "<bean:message key='label.aseKey.supplier' bundle='file' />", "valuesUrl": "findFilter.do?dispatch=findSupplierName&amp;ase=false"},
{"name": "contactName", "label": "<bean:message key='label.aseKey.contact.name' bundle='file' />", "title": "<bean:message key='label.aseKey.contact.name.title' bundle='file' />", "valuesUrl": "oauth/file/aseKeyContactName"},

{"name": "aseKeyNo", "label": "<bean:message key='label.aseKey.aseKeyNo' bundle='file' />", "valuesUrl": "findFilter.do?dispatch=findAseKeyNo"},
{"name": "status", "label": "<bean:message key='label.aseKey.status' bundle='file' />", "forceSelection": true, "values": [<c:forEach items="${statuses}" var="item" varStatus="status">{"label":"${item.name}","value":"${item.code}"}<c:if test="${not status.last}">,</c:if></c:forEach>]}
],

"actions": {
"findAction": "file/aseKey.do?dispatch=find",
"viewAction": "file/aseKey.do?dispatch=view",
"editAction": "file/aseKey.do?dispatch=detailView&amp;view=detailView", "editScript": "scripts/module/file/edit.js",
"exportAction": "file/aseKey.do?dispatch=export"
}
}
</jsp:root>