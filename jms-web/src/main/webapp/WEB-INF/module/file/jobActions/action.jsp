<?xml version="1.0" encoding="UTF-8"?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:html="http://struts.apache.org/tags-html"
    xmlns:bean="http://struts.apache.org/tags-bean"
    xmlns:c="http://java.sun.com/jsp/jstl/core"
    xmlns:fn="http://java.sun.com/jsp/jstl/functions"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    version="2.0">

    <li class="expanded" yuiConfig='{"type":"html"}'>
        <table class="width100">
            <tr class="valign-top">
                <td rowspan="2" class="width10em" title="${action.completed ? 'Completed Date' : 'Due Date'}"><fmt:formatDate value="${action.completed ? action.completedDate : action.dueDate}" pattern="${datePattern}" />${nbsp}</td>
                <td class="width100 bold" title="${action.actionCode.notation}">${action.actionCode.code}${nbsp}</td>
                <td rowspan="2" class="width2em">
                    <c:choose>
                        <c:when test="${action.completed}">
                            <c:choose>
                                <c:when test="${not empty action.attachment.id}">
                                    <a title="Download Job Document"
                                        href="javascript:YAHOO.jms.downloadJobActionAttachment(${action.id});">
                                        <span class="download">${nbsp}</span>
                                    </a>
                                </c:when>
                                <c:when test="${not empty action.document.id}">
                                    <a title="Download Job Document"
                                        href="javascript:YAHOO.jms.downloadJobAction(${action.id});">
                                        <span class="download">${nbsp}</span>
                                    </a>
                                </c:when>
                            </c:choose>
                        </c:when>
                        <c:otherwise>
		                    <a title="Delete Job Action"
		                        href="javascript:YAHOO.jms.deleteJobAction(${action.id});">
		                        <span class="delete">${nbsp}</span>
		                    </a>
                        </c:otherwise>
                    </c:choose>
                </td>
                <td rowspan="2" class="width2em">
                    <c:choose>
                        <c:when test="${action.completed}">
                            <a title="View Job Action"
                                href="javascript:YAHOO.jms.viewJobAction(${action.id});">
                                <span class="edit">${nbsp}</span>
                            </a>
                        </c:when>
                        <c:otherwise>
		                    <a title="Complete Job Action"
		                        href="javascript:YAHOO.jms.editJobAction(${action.id});">
		                        <span class="edit">${nbsp}</span>
		                    </a>
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
            <tr class="valign-top">
                <td class="width100 italic" title="Notation">${action.notation}${nbsp}</td>
            </tr>
        </table>
        <c:if test="${fn:length(action.childrenFileActions) gt 0}">
        <ul>
            <c:forEach items="${action.childrenFileActions}" var="child">
                <c:set var="action" value="${child}" scope="request" />
                <jsp:include page="/WEB-INF/module/file/crmActions/action.jsp" />
            </c:forEach>
        </ul>
        </c:if>
        <c:if test="${fn:length(action.childrenJobActions) gt 0}">
        <ul>
            <c:forEach items="${action.childrenJobActions}" var="child">
                <c:set var="action" value="${child}" scope="request" />
                <jsp:include page="/WEB-INF/module/file/jobActions/action.jsp" />
            </c:forEach>
        </ul>
        </c:if>
    </li>

</jsp:root>