<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:bean="http://struts.apache.org/tags-bean"
    xmlns:c="http://java.sun.com/jsp/jstl/core"
    xmlns:fn="http://java.sun.com/jsp/jstl/functions"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    version="2.0">
<jsp:directive.page contentType="application/json;charset=UTF-8"/>

<jsp:useBean id="fileForm" scope="session" class="au.gov.qld.fire.jms.web.module.file.FileForm" />

{
"tabs": ["tab1"],

"menuItems": [],

"columnDefs": [
{"key": "id", "label": "", "hidden": true},
{"key": "dateFrom", "label": "Date From", "sortable": true, "resizeable": true},
{"key": "name", "label": "Customer Name", "sortable": true, "resizeable": true}
],

"rows": [<c:forEach items="${entities}" var="entity" varStatus="status">
{"id": ${entity.id},
"dateFrom": "<fmt:formatDate value='${entity.dateFrom}' pattern='${datePattern}' />",
"name": "${entity.name}"
}<c:if test="${not status.last}">,</c:if>
</c:forEach>],

"filters": [
{"id": "f_date", "name": "date", "label": "<bean:message key="label.archive.date" bundle="file" />"},
{"name": ""},
{"name": ""},
{"name": "fcaId", "label": "<bean:message key='label.fca' />", "valuesUrl": "findFilter.do?dispatch=findFcaNo", "forceSelection": true},
{"name": "fileId", "label": "<bean:message key='label.file' />", "valuesUrl": "findFilter.do?dispatch=findFileNo", "forceSelection": true},
{"name": "sapCustNo", "label": "<bean:message key='label.sapHeader.sapCustNo' bundle="file" />", "valuesUrl": "findFilter.do?dispatch=findSapCustNo", "forceSelection": true}
],

"actions": {
"findAction": "file/fileArchive.do?dispatch=find",
"viewAction": "file/fileArchive.do?dispatch=view",
"newAction": "file/fileArchive.do?dispatch=edit",
"editAction": "file/fileArchive.do?dispatch=edit"
}
}
</jsp:root>