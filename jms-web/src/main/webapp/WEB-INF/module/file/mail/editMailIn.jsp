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

    <html:form action="/mailRegister">
        <html:hidden property="dispatch" value="save" />
        <html:hidden property="entity.mailRegisterId" />
        <input type="hidden" id="reloadOnChangeUrl" value="dispatch=reload" />
        <input type="hidden" id="title" value="Incoming Mail" />
        <html:hidden property="entity.mailIn" value="true" />

        <div id="errors" class="error">
            <html:errors />
        </div>

        <table class="nonDataTable">
            <c:set var="sapCustNo" value="${empty mailRegisterForm.file.fileId}" scope="request" />
            <jsp:include page="/WEB-INF/module/file/editFileFca.jsp" />
            <tr>
                <fmt:formatDate var="date" value="${mailRegisterForm.entity.date}" pattern="${datePattern}" />
                <td class="jms-label"><bean:message key="label.mail.date" bundle="${bundle}" /></td>
                <td><html:text property="entity.date" value="${date}" styleClass="text" styleId="f_date_date" /></td>
                <td class="jms-label"><bean:message key="label.mail.mailRegisterNo" bundle="${bundle}" /></td>
                <td><html:text property="entity.mailRegisterNo" styleClass="text" /></td>
            </tr>
            <tr>
                <td class="jms-label"><bean:message key="label.mail.mailType" bundle="${bundle}" /></td>
                <td>
                    <html:select property="entity.mailType.mailTypeId">
                        <option value=""><bean:message key="option.select" /></option>
                        <html:options collection="mailTypes" property="id" labelProperty="name" />
                    </html:select>
                </td>
                <td class="jms-label"><bean:message key="label.mail.rts" bundle="${bundle}" /></td>
                <td>
                    <html:hidden property="entity.rts" styleId="rts" />
                    <html:checkbox property="entity.rts" onchange="this.form.rts.value=this.checked;" />
                </td>
            </tr>
            <tr>
                <td class="jms-label valign-top"><bean:message key="label.mail.fromContact" bundle="${bundle}" /></td>
                <td colspan="3" style="background-color: #EDF5FF;">
                    <table class="noborder">
                        <jsp:include page="/WEB-INF/module/file/mail/editContact.jsp" />
                        <jsp:include page="/WEB-INF/module/file/mail/editAddress.jsp" />
                    </table>
                </td>
            </tr>
            <tr>
                <td class="jms-label valign-top"><bean:message key="label.mail.toContact" bundle="${bundle}" /></td>
                <td colspan="3" style="background-color: #EDF5FF;">
                    <table class="noborder">
                        <jsp:include page="/WEB-INF/module/file/mail/editWorkGroupUser.jsp" />
                    </table>
                </td>
            </tr>
            <tr>
                <td class="jms-label valign-top"><bean:message key="label.mail.details" bundle="${bundle}" /></td>
                <td colspan="3"><html:textarea property="entity.details" styleClass="textarea" /></td>
            </tr>
        </table>
    </html:form>

</jsp:root>