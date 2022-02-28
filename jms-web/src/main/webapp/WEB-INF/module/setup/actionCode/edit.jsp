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

    <html:form action="/editActionCode">
        <html:hidden property="dispatch" value="save" />
        <html:hidden property="entity.actionCodeId" />

        <div id="errors" class="error">
            <html:errors />
        </div>

        <table class="nonDataTable">
            <html:errors property="entity.code" />
            <html:errors property="entity.actionType.actionTypeId" />
            <tr>
                <td class="jms-label"><bean:message key="label.actionCode.code" bundle="${bundle}" /></td>
                <td><html:text property="entity.code" styleClass="text" /></td>
                <td class="jms-label"><bean:message key="label.actionType" bundle="${bundle}" /></td>
                <td>
                    <html:select property="entity.actionType.actionTypeId">
                        <option value=""><bean:message key="option.select" /></option>
                        <html:options collection="actionTypes" property="id" labelProperty="name" />
                    </html:select>
                </td>
                <td class="jms-label"><bean:message key="label.actionCode.active" bundle="${bundle}" /></td>
                <td>
                    <html:hidden property="entity.active" styleId="active" />
                    <html:checkbox property="entity.active" onchange="this.form.active.value=this.checked;" />
                </td>
            </tr>
            <html:errors property="entity.notation" />
            <tr>
                <td class="jms-label"><bean:message key="label.actionCode.notation" bundle="${bundle}" /></td>
                <td colspan="3"><html:text property="entity.notation" styleClass="text" /></td>
                <td class="jms-label"><bean:message key="label.actionCode.bulkMail" bundle="${bundle}" /></td>
                <td>
                    <html:hidden property="entity.bulkMail" styleId="bulkMail" />
                    <html:checkbox property="entity.bulkMail" onchange="this.form.bulkMail.value=this.checked;" />
                </td>
            </tr>
            <html:errors property="entity.template.templateId" />
            <html:errors property="entity.template.documentTemplate" />
            <tr>
                <td class="jms-label"><bean:message key="label.actionCode.template" bundle="${bundle}" /></td>
                <td>
                    <html:select property="entity.template.templateId">
                        <option value=""><bean:message key="option.select" /></option>
                        <html:options collection="templates" property="id" labelProperty="name" />
                    </html:select>
                </td>
                <td class="jms-label"><bean:message key="label.actionCode.documentTemplate" bundle="${bundle}" /></td>
                <td colspan="3">
                    <html:select property="entity.documentTemplate.templateId">
                        <option value=""><bean:message key="option.select" /></option>
                        <html:options collection="templates" property="id" labelProperty="name" />
                    </html:select>
                </td>
            </tr>
            <html:errors property="entity.jobType.jobTypeId" />
            <html:errors property="entity.workGroup.workGroupId" />
            <tr>
                <td class="jms-label"><bean:message key="label.jobType" bundle="${bundle}" /></td>
                <td>
                    <html:select property="entity.jobType.jobTypeId">
                        <option value=""><bean:message key="option.select" /></option>
                        <html:options collection="jobTypes" property="id" labelProperty="name" />
                    </html:select>
                </td>
                <td class="jms-label"><bean:message key="label.workGroup" bundle="${bundle}" /></td>
                <td colspan="3">
                    <html:select property="entity.workGroup.workGroupId">
                        <option value=""><bean:message key="option.select" /></option>
                        <html:options collection="workGroups" property="id" labelProperty="name" />
                    </html:select>
                </td>
            </tr>
        </table>
    </html:form>

</jsp:root>