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

    <table class="dataTable">
        <caption>Add Next Action</caption>
        <tr>
            <th><bean:message key="label.actionCode" /></th>
            <th class="width100"><bean:message key="label.fileAction.notation" bundle="${bundle}" /></th>
            <th class="nowrap"><bean:message key="label.fileAction.dueDate" bundle="${bundle}" /></th>
            <th></th>
        </tr>
        <c:set var="count" value="${fn:length(fileActionForm.nextActions)}" />
        <c:if test="${count gt 0}">
            <c:forEach begin="0" end="${count - 1}" varStatus="status">
                <c:set var="index" value="${status.index}" />
                <html:errors property="nextActions[${index}].actionCode.actionCodeId" />
                <html:errors property="nextActions[${index}].notation" />
                <html:errors property="nextActions[${index}].dueDate" />
                <tr class="${index % 2 eq 0 ? 'odd' : 'even'}">
                    <td>
                        <html:select property="nextActions[${index}].actionCode.actionCodeId"
                            onchange="YAHOO.jms.sendPostRequest(this.form, this.form.action + '?dispatch=changeNextActionCode&amp;index=${index}', 'scripts/common.js');">
                            <option value=""><bean:message key="option.select" /></option>
                            <html:options collection="actionCodes" property="id" labelProperty="code" />
                        </html:select>
                    </td>
                    <td>
                        <html:text property="nextActions[${index}].notation" styleClass="text" />
                    </td>
                    <td>
                        <c:set var="dueDate"><fmt:formatDate value="${fileActionForm.nextActions[index].dueDate}" pattern="${datePattern}" /></c:set>
                        <html:text property="nextActions[${index}].dueDate" value="${dueDate}" title="${datePattern}" styleClass="text" styleId="f_date_nextAction${index}" />
                    </td>
                    <td>
                        <html:link href="javascript:;" titleKey="link.fileAction.remove" bundle="${bundle}"
                            onclick="YAHOO.jms.sendPostRequest(document.forms[0], document.forms[0].action + '?dispatch=removeNextAction&amp;index=${index}', 'scripts/common.js');">
                            <span class="delete">&#160;</span>
                        </html:link>
                    </td>
                </tr>
            </c:forEach>
        </c:if>
        <tr>
            <th colspan="4">
                <html:link href="javascript:;" titleKey="link.fileAction.add" bundle="${bundle}"
                    onclick="YAHOO.jms.sendPostRequest(document.forms[0], document.forms[0].action + '?dispatch=addNextAction', 'scripts/common.js');">
                    <html:img src="images/new.png" /><bean:message key="link.fileAction.add" bundle="${bundle}" />
                </html:link>
            </th>
        </tr>
    </table>

</jsp:root>