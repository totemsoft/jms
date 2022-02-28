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

    <html:hidden styleId="${owner}.id" property="${owner}.ownerId" />
    <table class="nonDataTable">
        <tr>
            <td class="jms-label"><bean:message key="label.owner.legalName" bundle="${bundle}" /></td>
            <c:set var="modified" value="${auditOwner.legalName ne originalOwner.legalName}" />
            <td colspan="${defaultContact ? '2' : '3'}"><html:text property="${owner}.legalName" readonly="${readonly}" styleClass="text ${modified ? 'modified' : ''}" title="${not modified ? '' : originalOwner.legalName}" /></td>
            <c:if test="${defaultContact}">
                <td class="jms-label"><html:checkbox property="${owner}.defaultContact" disabled="true"><bean:message key="label.owner.defaultContact" bundle="${bundle}" /></html:checkbox></td>
            </c:if>
        </tr>
        <tr>
            <td class="jms-label"><bean:message key="label.owner.abn" bundle="${bundle}" /></td>
            <c:set var="modified" value="${auditOwner.abn ne originalOwner.abn}" />
            <td><html:text property="${owner}.abn" readonly="${readonly}" styleClass="text ${modified ? 'modified' : ''}" title="${not modified ? '' : originalOwner.abn}" /></td>
            <c:if test="${owner eq 'bodyCorporate'}">
                <td class="jms-label"><bean:message key="label.bodyCorporate.reference" bundle="${bundle}" /></td>
                <c:set var="modified" value="${auditOwner.reference ne originalOwner.reference}" />
                <td><html:text property="${owner}.reference" readonly="${readonly}" styleClass="text ${modified ? 'modified' : ''}" title="${not modified ? '' : originalOwner.reference}" /></td>
            </c:if>
        </tr>
        <c:if test="${owner eq 'bodyCorporate'}">
        <tr>
            <td class="jms-label"><bean:message key="label.bodyCorporate.management" bundle="${bundle}" /></td>
            <c:set var="modified" value="${auditOwner.management ne originalOwner.management}" />
            <td colspan="3"><html:text property="${owner}.management" readonly="${readonly}" styleClass="text ${modified ? 'modified' : ''}" title="${not modified ? '' : originalOwner.management}" /></td>
        </tr>
        </c:if>

        <tr>
            <td colspan="4"><html:hidden property="${owner}.contact.contactId" /><hr/></td>
        </tr>
        <tr>
            <td class="jms-label"><bean:message key="label.contact.salutation" /></td>
            <c:set var="modified" value="${auditOwner.contact.salutation.id ne originalOwner.contact.salutation.id}" />
            <td>
                <!--html:text property="${owner}.contact.salutation.salutation" readonly="${readonly}" styleClass="text ${modified ? 'modified' : ''}" title="${not modified ? '' : originalOwner.contact.salutation.salutation}" /-->
                <html:select property="${owner}.contact.salutation.salutation" disabled="${readonly}" styleClass="text ${modified ? 'modified' : ''}" title="${not modified ? '' : originalOwner.contact.salutation.salutation}">
                    <!--option value=""><bean:message key="option.select" /></option-->
                    <html:options collection="salutations" property="salutation" labelProperty="salutation" />
                </html:select>
            </td>
            <td class="jms-label"><bean:message key="label.contact.phone" /></td>
            <c:set var="modified" value="${auditOwner.contact.phone ne originalOwner.contact.phone}" />
            <td><html:text property="${owner}.contact.phone" readonly="${readonly}" styleClass="text ${modified ? 'modified' : ''}" title="${not modified ? '' : originalOwner.contact.phone}" /></td>
        </tr>
        <tr>
            <td class="jms-label"><bean:message key="label.contact.firstName" /></td>
            <c:set var="modified" value="${auditOwner.contact.firstName ne originalOwner.contact.firstName}" />
            <td><html:text property="${owner}.contact.firstName" readonly="${readonly}" styleClass="text ${modified ? 'modified' : ''}" title="${not modified ? '' : originalOwner.contact.firstName}" /></td>
            <td class="jms-label"><bean:message key="label.contact.fax" /></td>
            <c:set var="modified" value="${auditOwner.contact.fax ne originalOwner.contact.fax}" />
            <td><html:text property="${owner}.contact.fax" readonly="${readonly}" styleClass="text ${modified ? 'modified' : ''}" title="${not modified ? '' : originalOwner.contact.fax}" /></td>
        </tr>
        <tr>
            <td class="jms-label"><bean:message key="label.contact.surname" /></td>
            <c:set var="modified" value="${auditOwner.contact.surname ne originalOwner.contact.surname}" />
            <td><html:text property="${owner}.contact.surname" readonly="${readonly}" styleClass="text ${modified ? 'modified' : ''}" title="${not modified ? '' : originalOwner.contact.surname}" /></td>
            <td class="jms-label"><bean:message key="label.contact.mobile" /></td>
            <c:set var="modified" value="${auditOwner.contact.mobile ne originalOwner.contact.mobile}" />
            <td><html:text property="${owner}.contact.mobile" readonly="${readonly}" styleClass="text ${modified ? 'modified' : ''}" title="${not modified ? '' : originalOwner.contact.mobile}" /></td>
        </tr>
        <tr>
            <td class="jms-label"><bean:message key="label.contact.email" /></td>
            <c:set var="modified" value="${auditOwner.contact.email ne originalOwner.contact.email}" />
            <td colspan="3"><html:text property="${owner}.contact.email" readonly="${readonly}" styleClass="text ${modified ? 'modified' : ''}" title="${not modified ? '' : originalOwner.contact.email}" /></td>
        </tr>

        <tr>
            <td colspan="4"><html:hidden property="${owner}.address.addressId" /><hr/></td>
        </tr>
        <tr>
            <td class="jms-label"><bean:message key="label.address.addrLine1" /></td>
            <c:set var="modified" value="${auditOwner.address.addrLine1 ne originalOwner.address.addrLine1}" />
            <td colspan="3"><html:text property="${owner}.address.addrLine1" readonly="${readonly}" styleClass="text ${modified ? 'modified' : ''}" title="${not modified ? '' : originalOwner.address.addrLine1}" /></td>
        </tr>
        <tr>
            <td class="jms-label"><bean:message key="label.address.addrLine2" /></td>
            <c:set var="modified" value="${auditOwner.address.addrLine2 ne originalOwner.address.addrLine2}" />
            <td colspan="3"><html:text property="${owner}.address.addrLine2" readonly="${readonly}" styleClass="text ${modified ? 'modified' : ''}" title="${not modified ? '' : originalOwner.address.addrLine2}" /></td>
        </tr>
        <tr>
            <td class="jms-label"><bean:message key="label.address.suburb" /></td>
            <c:set var="modified" value="${auditOwner.address.suburb ne originalOwner.address.suburb}" />
            <td><html:text property="${owner}.address.suburb" readonly="${readonly}" styleClass="text ${modified ? 'modified' : ''}" title="${not modified ? '' : originalOwner.address.suburb}" /></td>
            <td class="jms-label"><bean:message key="label.address.postcode" /></td>
            <c:set var="modified" value="${auditOwner.address.postcode ne originalOwner.address.postcode}" />
            <td><html:text property="${owner}.address.postcode" readonly="${readonly}" styleClass="text ${modified ? 'modified' : ''}" title="${not modified ? '' : originalOwner.address.postcode}" /></td>
        </tr>
        <tr>
            <td class="jms-label"><bean:message key="label.address.state" /></td>
            <c:set var="modified" value="${auditOwner.address.state.state ne originalOwner.address.state.state}" />
            <td>
                <!--html:text property="${owner}.address.state.state" readonly="${readonly}" styleClass="text ${modified ? 'modified' : ''}" title="${not modified ? '' : originalOwner.address.state.state}" /-->
                <html:select property="${owner}.address.state.state" disabled="${readonly}" styleClass="text ${modified ? 'modified' : ''}" title="${not modified ? '' : originalOwner.address.state.state}">
                    <option value=""><bean:message key="option.select" /></option>
                    <html:options collection="states" property="state" labelProperty="state" />
                </html:select>
            </td>
            <td></td>
            <td></td>
        </tr>
    </table>

</jsp:root>