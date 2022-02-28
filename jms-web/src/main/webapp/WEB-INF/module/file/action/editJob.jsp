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

    <html:form action="/editJob">
        <html:hidden property="dispatch" value="saveJobAction" />

        <div id="errors" class="error">
            <html:errors />
        </div>

        <table class="nonDataTable">
            <html:errors property="jobAction.job.jobType.jobTypeId" />
            <html:errors property="actionCode.actionCodeId" />
            <html:errors property="jobAction.actionCode.actionCodeId" />
            <tr>
                <td class="bold nowrap"><bean:message key="label.jobType" /></td>
                <td>
                    <html:select property="jobAction.job.jobType.jobTypeId"
                        onchange="YAHOO.jms.sendPostRequest(this.form, this.form.action + '?dispatch=reload');">
                        <option value=""><bean:message key="option.select" /></option>
                        <html:options collection="jobTypes" property="jobTypeId" labelProperty="name" />
                    </html:select>
                </td>
                <td class="bold nowrap"><bean:message key="label.actionCode" /></td>
                <td>
                    <html:select property="actionCode.actionCodeId"
                        onchange="YAHOO.jms.sendPostRequest(this.form, this.form.action + '?dispatch=reload');">
                        <option value=""><bean:message key="option.select" /></option>
                        <html:options collection="actionCodes4type" property="actionCodeId" labelProperty="code" />
                    </html:select>
                </td>
            </tr>
            <html:errors property="jobAction.job.fca" />
            <jsp:include page="/WEB-INF/module/file/viewFileFca.jsp" />
            <html:errors property="jobAction.job.description" />
            <tr>
                <td class="jms-label-wrap"><bean:message key="label.job.description" bundle="${bundle}" /></td>
                <td colspan="3">
                    <html:textarea property="jobAction.job.description" styleClass="text" />
                </td>
            </tr>
            <html:errors property="jobAction.notation" />
            <tr>
                <td class="jms-label-wrap"><bean:message key="label.jobAction.notation" bundle="${bundle}" /></td>
                <td colspan="3">
                    <html:textarea property="jobAction.notation" styleClass="textarea" />
                </td>
            </tr>
        </table>
        <jsp:include page="/WEB-INF/entity/action/editDefaultNextAction.jsp" />
    </html:form>

</jsp:root>