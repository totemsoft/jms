<?xml version="1.0" encoding="UTF-8"?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:html="http://struts.apache.org/tags-html"
    xmlns:bean="http://struts.apache.org/tags-bean"
    xmlns:c="http://java.sun.com/jsp/jstl/core"
    xmlns:fn="http://java.sun.com/jsp/jstl/functions"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    version="2.0">

    <c:set var="backslash">\</c:set>
    <c:set var="backslashReplace">\\</c:set>
    <c:set var="isLeaf" value="${not fcaDoc.dir}" />

    <c:if test="${isLeaf}">
    <li class="expanded" yuiConfig='{"type":"html","isLeaf":true}'>
        <table class="width100">
            <tr class="valign-top">
                <td class="width90 ${fcaDoc.dir ? 'bold' : ''}" id="fcaDoc.${fcaDocId}">${fcaDoc.name}</td>
                <td class="width10em"><fmt:formatDate value="${fcaDoc.createdDate}" pattern="${datePattern}" />${nbsp}</td>
                <td class="width2em">
                    <a title="Download FCA Document"
                        href="javascript:YAHOO.jms.file.fcaDoc.downloadFileDoc('${fn:replace(fcaDoc.pathname, backslash, backslashReplace)}');">
                        <span class="download">${nbsp}</span>
                    </a>
                </td>
            </tr>
        </table>
        <c:if test="${fn:length(fcaDoc.children) gt 0}">
        <ul>
            <c:forEach items="${fcaDoc.children}" var="child">
                <c:set var="fcaDoc" value="${child}" scope="request" />
                <c:set var="fcaDocId" value="${fcaDocId + 1}" scope="request" />
                <jsp:include page="fcaDoc.jsp" />
            </c:forEach>
        </ul>
        </c:if>
    </li>
    </c:if>

    <c:if test="${not isLeaf}">
    <li class="expanded" yuiConfig='{"type":"html","isLeaf":false}'>
        <table class="width100">
            <tr class="valign-top">
                <td class="width90 ${fcaDoc.dir ? 'bold' : ''}" id="fcaDoc.${fcaDocId}">${fcaDoc.name}</td>
                <td class="width10em"><fmt:formatDate value="${fcaDoc.createdDate}" pattern="${datePattern}" />${nbsp}</td>
                <td class="width2em" style="visibility: hidden;">
                    <a href="javascript:;">
                        <span class="download">${nbsp}</span>
                    </a>
                </td>
            </tr>
        </table>
        <c:if test="${fn:length(fcaDoc.children) gt 0}">
        <ul>
            <c:forEach items="${fcaDoc.children}" var="child">
                <c:set var="fcaDoc" value="${child}" scope="request" />
                <c:set var="fcaDocId" value="${fcaDocId + 1}" scope="request" />
                <jsp:include page="fcaDoc.jsp" />
            </c:forEach>
        </ul>
        </c:if>
    </li>
    </c:if>

</jsp:root>