<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:bean="http://struts.apache.org/tags-bean"
    xmlns:c="http://java.sun.com/jsp/jstl/core"
    xmlns:fn="http://java.sun.com/jsp/jstl/functions"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    version="2.0">

{
"menuItems": [],

"columnDefs": [
{"key": "id", "label": "", "className": "width0", "hidden": true},
{"key": "fileId", "label": "File No", "width": 50, "sortable": true},
{"key": "fcaId", "label": "FCA No", "width": 50, "sortable": true, "formatter": "link"},
{"key": "nextActionDate", "label": "Next Action Date", "sortable": true, "resizeable": true},
{"key": "nextAction", "label": "Next Action", "sortable": true, "resizeable": true},
{"key": "workGroup", "label": "Work Group", "sortable": true, "resizeable": true}
],

"rows": [<c:forEach items="${entities}" var="entity" varStatus="status">
{"id": ${entity.fileId},
"fileId": "${entity.fileId}",
"fcaId": "javascript:YAHOO.jms.editFile(${entity.fileId});${entity.fcaId}",
"nextActionDate": "<fmt:formatDate value='${entity.nextActionDate}' pattern='${datePattern}'/>",
"nextAction": "${entity.nextAction}",
"workGroup": "${entity.workGroup}"}<c:if test="${not status.last}">,</c:if>
</c:forEach>],

"filters": [
{"name": "fileId", "label": "<bean:message key='label.file' />", "valuesUrl": "findFilter.do?dispatch=findFileNo"},
{"name": "workGroup", "label": "<bean:message key='label.workGroup' />", "values": [<c:forEach items="${workGroups}" var="item" varStatus="status">
"${item.name}"<c:if test="${not status.last}">,</c:if>
</c:forEach>]},
{"name": "actionCode", "label": "<bean:message key='label.actionCode' />", "valuesUrl": "findFilter.do?dispatch=findActionCode"}
],

"actions": {
"findAction": "todo/findTodoFile.do?dispatch=find",
"exportAction": "todo/findTodoFile.do?dispatch=export",
"viewAction": "",
"newAction":  "",
"editAction": "file/editFile.do?dispatch=edit&amp;view=detailView", "editScript": "scripts/module/file/edit.js"
}
}
</jsp:root>