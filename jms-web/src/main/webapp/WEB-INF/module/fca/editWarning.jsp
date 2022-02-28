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

    <html:form action="/editFcaWarning">
        <html:hidden property="dispatch" value="save" />

        <h2 class="jms-label">You are about to assign:</h2>

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
                <td class="jms-label" style="text-align:center;" colspan="2">to</td>
            </tr>
            <tr>
                <td class="jms-label"><bean:message key="label.file" /></td>
                <td>
                    <div>
                        <html:text property="entity.file.fileId" readonly="true" styleClass="text" />
                    </div>
                </td>
            </tr>
        </table>

        <h2 class="jms-label">Are you sure you want to proceed?</h2>

    </html:form>

</jsp:root>