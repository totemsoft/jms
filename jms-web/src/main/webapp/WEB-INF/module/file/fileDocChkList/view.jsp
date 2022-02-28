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
                    <c:forEach items="${fileForm.entity.fileDocChkLists}" var="item" varStatus="status">
                        <c:if test="${status.index eq 0}">
                            <th class="width100"><bean:message key="label.fileDocChkList.name" bundle="${bundle}" /></th>
                            <th><bean:message key="label.fileDocChkList.docOnFile" bundle="${bundle}" /></th>
                        </c:if>
                        <tr class="${status.index % 2 eq 0 ? 'odd' : 'even'}">
                            <td>${item.docChkList.template.name}</td>
                            <td>
                                <c:if test="${item.docOnFile}">
                                    <span class="accept">&#160;</span>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </td>
        </tr>
    </table>

</jsp:root>