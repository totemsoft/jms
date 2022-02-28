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

    <html:form action="/editJobAction" enctype="multipart/form-data">
        <html:hidden property="dispatch" value="saveJobAction" />

        <div id="errors" class="error">
            <html:errors />
        </div>

        <table class="nonDataTable">
            <html:errors property="jobAction.actionCode.actionCodeId" />
            <tr>
                <td class="bold nowrap"><bean:message key="label.actionCode" /></td>
                <td><html:text property="jobAction.actionCode.code" readonly="true" styleClass="text" /></td>
                <td class="bold nowrap"><bean:message key="label.actionType" /></td>
                <td><html:text property="jobAction.actionCode.actionType.name" readonly="true" styleClass="text" /></td>
            </tr>
            <jsp:include page="/WEB-INF/module/file/viewFileFca.jsp" />
            <jsp:include page="/WEB-INF/module/job/action/viewJob.jsp" />
            <jsp:include page="/WEB-INF/entity/action/viewResponsibleUser.jsp" />
            <html:errors property="entity.dueDate" />
            <tr>
                <td class="jms-label"><bean:message key="label.jobAction.dueDate" bundle="${bundle}" /></td>
                <td>
                    <fmt:formatDate var="dueDate" value="${action.dueDate}" pattern="${datePattern}" />
                    <html:text property="jobAction.dueDate" value="${dueDate}" styleClass="text" readonly="true" />
                </td>
                <td colspan="2" />
            </tr>
            <c:if test="${not empty action.destination or actionType.email or actionType.call or actionType.sms}">
                <html:errors property="entity.destination" />
                <tr>
                    <td class="jms-label"><bean:message key="label.jobAction.destination" bundle="${bundle}" /></td>
                    <td colspan="${empty fileActionForm.entity.contact ? '3' : '1'}">
                        <html:text property="jobAction.destination" styleClass="text" />
                    </td>
                    <c:if test="${not empty fileActionForm.entity.contact}">
	                    <td colspan="2">
	                        <html:text property="jobAction.contact.name" readonly="true" styleClass="text" />
	                    </td>
                    </c:if>
                </tr>
            </c:if>
            <c:if test="${not empty action.subject or actionType.email}">
                <html:errors property="entity.subject" />
                <tr>
                    <td class="jms-label"><bean:message key="label.jobAction.subject" bundle="${bundle}" /></td>
                    <td colspan="3">
                        <html:text property="jobAction.subject" styleClass="text" />
                    </td>
                </tr>
            </c:if>
            <html:errors property="entity.notation" />
            <tr>
                <td class="jms-label"><bean:message key="label.jobAction.notation" bundle="${bundle}" /></td>
                <td colspan="3">
                    <html:textarea property="jobAction.notation" styleClass="textarea" />
                </td>
            </tr>
        </table>
        <c:if test="${not empty fileActionForm.htmlInput}">
            <p>
                <textarea id="htmlInput" name="htmlInput" style="display:none;visibility:hidden;">${fileActionForm.htmlInput}</textarea>
            </p>
        </c:if>
        <c:if test="${actionType.email}">
            <jsp:include page="/WEB-INF/entity/action/editAttachment.jsp" />
        </c:if>
        <jsp:include page="/WEB-INF/entity/action/editDefaultNextAction.jsp" />
    </html:form>

</jsp:root>