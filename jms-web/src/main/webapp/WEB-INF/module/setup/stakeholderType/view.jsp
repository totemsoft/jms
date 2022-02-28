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

    <html:form action="/viewStakeholderType" method="get">
        <html:hidden property="entity.stakeHolderTypeId" />
        <table class="nonDataTable">
            <tr>
                <td class="jms-label"><bean:message key="label.stakeholderType.name" bundle="${bundle}" /></td>
                <td>
                    <html:text property="entity.name" readonly="true" styleClass="text" />
                </td>
            </tr>
        </table>
    </html:form>

</jsp:root>