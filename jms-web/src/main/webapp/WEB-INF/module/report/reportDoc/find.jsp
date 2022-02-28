<?xml version="1.0" encoding="UTF-8"?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:html="http://struts.apache.org/tags-html"
    xmlns:bean="http://struts.apache.org/tags-bean"
    xmlns:logic="http://struts.apache.org/tags-logic"
    xmlns:c="http://java.sun.com/jsp/jstl/core"
    xmlns:fn="http://java.sun.com/jsp/jstl/functions"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    version="2.0">

    <c:set var="bundle" value="report" />

    <html:form action="/findReportDoc">
        <html:hidden property="dispatch" value="close" />
        <html:hidden property="entity.templateId" />

        <div id="errors" class="error">
            <html:errors />
        </div>

        <table class="nonDataTable">
            <tr>
                <td>
                    <table class="dataTable">
                        <tr>
                            <th><bean:message key="label.date" /></th>
                            <th><bean:message key="label.report.name" bundle="${bundle}" /></th>
                            <th colspan="2"></th>
                        </tr>
                        <c:forEach items="${reportForm.reportDocs}" var="item" varStatus="status">
                            <c:set var="index" value="${status.index}" />
                            <tr class="${index % 2 eq 0 ? 'odd' : 'even'}">
                                <td><fmt:formatDate value="${item.createdDate}" pattern="${datePattern}" /></td>
                                <td>${item.name}</td>
                                <td>
                                    <a title="Download Report Document"
                                        href="javascript:YAHOO.jms.sendDownloadRequest('report/downloadReportDoc.do?id=${item.id}', null, 'Download Report Document');">
                                        <span class="download">&#160;</span>
                                    </a>
                                </td>
                                <td>
                                    <a title="Delete Report Document"
                                        href="javascript:YAHOO.jms.sendGetRequest('report/deleteReportDoc.do?id=${item.id}', null, 'Delete Report Document');">
                                        <span class="delete">&#160;</span>
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </td>
            </tr>
        </table>
    </html:form>

</jsp:root>