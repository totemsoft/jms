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
            <td class="jms-label"><bean:message key="label.fca" /></td>
            <td><html:text property="entity.fca.fcaId" readonly="true" styleClass="text" /></td>
            <td class="jms-label"><bean:message key="label.file.siteType" bundle="${bundle}" /></td>
            <td><html:text property="entity.siteType.name" readonly="true" styleClass="text" /></td>
        </tr>
        <tr>
            <td class="jms-label"><bean:message key="label.aseFile.aseSerialNo" bundle="${bundle}" /></td>
            <td><html:text property="entity.aseFile.aseSerialNo" readonly="true" styleClass="text" /></td>
            <td class="jms-label"><bean:message key="label.aseFile.aseType" bundle="${bundle}" /></td>
            <td><html:text property="entity.aseFile.aseType.name" readonly="true" styleClass="text" /></td>
        </tr>
        <tr>
            <td class="jms-label" colspan="2"><bean:message key="label.aseConn" /></td>
            <td class="italic"><bean:message key="label.aseConn.aseConnType" bundle="${bundle}" /></td>
            <td class="italic"><bean:message key="label.aseConn.reference" bundle="${bundle}" /></td>
        </tr>
        <tr>
            <td class="jms-label"></td>
            <td class="italic"><bean:message key="label.aseConn.primary" bundle="${bundle}" /></td>
            <td><html:text property="entity.aseFile.aseConn.aseConnType.name" readonly="true" styleClass="text" /></td>
            <td><html:text property="entity.aseFile.aseConn.priRef" readonly="true" styleClass="text" /></td>
        </tr>
        <tr>
            <td class="jms-label"></td>
            <td class="italic"><bean:message key="label.aseConn.secondary" bundle="${bundle}" /></td>
            <td><html:text property="entity.aseFile.aseConn.secAseConnType.name" readonly="true" styleClass="text" /></td>
            <td><html:text property="entity.aseFile.aseConn.secRef" readonly="true" styleClass="text" /></td>
        </tr>
        <tr>
            <td class="jms-label" colspan="4">
                <c:set var="supplier" value="${fileForm.entity.aseFile.supplier}" />
                <c:set var="aseSupplierDetails">
                    <ul>${supplier.name}
                        <li>Contact: ${supplier.contact.fullName}</li>
                        <li>Phone: ${supplier.contact.phone}</li>
                    </ul>
                </c:set>
                <html:link styleId="aseSupplierLink" href="javascript:;" title="${aseSupplierDetails}">
                    <bean:message key="label.aseFile.supplier" bundle="${bundle}" />&#160;
                    <i>(<bean:message key="label.aseFile.supplier.tooltip" bundle="${bundle}" />)</i>
                </html:link>
            </td>
        </tr>
    </table>

</jsp:root>