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

    <html:form action="/editActionWorkflow">
        <html:hidden property="dispatch" value="save" />
        <html:hidden property="entity.actionWorkflowId" />

        <div id="errors" class="error">
            <html:errors />
        </div>

        <table class="nonDataTable">
            <tr>
                <th><bean:message key="label.actionCode" bundle="${bundle}" /></th>
                <th><html:text property="entity.actionCode.code" readonly="true" styleClass="text" /></th>
                <th><bean:message key="label.actionOutcome" bundle="${bundle}" /></th>
                <th><html:text property="entity.actionOutcome.name" readonly="true" styleClass="text" /></th>
            </tr>
            <html:errors property="entity.nextActionCode.actionCodeId" />
            <html:errors property="entity.nextDueDays" />
            <tr>
                <td colspan="4">
                    <table class="dataTable">
                        <caption><bean:message key="label.actionWorkflow.nextActionCode" bundle="${bundle}" /></caption>
                        <tr>
                            <th><bean:message key="label.actionCode" bundle="${bundle}" /></th>
                            <th><bean:message key="label.actionWorkflow.nextDueDays" bundle="${bundle}" /></th>
                            <th></th>
                        </tr>
                        <c:set var="count" value="${fn:length(actionWorkflowForm.actionWorkflows)}" />
                        <c:if test="${count gt 0}">
                            <c:forEach begin="0" end="${count - 1}" varStatus="status">
                                <html:errors property="actionWorkflows[${status.index}].nextActionCode.actionCodeId" />
                                <html:errors property="actionWorkflows[${status.index}].nextDueDays" />
                                <tr class="${status.index % 2 eq 0 ? 'odd' : 'even'}">
                                    <td>
                                        <html:select property="actionWorkflows[${status.index}].nextActionCode.actionCodeId">
                                            <option value=""><bean:message key="option.select" /></option>
                                            <html:options collection="actionCodes" property="id" labelProperty="code" />
                                        </html:select>
                                    </td>
                                    <td>
                                        <html:text property="actionWorkflows[${status.index}].nextDueDays" styleClass="numberInput text" />
                                    </td>
                                    <td>
                                        <html:link href="javascript:;" titleKey="link.actionCode.remove" bundle="${bundle}"
                                            onclick="YAHOO.jms.sendPostRequest('actionWorkflowForm', 'setup/editActionWorkflow.do?dispatch=removeActionWorkflow&amp;index=${status.index}');">
                                            <span class="delete">&#160;</span>
                                        </html:link>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:if>
                        <tr>
                            <th colspan="3">
                                <html:link href="javascript:;" titleKey="link.actionCode.add" bundle="${bundle}"
                                    onclick="YAHOO.jms.sendPostRequest('actionWorkflowForm', 'setup/editActionWorkflow.do?dispatch=addActionWorkflow');">
                                    <html:img src="images/new.png" /><bean:message key="link.actionCode.add" bundle="${bundle}" />
                                </html:link>
                            </th>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
    </html:form>

</jsp:root>