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
        <td colspan="4"><html:hidden property="${beanPrefix}address.addressId" /></td>
    </tr>
    <html:errors property="${beanPrefix}address.addrLine1" />
    <tr>
        <td class="jms-label"><bean:message key="label.address.addrLine1" /></td>
        <td colspan="3"><html:text property="${beanPrefix}address.addrLine1" styleClass="text" /></td>
    </tr>
    <tr>
        <td class="jms-label-optional"><bean:message key="label.address.addrLine2" /></td>
        <td colspan="3"><html:text property="${beanPrefix}address.addrLine2" styleClass="text" /></td>
    </tr>
    <html:errors property="${beanPrefix}address.suburb" />
    <tr>
        <td class="jms-label"><bean:message key="label.address.suburb" /></td>
        <td colspan="3"><html:text property="${beanPrefix}address.suburb" styleClass="text" /></td>
    </tr>
    <html:errors property="${beanPrefix}address.postcode" />
    <html:errors property="${beanPrefix}address.state.state" />
    <tr>
        <td class="jms-label-optional"><bean:message key="label.address.postcode" /></td>
        <td><html:text property="${beanPrefix}address.postcode" styleClass="text" /></td>
        <td class="jms-label"><bean:message key="label.address.state" /></td>
        <td>
            <html:select property="${beanPrefix}address.state.state">
                <option value=""><bean:message key="option.select" /></option>
                <html:options collection="states" property="state" labelProperty="state" />
            </html:select>
        </td>
    </tr>

</jsp:root>