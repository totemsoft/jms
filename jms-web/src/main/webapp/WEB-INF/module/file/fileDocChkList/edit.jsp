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

    <html:form action="/editFileDocChkList">
        <html:hidden property="dispatch" value="save" />

        <div id="errors" class="error">
            <html:errors />
        </div>

        <table class="nonDataTable">
            <tr>
                <td colspan="2">
                    <table class="dataTable">
                        <tr>
                            <th class="width100"><bean:message key="label.fileDocChkList.name" bundle="${bundle}" /></th>
                            <th><bean:message key="label.fileDocChkList.docOnFile" bundle="${bundle}" /></th>
                        </tr>
                        <c:set var="count" value="${fn:length(fileForm.entity.fileDocChkLists)}" />
                        <c:if test="${count gt 0}">
                            <c:forEach begin="0" end="${count - 1}" varStatus="status">
                                <c:set var="index" value="${status.index}" />
                                <tr class="${index % 2 eq 0 ? 'odd' : 'even'}">
                                    <td>
                                        ${fileForm.entity.fileDocChkLists[index].docChkList.template.name}
                                    </td>
                                    <td>
                                        <html:hidden property="entity.fileDocChkLists[${index}].docOnFile"
                                            styleId="docOnFile${index}" />
                                        <html:checkbox property="entity.fileDocChkLists[${index}].docOnFile"
                                            onchange="this.form.docOnFile${index}.value=this.checked;" />
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:if>
                    </table>
                </td>
            </tr>
        </table>

    </html:form>

</jsp:root>