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

    <tr>
        <td class="jms-label"><bean:message key="label.user.responsible" /></td>
        <td colspan="3">
            <html:hidden property="responsibleUser.userId" styleId="userId.edit.value" />
            <html:text property="responsibleUser.contact.name" styleId="userId.edit" styleClass="text" />
            <div id="userId.edit.container"></div>
        </td>
    </tr>

</jsp:root>