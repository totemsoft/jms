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

    <c:if test="${not empty actionOutcomes}">
    <table class="dataTable">
        <caption>Default Next Action</caption>
        <tr>
            <th class="bold" colspan="4">
                <bean:message key="label.actionOutcome" />&#160;
                <html:select property="actionOutcome.actionOutcomeId"
                    onchange="YAHOO.jms.sendPostRequest(this.form, this.form.action + '?dispatch=updateDefaultAction', 'scripts/common.js');">
                    <option value=""><bean:message key="option.select" /></option>
                    <html:options collection="actionOutcomes" property="actionOutcomeId" labelProperty="name" />
                </html:select>
            </th>
        </tr>
        <c:set var="count" value="${fn:length(fileActionForm.defaultActions)}" />
        <c:if test="${count gt 0}">
            <tr>
                <th><bean:message key="label.actionCode" /></th>
                <th class="width100"><bean:message key="label.fileAction.notation" bundle="${bundle}" /></th>
                <th class="nowrap"><bean:message key="label.fileAction.dueDate" bundle="${bundle}" /></th>
                <th></th>
            </tr>
            <c:forEach begin="0" end="${count - 1}" varStatus="status">
                <c:set var="index" value="${status.index}" />
                <html:errors property="defaultActions[${index}].actionCode.actionCodeId" />
                <html:errors property="defaultActions[${index}].notation" />
                <html:errors property="defaultActions[${index}].dueDate" />
                <tr class="${index % 2 eq 0 ? 'odd' : 'even'}">
                    <td>
                        <html:select property="defaultActions[${index}].actionCode.actionCodeId"
                            onchange="YAHOO.jms.sendPostRequest(this.form, this.form.action + '?dispatch=changeDefaultActionCode&amp;index=${index}', 'scripts/common.js');">
                            <option value=""><bean:message key="option.select" /></option>
                            <html:options collection="actionCodes" property="id" labelProperty="code" />
                        </html:select>
                    </td>
                    <td>
                        <html:text property="defaultActions[${index}].notation" styleClass="text" />
                    </td>
                    <td>
                        <c:set var="dueDate"><fmt:formatDate value="${fileActionForm.defaultActions[index].dueDate}" pattern="${datePattern}" /></c:set>
                        <html:text property="defaultActions[${index}].dueDate" value="${dueDate}" title="${datePattern}" styleClass="text" styleId="f_date_defaultAction${index}" />
                    </td>
                    <td>
                        <html:link href="javascript:;" titleKey="link.fileAction.remove" bundle="${bundle}"
                            onclick="YAHOO.jms.sendPostRequest(document.forms[0], document.forms[0].action + '?dispatch=removeDefaultAction&amp;index=${index}', 'scripts/common.js');">
                            <span class="delete">&#160;</span>
                        </html:link>
                    </td>
                </tr>
            </c:forEach>
        </c:if>
    </table>
    </c:if>

</jsp:root>