<?xml version="1.0" encoding="utf-8"?>
<jsp:root
    xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:html="http://struts.apache.org/tags-html"
    xmlns:bean="http://struts.apache.org/tags-bean"
    xmlns:c="http://java.sun.com/jsp/jstl/core"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    xmlns:decorator="http://www.opensymphony.com/sitemesh/decorator"
    xmlns:perm="/WEB-INF/tld/argus-perm.tld"
    version="2.0">

    <jsp:directive.page pageEncoding="utf-8" contentType="text/html; charset=utf-8" />

    <c:set var="applicationBuild"><bean:message key="application.build" /></c:set>
    <c:set var="applicationEnv"><bean:message key="application.env" /></c:set>

    <html:html xhtml="true">

        <head>
            <meta http-equiv="content-type" content="text/html; charset=utf-8" />
            <!--meta http-equiv="Content-Type" content="application/xhtml+xml; charset=utf-8" /-->
            <meta http-equiv="Cache-Control" content="private,no-store,no-cache,post-check=0,pre-check=0,max-age=86400" />
            <meta http-equiv="Pragma" content="no-cache" />
            <meta http-equiv="Date" content="now" />
            <meta http-equiv="Expires" content="now+86400" />
            <!--meta http-equiv="Last-Modified" content="01 Jan 1970 00:00:00 GMT" />
            <meta http-equiv="If-Modified-Since" content="01 Jan 1970 00:00:00 GMT" /-->
            <meta http-equiv="X-UA-Compatible" content="chrome=1" />

            <meta name="application-build" content="${applicationBuild}" />
            <meta name="application-env" content="${applicationEnv}" />

            <html:base ref="site" />

            <title><bean:message key="application.name" /> :: <decorator:title /></title>

            <c:set var="logo"><bean:message key="icon.logo" /></c:set>
            <link rel="shortcut icon" href="${logo}" />

            <style>
                body {visibility: hidden;}
            </style>

            <link href="jscalendar-1.0/skins/aqua2/theme.css" rel="stylesheet" type="text/css" />
            <script type="text/javascript" src="jscalendar-1.0/all-calendar.js">${nbsp}</script>

            <script type="text/javascript" src="yui/yuiloader-dom-event/yuiloader-dom-event.js">${nbsp}</script> 
            <script type="text/javascript" src="yui/yui-combine.js">${nbsp}</script>
            <link type="text/css" rel="stylesheet" href="yui/yui-combine.css" />

            <script type="text/javascript">
                var contextPath = '${pageContext.request.contextPath}';
            </script>
            <script type="text/javascript" src="scripts/default.js" defer="defer">${nbsp}</script>

            <decorator:head />
        </head>

        <body class="yui-skin-sam jms-bg">
            <iframe id="yui-history-iframe" src="yui/history/assets/blank.html">${nbsp}</iframe>
            <input id="yui-history-field" type="hidden" />

            <div id="header">
                <div id="top_wrap" class="jms-bg">
                    <div id="jms-logo" class="jms-logo">${nbsp}</div>
                     <div id="info">
                        <strong>QFRS User: ${loggedUser.login}</strong>
                        <span class="links"><a href="${logout}">Sign Out</a></span>
                    </div>
                    <c:if test="${applicationEnv ne 'prd'}"><div class="jms-env">${applicationEnv}</div></c:if>
                </div>
            </div>
            <perm:present role="Administrator">
                <jsp:include page="/WEB-INF/menu/administrator.jsp" />
            </perm:present>
            <perm:notPresent role="Administrator">
                <jsp:include page="/WEB-INF/menu/user.jsp" />
            </perm:notPresent>

            <div id="editDiv">
                <div class="hd">${nbsp}</div>
                <div class="bd">${nbsp}</div>
                <div class="ft">${nbsp}</div>
            </div>
            <div id="helpDiv">
                <div class="hd">${nbsp}</div>
                <div class="bd">${nbsp}</div>
                <div class="ft">${nbsp}</div>
            </div>
        </body>

    </html:html>

</jsp:root>