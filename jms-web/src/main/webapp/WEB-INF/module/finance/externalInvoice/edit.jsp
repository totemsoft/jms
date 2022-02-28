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
    <c:set var="entity" value="${externalInvoiceForm.entity}" />

    <div id="tab3.content">

        <div id="tab3.toolbar.top"><span></span></div>

        <html:form action="/externalInvoice">
            <html:hidden property="dispatch" value="save" />

            <div id="errors" class="error">
                <html:errors />
            </div>

            <html:hidden property="entity.invoiceBatch.invoiceBatchId" styleId="batchId" />
            <table class="nonDataTable">
                <caption class="larger">
                    <div class="float-left"><bean:message key="label.finance.invoiceBatch" bundle="${bundle}" />${nbsp} ${entity.invoiceBatch.id}</div>
                    <div class="float-right"><bean:message key="label.finance.invoice" bundle="${bundle}" />${nbsp} ${entity.id}</div>
                </caption>
                <tr>
                    <td class="jms-label"><bean:message key="label.finance.invoice.invoiceType" bundle="${bundle}" /></td>
                    <td><html:text property="entity.invoiceBatch.invoiceType.name" styleClass="noborder" readonly="true" /></td>
                    <td class="jms-label"><bean:message key="label.finance.invoice.invoiceArea" bundle="${bundle}" /></td>
                    <td><html:text property="entity.invoiceBatch.invoiceArea.name" styleClass="noborder" readonly="true" /></td>
                </tr>
            </table>

            <c:set var="title"><bean:message key="label.finance.invoice" bundle="${bundle}" /></c:set>
            <argus-html:window id="invoice" title="${title}" collapse="true">
            <html:hidden property="entity.invoiceId" styleId="invoiceId" />
            <table class="nonDataTable">
                <tr>
                    <td class="jms-label"><bean:message key="label.finance.invoice.invoiceType" bundle="${bundle}" /></td>
                    <td><html:text property="entity.invoiceType.name" styleClass="text" /></td>
                    <td class="jms-label"><bean:message key="label.finance.invoice.invoiceArea" bundle="${bundle}" /></td>
                    <td><html:text property="entity.invoiceArea.name" styleClass="text" /></td>
                </tr>
                <tr>
                    <td class="jms-label"><bean:message key="label.finance.invoice.fiscalYear" bundle="${bundle}" /></td>
                    <td><html:text property="entity.fiscalYear" styleClass="text" /></td>
                    <td class="jms-label"><bean:message key="label.finance.invoice.region" bundle="${bundle}" /></td>
                    <td><html:text property="entity.region.name" styleClass="text" /></td>
                </tr>
                <tr>
                    <td class="jms-label"><bean:message key="label.finance.invoice.brigade" bundle="${bundle}" /></td>
                    <td><html:text property="entity.brigade.name" styleClass="text" /></td>
                    <td class="jms-label"><bean:message key="label.finance.invoice.receiptBatch" bundle="${bundle}" /></td>
                    <td><html:text property="entity.receiptBatch" styleClass="text" /></td>
                </tr>
                <tr>
                    <fmt:formatDate var="dateReceived" value="${entity.dateReceived}" pattern="${datePattern}" />
                    <td class="jms-label"><bean:message key="label.finance.invoice.dateReceived" bundle="${bundle}" /></td>
                    <td><html:text property="entity.dateReceived" value="${dateReceived}" styleId="f_date_dateReceived" styleClass="text" /></td>
                    <fmt:formatDate var="dateActioned" value="${entity.dateActioned}" pattern="${datePattern}" />
                    <td class="jms-label"><bean:message key="label.finance.invoice.dateActioned" bundle="${bundle}" /></td>
                    <td><html:text property="entity.dateActioned" value="${dateActioned}" styleId="f_date_dateActioned" styleClass="text" /></td>
                </tr>
                <tr>
                    <td class="jms-label"><bean:message key="label.finance.invoice.jobReference" bundle="${bundle}" /></td>
                    <td><html:text property="entity.jobReference" styleClass="text" /></td>
                    <fmt:formatDate var="incidentDate" value="${entity.incidentDate}" pattern="${datePattern}" />
                    <td class="jms-label"><bean:message key="label.finance.invoice.incidentDate" bundle="${bundle}" /></td>
                    <td><html:text property="entity.incidentDate" value="${incidentDate}" styleId="f_date_incidentDate" styleClass="text" /></td>
                </tr>
                <tr>
                    <td class="jms-label"><bean:message key="label.finance.invoice.sapCustNo" bundle="${bundle}" /></td>
                    <td><html:text property="entity.sapCustNo" styleClass="text" /></td>
                    <td colspan="2">
                        <table class="noborder">
                            <tr>
                                <td class="jms-label">
                                    <bean:message key="label.finance.invoice.sapCustCreate" bundle="${bundle}" />${nbsp}
                                    <html:hidden property="entity.sapCustCreate" styleId="sapCustCreate" />
                                    <html:checkbox property="entity.sapCustCreate" onchange="this.form.sapCustCreate.value=this.checked;" />
                                </td>
                                <td class="jms-label">
                                    <bean:message key="label.finance.invoice.sapCustChange" bundle="${bundle}" />${nbsp}
                                    <html:hidden property="entity.sapCustChange" styleId="sapCustChange" />
                                    <html:checkbox property="entity.sapCustChange" onchange="this.form.sapCustChange.value=this.checked;" />
                                </td>
                                <td class="jms-label">
                                    <bean:message key="label.finance.invoice.sapCustUnblock" bundle="${bundle}" />${nbsp}
                                    <html:hidden property="entity.sapCustUnblock" styleId="sapCustUnblock" />
                                    <html:checkbox property="entity.sapCustUnblock" onchange="this.form.sapCustUnblock.value=this.checked;" />
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
            </argus-html:window>

            <c:set var="title"><bean:message key="label.finance.invoice.header" bundle="${bundle}" /></c:set>
            <argus-html:window id="header" title="${title}" collapse="true">
            <html:hidden property="entity.header.invoiceHeaderId" styleId="invoiceHeaderId" />
            <table class="nonDataTable">
                <tr>
                    <td class="jms-label"><bean:message key="label.finance.invoice.header.companyCode" bundle="${bundle}" /></td>
                    <td><html:text property="entity.header.companyCode" styleClass="text" /></td>
                    <td class="jms-label"><bean:message key="label.finance.invoice.header.reference" bundle="${bundle}" /></td>
                    <td><html:text property="entity.header.reference" styleClass="text" /></td>
                </tr>
                <tr>
                    <fmt:formatDate var="documentDate" value="${entity.header.documentDate}" pattern="${datePattern}" />
                    <td class="jms-label"><bean:message key="label.finance.invoice.header.documentDate" bundle="${bundle}" /></td>
                    <td><html:text property="entity.header.documentDate" value="${documentDate}" styleId="f_date_documentDate" styleClass="text" /></td>
                    <fmt:formatDate var="postDate" value="${entity.header.postDate}" pattern="${datePattern}" />
                    <td class="jms-label"><bean:message key="label.finance.invoice.header.postDate" bundle="${bundle}" /></td>
                    <td><html:text property="entity.header.postDate" value="${postDate}" styleId="f_date_postDate" styleClass="text" /></td>
                </tr>
                <tr>
                    <td class="jms-label"><bean:message key="label.finance.invoice.header.text" bundle="${bundle}" /></td>
                    <td colspan="3"><html:textarea property="entity.header.text" styleClass="height5" /></td>
                </tr>
            </table>
            </argus-html:window>

            <c:set var="title"><bean:message key="label.finance.invoice.data" bundle="${bundle}" /></c:set>
            <argus-html:window id="data" title="${title}" collapse="true">
            <html:hidden property="entity.data.invoiceDataId" styleId="invoiceDataId" />
            <table class="nonDataTable">
                <tr>
                    <fmt:formatNumber var="amount" value="${entity.data.amount}" type="currency" />
                    <td class="jms-label"><bean:message key="label.finance.invoice.data.amount" bundle="${bundle}" /></td>
                    <td><html:text property="entity.data.amount" value="${amount}" styleClass="text" /></td>
                    <td class="jms-label"><bean:message key="label.finance.invoice.data.debitCredit" bundle="${bundle}" /></td>
                    <td><html:text property="entity.data.debitCredit" styleClass="text" /></td>
                </tr>
                <tr>
                    <td class="jms-label"><bean:message key="label.finance.invoice.data.text" bundle="${bundle}" /></td>
                    <td colspan="3"><textarea class="height5">${entity.data.text}</textarea></td>
                </tr>
                <tr>
                    <td class="jms-label"><bean:message key="label.finance.invoice.data.assignment" bundle="${bundle}" /></td>
                    <td><html:text property="entity.data.assignment" styleClass="text" /></td>
                    <td class="jms-label"><bean:message key="label.finance.invoice.data.sapCustNo" bundle="${bundle}" /></td>
                    <td><html:text property="entity.data.sapCustNo" styleClass="text" /></td>
                </tr>
                <tr>
                    <td class="jms-label"><bean:message key="label.finance.invoice.data.paymentTerm" bundle="${bundle}" /></td>
                    <td><html:text property="entity.data.paymentTerm" styleClass="text" /></td>
                    <td class="jms-label"><bean:message key="label.finance.invoice.data.dunningArea" bundle="${bundle}" /></td>
                    <td><html:text property="entity.data.dunningArea" styleClass="text" /></td>
                </tr>
            </table>
            </argus-html:window>

            <c:set var="title"><bean:message key="label.finance.invoice.glData" bundle="${bundle}" /></c:set>
            <argus-html:window id="glDataWindow" title="${title}" collapse="true">
            <argus-html:winoption id="addGlData" url="javascript:YAHOO.jms.finance.addGlData(${entity.id});" title="Add GL Data">add</argus-html:winoption>
            <div id="glDataDiv">
            <table id="glData">
                <c:forEach items="${entity.invoiceGlData}" var="glData" varStatus="s">
                <c:set var="index" value="${s.index}" />
                <tr class="${index % 2 eq 0 ? 'even' : 'odd'}">
                    <td>${glData.id}</td>
                    <td>${glData.glAccount}</td>
                    <td>${glData.costCenter}</td>
                    <td>${glData.taxCode}</td>
                    <td><fmt:formatNumber value="${glData.amount}" type="currency" /></td>
                    <td>${glData.debitCredit}</td>
                    <td>${glData.text}</td>
                    <td>${glData.assignment}</td>
                </tr>
                </c:forEach>
            </table>
            </div>
            </argus-html:window>

            <c:set var="title"><bean:message key="label.finance.invoice.text" bundle="${bundle}" /></c:set>
            <html:hidden property="entity.text.invoiceTextId" styleId="invoiceTextId" />
            <argus-html:window id="text" title="${title}" collapse="true">
            <table class="nonDataTable">
                <tr>
                    <td class="jms-label"><bean:message key="label.finance.invoice.text.text" bundle="${bundle}" /></td>
                    <td colspan="3"><html:textarea property="entity.text.text" styleClass="height5" /></td>
                </tr>
            </table>
            </argus-html:window>

        </html:form>

        <div id="tab3.toolbar.bottom"><span></span></div>

    </div>

</jsp:root>