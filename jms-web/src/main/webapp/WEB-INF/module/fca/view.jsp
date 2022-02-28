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

    <c:set var="bundle" value="fca" />

    <html:form action="/viewFca" method="get">
        <table class="nonDataTable">
            <tr>
                <td class="jms-label"><bean:message key="label.fca.id" bundle="${bundle}" /></td>
                <td><html:text property="entity.fcaId" readonly="true" styleClass="text" /></td>
                <td class="jms-label"><bean:message key="label.region" /></td>
                <td>
                    <c:if test="${not empty fcaForm.entity.region}">
                        <html:text property="entity.region.name" readonly="true" styleClass="text" />
                    </c:if>
                </td>
            </tr>
            <tr>
                <td class="jms-label"><bean:message key="label.file" /></td>
                <td>
                    <c:if test="${not empty fcaForm.entity.file.id}">
                        <html:text property="entity.file.fileId" readonly="true" styleClass="text" />
                    </c:if>
                </td>
                <td class="jms-label"><bean:message key="label.fileStatus" /></td>
                <td>
                    <c:if test="${not empty fcaForm.entity.file.id and not empty fcaForm.entity.file.fileStatus}">
                        <html:text property="entity.file.fileStatus.name" readonly="true" styleClass="text" />
                    </c:if>
                </td>
            </tr>
            <tr>
                <td class="jms-label"><bean:message key="label.area" /></td>
                <td>
                    <c:if test="${not empty fcaForm.entity.area}">
                        <html:text property="entity.area.name" readonly="true" styleClass="text" />
                    </c:if>
                </td>
                <td class="jms-label"><bean:message key="label.station" /></td>
                <td>
                    <c:if test="${not empty fcaForm.entity.station}">
                        <html:text property="entity.station.name" readonly="true" styleClass="text" />
                    </c:if>
                </td>
            </tr>
            <tr>
                <td class="jms-label"><bean:message key="label.building" /></td>
                <td colspan="3">
                    <c:if test="${not empty fcaForm.entity.file.id and not empty fcaForm.entity.file.building}">
                        <html:text property="entity.file.building.name" readonly="true" styleClass="text" />
                    </c:if>
                </td>
            </tr>
        </table>
    </html:form>

</jsp:root>