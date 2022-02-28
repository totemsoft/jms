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
        <th class="width1em" title="Priority"></th>
        <c:choose>
            <c:when test="${buildingContactsProperty eq 'fireBuildingContacts'}">
                <th><bean:message key="label.company.name" /></th>
                <th>
                    <bean:message key="label.contact.phone" /> /<br/>
                    <bean:message key="label.contact.mobile" />
                </th>
            </c:when>
            <c:when test="${buildingContactsProperty eq 'securityBuildingContacts'}">
                <th><bean:message key="label.company.name" /></th>
                <th>
                    <bean:message key="label.contact.phone" /> /<br/>
                    <bean:message key="label.contact.mobile" />
                </th>
            </c:when>
            <c:otherwise>
                <th><bean:message key="label.contact.name" /></th>
                <th>
                    <bean:message key="label.contact.phone.daytime" /> /<br/>
                    <bean:message key="label.contact.phone.afterhours" />
                </th>
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
                <th title="Last Updated"><bean:message key="label.updatedDate" /></th>
            </c:otherwise>
        </c:choose>
        <!--th style="width: 17px;" title="Emergency Contact Form" /-->
        <th style="width: 17px;" title="Emergency Contact Active"><bean:message key="label.active" /></th>
        <c:set var="index" value="0" />
        <c:forEach items="${buildingContacts}" var="item">
            <tr class="${index % 2 eq 0 ? 'odd' : 'even'}${item.active ? '' : ' hidden'}">
                <td class="number"><c:if test="${item.priority gt 0}">${item.priority}0</c:if></td>
                <c:choose>
                    <c:when test="${buildingContactsProperty eq 'fireBuildingContacts'}">
                        <td>${item.company.name}</td>
                    </c:when>
                    <c:when test="${buildingContactsProperty eq 'securityBuildingContacts'}">
                        <td>${item.company.name}</td>
                    </c:when>
                    <c:otherwise>
                        <td>${item.contact.fullName}</td>
                    </c:otherwise>
                </c:choose>
                <td>${item.contact.phone}<br/>${item.contact.mobile}</td>
                <c:set var="lastDate" value="${item.lastDate}" />
                <c:set var="lastBy" value="${item.lastBy.contact.name}" />
                <c:if test="${item.contact.lastDate.time gt lastDate.time}">
                    <c:set var="lastDate" value="${item.contact.lastDate}" />
                    <c:set var="lastBy" value="${item.contact.lastBy.contact.name}" />
                </c:if>
                <c:if test="${item.company.lastDate.time gt lastDate.time}">
                    <c:set var="lastDate" value="${item.company.lastDate}" />
                    <c:set var="lastBy" value="${item.company.lastBy.contact.name}" />
                </c:if>
                 <c:choose>
                    <c:when test="${buildingContactsProperty eq 'fireBuildingContacts'}">
                        <td>${item.notes}</td>
                    </c:when>
                    <c:when test="${buildingContactsProperty eq 'securityBuildingContacts'}">
                        <td>${item.notes}</td>
                    </c:when>
                    <c:otherwise>
                        <fmt:formatDate var="lastTime" value="${lastDate}" pattern="${timePattern}" />
                        <td title="by ${lastBy} at ${lastTime}">
                            <fmt:formatDate value="${lastDate}" pattern="${datePattern}" />
                        </td>
                    </c:otherwise>
                </c:choose>
                <!--td>
                    <c:if test="${not empty item.document.documentId}">
                        <a title="Download Emergency Contact Form"
                            href="javascript:YAHOO.jms.sendDownloadRequest('file/downloadFileDoc.do?dispatch=downloadFileDoc&amp;documentId=${item.document.documentId}', null, 'Download Document');">
                            <span class="download" title="${item.document.name}" />
                        </a>
                    </c:if>
                </td-->
                <td>
                    <c:if test="${item.active}">
                        <span class="accept" title="Emergency Contact Active" />
                    </c:if>
                </td>
            </tr>
            <c:set var="index" value="${index + 1}" />
        </c:forEach>
    </table>

</jsp:root>