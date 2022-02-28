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

    <c:set var="bundle" value="setup" />

    <html:form action="/viewActionCode" method="get">
        <html:hidden property="entity.actionCodeId" />
        <table class="nonDataTable">
            <tr>
                <td class="jms-label"><bean:message key="label.actionCode.code" bundle="${bundle}" /></td>
                <td><html:text property="entity.code" readonly="true" styleClass="text" /></td>
                <td class="jms-label"><bean:message key="label.actionType" bundle="${bundle}" /></td>
                <td><html:text property="entity.actionType.name" readonly="true" styleClass="text" /></td>
                <td class="jms-label"><bean:message key="label.actionCode.active" bundle="${bundle}" /></td>
                <td><html:checkbox property="entity.active" disabled="true" /></td>
            </tr>
            <tr>
                <td class="jms-label"><bean:message key="label.actionCode.notation" bundle="${bundle}" /></td>
                <td colspan="3"><html:text property="entity.notation" readonly="true" styleClass="text" /></td>
                <td class="jms-label"><bean:message key="label.actionCode.bulkMail" bundle="${bundle}" /></td>
                <td><html:checkbox property="entity.bulkMail" disabled="true" /></td>
            </tr>
            <tr>
                <td class="jms-label"><bean:message key="label.actionCode.template" bundle="${bundle}" /></td>
                <td>
                    <c:if test="${not empty actionCodeForm.entity.template.id}">
                        <html:text property="entity.template.name" readonly="true" />
                    </c:if>
                </td>
                <td class="jms-label"><bean:message key="label.actionCode.documentTemplate" bundle="${bundle}" /></td>
                <td colspan="3">
                    <c:if test="${not empty actionCodeForm.entity.documentTemplate.id}">
                        <html:text property="entity.documentTemplate.name" readonly="true" />
                    </c:if>
                </td>
            </tr>
            <tr>
                <td class="jms-label"><bean:message key="label.jobType" bundle="${bundle}" /></td>
                <td>
                    <c:if test="${not empty actionCodeForm.entity.jobType.id}">
                        <html:text property="entity.jobType.name" readonly="true" styleClass="text" />
                    </c:if>
                </td>
                <td class="jms-label"><bean:message key="label.workGroup" bundle="${bundle}" /></td>
                <td colspan="3">
                    <c:if test="${not empty actionCodeForm.entity.workGroup.id}">
                        <html:text property="entity.workGroup.name" readonly="true" styleClass="text" />
                    </c:if>
                </td>
            </tr>
        </table>
    </html:form>

</jsp:root>