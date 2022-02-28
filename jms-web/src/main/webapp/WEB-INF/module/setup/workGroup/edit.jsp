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

    <html:form action="/editWorkGroup">
        <html:hidden property="dispatch" value="save" />
        <html:hidden property="entity.workGroupId" />

        <div id="errors" class="error">
            <html:errors />
        </div>

        <table class="nonDataTable">
            <html:errors property="entity.name" />
            <tr>
                <td class="jms-label"><bean:message key="label.workGroup.name" bundle="${bundle}" /></td>
                <td><html:text property="entity.name" /></td>
            </tr>
            <tr>
                <td class="jms-label"><bean:message key="label.workGroup.active" bundle="${bundle}" /></td>
                <td>
                    <html:hidden property="entity.active" styleId="active" />
                    <html:checkbox property="entity.active" onchange="this.form.active.value=this.checked;" />
                </td>
            </tr>
        </table>
    </html:form>

</jsp:root>