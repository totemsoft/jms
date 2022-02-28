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

    <html:form action="/editCall">
        <html:hidden property="dispatch" value="saveFileAction" />
        <html:hidden property="todoAction" />
        <input type="hidden" id="reloadOnChangeUrl" value="dispatch=reload" />

        <c:set var="action" value="${fileActionForm.entity}" />
        <c:if test="${not fileActionForm.todoAction}">
            <fmt:formatDate var="completedDate" value="${action.completedDate}" pattern="${datePattern}" />
            <html:hidden property="entity.completedDate" value="${completedDate}" />
        </c:if>

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
            <c:choose>
                <c:when test="${fileActionForm.todoAction}">
                    <jsp:include page="/WEB-INF/module/file/editFileFca.jsp" />
                    <jsp:include page="/WEB-INF/entity/action/editResponsibleUser.jsp" />
                </c:when>
                <c:otherwise>
                    <jsp:include page="/WEB-INF/module/file/viewFileFca.jsp" />
                    <jsp:include page="/WEB-INF/entity/action/viewResponsibleUser.jsp" />
                </c:otherwise>
            </c:choose>
            <html:errors property="entity.dueDate" />
            <tr>
                <td class="jms-label"><bean:message key="label.fileAction.dueDate" bundle="${bundle}" /></td>
                <td>
                    <fmt:formatDate var="dueDate" value="${action.dueDate}" pattern="${datePattern}" />
                    <html:text property="entity.dueDate" title="${datePattern}" value="${dueDate}" styleClass="text" styleId="f_date_dueDate" />
                </td>
                <td colspan="2" />
            </tr>
            <html:errors property="entity.destination" />
            <tr>
                <td class="jms-label"><bean:message key="label.fileAction.destination" bundle="${bundle}" /></td>
                <td colspan="3">
                    <table class="nonDataTable" style="border: 1px solid #65A1D7;">
                        <tr>
                            <td>
                                <html:hidden property="contactId" styleId="action.contactId" />
                                <html:radio property="entity.destination" value="${fileActionForm.manualDestination}" onchange="YUD.get('action.contactId').value='';" />
                            </td>
                            <td class="width100" colspan="3"><html:text property="manualDestination" styleClass="text" /></td>
                        </tr>
                        <c:forEach items="${contacts}" var="contact">
                        <c:if test="${not empty contact.phone}">
                            <tr>
                                <td><html:radio property="entity.destination" value="${contact.phone}" onchange="YUD.get('action.contactId').value=${contact.id};"></html:radio></td>
                                <td>${contact.phone}</td>
                                <td class="bold italic">${contact.fullName}</td>
                                <td class="italic">${contact.type}</td>
                            </tr>
                        </c:if>
                        </c:forEach>
                    </table>
                </td>
            </tr>
            <html:errors property="entity.notation" />
            <tr>
                <td class="jms-label"><bean:message key="label.fileAction.notation" bundle="${bundle}" /></td>
                <td colspan="3">
                    <html:textarea property="entity.notation" styleClass="textarea" />
                </td>
            </tr>
        </table>
        <jsp:include page="/WEB-INF/entity/action/editDefaultNextAction.jsp" />
    </html:form>

</jsp:root>