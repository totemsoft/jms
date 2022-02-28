<?xml version="1.0" encoding="utf-8"?>
<jsp:root
    xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:c="http://java.sun.com/jsp/jstl/core"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    xmlns:html="/WEB-INF/tld/argus-html.tld"
    xmlns:perm="/WEB-INF/tld/argus-perm.tld"
    version="2.0">

    <fmt:message var="title" key="pmaIncident.title" />
    <input id="title" type="hidden" value="${title}" />

    <div id="tabViewDiv" class="yui-navset">
        <ul class="yui-nav">
            <li><a href="#tab0"><em><fmt:message key="activation.tab0"/></em></a></li>
            <li><a href="#tab1"><em><fmt:message key="activation.tab1"/></em></a></li>
            <li><a href="#tab2"><em><fmt:message key="activation.tab2"/></em></a></li>
        </ul>
        <div class="yui-content" style="overflow: auto;">
            <div id="tab0"></div>
            <div id="tab1"></div>
            <div id="tab2"></div>
        </div>
    </div>

    <!-- info which scripts to load -->
    <script type="text/javascript" src="scripts/module/activation/main.js" />

</jsp:root>