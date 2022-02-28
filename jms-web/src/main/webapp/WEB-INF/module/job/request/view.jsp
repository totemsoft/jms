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

    <html:form action="/viewJobRequest" method="get">
        <table class="nonDataTable">
            <tr>
                <td class="jms-label"><bean:message key="label.jobRequest.id" bundle="${bundle}" /></td>
                <td><html:text property="entity.jobRequestId" readonly="true" styleClass="text" /></td>
                <td class="jms-label"><bean:message key="label.jobType" /></td>
                <td><html:text property="entity.jobType.name" readonly="true" styleClass="text" /></td>
            </tr>
            <tr>
                <td class="jms-label"><bean:message key="label.fca" /></td>
                <td><html:text property="entity.fca.fcaId" readonly="true" styleClass="text" /></td>
                <td class="jms-label"><bean:message key="label.file" /></td>
                <td><html:text property="entity.fca.file.fileId" readonly="true" styleClass="text" /></td>
            </tr>
            <tr>
                <td class="jms-label"><bean:message key="label.jobRequest.description" bundle="${bundle}" /></td>
                <td colspan="3"><html:textarea property="entity.description" readonly="true" styleClass="textarea" /></td>
            </tr>
            <tr>
                <c:set var="date"><fmt:formatDate value="${jobRequestForm.entity.requestedDate}" pattern="${datePattern}"/></c:set>
                <td class="jms-label"><bean:message key="label.jobRequest.requestedDate" bundle="${bundle}" /></td>
                <td><html:text property="entity.requestedDate" value="${date}" title="${datePattern}" maxlength="10" readonly="true" styleClass="text" /></td>
                <td class="jms-label"><bean:message key="label.jobRequest.requestedBy" bundle="${bundle}" /></td>
                <td><html:text property="entity.requestedBy.login" readonly="true" styleClass="text" /></td>
            </tr>
            <tr>
                <td class="jms-label"><bean:message key="label.jobRequest.requestedEmail" bundle="${bundle}" /></td>
                <td><html:text property="entity.requestedEmail" readonly="true" styleClass="text" /></td>
                <td class="jms-label"><bean:message key="label.jobRequest.link" bundle="${bundle}" /></td>
                <td><html:text property="entity.link" readonly="true" styleClass="text" /></td>
            </tr>
        </table>
    </html:form>

</jsp:root>