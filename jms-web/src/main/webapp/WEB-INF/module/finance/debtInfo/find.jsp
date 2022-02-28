<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:bean="http://struts.apache.org/tags-bean"
    xmlns:c="http://java.sun.com/jsp/jstl/core"
    xmlns:fn="http://java.sun.com/jsp/jstl/functions"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    version="2.0">
<jsp:directive.page contentType="application/json;charset=UTF-8"/>
{
"tabs": ["tab1"],

"menuItems": [],

"columnDefs": [
{"key": "id", "label": "", "hidden": true},
{"key": "name", "label": "Name", "sortable": true, "resizeable": true}
],

"rows": [<c:forEach items="${entities}" var="entity" varStatus="status">
{"id": ${entity.id},
"name": "${entity.name}"
}<c:if test="${not status.last}">,</c:if>
</c:forEach>],

"filters": [
{"id": "f_date", "name": "date", "label": "<bean:message key="label.date" />"},
{"name": "fcaId", "label": "<bean:message key='label.fca' />", "valuesUrl": "findFilter.do?dispatch=findFcaNo", "forceSelection": true},
{"name": "fileId", "label": "<bean:message key='label.file' />", "valuesUrl": "findFilter.do?dispatch=findFileNo", "forceSelection": true}
],

"actions": {
"findAction": "finance/debtInfo.do?dispatch=find",
"viewAction": "finance/debtInfo.do?dispatch=view",
"newAction": "",
"editAction": ""
}
}
</jsp:root>