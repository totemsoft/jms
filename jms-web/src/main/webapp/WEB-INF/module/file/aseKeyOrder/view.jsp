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
            <td class="jms-label"><bean:message key="label.aseKeyOrder.orderNo" bundle="${bundle}" /></td>
            <td colspan="3"><input value="${entity.orderNo}" readonly="readonly" class="text" /></td>
        </tr>
        <tr>
            <fmt:formatDate var="receivedDate" value="${entity.receivedDate}" pattern="${datePattern}" />
            <td class="jms-label"><bean:message key="label.aseKeyOrder.receivedDate" bundle="${bundle}" /></td>
            <td colspan="3"><input value="${receivedDate}" readonly="readonly" class="text" /></td>
        </tr>
        <tr>
            <td class="jms-label"><bean:message key="label.aseKeyOrder.supplier" bundle="${bundle}" /></td>
            <td colspan="3"><input value="${entity.supplier.name}" readonly="readonly" class="text" /></td>
        </tr>

        <c:set var="beanPrefix" value="entity." scope="request" />
        <jsp:include page="/WEB-INF/entity/address/view.jsp" />
        <c:set var="beanPrefix" value="" scope="request" />

        <c:set var="beanPrefix" value="entity." scope="request" />
        <jsp:include page="/WEB-INF/entity/contact/view.jsp" />
        <c:set var="beanPrefix" value="" scope="request" />

        <tr><td colspan="4"><hr/></td></tr>
        <tr>
            <td class="jms-label"><bean:message key="label.aseKeyOrder.sentMethod" bundle="${bundle}" /></td>
            <td colspan="3"><input value="${entity.sentMethod.name}" readonly="readonly" class="text" /></td>
        </tr>
        <tr>
            <td class="jms-label"><bean:message key="label.aseKeyOrder.sentReferenceNo" bundle="${bundle}" /></td>
            <td colspan="3"><input value="${entity.sentReferenceNo}" readonly="readonly" class="text" /></td>
        </tr>
        <tr>
            <td class="jms-label"><bean:message key="label.aseKeyOrder.paymentMethod" bundle="${bundle}" /></td>
            <td colspan="3"><input value="${entity.paymentMethod.name}" readonly="readonly" class="text" /></td>
        </tr>
        <tr>
            <td class="jms-label"><bean:message key="label.aseKeyOrder.keyPrice" bundle="${bundle}" /></td>
            <td><input value="${entity.keyPrice}" readonly="readonly" class="text number" /></td>
            <td class="jms-label"><bean:message key="label.aseKeyOrder.postagePrice" bundle="${bundle}" /></td>
            <td><input value="${entity.postagePrice}" readonly="readonly" class="text number" /></td>
        </tr>
            <tr><td class="jms-label-caption" colspan="4"><bean:message key="label.aseKeyOrder.comment" bundle="${bundle}" /></td></tr>
            <tr>
                <td colspan="4">
                    <textarea class="height10">${entity.comment}</textarea>
                </td>
            </tr>
    </table>

</jsp:root>