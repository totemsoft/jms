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

    <c:set var="bundle" value="job" />

    <table class="nonDataTable">
        <tr>
            <td>
                <table class="dataTable">
                    <c:forEach items="${jobForm.entity.jobDocs}" var="item" varStatus="status">
                        <c:if test="${status.index eq 0}">
                            <th><bean:message key="label.jobDoc.jobDocId" bundle="${bundle}" /></th>
                            <th><bean:message key="label.jobDoc.createdDate" bundle="${bundle}" /></th>
                            <th><bean:message key="label.jobDoc.docType" bundle="${bundle}" /></th>
                            <th class="width100"><bean:message key="label.jobDoc.description" bundle="${bundle}" /></th>
                        </c:if>
                        <tr class="${status.index % 2 eq 0 ? 'odd' : 'even'}">
                            <td>${item.jobDocId}</td>
                            <td><fmt:formatDate value="${item.createdDate}" pattern="${datePattern}" /></td>
                            <td>${item.docType.name}</td>
                            <td>${item.description}</td>
                        </tr>
                    </c:forEach>
                </table>
            </td>
        </tr>
    </table>

</jsp:root>