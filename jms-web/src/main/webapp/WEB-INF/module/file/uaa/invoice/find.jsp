<?xml version="1.0" encoding="utf-8"?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:bean="http://struts.apache.org/tags-bean"
    xmlns:c="http://java.sun.com/jsp/jstl/core"
    xmlns:fn="http://java.sun.com/jsp/jstl/functions"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    version="2.0">
<jsp:directive.page contentType="application/json;charset=UTF-8"/>
{
"columnDefs": [
{"key":"id","className":"width0","hidden":true},
{"key":"batch","label":"Batch","className":"center middle","sortable":true,"resizeable":false},
{"key":"fiscalYear","label":"Fiscal Year","className":"center middle","sortable":true,"resizeable":false},
{"key":"invoiceArea","label":"Invoice Area","className":"center middle","sortable":true,"resizeable":false},
{"key":"region","label":"Region","className":"center middle","sortable":true,"resizeable":false},
{"key":"dateReceived","label":"Received Date","className":"center middle","sortable":true,"resizeable":false},
{"key":"incidentDate","label":"Incident Date","className":"center middle","sortable":true,"resizeable":false},
{"key":"sapCustNo","label":"Customer","className":"center middle","sortable":true,"resizeable":false}
],

"rows":[<c:forEach items="${entities}" var="e" varStatus="s">
{"id":"${e.id}",
"batch":"${e.invoiceBatch.id}",
"fiscalYear":"${e.fiscalYear}",
"invoiceArea":"${e.invoiceArea.name}",
"region":"${e.region.name}",
"dateReceived":"<fmt:formatDate value='${e.dateReceived}' pattern='${datePattern}' />",
"incidentDate":"<fmt:formatDate value='${e.incidentDate}' pattern='${datePattern}' />",
"sapCustNo":"${e.sapCustNo}"
}<c:if test="${not s.last}">,</c:if></c:forEach>],

"filters": [
{"id": "f_date", "name": "date", "label": "<bean:message key="label.date" />"},
{"name": "fcaId", "label": "<bean:message key='label.fca' />", "valuesUrl": "findFilter.do?dispatch=findFcaNo", "forceSelection": true},
{"name": "fileId", "label": "<bean:message key='label.file' />", "valuesUrl": "findFilter.do?dispatch=findFileNo", "forceSelection": true}
],

"actions": {
"findAction": "file/uaaInvoice.do?dispatch=find",
"viewAction": "file/uaaInvoice.do?dispatch=view",
"exportAction": "file/uaaInvoice.do?dispatch=export",
"importAction": "file/uaaInvoice.do?dispatch=importData", "importWidth": 450, "importHeight": 150
}
}
</jsp:root>