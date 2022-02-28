<?xml version="1.0" encoding="UTF-8"?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:html="http://struts.apache.org/tags-html"
    xmlns:bean="http://struts.apache.org/tags-bean"
    xmlns:c="http://java.sun.com/jsp/jstl/core"
    xmlns:fn="http://java.sun.com/jsp/jstl/functions"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    version="2.0">

    <html:xhtml/>

    <c:set var="bundle" value="file" />
    <c:set var="fileAction" value="${mailBatchFile.fileAction}" />
    <c:set var="mailStatus" value="${mailBatchFile.mailStatus}" />
    <table class="nonDataTable">
        <tr>
            <!--td class="jms-label-caption"><bean:message key="label.om89.status" bundle="${bundle}" /></td>
            <td colspan="3" class="jms-label-caption">
                <c:choose>
                    <c:when test="${not empty mailBatchFile}">
                        ${mailStatus.status}
                    </c:when>
                    <c:otherwise>
                        <bean:message key="label.om89.notFound" bundle="${bundle}" />
                    </c:otherwise>
                </c:choose>
            </td-->
            <!--td class="jms-label"><bean:message key="label.fileAudit.status" bundle="${bundle}" /></td>
            <td>${fileAudit.status}</td-->
        </tr>
        <tr>
            <td class="jms-label">
                <c:choose>
                    <c:when test="${not empty mailBatchFile and empty mailStatus.sentDate}">
                        <button id="mailSentBtn">
                            <bean:message key="label.mailStatus.sent" bundle="${bundle}" />
                        </button>
                    </c:when>
                    <c:otherwise>
                        <bean:message key="label.mailStatus.sent" bundle="${bundle}" />
                    </c:otherwise>
                </c:choose>
            </td>
            <td title="${time}">
                <fmt:formatDate var="date" value="${mailStatus.sentDate}" pattern="${datePattern}" />
                <fmt:formatDate var="time" value="${mailStatus.sentDate}" pattern="${timePattern}" />
                <input id="sentDate" value="${date}" title="${time}" class="width100" readonly="readonly" />
            </td>
            <td class="jms-label">
                <c:choose>
                    <c:when test="${not empty mailBatchFile and empty mailStatus.receivedDate}">
                        <button id="mailReceivedBtn">
                            <bean:message key="label.mailStatus.received" bundle="${bundle}" />
                        </button>
                    </c:when>
                    <c:otherwise>
                        <bean:message key="label.mailStatus.received" bundle="${bundle}" />
                    </c:otherwise>
                </c:choose>
            </td>
            <td>
                <fmt:formatDate var="date" value="${mailStatus.receivedDate}" pattern="${datePattern}" />
                <fmt:formatDate var="time" value="${mailStatus.receivedDate}" pattern="${timePattern}" />
                <input id="receivedDate" value="${date}" title="${time}" class="width100" readonly="readonly" />
            </td>
        </tr>
        <tr>
            <td colspan="4" class="jms-label"><bean:message key="label.om89.lastSentTo" bundle="${bundle}" /></td>
        </tr>
        <tr>
            <td colspan="4"><textarea readonly="readonly" class="height4">${fileAction.destination}</textarea></td>
        </tr>
        <tr><td colspan="4"><hr /></td></tr>

        <tr>
            <td colspan="3" class="jms-label-caption"><bean:message key="label.om89.defaultContact" bundle="${bundle}" /></td>
            <td class="right">
                <html:select property="entity.defaultOwnerType.id" styleId="defaultOwnerTypeId">
                    <html:options collection="ownerTypes" property="id" labelProperty="name" />
                </html:select>
            </td>
        </tr>
        <tr>
            <td class="jms-label"><bean:message key="label.om89.sendTo" bundle="${bundle}" /></td>
            <td colspan="3"><html:text property="defaultOwner.contact.fullName" styleClass="width100" readonly="true" /></td>
        </tr>
        <tr>
            <td class="jms-label"><bean:message key="label.om89.sendAddress" bundle="${bundle}" /></td>
            <td colspan="3"><html:textarea property="defaultOwner.address.fullAddress" readonly="true" styleClass="height4" /></td>
        </tr>
        <tr>
            <td class="jms-label"><bean:message key="label.om89.sendEmail" bundle="${bundle}" /></td>
            <td colspan="3"><html:text property="defaultOwner.contact.email" styleClass="width100" readonly="true" /></td>
        </tr>
        <tr>
            <td class="jms-label"><bean:message key="label.om89.sendBy" bundle="${bundle}" /></td>
            <td>
                <html:select property="entity.mailMethodId" styleId="mailMethodId">
                    <option value=""><bean:message key="option.select" /></option>
                    <html:options collection="mailMethods" property="id" labelProperty="name" />
                </html:select>
            </td>
            <td colspan="2">
                <html:checkbox property="entity.noMailOut" styleId="noMailOut" />${nbsp}<bean:message key="label.om89.notRequired" bundle="${bundle}" />
            </td>
        </tr>
        <tr><td colspan="4"><hr /></td></tr>

        <tr>
            <td colspan="3" class="jms-label-caption"><bean:message key="label.om89.altContact" bundle="${bundle}" /></td>
            <td class="right">
                <a id="alternateEdit" href="javascript:;" title="Edit" target="">edit</a>
            </td>
        </tr>
        <c:set var="defaultContact" value="${entity.alternate.defaultContact}" scope="request" />
        <c:set var="beanPrefix" value="alternate." scope="request" />
        <jsp:include page="/WEB-INF/module/file/owner/view.jsp" />
        <c:set var="beanPrefix" value="" scope="request" />
    </table>

</jsp:root>