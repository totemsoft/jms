<?xml version="1.0" encoding="UTF-8"?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:html="http://struts.apache.org/tags-html"
    xmlns:bean="http://struts.apache.org/tags-bean"
    xmlns:logic="http://struts.apache.org/tags-logic"
    xmlns:c="http://java.sun.com/jsp/jstl/core"
    xmlns:fn="http://java.sun.com/jsp/jstl/functions"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    xmlns:argus-html="/WEB-INF/tld/argus-html.tld"
    xmlns:perm="/WEB-INF/tld/argus-perm.tld"
    version="2.0">

    <html:xhtml/>

    <c:set var="bundle" value="finance" />

    <c:set var="title"><bean:message key="label.finance.invoice" bundle="${bundle}" /></c:set>
    <argus-html:window id="invoice" title="${title}" collapse="true">
    <table class="nonDataTable">
        <tr>
            <td class="jms-label"><bean:message key="label.finance.invoice.invoiceType" bundle="${bundle}" /></td>
            <td><input type="text" readonly="readonly" value="${entity.invoiceType.name}" class="text" /></td>
            <td class="jms-label"><bean:message key="label.finance.invoice.invoiceArea" bundle="${bundle}" /></td>
            <td><input type="text" readonly="readonly" value="${entity.invoiceArea.name}" class="text" /></td>
        </tr>
        <tr>
            <td class="jms-label"><bean:message key="label.finance.invoice.fiscalYear" bundle="${bundle}" /></td>
            <td><input type="text" readonly="readonly" value="${entity.fiscalYear}" class="text" /></td>
            <td class="jms-label"><bean:message key="label.finance.invoice.region" bundle="${bundle}" /></td>
            <td><input type="text" readonly="readonly" value="${entity.region.name}" class="text" /></td>
        </tr>
        <tr>
            <td class="jms-label"><bean:message key="label.finance.invoice.brigade" bundle="${bundle}" /></td>
            <td><input type="text" readonly="readonly" value="${entity.brigade.name}" class="text" /></td>
            <td class="jms-label"><bean:message key="label.finance.invoice.receiptBatch" bundle="${bundle}" /></td>
            <td><input type="text" readonly="readonly" value="${entity.receiptBatch}" class="text" /></td>
        </tr>
        <tr>
            <fmt:formatDate var="dateReceived" value="${entity.dateReceived}" pattern="${datePattern}" />
            <td class="jms-label"><bean:message key="label.finance.invoice.dateReceived" bundle="${bundle}" /></td>
            <td><input type="text" readonly="readonly" value="${dateReceived}" class="text" /></td>
            <fmt:formatDate var="dateActioned" value="${entity.dateActioned}" pattern="${datePattern}" />
            <td class="jms-label"><bean:message key="label.finance.invoice.dateActioned" bundle="${bundle}" /></td>
            <td><input type="text" readonly="readonly" value="${dateActioned}" class="text" /></td>
        </tr>
        <tr>
            <td class="jms-label"><bean:message key="label.finance.invoice.jobReference" bundle="${bundle}" /></td>
            <td><input type="text" readonly="readonly" value="${entity.jobReference}" class="text" /></td>
            <fmt:formatDate var="incidentDate" value="${entity.incidentDate}" pattern="${datePattern}" />
            <td class="jms-label"><bean:message key="label.finance.invoice.incidentDate" bundle="${bundle}" /></td>
            <td><input type="text" readonly="readonly" value="${incidentDate}" class="text" /></td>
        </tr>
        <tr>
            <td class="jms-label"><bean:message key="label.finance.invoice.sapCustNo" bundle="${bundle}" /></td>
            <td><input readonly="readonly" value="${entity.sapCustNo}" class="text" /></td>
            <td colspan="2">
                <table class="noborder">
                    <tr>
                        <td class="jms-label">
                            <bean:message key="label.finance.invoice.sapCustCreate" bundle="${bundle}" />${nbsp}
                            <input type="checkbox" disabled="disabled" value="${entity.sapCustCreate}" />
                        </td>
                        <td class="jms-label">
                            <bean:message key="label.finance.invoice.sapCustChange" bundle="${bundle}" />${nbsp}
                            <input type="checkbox" disabled="disabled" value="${entity.sapCustChange}" />
                        </td>
                        <td class="jms-label">
                            <bean:message key="label.finance.invoice.sapCustUnblock" bundle="${bundle}" />${nbsp}
                            <input type="checkbox" disabled="disabled" value="${entity.sapCustUnblock}" />
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
    </argus-html:window>

    <c:set var="title"><bean:message key="label.finance.invoice.header" bundle="${bundle}" /></c:set>
    <argus-html:window id="header" title="${title}" collapse="true">
    <table class="nonDataTable">
        <tr>
            <td class="jms-label"><bean:message key="label.finance.invoice.header.companyCode" bundle="${bundle}" /></td>
            <td><input type="text" readonly="readonly" value="${entity.header.companyCode}" class="text" /></td>
            <td class="jms-label"><bean:message key="label.finance.invoice.header.reference" bundle="${bundle}" /></td>
            <td><input type="text" readonly="readonly" value="${entity.header.reference}" class="text" /></td>
        </tr>
        <tr>
            <fmt:formatDate var="documentDate" value="${entity.header.documentDate}" pattern="${datePattern}" />
            <td class="jms-label"><bean:message key="label.finance.invoice.header.documentDate" bundle="${bundle}" /></td>
            <td><input type="text" readonly="readonly" value="${documentDate}" class="text" /></td>
            <fmt:formatDate var="postDate" value="${entity.header.postDate}" pattern="${datePattern}" />
            <td class="jms-label"><bean:message key="label.finance.invoice.header.postDate" bundle="${bundle}" /></td>
            <td><input type="text" readonly="readonly" value="${postDate}" class="text" /></td>
        </tr>
        <tr>
            <td class="jms-label"><bean:message key="label.finance.invoice.header.text" bundle="${bundle}" /></td>
            <td colspan="3"><textarea readonly="readonly" class="height5">${entity.header.text}</textarea></td>
        </tr>
    </table>
    </argus-html:window>

    <c:set var="title"><bean:message key="label.finance.invoice.data" bundle="${bundle}" /></c:set>
    <argus-html:window id="data" title="${title}" collapse="true">
    <table class="nonDataTable">
        <tr>
            <fmt:formatNumber var="amount" value="${entity.data.amount}" type="currency" />
            <td class="jms-label"><bean:message key="label.finance.invoice.data.amount" bundle="${bundle}" /></td>
            <td><input type="text" readonly="readonly" value="${amount}" class="text" /></td>
            <td class="jms-label"><bean:message key="label.finance.invoice.data.debitCredit" bundle="${bundle}" /></td>
            <td><input type="text" readonly="readonly" value="${entity.data.debitCredit}" class="text" /></td>
        </tr>
        <tr>
            <td class="jms-label"><bean:message key="label.finance.invoice.data.text" bundle="${bundle}" /></td>
            <td colspan="3"><textarea readonly="readonly" class="height5">${entity.data.text}</textarea></td>
        </tr>
        <tr>
            <td class="jms-label"><bean:message key="label.finance.invoice.data.assignment" bundle="${bundle}" /></td>
            <td><input type="text" readonly="readonly" value="${entity.data.assignment}" class="text" /></td>
            <td class="jms-label"><bean:message key="label.finance.invoice.data.sapCustNo" bundle="${bundle}" /></td>
            <td><input type="text" readonly="readonly" value="${entity.data.sapCustNo}" class="text" /></td>
        </tr>
        <tr>
            <td class="jms-label"><bean:message key="label.finance.invoice.data.paymentTerm" bundle="${bundle}" /></td>
            <td><input type="text" readonly="readonly" value="${entity.data.paymentTerm}" class="text" /></td>
            <td class="jms-label"><bean:message key="label.finance.invoice.data.dunningArea" bundle="${bundle}" /></td>
            <td><input type="text" readonly="readonly" value="${entity.data.dunningArea}" class="text" /></td>
        </tr>
    </table>
    </argus-html:window>

    <c:set var="title"><bean:message key="label.finance.invoice.glData" bundle="${bundle}" /></c:set>
    <argus-html:window id="glData" title="${title}" collapse="true">
    <table class="dataTable">
        <tr>
            <th><bean:message key="label.finance.invoice.glData.glAccount" bundle="${bundle}" /></th>
            <th><bean:message key="label.finance.invoice.glData.costCenter" bundle="${bundle}" /></th>
            <th><bean:message key="label.finance.invoice.glData.taxCode" bundle="${bundle}" /></th>
            <th><bean:message key="label.finance.invoice.glData.amount" bundle="${bundle}" /></th>
            <th><bean:message key="label.finance.invoice.glData.debitCredit" bundle="${bundle}" /></th>
            <th><bean:message key="label.finance.invoice.glData.text" bundle="${bundle}" /></th>
            <th><bean:message key="label.finance.invoice.glData.assignment" bundle="${bundle}" /></th>
        </tr>
        <c:forEach items="${entity.invoiceGlData}" var="glData" varStatus="s">
        <tr class="${s.index % 2 eq 0 ? 'even' : 'odd'}">
            <fmt:formatNumber var="amount" value="${glData.amount}" type="currency" />
            <td>${glData.glAccount}</td>
            <td>${glData.costCenter}</td>
            <td>${glData.taxCode}</td>
            <td>${amount}</td>
            <td>${glData.debitCredit}</td>
            <td>${glData.text}</td>
            <td>${glData.assignment}</td>
        </tr>
        </c:forEach>
    </table>
    </argus-html:window>

    <c:set var="title"><bean:message key="label.finance.invoice.text" bundle="${bundle}" /></c:set>
    <argus-html:window id="text" title="${title}" collapse="true">
    <table class="nonDataTable">
        <tr>
            <td class="jms-label"><bean:message key="label.finance.invoice.text.text" bundle="${bundle}" /></td>
            <td colspan="3"><textarea readonly="readonly" class="height5">${entity.text.text}</textarea></td>
        </tr>
    </table>
    </argus-html:window>

</jsp:root>