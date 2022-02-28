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
                    <c:forEach items="${fileActionsToDo}" var="item" varStatus="status">
                        <c:if test="${status.index eq 0}">
                            <th><bean:message key="label.fileAction.dueDate" bundle="${bundle}" /></th>
                            <th><bean:message key="label.fileAction.actionCode" bundle="${bundle}" /></th>
                            <th class="width100"><bean:message key="label.fileAction.notation" bundle="${bundle}" /></th>
                            <!--th></th-->
                            <th></th>
                            <th></th>
                        </c:if>
                        <tr class="${status.index % 2 eq 0 ? 'odd' : 'even'}">
                            <td><fmt:formatDate value="${item.dueDate}" pattern="${datePattern}" /></td>
                            <td title="${item.actionCode.notation}">${item.actionCode.code}</td>
                            <td>${item.notation}</td>
                            <!--td>
                                <c:if test="${not empty item.document.documentId}">
                                    <a title="Download File Document"
                                        href="javascript:YAHOO.jms.sendDownloadRequest('file/downloadFileAction.do?dispatch=downloadFileAction&amp;actionId=${item.id}', null, 'Download File Document');">
                                        <span class="download">${nbsp}</span>
                                    </a>
                                </c:if>
                            </td-->
                            <td>
                                <a title="Delete File Action"
                                    href="javascript:YAHOO.jms.deleteFileAction(${item.id});">
                                    <span class="delete">${nbsp}</span>
                                </a>
                            </td>
                            <td>
                                <a title="Complete File Action"
                                    href="javascript:YAHOO.jms.editFileAction(${item.id});">
                                    <span class="edit">${nbsp}</span>
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </td>
        </tr>
    </table>

</jsp:root>