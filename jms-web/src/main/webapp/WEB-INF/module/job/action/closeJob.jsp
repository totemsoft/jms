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

    <c:set var="bundle" value="job" />

    <html:form action="/closeJob">
        <html:hidden property="dispatch" value="closeJobAction" />

        <div id="errors" class="error">
            <html:errors />
        </div>

        <table class="nonDataTable">
            <jsp:include page="/WEB-INF/module/job/action/viewJob.jsp" />
            <jsp:include page="/WEB-INF/module/file/viewFileFca.jsp" />
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
                <td colspan="2"></td>
            </tr>
            <html:errors property="jobAction.job.description" />
            <tr>
                <td class="jms-label"><bean:message key="label.job.description" bundle="${bundle}" /></td>
                <td colspan="3">
                    <html:textarea property="jobAction.job.description" readonly="true" styleClass="textarea" />
                </td>
            </tr>
            <html:errors property="jobAction.notation" />
            <tr>
                <td class="jms-label"><bean:message key="label.jobAction.notation" bundle="${bundle}" /></td>
                <td colspan="3">
                    <html:textarea property="jobAction.notation" styleClass="textarea" />
                </td>
            </tr>
        </table>
        <table class="nonDataTable">
            <tr>
                <td>
                    <jsp:include page="/WEB-INF/entity/action/editDefaultAction.jsp" />
                </td>
            </tr>
            <!--tr>
                <td>
                    <jsp:include page="/WEB-INF/entity/action/editNextAction.jsp" />
                </td>
            </tr-->
        </table>
    </html:form>

</jsp:root>