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

    <table class="nonDataTable">
        <tr>
            <td class="jms-label"><bean:message key="label.sapHeader.sapCustNo" bundle="${bundle}" /></td>
            <c:set var="modified" value="${fileAuditForm.entity.sapHeader.sapCustNo ne original.sapHeader.sapCustNo}" />
            <td><html:text property="entity.sapHeader.sapCustNo" readonly="${readonly}" styleClass="text numberInput ${modified ? 'modified' : ''}" /></td>
        </tr>
    </table>

</jsp:root>