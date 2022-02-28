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

    <c:set var="bundle" value="setup" />

    <html:form action="/editSecurityGroup">
        <html:hidden property="dispatch" value="save" />
        <html:hidden property="entity.securityGroupId" />

        <div id="errors" class="error">
            <html:errors />
        </div>

        <table class="nonDataTable">
            <html:errors property="entity.name" />
            <html:errors property="entity.description" />
            <tr>
                <td class="jms-label"><bean:message key="label.securityGroup.name" bundle="${bundle}" /></td>
                <td><html:text property="entity.name" readonly="${not empty securityGroupForm.entity.securityGroupId}" styleClass="text" /></td>
                <td class="jms-label"><bean:message key="label.securityGroup.description" bundle="${bundle}" /></td>
                <td><html:text property="entity.description" styleClass="text" /></td>
            </tr>
            <tr>
                <td class="jms-label"><bean:message key="label.systemFunction" bundle="${bundle}" /></td>
                <td colspan="3">
                    <table class="width100">
                        <c:forEach items="${systemFunctionsMap}" var="entry">
                         <c:set var="module" value="${entry.key}" />
                         <c:set var="items" value="${entry.value}" />

                            <tr><th colspan="2">Module: ${module}</th></tr>

                         <c:forEach begin="0" end="${fn:length(items) - 1}" step="2" varStatus="status">
                             <c:set var="item1" value="${items[status.index]}" />
                             <c:set var="item2" value="${items[status.index + 1]}" />
                             <tr>
                                 <td title="${item1.description}"><html:multibox property="systemFunctionIds" value="${item1.systemFunctionId}" />${nbsp}${item1.name}</td>
 
                                 <c:if test="${not empty item2}">
                                     <td title="${item2.description}"><html:multibox property="systemFunctionIds" value="${item2.systemFunctionId}" />${nbsp}${item2.name}</td>
                                 </c:if>
                                 <c:if test="${empty item2}">
                                     <td></td>
                                 </c:if>
                             </tr>
                         </c:forEach>
                        </c:forEach>
                    </table>
                </td>
            </tr>
        </table>
    </html:form>

</jsp:root>