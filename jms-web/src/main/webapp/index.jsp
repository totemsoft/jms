<?xml version="1.0" encoding="utf-8"?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:c="http://java.sun.com/jsp/jstl/core"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    version="2.0">
    <head>
        <!-- to change the content type or response encoding change the following line -->
        <jsp:directive.page contentType="text/html;charset=utf-8"/>
        <title><fmt:message key="access.redirect.title"/></title>
    </head>
    <body>
        <c:redirect url="/j_security_check.do">
            <c:param name="propogationBehavior" value="4" />
        </c:redirect>
    </body>
</jsp:root>