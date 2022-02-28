<?xml version="1.0" encoding="UTF-8"?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:bean="http://struts.apache.org/tags-bean"
    xmlns:c="http://java.sun.com/jsp/jstl/core"
    xmlns:fn="http://java.sun.com/jsp/jstl/functions"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    version="2.0">
{
"columnDefs": [
{"key": "id", "label": "", "hidden": true},
{"key": "name", "label": "Name", "sortable":true, "resizeable":false},
{"key": "run", "label": "Run", "formatter": "link"}
],

"rows": [<c:forEach items="${entities}" var="entity" varStatus="status">
{"id": "${entity.key}",
"name": "${entity.value}",
"run": "javascript:YAHOO.jms.setup.runProcess('${entity.key}');run"
}<c:if test="${not status.last}">,</c:if>
</c:forEach>],

"filters": [],

"actions": {}
}
</jsp:root>