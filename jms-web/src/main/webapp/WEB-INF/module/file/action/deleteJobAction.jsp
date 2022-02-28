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

    <html:form action="/deleteJobAction">
        <html:hidden property="dispatch" value="deleteJobAction" />

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
            <jsp:include page="/WEB-INF/entity/action/viewResponsibleUser.jsp" />
            <tr>
                <td class="jms-label"><bean:message key="label.jobAction.dueDate" bundle="${bundle}" /></td>
                <td>
                    <fmt:formatDate var="dueDate" value="${fileActionForm.jobAction.dueDate}" pattern="${datePattern}" />
                    <html:text property="jobAction.dueDate" value="${dueDate}" readonly="true" styleClass="text" />
                </td>
                <td colspan="2" />
            </tr>
            <c:if test="${not empty fileActionForm.jobAction.destination}">
                <tr>
                    <td class="jms-label"><bean:message key="label.jobAction.destination" bundle="${bundle}" /></td>
                    <td>
                        <html:text property="jobAction.destination" readonly="true" styleClass="text" />
                    </td>
                    <td colspan="2">
                        <c:if test="${not empty fileActionForm.jobAction.contact}">
                            <html:text property="jobAction.contact.name" readonly="true" styleClass="text" />
                        </c:if>
                    </td>
                </tr>
            </c:if>
            <c:if test="${not empty fileActionForm.jobAction.subject}">
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
                    <html:textarea property="jobAction.notation" readonly="true" styleClass="text" />
                </td>
            </tr>
        </table>
    </html:form>

</jsp:root>