<?xml version="1.0" encoding="UTF-8"?>
<jsp:root
    xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:html="http://struts.apache.org/tags-html"
    xmlns:bean="http://struts.apache.org/tags-bean"
    xmlns:c="http://java.sun.com/jsp/jstl/core"
    xmlns:fn="http://java.sun.com/jsp/jstl/functions"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    version="2.0">

    <html:xhtml/>

    <html:hidden property="contact.contactId" />

    <html:errors property="contact.salutation.id" />
    <html:errors property="contact.salutation.salutation" />
    <html:errors property="contact.firstName" />
    <html:errors property="contact.surname" />
    <tr>
        <td class="jms-label"><bean:message key="label.contact.salutation" /></td>
        <td>
            <html:select property="contact.salutation.salutation">
                <!--option value=""><bean:message key="option.select" /></option-->
                <html:options collection="salutations" property="salutation" labelProperty="salutation" />
            </html:select>
        </td>
        <td colspan="2" />
    </tr>
    <tr>
        <td class="jms-label"><bean:message key="label.contact.firstName" /></td>
        <td><html:text property="contact.firstName" styleClass="text" /></td>
        <td class="jms-label"><bean:message key="label.contact.surname" /></td>
        <td><html:text property="contact.surname" styleClass="text" /></td>
    </tr>

    <!--jsp:include page="/WEB-INF/entity/contact/edit.jsp" /-->

</jsp:root>