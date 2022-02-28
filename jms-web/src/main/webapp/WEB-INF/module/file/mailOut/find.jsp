<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:bean="http://struts.apache.org/tags-bean"
    xmlns:c="http://java.sun.com/jsp/jstl/core"
    xmlns:fn="http://java.sun.com/jsp/jstl/functions"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    version="2.0">
<jsp:directive.page contentType="application/json;charset=UTF-8"/>
<c:set var="bundle" value="file" />
<c:set var="dquot">"</c:set><c:set var="squot">'</c:set>
<c:set var="selectAll"><input id="selectAll" type="checkbox" title="Select All" onchange="YAHOO.jms.mailOut.bulkSelectAll(this.checked);"></input></c:set>
<c:set var="selectAll">${fn:replace(selectAll, dquot, squot)}</c:set>
{
"tabs": ["tab1", "tab2",
{"id":"tab3","config":{"label":"Batch Log","cacheData":false,
   "dataSrc":"file/mailOut.do?dispatch=findBatch","batchId":"${batchId}"}}
],

"menuItems": [],

"columnDefs": [
{"key": "id", "label": "Action ID", "className": "width0", "hidden": true},
{"key": "fileId", "label": "", "className": "width0", "hidden": true},
{"key": "fcaId", "label": "FCA No", "width": 50, "sortable": true, "formatter": "link"},
{"key": "buildingName", "label": "Building Name", "sortable": true},
{"key": "nextActionDate", "label": "Completed", "width": 70, "sortable": true},
{"key": "lastSent", "label": "Last Sent", "width": 70, "sortable": true},
{"key": "lastReceived", "label": "Last Received", "width": 70, "sortable": true},
{"key": "nextAction", "label": "Last Action", "sortable": true},
{"key": "mailMethod", "label": "<bean:message key='label.om89.sendBy' bundle='${bundle}' />", "width": 40, "sortable": true},
{"key": "selected", "label": "${selectAll}", "width": 10, "className": "center", "title": "Exclude File Action", "sortable": false, "formatter": "checkbox", "disabled": false}
],

"rows": [<c:forEach items="${entities}" var="entity" varStatus="status">
{"id": "${entity.id}",
"fileId": "${entity.fileId}",
"fcaId": "javascript:YAHOO.jms.editFile(${entity.fileId});${entity.fcaId}",
"buildingName": "<c:out value='${entity.buildingName}' />",
"nextActionDate": "<fmt:formatDate value='${entity.nextActionDate}' pattern='${datePattern}'/>",
"lastSent": "<fmt:formatDate value='${entity.lastSent}' pattern='${datePattern}'/>",
"lastReceived": "<fmt:formatDate value='${entity.lastReceived}' pattern='${datePattern}'/>",
"nextAction": "${entity.nextAction}",
"mailMethod": "${entity.mailMethod.name}",
"selected": "${entity.noMailOut ? '' : 'false'}"
}<c:if test="${not status.last}">,</c:if>
</c:forEach>],

"pagination": {
"rowsPerPageOptions": [10, 50, 100, 200, 500, 1000],
"rowsPerPage": 10
},

<c:if test="${not empty criteria}">
"filters": [
{"name": "actionCode", "label": "<bean:message key='label.actionCode' />", "values": [<c:forEach items="${actionCodes}" var="item" varStatus="status">{"label":"${item.code}","value":"${item.id}"}<c:if test="${not status.last}">,</c:if></c:forEach>], "hiddenValue": "${criteria.actionCodeId}", "value": "${criteria.actionCode}", "forceSelection": false, "maxResultsDisplayed": ${fn:length(actionCodes)}},
{"name": "rejected", "label": "<bean:message key='label.mailStatus.rejected' bundle='${bundle}' />", "value": "${criteria.rejected}", "values": ["true", "false"]},
{"name": "doNotMail", "label": "<bean:message key='label.mailStatus.doNotMail' bundle='${bundle}' />", "value": "${criteria.doNotMail}", "values": ["true", "false"], "title": "<bean:message key='label.mailStatus.doNotMail.title' bundle='${bundle}' />"},

{"name": "region", "label": "<bean:message key='label.region.name' />", "values": [<c:forEach items="${regions}" var="item" varStatus="status">{"label":"${item.name}","value":"${item.id}"}<c:if test="${not status.last}">,</c:if></c:forEach>], "hiddenValue": "${criteria.regionId}", "value": "${criteria.region}", "forceSelection": true, "maxResultsDisplayed": ${fn:length(regions)}},
{"name": "fcaId", "label": "<bean:message key='label.fca' />", "valuesUrl": "findFilter.do?dispatch=findFcaNo", "value": "${criteria.fcaId}"},
{"name": "fileId", "label": "<bean:message key='label.file' />", "valuesUrl": "findFilter.do?dispatch=findFileNo", "value": "${criteria.fileId}"},

{"name": "sentDateOption", "label": "<bean:message key='label.mail.sentDateOption' bundle='${bundle}' />", "values": [<c:forEach items="${dateOptions}" var="item" varStatus="status">{"label":"${item.name}","value":"${item.id}"}<c:if test="${not status.last}">,</c:if></c:forEach>], "hiddenValue": "${criteria.sentDateOptionId}", "value": "${criteria.sentDateOption}", "forceSelection": true, "maxResultsDisplayed": ${fn:length(dateOptions)}},
{"name": "sentDateFrom", "id": "f_date_sentDateFrom", "value": "<fmt:formatDate value='${criteria.sentDateFrom}' pattern='${datePattern}'/>", "label": "<bean:message key='label.mail.sentDateFrom' bundle='${bundle}' />"},
{"name": "sentDateTo", "id": "f_date_sentDateTo", "value": "<fmt:formatDate value='${criteria.sentDateTo}' pattern='${datePattern}'/>", "label": "<bean:message key='label.mail.sentDateTo' bundle='${bundle}' />"},

{"name": "receivedDateOption", "label": "<bean:message key='label.mail.receivedDateOption' bundle='${bundle}' />", "values": [<c:forEach items="${dateOptions}" var="item" varStatus="status">{"label":"${item.name}","value":"${item.id}"}<c:if test="${not status.last}">,</c:if></c:forEach>], "hiddenValue": "${criteria.receivedDateOptionId}", "value": "${criteria.receivedDateOption}", "forceSelection": true, "maxResultsDisplayed": ${fn:length(dateOptions)}},
{"name": "receivedDateFrom", "id": "f_date_receivedDateFrom", "value": "<fmt:formatDate value='${criteria.receivedDateFrom}' pattern='${datePattern}'/>", "label": "<bean:message key='label.mail.receivedDateFrom' bundle='${bundle}' />"},
{"name": "receivedDateTo", "id": "f_date_receivedDateTo", "value": "<fmt:formatDate value='${criteria.receivedDateTo}' pattern='${datePattern}'/>", "label": "<bean:message key='label.mail.receivedDateTo' bundle='${bundle}' />"},

{"name": "ownerType", "label": "<bean:message key='label.ownerType' bundle='${bundle}' />", "values": [<c:forEach items="${ownerTypes}" var="item" varStatus="status">{"label":"${item.name}","value":"${item.code}"}<c:if test="${not status.last}">,</c:if></c:forEach>], "hiddenValue": "${criteria.ownerType.code}", "value": "${criteria.ownerType.name}", "forceSelection": true, "maxResultsDisplayed": ${fn:length(ownerTypes)}},
{"name": "ownerLegalName", "label": "<bean:message key='label.owner.legalName' bundle='${bundle}' />", "valuesUrl": "oauth/file/findOwnerLegalName"},
{"name": "ownerContactName", "label": "<bean:message key='label.mailOut.contact.name' bundle='${bundle}' />", "title": "<bean:message key='label.mailOut.contact.name.title' bundle='${bundle}' />", "valuesUrl": "oauth/file/findOwnerContactName"}
],
</c:if>

"actions": {
"findAction": "file/mailOut.do?dispatch=find",
"viewFunction": "YAHOO.jms.mailOut.bulkSelection", "viewTitle": "FCA Selection",
"exportAction": "file/mailOut.do?dispatch=export",
"customFunction": "mailOut.createBatch", "customTitle": "Create Batch"
}
}
</jsp:root>