<?xml version="1.0" encoding="UTF-8"?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:html="http://struts.apache.org/tags-html"
    xmlns:bean="http://struts.apache.org/tags-bean"
    xmlns:c="http://java.sun.com/jsp/jstl/core"
    xmlns:fn="http://java.sun.com/jsp/jstl/functions"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    xmlns:argus-html="/WEB-INF/tld/argus-html.tld"
    version="2.0">

    <html:xhtml/>

    <c:set var="entity" value="${jobForm.entity}" />

    <html:form action="/editJob" method="get">
        <html:hidden styleId="jobId" property="entity.jobId" />

        <div id="doc" class="width100">
            <div id="hd">
                <div class="top-bar">
                    <b>Job No:</b> ${entity.jobId} | <b>Job Status:</b> ${jobForm.jobStatus} | <b>File:</b> ${entity.file.fileId} | <b>File Status:</b> ${entity.file.fileStatus.name}
                </div>
                <jsp:include page="/WEB-INF/module/job/pagetools.jsp" />
            </div>
            <div id="bd">
                <div id="yui-main" class="yui-g">
                    <div class="yui-u first">
                        <argus-html:window id="jobDetails" title="Job Details" collapse="true">
                            <argus-html:winoption id="jobDetailsEdit" url="javascript:;" title="Edit">edit</argus-html:winoption>
                            <jsp:include page="/WEB-INF/module/job/jobDetails/view.jsp" />
                        </argus-html:window>
                        <argus-html:window id="jobDoc" title="Job Documents" collapse="true">
                            <argus-html:winoption id="jobDocEdit" url="javascript:;" title="Edit">edit</argus-html:winoption>
                            <jsp:include page="/WEB-INF/module/job/jobDoc/view.jsp" />
                        </argus-html:window>
                    </div>
                    <div class="yui-u">
                        <argus-html:window id="jobActions" title="Job Actions" collapse="true">
                            <jsp:include page="/WEB-INF/module/file/jobActions/view.jsp" />
                        </argus-html:window>
                        <argus-html:window id="jobActionsToDo" title="Job Actions To Do" collapse="true">
                            <argus-html:winoption id="jobActionsToDoEdit" url="javascript:;" title="Add">add</argus-html:winoption>
                            <jsp:include page="/WEB-INF/module/file/jobActionsToDo/view.jsp" />
                        </argus-html:window>
                        <argus-html:window id="jobCompletedActions" title="Job Completed Actions" collapse="true" collapsed="true">
                            <jsp:include page="/WEB-INF/module/file/jobCompletedActions/view.jsp" />
                        </argus-html:window>
                    </div>
                </div>
            </div>
        </div>
    </html:form>

</jsp:root>