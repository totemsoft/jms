<?xml version="1.0" encoding="UTF-8"?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:html="http://struts.apache.org/tags-html"
    xmlns:bean="http://struts.apache.org/tags-bean"
    xmlns:c="http://java.sun.com/jsp/jstl/core"
    xmlns:fn="http://java.sun.com/jsp/jstl/functions"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    version="2.0">

    <html:xhtml/>

    <tr>
        <td colspan="4"><html:hidden property="${beanPrefix}contact.contactId" /><hr/></td>
    </tr>
    <tr>
        <td class="jms-label"><bean:message key="label.contact" /></td>
        <td colspan="3"><html:text property="${beanPrefix}contact.fullName" readonly="true" styleClass="text" /></td>
    </tr>
    <tr>
        <td class="jms-label"><bean:message key="label.contact.phone" /></td>
        <td colspan="3"><html:text property="${beanPrefix}contact.phone" readonly="true" styleClass="text" /></td>
    </tr>
    <tr>
        <td class="jms-label"><bean:message key="label.contact.fax" /></td>
        <td colspan="3"><html:text property="${beanPrefix}contact.fax" readonly="true" styleClass="text" /></td>
    </tr>
    <tr>
        <td class="jms-label"><bean:message key="label.contact.mobile" /></td>
        <td colspan="3"><html:text property="${beanPrefix}contact.mobile" readonly="true" styleClass="text" /></td>
    </tr>
    <tr>
        <td class="jms-label"><bean:message key="label.contact.email" /></td>
        <td colspan="3"><html:text property="${beanPrefix}contact.email" readonly="true" styleClass="text" /></td>
    </tr>
    <!--tr>
        <td class="jms-label"><bean:message key="label.contact.skype" /></td>
        <td colspan="3"><html:text property="${beanPrefix}contact.skype" readonly="true" styleClass="text" /></td>
    </tr-->

</jsp:root>