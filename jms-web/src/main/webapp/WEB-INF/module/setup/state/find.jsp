<?xml version="1.0" encoding="UTF-8"?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:bean="http://struts.apache.org/tags-bean"
    xmlns:c="http://java.sun.com/jsp/jstl/core"
    xmlns:fn="http://java.sun.com/jsp/jstl/functions"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    version="2.0">
{
"columnDefs": [
{"key": "id", "label": "", "formatter": "YAHOO.widget.DataTable.formatCheckbox", "hidden": true},
{"key": "state", "label": "State", "sortable":true, "resizeable":true},
{"key": "defaultState", "label": "Default", "sortable":false, "formatter": "checkbox", "disabled": true}
],

"rows": [<c:forEach items="${entities}" var="entity" varStatus="status">
{"id": "${entity.id}",
"state": "${entity.state}",
"defaultState": "${entity.defaultState}"}<c:if test="${not status.last}">,</c:if>
</c:forEach>],

"filters": [],

"actions": {
"findAction": "setup/findState.do?dispatch=find",
"viewAction": "setup/viewState.do?dispatch=view",
"newAction":  "setup/editState.do?dispatch=edit",
"editAction": "setup/editState.do?dispatch=edit"}
}
</jsp:root>