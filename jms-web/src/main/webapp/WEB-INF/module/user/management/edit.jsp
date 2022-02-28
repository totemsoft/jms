<?xml version="1.0" encoding="UTF-8"?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:html="http://struts.apache.org/tags-html"
    xmlns:bean="http://struts.apache.org/tags-bean"
    xmlns:logic="http://struts.apache.org/tags-logic"
    xmlns:c="http://java.sun.com/jsp/jstl/core"
    xmlns:fn="http://java.sun.com/jsp/jstl/functions"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    xmlns:perm="/WEB-INF/tld/argus-perm.tld"
    version="2.0">

    <html:xhtml/>

    <c:set var="bundle" value="user" />

    <c:set var="supervisor" value="false" />
    <perm:present module="/user" path="/calendarStaffSupervisor"><c:set var="supervisor" value="true" /></perm:present>

    <html:form action="/calendarStaff">
        <html:hidden property="dispatch" value="save" />
        <html:hidden property="userId" />

        <div id="errors" class="error">
            <html:errors />
        </div>

        <table class="dataTable">
            <caption><bean:message key="label.user.calendar" bundle="${bundle}" /> :: ${userName}</caption>
            <tr>
                <th colspan="2"><bean:message key="label.user.calendar.dateStart" bundle="${bundle}" /></th>
                <th class="nowrap"><bean:message key="label.user.calendar.dateEnd" bundle="${bundle}" /></th>
                <th><bean:message key="label.user.calendar.leaveTaken" bundle="${bundle}" /></th>
                <th><bean:message key="label.user.calendar.leaveType" bundle="${bundle}" /></th>
                <th class="width100"><bean:message key="label.user.calendar.description" bundle="${bundle}" /></th>
                <th><bean:message key="label.user.calendar.formReceived" bundle="${bundle}" /></th>
                <th class="nowrap"><bean:message key="label.user.calendar.formSentHrDate" bundle="${bundle}" /></th>
                <th colspan="2"><bean:message key="label.user.calendar.status" bundle="${bundle}" /></th>
            </tr>
            <c:forEach items="${staffCalendarForm.staffLeaves}" var="item" varStatus="status">
                <c:set var="index" value="${status.index}" />
                <c:set var="staffLeave" value="${staffCalendarForm.staffLeaves[index]}" />
                <c:set var="weekend" value="${empty staffLeave.leaveType}" />
                <c:if test="${weekend}">

                </c:if>
                <c:if test="${not weekend}">
                    <html:errors property="staffLeaves[${index}].leaveType" />
                    <c:set var="editable" value="${empty staffLeave.leaveType.leaveTypeId}" />
                    <tr class="${index % 2 eq 0 ? 'odd' : 'even'} ${editable ? 'editable' : ''}">
                        <td class="right">
                            <fmt:formatDate value="${staffLeave.date}" pattern="E" />
                        </td>
                        <td class="right">
                            <html:hidden property="staffLeave[${index}].staffLeaveId" />
                            <html:hidden property="staffLeave[${index}].leaveGroupId" />
                            <fmt:formatDate var="date" value="${staffLeave.date}" pattern="${datePattern}" />
                            <input type="hidden" name="staffLeave[${index}].date" value="${date}" />
                            ${date}
                        </td>
                        <td class="right">
                            <c:if test="${editable}">
                                <html:text property="staffLeave[${index}].dateEnd" styleId="f_date_end.${index}" styleClass="right" />
                            </c:if>
                            <c:if test="${not editable}">

                            </c:if>
                        </td>
                        <td class="right nowrap">
                            <c:if test="${editable}">
                                <html:select property="staffLeave[${index}].leaveTakenHours">
                                    <html:options collection="leaveTakenHours" property="key" labelProperty="value" />
                                </html:select>
                                <html:select property="staffLeave[${index}].leaveTakenMinutes">
                                    <html:options collection="leaveTakenMinutes" property="key" labelProperty="value" />
                                </html:select>
                            </c:if>
                            <c:if test="${not editable}">
                                <html:hidden property="staffLeave[${index}].leaveTaken" />
                                ${staffLeave.leaveTakenHours}:${staffLeave.leaveTakenMinutes lt 10 ? '0' : ''}${staffLeave.leaveTakenMinutes}
                            </c:if>
                        </td>
                        <td class="nowrap">
                            <c:if test="${editable}">
                                <html:select property="staffLeave[${index}].leaveType.leaveTypeId">
                                    <option value=""><bean:message key="option.select" /></option>
                                    <html:options collection="leaveTypes" property="leaveTypeId" labelProperty="name" />
                                </html:select>
                            </c:if>
                            <c:if test="${not editable}">
                                <html:hidden property="staffLeave[${index}].leaveType.leaveTypeId" />
                                ${staffLeave.leaveType.name}
                            </c:if>
                        </td>
                        <td>
                            <html:text property="staffLeave[${index}].description" />
                        </td>
                        <c:choose>
                            <c:when test="${supervisor}">
                                <td>
                                    <html:checkbox property="staffLeave[${index}].formReceived" />
                                </td>
                                <td class="right">
                                    <html:text property="staffLeave[${index}].formSentHrDate" styleId="f_date_formSentHr.${index}" styleClass="right" />
                                </td>
                            </c:when>
                            <c:otherwise>
                                <td>
                                    <html:hidden property="staffLeave[${index}].formReceived" />
                                    <c:if test="${staffLeave.formReceived}">
                                        <input type="checkbox" checked="checked" disabled="disabled" />
                                    </c:if>
                                </td>
                                <td class="right">
                                    <fmt:formatDate var="formSentHrDate" value="${staffLeave.formSentHrDate}" pattern="${datePattern}" />
                                    <input type="hidden" name="staffLeave[${index}].formSentHrDate" value="${formSentHrDate}" />
                                    ${formSentHrDate}
                                </td>
                            </c:otherwise>
                        </c:choose>
                        <c:choose>
                            <c:when test="${supervisor and staffLeave.submitted}">
                                <td>
                                    <a id="approve.${index}" href="javascript:YAHOO.jms.approveStaffLeave(${staffLeave.id});">approve</a>
                                </td>
                                <td>
                                    <a id="decline.${index}" href="javascript:YAHOO.jms.declineStaffLeave(${staffLeave.id});">decline</a>
                                </td>
                            </c:when>
                            <c:otherwise>
                                <td colspan="2">
                                    ${staffLeave.status}
                                </td>
                            </c:otherwise>
                        </c:choose>
                    </tr>
                </c:if>
            </c:forEach>
        </table>
    </html:form>
</jsp:root>