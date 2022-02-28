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

    <html:form action="/editSapBilling">
        <html:hidden property="dispatch" value="save" />

        <div id="errors" class="error">
            <html:errors />
        </div>

        <table class="nonDataTable">
            <tr>
                <td class="jms-label"><bean:message key="label.sapHeader.sapCustNo" bundle="${bundle}" /></td>
                <td><html:text property="entity.sapHeader.sapCustNo" styleClass="text numberInput" /></td>
            </tr>
            <tr>
                <td colspan="2">
                    <table class="dataTable">
                        <caption>RFI Details</caption>
                        <tr>
                            <th><bean:message key="label.rfi.rfiId" bundle="${bundle}" /></th>
                            <th><bean:message key="label.rfi.fileAction" bundle="${bundle}" /></th>
                            <th><bean:message key="label.rfi.description" bundle="${bundle}" /></th>
                            <th><bean:message key="label.rfi.sapInvNo" bundle="${bundle}" /></th>
                            <th></th>
                        </tr>
                        <c:set var="count" value="${fn:length(fileForm.entity.rfis)}" />
                        <c:if test="${count gt 0}">
                            <c:forEach begin="0" end="${count - 1}" varStatus="status">
                                <c:set var="index" value="${status.index}" />
                                <tr class="${index % 2 eq 0 ? 'odd' : 'even'}">
                                    <td>
                                        <html:text property="entity.rfis[${index}].rfiId" readonly="true" styleClass="text numberInput" />
                                    </td>
                                    <td>
                                        <html:text property="entity.rfis[${index}].fileAction.actionCode.code" readonly="true" styleClass="text" />
                                    </td>
                                    <td>
                                        <html:text property="entity.rfis[${index}].description" styleClass="text" />
                                    </td>
                                    <td>
                                        <html:text property="entity.rfis[${index}].sapInvNo" styleClass="text" />
                                    </td>
                                    <td>
                                        <html:link href="javascript:;" titleKey="link.rfi.remove" bundle="${bundle}"
                                            onclick="YAHOO.jms.sendPostRequest('fileForm', 'file/editSapBilling.do?dispatch=removeRfi&amp;index=${index}');">
                                            <bean:message key="link.remove" />
                                        </html:link>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:if>
                    </table>
                </td>
            </tr>
        </table>

    </html:form>

</jsp:root>