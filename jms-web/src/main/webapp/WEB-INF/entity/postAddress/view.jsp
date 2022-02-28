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
        <td colspan="4"><html:hidden property="postAddress.addressId" /><hr/></td>
    </tr>
    <tr>
        <td class="bold"><bean:message key="label.postAddress.addrLine1" /></td>
        <td colspan="3"><html:text property="postAddress.addrLine1" readonly="true" styleClass="text" /></td>
    </tr>
    <tr>
        <td class="bold"><bean:message key="label.postAddress.addrLine2" /></td>
        <td colspan="3"><html:text property="postAddress.addrLine2" readonly="true" styleClass="text" /></td>
    </tr>
    <tr>
        <td class="bold"><bean:message key="label.postAddress.suburb" /></td>
        <td><html:text property="postAddress.suburb" readonly="true" styleClass="text" /></td>
        <td class="bold"><bean:message key="label.postAddress.postcode" /></td>
        <td><html:text property="postAddress.postcode" readonly="true" styleClass="text" /></td>
    </tr>
    <tr>
        <td class="bold"><bean:message key="label.postAddress.state" /></td>
        <td><html:text property="postAddress.state.state" readonly="true" styleClass="text" /></td>
        <td></td>
        <td></td>
    </tr>

</jsp:root>