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

    <div id="tab3.content">
        <div id="tab3.toolbar"><span></span></div>
        <div id="batchDiv">
            <table id="batchData">
                <c:forEach items="${entities}" var="entity" varStatus="s">
                <tr>
                    <td>${entity.id}</td>
                    <td>${entity.name}</td>
                    <td>${entity.createdBy.contact.name}</td>
                    <td><fmt:formatDate value="${entity.createdDate}" pattern="${datePattern}" /></td>
                    <td>${entity.completedPercent} %</td>
                </tr>
                </c:forEach>
            </table>
        </div>
        <argus-html:window title="Batch Details">
            <div id="batchViewDiv"></div>
        </argus-html:window>
        <script type="text/javascript">
            var statuses = [];
            <c:forEach items="${statuses}" var="item">statuses.push({label: '${item.name}', value: '${item.name}'});</c:forEach>
            YAHOO.jms.mailOut.loadTab3(statuses);
        </script>
    </div>

</jsp:root>