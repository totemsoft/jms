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

    <tr>
        <td colspan="4"><html:hidden property="${beanPrefix}contact.contactId" /></td>
    </tr>

    <html:errors property="${beanPrefix}contact.salutation.id" />
    <html:errors property="${beanPrefix}contact.salutation.salutation" />
    <tr>
        <td class="jms-label"><bean:message key="label.contact.salutation" /></td>
        <td colspan="3">
            <html:select property="${beanPrefix}contact.salutation.salutation">
                <!--option value=""><bean:message key="option.select" /></option-->
                <html:options collection="salutations" property="salutation" labelProperty="salutation" />
            </html:select>
        </td>
    </tr>

    <html:errors property="${beanPrefix}contact.firstName" />
    <html:errors property="${beanPrefix}contact.surname" />
    <tr>
        <td class="jms-label"><bean:message key="label.contact.firstName" /></td>
        <td><html:text property="${beanPrefix}contact.firstName" styleClass="text" /></td>
        <td class="jms-label"><bean:message key="label.contact.surname" /></td>
        <td><html:text property="${beanPrefix}contact.surname" styleClass="text" /></td>
    </tr>

    <html:errors property="${beanPrefix}contact.email" />
    <tr>
        <td class="jms-label"><bean:message key="label.contact.email" /></td>
        <td colspan="3"><html:text property="${beanPrefix}contact.email" styleClass="text" /></td>
    </tr>
    <html:errors property="${beanPrefix}contact.emailConfirm" />
    <tr class="hidden">
        <td class="jms-label-optional"><bean:message key="label.contact.emailConfirm" /></td>
        <td colspan="3"><html:text property="${beanPrefix}contact.emailConfirm" styleClass="text" /></td>
    </tr>

    <tr>
        <td class="jms-label"><bean:message key="label.contact.phone" /></td>
        <td><html:text property="${beanPrefix}contact.phone" styleClass="text" /></td>
        <td class="jms-label"><bean:message key="label.contact.fax" /></td>
        <td><html:text property="${beanPrefix}contact.fax" styleClass="text" /></td>
    </tr>

    <tr>
        <td class="jms-label"><bean:message key="label.contact.mobile" /></td>
        <td><html:text property="${beanPrefix}contact.mobile" styleClass="text" /></td>
        <td colspan="2" />
        <!--td class="jms-label"><bean:message key="label.contact.skype" /></td>
        <td><html:text property="${beanPrefix}contact.skype" styleClass="text" /></td-->
    </tr>

</jsp:root>