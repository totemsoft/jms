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

    <table class="dataTable">
        <caption><bean:message key="label.archive.files" bundle="${bundle}" /></caption>
        <tr>
            <th><bean:message key="label.file" /></th>
            <th><bean:message key="label.fca" /></th>
            <th><bean:message key="label.sapHeader.sapCustNo" bundle="${bundle}" /></th>
        </tr>
        <c:set var="count" value="1" />
        <c:forEach items="${archiveForm.entity.files}" var="item">
            <c:set var="file" value="${item.file}" />
            <c:if test="${file.fileStatus.archived}">
                <tr class="${count mod 2 eq 0 ? 'even' : 'odd'}">
                    <td><a href="javascript:YAHOO.jms.editFile(${file.id});">${file.id}</a></td>
                    <td>${file.fca.id}</td>
                    <td>${file.sapHeader.sapCustNo}</td>
                </tr>
                <c:set var="count" value="${count + 1}" />
            </c:if>
        </c:forEach>
    </table>

</jsp:root>