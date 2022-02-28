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

    <html:form action="/viewActionWorkflow" method="get">
        <html:hidden property="entity.actionWorkflowId" />
        <table class="nonDataTable">
            <tr>
                <td class="jms-label"><bean:message key="label.actionCode" bundle="${bundle}" /></td>
                <td><html:text property="entity.actionCode.code" readonly="true" styleClass="text" /></td>
                <td class="jms-label"><bean:message key="label.actionOutcome" bundle="${bundle}" /></td>
                <td><html:text property="entity.actionOutcome.name" readonly="true" styleClass="text" /></td>
            </tr>
            <tr>
                <td colspan="4">
                    <table class="dataTable" width="100%">
                        <caption><bean:message key="label.actionWorkflow.nextActionCode" bundle="${bundle}" /></caption>
                        <c:forEach items="${actionWorkflowForm.actionWorkflows}" var="item" varStatus="s">
                            <c:if test="${s.index eq 0}">
                                <th><bean:message key="label.actionCode" bundle="${bundle}" /></th>
                                <th class="width10em number"><bean:message key="label.actionWorkflow.nextDueDays" bundle="${bundle}" /></th>
                            </c:if>
                            <tr class="${s.index % 2 eq 0 ? 'odd' : 'even'}">
                                <td>${item.nextActionCode.code}</td>
                                <td class="width10em number">${item.nextDueDays}</td>
                            </tr>
                        </c:forEach>
                    </table>
                </td>
            </tr>
        </table>
    </html:form>

</jsp:root>