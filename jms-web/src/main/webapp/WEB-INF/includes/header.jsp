<?xml version="1.0" encoding="UTF-8"?>
<jsp:root
    xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:html="http://struts.apache.org/tags-html"
    xmlns:bean="http://struts.apache.org/tags-bean"
    xmlns:logic="http://struts.apache.org/tags-logic"
    xmlns:c="http://java.sun.com/jsp/jstl/core"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    xmlns:decorator="http://www.opensymphony.com/sitemesh/decorator"
    version="2.0">

    <table cellspacing="0">
        <tr>
            <td width="100%">&#160;</td>
            <td align="right" nowrap="nowrap">
                <!--c:if test="${not empty pageContext.request.userPrincipal}"-->
                    <html:link module="/user" action="/logout.do">Logout</html:link>
                <!--/c:if-->
            </td>
        </tr>
        <!--c:if test="${not empty pageContext.request.userPrincipal}"-->
        <tr class="top_bar">
            <td nowrap="nowrap">
                <c:if test="${not empty file}">
                    <bean:message key="label.workGroup" />: ${file}
                </c:if>
            </td>
            <td nowrap="nowrap">Hi ${pageContext.request.userPrincipal.name}</td>
        </tr>
        <!--/c:if-->
        <tr>
            <c:set var="background"><bean:message key="background.orange" /></c:set>
            <td colspan="2" background="${background}">
                <img src="${background}" />
            </td>
        </tr>
    </table>

</jsp:root>