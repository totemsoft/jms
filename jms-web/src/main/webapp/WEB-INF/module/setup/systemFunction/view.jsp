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

    <html:form action="/systemFunction" method="get">
        <html:hidden property="entity.systemFunctionId" />
        <table class="nonDataTable">
            <tr>
                <td class="jms-label"><bean:message key="label.systemFunction.module" bundle="${bundle}" /></td>
                <td><html:text property="entity.module" readonly="true" /></td>
                <td class="jms-label"><bean:message key="label.systemFunction.name" bundle="${bundle}" /></td>
                <td><html:text property="entity.name" readonly="true" /></td>
            </tr>
            <tr>
                <td class="jms-label"><bean:message key="label.systemFunction.query" bundle="${bundle}" /></td>
                <td><html:text property="entity.query" readonly="true" /></td>
                <td class="jms-label"><bean:message key="label.systemFunction.description" bundle="${bundle}" /></td>
                <td><html:text property="entity.description" readonly="true" /></td>
            </tr>
        </table>
    </html:form>

</jsp:root>