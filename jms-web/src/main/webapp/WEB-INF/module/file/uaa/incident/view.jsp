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
        <caption><bean:message key="label.uaa.incident" bundle="${bundle}" /></caption>
        <tr>
            <td class="jms-label"><bean:message key="label.uaa.incident.fireCallNo" bundle="${bundle}" /></td>
            <td><input type="text" readonly="readonly" value="${entity.fireCallNo}" class="text" /></td>
            <td class="jms-label"><bean:message key="label.uaa.incident.injuryNo" bundle="${bundle}" /></td>
            <td><input type="text" readonly="readonly" value="${entity.injuryNo}" class="text" /></td>
        </tr>
        <tr>
            <td class="jms-label"><bean:message key="label.uaa.incident.region" bundle="${bundle}" /></td>
            <td><input type="text" readonly="readonly" value="${entity.region.name}" class="text" /></td>
            <fmt:formatDate var="incidentDate" value="${entity.incidentDate}" pattern="${datePattern}" />
            <td class="jms-label"><bean:message key="label.uaa.incident.incidentDate" bundle="${bundle}" /></td>
            <td><input type="text" readonly="readonly" value="${incidentDate}" class="text" /></td>
        </tr>
        <tr>
            <td class="jms-label"><bean:message key="label.uaa.incident.name" bundle="${bundle}" /></td>
            <td><input type="text" readonly="readonly" value="${entity.contact.firstName} ${entity.contact.surname}" class="text" /></td>
            <fmt:formatDate var="dateOfBirth" value="${entity.contact.dateOfBirth}" pattern="${datePattern}" />
            <td class="jms-label"><bean:message key="label.uaa.incident.dateOfBirth" bundle="${bundle}" /></td>
            <td><input type="text" readonly="readonly" value="${dateOfBirth}" class="text" /></td>
        </tr>
        <tr>
            <td class="jms-label"><bean:message key="label.uaa.incident.address" bundle="${bundle}" /></td>
            <td colspan="3"><input type="text" readonly="readonly" value="${entity.address.fullAddress}" class="text" /></td>
        </tr>
        <tr>
            <td class="jms-label"><bean:message key="label.uaa.incident.injury" bundle="${bundle}" /></td>
            <td><input type="text" readonly="readonly" value="${entity.injuryType.name}" class="text" /></td>
            <td colspan="2"><input type="text" readonly="readonly" value="${entity.injuryTo}" class="text" /></td>
        </tr>
        <tr>
            <td class="jms-label"><bean:message key="label.uaa.incident.multipleInjury" bundle="${bundle}" /></td>
            <td><input type="text" readonly="readonly" value="${entity.multipleInjuryType.name}" class="text" /></td>
            <td colspan="2"><input type="text" readonly="readonly" value="${entity.multipleInjuryTo}" class="text" /></td>
        </tr>
        <tr>
            <td class="jms-label"><bean:message key="label.uaa.incident.alcoholLevel" bundle="${bundle}" /></td>
            <td><input type="text" readonly="readonly" value="${entity.alcoholLevel}" class="text" /></td>
            <td class="jms-label"><bean:message key="label.uaa.incident.drugs" bundle="${bundle}" /></td>
            <td><input type="text" readonly="readonly" value="${entity.drugs}" class="text" /></td>
        </tr>
        <tr>
            <td class="jms-label"><bean:message key="label.uaa.incident.treatedAt" bundle="${bundle}" /></td>
            <td colspan="3"><input type="text" readonly="readonly" value="${entity.treatedAt}" class="text" /></td>
        </tr>
    </table>
</jsp:root>