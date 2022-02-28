<?xml version="1.0" encoding="UTF-8"?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:bean="http://struts.apache.org/tags-bean"
    xmlns:c="http://java.sun.com/jsp/jstl/core"
    xmlns:fn="http://java.sun.com/jsp/jstl/functions"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    version="2.0">

    <c:set var="backslash">\</c:set>
    <c:set var="backslashReplace">/</c:set>

{
"columnDefs": [
{"key": "id", "label": "", "formatter": "YAHOO.widget.DataTable.formatCheckbox", "hidden": true},
{"key": "code", "label": "Code", "sortable":true, "resizeable":true},
{"key": "name", "label": "Name", "sortable":true, "resizeable":true},
{"key": "templateType", "label": "Template Type", "sortable":true, "resizeable":true},
{"key": "location", "label": "Location", "sortable":true, "resizeable":true},
{"key": "contentType", "label": "Content Type", "sortable":true, "resizeable":true},
{"key": "download", "label": "", "formatter": "link", "resizeable":true},
{"key": "config", "label": "", "formatter": "link", "resizeable":true}
],

"rows": [<c:forEach items="${entities}" var="entity" varStatus="status">
{"id": "${entity.id}",
"code": "${entity.code}",
"name": "${entity.name}",
"templateType": "${entity.templateType.name}",
"location": "${fn:replace(entity.location, backslash, backslashReplace)}",
"contentType": "${entity.contentType}",
"download": "javascript:YAHOO.jms.sendDownloadRequest('setup/downloadTemplate.do?id=${entity.id}', null, 'Download Template');download",
"config": "<c:if test='${entity.config.id gt 0}'>javascript:YAHOO.jms.sendDownloadRequest('setup/downloadDocument.do?documentId=${entity.config.id}', null, 'Download Config');config</c:if>"
}<c:if test="${not status.last}">,</c:if>
</c:forEach>],

"filters": [
{"name": "templateTypes", "label": "Template Type", "values": [<c:forEach items="${templateTypes}" var="item" varStatus="status">
"${item.name}"<c:if test="${not status.last}">,</c:if>
</c:forEach>]}
],

"actions": {
"findAction":   "setup/findTemplate.do",
"helpAction":   "setup/helpTemplate.do", "helpTitle": "Template Help",
"viewAction":   "setup/viewTemplate.do?dispatch=view",
"newAction":    "setup/editTemplate.do?dispatch=edit", "newTitle": "New Template",
"editAction":   "setup/editTemplate.do?dispatch=edit", "editTitle": "Edit Template", "editScript": "scripts/common.js",
"deleteAction": "setup/editTemplate.do?dispatch=delete&amp;readOnly=false", "deleteTitle": "Delete Template"
}
}
</jsp:root>