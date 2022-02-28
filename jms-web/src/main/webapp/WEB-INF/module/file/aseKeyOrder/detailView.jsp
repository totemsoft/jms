<?xml version="1.0" encoding="UTF-8"?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:html="http://struts.apache.org/tags-html"
    xmlns:bean="http://struts.apache.org/tags-bean"
    xmlns:c="http://java.sun.com/jsp/jstl/core"
    xmlns:fn="http://java.sun.com/jsp/jstl/functions"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    xmlns:argus-html="/WEB-INF/tld/argus-html.tld"
    xmlns:perm="/WEB-INF/tld/argus-perm.tld"
    version="2.0">

    <html:xhtml/>

    <c:set var="bundle" value="file" />

    <html:form action="/aseKeyOrder" method="get">
        <html:hidden styleId="aseKeyOrderId" property="entity.aseKeyOrderId" />

        <div id="doc" class="width100">
            <div id="hd">
                <div class="top-bar">
                    <b>ASE Key Order:</b> ${entity.orderNo}
                </div>
            </div>
            <div id="bd">
                <div id="yui-main" class="yui-g">
                    <div class="yui-u first">
                        <c:set var="title"></c:set>
                        <argus-html:window id="aseKeyOrderDetails" title="Order Details" collapse="true">
                            <perm:present module="/file" path="/aseKeyOrderEdit">
                                <argus-html:winoption id="aseKeyOrderEdit" url="javascript:;" title="Edit">edit</argus-html:winoption>
                            </perm:present>
                            <jsp:include page="/WEB-INF/module/file/aseKeyOrder/view.jsp" />
                        </argus-html:window>
                    </div>
                    <div class="yui-u">
                        <c:set var="title"></c:set>
                        <argus-html:window id="aseKeyDetails" title="ASE Keys" collapse="true">
                            <perm:present module="/file" path="/aseKeyEdit">
                                <argus-html:winoption id="aseKeyAdd" url="javascript:;" title="Add (double click selected row to edit)">add</argus-html:winoption>
                            </perm:present>
                            <div id="aseKeysDiv">
                                <table id="aseKeysData">
                                    <c:forEach items="${entity.aseKeys}" var="aseKey">
                                    <tr>
                                        <td>${aseKey.id}</td>
                                        <td>javascript:YAHOO.jms.file.aseKeyOrder.editAseKey(${aseKey.id});${aseKey.aseKeyNo}</td>
                                        <td>${aseKey.contact.name}</td>
                                        <td>${aseKey.licenseNo}</td>
                                        <td>${aseKey.contact.mobile}</td>
                                        <td>${aseKey.status.name}</td>
                                    </tr>
                                    </c:forEach>
                                </table>
                            </div>
					        <table class="nonDataTable">
					            <tr>
					                <td class="jms-label"><bean:message key="label.aseKeyOrder.totalNo" bundle="${bundle}" /></td>
					                <td><html:text property="entity.totalNo" readonly="true" styleClass="text number" /></td>
					                <td class="jms-label"><bean:message key="label.aseKeyOrder.totalPrice" bundle="${bundle}" /></td>
					                <td><html:text property="entity.totalPrice" readonly="true" styleClass="text number" /></td>
					            </tr>
                            </table>
                        </argus-html:window>
                    </div>
                </div>
           </div>
        </div>
    </html:form>

    <!-- info which scripts to load -->
    <script type="text/javascript">YAHOO.jms.file.aseKeyOrder.init();</script>

</jsp:root>