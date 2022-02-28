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

    <c:set var="bundle" value="report" />

    <html:form action="/deleteReportDoc">
        <html:hidden property="dispatch" value="deleteReportDoc" />
        <html:hidden property="entity.templateId" />
        <html:hidden property="reportDoc.reportDocId" />

        <div id="errors" class="error">
            <html:errors />
        </div>

        <table class="nonDataTable">
            <tr>
                <td class="jms-label"><bean:message key="label.report.code" bundle="${bundle}" /></td>
                <td><html:text property="entity.code" readonly="true" styleClass="text" /></td>
                <td class="jms-label"><bean:message key="label.report.date" bundle="${bundle}" /></td>
                <td><fmt:formatDate value="${reportForm.reportDoc.createdDate}" pattern="${datePattern}" /></td>
            </tr>
            <tr>
                <td class="jms-label"><bean:message key="label.report.name" bundle="${bundle}" /></td>
                <td colspan="3"><html:text property="reportDoc.name" readonly="true" styleClass="text" /></td>
            </tr>
            <tr>
                <td class="jms-label"><bean:message key="label.report.description" bundle="${bundle}" /></td>
                <td colspan="3"><html:textarea property="reportDoc.description" readonly="true" styleClass="text" /></td>
            </tr>
        </table>
    </html:form>

</jsp:root>