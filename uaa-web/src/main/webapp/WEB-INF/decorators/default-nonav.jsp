<?xml version="1.0" encoding="utf-8"?>
<jsp:root
    xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:c="http://java.sun.com/jsp/jstl/core"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    xmlns:decorator="http://www.opensymphony.com/sitemesh/decorator"
    version="2.0">

    <c:set var="contextPath" value="${pageContext.request.contextPath}" scope="request" />

    <html xhtml="true">

        <head>
            <meta http-equiv="content-type" content="text/html; charset=utf-8" />
            <!--meta http-equiv="Content-Type" content="application/xhtml+xml; charset=utf-8" /-->
            <meta http-equiv="Cache-Control" content="private,no-cache,post-check=0,pre-check=0,max-age=86400" />
            <meta http-equiv="Pragma" content="no-cache" />
            <meta http-equiv="Date" content="now" />
            <meta http-equiv="Expires" content="now+86400" />

            <title>:: <fmt:message key="label.application.name" /> :: <decorator:title /></title>

            <fmt:message var="logo" key="icon.logo" />
            <link rel="shortcut icon" href="${logo}" />

            <link href="${contextPath}/yui/assets/skins/sam/skin.css" rel="stylesheet" type="text/css" />
            <link href="${contextPath}/styles/default.css" rel="stylesheet" type="text/css" />

            <script type="text/javascript" src="${contextPath}/yui/yahoo-dom-event/yahoo-dom-event.js">&#160;</script>
            <script type="text/javascript" src="${contextPath}/yui/container/container-min.js">&#160;</script>
            
            <style>
                body {visibility: hidden;}
            </style>

            <decorator:head />
        </head>

        <body>
            <!--p>Decorator [default-nonav]: ${pageContext.request.contextPath}${pageContext.request.servletPath}</p-->

            <!-- #doc3 = 100% width -->
            <div id="doc3">
                <div id="hd">
                    <!--jsp:include page="/WEB-INF/includes/header.jsp" /-->
                </div> 
                <div id="bd">
                    <div id="yui-main" class="yui-skin-sam">
                        <div class="yui-b">
                            <decorator:body />
                        </div>
                    </div>
                </div>
                <div id="ft">
                    <!--jsp:include page="/WEB-INF/includes/footer.jsp" /-->
                </div>
            </div>
        </body>

    </html>

</jsp:root>