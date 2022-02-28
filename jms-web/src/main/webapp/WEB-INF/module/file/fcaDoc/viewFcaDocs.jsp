<?xml version="1.0" encoding="UTF-8"?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:html="http://struts.apache.org/tags-html"
    xmlns:bean="http://struts.apache.org/tags-bean"
    xmlns:logic="http://struts.apache.org/tags-logic"
    xmlns:c="http://java.sun.com/jsp/jstl/core"
    xmlns:fn="http://java.sun.com/jsp/jstl/functions"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    version="2.0">

    <html:xhtml/>

    <ul>
        <c:forEach items="${fcaDocs}" var="item">
            <c:set var="fcaDoc" value="${item}" scope="request" />
            <c:set var="fcaDocId" value="0" scope="request" />
            <jsp:include page="fcaDoc.jsp" />
        </c:forEach>
    </ul>

</jsp:root>