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

    <html:form action="/editJobDetails">
        <html:hidden property="dispatch" value="save" />

        <div id="errors" class="error">
            <html:errors />
        </div>

        <table class="nonDataTable">
            <tr>
                <td class="jms-label"><bean:message key="label.job.id" bundle="${bundle}" /></td>
                <td><html:text property="entity.jobId" readonly="true" styleClass="text" /></td>
                <td colspan="2" />
            </tr>
            <html:errors property="entity.jobType.jobTypeId" />
            <tr>
                <td class="jms-label"><bean:message key="label.jobType" /></td>
                <td>
                    <html:select property="entity.jobType.jobTypeId">
                        <option value=""><bean:message key="option.select" /></option>
                        <html:options collection="jobTypes" property="id" labelProperty="name" />
                    </html:select>
                </td>
                <td class="jms-label"><bean:message key="label.workGroup" /></td>
                <td><input type="text" value="${jobForm.entity.jobType.workGroup.name}" readonly="readonly" class="text" /></td>
            </tr>
            <jsp:include page="/WEB-INF/module/job/viewFileFca.jsp" />
            <jsp:include page="/WEB-INF/entity/action/editResponsibleUser.jsp" />
            <html:errors property="entity.description" />
            <tr>
                <td colspan="4" class="jms-label"><bean:message key="label.job.description" bundle="${bundle}" /></td>
            </tr>
            <tr>
                <td colspan="4"><html:textarea property="entity.description" styleClass="text" /></td>
            </tr>
            <tr>
                <c:set var="date"><fmt:formatDate value="${jobForm.entity.createdDate}" pattern="${datePattern}"/></c:set>
                <td class="jms-label"><bean:message key="label.job.requestedDate" bundle="${bundle}" /></td>
                <td><html:text property="entity.createdDate" value="${date}" title="${datePattern}" maxlength="10" readonly="true" styleClass="text" /></td>
                <td class="jms-label"><bean:message key="label.job.requestedBy" bundle="${bundle}" /></td>
                <td><html:text property="entity.createdBy.contact.name" readonly="true" styleClass="text" /></td>
            </tr>
            <html:errors property="entity.requestedEmail" />
            <tr>
                <td class="jms-label"><bean:message key="label.job.requestedEmail" bundle="${bundle}" /></td>
                <td colspan="3"><html:text property="entity.requestedEmail" styleClass="text" /></td>
            </tr>
            <html:errors property="entity.link" />
            <tr>
                <td class="jms-label"><bean:message key="label.job.link" bundle="${bundle}" /></td>
                <td colspan="3"><html:text property="entity.link" styleClass="text" /></td>
            </tr>
        </table>
    </html:form>

</jsp:root>