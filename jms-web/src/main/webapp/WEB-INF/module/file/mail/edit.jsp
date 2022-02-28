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

    <html:form action="/mailRegister">
        <input type="hidden" name="file.fileId" value="${fileId gt 0 ? fileId : 0}" />
        <table class="nonDataTable">
            <tr>
                <td>
                    <td class="jms-label"><bean:message key="label.mail.mailOut" bundle="${bundle}" /></td>
                    <td><input type="radio" name="dispatch" value="editMailOut" onchange="YAHOO.jms.sendPostRequest(this.form, this.form.action, 'scripts/common.js');" /></td>
                    <td class="jms-label"><bean:message key="label.mail.mailIn" bundle="${bundle}" /></td>
                    <td><input type="radio" name="dispatch" value="editMailIn" onchange="YAHOO.jms.sendPostRequest(this.form, this.form.action, 'scripts/common.js');" /></td>
                </td>
            </tr>
        </table>
    </html:form>

</jsp:root>