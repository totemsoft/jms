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
                    <c:forEach items="${fileForm.entity.fileDocs}" var="item" varStatus="status">
                        <c:if test="${status.index eq 0}">
                            <th><bean:message key="label.fileDoc.fileDocId" bundle="${bundle}" /></th>
                            <th><bean:message key="label.fileDoc.createdDate" bundle="${bundle}" /></th>
                            <th><bean:message key="label.fileDoc.docType" bundle="${bundle}" /></th>
                            <th class="width100"><bean:message key="label.fileDoc.description" bundle="${bundle}" /></th>
                            <th></th>
                        </c:if>
                        <tr class="${status.index % 2 eq 0 ? 'odd' : 'even'}">
                            <td>${item.fileDocId}</td>
                            <td><fmt:formatDate value="${item.createdDate}" pattern="${datePattern}" /></td>
                            <td>${item.docType.name}</td>
                            <td>${item.description}</td>
                            <td>
                                <c:if test="${not empty item.document.documentId}">
                                    <a title="Download Document"
                                        href="javascript:YAHOO.jms.sendDownloadRequest('file/downloadFileDoc.do?dispatch=downloadFileDoc&amp;documentId=${item.document.documentId}', null, 'Download Document');">
                                        <span class="download">&#160;</span>
                                    </a>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </td>
        </tr>
    </table>

</jsp:root>