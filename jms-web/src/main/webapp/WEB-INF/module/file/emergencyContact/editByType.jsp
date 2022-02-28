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

    <table class="dataTable">
        <tr>
             <th><bean:message key="label.contact.priority" /></th>
             <c:choose>
                 <c:when test="${buildingContactsProperty eq 'fireBuildingContacts'}">
                     <th colspan="3"><bean:message key="label.company.name" /></th>
                     <th><bean:message key="label.contact.phone" /></th>
                     <th><bean:message key="label.contact.mobile" /></th>
                 </c:when>
                 <c:when test="${buildingContactsProperty eq 'securityBuildingContacts'}">
                     <th colspan="3"><bean:message key="label.company.name" /></th>
                     <th><bean:message key="label.contact.phone" /></th>
                     <th><bean:message key="label.contact.mobile" /></th>
                 </c:when>
                 <c:otherwise>
                     <th><bean:message key="label.contact.salutation" /></th>
                     <th><bean:message key="label.contact.firstName" /></th>
                     <th><bean:message key="label.contact.surname" /></th>
                     <th><bean:message key="label.contact.phone.daytime" /></th>
                     <th><bean:message key="label.contact.phone.afterhours" /></th>
                 </c:otherwise>
             </c:choose>
             <c:choose>
                 <c:when test="${buildingContactsProperty eq 'fireBuildingContacts'}">
                     <th><bean:message key="label.contact.notes" /></th>
                 </c:when>
                 <c:when test="${buildingContactsProperty eq 'securityBuildingContacts'}">
                     <th><bean:message key="label.contact.notes" /></th>
                 </c:when>
                 <c:otherwise>
                     <th><bean:message key="label.contact.email" /></th>
                 </c:otherwise>
             </c:choose>
             <th title="Last Updated"><bean:message key="label.updatedDate" /></th>
             <th class="width2em" title="Set to Active"><bean:message key="label.active" /></th>
             <th class="width2em" title="Delete"><bean:message key="label.delete" /></th>
        </tr>
        <c:forEach items="${buildingContacts}" var="item" varStatus="status">
            <c:set var="index" value="${status.index}" />
            <html:errors property="${buildingContactsProperty}[${index}].priority" />
            <html:errors property="${buildingContactsProperty}[${index}].notes" />
            <html:errors property="${buildingContactsProperty}[${index}].company.name" />
            <html:errors property="${buildingContactsProperty}[${index}].contact.salutationTitle" />
            <html:errors property="${buildingContactsProperty}[${index}].contact.firstName" />
            <html:errors property="${buildingContactsProperty}[${index}].contact.surname" />
            <html:errors property="${buildingContactsProperty}[${index}].contact.phone" />
            <html:errors property="${buildingContactsProperty}[${index}].contact.mobile" />
            <html:errors property="${buildingContactsProperty}[${index}].contact.email" />
            <tr class="${index % 2 eq 0 ? 'odd' : 'even'}${item.active ? '' : ' disabled'}">
                <td class="number">
                    <html:select property="${buildingContactsProperty}[${index}].priority">
                        <html:options collection="${buildingContactsProperty}Priorities" property="key" labelProperty="value" />
                    </html:select>
                </td>
                <c:choose>
                    <c:when test="${buildingContactsProperty eq 'fireBuildingContacts'}">
                        <td colspan="3">
                            <html:text property="${buildingContactsProperty}[${index}].company.name" styleClass="text" />
                        </td>
                    </c:when>
                    <c:when test="${buildingContactsProperty eq 'securityBuildingContacts'}">
                        <td colspan="3">
                            <html:text property="${buildingContactsProperty}[${index}].company.name" styleClass="text" />
                        </td>
                    </c:when>
                    <c:otherwise>
                        <td>
                            <html:select property="${buildingContactsProperty}[${index}].contact.salutationTitle">
                                <!--option value=""><bean:message key="option.select" /></option-->
                                <html:options collection="salutations" property="salutation" labelProperty="salutation" />
                            </html:select>
                        </td>
                        <td>
                            <html:text property="${buildingContactsProperty}[${index}].contact.firstName" styleClass="text" />
                        </td>
                        <td>
                            <html:text property="${buildingContactsProperty}[${index}].contact.surname" styleClass="text" />
                        </td>
                    </c:otherwise>
                </c:choose>
                <td>
                    <html:text property="${buildingContactsProperty}[${index}].contact.phone" styleClass="text" />
                </td>
                <td>
                    <html:text property="${buildingContactsProperty}[${index}].contact.mobile" styleClass="text" />
                </td>
                <c:choose>
                    <c:when test="${buildingContactsProperty eq 'fireBuildingContacts'}">
                        <td>
                            <html:text property="${buildingContactsProperty}[${index}].notes" styleClass="text" />
                        </td>
                    </c:when>
                    <c:when test="${buildingContactsProperty eq 'securityBuildingContacts'}">
                        <td>
                            <html:text property="${buildingContactsProperty}[${index}].notes" styleClass="text" />
                        </td>
                    </c:when>
                    <c:otherwise>
                        <td>
                            <html:text property="${buildingContactsProperty}[${index}].contact.email" styleClass="text" />
                        </td>
                    </c:otherwise>
                </c:choose>
                <c:set var="lastDate" value="${item.lastDate}" />
                <c:set var="lastBy" value="${item.lastBy.contact.name}" />
                <fmt:formatDate var="lastTime" value="${lastDate}" pattern="${timePattern}" />
                <td title="${lastBy} at ${lastTime}">
                    <fmt:formatDate value="${lastDate}" pattern="${datePattern}" />
                </td>
                <td>
                    <c:set var="id" value="${buildingContactsProperty}${index}Active" />
                    <html:hidden property="${buildingContactsProperty}[${index}].active" styleId="${id}" />
                    <html:checkbox property="${buildingContactsProperty}[${index}].active" onchange="YUD.get('${id}').value=this.checked;" />
                </td>
                <td class="nowrap">
                    <html:link href="javascript:;" titleKey="link.buildingContact.remove" bundle="${bundle}"
                        onclick="YAHOO.jms.sendPostRequest(document.forms[0], document.forms[0].action + '?dispatch=removeBuildingContact&amp;${buildingContactType}=true&amp;index=${index}');">
                        <span class="remove">${nbsp}</span>
                        <!--bean:message key="link.remove" /-->
                    </html:link>
                </td>
            </tr>
            <!--tr class="${index % 2 eq 0 ? 'odd' : 'even'}${item.active ? '' : ' disabled'}">
                <th><bean:message key="label.document" /></th>
                <th colspan="3">
                    <c:if test="${not empty buildingContacts[index].document}">
                        <html:hidden property="${buildingContactsProperty}[${index}].document.documentId" />
                        <html:text property="${buildingContactsProperty}[${index}].document.name" styleClass="text" />
                    </c:if>
                </th>
                <th colspan="4">
                    <input type="file" id="uploadFile${attachmentIndex}" value="" size="70" name="attachmentFiles[${attachmentIndex}]" />
                </th>
                <c:set var="attachmentIndex" value="${attachmentIndex + 1}" scope="request" />
                <td class="nowrap">
                    <html:link href="javascript:;" titleKey="link.buildingContact.remove" bundle="${bundle}"
                        onclick="YAHOO.jms.sendPostRequest(document.forms[0], document.forms[0].action + '?dispatch=removeBuildingContact&amp;${buildingContactType}=true&amp;index=${index}');">
                        <bean:message key="link.remove" />
                    </html:link>
                </td>
            </tr-->
        </c:forEach>
        <tr>
            <th colspan="4" class="noborder">
                <html:link href="javascript:;" titleKey="link.buildingContact.add" bundle="${bundle}"
                    onclick="YAHOO.jms.sendPostRequest(document.forms[0], document.forms[0].action + '?dispatch=addBuildingContact&amp;${buildingContactType}=true');">
                    <bean:message key="link.buildingContact.add" bundle="${bundle}" />
                </html:link>
            </th>
            <th colspan="6" class="noborder">
                <c:if test="${copyBuildingContactDaytime eq 'true'}">
                    <html:link href="javascript:;" titleKey="link.buildingContact.add" bundle="${bundle}"
                        onclick="YAHOO.jms.sendPostRequest(document.forms[0], document.forms[0].action + '?dispatch=copyBuildingContactDaytime');">
                        <bean:message key="link.buildingContact.copy" bundle="${bundle}" />
                    </html:link>
                </c:if>
            </th>
        </tr>
    </table>

</jsp:root>