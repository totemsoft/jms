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
            <td>
                <table class="dataTable">
                    <c:forEach items="${fileForm.entity.mailRegisters}" var="item" varStatus="status">
                        <c:if test="${status.first}">
                            <th class="width1em"></th>
                            <th><bean:message key="label.mail.date" bundle="${bundle}" /></th>
                            <th><bean:message key="label.contact" /></th>
                            <th><bean:message key="label.address" /></th>
                            <th class="width1em"></th>
                        </c:if>
                        <tr class="${status.index % 2 eq 0 ? 'odd' : 'even'}">
                            <td class="${item.mailIn ? 'arrow-right-blue' : 'arrow-left-green'}" title="${item.mailIn ? 'Incoming' : 'Outgoing'}">${nbsp}</td>
                            <td><fmt:formatDate value="${item.date}" pattern="${datePattern}" /></td>
                            <td>${item.contact.name}</td>
                            <td>${item.address.fullAddress}</td>
                            <td>
                                <a title="Edit Mail Register"
                                    href="javascript:YAHOO.jms.sendGetRequest('file/mailRegister.do?dispatch=${item.mailIn ? 'editMailIn' : 'editMailOut'}&amp;id=${item.id}', null, 'Mail Register');">
                                    <span class="edit">&#160;</span>
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </td>
        </tr>
    </table>

</jsp:root>