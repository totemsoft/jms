<?xml version="1.0" encoding="UTF-8"?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:html="http://struts.apache.org/tags-html"
    xmlns:bean="http://struts.apache.org/tags-bean"
    xmlns:c="http://java.sun.com/jsp/jstl/core"
    xmlns:fn="http://java.sun.com/jsp/jstl/functions"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    version="2.0">

    <html:xhtml/>

    <c:set var="bundle" value="file" />

    <html:hidden styleId="${beanPrefix}id" property="${beanPrefix}ownerId" />
    <table class="nonDataTable">
        <tr>
            <td class="jms-label"><bean:message key="label.owner.legalName" bundle="${bundle}" /></td>
            <td colspan="${defaultContact ? '2' : '3'}"><html:text property="${beanPrefix}legalName" readonly="true" styleClass="text" /></td>
            <c:if test="${defaultContact}">
                <td class="jms-label"><html:checkbox property="${beanPrefix}defaultContact" disabled="true"><bean:message key="label.owner.defaultContact" bundle="${bundle}" /></html:checkbox></td>
            </c:if>
        </tr>
        <tr>
            <td class="jms-label"><bean:message key="label.owner.abn" bundle="${bundle}" /></td>
            <td><html:text property="${beanPrefix}abn" readonly="true" styleClass="text" /></td>
            <c:if test="${owner eq 'bodyCorporate'}">
                <td class="jms-label"><bean:message key="label.bodyCorporate.reference" bundle="${bundle}" /></td>
                <td><html:text property="${beanPrefix}reference" readonly="true" styleClass="text" /></td>
            </c:if>
        </tr>
        <c:if test="${owner eq 'bodyCorporate'}">
        <tr>
            <td class="jms-label"><bean:message key="label.bodyCorporate.management" bundle="${bundle}" /></td>
            <td colspan="3"><html:text property="${beanPrefix}management" readonly="true" styleClass="text" /></td>
        </tr>
        </c:if>
        <jsp:include page="/WEB-INF/entity/contact/view.jsp" />
        <jsp:include page="/WEB-INF/entity/address/view.jsp" />
    </table>

</jsp:root>