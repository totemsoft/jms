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

    <html:form action="/editFca">
        <html:hidden property="dispatch" value="showEditWarning" />

        <c:if test="${not empty fcaForm.entity.file.fileId}">
            <h2 class="jms-label" align="center">Change the Assignment of an FCA Number</h2>
            <hr/>
            <h2 class="bold warning">** WARNING **</h2>
            <br/>
            <h2 class="warning">YOU MUST BE VERY CAREFUL CHANGING THE</h2>
            <h2 class="warning">ASSIGNMENT OF AN FCA NUMBER TO A BUILDING</h2>
        </c:if>

        <div id="errors" class="error">
            <html:errors />
        </div>

        <table class="nonDataTable">
            <html:errors property="entity.fcaId" />
            <tr>
                <td class="jms-label"><bean:message key="label.fca.id" bundle="${bundle}" /></td>
                <td><html:text property="entity.fcaId" readonly="true" styleClass="text" /></td>
            </tr>
            <tr>
                <td class="jms-label"><bean:message key="label.file" /></td>
                <td>
                    <div>
                        <html:text styleId="fileId.input" property="entity.file.fileId" styleClass="" style="position: static;" />
                        <div id="fileId.input.container"></div>
                    </div>
                </td>
            </tr>
            <tr>
                <td class="jms-label"><bean:message key="label.fileStatus" /></td>
                <td>
                    <c:if test="${not empty fcaForm.entity.file.fileStatus}">
                        <html:text property="entity.file.fileStatus.name" readonly="true" styleClass="text" />
                    </c:if>
                </td>
            </tr>
            <tr>
                <td class="jms-label"><bean:message key="label.region" /></td>
                <td>
                    <c:if test="${not empty fcaForm.entity.region}">
                        <html:text property="entity.region.name" readonly="true" styleClass="text" />
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
            </tr>
            <tr>
                <td class="jms-label"><bean:message key="label.station" /></td>
                <td>
                    <c:if test="${not empty fcaForm.entity.station}">
                        <html:text property="entity.station.name" readonly="true" styleClass="text" />
                    </c:if>
                </td>
            </tr>
            <tr>
                <td class="jms-label"><bean:message key="label.building" /></td>
                <td>
                    <c:if test="${not empty fcaForm.entity.file.building}">
                        <html:text property="entity.file.building.name" readonly="true" styleClass="text" />
                    </c:if>
                </td>
            </tr>
            <c:if test="${not empty fcaForm.entity.file.fileId}">
                <tr>
                    <td class="jms-label"><bean:message key="label.fca.reason" bundle="${bundle}" /></td>
                    <td><html:textarea property="entity.comment" styleClass="textarea" /></td>
                </tr>
            </c:if>
        </table>
    </html:form>
</jsp:root>