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

    <html:errors property="file.fileId" />
    <html:errors property="file.fca.fcaId" />
    <tr>
        <td class="jms-label"><bean:message key="label.fca" /></td>
        <td>
            <div>
                <html:text property="file.fca.fcaId" styleId="fcaId.edit" styleClass="text" />
                <div id="fcaId.edit.container"></div>
            </div>
        </td>
        <td class="jms-label"><bean:message key="label.file" /></td>
        <td>
            <html:text property="file.fileId" readonly="true" styleId="fcaId.edit.value" styleClass="text" />
        </td>
    </tr>
    <c:if test="${sapCustNo}">
    <tr>
        <td class="jms-label"><bean:message key="label.sapHeader.sapCustNo" bundle="file" /></td>
        <td>
            <div>
                <html:text property="file.sapHeader.sapCustNo" styleId="sapCustNo.edit" styleClass="text" />
                <div id="sapCustNo.edit.container"></div>
            </div>
        </td>
        <td colspan="2" />
    </tr>
    <tr>
        <td colspan="4"><hr /></td>
    </tr>
    </c:if>
</jsp:root>