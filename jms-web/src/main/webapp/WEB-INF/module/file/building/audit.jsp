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
    <c:set var="building" value="${fileAuditForm.building}" />

    <table class="nonDataTable">
        <tr>
            <td class="jms-label"><bean:message key="label.building.name" bundle="${bundle}" /></td>
            <c:set var="modified" value="${building.name ne original.building.name}" />
            <td colspan="3">
                <html:text property="building.name" readonly="${readonly}" styleClass="text ${modified ? 'modified' : ''}" title="${not modified ? '' : original.building.name}" />
            </td>
        </tr>

        <tr>
            <td colspan="4"><html:hidden property="building.address.addressId" /><hr/></td>
        </tr>
        <tr>
            <td class="jms-label"><bean:message key="label.address.addrLine1" /></td>
            <c:set var="modified" value="${building.address.addrLine1 ne original.building.address.addrLine1}" />
            <td colspan="3"><html:text property="building.address.addrLine1" readonly="${readonly}" styleClass="text ${modified ? 'modified' : ''}" title="${not modified ? '' : original.building.address.addrLine1}" /></td>
        </tr>
        <tr>
            <td class="jms-label"><bean:message key="label.address.addrLine2" /></td>
            <c:set var="modified" value="${building.address.addrLine2 ne original.building.address.addrLine2}" />
            <td colspan="3"><html:text property="building.address.addrLine2" readonly="${readonly}" styleClass="text ${modified ? 'modified' : ''}" title="${not modified ? '' : original.building.address.addrLine2}" /></td>
        </tr>
        <tr>
            <td class="jms-label"><bean:message key="label.address.suburb" /></td>
            <c:set var="modified" value="${building.address.suburb ne original.building.address.suburb}" />
            <td><html:text property="building.address.suburb" readonly="${readonly}" styleClass="text ${modified ? 'modified' : ''}" title="${not modified ? '' : original.building.address.suburb}" /></td>
            <td class="jms-label"><bean:message key="label.address.postcode" /></td>
            <c:set var="modified" value="${building.address.postcode ne original.building.address.postcode}" />
            <td><html:text property="building.address.postcode" readonly="${readonly}" styleClass="text ${modified ? 'modified' : ''}" title="${not modified ? '' : original.building.address.postcode}" /></td>
        </tr>
        <tr>
            <td class="jms-label"><bean:message key="label.address.state" /></td>
            <c:set var="modified" value="${building.address.state.state ne original.building.address.state.state}" />
            <td>
                <!--html:text property="building.address.state.state" readonly="${readonly}" styleClass="text ${modified ? 'modified' : ''}" title="${not modified ? '' : original.building.address.state.state}" /-->
                <html:select property="building.address.state.state" disabled="${readonly}" styleClass="text ${modified ? 'modified' : ''}" title="${not modified ? '' : original.building.address.state.state}">
                    <option value=""><bean:message key="option.select" /></option>
                    <html:options collection="states" property="state" labelProperty="state" />
                </html:select>
            </td>
            <td colspan="2" />
        </tr>
        <tr>
            <td class="jms-label"><bean:message key="label.building.classification" bundle="${bundle}" /></td>
            <td>
                <c:set var="classifications" value="" />
                <c:forEach items="${fileForm.building.classifications}" var="item" varStatus="s">
                    <c:set var="classifications" value="${classifications}${s.first or (s.first and s.last) ? '' : ','}${item.name}" />
                </c:forEach>
                <input type="text" value="${classifications}" readonly="readonly" class="text" />
            </td>
            <td class="jms-label"><bean:message key="label.building.lotPlanNumber" bundle="${bundle}" /></td>
            <td>
                <c:set var="modified" value="${building.lotPlanNumber ne original.building.lotPlanNumber}" />
                <html:text property="building.lotPlanNumber" styleClass="text ${modified ? 'modified' : ''}" />
            </td>
        </tr>
    </table>

</jsp:root>