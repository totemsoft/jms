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

    <html:form action="/editBuilding">
        <html:hidden property="dispatch" value="save" />

        <div id="errors" class="error">
            <html:errors />
        </div>

        <table class="nonDataTable">
            <html:errors property="building.name" />
            <tr>
                <td class="jms-label"><bean:message key="label.building.name" bundle="${bundle}" /></td>
                <td colspan="3">
                    <html:text property="building.name" styleClass="text" />
                </td>
            </tr>
            <tr>
                <td class="jms-label"><bean:message key="label.building.classification" bundle="${bundle}" /></td>
                <td colspan="3">
                    <table>
                        <c:forEach items="${fileForm.building.classifications}" var="item" varStatus="status">
                            <c:set var="index" value="${status.index}" />
                            <c:set var="editable" value="${empty item.id}" />
                            <tr class="${index % 2 eq 0 ? 'odd' : 'even'}">
                                <td>
                                    <c:if test="${not editable}">
                                        <html:hidden property="building.classifications[${index}].buildingClassificationId" />
                                    </c:if>
                                    <html:select property="building.classifications[${index}].buildingClassificationId" disabled="${not editable}">
                                        <option value=""><bean:message key="option.select" /></option>
                                        <html:options collection="buildingClassifications" property="id" labelProperty="name" />
                                    </html:select>
                                </td>
                                <td class="nowrap">
                                    <html:link href="javascript:;" titleKey="link.buildingClassification.remove" bundle="${bundle}"
                                        onclick="YAHOO.jms.sendPostRequest(document.forms[0], document.forms[0].action + '?dispatch=removeBuildingClassification&amp;index=${index}');">
                                        <bean:message key="link.remove" />
                                    </html:link>
                                </td>
                            </tr>
                        </c:forEach>
                        <tr>
                            <th colspan="2" class="noborder">
                                <html:link href="javascript:;" titleKey="link.buildingClassification.add" bundle="${bundle}"
                                    onclick="YAHOO.jms.sendPostRequest(document.forms[0], document.forms[0].action + '?dispatch=addBuildingClassification');">
                                    <bean:message key="link.buildingClassification.add" bundle="${bundle}" />
                                </html:link>
                            </th>
                        </tr>
                    </table>
                </td>
            </tr>
            <html:errors property="building.lotPlanNumber" />
            <tr>
                <td class="jms-label"><bean:message key="label.building.lotPlanNumber" bundle="${bundle}" /></td>
                <td colspan="3">
                    <html:text property="building.lotPlanNumber" styleClass="text" />
                </td>
            </tr>
            <html:errors property="building.description" />
            <tr>
                <td colspan="4" class="jms-label"><bean:message key="label.building.description" bundle="${bundle}" /></td>
            </tr>
            <tr>
                <td colspan="4"><html:textarea property="building.description" styleClass="textarea" /></td>
            </tr>
            <html:errors property="building.usage" />
            <tr>
                <td colspan="4" class="jms-label"><bean:message key="label.building.usage" bundle="${bundle}" /></td>
            </tr>
            <tr>
                <td colspan="4"><html:textarea property="building.usage" styleClass="textarea" /></td>
            </tr>

            <tr>
                <td colspan="4"><html:hidden property="building.address.addressId" /><hr/></td>
            </tr>
            <tr>
                <td class="jms-label"><bean:message key="label.address.addrLine1" /></td>
                <td colspan="3"><html:text property="building.address.addrLine1" styleClass="text" /></td>
            </tr>
            <tr>
                <td class="jms-label-optional"><bean:message key="label.address.addrLine2" /></td>
                <td colspan="3"><html:text property="building.address.addrLine2" styleClass="text" /></td>
            </tr>
            <tr>
                <td class="jms-label"><bean:message key="label.address.suburb" /></td>
                <td><html:text property="building.address.suburb" styleClass="text" /></td>
                <td class="jms-label-optional"><bean:message key="label.address.postcode" /></td>
                <td><html:text property="building.address.postcode" styleClass="text" /></td>
            </tr>
            <tr>
                <td class="jms-label"><bean:message key="label.address.state" /></td>
                <td>
                    <html:select property="building.address.state.state">
                        <option value=""><bean:message key="option.select" /></option>
                        <html:options collection="states" property="state" labelProperty="state" />
                    </html:select>
                </td>
                <td colspan="2" />
            </tr>
        </table>
    </html:form>

</jsp:root>