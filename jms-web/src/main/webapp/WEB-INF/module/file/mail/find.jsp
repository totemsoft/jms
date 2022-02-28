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
{"key": "mailIn", "label": "", "sortable": true, "formatter": "class", "className": "width2em"},
{"key": "date", "label": "Date", "sortable": true, "resizeable": true},
{"key": "fcaId", "label": "FCA No", "sortable": true},
{"key": "sapCustNo", "label": "<bean:message key='label.sapHeader.sapCustNo' bundle="file" />", "sortable": true},
{"key": "name", "label": "Customer Name", "sortable": true, "resizeable": true}
],

"fields": [
"id", "mailIn", "date", "fcaId", "sapCustNo", "name"
],

"rows": [<c:forEach items="${entities}" var="entity" varStatus="status">
{"id": ${entity.id},
"mailIn": {"className": "${entity.mailIn ? 'arrow-right-blue' : 'arrow-left-green'}", "title": "${entity.mailIn ? 'Incoming' : 'Outgoing'}"},
"date": "<fmt:formatDate value='${entity.date}' pattern='${datePattern}' />",
"fcaId": "${entity.file.fca.fcaId}",
"sapCustNo": "${entity.file.sapHeader.sapCustNo}",
"name": "${entity.contact.name}"
}<c:if test="${not status.last}">,</c:if>
</c:forEach>],

"filters": [
{"id": "f_date", "name": "date", "label": "<bean:message key="label.mail.date" bundle="file" />"},
{"name": "fcaNo", "label": "<bean:message key='label.fca' />", "valuesUrl": "findFilter.do?dispatch=findFcaNo"},
{"name": "sapCustNo", "label": "<bean:message key='label.sapHeader.sapCustNo' bundle="file" />", "valuesUrl": "findFilter.do?dispatch=findSapCustNo"},
{"name": "mailOut", "label": "<bean:message key='label.mail.mailOut' bundle='file' />", "values": ["true", "false"]},
{"name": "mailIn", "label": "<bean:message key='label.mail.mailIn' bundle='file' />", "values": ["true", "false"]},
{"name": "rts", "label": "<bean:message key='label.mail.rts' bundle='file' />", "values": ["true", "false"], "title": "Return to Sender"},
{"name": "mailType", "label": "<bean:message key='label.mail.mailType' bundle='file' />", "values": [<c:forEach items="${mailTypes}" var="item" varStatus="status">"${item.name}"<c:if test="${not status.last}">,</c:if></c:forEach>]},
{"name": "mailRegisterNo", "label": "<bean:message key="label.mail.mailRegisterNo" bundle="file" />"},
{"name": "contactName", "label": "<bean:message key="label.contact.name" />", "title": "FirstName LastName"}
],

"actions": {
"findAction": "file/mailRegister.do?dispatch=find",
"viewAction": "file/mailRegister.do?dispatch=view",
"newAction": "file/mailRegister.do?dispatch=edit",
"editAction": "file/mailRegister.do?dispatch=detailView&amp;view=detailView", "editScript": "scripts/module/file/edit.js",
"exportAction": "file/mailRegister.do?dispatch=export"
}
}
</jsp:root>