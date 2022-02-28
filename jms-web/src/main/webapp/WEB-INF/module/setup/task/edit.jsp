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

    <html:form action="/scheduledTask.do?dispatch=save">
        <html:hidden property="entity.scheduledTaskId" />

        <div id="errors" class="error">
            <html:errors />
        </div>

        <table class="nonDataTable">
            <html:errors property="entity.name" />
            <tr>
                <td class="jms-label"><bean:message key="label.scheduledTask.name" bundle="${bundle}" /></td>
                <td>
                    <html:select property="entity.name">
                        <option value=""><bean:message key="option.select" /></option>
                        <html:options collection="scheduledTasks" property="name" labelProperty="name" />
                    </html:select>
                </td>
                <td class="jms-label"><bean:message key="label.active" /></td>
                <td>
                    <html:hidden property="entity.active" styleId="entityActive" />
                    <html:checkbox property="entity.active" onchange="this.form.entityActive.value=this.checked;" />
                </td>
            </tr>
            <tr>
                <td class="jms-label"><bean:message key="label.scheduledTask.cronExpression" bundle="${bundle}" /></td>
                <td colspan="3">
                    <html:text property="entity.taskRequest.cronExpression" styleClass="text" />
                </td>
            </tr>
        </table>
    </html:form>

</jsp:root>