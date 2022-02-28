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
        <tr>
            <td class="jms-label"><bean:message key="label.sapHeader.sapCustNo" bundle="${bundle}" /></td>
            <td><html:text property="entity.sapHeader.sapCustNo" readonly="true" styleClass="text numberInput" /></td>
        </tr>
        <tr>
            <td colspan="2">
                <table class="dataTable">
                    <caption>RFI Details</caption>
                    <c:forEach items="${fileForm.entity.rfis}" var="item" varStatus="status">
                        <c:if test="${status.index eq 0}">
                            <th><bean:message key="label.rfi.rfiId" bundle="${bundle}" /></th>
                            <th><bean:message key="label.rfi.fileAction" bundle="${bundle}" /></th>
                            <th><bean:message key="label.rfi.description" bundle="${bundle}" /></th>
                            <th><bean:message key="label.rfi.sapInvNo" bundle="${bundle}" /></th>
                        </c:if>
                        <tr class="${status.index % 2 eq 0 ? 'odd' : 'even'}">
                            <td>${item.rfiId}</td>
                            <td>${item.fileAction.actionCode.code}</td>
                            <td>${item.description}</td>
                            <td>${item.sapInvNo}</td>
                        </tr>
                    </c:forEach>
                </table>
            </td>
        </tr>
    </table>

</jsp:root>