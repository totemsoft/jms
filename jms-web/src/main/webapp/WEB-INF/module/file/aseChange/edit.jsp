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

    <html:form action="/editAseChange">
        <html:hidden property="dispatch" value="save" />

        <div id="errors" class="error">
            <html:errors />
        </div>

        <table class="nonDataTable">
            <caption>Change Over Schedule</caption>
            <html:errors property="entity.fca.station.stationId" />
            <html:errors property="entity.aseFile.createdDate" />
            <html:errors property="aseDateChange" />
            <html:errors property="aseTimeChange" />
            <tr>
                <fmt:formatDate var="date" value="${fileForm.entity.aseFile.aseChange.dateChange}" pattern="${datePattern}"/>
                <fmt:formatDate var="time" value="${fileForm.entity.aseFile.aseChange.dateChange}" pattern="${timePattern}"/>
                <td class="jms-label"><bean:message key="label.date" /></td>
                <td>
                    <html:text property="aseDateChange" value="${date}" title="${datePattern}" maxlength="10" styleClass="text" styleId="f_date_aseChange" />
                </td>
                <td class="jms-label"><bean:message key="label.time" /></td>
                <td>
                    <html:text property="aseTimeChange" value="${time}" title="${timePattern}" maxlength="5" styleClass="text" styleId="f_time_aseChange" />
                </td>
            </tr>
        </table>

        <table class="nonDataTable">
            <caption>ASE Installation</caption>
            <tr>
                <td>
                    <table class="dataTable">
                        <tr>
                             <th><bean:message key="label.supplier" /></th>
                             <th><bean:message key="label.aseConnType" /></th>
                             <th class="width100"><bean:message key="label.aseChangeSupplier.notation" bundle="${bundle}" /></th>
                             <th><bean:message key="label.aseChangeSupplier.dateCompleted" bundle="${bundle}" /></th>
                             <th></th>
                        </tr>
                        <c:set var="count" value="${fn:length(fileForm.aseInstallationAseChangeSuppliers)}" />
                        <c:if test="${count gt 0}">
                            <c:forEach begin="0" end="${count - 1}" varStatus="status">
                                <c:set var="index" value="${status.index}" />
                                <c:set var="item" value="${fileForm.aseInstallationAseChangeSuppliers[index]}" />
                                <html:errors property="aseInstallationAseChangeSuppliers[${index}].supplier.supplierId" />
                                <html:errors property="aseInstallationAseChangeSuppliers[${index}].aseConnType.aseConnTypeId" />
                                <html:errors property="aseInstallationAseChangeSuppliers[${index}].notation" />
                                <c:if test="${empty item.logicallyDeleted}">
                                    <tr class="${index % 2 eq 0 ? 'odd' : 'even'}">
                                        <td>
                                            <html:select property="aseInstallationAseChangeSuppliers[${index}].supplier.supplierId">
                                                <option value=""><bean:message key="option.select" /></option>
                                                <html:options collection="aseInstallationSuppliers" property="supplierId" labelProperty="name" />
                                            </html:select>
                                        </td>
                                        <td>
                                            <html:select property="aseInstallationAseChangeSuppliers[${index}].aseConnType.aseConnTypeId">
                                                <option value=""><bean:message key="option.select" /></option>
                                                <html:options collection="aseConnTypes" property="aseConnTypeId" labelProperty="name" />
                                            </html:select>
                                        </td>
                                        <td>
                                            <html:textarea property="aseInstallationAseChangeSuppliers[${index}].notation" styleClass="textarea" />
                                        </td>
                                        <td class="center">
                                            <c:if test="${empty item.dateCompleted}">
                                                <a title="Complete ASE Change"
                                                    href="javascript:YAHOO.jms.sendPostRequest(document.forms[0], document.forms[0].action + '?dispatch=completeAseChangeSupplier&amp;aseInstallation=true&amp;id=${item.id}', 'scripts/common.js');">
                                                    <span class="attention">&#160;</span>
                                                </a>
                                            </c:if>
                                            <c:if test="${not empty item.dateCompleted}">
                                                <fmt:formatDate value="${item.dateCompleted}" pattern="${datePattern}" />
                                            </c:if>
                                        </td>
                                        <td>
                                            <c:if test="${empty item.dateCompleted}">
                                                <html:link href="javascript:;" titleKey="link.aseChangeSupplier.remove" bundle="${bundle}"
                                                    onclick="YAHOO.jms.sendPostRequest(document.forms[0], document.forms[0].action + '?dispatch=removeAseChangeSupplier&amp;aseInstallation=true&amp;index=${index}', 'scripts/common.js');">
                                                    <span class="delete">&#160;</span>
                                                </html:link>
                                            </c:if>
                                        </td>
                                    </tr>
                                </c:if>
                            </c:forEach>
                        </c:if>
                        <tr>
                            <td colspan="5">
                                <html:link href="javascript:;" titleKey="label.aseChangeSupplier.aseInstallation.add" bundle="${bundle}"
                                    onclick="YAHOO.jms.sendPostRequest(document.forms[0], document.forms[0].action + '?dispatch=addAseChangeSupplier&amp;aseInstallation=true', 'scripts/common.js');">
                                    <html:img src="images/new.png" /><bean:message key="link.aseChangeSupplier.add" bundle="${bundle}" />
                                </html:link>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>

        <table class="nonDataTable">
            <caption>Carrier Installation</caption>
            <tr>
                <td>
                    <table class="dataTable">
                        <tr>
                             <th><bean:message key="label.supplier" /></th>
                             <th><bean:message key="label.aseConnType" /></th>
                             <th class="width100"><bean:message key="label.aseChangeSupplier.notation" bundle="${bundle}" /></th>
                             <th><bean:message key="label.aseChangeSupplier.dateCompleted" bundle="${bundle}" /></th>
                             <th></th>
                        </tr>
                        <c:set var="count" value="${fn:length(fileForm.telcoAseChangeSuppliers)}" />
                        <c:if test="${count gt 0}">
                            <c:forEach begin="0" end="${count - 1}" varStatus="status">
                                <c:set var="index" value="${status.index}" />
                                <c:set var="item" value="${fileForm.telcoAseChangeSuppliers[index]}" />
                                <html:errors property="telcoAseChangeSuppliers[${index}].supplier.supplierId" />
                                <html:errors property="telcoAseChangeSuppliers[${index}].aseConnType.aseConnTypeId" />
                                <html:errors property="telcoAseChangeSuppliers[${index}].notation" />
                                <c:if test="${empty item.logicallyDeleted}">
                                    <tr class="${index % 2 eq 0 ? 'odd' : 'even'}">
                                        <td>
                                            <html:select property="telcoAseChangeSuppliers[${index}].supplier.supplierId">
                                                <option value=""><bean:message key="option.select" /></option>
                                                <html:options collection="telcoSuppliers" property="supplierId" labelProperty="name" />
                                            </html:select>
                                        </td>
                                        <td>
                                            <html:select property="telcoAseChangeSuppliers[${index}].aseConnType.aseConnTypeId">
                                                <option value=""><bean:message key="option.select" /></option>
                                                <html:options collection="aseConnTypes" property="aseConnTypeId" labelProperty="name" />
                                            </html:select>
                                        </td>
                                        <td>
                                            <html:textarea property="telcoAseChangeSuppliers[${index}].notation" styleClass="textarea" />
                                        </td>
                                        <td class="center">
                                            <c:if test="${empty item.dateCompleted}">
                                                <a title="Complete ASE Change"
                                                    href="javascript:YAHOO.jms.sendPostRequest(document.forms[0], document.forms[0].action + '?dispatch=completeAseChangeSupplier&amp;telco=true&amp;id=${item.id}', 'scripts/common.js');">
                                                    <span class="attention">&#160;</span>
                                                </a>
                                            </c:if>
                                            <c:if test="${not empty item.dateCompleted}">
                                                <fmt:formatDate value="${item.dateCompleted}" pattern="${datePattern}" />
                                            </c:if>
                                        </td>
                                        <td>
                                            <c:if test="${empty item.dateCompleted}">
                                                <html:link href="javascript:;" titleKey="link.aseChangeSupplier.remove" bundle="${bundle}"
                                                    onclick="YAHOO.jms.sendPostRequest(document.forms[0], document.forms[0].action + '?dispatch=removeAseChangeSupplier&amp;telco=true&amp;index=${index}', 'scripts/common.js');">
                                                    <span class="delete">&#160;</span>
                                                </html:link>
                                            </c:if>
                                        </td>
                                    </tr>
                                </c:if>
                            </c:forEach>
                        </c:if>
                        <tr>
                            <td colspan="5">
                                <html:link href="javascript:;" titleKey="label.aseChangeSupplier.telco.add" bundle="${bundle}"
                                    onclick="YAHOO.jms.sendPostRequest(document.forms[0], document.forms[0].action + '?dispatch=addAseChangeSupplier&amp;telco=true', 'scripts/common.js');">
                                    <html:img src="images/new.png" /><bean:message key="link.aseChangeSupplier.add" bundle="${bundle}" />
                                </html:link>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
    </html:form>

</jsp:root>