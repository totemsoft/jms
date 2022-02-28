<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:c="http://java.sun.com/jsp/jstl/core"
    xmlns:fn="http://java.sun.com/jsp/jstl/functions"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    version="2.0">
<jsp:directive.page contentType="application/json;charset=UTF-8"/>
<c:set var="dquot">"</c:set><c:set var="squot">'</c:set>
<c:set var="viewLink"><span class="view">${nbsp}</span></c:set>
<c:set var="viewLink">${fn:replace(viewLink, dquot, squot)}</c:set>
{
"rows": [<c:forEach items="${entities}" var="entity" varStatus="status">
{"id": "${entity.id}",
"actionId": "${entity.fileAction.id}",
"fileId": "${entity.file.fileId}",
"fcaId": "javascript:YAHOO.jms.editFile(${entity.file.fileId});${entity.file.fca.fcaId}",
"buildingName": "<c:out value='${entity.file.building.name}' />",
"actionCode": "${entity.fileAction.actionCode.code}",
"dueDate": "<fmt:formatDate value='${entity.fileAction.dueDate}' pattern='${datePattern}'/>",
"status": "${entity.status.name}",
"statusDate": "<fmt:formatDate value='${entity.statusDate}' pattern='${datePattern}'/>",
"view": "javascript:YAHOO.jms.viewFileAction(${entity.fileAction.id});${viewLink}",
"selected": "${entity.fileAction.completed ? '' : 'true'}"
}<c:if test="${not status.last}">,</c:if>
</c:forEach>]
}
</jsp:root>