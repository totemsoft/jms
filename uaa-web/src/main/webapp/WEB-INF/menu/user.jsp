<?xml version="1.0" encoding="UTF-8"?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:c="http://java.sun.com/jsp/jstl/core"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    xmlns:perm="/WEB-INF/tld/argus-perm.tld"
    version="2.0">
    <div id="leftNav">
        <div id="folder_list">
            <div>
                <ul>
                    <li class="about"><a href="${aboutUrl}" onclick="return false;"><fmt:message key="about.title" /></a></li>
                    <!--perm:present module="/admin"-->
                        <li class="admin"><a href="${adminUrl}" onclick="return false;"><fmt:message key="admin.title" /></a></li>
                    <!--/perm:present-->
                    <!--perm:present module="/activation"-->
                        <li class="activation"><a href="${activationUrl}" onclick="return false;"><fmt:message key="activation.title" /></a></li>
                    <!--/perm:present-->
                    <!--perm:present module="/incidentMpf"-->
                        <li class="incidentMpf"><a href="${incidentMpfUrl}" onclick="return false;"><fmt:message key="incidentMpf.title" /></a></li>
                    <!--/perm:present-->
                    <!--perm:present module="/incidentPma"-->
                        <li class="incidentPma"><a href="${incidentPmaUrl}" onclick="return false;"><fmt:message key="incidentPma.title" /></a></li>
                    <!--/perm:present-->
                    <!--perm:present module="/a23CommentIssue"-->
                        <li class="a23CommentIssue"><a href="${a23CommentIssueUrl}" onclick="return false;"><fmt:message key="a23CommentIssue.title" /></a></li>
                    <!--/perm:present-->
                    <!--perm:present module="/awaitingExport"-->
                        <li class="awaitingExport"><a href="${awaitingExportUrl}" onclick="return false;"><fmt:message key="awaitingExport.title" /></a></li>
                    <!--/perm:present-->
                </ul>
                <ul>
                    <perm:present module="/report">
                        <li class="report"><a href="${reportUrl}" onclick="return false;"><fmt:message key="report.title" /></a></li>
                    </perm:present>
                </ul>
            </div>
            <div id="calContainer">
                <div id="cal">&#160;</div>
            </div>
            <div id="calendars">
                <ul>
                    <li id="aseChangeCal" class="calendar">&#160;<fmt:message key="uaa.calendar.title" /></li>
                    <li id="jobActionCal" class="calendar">&#160;<fmt:message key="job.calendar.title" /></li>
                </ul>
            </div>
        </div>
    </div>
</jsp:root>