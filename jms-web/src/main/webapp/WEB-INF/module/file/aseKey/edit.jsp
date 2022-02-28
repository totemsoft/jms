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

    <html:form action="/aseKey">
        <html:hidden property="dispatch" value="save" />
        <html:hidden property="entity.aseKeyId" />
        <html:hidden property="entity.contact.contactId" />
        <input type="hidden" id="reloadOnChangeUrl" value="dispatch=reload" />
        <input type="hidden" id="title" value="${title}" />

        <div id="errors" class="error">
            <html:errors />
        </div>

        <table class="nonDataTable">
            <tr>
                <td class="jms-label"><bean:message key="label.aseKey.aseKeyNo" bundle="${bundle}" /></td>
                <td><html:text property="entity.aseKeyNo" styleClass="text" /></td>
                <c:if test="${not empty aseKeyForm.entity.file.fileId}">
                    <td class="jms-label"><bean:message key="label.file" /></td>
                    <td><input value="${aseKeyForm.entity.file.fileId}" readonly="readonly" class="text" /></td>
                </c:if>
                <c:if test="${empty aseKeyForm.entity.file.fileId}">
                    <td colspan="2" />
                </c:if>
            </tr>
            <tr>
                <td class="jms-label"><bean:message key="label.aseKey.supplier" bundle="${bundle}" /></td>
                <td colspan="3">
                    <div>
                        <html:hidden property="entity.supplier.supplierId" styleId="supplierId.edit.value" />
                        <html:text property="entity.supplier.name" styleId="supplierId.edit" />
                        <div id="supplierId.edit.container"></div>
                    </div>
                </td>
            </tr>
            <tr>
                <td class="jms-label"><bean:message key="label.contact.firstName" /></td>
                <td><html:text property="entity.contact.firstName" styleClass="text" /></td>
                <td class="jms-label"><bean:message key="label.contact.surname" /></td>
                <td><html:text property="entity.contact.surname" styleClass="text" /></td>
            </tr>
            <tr>
                <td class="jms-label"><bean:message key="label.contact.phone" /></td>
                <td><html:text property="entity.contact.phone" styleClass="text" /></td>
                <td class="jms-label"><bean:message key="label.contact.mobile" /></td>
                <td><html:text property="entity.contact.mobile" styleClass="text" /></td>
            </tr>
            <tr>
                <fmt:formatDate var="sentAdtDate" value="${aseKeyForm.entity.sentAdtDate}" pattern="${datePattern}" />
                <fmt:formatDate var="sentCustomerDate" value="${aseKeyForm.entity.sentCustomerDate}" pattern="${datePattern}" />
                <td class="jms-label"><bean:message key="label.aseKey.sentAdtDate" bundle="${bundle}" /></td>
                <td><html:text property="entity.sentAdtDate" value="${sentAdtDate}" styleClass="text" styleId="f_date_sentAdtDate" /></td>
                <td class="jms-label"><bean:message key="label.aseKey.sentCustomerDate" bundle="${bundle}" /></td>
                <td><html:text property="entity.sentCustomerDate" value="${sentCustomerDate}" styleClass="text" styleId="f_date_sentCustomerDate" /></td>
            </tr>
            <tr>
                <td class="jms-label"><bean:message key="label.aseKey.sentMethod" bundle="${bundle}" /></td>
                <td>
                    <html:select property="entity.sentMethod">
                        <option value=""><bean:message key="option.select" /></option>
                        <html:options collection="sentMethods" property="id" labelProperty="name" />
                    </html:select>
                </td>
                <td class="jms-label"><bean:message key="label.aseKey.sentReferenceNo" bundle="${bundle}" /></td>
                <td><html:text property="entity.sentReferenceNo" styleClass="text" /></td>
            </tr>
            <tr>
                <td class="jms-label"><bean:message key="label.aseKey.paymentMethod" bundle="${bundle}" /></td>
                <td>
                    <html:select property="entity.paymentMethod">
                        <option value=""><bean:message key="option.select" /></option>
                        <html:options collection="paymentMethods" property="id" labelProperty="name" />
                    </html:select>
                </td>
                <td colspan="2" />
            </tr>
            <tr>
                <fmt:formatDate var="invoiceDate" value="${aseKeyForm.entity.invoice.invoiceDate}" pattern="${datePattern}" />
                <td class="jms-label"><bean:message key="label.invoice.invoiceDate" bundle="${bundle}" /></td>
                <td><html:text property="entity.invoice.invoiceDate" value="${invoiceDate}" styleClass="text" styleId="f_date_invoiceDate" /></td>
                <td class="jms-label"><bean:message key="label.invoice.invoiceNo" bundle="${bundle}" /></td>
                <td><html:text property="entity.invoice.invoiceNo" styleClass="text" /></td>
            </tr>
        </table>
    </html:form>

</jsp:root>