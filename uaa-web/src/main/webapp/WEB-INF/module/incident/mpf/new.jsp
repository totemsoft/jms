<?xml version="1.0" encoding="utf-8"?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
	xmlns:c="http://java.sun.com/jsp/jstl/core"
	xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
	xmlns:html="/WEB-INF/tld/argus-html.tld"
	xmlns:perm="/WEB-INF/tld/argus-perm.tld"
	version="2.0">

    <c:set var="tabIndex" value="0" />

    <fmt:message var="title" key="title.new" />
    <input id="title${tabIndex}" type="hidden" value="${title}" />

    <div id="dataHolder${tabIndex}">
        <fmt:message var="title" key="title.injury" />
        <html:window id="dataHeader${tabIndex}" title="${title}">
            <form id="dataForm${tabIndex}" name="dataForm${tabIndex}">
                <table class="nonDataTable">
                    <tr>
                        <td class="jms-label"><fmt:message key="injury.type"/></td>
                        <td>
                            <table class="noborder">
                                <tr>
                                    <td>
                                        <div id="filter0">
                                            <input id="filter0Hidden" type="hidden" name="injuryTypeId" value="${injuryTypeId}" />
                                            <input id="filter0Input" class="filterInput yui-ac-input" type="text" name="injuryTypeName" value="${injuryTypeName}" />${nbsp}
                                            <div id="filter0Container" class="filterContainer"></div>
                                        </div>
                                    </td>
                                    <td class="width3em yui-ac">
                                        <span id="filter0Toggle" class="filterToggle">&#160;</span>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td rowspan="3" class="width10em">
                            <div id="toolbarDiv${tabIndex}" class="jms-toolbar"></div>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2"/>
                    </tr>
                    <tr>
                        <td colspan="2"/>
                    </tr>
                </table>
            </form>
        </html:window>
        <div id="dataCenter${tabIndex}" class="argus-container">
            <div id="dataDiv${tabIndex}"></div>
        </div>
    </div>
</jsp:root>