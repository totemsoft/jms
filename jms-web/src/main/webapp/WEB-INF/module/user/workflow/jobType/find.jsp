<?xml version="1.0" encoding="UTF-8"?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:html="http://struts.apache.org/tags-html"
    xmlns:bean="http://struts.apache.org/tags-bean"
    xmlns:logic="http://struts.apache.org/tags-logic"
    xmlns:c="http://java.sun.com/jsp/jstl/core"
    xmlns:fn="http://java.sun.com/jsp/jstl/functions"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    xmlns:argus-html="/WEB-INF/tld/argus-html.tld"
    xmlns:perm="/WEB-INF/tld/argus-perm.tld"
    version="2.0">

    <html:xhtml/>

    <div id="tab4.content">

        <div id="jobTypesDiv">
            <table id="jobTypes">
                <c:forEach items="${entities}" var="entity" varStatus="status">
                <tr>
                    <td>${entity.jobType.id}</td>
                    <td>${entity.jobType.name}</td>
                </tr>
                </c:forEach>
            </table>
        </div>

        <argus-html:window title="Action Workflow" collapse="false">
            <div id="jobTypeActionWorkflowDiv">${nbsp}</div>
        </argus-html:window>

        <script type="text/javascript" src="scripts/module/user/workflow/jobType/find.js"></script>

    </div>

</jsp:root>