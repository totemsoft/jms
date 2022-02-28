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

    <html:form action="/isolation.do?dispatch=save">
        <html:hidden property="entity.isolationHistoryId" />

        <div id="errors" class="error">
            <html:errors />
        </div>

        <table class="nonDataTable">
            <html:errors property="entity.fca.file.fileId" />
            <html:errors property="entity.fca.fcaId" />
            <tr>
                <td class="jms-label"><bean:message key="label.fca" /></td>
                <td>
                    <div>
                        <html:text property="entity.fca.fcaId" readonly="${not empty isolationHistoryForm.entity.fca.fcaId}" styleId="fcaId.edit" styleClass="text" />
                        <div id="fcaId.edit.container"></div>
                    </div>
                </td>
                <td class="jms-label"><bean:message key="label.file" /></td>
                <td>
                    <html:text property="entity.fca.file.fileId" readonly="true" disabled="true" styleId="fcaId.edit.value" styleClass="text" />
                </td>
            </tr>
            <html:errors property="entity.csNumber" />
            <html:errors property="entity.input" />
            <tr>
                <td class="jms-label"><bean:message key="label.isolation.csNumber" bundle="${bundle}" /></td>
                <td><html:text property="entity.csNumber" styleClass="text" /></td>
                <td class="jms-label"><bean:message key="label.isolation.input" bundle="${bundle}" /></td>
                <td><html:text property="entity.input" styleClass="number" /></td>
            </tr>
            <html:errors property="entity.isolationDate" />
            <html:errors property="entity.deIsolationDate" />
            <tr>
                <fmt:formatDate var="isolationDate" value="${isolationHistoryForm.entity.isolationDate}" pattern="${dateTimePattern}"/>
                <fmt:formatDate var="deIsolationDate" value="${isolationHistoryForm.entity.deIsolationDate}" pattern="${dateTimePattern}"/>
                <td class="jms-label"><bean:message key="label.isolation.isolationDate" bundle="${bundle}" /></td>
                <td><html:text property="entity.isolationDate" styleClass="text" styleId="f_date_time_isolationDate" value="${isolationDate}" /></td>
                <td class="jms-label"><bean:message key="label.isolation.deIsolationDate" bundle="${bundle}" /></td>
                <td><html:text property="entity.deIsolationDate" styleClass="text" styleId="f_date_time_deIsolationDate" value="${deIsolationDate}" /></td>
            </tr>
            <html:errors property="entity.details" />
            <tr>
                <td class="jms-label"><bean:message key="label.isolation.details" bundle="${bundle}" /></td>
                <td colspan="3"><html:textarea property="entity.details" styleClass="textarea" /></td>
            </tr>
            <html:errors property="entity.keyDetails" />
            <tr>
                <td class="jms-label"><bean:message key="label.isolation.keyDetails" bundle="${bundle}" /></td>
                <td colspan="3"><html:textarea property="entity.keyDetails" styleClass="textarea" /></td>
            </tr>
        </table>
    </html:form>

</jsp:root>