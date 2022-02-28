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

    <html:form action="/moveFileDoc">
        <html:hidden property="dispatch" value="moveFileDocSave" />

        <div id="errors" class="error">
            <html:errors />
        </div>

        <table class="nonDataTable">
            <caption>FROM</caption>
            <tr>
                <td class="jms-label"><bean:message key="label.file" /></td>
                <td><html:text property="entity.file.fileId" readonly="true" styleClass="text" /></td>
                <c:if test="${empty fileDocForm.entity.file.fca}">
                    <td colspan="2" />
                </c:if>
                <c:if test="${not empty fileDocForm.entity.file.fca}">
                    <td class="jms-label"><bean:message key="label.fca" /></td>
                    <td><html:text property="entity.file.fca.fcaId" readonly="true" styleClass="text" /></td>
                </c:if>
            </tr>
            <tr>
                <td class="jms-label"><bean:message key="label.fileDoc.fileDocId" bundle="${bundle}" /></td>
                <td><html:text property="entity.fileDocId" readonly="true" styleClass="text" /></td>
                <td class="jms-label"><bean:message key="label.fileDoc.description" bundle="${bundle}" /></td>
                <td><html:text property="entity.description" readonly="true" styleClass="text" /></td>
            </tr>
        </table>

        <table class="nonDataTable">
            <caption>TO</caption>
            <html:errors property="moveFileId" />
            <html:errors property="moveFcaId" />
            <tr>
                <td class="jms-label"><bean:message key="label.file" /></td>
                <td>
                    <div>
                        <html:text styleId="fileId.input" property="moveFileId" styleClass="text" />
                        <div id="fileId.container"></div>
                    </div>
                </td>
                <td class="jms-label"><bean:message key="label.fca" /></td>
                <td>
                    <div>
                        <html:text styleId="fcaId.input" property="moveFcaId" styleClass="text" />
                        <div id="fcaId.container"></div>
                    </div>
                </td>
            </tr>
        </table>

    </html:form>

</jsp:root>