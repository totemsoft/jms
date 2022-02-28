<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:bean="http://struts.apache.org/tags-bean"
    xmlns:c="http://java.sun.com/jsp/jstl/core"
    xmlns:fn="http://java.sun.com/jsp/jstl/functions"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    version="2.0">
<jsp:directive.page contentType="application/json;charset=UTF-8"/>
<c:set var="completeLink">&lt;span class='edit'&gt;${nbsp}&lt;/span&gt;</c:set>
<c:set var="deleteLink">&lt;span class='delete'&gt;${nbsp}&lt;/span&gt;</c:set>
{
"menuItems": [],

"columnDefs": [
{"key": "id", "label": "Call ID"},
{"key": "sapCustNo", "label": "Customer No", "sortable": true},
{"key": "fileId", "label": "File No", "sortable": true},
{"key": "fcaId", "label": "FCA No", "sortable": true},
{"key": "createdBy", "label": "Taken By", "sortable": true, "resizeable": true},
{"key": "responsibleUser", "label": "Forwarded To", "sortable": true, "resizeable": true},
{"key": "completedBy", "label": "Completed By", "sortable": true, "resizeable": true, "hidden": ${not completed}},
{"key": "firstName", "label": "Customer Name", "sortable": true, "resizeable": true},
{"key": "surname", "label": "Last Name", "sortable": true, "resizeable": true},
{"key": "destination", "label": "Phone #", "sortable": true, "resizeable": true},
{"key": "notation", "label": "Details", "resizeable": true},
{"key": "complete", "label": "", "title": "Complete Action", "formatter": "link", "hidden": ${completed}},
{"key": "delete", "label": "", "title": "Delete Action", "formatter": "link", "hidden": ${completed}}
],

"rows": [<c:forEach items="${entities}" var="entity" varStatus="status">
{"id": ${entity.id},
"sapCustNo": "${entity.sapCustNo}",
"fileId": "${entity.fileId}",
"fcaId": "${entity.fcaId}",
"createdBy": "${entity.createdBy}",
"responsibleUser": "${entity.responsibleUser}",
"completedBy": "${entity.completedBy}",
"firstName": "${entity.contact.firstName}",
"surname": "${entity.contact.surname}",
"destination": "${entity.destination}",
"notation": "${entity.notation}",
"complete": "javascript:YAHOO.jms.editFileAction(${entity.id});${completeLink}",
"delete": "javascript:YAHOO.jms.deleteFileAction(${entity.id});${deleteLink}"
}<c:if test="${not status.last}">,</c:if>
</c:forEach>],

"filters": [
{"name": "fileId", "label": "<bean:message key='label.file' />", "valuesUrl": "findFilter.do?dispatch=findFileNo"},
{"name": "fcaId", "label": "<bean:message key='label.fca' />", "valuesUrl": "findFilter.do?dispatch=findFcaNo"},
{"name": ""},
{"name": "actionCode", "label": "<bean:message key='label.actionCode' />", "values": [<c:forEach items="${actionCodes}" var="item" varStatus="status">
{"label":"${item.code}","value":"${item.id}"}<c:if test="${not status.last}">,</c:if></c:forEach>],"forceSelection":false},
{"name": "completed", "label": "<bean:message key='label.action.completed' bundle='file' />", "values": ["true", "false"]}
],

"actions": {
"findAction": "file/findCall.do?dispatch=find",
"newAction":  "file/addFileAction.do?dispatch=changeActionType&amp;actionTypeId=2&amp;todoAction=true",
"exportAction": "file/findCall.do?dispatch=export"
}
}
</jsp:root>