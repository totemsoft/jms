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

    <c:set var="bundle" value="report" />

    <c:if test="${empty parameters.dateStart}">
        <html:hidden property="criteria.dateStart" />
    </c:if>
    <c:if test="${empty parameters.dateEnd}">
        <html:hidden property="criteria.dateEnd" />
    </c:if>

    <table class="nonDataTable">
        <caption>Criteria Details</caption>
        <c:if test="${not empty parameters.dateStart}">
            <tr>
                <td class="jms-label width10em"><bean:message key="label.report.dateStart" bundle="${bundle}" /></td>
                <c:set var="dateStart"><fmt:formatDate value="${reportForm.criteria.dateStart}" pattern="${datePattern}" /></c:set>
                <td><html:text property="criteria.dateStart" value="${dateStart}" title="${datePattern}" styleId="f_date_start" styleClass="text" /></td>
            </tr>
        </c:if>
        <c:if test="${not empty parameters.dateEnd}">
            <tr>
                <td class="jms-label"><bean:message key="label.report.dateEnd" bundle="${bundle}" /></td>
                <c:set var="dateEnd"><fmt:formatDate value="${reportForm.criteria.dateEnd}" pattern="${datePattern}" /></c:set>
                <td><html:text property="criteria.dateEnd" value="${dateEnd}" title="${datePattern}" styleId="f_date_end" styleClass="text" /></td>
            </tr>
        </c:if>
        <c:if test="${not empty parameters.fileId}">
            <tr>
                <td class="jms-label"><bean:message key="label.file" /></td>
                <td><html:text property="criteria.fileId" styleClass="text" /></td>
            </tr>
        </c:if>
        <c:if test="${not empty parameters.workGroupId}">
            <tr>
                <td class="jms-label"><bean:message key="label.workGroup" /></td>
                <td>
                    <html:select property="criteria.workGroupId">
                        <html:option value="0"><bean:message key="option.all" /></html:option>
                        <html:options collection="workGroups" property="id" labelProperty="name" />
                    </html:select>
                </td>
            </tr>
        </c:if>
        <c:if test="${not empty parameters.supplierId and not empty suppliers}">
            <tr>
                <td class="jms-label"><bean:message key="label.supplier" /></td>
                <td>
                    <html:select property="criteria.supplierId">
                        <html:option value="0"><bean:message key="option.all" /></html:option>
                        <html:options collection="suppliers" property="id" labelProperty="name" />
                    </html:select>
                </td>
            </tr>
        </c:if>
        <c:if test="${not empty parameters.actionCodeIds}">
            <tr>
                <td class="jms-label"><bean:message key="label.actionCode" /></td>
                <td>
                    <c:set var="count" value="${fn:length(actionCodes)}" />
                    <c:if test="${count gt 0}">
                        <table class="nonDataTable">
                            <c:forEach begin="0" end="${count - 1}" step="3" varStatus="status">
                                <c:set var="item" value="${actionCodes[status.index]}" />
                                <tr>
                                    <td><html:multibox property="criteria.actionCodeIds" value="${item.id}" title="${item.notation}" /></td>
                                    <td class="smaller">${item.code}</td>
                                    <c:set var="item" value="${actionCodes[status.index + 1]}" />
                                    <c:if test="${not empty item}">
                                        <td><html:multibox property="criteria.actionCodeIds" value="${item.id}" title="${item.notation}" /></td>
                                        <td class="smaller">${item.code}</td>
                                    </c:if>
                                    <c:set var="item" value="${actionCodes[status.index + 2]}" />
                                    <c:if test="${not empty item}">
                                        <td><html:multibox property="criteria.actionCodeIds" value="${item.id}" title="${item.notation}" /></td>
                                        <td class="smaller">${item.code}</td>
                                    </c:if>
                                </tr>
                            </c:forEach>
                        </table>
                    </c:if>
                </td>
            </tr>
        </c:if>
        <c:if test="${not empty parameters.nextCompletedActionCodeId}">
        <tr>
            <td><bean:message key="label.nextActionCode" /></td>
            <td>
                <html:select property="criteria.nextCompletedActionCodeId" title="Select Next Completed Action Code (optional)">
                    <html:option value="0"><bean:message key="option.select" /></html:option>
                    <html:options collection="actionCodes" property="id" labelProperty="code" />
                </html:select>
            </td>
        </tr>
        </c:if>
        <c:if test="${not empty parameters.active}">
            <tr>
                <td class="jms-label"><bean:message key="label.active" /></td>
                <td>
                    <html:select property="criteria.active">
                        <html:option value=" "><bean:message key="option.both" /></html:option>
                        <html:option value="Y"><bean:message key="option.active" /></html:option>
                        <html:option value="N"><bean:message key="option.inactive" /></html:option>
                    </html:select>
                </td>
            </tr>
        </c:if>
    </table>

</jsp:root>