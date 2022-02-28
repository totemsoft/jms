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

    <html:form action="/addJobAction">
        <table class="nonDataTable">
            <tr>
                <td class="bold nowrap"><bean:message key="label.actionType" /></td>
                <td>
                    <html:select property="actionTypeId"
                        onchange="YAHOO.jms.sendPostRequest(this.form, this.form.action + '?dispatch=changeActionType', 'scripts/common.js');">
                        <option value=""><bean:message key="option.select" /></option>
                        <html:options collection="actionTypes" property="id" labelProperty="name" />
                    </html:select>
                </td>
            </tr>
        </table>
    </html:form>

</jsp:root>