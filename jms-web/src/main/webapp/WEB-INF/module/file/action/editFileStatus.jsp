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

    <html:form action="/editFileStatus">
        <html:hidden property="dispatch" value="saveFileAction" />

        <div id="errors" class="error">
            <html:errors />
        </div>

        <table class="nonDataTable">
            <html:errors property="fileStatus.fileStatusId" />
            <html:errors property="actionCode.actionCodeId" />
            <tr>
                <td class="jms-label"><bean:message key="label.fileStatus" /></td>
                <td>
                    <html:select property="fileStatus.fileStatusId"
                        onchange="YAHOO.jms.sendPostRequest(this.form, this.form.action + '?dispatch=reload', 'scripts/common.js');">
                        <option value=""><bean:message key="option.select" /></option>
                        <html:options collection="fileStatuses" property="fileStatusId" labelProperty="name" />
                    </html:select>
                </td>
                <td class="jms-label"><bean:message key="label.actionCode" /></td>
                <td>
                    <html:select property="actionCode.actionCodeId"
                        onchange="YAHOO.jms.sendPostRequest(this.form, this.form.action + '?dispatch=reload', 'scripts/common.js');">
                        <option value=""><bean:message key="option.select" /></option>
                        <html:options collection="actionCodes4type" property="actionCodeId" labelProperty="code" />
                    </html:select>
                </td>
            </tr>
            <jsp:include page="/WEB-INF/module/file/viewFileFca.jsp" />
            <html:errors property="entity.notation" />
            <tr>
                <td class="jms-label"><bean:message key="label.fileAction.notation" bundle="${bundle}" /></td>
                <td colspan="3">
                    <html:textarea property="entity.notation" styleClass="textarea" />
                </td>
            </tr>
            <c:if test="${fileActionForm.fileStatus.archived}">
            <tr class="bordered">
                <td class="jms-label"><bean:message key="label.archive.code" bundle="${bundle}" /></td>
                <td>
                    <html:text property="archive.code" styleId="archive.edit" styleClass="text" />
                    <div id="archive.edit.container"></div>
                </td>
                <td class="jms-label"><bean:message key="label.archive.name" bundle="${bundle}" /></td>
                <td>
                    <html:text property="archive.name" readonly="true" styleId="archive.edit.value" styleClass="text" />
                </td>
            </tr>
            </c:if>
        </table>
        <jsp:include page="/WEB-INF/entity/action/editDefaultNextAction.jsp" />
    </html:form>

</jsp:root>