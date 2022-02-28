<?xml version="1.0" encoding="UTF-8"?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:c="http://java.sun.com/jsp/jstl/core"
    xmlns:fn="http://java.sun.com/jsp/jstl/functions"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    version="2.0">
{
"actionWorkflowRows": [<c:forEach items="${entity.actionWorkflows}" var="item" varStatus="s">
{"id": "${item.id}"
,"actionCodeId": "${item.actionCode.id}"
,"outcomeId": "${item.actionOutcome.id}"
,"action": "${item.actionCode.code}"
,"outcome": "${item.actionOutcome.name}"
,"nextAction": "${item.nextActionCode.code}"
,"dueDays": ${item.nextDueDays}
,"active": "${item.id gt 0 ? item.active : ''}"
}<c:if test="${not s.last}">,</c:if></c:forEach>]
}
</jsp:root>