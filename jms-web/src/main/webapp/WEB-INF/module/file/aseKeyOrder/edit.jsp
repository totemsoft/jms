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
        <html:hidden property="dispatch" value="save" />
        <html:hidden property="entity.aseKeyOrderId" />
        <input type="hidden" id="reloadOnChangeUrl" value="dispatch=reload" />
        <input type="hidden" id="title" value="${title}" />

        <div id="errors" class="error">
            <html:errors />
        </div>

        <table class="nonDataTable">
            <tr>
                <td class="jms-label"><bean:message key="label.aseKeyOrder.orderNo" bundle="${bundle}" /></td>
                <td><html:text property="entity.orderNo" styleClass="text" readonly="true" /></td>
                <fmt:formatDate var="receivedDate" value="${aseKeyOrderForm.entity.receivedDate}" pattern="${datePattern}" />
                <td class="jms-label"><bean:message key="label.aseKeyOrder.receivedDate" bundle="${bundle}" /></td>
                <td><html:text property="entity.receivedDate" value="${receivedDate}" styleClass="text" styleId="f_date_receivedDate" /></td>
            </tr>
            <tr>
                <td class="jms-label"><bean:message key="label.aseKeyOrder.supplier" bundle="${bundle}" /></td>
                <td colspan="3">
                    <div>
                        <html:hidden property="entity.supplier.supplierId" styleId="supplierId.edit.value" />
                        <html:text property="entity.supplier.name" styleId="supplierId.edit" />
                        <div id="supplierId.edit.container"></div>
                    </div>
                </td>
            </tr>

            <tr><td colspan="4"><hr/></td></tr>
            <c:set var="beanPrefix" value="entity." scope="request" />
            <jsp:include page="/WEB-INF/entity/address/edit-tr.jsp" />
            <c:set var="beanPrefix" value="" scope="request" />

            <tr><td colspan="4"><hr/></td></tr>
            <c:set var="beanPrefix" value="entity." scope="request" />
            <jsp:include page="/WEB-INF/entity/contact/edit-tr.jsp" />
            <c:set var="beanPrefix" value="" scope="request" />

            <tr><td colspan="4"><hr/></td></tr>
            <tr>
                <td class="jms-label"><bean:message key="label.aseKeyOrder.sentMethod" bundle="${bundle}" /></td>
                <td>
                    <html:select property="entity.sentMethod">
                        <html:options collection="sentMethods" property="code" labelProperty="name" />
                    </html:select>
                </td>
                <td class="jms-label"><bean:message key="label.aseKeyOrder.sentReferenceNo" bundle="${bundle}" /></td>
                <td><html:text property="entity.sentReferenceNo" styleClass="text" /></td>
            </tr>
            <tr>
                <td class="jms-label"><bean:message key="label.aseKeyOrder.paymentMethod" bundle="${bundle}" /></td>
                <td>
                    <html:select property="entity.paymentMethod">
                        <option value=""><bean:message key="option.select" /></option>
                        <html:options collection="paymentMethods" property="code" labelProperty="name" />
                    </html:select>
                </td>
                <td colspan="2" />
            </tr>
            <tr>
                <td class="jms-label"><bean:message key="label.aseKeyOrder.keyPrice" bundle="${bundle}" /></td>
                <td>
                    <html:select property="entity.keyPrice">
                        <html:options collection="keyPrices" property="keyPrice" labelProperty="keyPriceName" />
                    </html:select>
                </td>
                <td class="jms-label"><bean:message key="label.aseKeyOrder.postagePrice" bundle="${bundle}" /></td>
                <td><html:text property="entity.postagePrice" styleClass="text number" /></td>
            </tr>
            <tr><td class="jms-label-caption" colspan="4"><bean:message key="label.aseKeyOrder.comment" bundle="${bundle}" /></td></tr>
            <tr>
                <td colspan="4">
                    <html:textarea property="entity.comment" styleClass="height10" />
                </td>
            </tr>
        </table>
    </html:form>

</jsp:root>