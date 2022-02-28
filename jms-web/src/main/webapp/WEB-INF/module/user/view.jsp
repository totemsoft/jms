<?xml version="1.0" encoding="UTF-8"?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:html="http://struts.apache.org/tags-html"
    xmlns:bean="http://struts.apache.org/tags-bean"
    xmlns:logic="http://struts.apache.org/tags-logic"
    xmlns:c="http://java.sun.com/jsp/jstl/core"
    xmlns:fn="http://java.sun.com/jsp/jstl/functions"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    version="2.0">

    <html:form action="/viewUser" method="get">
        <html:hidden property="entity.userId" />

        <table class="nonDataTable">
            <tr>
                <td class="jms-label"><bean:message key="label.user.login" /></td>
                <td><html:text property="entity.login" readonly="true" styleClass="text" /></td>
                <td></td>
                <td></td>
            </tr>
            <tr>
                <td class="jms-label"><bean:message key="label.user.userType" /></td>
                <td>
                    <c:if test="${not empty userForm.entity.userType}">
                        <html:text property="entity.userType.name" readonly="true" styleClass="text" />
                    </c:if>
                </td>
                <td class="jms-label"><bean:message key="label.user.securityGroup" /></td>
                <td>
                    <c:if test="${not empty userForm.entity.securityGroup}">
                        <html:text property="entity.securityGroup.name" readonly="true" styleClass="text" />
                    </c:if>
                </td>
            </tr>
            <c:set var="contact" value="${userForm.contact}" />
            <jsp:include page="/WEB-INF/entity/contact/view.jsp" />
            <tr>
                <td class="jms-label"><bean:message key="label.user.status" /></td>
                <td><html:checkbox property="entity.active" disabled="true" /></td>
                <td></td>
                <td></td>
            </tr>
        </table>
    </html:form>

</jsp:root>