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
            <td>
                <table class="dataTable">
                    <c:forEach items="${fileForm.entity.isolations}" var="item" varStatus="status">
                        <c:if test="${status.first}">
                            <th><bean:message key="label.isolation.csNumber" bundle="${bundle}" /></th>
                            <th><bean:message key="label.isolation.input" bundle="${bundle}" /></th>
                            <th><bean:message key="label.isolation.isolationDate" bundle="${bundle}" /></th>
                            <th><bean:message key="label.isolation.deIsolationDate" bundle="${bundle}" /></th>
                            <th><bean:message key="label.isolation.isolationTime" bundle="${bundle}" /></th>
                        </c:if>
                        <tr class="${status.index % 2 eq 0 ? 'odd' : 'even'}">
                            <td>${item.csNumber}</td>
                            <td>${item.input}</td>
                            <td><fmt:formatDate value="${item.isolationDate}" pattern="${dateTimePattern}" /></td>
                            <td><fmt:formatDate value="${item.deIsolationDate}" pattern="${dateTimePattern}" /></td>
                            <td>${item.isolationTimeText}</td>
                        </tr>
                        <tr class="${status.index % 2 eq 0 ? 'odd' : 'even'}" title="Details">
                            <td colspan="5">${item.details}</td>
                        </tr>
                        <c:if test="${not empty item.keyDetails}">
                        <tr class="${status.index % 2 eq 0 ? 'odd' : 'even'}" title="Key Details">
                            <td colspan="5">${item.keyDetails}</td>
                        </tr>
                        </c:if>
                    </c:forEach>
                </table>
            </td>
        </tr>
    </table>

</jsp:root>