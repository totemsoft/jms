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

    <html:form action="/editTemplate" enctype="multipart/form-data">
        <html:hidden property="dispatch" value="save" />
        <html:hidden property="entity.templateId" />

        <div id="errors" class="error">
            <html:errors />
        </div>

        <table class="nonDataTable">
            <html:errors property="entity.name" />
            <html:errors property="entity.templateType.templateTypeId" />
            <tr>
                <td class="bold width10em"><bean:message key="label.template.name" bundle="${bundle}" /></td>
                <td>
                    <html:text property="entity.name" styleClass="text" />
                </td>
                <td class="jms-label"><bean:message key="label.templateType" bundle="${bundle}" /></td>
                <td>
                    <html:select property="entity.templateType.templateTypeId"
                        onchange="YAHOO.jms.sendPostRequest(this.form, this.form.action + '?dispatch=updateTemplateType', 'scripts/common.js');">
                        <option value=""><bean:message key="option.select" /></option>
                        <html:options collection="templateTypes" property="id" labelProperty="name" />
                    </html:select>
                </td>
            </tr>
            <tr>
                <td class="jms-label"><bean:message key="label.template.description" bundle="${bundle}" /></td>
                <td colspan="3">
                    <html:textarea property="entity.description" styleClass="textarea" />
                </td>
            </tr>
            <tr>
                <td class="jms-label"><bean:message key="label.template.upload" bundle="${bundle}" /></td>
                <td>
                    <html:file styleId="uploadFile" property="templateFile" size="70"
                        onchange="this.form.location.value=YAHOO.jms.onchangeUploadFile(this);" />
                </td>
                <td class="jms-label"><bean:message key="label.template.uploadDate" bundle="${bundle}" /></td>
                <td>
                    <c:set var="uploadDate"><fmt:formatDate value="${templateForm.entity.uploadDate}" pattern="${datePattern}" /></c:set>
                    <html:text property="entity.uploadDate" value="${uploadDate}" readonly="true" styleClass="text" />
                </td>
            </tr>
            <html:errors property="entity.code" />
            <tr>
                <td class="jms-label"><bean:message key="label.template.location" bundle="${bundle}" /></td>
                <td>
                    <html:text property="entity.location" styleId="location" readonly="false" styleClass="text" />
                </td>
                <td class="jms-label"><bean:message key="label.template.code" bundle="${bundle}" /></td>
                <td>
                    <html:text property="entity.code" size="50" styleClass="text" />
                </td>
            </tr>
            <tr>
                <td class="jms-label"><bean:message key="label.template.config.upload" bundle="${bundle}" /></td>
                <td>
                    <html:file styleId="uploadFileConfig" property="configFile" size="70" />
                </td>
                <td colspan="2" />
            </tr>
        </table>
        <c:choose>
            <c:when test="${templateForm.entity.templateType.email and empty templateForm.entity.location}">
                <p>
                    <html:textarea styleId="htmlInput" property="htmlInput" styleClass="textarea" style="display:none;visibility:hidden;" />
                </p>
            </c:when>
            <c:when test="${templateForm.entity.templateType.email and not empty templateForm.entity.location}">
                <table class="nonDataTable">
                    <tr>
                        <td class="jms-label"><bean:message key="label.template.content" bundle="${bundle}" /></td>
                        <td>
                            <html:textarea styleId="htmlInput" property="htmlInput" styleClass="textarea" />
                        </td>
                    </tr>
                </table>
            </c:when>
            <c:when test="${templateForm.entity.templateType.reportFOP}">
                <table class="nonDataTable">
                    <tr>
                        <td class="jms-label"><bean:message key="label.template.sqlQuery" bundle="${bundle}" /></td>
                        <td>
                            <html:textarea property="entity.sqlQuery" styleClass="textarea" />
                        </td>
                    </tr>
                </table>
            </c:when>
        </c:choose>
    </html:form>

</jsp:root>