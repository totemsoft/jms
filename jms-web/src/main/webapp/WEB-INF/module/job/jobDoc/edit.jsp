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

    <html:form action="/editJobDoc">
        <html:hidden property="dispatch" value="save" />

        <div id="errors" class="error">
            <html:errors />
        </div>

        <table class="nonDataTable">
            <tr>
                <td colspan="2">
                    <table class="dataTable">
                        <tr>
                            <th class="nowrap"><bean:message key="label.jobDoc.jobDocId" bundle="${bundle}" /></th>
                            <th><bean:message key="label.jobDoc.createdDate" bundle="${bundle}" /></th>
                            <th><bean:message key="label.jobDoc.docType" bundle="${bundle}" /></th>
                            <th class="width50"><bean:message key="label.jobDoc.description" bundle="${bundle}" /></th>
                            <th class="width50"><bean:message key="label.jobDoc.location" bundle="${bundle}" /></th>
                            <th colspan="2"></th>
                        </tr>
                        <c:set var="count" value="${fn:length(jobForm.entity.jobDocs)}" />
                        <c:if test="${count gt 0}">
                            <c:forEach begin="0" end="${count - 1}" varStatus="status">
                                <c:set var="index" value="${status.index}" />
                                <tr class="${index % 2 eq 0 ? 'odd' : 'even'}">
                                    <td>${jobForm.entity.jobDocs[index].jobDocId}</td>
                                    <td><fmt:formatDate value="${jobForm.entity.jobDocs[index].createdDate}" pattern="${datePattern}" /></td>
                                    <td>
                                        <html:select property="entity.jobDocs[${status.index}].docType.docTypeId">
                                            <option value=""><bean:message key="option.select" /></option>
                                            <html:options collection="docTypes" property="docTypeId" labelProperty="name" />
                                        </html:select>
                                    </td>
                                    <td>
                                        <html:text property="entity.jobDocs[${index}].description" styleClass="text" />
                                    </td>
                                    <td>
                                        <html:text property="entity.jobDocs[${index}].location" styleClass="text" />
                                    </td>
                                    <td>
                                        <c:if test="${not empty jobForm.entity.jobDocs[index].jobDocId}">
                                            <html:link href="javascript:;" titleKey="link.jobDoc.move" bundle="${bundle}"
                                                onclick="YAHOO.jms.sendGetRequest('job/moveJobDoc.do?index=${index}', 'scripts/module/job/moveJobDoc.js', 'Move Document');">
                                                <bean:message key="link.move" />
                                            </html:link>
                                        </c:if>
                                    </td>
                                    <td>
                                        <html:link href="javascript:;" titleKey="link.jobDoc.remove" bundle="${bundle}"
                                            onclick="YAHOO.jms.sendPostRequest(document.forms[0], document.forms[0].action + '?dispatch=removeJobDoc&amp;index=${index}');">
                                            <span class="delete">&#160;</span>
                                        </html:link>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:if>
                        <tr>
                            <th colspan="7">
                                <html:link href="javascript:;" titleKey="link.jobDoc.add" bundle="${bundle}"
                                    onclick="YAHOO.jms.sendPostRequest(document.forms[0], document.forms[0].action + '?dispatch=addJobDoc');">
                                    <html:img src="images/new.png" /><bean:message key="link.jobDoc.add" bundle="${bundle}" />
                                </html:link>
                            </th>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
    </html:form>

</jsp:root>