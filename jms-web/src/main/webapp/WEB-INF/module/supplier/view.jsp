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

    <div class="jms-view-scroll">
    <html:form action="/viewSupplier" method="get">
        <table class="nonDataTable">
            <tr>
                <td class="jms-label"><bean:message key="label.supplier.supplierId" bundle="${bundle}" /></td>
                <td><html:text property="entity.supplierId" readonly="true" styleClass="text" /></td>
                <td class="jms-label"><bean:message key="label.supplier.supplierType" bundle="${bundle}" /></td>
                <td><html:text property="entity.supplierType.name" readonly="true" styleClass="text" /></td>
            </tr>
            <tr>
                <td class="jms-label"><bean:message key="label.supplier.name" bundle="${bundle}" /></td>
                <td><html:text property="entity.name" readonly="true" styleClass="text" /></td>
                <td class="jms-label"><bean:message key="label.supplier.legalName" bundle="${bundle}" /></td>
                <td><html:text property="entity.legalName" readonly="true" styleClass="text" /></td>
            </tr>
            <tr>
                <td class="jms-label"><bean:message key="label.supplier.abn" bundle="${bundle}" /></td>
                <td><html:text property="entity.abn" readonly="true" styleClass="text" /></td>
                <td class="jms-label"><bean:message key="label.region" /></td>
                <td><html:text property="entity.region.name" readonly="true" styleClass="text" /></td>
            </tr>
            <tr>
                <td colspan="2">
                    <table style="border:none;">
                        <caption><bean:message key="label.address" /></caption>
                        <jsp:include page="/WEB-INF/entity/address/view.jsp" />
                    </table>
                </td>
                <td colspan="2">
                    <table style="border:none;">
                        <caption><bean:message key="label.postAddress" /></caption>
                        <jsp:include page="/WEB-INF/entity/postAddress/view.jsp" />
                    </table>
                </td>
            </tr>
            <jsp:include page="/WEB-INF/entity/contact/view.jsp" />
            <tr>
                <td class="jms-label" colspan="1"><bean:message key="label.supplier.serviceDescription" bundle="${bundle}" /></td>
                <td class="jms-label" colspan="3"><html:textarea property="entity.serviceDescription" readonly="true" styleClass="textarea" /></td>
            </tr>
        </table>
    </html:form>
    </div>

</jsp:root>