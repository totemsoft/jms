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

    <table class="nonDataTable">
        <caption>
            <div class="float-left width2em ${entity.mailIn ? 'arrow-right-blue' : 'arrow-left-green'}">${nbsp}</div>
            <div class="float-left">${entity.mailIn ? 'Incoming Mail' : 'Outgoing Mail'}</div>
        </caption>
        <tr>
            <td class="jms-label"><bean:message key="label.file" /></td>
            <td><input value="${entity.file.fileId}" readonly="true" class="text" /></td>
            <c:if test="${empty entity.file.fca}">
                <td colspan="2" />
            </c:if>
            <c:if test="${not empty entity.file.fca.fcaId}">
                <td class="jms-label"><bean:message key="label.fca" /></td>
                <td><input value="${entity.file.fca.fcaId}" readonly="true" class="text" /></td>
            </c:if>
        </tr>
        <tr>
            <fmt:formatDate var="date" value="${entity.date}" pattern="${datePattern}" />
            <td class="jms-label"><bean:message key="label.mail.date" bundle="${bundle}" /></td>
            <td><input value="${date}" readonly="readonly" class="text" /></td>
            <td class="jms-label"><bean:message key="label.mail.mailRegisterNo" bundle="${bundle}" /></td>
            <td><input value="${entity.mailRegisterNo}" readonly="readonly" class="text" /></td>
        </tr>
        <tr>
            <td class="jms-label"><bean:message key="label.mail.mailType" bundle="${bundle}" /></td>
            <td><input value="${entity.mailType.name}" readonly="readonly" class="text" /></td>
            <c:if test="${entity.mailIn}">
                <td class="jms-label"><bean:message key="label.mail.rts" bundle="${bundle}" /></td>
                <td>
                    <c:if test="${entity.rts}"><input type="checkbox" checked="checked" disabled="disabled" /></c:if>
                    <c:if test="${not entity.rts}"><input type="checkbox" disabled="disabled" /></c:if>
                </td>
            </c:if>
            <c:if test="${not entity.mailIn}">
                <td colspan="2" />
            </c:if>
        </tr>
        <tr>
            <td class="jms-label"><bean:message key="label.workGroup" /></td>
            <td><input value="${entity.workGroup.name}" readonly="readonly" class="text" /></td>
            <td class="jms-label"><bean:message key="label.user" /></td>
            <td><input value="${entity.user.contact.name}" readonly="readonly" class="text" /></td>
        </tr>
        <tr>
            <td class="jms-label"><bean:message key="label.contact.name" /></td>
            <td colspan="3"><input value="${entity.contact.name}" readonly="readonly" class="text" /></td>
        </tr>
        <tr>
            <td class="jms-label"><bean:message key="label.address" /></td>
            <td colspan="3"><input value="${entity.address.fullAddress}" readonly="readonly" class="text" /></td>
        </tr>
        <tr>
            <td class="jms-label valign-top"><bean:message key="label.mail.details" bundle="${bundle}" /></td>
            <td colspan="3"><textarea readonly="readonly" class="text">${entity.details}</textarea></td>
        </tr>
    </table>

</jsp:root>