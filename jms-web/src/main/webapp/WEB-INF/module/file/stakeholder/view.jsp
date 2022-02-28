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
            <td colspan="4">
                <table class="dataTable">
                    <c:forEach items="${stakeholders}" var="item" varStatus="status">
                        <c:if test="${status.index eq 0}">
                            <th><bean:message key="label.stakeholderType" /></th>
                            <th><bean:message key="label.contact.phone" /></th>
                            <th><bean:message key="label.contact.fax" /></th>
                            <th><bean:message key="label.contact.email" /></th>
                        </c:if>
                        <tr class="${status.index % 2 eq 0 ? 'odd' : 'even'}">
                            <td>${item.stakeHolderType.name}</td>
                            <td>${item.contact.phone}</td>
                            <td>${item.contact.fax}</td>
                            <td>${item.contact.email}</td>
                        </tr>
                    </c:forEach>
                </table>
            </td>
        </tr>
    </table>

</jsp:root>