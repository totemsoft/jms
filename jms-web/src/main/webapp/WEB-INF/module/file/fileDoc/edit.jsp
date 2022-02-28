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

    <html:form action="/editFileDoc" enctype="multipart/form-data">
        <html:hidden property="dispatch" value="save" />

        <div id="errors" class="error">
            <html:errors />
        </div>

        <table class="nonDataTable">
            <tr>
                <td colspan="2">
                    <table class="dataTable">
                        <tr>
                            <th><bean:message key="label.fileDoc.fileDocId" bundle="${bundle}" /></th>
                            <th><bean:message key="label.fileDoc.createdDate" bundle="${bundle}" /></th>
                            <th><bean:message key="label.fileDoc.docType" bundle="${bundle}" /></th>
                            <th><bean:message key="label.fileDoc.description" bundle="${bundle}" /></th>
                            <th class="width100"><bean:message key="label.fileDoc.location" bundle="${bundle}" /></th>
                            <th colspan="2"></th>
                        </tr>
                        <c:set var="count" value="${fn:length(fileForm.entity.fileDocs)}" />
                        <c:if test="${count gt 0}">
                            <c:forEach begin="0" end="${count - 1}" varStatus="status">
                                <c:set var="index" value="${status.index}" />
                                <c:set var="hasDocument" value="${not empty fileForm.entity.fileDocs[index].document.documentId}" />
                                <tr class="${index % 2 eq 0 ? 'odd' : 'even'}">
                                    <td>${fileForm.entity.fileDocs[index].fileDocId}</td>
                                    <td><fmt:formatDate value="${fileForm.entity.fileDocs[index].createdDate}" pattern="${datePattern}" /></td>
                                    <td>
                                        <html:select property="entity.fileDocs[${status.index}].docType.docTypeId">
                                            <option value=""><bean:message key="option.select" /></option>
                                            <html:options collection="docTypes" property="docTypeId" labelProperty="name" />
                                        </html:select>
                                    </td>
                                    <td>
                                        <html:text property="entity.fileDocs[${index}].description" styleClass="text" />
                                    </td>
                                    <td>
                                        <html:text property="entity.fileDocs[${index}].location" styleId="location${index}" styleClass="text" readonly="${hasDocument}" />
                                    </td>
                                    <td>
                                        <c:if test="${not empty fileForm.entity.fileDocs[index].fileDocId}">
                                            <html:link href="javascript:;" titleKey="link.fileDoc.move" bundle="${bundle}"
                                                onclick="YAHOO.jms.sendGetRequest('file/moveFileDoc.do?index=${index}', null, 'Move Document');">
                                                <bean:message key="link.move" />
                                            </html:link>
                                        </c:if>
                                    </td>
                                    <td>
                                        <html:link href="javascript:;" titleKey="link.fileDoc.remove" bundle="${bundle}"
                                            onclick="YAHOO.jms.sendPostRequest('fileForm', 'file/editFileDoc.do?dispatch=removeFileDoc&amp;index=${index}');">
                                            <span class="delete">&#160;</span>
                                        </html:link>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="7">
                                        <input type="hidden" name="attachmentIndex[${index}]" value="${index}" />
                                        <c:if test="${not hasDocument}">
                                            <input type="file" id="uploadFile${index}" value="" size="70" name="attachmentFiles[${index}]"
                                                onchange="this.form.location${index}.value=YAHOO.jms.onchangeUploadFile(this);" />
                                        </c:if>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:if>
                        <tr>
                            <th colspan="7">
                                <html:link href="javascript:;" titleKey="link.fileDoc.add" bundle="${bundle}"
                                    onclick="YAHOO.jms.sendPostRequest('fileForm', 'file/editFileDoc.do?dispatch=addFileDoc');">
                                    <html:img src="images/new.png" /><bean:message key="link.fileDoc.add" bundle="${bundle}" />
                                </html:link>
                            </th>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>

    </html:form>

</jsp:root>