<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:bean="http://struts.apache.org/tags-bean"
    xmlns:c="http://java.sun.com/jsp/jstl/core"
    xmlns:fn="http://java.sun.com/jsp/jstl/functions"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    version="2.0">
<jsp:directive.page contentType="application/json;charset=UTF-8"/>
{
"tabs": ["tab1"
, {"id":"tab3","config":{"label":"Invoicing"}}
],

"menuItems": [],

"columnDefs": [
{"key": "batch", "label": "Batch", "className": "center middle", "sortable": true, "resizeable": false},
{"key": "id", "label": "Invoice", "className": "center middle", "hidden": false, "sortable": true, "resizeable": false},
{"key": "area", "label": "Area", "className": "center middle", "sortable": true, "resizeable": false},
{"key": "dateReceived", "label": "Date Received", "className": "center middle", "sortable": true, "resizeable": false},
{"key": "lastBy", "label": "User", "className": "center middle", "sortable": true, "resizeable": false},
{"key": "dateActioned", "label": "Date Actioned", "className": "center middle", "sortable": true, "resizeable": false},
{"key": "status", "label": "Status", "className": "center middle", "sortable": true, "resizeable": false},
{"key": "add", "label": "", "className": "center middle width2em noborder-right", "formatter": "link", "width": 20},
{"key": "edit", "label": "Actions", "className": "center middle width2em noborder-right", "formatter": "link", "width": 20},
{"key": "view", "label": "", "className": "center middle width2em", "formatter": "link", "width": 20}
],

"rows": [<c:forEach items="${entities}" var="e" varStatus="status">
{"id": "${e.id}",
"batch": "${e.invoiceBatch.id}",
"area": "${not empty e.invoiceArea.id ? e.invoiceArea.name : e.invoiceBatch.invoiceArea.name}",
"dateReceived": "<fmt:formatDate value='${e.dateReceived}' pattern='${datePattern}' />",
"lastBy": "${not empty e.lastBy ? e.lastBy.login : e.invoiceBatch.lastBy.login}",
"dateActioned": "<fmt:formatDate value='${e.dateActioned}' pattern='${datePattern}' />",
"status": "TBD",
"add": "<c:if test='${not empty e.invoiceBatch.id}'>javascript:YAHOO.jms.finance.addBatchInvoice('${e.invoiceBatch.id}');add</c:if>",
"edit": "<c:if test='${not empty e.id}'>javascript:YAHOO.jms.finance.editBatchInvoice('${e.id}');edit</c:if>",
"view": "<c:if test='${not empty e.id}'>javascript:YAHOO.jms.finance.viewBatchInvoice('${e.id}');view</c:if>"
}<c:if test="${not status.last}">,</c:if>
</c:forEach>],

"filters": [
{"name": "year", "label": "<bean:message key='label.finance.invoice.fiscalYear' bundle='finance' />", "values": [<c:forEach items="${fiscalYears}" var="item" varStatus="status">"${item}"<c:if test="${not status.last}">,</c:if></c:forEach>]},
{"name": "invoiceArea", "label": "<bean:message key='label.invoiceArea' bundle='finance' />", "valuesUrl": "findFilter.do?dispatch=findInvoiceArea", "forceSelection": true},
{"name": "fca", "label": "<bean:message key='label.fca' />", "valuesUrl": "findFilter.do?dispatch=findFcaNo", "forceSelection": true}
],

"actions": {
"findAction": "finance/externalInvoice.do?dispatch=find",
"customAction": "finance/externalInvoice.do?dispatch=addBatch&amp;readOnly=false", "customTitle":"Add Batch"
}
}
</jsp:root>