<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:c="http://java.sun.com/jsp/jstl/core"
    xmlns:fn="http://java.sun.com/jsp/jstl/functions"
    version="2.0">
<jsp:directive.page contentType="application/json;charset=UTF-8"/>
<c:set var="dquot">"</c:set>
<c:set var="squot">'</c:set>
{"records":[<c:forEach items="${entityCodes}" var="entityCode" varStatus="status">{"label":"${fn:replace(entityCode, dquot, squot)}","value":"${fn:replace(entityCode, dquot, squot)}"}<c:if test="${not status.last}">,</c:if></c:forEach>]}
</jsp:root>