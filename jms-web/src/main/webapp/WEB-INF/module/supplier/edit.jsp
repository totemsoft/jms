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

    <c:set var="bundle" value="supplier" />

    <html:form action="/editSupplier">
        <html:hidden property="dispatch" value="save" />

        <div id="errors" class="error">
            <html:errors />
        </div>

        <table class="nonDataTable">
            <html:errors property="entity.supplierType.supplierTypeId" />
            <tr>
                <td class="jms-label"><bean:message key="label.supplier.supplierId" bundle="${bundle}" /></td>
                <td><html:text property="entity.supplierId" readonly="true" styleClass="text" /></td>
                <td class="jms-label"><bean:message key="label.supplier.active" bundle="${bundle}" /></td>
                <td>
                    <html:hidden property="entity.active"
                        styleId="active" />
                    <html:checkbox property="entity.active"
                        onchange="this.form.active.value=this.checked;" />
                </td>
            </tr>
            <tr>
                <td class="jms-label"><bean:message key="label.supplier.supplierType" bundle="${bundle}" /></td>
                <td>
                    <html:select property="entity.supplierType.supplierTypeId">
                        <option value=""><bean:message key="option.select" /></option>
                        <html:options collection="supplierTypes" property="supplierTypeId" labelProperty="name" />
                    </html:select>
                </td>
                <td class="jms-label"><bean:message key="label.region" /></td>
                <td>
                    <html:select property="entity.region.regionId">
                        <option value="0"><bean:message key="option.all" /></option>
                        <html:options collection="regions" property="regionId" labelProperty="name" />
                    </html:select>
                </td>
            </tr>
            <html:errors property="entity.name" />
            <tr>
                <td class="jms-label"><bean:message key="label.supplier.name" bundle="${bundle}" /></td>
                <td colspan="3"><html:text property="entity.name" styleClass="text" /></td>
            </tr>
            <html:errors property="entity.legalName" />
            <tr>
                <td class="jms-label"><bean:message key="label.supplier.legalName" bundle="${bundle}" /></td>
                <td colspan="3"><html:text property="entity.legalName" styleClass="text" /></td>
            </tr>
            <html:errors property="entity.abn" />
            <tr>
                <td class="jms-label"><bean:message key="label.supplier.abn" bundle="${bundle}" /></td>
                <td><html:text property="entity.abn" styleClass="text" /></td>
                <td colspan="2" />
            </tr>

            <tr>
                <td colspan="4">
                    <jsp:include page="/WEB-INF/entity/address/edit.jsp" />
                </td>
            </tr>

            <tr>
                <td colspan="4">
                    <jsp:include page="/WEB-INF/entity/postAddress/edit.jsp" />
                </td>
            </tr>

            <tr>
                <td colspan="4">
                    <jsp:include page="/WEB-INF/entity/contact/edit.jsp" />
                </td>
            </tr>

            <html:errors property="entity.serviceDescription" />
            <tr>
                <td class="jms-label" colspan="4"><bean:message key="label.supplier.serviceDescription" bundle="${bundle}" /></td>
            </tr>
            <tr>
                <td colspan="4"><html:textarea property="entity.serviceDescription" styleClass="textarea" /></td>
            </tr>
        </table>

    </html:form>

</jsp:root>