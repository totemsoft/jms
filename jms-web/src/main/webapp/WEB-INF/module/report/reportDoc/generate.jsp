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

    <html:form action="/generateReportDoc">
        <html:hidden property="dispatch" value="generateReportDoc" />
        <html:hidden property="entity.templateId" />

        <div id="errors" class="error">
            <html:errors />
        </div>

        <table class="nonDataTable">
            <tr>
                <td class="jms-label width10em"><bean:message key="label.report.code" bundle="${bundle}" /></td>
                <td><html:text property="entity.code" readonly="true" styleClass="text" /></td>
            </tr>
            <tr>
                <td class="jms-label"><bean:message key="label.report.name" bundle="${bundle}" /></td>
                <td><html:text property="reportDoc.name" styleClass="text" /></td>
            </tr>
            <tr>
                <td class="jms-label"><bean:message key="label.report.description" bundle="${bundle}" /></td>
                <td><html:textarea property="reportDoc.description" styleClass="text" /></td>
            </tr>
        </table>

        <jsp:include page="/WEB-INF/module/report/reportDoc/criteria.jsp" />

    </html:form>

</jsp:root>