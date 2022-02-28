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
        <tr>
            <td class="jms-label"><bean:message key="label.aseKey.aseKeyNo" bundle="${bundle}" /></td>
            <td><input value="${entity.aseKeyNo}" readonly="readonly" class="text" /></td>
            <td class="jms-label"><bean:message key="label.file" /></td>
            <td><input value="${entity.file.fileId}" readonly="readonly" class="text" /></td>
        </tr>
        <tr>
            <td class="jms-label"><bean:message key="label.aseKey.supplier" bundle="${bundle}" /></td>
            <td colspan="3"><input value="${entity.supplier.name}" readonly="readonly" class="text" /></td>
        </tr>
        <tr>
            <td class="jms-label"><bean:message key="label.aseKey.contact.name" bundle="${bundle}" /></td>
            <td colspan="3"><input value="${entity.contact.fullName}" readonly="readonly" class="text" /></td>
        </tr>
        <tr>
            <td class="jms-label"><bean:message key="label.contact.phone" /></td>
            <td><input value="${entity.contact.phone}" readonly="readonly" class="text" /></td>
            <td class="jms-label"><bean:message key="label.contact.mobile" /></td>
            <td><input value="${entity.contact.mobile}" readonly="readonly" class="text" /></td>
        </tr>
        <tr>
            <fmt:formatDate var="sentAdtDate" value="${entity.sentAdtDate}" pattern="${datePattern}" />
            <fmt:formatDate var="sentCustomerDate" value="${entity.sentCustomerDate}" pattern="${datePattern}" />
            <td class="jms-label"><bean:message key="label.aseKey.sentAdtDate" bundle="${bundle}" /></td>
            <td><input value="${sentAdtDate}" readonly="readonly" class="text" /></td>
            <td class="jms-label"><bean:message key="label.aseKey.sentCustomerDate" bundle="${bundle}" /></td>
            <td><input value="${sentCustomerDate}" readonly="readonly" class="text" /></td>
        </tr>
        <tr>
            <td class="jms-label"><bean:message key="label.aseKey.sentMethod" bundle="${bundle}" /></td>
            <td><input value="${entity.sentMethod.name}" readonly="readonly" class="text" /></td>
            <td class="jms-label"><bean:message key="label.aseKey.sentReferenceNo" bundle="${bundle}" /></td>
            <td><input value="${entity.sentReferenceNo}" readonly="readonly" class="text" /></td>
        </tr>
        <tr>
            <td class="jms-label"><bean:message key="label.aseKey.paymentMethod" bundle="${bundle}" /></td>
            <td><input value="${entity.paymentMethod.name}" readonly="readonly" class="text" /></td>
            <td colspan="2" />
        </tr>
        <tr>
            <fmt:formatDate var="invoiceDate" value="${entity.invoice.invoiceDate}" pattern="${datePattern}" />
            <td class="jms-label"><bean:message key="label.invoice.invoiceDate" bundle="${bundle}" /></td>
            <td><input value="${invoiceDate}" readonly="readonly" class="text" /></td>
            <td class="jms-label"><bean:message key="label.invoice.invoiceNo" bundle="${bundle}" /></td>
            <td><input value="${entity.invoice.invoiceNo}" readonly="readonly" class="text" /></td>
        </tr>
    </table>

</jsp:root>