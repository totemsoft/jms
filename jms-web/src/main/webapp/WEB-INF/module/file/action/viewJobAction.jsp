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
    <c:set var="action" value="${fileActionForm.jobAction}" />
    <c:set var="actionType" value="${action.actionCode.actionType}" />

    <html:form action="/viewJobAction" method="get">
        <html:hidden property="dispatch" value="close" />

        <div id="errors" class="error">
            <html:errors />
        </div>

        <table class="nonDataTable">
            <tr>
                <td class="bold nowrap"><bean:message key="label.actionCode" /></td>
                <td><html:text property="jobAction.actionCode.code" readonly="true" styleClass="text" /></td>
                <td class="bold nowrap"><bean:message key="label.actionType" /></td>
                <td><html:text property="jobAction.actionCode.actionType.name" readonly="true" styleClass="text" /></td>
            </tr>
            <jsp:include page="/WEB-INF/module/file/viewFileFca.jsp" />
            <jsp:include page="/WEB-INF/module/job/action/viewJob.jsp" />
            <jsp:include page="/WEB-INF/entity/action/viewResponsibleUser.jsp" />
            <tr>
                <td class="jms-label"><bean:message key="label.jobAction.dueDate" bundle="${bundle}" /></td>
                <td>
                    <c:set var="dueDate"><fmt:formatDate value="${action.dueDate}" pattern="${datePattern}" /></c:set>
                    <html:text property="jobAction.dueDate" value="${dueDate}" readonly="true" styleClass="text" />
                </td>
                <td class="jms-label"><bean:message key="label.jobAction.completedDate" bundle="${bundle}" /></td>
                <td>
                    <c:set var="completedDate"><fmt:formatDate value="${action.completedDate}" pattern="${datePattern}" /></c:set>
                    <html:text property="jobAction.completedDate" value="${completedDate}" title="by ${action.completedBy.contact.fullName}" readonly="true" styleClass="text" />
                </td>
            </tr>
            <c:if test="${not empty action.destination or actionType.email or actionType.call or actionType.sms}">
                <tr>
                    <td class="jms-label"><bean:message key="label.jobAction.destination" bundle="${bundle}" /></td>
                    <td>
                        <html:text property="jobAction.destination" readonly="true" styleClass="text" />
                    </td>
                    <td colspan="2">
                        <c:if test="${not empty action.contact}">
                            <html:text property="entity.contact.name" readonly="true" styleClass="text" />
                        </c:if>
                    </td>
                </tr>
            </c:if>
            <c:if test="${not empty action.subject or actionType.email}">
                <tr>
                    <td class="jms-label"><bean:message key="label.jobAction.subject" bundle="${bundle}" /></td>
                    <td colspan="3">
                        <html:text property="jobAction.subject" readonly="true" styleClass="text" />
                    </td>
                </tr>
            </c:if>
            <tr>
                <td class="jms-label"><bean:message key="label.jobAction.notation" bundle="${bundle}" /></td>
                <td colspan="3">
                    <html:textarea property="jobAction.notation" readonly="true" styleClass="textarea" />
                </td>
            </tr>
        </table>
        <c:if test="${not empty fileActionForm.htmlInput}">
            <p>
                <textarea id="htmlInput" name="htmlInput" style="display:none;visibility:hidden;">${fileActionForm.htmlInput}</textarea>
            </p>
        </c:if>
    </html:form>

</jsp:root>