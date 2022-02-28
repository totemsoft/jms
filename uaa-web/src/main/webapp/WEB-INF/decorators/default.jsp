<?xml version="1.0" encoding="utf-8"?>
<jsp:root
    xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:c="http://java.sun.com/jsp/jstl/core"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    xmlns:decorator="http://www.opensymphony.com/sitemesh/decorator"
    xmlns:perm="/WEB-INF/tld/argus-perm.tld"
    version="2.0">

    <jsp:directive.page pageEncoding="utf-8" contentType="text/html; charset=utf-8" />

    <c:set var="contextPath" value="${pageContext.request.contextPath}" scope="request" />

    <html xhtml="true">

        <head>
            <meta http-equiv="content-type" content="text/html; charset=utf-8" />
            <!--meta http-equiv="Content-Type" content="application/xhtml+xml; charset=utf-8" /-->
            <meta http-equiv="Cache-Control" content="private,no-store,no-cache,post-check=0,pre-check=0,max-age=86400" />
            <meta http-equiv="Pragma" content="no-cache" />
            <meta http-equiv="Date" content="now" />
            <meta http-equiv="Expires" content="now+86400" />
            <!--meta http-equiv="Last-Modified" content="01 Jan 1970 00:00:00 GMT" />
            <meta http-equiv="If-Modified-Since" content="01 Jan 1970 00:00:00 GMT" /-->

            <title><fmt:message key="label.application.name" /> :: <fmt:message key="application.build" /></title>

            <fmt:message var="logo" key="icon.logo" />
            <link rel="shortcut icon" href="${logo}" />

            <style>
                body {visibility: hidden;}
            </style>

            <link href="${contextPath}/jscalendar-1.0/skins/aqua2/theme.css" rel="stylesheet" type="text/css" />
            <script type="text/javascript" src="${contextPath}/jscalendar-1.0/all-calendar.js">&#160;</script>

            <script type="text/javascript" src="${contextPath}/yui/yuiloader-dom-event/yuiloader-dom-event.js">&#160;</script> 
            <script type="text/javascript" src="${contextPath}/yui/yui-combine.js">&#160;</script>
            <link type="text/css" rel="stylesheet" href="${contextPath}/yui/yui-combine.css" />

            <decorator:head />
        </head>

        <body class="yui-skin-sam">
            <iframe id="yui-history-iframe" src="yui/history/assets/blank.html">&#160;</iframe>
            <input id="yui-history-field" type="hidden" />

            <div id="header">
                <div id="top_wrap">
                    <div id="logo">&#160;</div>
                    <div id="info">
                        <strong>QFRS User: ${loggedUser.login}</strong>
                        <span class="links"><a href="${logout}">Sign Out</a></span>
                    </div>
                    <div id="searchBox">
                        <span class="links"><a href="http://www.fire.qld.gov.au/" target="_blank">Queensland Fire and Rescue Service</a></span>
                        <input type="text" size="20" id="query" value="Search the Web.." />
                        <input type="button" id="search" value="Search" />
                    </div>
                </div>
            </div>
            <!--jsp:directive.include file="/WEB-INF/menu/user.jsp" /-->
            <jsp:include page="/WEB-INF/menu/user.jsp" />

            <div id="editDiv">
                <div class="hd">&#160;</div>
                <div class="bd">&#160;</div>
                <div class="ft">&#160;</div>
            </div>
        </body>

        <script type="text/javascript" src="scripts/default.js">&#160;</script>
    </html>

</jsp:root>