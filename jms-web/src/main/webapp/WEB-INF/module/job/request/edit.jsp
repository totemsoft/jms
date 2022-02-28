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

    <html:form action="/editJobRequest">
        <html:hidden property="dispatch" value="save" />

        <div id="errors" class="error">
            <html:errors />
        </div>

        <table class="nonDataTable">
            <c:if test="${not empty jobRequestForm.entity.jobRequestId}">
                <tr>
                    <td class="jms-label"><bean:message key="label.jobRequest.id" bundle="${bundle}" /></td>
                    <td><html:text property="entity.jobRequestId" readonly="true" styleClass="text" /></td>
                </tr>
            </c:if>
            <html:errors property="entity.jobType.jobTypeId" />
            <tr>
                <td class="jms-label"><bean:message key="label.jobType" /></td>
                <td>
                    <html:select property="entity.jobType.jobTypeId">
                        <option value=""><bean:message key="option.select" /></option>
                        <html:options collection="jobTypes" property="id" labelProperty="name" />
                    </html:select>
                </td>
            </tr>
            <html:errors property="entity.fca.fcaId" />
            <tr>
                <td class="jms-label"><bean:message key="label.fca" /></td>
                <td><html:text property="entity.fca.fcaId" styleClass="text" /></td>
            </tr>
            <html:errors property="entity.description" />
            <tr>
                <td class="jms-label"><bean:message key="label.jobRequest.description" bundle="${bundle}" /></td>
                <td><html:textarea property="entity.description" styleClass="textarea" /></td>
            </tr>
            <html:errors property="entity.requestedDate" />
            <tr>
                <c:set var="date"><fmt:formatDate value="${jobRequestForm.entity.requestedDate}" pattern="${datePattern}"/></c:set>
                <td class="jms-label"><bean:message key="label.jobRequest.requestedDate" bundle="${bundle}" /></td>
                <td><html:text property="entity.requestedDate" value="${date}" title="${datePattern}" maxlength="10" readonly="true" styleClass="text" /></td>
            </tr>
            <html:errors property="entity.requestedBy" />
            <tr>
                <td class="jms-label"><bean:message key="label.jobRequest.requestedBy" bundle="${bundle}" /></td>
                <td><html:text property="entity.requestedBy.login" readonly="true" styleClass="text" /></td>
            </tr>
            <html:errors property="entity.requestedEmail" />
            <tr>
                <td class="jms-label"><bean:message key="label.jobRequest.requestedEmail" bundle="${bundle}" /></td>
                <td><html:text property="entity.requestedEmail" styleClass="text" /></td>
            </tr>
            <html:errors property="entity.link" />
            <tr>
                <td class="jms-label"><bean:message key="label.jobRequest.link" bundle="${bundle}" /></td>
                <td><html:text property="entity.link" styleClass="text" /></td>
            </tr>
        </table>
    </html:form>

</jsp:root>