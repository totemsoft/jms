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
        <caption>Change Over Schedule</caption>
        <tr>
            <c:set var="date"><fmt:formatDate value="${fileForm.entity.aseFile.aseChange.dateChange}" pattern="${datePattern}"/></c:set>
            <c:set var="time"><fmt:formatDate value="${fileForm.entity.aseFile.aseChange.dateChange}" pattern="${timePattern}"/></c:set>
            <td class="jms-label"><bean:message key="label.date" /></td>
            <td>${date}</td>
            <td class="jms-label"><bean:message key="label.time" /></td>
            <td>${time}</td>
        </tr>
    </table>

    <table class="nonDataTable">
        <caption>ASE Installation</caption>
        <tr>
            <td>
                <table class="dataTable">
                    <c:set var="index" value="0" />
                    <c:forEach items="${fileForm.entity.aseFile.aseChange.aseChangeSuppliers}" var="item">
                        <c:if test="${item.supplier.aseInstallation}">
                            <c:if test="${index eq 0}">
                                <th><bean:message key="label.supplier" /></th>
                                <th><bean:message key="label.aseConnType" /></th>
                                <th class="width100"><bean:message key="label.aseChangeSupplier.notation" bundle="${bundle}" /></th>
                                <th><bean:message key="label.aseChangeSupplier.dateCompleted" bundle="${bundle}" /></th>
                            </c:if>
                            <tr class="${index % 2 eq 0 ? 'odd' : 'even'}">
                                <td>${item.supplier.name}</td>
                                <td>${item.aseConnType.name}</td>
                                <td>${item.notation}</td>
                                <td>
                                    <c:if test="${empty item.dateCompleted}">
                                        <span class="attention">&#160;</span>
                                    </c:if>
                                    <c:if test="${not empty item.dateCompleted}">
                                        <fmt:formatDate value="${item.dateCompleted}" pattern="${datePattern}" />
                                    </c:if>
                                </td>
                            </tr>
                            <c:set var="index" value="${index + 1}" />
                        </c:if>
                    </c:forEach>
                </table>
            </td>
        </tr>
    </table>

    <table class="nonDataTable">
        <caption>Carrier Installation</caption>
        <tr>
            <td>
                <table class="dataTable">
                    <c:set var="index" value="0" />
                    <c:forEach items="${fileForm.entity.aseFile.aseChange.aseChangeSuppliers}" var="item">
                        <c:if test="${item.supplier.telco}">
                            <c:if test="${index eq 0}">
                                <th><bean:message key="label.supplier" /></th>
                                <th><bean:message key="label.aseConnType" /></th>
                                <th class="width100"><bean:message key="label.aseChangeSupplier.notation" bundle="${bundle}" /></th>
                                <th><bean:message key="label.aseChangeSupplier.dateCompleted" bundle="${bundle}" /></th>
                            </c:if>
                            <tr class="${index % 2 eq 0 ? 'odd' : 'even'}">
                                <td>${item.supplier.name}</td>
                                <td>${item.aseConnType.name}</td>
                                <td>${item.notation}</td>
                                <td>
                                    <c:if test="${empty item.dateCompleted}">
                                        <span class="attention">&#160;</span>
                                    </c:if>
                                    <c:if test="${not empty item.dateCompleted}">
                                        <fmt:formatDate value="${item.dateCompleted}" pattern="${datePattern}" />
                                    </c:if>
                                </td>
                            </tr>
                            <c:set var="index" value="${index + 1}" />
                        </c:if>
                    </c:forEach>
                </table>
            </td>
        </tr>
    </table>

</jsp:root>