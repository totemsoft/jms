<?xml version="1.0" encoding="UTF-8"?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:html="http://struts.apache.org/tags-html"
    xmlns:bean="http://struts.apache.org/tags-bean"
    xmlns:c="http://java.sun.com/jsp/jstl/core"
    xmlns:fn="http://java.sun.com/jsp/jstl/functions"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    version="2.0">

    <html:xhtml/>

    <html:hidden property="contact.contactId" />

    <tr>
        <td class="bold"><bean:message key="label.contact.salutation" /></td>
        <td><html:text property="contact.salutation.id" readonly="true" styleClass="text" /></td>
        <td colspan="2" />
        <!--td class="bold"><bean:message key="label.contact.phone" /></td>
        <td><html:text property="contact.phone" readonly="true" styleClass="text" /></td-->
    </tr>
    <tr>
        <td class="bold"><bean:message key="label.contact.firstName" /></td>
        <td><html:text property="contact.firstName" readonly="true" styleClass="text" /></td>
        <td colspan="2" />
        <!--td class="bold"><bean:message key="label.contact.fax" /></td>
        <td><html:text property="contact.fax" readonly="true" styleClass="text" /></td>
    </tr>
    <tr-->
        <td class="bold"><bean:message key="label.contact.surname" /></td>
        <td><html:text property="contact.surname" readonly="true" styleClass="text" /></td>
        <td colspan="2" />
        <!--td class="bold"><bean:message key="label.contact.mobile" /></td>
        <td><html:text property="contact.mobile" readonly="true" styleClass="text" /></td-->
    </tr>
    <!--tr>
        <td class="bold"><bean:message key="label.contact.email" /></td>
        <td><html:text property="contact.email" readonly="true" styleClass="text" /></td>
        <td class="bold"><bean:message key="label.contact.skype" /></td>
        <td><html:text property="contact.skype" readonly="true" styleClass="text" /></td>
    </tr-->

</jsp:root>