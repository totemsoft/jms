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

    <c:set var="bundle" value="file" />

    <c:set var="parent" value="${fileForm.parent}" />
    <c:if test="${not empty parent.value}">
    <table class="dataTable">
        <caption><bean:message key="label.file.parent" bundle="${bundle}" /></caption>
        <tr>
            <td class="width50">${parent.value}</td>
            <td class="width40"><c:if test="${parent.key gt 0}">${parent.key}</c:if></td>
            <td>
                <c:if test="${parent.key gt 0}">
                    <a href="javascript:YAHOO.jms.editFile(${parent.key});" class="action">Go</a>
                </c:if>
            </td>
        </tr>
    </table>
    </c:if>

    <c:set var="children" value="${fileForm.children}" />
    <c:if test="${fn:length(children) gt 0}">
    <table class="dataTable">
        <caption><bean:message key="label.file.children" bundle="${bundle}" /></caption>
        <tr>
            <th><bean:message key="label.fca" /></th>
            <th><bean:message key="label.file" /></th>
            <th>&#160;</th>
        </tr>
        <c:forEach items="${children}" var="item" varStatus="status">
            <tr class="${index % 2 eq 0 ? 'odd' : 'even'}">
                <td class="width50">${item.value}</td>
                <td class="width40"><c:if test="${item.key gt 0}">${item.key}</c:if></td>
                <td>
                    <c:if test="${item.key gt 0}">
                        <a href="javascript:YAHOO.jms.editFile(${item.key});" class="action">Go</a>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
    </table>
    </c:if>

</jsp:root>