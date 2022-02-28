<?xml version="1.0" encoding="UTF-8"?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:html="http://struts.apache.org/tags-html"
    xmlns:bean="http://struts.apache.org/tags-bean"
    xmlns:c="http://java.sun.com/jsp/jstl/core"
    xmlns:fn="http://java.sun.com/jsp/jstl/functions"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    version="2.0">

    <html:xhtml/>

    <html:hidden property="address.addressId" />

    <tr>
        <td class="bold nowrap"><bean:message key="label.address.addrLine1" /></td>
        <td colspan="3"><html:text property="address.addrLine1" readonly="true" styleClass="text" /></td>
    </tr>
    <tr>
        <td class="bold nowrap"><bean:message key="label.address.addrLine2" /></td>
        <td colspan="3"><html:text property="address.addrLine2" readonly="true" styleClass="text" /></td>
    </tr>
    <tr>
        <td class="bold nowrap"><bean:message key="label.address.suburb" /></td>
        <td><html:text property="address.suburb" readonly="true" styleClass="text" /></td>
        <td class="bold nowrap"><bean:message key="label.address.postcode" /></td>
        <td><html:text property="address.postcode" readonly="true" styleClass="text" /></td>
    </tr>
    <tr>
        <td class="bold nowrap"><bean:message key="label.address.state" /></td>
        <td><html:text property="address.state.state" readonly="true" styleClass="text" /></td>
        <td></td>
        <td></td>
    </tr>

</jsp:root>