<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:c="http://java.sun.com/jstl/core_rt"
    version="2.0">
<jsp:directive.page contentType="application/json;charset=UTF-8"/>
{"records":[<c:forEach items="${entities}" var="entity" varStatus="s">{"key":"${entity.key}","value":"${entity.value}"}<c:if test="${not s.last}">,</c:if></c:forEach>]}
</jsp:root>