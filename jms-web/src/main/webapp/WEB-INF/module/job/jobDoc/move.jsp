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

    <c:set var="bundle" value="job" />

    <html:form action="/moveJobDoc">
        <html:hidden property="dispatch" value="moveJobDocSave" />

        <div id="errors" class="error">
            <html:errors />
        </div>

        <table class="nonDataTable">
            <caption>FROM</caption>
            <tr>
                <td class="jms-label"><bean:message key="label.job" /></td>
                <td><html:text property="entity.job.jobId" readonly="true" styleClass="text" /></td>
                <c:if test="${empty jobDocForm.entity.job.fca}">
                    <td colspan="2" />
                </c:if>
                <c:if test="${not empty jobDocForm.entity.job.fca}">
                    <td class="jms-label"><bean:message key="label.fca" /></td>
                    <td><html:text property="entity.job.fca.fcaId" readonly="true" styleClass="text" /></td>
                </c:if>
            </tr>
            <tr>
                <td class="jms-label"><bean:message key="label.jobDoc.jobDocId" bundle="${bundle}" /></td>
                <td><html:text property="entity.jobDocId" readonly="true" styleClass="text" /></td>
                <td class="jms-label"><bean:message key="label.jobDoc.description" bundle="${bundle}" /></td>
                <td><html:text property="entity.description" readonly="true" styleClass="text" /></td>
            </tr>
        </table>

        <table class="nonDataTable">
            <caption>TO</caption>
            <html:errors property="moveJobId" />
            <tr>
                <td class="jms-label"><bean:message key="label.job" /></td>
                <td>
                    <div>
                        <html:text styleId="jobId.input" property="moveJobId" styleClass="text" />
                        <div id="jobId.container"></div>
                    </div>
                </td>
            </tr>
        </table>

    </html:form>

</jsp:root>