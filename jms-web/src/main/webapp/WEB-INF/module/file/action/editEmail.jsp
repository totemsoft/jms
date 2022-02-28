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

    <html:form action="/editEmail" enctype="multipart/form-data">
        <html:hidden property="dispatch" value="saveFileAction" />
        <c:set var="completedDate"><fmt:formatDate value="${fileActionForm.entity.completedDate}" pattern="${datePattern}" /></c:set>
        <html:hidden property="entity.completedDate" value="${completedDate}" />

        <div id="errors" class="error">
            <html:errors />
        </div>

        <table class="nonDataTable">
            <html:errors property="actionCode.actionCodeId" />
            <tr>
                <td class="bold nowrap"><bean:message key="label.actionCode" /></td>
                <td>
                    <html:select property="actionCode.actionCodeId"
                        onchange="YAHOO.jms.sendPostRequest(this.form, this.form.action + '?dispatch=reload', 'scripts/common.js');">
                        <option value=""><bean:message key="option.select" /></option>
                        <html:options collection="actionCodes4type" property="actionCodeId" labelProperty="code" />
                    </html:select>
                </td>
                <td class="bold nowrap"><bean:message key="label.actionType" /></td>
                <td>${fileActionForm.actionType.name}</td>
            </tr>
            <jsp:include page="/WEB-INF/module/file/viewFileFca.jsp" />
            <jsp:include page="/WEB-INF/entity/action/editResponsibleUser.jsp" />
            <html:errors property="entity.dueDate" />
            <tr>
                <td class="jms-label"><bean:message key="label.fileAction.dueDate" bundle="${bundle}" /></td>
                <td>
                    <c:set var="dueDate"><fmt:formatDate value="${fileActionForm.entity.dueDate}" pattern="${datePattern}" /></c:set>
                    <html:text property="entity.dueDate" title="${datePattern}" value="${dueDate}" styleClass="text" styleId="f_date_dueDate" />
                </td>
                <td colspan="2" />
            </tr>
            <html:errors property="entity.destination" />
            <tr>
                <td class="jms-label"><bean:message key="label.fileAction.destination" bundle="${bundle}" /></td>
                <td colspan="3">
                    <table class="nonDataTable" style="border: 1px solid #65A1D7;">
                        <tr>
                            <td>
                                <html:hidden property="contactId" styleId="action.contactId" />
                                <html:radio property="entity.destination" value="${fileActionForm.manualDestination}" onchange="YUD.get('action.contactId').value='';" />
                            </td>
                            <td class="width100" colspan="3"><html:text property="manualDestination" styleClass="text" /></td>
                        </tr>
                        <c:forEach items="${contacts}" var="contact">
                        <c:if test="${not empty contact.email}">
                            <tr>
                                <td><html:radio property="entity.destination" value="${contact.email}" onchange="YUD.get('action.contactId').value=${contact.id};"></html:radio></td>
                                <td>${contact.email}</td>
                                <td class="bold italic">${contact.fullName}</td>
                                <td class="italic">${contact.type}</td>
                            </tr>
                        </c:if>
                        </c:forEach>
                    </table>
                </td>
            </tr>
            <html:errors property="entity.subject" />
            <tr>
                <td class="jms-label"><bean:message key="label.fileAction.subject" bundle="${bundle}" /></td>
                <td colspan="3">
                    <html:text property="entity.subject" styleClass="text"/>
                </td>
            </tr>
            <html:errors property="htmlInput" />
            <html:errors property="entity.notation" />
            <tr>
                <td class="jms-label"><bean:message key="label.fileAction.notation" bundle="${bundle}" /></td>
                <td colspan="3">
                    <html:textarea property="entity.notation" styleClass="textarea" />
                </td>
            </tr>
        </table>
        <c:if test="${not empty fileActionForm.htmlInput}">
            <p>
                <textarea id="htmlInput" name="htmlInput" style="display:none;visibility:hidden;">${fileActionForm.htmlInput}</textarea>
            </p>
        </c:if>
        <jsp:include page="/WEB-INF/entity/action/editAttachment.jsp" />
        <jsp:include page="/WEB-INF/entity/action/editDefaultNextAction.jsp" />
        <c:if test="${fileActionForm.pdfInput}">
            <!-- http://pdfobject.com/generator.php -->
            <!-- http://www.adobe.com/content/dam/Adobe/en/devnet/acrobat/pdfs/pdf_open_parameters.pdf -->
            <object id="pdfViewer" data="file/downloadFileAction.do?dispatch=downloadFileActionAttachment&amp;actionId=${action.id}#page=1&amp;view=FitBH" type="application/pdf" width="100%" height="400px">
                It appears you don't have a PDF plugin for this browser.
            </object>
        </c:if>
    </html:form>

</jsp:root>