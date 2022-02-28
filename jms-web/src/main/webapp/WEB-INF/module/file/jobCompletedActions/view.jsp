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
    <c:set var="jobId" value="${jobForm.entity.jobId}" />

    <table class="nonDataTable">
        <tr>
            <td>
                <table class="dataTable">
                    <c:forEach items="${jobActionsCompleted}" var="item" varStatus="status">
                        <c:set var="hideJob" value="${empty jobId or jobId eq item.job.jobId}" />
                        <c:set var="rowId"><c:if test="${not hideJob}">job.${item.job.jobId}</c:if></c:set>
                        <c:set var="rowClassName" value="${status.index % 2 eq 0 ? 'odd' : 'even'}${hideJob ? '' : ' hidden'}" />
                        <c:if test="${status.index eq 0}">
                            <th><bean:message key="label.jobAction.completed" bundle="${bundle}" /></th>
                            <th><bean:message key="label.job" bundle="${bundle}" /></th>
                            <th><bean:message key="label.jobAction.actionCode" bundle="${bundle}" /></th>
                            <th class="width100"><bean:message key="label.jobAction.notation" bundle="${bundle}" /></th>
                            <th></th>
                            <th></th>
                        </c:if>
                        <fmt:formatDate var="completedDate" value="${item.completedDate}" pattern="${datePattern}" />
                        <fmt:formatDate var="completedTime" value="${item.completedDate}" pattern="${timePattern}" />
                        <tr id="${rowId}" class="${rowClassName}">
                            <td title="${completedTime}">${completedDate}</td>
                            <td rowspan="2">${item.job.id}</td>
                            <td rowspan="2" title="${item.actionCode.notation}">${item.actionCode.code}</td>
                            <td rowspan="2">${item.notation}</td>
                            <td rowspan="2">
                                <c:choose>
                                    <c:when test="${not empty item.attachment.id}">
                                        <a title="Download Job Document"
                                            href="javascript:YAHOO.jms.downloadJobActionAttachment(${item.id});">
                                            <span class="download">${nbsp}</span>
                                        </a>
                                    </c:when>
                                    <c:when test="${not empty item.document.id}">
                                        <a title="Download Job Document"
                                            href="javascript:YAHOO.jms.downloadJobAction(${item.id});">
                                            <span class="download">${nbsp}</span>
                                        </a>
                                    </c:when>
                                </c:choose>
                            </td>
                            <td rowspan="2">
                                <a title="View Job Action"
                                    href="javascript:YAHOO.jms.viewJobAction(${item.id});">
                                    <span class="edit">${nbsp}</span>
                                </a>
                            </td>
                        </tr>
                        <tr id="${rowId}" class="${rowClassName}">
                            <td title="${item.completedBy.contact.name}">${item.completedBy.login}</td>
                        </tr>
                    </c:forEach>
                </table>
            </td>
        </tr>
    </table>

</jsp:root>