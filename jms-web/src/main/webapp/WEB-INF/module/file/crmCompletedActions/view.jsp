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
                    <c:forEach items="${fileActionsCompleted}" var="item" varStatus="status">
                        <c:if test="${status.index eq 0}">
                            <th><bean:message key="label.fileAction.completed" bundle="${bundle}" /></th>
                            <th><bean:message key="label.fileAction.actionCode" bundle="${bundle}" /></th>
                            <th class="width100"><bean:message key="label.fileAction.notation" bundle="${bundle}" /></th>
                            <th></th>
                            <th></th>
                        </c:if>
                        <fmt:formatDate var="completedDate" value="${item.completedDate}" pattern="${datePattern}" />
                        <fmt:formatDate var="completedTime" value="${item.completedDate}" pattern="${timePattern}" />
                        <tr class="${status.index % 2 eq 0 ? 'odd' : 'even'}">
                            <td title="${completedTime}">${completedDate}</td>
                            <td rowspan="2" title="${item.actionCode.notation}">${item.actionCode.code}</td>
                            <td rowspan="2">${item.notation}</td>
                            <td rowspan="2">
                                <c:choose>
                                    <c:when test="${not empty item.attachment.id}">
                                        <a title="Download File Document"
                                            href="javascript:YAHOO.jms.downloadFileActionAttachment(${item.id});">
                                            <span class="download">${nbsp}</span>
                                        </a>
                                    </c:when>
                                    <c:when test="${not empty item.document.id}">
                                        <a title="Download File Document"
                                            href="javascript:YAHOO.jms.downloadFileAction(${item.id});">
                                            <span class="download">${nbsp}</span>
                                        </a>
                                    </c:when>
                                </c:choose>
                            </td>
                            <td rowspan="2">
                                <a title="View File Action"
                                    href="javascript:YAHOO.jms.viewFileAction(${item.id});">
                                    <span class="view">${nbsp}</span>
                                </a>
                            </td>
                        </tr>
                        <tr class="${status.index % 2 eq 0 ? 'odd' : 'even'}">
                            <td title="${item.completedBy.contact.name}">${item.completedBy.login}</td>
                        </tr>
                    </c:forEach>
                </table>
            </td>
        </tr>
    </table>

</jsp:root>