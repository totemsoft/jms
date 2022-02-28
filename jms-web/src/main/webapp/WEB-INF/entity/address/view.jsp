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
        <td colspan="4"><html:hidden property="${beanPrefix}address.addressId" /><hr/></td>
    </tr>
    <tr>
        <td class="jms-label"><bean:message key="label.address" /></td>
        <td colspan="3"><html:textarea property="${beanPrefix}address.fullAddress" readonly="true" styleClass="height4" /></td>
    </tr>

</jsp:root>