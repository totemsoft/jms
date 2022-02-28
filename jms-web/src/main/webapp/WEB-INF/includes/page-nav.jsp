<?xml version="1.0" encoding="UTF-8"?>
<jsp:root
    xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:html="http://struts.apache.org/tags-html"
    xmlns:bean="http://struts.apache.org/tags-bean"
    xmlns:logic="http://struts.apache.org/tags-logic"
    xmlns:c="http://java.sun.com/jsp/jstl/core"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    xmlns:decorator="http://www.opensymphony.com/sitemesh/decorator"
    version="2.0">

    <div id="pageNav" class="yui-navset">
        <ul class="yui-nav">
            <li><bean:message key="label.page.nav" /></li>
            <c:forEach items="${links}" var="link" varStatus="status">
                <c:choose>
                    <c:when test="${status.last}">
                        <li class="selected">
                            <html:link module="${link.module}" action="${link.action}">
                                <em>${link.title}</em>
                            </html:link>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li>
                            <html:link module="${link.module}" action="${link.action}">
                                <em>${link.title}</em>
                            </html:link>
                        </li>&gt;
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </ul>
    </div>

</jsp:root>