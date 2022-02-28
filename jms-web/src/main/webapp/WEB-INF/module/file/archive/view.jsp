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

    <table class="nonDataTable">
        <tr>
            <td class="jms-label"><bean:message key="label.archive.name" bundle="${bundle}" /></td>
            <td><input type="text" readonly="readonly" value="${entity.name}" class="text" /></td>
            <td class="jms-label"><bean:message key="label.archive.code" bundle="${bundle}" /></td>
            <td><input type="text" readonly="readonly" value="${entity.code}" class="text" /></td>
        </tr>
        <tr>
            <fmt:formatDate var="dateFrom" value="${entity.dateFrom}" pattern="${datePattern}" />
            <td class="jms-label"><bean:message key="label.archive.dateFrom" bundle="${bundle}" /></td>
            <td><input type="text" readonly="readonly" value="${dateFrom}" class="text" /></td>
            <fmt:formatDate var="dateTo" value="${entity.dateTo}" pattern="${datePattern}" />
            <td class="jms-label"><bean:message key="label.archive.dateTo" bundle="${bundle}" /></td>
            <td><input type="text" readonly="readonly" value="${dateTo}" class="text" /></td>
        </tr>
        <tr>
            <td class="jms-label"><bean:message key="label.archive.location" bundle="${bundle}" /></td>
            <td colspan="3"><textarea readonly="readonly" class="textarea">${entity.location}</textarea></td>
        </tr>
        <tr>
            <td class="jms-label"><bean:message key="label.archive.description" bundle="${bundle}" /></td>
            <td colspan="3"><input readonly="readonly" value="${entity.description}" class="text" /></td>
        </tr>
    </table>
    <jsp:include page="/WEB-INF/module/file/archive/files.jsp" />
</jsp:root>