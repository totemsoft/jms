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
    <c:set var="building" value="${fileForm.building}" />

    <table class="nonDataTable">
        <tr>
            <td class="jms-label"><bean:message key="label.building.name" bundle="${bundle}" /></td>
            <td colspan="3"><html:text property="building.name" readonly="true" styleClass="text" /></td>
        </tr>

        <c:set var="beanPrefix" value="building." scope="request" />
        <jsp:include page="/WEB-INF/entity/address/view.jsp" />
        <c:set var="beanPrefix" value="" scope="request" />
        <tr>
            <td class="jms-label"><bean:message key="label.building.classification" bundle="${bundle}" /></td>
            <td>
                <c:set var="classifications" value="" />
                <c:forEach items="${building.classifications}" var="item" varStatus="s">
                    <c:set var="classifications" value="${classifications}${s.first or (s.first and s.last) ? '' : ','}${item.name}" />
                </c:forEach>
                <input type="text" value="${classifications}" readonly="readonly" class="text" />
            </td>
            <td class="jms-label"><bean:message key="label.building.lotPlanNumber" bundle="${bundle}" /></td>
            <td>
                <input type="text" value="${building.lotPlanNumber}" readonly="readonly" class="text" />
            </td>
        </tr>
        <tr>
            <td colspan="4" class="jms-label">
                <bean:message key="label.building.description" bundle="${bundle}" />
            </td>
        </tr>
        <tr>
            <td colspan="4">
                <html:textarea property="building.description" readonly="true" style="height: auto;min-height: 5em;" />
            </td>
        </tr>
        <tr>
            <td colspan="4" class="jms-label">
                <bean:message key="label.building.usage" bundle="${bundle}" />
            </td>
        </tr>
        <tr>
            <td colspan="4">
                <html:textarea property="building.usage" readonly="true" style="height: auto;min-height: 5em;" />
            </td>
        </tr>

        <c:if test="${not empty building.escadBuilding}">
        <tr><td colspan="4"><hr/></td></tr>
        <tr><td colspan="4" class="jms-label-caption"><bean:message key="label.building.escad" bundle="${bundle}" /></td></tr>
        <tr>
            <td class="jms-label"><bean:message key="label.building.name" bundle="${bundle}" /></td>
            <td colspan="3"><input value="${building.escadBuilding.name}" readonly="readonly" class="text" /></td>
        </tr>
        <tr>
            <td colspan="4"><input type="hidden" value="${building.escadBuilding.address.addressId}" /></td>
        </tr>
        <tr>
            <td class="jms-label"><bean:message key="label.address" /></td>
            <td colspan="3"><textarea readonly="readonly" class="height5">${building.escadBuilding.address.fullAddress}</textarea></td>
        </tr>
        </c:if>

        <c:if test="${not empty building.mastermindBuilding}">
        <tr><td colspan="4"><hr/></td></tr>
        <tr><td colspan="4" class="jms-label-caption"><bean:message key="label.building.mastermind" bundle="${bundle}" /></td></tr>
        <tr>
            <td class="jms-label"><bean:message key="label.building.name" bundle="${bundle}" /></td>
            <td colspan="3"><input value="${building.mastermindBuilding.name}" readonly="readonly" class="text" /></td>
        </tr>
        <tr>
            <td colspan="4"><input type="hidden" value="${building.mastermindBuilding.address.addressId}" /></td>
        </tr>
        <tr>
            <td class="jms-label"><bean:message key="label.address" /></td>
            <td colspan="3"><textarea readonly="readonly" class="height5">${building.mastermindBuilding.address.fullAddress}</textarea></td>
        </tr>
        </c:if>
    </table>

</jsp:root>