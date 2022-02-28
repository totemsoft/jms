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

    <tr>
        <td class="jms-label"><bean:message key="label.fca" /></td>
        <td><html:text property="entity.fca.fcaId" readonly="true" styleClass="text" /></td>
        <c:if test="${empty jobRequestForm.entity.fca.file}">
            <td colspan="2" />
        </c:if>
        <c:if test="${not empty jobRequestForm.entity.fca.file}">
            <td class="jms-label"><bean:message key="label.file" /></td>
            <td><html:text property="entity.fca.file.fileId" readonly="true" styleClass="text" /></td>
        </c:if>
    </tr>

</jsp:root>