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
{"key": "id", "label": "", "className": "width0", "hidden": true},
{"key": "fileId", "label": "File No", "width": 50, "sortable": true},
{"key": "fcaId", "label": "FCA No", "width": 50, "sortable": true, "formatter": "link"},
{"key": "input", "label": "Input", "sortable": false, "className": "number"},
{"key": "isolationDate", "label": "Isolation Date", "sortable": true, "resizeable": false},
{"key": "deIsolationDate", "label": "De-Isolation Date", "sortable": true, "resizeable": false},
{"key": "isolationTime", "label": "Time", "sortable": true, "className": "number"},
{"key": "details", "label": "Details", "sortable": false, "resizeable": true}
],

"rows": [<c:forEach items="${entities}" var="entity" varStatus="status">
{"id": ${entity.id},
"fileId": "${entity.fca.file.id}",
"fcaId": "javascript:YAHOO.jms.editFile(${entity.fca.file.id});${entity.fca.id}",
"input": "${entity.input}",
"isolationDate": "<fmt:formatDate value='${entity.isolationDate}' pattern='${dateTimePattern}' />",
"deIsolationDate": "<fmt:formatDate value='${entity.deIsolationDate}' pattern='${dateTimePattern}' />",
"isolationTime": "${entity.isolationTimeText}",
"details": "${entity.details}"
}<c:if test="${not status.last}">,</c:if>
</c:forEach>],

"filters": [
{"name": "fcaId", "label": "<bean:message key='label.fca' />", "valuesUrl": "findFilter.do?dispatch=findFcaNo"},
{"id": "f_date_time_isolationDate", "name": "isolationDate", "label": "<bean:message key='label.isolation.isolationDate' bundle='file' />"},
{"id": "f_date_time_deIsolationDate", "name": "deIsolationDate", "label": "<bean:message key='label.isolation.deIsolationDate' bundle='file' />"},
{"name": "region", "label": "<bean:message key='label.region.name' />", "values": [<c:forEach items="${regions}" var="item" varStatus="status">{"label":"${item.name}","value":"${item.id}"}<c:if test="${not status.last}">,</c:if></c:forEach>]},
{"name": "area", "label": "<bean:message key='label.area.name' />", "valuesUrl": "findFilter.do?dispatch=findArea"},
{"name": "station", "label": "<bean:message key='label.station.name' />", "valuesUrl": "findFilter.do?dispatch=findStation"}
],

"actions": {
"findAction": "file/isolation.do?dispatch=find",
"newAction":  "file/isolation.do?dispatch=edit", "newHeight": 500,
"editAction": "file/isolation.do?dispatch=edit", "editHeight": 500,
"exportAction": "file/isolation.do?dispatch=export",
"importAction": "file/isolation.do?dispatch=importData", "importWidth": 450, "importHeight": 150
}
}
</jsp:root>