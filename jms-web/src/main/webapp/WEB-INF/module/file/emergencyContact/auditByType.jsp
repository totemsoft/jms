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
        <th class="width1em" title="Priority"><bean:message key="label.contact.priority" /></th>
        <c:choose>
            <c:when test="${buildingContactsProperty eq 'securityBuildingContacts'}">
                <th><bean:message key="label.company.name" /></th>
            </c:when>
            <c:otherwise>
                <th><bean:message key="label.contact.firstName" /></th>
                <th><bean:message key="label.contact.surname" /></th>
            </c:otherwise>
        </c:choose>
        <th>
            <bean:message key="label.contact.phone" />
        </th>
        <th style="width: 17px;" title="Emergency Contact Active" />
        <c:set var="index" value="0" />
        <c:forEach items="${buildingContacts}" var="item" varStatus="s">
            <c:set var="index" value="${s.index}" />
            <tr class="${index % 2 eq 0 ? 'odd' : 'even'}">
                <td class="number">${item.priority}0</td>
                <c:choose>
                    <c:when test="${buildingContactsProperty eq 'securityBuildingContacts'}">
                        <c:set var="originalItem" value="${original.securityBuildingContacts[index]}" />
                        <c:set var="modified" value="${item.company.name ne originalItem.company.name}" />
                        <td class="${modified ? 'modified' : ''}" title="${not modified ? '' : originalItem.company.name}">
                            <html:text property="${buildingContactsProperty}[${index}].company.name" styleClass="text" />
                        </td>
                        <c:set var="modified" value="${item.contact.phone ne originalItem.contact.phone}" />
                        <td class="${modified ? 'modified' : ''}" title="${not modified ? '' : originalItem.contact.phone}">
                            <html:text property="${buildingContactsProperty}[${index}].contact.phone" styleClass="text" />
                        </td>
                    </c:when>
                    <c:when test="${buildingContactsProperty eq 'ownerBuildingContacts'}">
                        <c:set var="originalItem" value="${original.ownerBuildingContacts[index]}" />
                        <c:set var="modified" value="${item.contact.firstName ne originalItem.contact.firstName}" />
                        <td class="${modified ? 'modified' : ''}" title="${not modified ? '' : originalItem.contact.firstName}">
                            <html:text property="${buildingContactsProperty}[${index}].contact.firstName" styleClass="text" />
                        </td>
                        <c:set var="modified" value="${item.contact.surname ne originalItem.contact.surname}" />
                        <td class="${modified ? 'modified' : ''}" title="${not modified ? '' : originalItem.contact.surname}">
                            <html:text property="${buildingContactsProperty}[${index}].contact.surname" styleClass="text" />
                        </td>
                        <c:set var="modified" value="${item.contact.phone ne originalItem.contact.phone}" />
                        <td class="${modified ? 'modified' : ''}" title="${not modified ? '' : originalItem.contact.phone}">
                            <html:text property="${buildingContactsProperty}[${index}].contact.phone" styleClass="text" />
                        </td>
                    </c:when>
                </c:choose>
                <td>
                    <c:if test="${item.active}">
                        <span class="accept" title="Emergency Contact Active" />
                    </c:if>
                </td>
            </tr>
        </c:forEach>
    </table>

</jsp:root>