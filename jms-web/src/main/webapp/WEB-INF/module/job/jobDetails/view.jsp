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

    <table class="nonDataTable">
         <tr>
             <td class="jms-label"><bean:message key="label.job.id" bundle="${bundle}" /></td>
             <td><html:text property="entity.jobId" readonly="true" styleClass="text" /></td>
             <td colspan="2" />
         </tr>
         <tr>
             <td class="jms-label"><bean:message key="label.jobType" /></td>
             <td colspan="3"><html:text property="entity.jobType.name" readonly="true" styleClass="text" /></td>
         </tr>
         <tr>
             <td class="jms-label"><bean:message key="label.workGroup" /></td>
             <td colspan="3"><input type="text" value="${jobForm.entity.jobType.workGroup.name}" readonly="readonly" class="text" /></td>
         </tr>
         <jsp:include page="/WEB-INF/module/job/viewFileFca.jsp" />
         <jsp:include page="/WEB-INF/entity/action/viewResponsibleUser.jsp" />
         <tr>
             <td colspan="4" class="jms-label"><bean:message key="label.job.description" bundle="${bundle}" /></td>
         </tr>
         <tr>
             <td colspan="4"><html:textarea property="entity.description" readonly="true" styleClass="textarea" /></td>
         </tr>
         <tr>
             <c:set var="date"><fmt:formatDate value="${jobForm.entity.createdDate}" pattern="${datePattern}"/></c:set>
             <td class="jms-label"><bean:message key="label.job.requestedDate" bundle="${bundle}" /></td>
             <td colspan="3"><html:text property="entity.createdDate" value="${date}" title="${datePattern}" maxlength="10" readonly="true" /></td>
         </tr>
         <tr>
             <td class="jms-label"><bean:message key="label.job.requestedBy" bundle="${bundle}" /></td>
             <td colspan="3"><html:text property="entity.createdBy.contact.name" readonly="true" styleClass="text" /></td>
         </tr>
         <tr>
             <td class="jms-label"><bean:message key="label.job.requestedEmail" bundle="${bundle}" /></td>
             <td colspan="3"><html:text property="entity.requestedEmail" readonly="true" styleClass="text" /></td>
         </tr>
         <tr>
             <td class="jms-label"><bean:message key="label.job.link" bundle="${bundle}" /></td>
             <td colspan="3"><html:text property="entity.link" readonly="true" styleClass="text" /></td>
         </tr>
    </table>

</jsp:root>