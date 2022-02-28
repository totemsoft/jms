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

    <html:form action="/editFcaAse">
        <html:hidden property="dispatch" value="save" />

        <div id="errors" class="error">
            <html:errors />
        </div>

        <table class="nonDataTable">
            <tr>
                <td class="jms-label"><bean:message key="label.fca" /></td>
                <td><html:text property="entity.fca.fcaId" readonly="true" styleClass="text" /></td>
                <td class="jms-label"><bean:message key="label.file.siteType" bundle="${bundle}" /></td>
                <td>
                    <html:select property="entity.siteType.siteTypeId">
                        <option value=""><bean:message key="option.select" /></option>
                        <html:options collection="siteTypes" property="id" labelProperty="name" />
                    </html:select>
                </td>
            </tr>
            <html:errors property="entity.aseFile.aseSerialNo" />
            <html:errors property="entity.aseFile.aseType.aseTypeId" />
            <tr>
                <td class="jms-label"><bean:message key="label.aseFile.aseSerialNo" bundle="${bundle}" /></td>
                <td><html:text property="entity.aseFile.aseSerialNo" styleClass="text" /></td>
                <td class="jms-label"><bean:message key="label.aseFile.aseType" bundle="${bundle}" /></td>
                <td>
                    <html:select property="entity.aseFile.aseType.aseTypeId">
                        <option value=""><bean:message key="option.select" /></option>
                        <html:options collection="aseTypes" property="aseTypeId" labelProperty="name" />
                    </html:select>
                </td>
            </tr>
            <tr>
                <td class="jms-label" colspan="2"><bean:message key="label.aseConn" /></td>
                <td class="italic"><bean:message key="label.aseConn.aseConnType" bundle="${bundle}" /></td>
                <td class="italic"><bean:message key="label.aseConn.reference" bundle="${bundle}" /></td>
            </tr>
            <html:errors property="entity.aseFile.aseConn.aseConnType.aseConnTypeId" />
            <html:errors property="entity.aseFile.aseConn.priRef" />
            <tr>
                <td class="jms-label"></td>
                <td class="italic"><bean:message key="label.aseConn.primary" bundle="${bundle}" /></td>
                <td>
                    <html:select property="entity.aseFile.aseConn.aseConnType.aseConnTypeId">
                        <option value=""><bean:message key="option.select" /></option>
                        <html:options collection="aseConnTypes" property="aseConnTypeId" labelProperty="name" />
                    </html:select>
                </td>
                <td><html:text property="entity.aseFile.aseConn.priRef" styleClass="text" /></td>
            </tr>
            <html:errors property="entity.aseFile.aseConn.secAseConnType.aseConnTypeId" />
            <html:errors property="entity.aseFile.aseConn.secRef" />
            <tr>
                <td class="jms-label"></td>
                <td class="italic"><bean:message key="label.aseConn.secondary" bundle="${bundle}" /></td>
                <td>
                    <html:select property="entity.aseFile.aseConn.secAseConnType.aseConnTypeId">
                        <option value=""><bean:message key="option.select" /></option>
                        <html:options collection="aseConnTypes" property="aseConnTypeId" labelProperty="name" />
                    </html:select>
                </td>
                <td><html:text property="entity.aseFile.aseConn.secRef" styleClass="text" /></td>
            </tr>
            <html:errors property="entity.aseFile.supplier.supplierId" />
            <tr>
                <td class="jms-label"><bean:message key="label.aseFile.supplier" bundle="${bundle}" /></td>
                <td class="jms-label" colspan="3">
                    <html:select property="entity.aseFile.supplier.supplierId">
                        <option value=""><bean:message key="option.select" /></option>
                        <html:options collection="suppliers" property="supplierId" labelProperty="name" />
                    </html:select>
                </td>
            </tr>
        </table>
    </html:form>

</jsp:root>