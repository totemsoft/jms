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

    <html:form action="/viewJobType" method="get">
        <html:hidden property="entity.jobTypeId" />
        <table class="nonDataTable">
            <tr>
                <td class="jms-label"><bean:message key="label.jobType.name" bundle="${bundle}" /></td>
                <td>
                    <html:text property="entity.name" readonly="true" styleClass="text" />
                </td>
            </tr>
            <tr>
                <td class="jms-label"><bean:message key="label.securityGroup" bundle="${bundle}" /></td>
                <td>
                    <c:if test="${not empty jobTypeForm.entity.securityGroup}">
                        <html:text property="entity.securityGroup.name" readonly="true" styleClass="text" />
                    </c:if>
                </td>
            </tr>
            <tr>
                <td class="jms-label"><bean:message key="label.actionCode" bundle="${bundle}" /></td>
                <td>
                    <c:if test="${not empty jobTypeForm.entity.actionCode}">
                        <html:text property="entity.actionCode.code" readonly="true" styleClass="text" />
                    </c:if>
                </td>
            </tr>
            <tr>
                <td class="jms-label"><bean:message key="label.jobType.closeJobSecurityGroup" bundle="${bundle}" /></td>
                <td>
                    <c:if test="${not empty jobTypeForm.entity.closeJobSecurityGroup}">
                        <html:text property="entity.closeJobSecurityGroup.name" readonly="true" styleClass="text" />
                    </c:if>
                </td>
            </tr>
            <tr>
                <td class="jms-label"><bean:message key="label.workGroup" bundle="${bundle}" /></td>
                <td>
                    <c:if test="${not empty jobTypeForm.entity.workGroup}">
                        <html:text property="entity.workGroup.name" readonly="true" styleClass="text" />
                    </c:if>
                </td>
            </tr>
        </table>
    </html:form>

</jsp:root>