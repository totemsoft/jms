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

    <c:set var="bundle" value="setup" />

    <html:form action="/viewArea" method="get">
        <html:hidden property="entity.areaId" />
        <table class="nonDataTable">
            <tr>
                <td class="jms-label"><bean:message key="label.area.areaId" bundle="${bundle}" /></td>
                <td><html:text property="entity.areaId" readonly="true" styleClass="text" /></td>
                <td class="jms-label"><bean:message key="label.area.name" bundle="${bundle}" /></td>
                <td><html:text property="entity.name" readonly="true" styleClass="text" /></td>
            </tr>
            <tr>
                <td class="jms-label"><bean:message key="label.region" bundle="${bundle}" /></td>
                <td><html:text property="entity.region.regionId" readonly="true" styleClass="text" /></td>
                <td class="jms-label"></td>
                <td></td>
            </tr>
            <c:set var="contact" value="${areaForm.entity.contact}" />
            <c:set var="address" value="${areaForm.entity.address}" />
            <jsp:include page="/WEB-INF/entity/contact/view.jsp" />
            <jsp:include page="/WEB-INF/entity/address/view.jsp" />
        </table>
    </html:form>

</jsp:root>