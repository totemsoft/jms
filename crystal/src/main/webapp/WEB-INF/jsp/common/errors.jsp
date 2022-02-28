<?xml version="1.0" encoding="utf-8"?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:c="http://java.sun.com/jstl/core_rt"
    version="2.0">
<!--jsp:directive.page contentType="application/json;charset=UTF-8" /-->
<jsp:directive.page contentType="text/plain;charset=UTF-8" />
{"errors":[<c:forEach items="${errors}" var="error" varStatus="status">{"name":"${error.name}","message":"${error.message}"}<c:if test="${not status.last}">,</c:if></c:forEach>]}
</jsp:root>