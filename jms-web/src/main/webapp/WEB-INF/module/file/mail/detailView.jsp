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

    <c:set var="bundle" value="file" />

    <html:form action="/mailRegister" method="get">
        <html:hidden styleId="mailRegisterId" property="entity.mailRegisterId" />
        <html:hidden styleId="fileId" property="entity.file.fileId" />

        <div id="doc" class="width100">
            <div id="hd">
                <div class="top-bar">
                    <b>Mail Register:</b> ${mailRegisterForm.entity.mailRegisterId} :: ${mailRegisterForm.entity.mailRegisterNo}
                </div>
                <jsp:include page="/WEB-INF/module/file/mail/pagetools.jsp" />
            </div>
            <div id="bd">
                <div id="yui-main" class="yui-g">
                    <div class="yui-u first">
                        <c:set var="title"></c:set>
                        <argus-html:window id="mailRegisterDetails" title="Mail Register Details" collapse="true">
                            <perm:present module="/file" path="/editFcaAse">
                                <argus-html:winoption id="mailRegisterEdit" url="javascript:;" title="Edit">edit</argus-html:winoption>
                            </perm:present>
                            <jsp:include page="/WEB-INF/module/file/mail/view.jsp" />
                        </argus-html:window>
                        <perm:present module="/file" path="/viewFileDocChkList">
                            <c:set var="title"></c:set>
                            <argus-html:window id="fileDocChkList" title="Document Checklist" collapse="true">
                                <perm:present module="/file" path="/editFileDocChkList">
                                    <argus-html:winoption id="fileDocChkListEdit" url="javascript:;" title="Edit">edit</argus-html:winoption>
                                </perm:present>
                                <jsp:include page="/WEB-INF/module/file/fileDocChkList/view.jsp" />
                            </argus-html:window>
                        </perm:present>
                        <perm:present module="/file" path="/viewFileDoc">
                            <c:set var="title"></c:set>
                            <argus-html:window id="fileDoc" title="Documents" collapse="true">
                                <perm:present module="/file" path="/editFileDoc">
                                    <argus-html:winoption id="fileDocEdit" url="javascript:;" title="Edit">edit</argus-html:winoption>
                                </perm:present>
                                <jsp:include page="/WEB-INF/module/file/fileDoc/view.jsp" />
                            </argus-html:window>
                        </perm:present>
                    </div>
                    <div class="yui-u">
                        <perm:present module="/file" path="/viewCrmActionsToDo">
                            <c:set var="title"></c:set>
                            <argus-html:window id="crmActionsToDo" title="CRM Actions To Do" collapse="true">
                                <perm:present module="/file" path="/addFileAction">
                                    <argus-html:winoption id="crmActionsToDoEdit" url="javascript:;" title="Add">add</argus-html:winoption>
                                </perm:present>
                                <jsp:include page="/WEB-INF/module/file/crmActionsToDo/view.jsp" />
                            </argus-html:window>
                        </perm:present>
                        <perm:present module="/file" path="/viewCrmCompletedActions">
                            <c:set var="title"></c:set>
                            <argus-html:window id="crmCompletedActions" title="CRM Completed Actions" collapse="true" collapsed="true">
                                <jsp:include page="/WEB-INF/module/file/crmCompletedActions/view.jsp" />
                            </argus-html:window>
                        </perm:present>
                        <perm:present module="/file" path="/viewJobActionsToDo">
                            <c:set var="title"></c:set>
                            <argus-html:window id="jobActionsToDo" title="Job Actions To Do" collapse="true">
                                <jsp:include page="/WEB-INF/module/file/jobActionsToDo/view.jsp" />
                            </argus-html:window>
                        </perm:present>
                        <perm:present module="/file" path="/viewJobCompletedActions">
                            <c:set var="title"></c:set>
                            <argus-html:window id="jobCompletedActions" title="Job Completed Actions" collapse="true" collapsed="true">
                                <jsp:include page="/WEB-INF/module/file/jobCompletedActions/view.jsp" />
                            </argus-html:window>
                        </perm:present>
                        <perm:present module="/file" path="/mailRegister">
                            <c:set var="title"><bean:message key="label.mail" bundle="${bundle}" /></c:set>
                            <argus-html:window id="mailRegister" title="${title}" collapse="true">
                                <perm:present module="/file" path="/mailRegister">
                                    <argus-html:winoption id="mailRegisterNew" url="javascript:;" title="Edit">edit</argus-html:winoption>
                                </perm:present>
                                <jsp:include page="/WEB-INF/module/file/mail/viewAll.jsp" />
                            </argus-html:window>
                        </perm:present>
                    </div>
                </div>
            </div>
        </div>
    </html:form>

</jsp:root>