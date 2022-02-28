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

    <html:form action="/editDiary">
        <html:hidden property="dispatch" value="saveJobAction" />
        <c:set var="completedDate"><fmt:formatDate value="${fileActionForm.jobAction.completedDate}" pattern="${datePattern}" /></c:set>
        <html:hidden property="jobAction.completedDate" value="${completedDate}" />

        <div id="errors" class="error">
            <html:errors />
        </div>

        <table class="nonDataTable">
            <html:errors property="actionCode.actionCodeId" />
            <tr>
                <td class="bold nowrap"><bean:message key="label.actionCode" /></td>
                <td>
                    <html:select property="actionCode.actionCodeId"
                        onchange="YAHOO.jms.sendPostRequest(this.form, this.form.action + '?dispatch=reload', 'scripts/common.js');">
                        <option value=""><bean:message key="option.select" /></option>
                        <html:options collection="actionCodes4type" property="actionCodeId" labelProperty="code" />
                    </html:select>
                </td>
                <td class="bold nowrap"><bean:message key="label.actionType" /></td>
                <td>${fileActionForm.actionType.name}</td>
            </tr>
            <jsp:include page="/WEB-INF/module/job/action/viewJob.jsp" />
            <c:choose>
                <c:when test="${fileActionForm.todoAction}">
                    <jsp:include page="/WEB-INF/module/file/editFileFca.jsp" />
                </c:when>
                <c:otherwise>
                    <jsp:include page="/WEB-INF/module/file/viewFileFca.jsp" />
                </c:otherwise>
            </c:choose>
            <html:errors property="jobAction.dueDate" />
            <tr>
                <td class="jms-label"><bean:message key="label.jobAction.dueDate" bundle="${bundle}" /></td>
                <td>
                    <c:set var="dueDate"><fmt:formatDate value="${fileActionForm.jobAction.dueDate}" pattern="${datePattern}" /></c:set>
                    <html:text property="jobAction.dueDate" title="${datePattern}" value="${dueDate}" styleClass="text" styleId="f_date_dueDate" />
                </td>
                <td colspan="2" />
            </tr>
            <html:errors property="jobAction.notation" />
            <tr>
                <td class="jms-label"><bean:message key="label.fileAction.notation" bundle="${bundle}" /></td>
                <td colspan="3">
                    <html:textarea property="jobAction.notation" styleClass="textarea" />
                </td>
            </tr>
        </table>
        <jsp:include page="/WEB-INF/entity/action/editDefaultNextAction.jsp" />
    </html:form>

</jsp:root>