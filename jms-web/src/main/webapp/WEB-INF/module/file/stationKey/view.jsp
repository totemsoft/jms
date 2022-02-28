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
        <caption>Station Details</caption>
        <tr>
            <td class="jms-label"><bean:message key="label.station.code" /></td>
            <td colspan="3"><html:text property="${beanPrefix}stationId" readonly="true" styleClass="text" /></td>
        </tr>
        <tr>
            <td class="jms-label"><bean:message key="label.station.name" /></td>
            <td colspan="3"><html:text property="${beanPrefix}name" readonly="true" styleClass="text" /></td>
        </tr>
        <tr>
            <td class="jms-label"><bean:message key="label.area" /></td>
            <td colspan="3"><html:text property="entity.fca.area.name" readonly="true" styleClass="text" /></td>
        </tr>
        <tr>
            <td class="jms-label"><bean:message key="label.region" /></td>
            <td colspan="3"><html:text property="entity.fca.region.name" readonly="true" styleClass="text" /></td>
        </tr>

        <c:if test="${not empty fileForm.entity.fca.station.contact}">
            <jsp:include page="/WEB-INF/entity/contact/view.jsp" />
        </c:if>

        <c:if test="${not empty fileForm.entity.fca.station.address}">
            <jsp:include page="/WEB-INF/entity/address/view.jsp" />
        </c:if>
    </table>

    <table class="nonDataTable">
        <caption>Key Receipt Details</caption>
        <tr>
            <td class="jms-label"><bean:message key="label.key.required" bundle="${bundle}" /></td>
            <td><html:checkbox property="entity.building.keyRequired" disabled="true" /></td>
        </tr>
        <tr>
            <td class="jms-label"><bean:message key="label.keyReceipt" bundle="${bundle}" /></td>
            <td><html:text property="entity.keyReceipt.keyReceiptNo" readonly="true" styleClass="text" /></td>
        </tr>
        <tr>
            <td colspan="2">
                <table class="dataTable">
                    <c:forEach items="${fileForm.entity.keyRegs}" var="item" varStatus="status">
                        <c:if test="${status.index eq 0}">
                            <th><bean:message key="label.keyReg.keyNo" bundle="${bundle}" /></th>
                            <th><bean:message key="label.keyReg.ownerKeyId" bundle="${bundle}" /></th>
                            <th><bean:message key="label.keyReg.lockType" bundle="${bundle}" /></th>
                            <th><bean:message key="label.keyReg.lockLocation" bundle="${bundle}" /></th>
                        </c:if>
                        <tr class="${status.index % 2 eq 0 ? 'odd' : 'even'}">
                            <td>${item.keyNo}</td>
                            <td>${item.ownerKeyId}</td>
                            <td>${item.lockType}</td>
                            <td>${item.lockLocation}</td>
                        </tr>
                    </c:forEach>
                </table>
            </td>
        </tr>
    </table>

</jsp:root>