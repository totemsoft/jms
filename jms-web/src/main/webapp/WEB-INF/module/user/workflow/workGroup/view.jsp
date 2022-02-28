<?xml version="1.0" encoding="UTF-8"?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:c="http://java.sun.com/jsp/jstl/core"
    xmlns:fn="http://java.sun.com/jsp/jstl/functions"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    version="2.0">
{
"actionWorkflowRows": [<c:forEach items="${entity.actionWorkflows}" var="item" varStatus="s">
{"id": "${item.id}"
,"outcome": "${item.actionOutcome.name}"
,"action": "${item.actionCode.code}"
,"nextAction": "${item.nextActionCode.code}"
,"dueDays": "${item.nextDueDays}"
}<c:if test="${not s.last}">,</c:if></c:forEach>]
,
"systemFunctionRows": [<c:forEach items="${entity.systemFunctions}" var="item" varStatus="s">
{"id": "${item.id}"
,"module": "${item.module}"
,"name": "${item.name}"
,"description": "${item.description}"
}<c:if test="${not s.last}">,</c:if></c:forEach>]
}
</jsp:root>