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

    <html:form action="/editStationKey">
        <html:hidden property="dispatch" value="save" />

        <div id="errors" class="error">
            <html:errors />
        </div>

        <table class="nonDataTable">
            <caption>Station Details</caption>

            <html:errors property="entity.fca.station.stationId" />
            <tr>
                <td class="jms-label"><bean:message key="label.station" /></td>
                <td colspan="3">
                    <html:select property="entity.fca.station.stationId"
                        onchange="YAHOO.jms.sendPostRequest(this.form, this.form.action + '?dispatch=changeStation');">
                        <option value=""><bean:message key="option.select" /></option>
                        <html:options collection="stations" property="stationId" labelProperty="name" />
                    </html:select>
                </td>
            </tr>
            <tr>
                <td class="jms-label"><bean:message key="label.station.name" /></td>
                <td colspan="3">
                    <html:text property="entity.fca.station.name" readonly="true" styleClass="text" />
                </td>
            </tr>
            <tr>
                <td class="jms-label"><bean:message key="label.area" /></td>
                <td colspan="3">
                    <html:text property="entity.fca.area.name" readonly="true" styleClass="text" />
                </td>
            </tr>
            <tr>
                <td class="jms-label"><bean:message key="label.region" /></td>
                <td colspan="3">
                    <html:text property="entity.fca.region.name" readonly="true" styleClass="text" />
                </td>
            </tr>

            <c:if test="${not empty fileForm.entity.fca.station.contact}">
            <tr>
                <td colspan="4"><html:hidden property="entity.fca.station.contact.contactId" /><hr/></td>
            </tr>
            <tr>
                <td class="jms-label"><bean:message key="label.contact.phone" /></td>
                <td><html:text property="entity.fca.station.contact.phone" readonly="true" styleClass="text" /></td>
                <td class="jms-label"><bean:message key="label.contact.fax" /></td>
                <td><html:text property="entity.fca.station.contact.fax" readonly="true" styleClass="text" /></td>
            </tr>
            <tr>
                <td class="jms-label"><bean:message key="label.contact.mobile" /></td>
                <td><html:text property="entity.fca.station.contact.mobile" readonly="true" styleClass="text" /></td>
                <td colspan="2" />
            </tr>
            </c:if>

            <c:if test="${not empty fileForm.entity.fca.station.address}">
            <tr>
                <td colspan="4"><html:hidden property="entity.fca.station.address.addressId" /><hr/></td>
            </tr>
            <tr>
                <td class="jms-label"><bean:message key="label.address.addrLine1" /></td>
                <td colspan="3"><html:text property="entity.fca.station.address.addrLine1" readonly="true" styleClass="text" /></td>
            </tr>
            <tr>
                <td class="jms-label"><bean:message key="label.address.addrLine2" /></td>
                <td colspan="3"><html:text property="entity.fca.station.address.addrLine2" readonly="true" styleClass="text" /></td>
            </tr>
            <tr>
                <td class="jms-label"><bean:message key="label.address.suburb" /></td>
                <td><html:text property="entity.fca.station.address.suburb" readonly="true" styleClass="text" /></td>
                <td class="jms-label"><bean:message key="label.address.postcode" /></td>
                <td><html:text property="entity.fca.station.address.postcode" readonly="true" styleClass="text" /></td>
            </tr>
            <tr>
                <td class="jms-label"><bean:message key="label.address.state" /></td>
                <td><html:text property="entity.fca.station.address.state.state" readonly="true" styleClass="text" /></td>
                <td></td>
                <td></td>
            </tr>
            </c:if>
        </table>

        <table class="nonDataTable">
            <caption>Key Receipt Details</caption>
            <html:errors property="entity.building.id" />
            <tr>
                <td class="jms-label"><bean:message key="label.key.required" bundle="${bundle}" /></td>
                <td>
                    <html:hidden property="entity.building.keyRequired" styleId="keyRequired" />
                    <html:checkbox property="entity.building.keyRequired"
                        onchange="this.form.keyRequired.value=this.checked;" />
                </td>
            </tr>
            <html:errors property="entity.keyReceipt.keyReceiptNo" />
            <tr>
                <td class="jms-label"><bean:message key="label.keyReceipt" bundle="${bundle}" /></td>
                <td><html:text property="entity.keyReceipt.keyReceiptNo" styleClass="text" /></td>
            </tr>
            <tr>
                <td colspan="2">
                    <table class="dataTable">
                        <tr>
                            <th><bean:message key="label.keyReg.keyNo" bundle="${bundle}" /></th>
                            <th><bean:message key="label.keyReg.ownerKeyId" bundle="${bundle}" /></th>
                            <th><bean:message key="label.keyReg.lockType" bundle="${bundle}" /></th>
                            <th><bean:message key="label.keyReg.lockLocation" bundle="${bundle}" /></th>
                            <th></th>
                        </tr>
                        <c:set var="count" value="${fn:length(fileForm.entity.keyRegs)}" />
                        <c:if test="${count eq 0}">
                            <tr>
                                <td colspan="4"></td>
                                <td class="nowrap">
                                    <html:link href="javascript:;" titleKey="link.keyReg.add" bundle="${bundle}"
                                        onclick="YAHOO.jms.sendPostRequest(document.forms[0], document.forms[0].action + '?dispatch=addKeyReg');">
                                        <bean:message key="link.add" />
                                    </html:link>
                                </td>
                            </tr>
                        </c:if>
                        <c:if test="${count gt 0}">
                            <c:forEach begin="0" end="${count - 1}" varStatus="status">
                                <c:set var="index" value="${status.index}" />
                                <html:errors property="entity.keyRegs[${index}].keyNo" />
                                <html:errors property="entity.keyRegs[${index}].ownerKeyId" />
                                <html:errors property="entity.keyRegs[${index}].lockType" />
                                <html:errors property="entity.keyRegs[${index}].lockLocation" />
                                <tr class="${index % 2 eq 0 ? 'odd' : 'even'}">
                                    <td>
                                        <html:text property="entity.keyRegs[${index}].keyNo" styleClass="text" />
                                    </td>
                                    <td>
                                        <html:text property="entity.keyRegs[${index}].ownerKeyId" styleClass="text" />
                                    </td>
                                    <td>
                                        <html:text property="entity.keyRegs[${index}].lockType" styleClass="text" />
                                    </td>
                                    <td>
                                        <html:text property="entity.keyRegs[${index}].lockLocation" styleClass="text" />
                                    </td>
                                    <td class="nowrap">
                                        <html:link href="javascript:;" titleKey="link.keyReg.remove" bundle="${bundle}"
                                            onclick="YAHOO.jms.sendPostRequest(document.forms[0], document.forms[0].action + '?dispatch=removeKeyReg&amp;index=${index}');">
                                            <bean:message key="link.remove" />
                                        </html:link>
                                        <c:if test="${status.last}">
                                            &#160;
                                            <html:link href="javascript:;" titleKey="link.keyReg.add" bundle="${bundle}"
                                                onclick="YAHOO.jms.sendPostRequest(document.forms[0], document.forms[0].action + '?dispatch=addKeyReg');">
                                                <bean:message key="link.add" />
                                            </html:link>
                                        </c:if>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:if>
                    </table>
                </td>
            </tr>
        </table>

    </html:form>

</jsp:root>