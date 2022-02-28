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

    <html:form action="/fileArchive.do?dispatch=save">
        <html:hidden property="entity.archiveId" />
        <table class="nonDataTable">
            <tr>
                <td class="jms-label"><bean:message key="label.archive.name" bundle="${bundle}" /></td>
                <td><html:text property="entity.name" styleClass="text" /></td>
                <td class="jms-label"><bean:message key="label.archive.code" bundle="${bundle}" /></td>
                <td><html:text property="entity.code" styleClass="text" /></td>
            </tr>
            <tr>
                <fmt:formatDate var="dateFrom" value="${archiveForm.entity.dateFrom}" pattern="${datePattern}" />
                <td class="jms-label"><bean:message key="label.archive.dateFrom" bundle="${bundle}" /></td>
                <td><input id="f_date_dateFrom" name="entity.dateFrom" value="${dateFrom}" class="text" /></td>
                <fmt:formatDate var="dateTo" value="${archiveForm.entity.dateTo}" pattern="${datePattern}" />
                <td class="jms-label"><bean:message key="label.archive.dateTo" bundle="${bundle}" /></td>
                <td><input id="f_date_dateTo" name="entity.dateTo" value="${dateTo}" class="text" /></td>
            </tr>
            <tr>
                <td class="jms-label"><bean:message key="label.archive.location" bundle="${bundle}" /></td>
                <td colspan="3"><html:textarea property="entity.location" styleClass="textarea" /></td>
            </tr>
            <tr>
                <td class="jms-label"><bean:message key="label.archive.description" bundle="${bundle}" /></td>
                <td colspan="3"><html:text property="entity.description" styleClass="text" /></td>
            </tr>
        </table>
        <jsp:include page="/WEB-INF/module/file/archive/files.jsp" />
    </html:form>

</jsp:root>