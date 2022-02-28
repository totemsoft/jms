<?xml version="1.0" encoding="utf-8"?>
<jsp:root
    xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:c="http://java.sun.com/jsp/jstl/core"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    version="2.0">
    <head>
        <jsp:directive.page contentType="text/html;charset=utf-8"/>
        <title>Crystal Reports</title>
    </head>
    <body>
        <ul>
            <c:set var="reportViewUrl" value="reportViewer.do?dispatch=view&amp;propogationBehavior=4" />
            <c:set var="reportExportUrl" value="reportViewer.do?dispatch=export&amp;propogationBehavior=4" />
            <c:forEach items="${entities}" var="entity">
                <li>
                    <a href="${reportViewUrl}&amp;reportName=${entity.code}" target="_blank">${entity.name}</a>
                    ::
                    <a href="${reportExportUrl}&amp;reportName=${entity.code}" target="_blank">export</a>
                </li>
            </c:forEach>
        </ul>
        <!--ul>
            <c:set var="reportViewTaglibUrl" value="reportViewer.do?dispatch=viewTaglib&amp;propogationBehavior=4" />
            <li>
                <a href="${reportViewTaglibUrl}&amp;reportName=ConsolidatedBalanceSheet" target="_blank">Consolidated Balance Sheet</a>
            </li>
            <li>
                <a href="${reportViewTaglibUrl}&amp;reportName=CustomFunctions" target="_blank">Custom Functions</a>
            </li>
            <li>
                <a href="${reportViewTaglibUrl}&amp;reportName=CustomerProfileReport" target="_blank">Customer Profile Report</a>
            </li>
            <li>
                <a href="${reportViewTaglibUrl}&amp;reportName=StatementOfAccount" target="_blank">Statement of Account</a>
            </li>
        </ul-->
    </body>
</jsp:root>