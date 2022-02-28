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

    <html:form action="/aseKeyOrder">
        <html:hidden property="dispatch" value="saveAseKey" />
        <html:hidden property="aseKey.aseKeyId" />
        <c:if test="${not empty aseKeyOrderForm.aseKey.file.fileId}">
            <html:hidden property="aseKey.file.fileId" />
        </c:if>
        <html:hidden property="aseKey.supplier.supplierId" />
        <html:hidden property="aseKey.contact.contactId" />
        <input type="hidden" id="title" value="${title}" />

        <div id="errors" class="error">
            <html:errors />
        </div>

        <table class="nonDataTable">
            <tr>
                <td class="jms-label"><bean:message key="label.aseKey.aseKeyNo" bundle="${bundle}" /></td>
                <td><html:text property="aseKey.aseKeyNo" styleClass="text" /></td>
                <td class="jms-label"><bean:message key="label.aseKey.licenseNo" bundle="${bundle}" /></td>
                <td><html:text property="aseKey.licenseNo" styleClass="text" /></td>
            </tr>
            <tr>
                <td class="jms-label"><bean:message key="label.contact.firstName" /></td>
                <td><html:text property="aseKey.contact.firstName" styleClass="text" /></td>
                <td class="jms-label"><bean:message key="label.contact.surname" /></td>
                <td><html:text property="aseKey.contact.surname" styleClass="text" /></td>
            </tr>
            <tr>
                <td class="jms-label"><bean:message key="label.contact.phone" /></td>
                <td><html:text property="aseKey.contact.phone" styleClass="text" /></td>
                <td class="jms-label"><bean:message key="label.contact.mobile" /></td>
                <td><html:text property="aseKey.contact.mobile" styleClass="text" /></td>
            </tr>
            <tr>
                <td class="jms-label"><bean:message key="label.aseKey.status" bundle="${bundle}" /></td>
                <td>
                    <html:select property="aseKey.status">
                        <option value=""><bean:message key="option.select" /></option>
                        <html:options collection="statuses" property="code" labelProperty="name" />
                    </html:select>
                </td>
                <td colspan="2" />
            </tr>
        </table>
    </html:form>

</jsp:root>