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

    <table class="noborder">
        <caption><bean:message key="label.postAddress" /></caption>
        <tr>
            <td class="italic" colspan="2">
                <html:hidden property="postAddress.addressId" />
                <html:hidden property="postAddress.sameAs" styleId="sameAs" />
                <a name="sameAs" />
                <html:checkbox property="postAddress.sameAs"
                    onchange="this.form.sameAs.value=this.checked;YAHOO.jms.sendPostRequest(this.form, this.form.action + '?dispatch=changeSameAsAddress&amp;1=1#sameAs');">
                    &#160;<bean:message key="label.postAddress.sameAs" />
                </html:checkbox>
            </td>
        </tr>
        <html:errors property="postAddress.addrLine1" />
        <tr>
            <td class="jms-label"><bean:message key="label.postAddress.addrLine1" /></td>
            <td><html:text property="postAddress.addrLine1" styleClass="text" /></td>
        </tr>
        <tr>
            <td class="jms-label-optional"><bean:message key="label.postAddress.addrLine2" /></td>
            <td><html:text property="postAddress.addrLine2" styleClass="text" /></td>
        </tr>
        <html:errors property="postAddress.suburb" />
        <tr>
            <td class="jms-label"><bean:message key="label.postAddress.suburb" /></td>
            <td><html:text property="postAddress.suburb" styleClass="text" /></td>
        </tr>
        <tr>
            <td class="jms-label-optional"><bean:message key="label.postAddress.postcode" /></td>
            <td><html:text property="postAddress.postcode" styleClass="text" /></td>
        </tr>
        <html:errors property="postAddress.state.state" />
        <tr>
            <td class="jms-label"><bean:message key="label.postAddress.state" /></td>
            <td>
                <html:select property="postAddress.state.state">
                    <option value=""><bean:message key="option.select" /></option>
                    <html:options collection="states" property="state" labelProperty="state" />
                </html:select>
            </td>
        </tr>
    </table>

</jsp:root>