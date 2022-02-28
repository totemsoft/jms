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

    <html:form action="/editOwner">
        <html:hidden property="dispatch" value="save" />
        <html:hidden property="editOwner.ownerType.ownerTypeId" />
        <input type="hidden" id="title" value="${title}" />

        <div id="errors" class="error">
            <html:errors />
        </div>

        <c:set var="defaultContact" value="${fileForm.editOwner.defaultContact}" />
        <table class="nonDataTable">
            <html:errors property="editOwner.legalName" />
            <tr>
                <td class="jms-label"><bean:message key="label.owner.legalName" bundle="${bundle}" /></td>
                <td colspan="2">
                    <html:text property="editOwner.legalName" styleClass="text" />
                </td>
                <td class="jms-label"><html:checkbox property="editOwner.defaultContact"><bean:message key="label.owner.defaultContact" bundle="${bundle}" /></html:checkbox></td>
            </tr>
            <tr>
                <td class="jms-label"><bean:message key="label.owner.abn" bundle="${bundle}" /></td>
                <td><html:text property="editOwner.abn" styleClass="text" /></td>
                <c:if test="${fileForm.editOwner.bodyCorporate}">
                    <td class="jms-label"><bean:message key="label.bodyCorporate.reference" bundle="${bundle}" /></td>
                    <td><html:text property="editOwner.reference" styleClass="text" /></td>
                </c:if>
            </tr>
            <c:if test="${fileForm.editOwner.bodyCorporate}">
            <tr>
                <td class="jms-label"><bean:message key="label.bodyCorporate.management" bundle="${bundle}" /></td>
                <td colspan="3"><html:text property="editOwner.management" styleClass="text" /></td>
            </tr>
            </c:if>
        </table>

        <c:set var="beanPrefix" value="editOwner." scope="request" />
        <jsp:include page="/WEB-INF/entity/contact/edit.jsp" />
        <jsp:include page="/WEB-INF/entity/address/edit.jsp" />
        <c:set var="beanPrefix" value="" scope="request" />
    </html:form>

</jsp:root>