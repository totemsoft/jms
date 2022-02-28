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

    <c:set var="bundle" value="file" />

    <html:form action="/editMultiSite">
        <html:hidden property="dispatch" value="save" />

        <div id="errors" class="error">
            <html:errors />
        </div>

        <c:set var="parent" value="${fileForm.parent}" />
        <table class="dataTable">
	        <caption><bean:message key="label.file.parent" bundle="${bundle}" /></caption>
	        <tr>
	            <th class="width50"><bean:message key="label.fca" /></th>
	            <th class="width40"><bean:message key="label.file" /></th>
	            <th>&#160;</th>
	        </tr>
	        <html:errors property="parent.value" />
	        <tr>
	            <td>
	                <div>
	                    <html:text property="parent.value" styleId="fcaId.parent" />
	                    <div id="fcaId.parent.container"></div>
	                </div>
	            </td>
	            <td>
	                <html:text property="parent.key" readonly="true" styleId="fcaId.parent.value" />
	            </td>
	            <td>
                    <html:link href="javascript:;" titleKey="link.multiSite.remove" bundle="${bundle}"
                        onclick="YAHOO.jms.sendPostRequest(document.forms[0], document.forms[0].action + '?dispatch=removeParent');">
                        <bean:message key="link.remove" />
                    </html:link>
	            </td>
	        </tr>
            <tr>
                <th colspan="1" class="noborder" />
                <th colspan="2" class="noborder">
                    <html:link href="javascript:;" titleKey="link.multiSite.addParent" bundle="${bundle}"
                        onclick="YAHOO.jms.sendPostRequest(document.forms[0], document.forms[0].action + '?dispatch=addParent');">
                        <bean:message key="link.multiSite.addParent" bundle="${bundle}" />
                    </html:link>
                </th>
            </tr>
        </table>

	    <c:set var="children" value="${fileForm.children}" />
	    <table class="dataTable">
	        <caption><bean:message key="label.file.children" bundle="${bundle}" /></caption>
	        <tr>
	            <th class="width50"><bean:message key="label.fca" /></th>
	            <th class="width40"><bean:message key="label.file" /></th>
	            <th>&#160;</th>
	        </tr>
	        <c:forEach items="${children}" var="item" varStatus="status">
	            <c:set var="index" value="${status.index}" />
    	        <html:errors property="children[${index}].value" />
	            <tr class="${index % 2 eq 0 ? 'odd' : 'even'}">
	                <td>
	                    <div>
	                        <html:text property="children[${index}].value" styleId="fcaId.child.${index}" styleClass="" />
	                        <div id="fcaId.child.${index}.container"></div>
	                    </div>
	                </td>
	                <td>
	                    <html:text property="children[${index}].key" readonly="true" styleId="fcaId.child.${index}.value" styleClass="" />
	                </td>
	                <td>
                        <html:link href="javascript:;" titleKey="link.multiSite.remove" bundle="${bundle}"
                            onclick="YAHOO.jms.sendPostRequest(document.forms[0], document.forms[0].action + '?dispatch=removeChild&amp;index=${index}');">
                            <bean:message key="link.remove" />
                        </html:link>
	                </td>
	            </tr>
	        </c:forEach>
            <tr>
                <th colspan="1" class="noborder">
                    <html:link href="javascript:;" titleKey="link.multiSite.add" bundle="${bundle}"
                        onclick="YAHOO.jms.sendPostRequest(document.forms[0], document.forms[0].action + '?dispatch=addChild');">
                        <bean:message key="link.multiSite.add" bundle="${bundle}" />
                    </html:link>
                </th>
                <th colspan="2" class="noborder">
                    <html:link href="javascript:;" titleKey="link.multiSite.addSubPanel" bundle="${bundle}"
                        onclick="YAHOO.jms.sendPostRequest(document.forms[0], document.forms[0].action + '?dispatch=addSubPanel');">
                        <bean:message key="link.multiSite.addSubPanel" bundle="${bundle}" />
                    </html:link>
                </th>
            </tr>
	    </table>
    </html:form>

</jsp:root>