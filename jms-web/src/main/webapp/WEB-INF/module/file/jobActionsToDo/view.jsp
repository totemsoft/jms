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
                    <c:forEach items="${jobActionsToDo}" var="item" varStatus="status">
                        <c:set var="hideJob" value="${empty jobId or jobId eq item.job.jobId}" />
                        <c:set var="rowId"><c:if test="${not hideJob}">job.${item.job.jobId}</c:if></c:set>
                        <c:set var="rowClassName" value="${status.index % 2 eq 0 ? 'odd' : 'even'}${hideJob ? '' : ' hidden'}" />
                        <c:if test="${status.index eq 0}">
                            <th><bean:message key="label.jobAction.dueDate" bundle="${bundle}" /></th>
                            <th><bean:message key="label.job" bundle="${bundle}" /></th>
                            <th><bean:message key="label.jobAction.actionCode" bundle="${bundle}" /></th>
                            <th class="width100"><bean:message key="label.jobAction.notation" bundle="${bundle}" /></th>
                            <th></th>
                            <th></th>
                        </c:if>
                        <tr id="${rowId}" class="${rowClassName}">
                            <td><fmt:formatDate value="${item.dueDate}" pattern="${datePattern}" /></td>
                            <td>${item.job.id}</td>
                            <td title="${item.actionCode.notation}">${item.actionCode.code}</td>
                            <td>${item.notation}</td>
                            <td>
                                <a title="Delete Job Action"
                                    href="javascript:YAHOO.jms.deleteJobAction(${item.id});">
                                    <span class="delete">${nbsp}</span>
                                </a>
                            </td>
                            <td>
                                <a title="Complete Job Action"
                                    href="javascript:YAHOO.jms.editJobAction(${item.id});">
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