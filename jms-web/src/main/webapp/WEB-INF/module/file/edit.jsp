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
    <c:set var="entity" value="${fileForm.entity}" scope="request" />
    <c:set var="parentFile" value="${entity.fca.parent.file}" />
    <c:set var="fileAudit" value="${entity.fileAudit}" scope="request" />
    <c:set var="mailBatchFile" value="${entity.lastMailBatchFile}" scope="request" />

    <html:form action="/editFile" method="get">
        <html:hidden styleId="fileId" property="entity.fileId" />

        <div id="doc" class="width100">
            <div id="hd">
                <div class="top-bar jms-fileStatus-${entity.fileStatus.id}">
                    <b>File:</b> ${entity.id} | <b>FCA No:</b> ${entity.fca.id} | <b>File Status:</b> ${entity.fileStatus.name} | <b>Job Status:</b> ${entity.jobStatus}
                </div>
                <jsp:include page="/WEB-INF/module/file/pagetools.jsp" />
            </div>
            <div id="bd">
                <div id="yui-main" class="yui-g">
                    <div class="yui-u first">
                        <perm:present module="/file" path="/viewOwner">
                            <c:set var="title">Owner Details ${not empty entity.owner.file.id and entity.owner.file.id ne entity.id ? '(from parent)' : ''}</c:set>
                            <c:set var="owner" scope="request">owner</c:set>
                            <argus-html:window id="${owner}Details" title="${title}" collapse="true" collapsed="${not entity.owner.defaultContact}">
                                <perm:present module="/file" path="/editOwner">
                                    <argus-html:winoption id="${owner}Edit" url="javascript:;" title="Edit">edit</argus-html:winoption>
                                    <c:set var="parentOwner" value="${parentFile.owner}" />
                                    <input type="hidden" id="copy.${owner}.parentId" value="${parentOwner.id}" />
                                    <input type="hidden" id="copy.${owner}.ownerTypeId" value="1" />
                                    <argus-html:winoption id="copy.${owner}" className="float-left" url="" title="Right Click to Copy">copy</argus-html:winoption>
                                </perm:present>
                                <c:if test="${not empty entity.owner}">
                                    <c:set var="defaultContact" value="${entity.owner.defaultContact}" scope="request" />
                                    <c:set var="beanPrefix" value="${owner}." scope="request" />
                                    <jsp:include page="/WEB-INF/module/file/owner/view.jsp" />
                                    <c:set var="beanPrefix" value="" scope="request" />
                                </c:if>
                            </argus-html:window>
                            <c:set var="title">Body Corporate Details ${not empty entity.bodyCorporate.file.id and entity.bodyCorporate.file.id ne entity.id ? '(from parent)' : ''}</c:set>
                            <c:set var="owner" scope="request">bodyCorporate</c:set>
                            <argus-html:window title="${title}" collapse="true" collapsed="${not entity.bodyCorporate.defaultContact}">
                                <perm:present module="/file" path="/editOwner">
                                    <argus-html:winoption id="${owner}Edit" url="javascript:;" title="Edit">edit</argus-html:winoption>
                                    <c:set var="parentOwner" value="${parentFile.bodyCorporate}" />
                                    <input type="hidden" id="copy.${owner}.parentId" value="${parentOwner.id}" />
                                    <input type="hidden" id="copy.${owner}.ownerTypeId" value="2" />
                                    <argus-html:winoption id="copy.${owner}" className="float-left" url="" title="Right Click to Copy">copy</argus-html:winoption>
                                </perm:present>
                                <c:if test="${not empty entity.bodyCorporate}">
                                    <c:set var="defaultContact" value="${entity.bodyCorporate.defaultContact}" scope="request" />
                                    <c:set var="beanPrefix" value="${owner}." scope="request" />
                                    <jsp:include page="/WEB-INF/module/file/owner/view.jsp" />
                                    <c:set var="beanPrefix" value="" scope="request" />
                                </c:if>
                            </argus-html:window>
                            <c:set var="title">Property Manager Details ${not empty entity.propertyManager.file.id and entity.propertyManager.file.id ne entity.id ? '(from parent)' : ''}</c:set>
                            <c:set var="owner" scope="request">propertyManager</c:set>
                            <argus-html:window title="${title}" collapse="true" collapsed="${not entity.propertyManager.defaultContact}">
                                <perm:present module="/file" path="/editOwner">
                                    <argus-html:winoption id="${owner}Edit" url="javascript:;" title="Edit">edit</argus-html:winoption>
                                    <c:set var="parentOwner" value="${parentFile.propertyManager}" />
                                    <input type="hidden" id="copy.${owner}.parentId" value="${parentOwner.id}" />
                                    <input type="hidden" id="copy.${owner}.ownerTypeId" value="3" />
                                    <argus-html:winoption id="copy.${owner}" className="float-left" url="" title="Right Click to Copy">copy</argus-html:winoption>
                                </perm:present>
                                <c:if test="${not empty entity.propertyManager}">
                                    <c:set var="defaultContact" value="${entity.propertyManager.defaultContact}" scope="request" />
                                    <c:set var="beanPrefix" value="${owner}." scope="request" />
                                    <jsp:include page="/WEB-INF/module/file/owner/view.jsp" />
                                    <c:set var="beanPrefix" value="" scope="request" />
                                </c:if>
                            </argus-html:window>
                            <c:set var="title">Tenant Details ${not empty entity.tenant.file.id and entity.tenant.file.id ne entity.id ? '(from parent)' : ''}</c:set>
                            <c:set var="owner" scope="request">tenant</c:set>
                            <argus-html:window title="${title}" collapse="true" collapsed="${not entity.tenant.defaultContact}">
                                <perm:present module="/file" path="/editOwner">
                                    <argus-html:winoption id="${owner}Edit" url="javascript:;" title="Edit">edit</argus-html:winoption>
                                    <c:set var="parentOwner" value="${parentFile.tenant}" />
                                    <input type="hidden" id="copy.${owner}.parentId" value="${parentOwner.id}" />
                                    <input type="hidden" id="copy.${owner}.ownerTypeId" value="4" />
                                    <argus-html:winoption id="copy.${owner}" className="float-left" url="" title="Right Click to Copy">copy</argus-html:winoption>
                                </perm:present>
                                <c:if test="${not empty entity.tenant}">
                                    <c:set var="defaultContact" value="${entity.tenant.defaultContact}" scope="request" />
                                    <c:set var="beanPrefix" value="${owner}." scope="request" />
                                    <jsp:include page="/WEB-INF/module/file/owner/view.jsp" />
                                    <c:set var="beanPrefix" value="" scope="request" />
                                </c:if>
                            </argus-html:window>
                        </perm:present>
                        <c:if test="${not empty entity.fca.id}">
                        <perm:present module="/file" path="/viewMultiSite">
                            <c:set var="title"><bean:message key="title.file.viewMultiSite" bundle="${bundle}" /></c:set>
                            <argus-html:window id="multiSite" title="${title}" collapse="true">
                                <perm:present module="/file" path="/editMultiSite">
                                    <argus-html:winoption id="multiSiteEdit" url="javascript:;" title="Edit">edit</argus-html:winoption>
                                </perm:present>
                                <jsp:include page="/WEB-INF/module/file/multiSite/view.jsp" />
                            </argus-html:window>
                        </perm:present>
                        </c:if>
                        <perm:present module="/file" path="/viewFcaAse">
                            <c:set var="title"></c:set>
                            <argus-html:window id="fcaAseDetails" title="FCA &amp; ASE Details" collapse="true">
                                <perm:present module="/file" path="/editFcaAse">
                                    <argus-html:winoption id="fcaAseDetailsEdit" url="javascript:;" title="Edit">edit</argus-html:winoption>
                                </perm:present>
                                <jsp:include page="/WEB-INF/module/file/fcaAse/view.jsp" />
                            </argus-html:window>
                        </perm:present>
                        <!--perm:present module="/file" path="/viewAseChange">
                            <c:set var="title"></c:set>
                            <argus-html:window id="aseChange" title="ASE Change Over - Supplier Scheduling" collapse="true">
                                <perm:present module="/file" path="/editAseChange">
                                    <argus-html:winoption id="aseChangeEdit" url="javascript:;" title="Edit">edit</argus-html:winoption>
                                </perm:present>
                                <jsp:include page="/WEB-INF/module/file/aseChange/view.jsp" />
                            </argus-html:window>
                        </perm:present-->
                        <perm:present module="/file" path="/viewStationKey">
                            <c:set var="title"></c:set>
                            <argus-html:window id="stationKey" title="Nearest Responding Station &amp; Keys" collapse="true">
                                <perm:present module="/file" path="/editStationKey">
                                    <argus-html:winoption id="stationKeyEdit" url="javascript:;" title="Edit">edit</argus-html:winoption>
                                </perm:present>
                                <c:set var="beanPrefix" value="station." scope="request" />
                                <jsp:include page="/WEB-INF/module/file/stationKey/view.jsp" />
                                <c:set var="beanPrefix" value="" scope="request" />
                            </argus-html:window>
                        </perm:present>
                        <perm:present module="/file" path="/viewEmergencyContact">
                            <c:set var="title"></c:set>
                            <argus-html:window id="emergencyContact" title="Emergency Contact" collapse="true">
                                <perm:present module="/file" path="/editEmergencyContact">
                                    <argus-html:winoption id="emergencyContactEdit" url="javascript:;" title="Edit">edit</argus-html:winoption>
                                    <c:if test="${fn:length(parentFile.buildingContacts) gt 0}">
                                        <input type="hidden" id="copy.emergencyContact.parentId" value="${parentFile.id}" />
                                        <argus-html:winoption id="copy.emergencyContact" className="float-left" url="" title="Right Click to Copy">copy</argus-html:winoption>
                                    </c:if>
                                </perm:present>
                                <jsp:include page="/WEB-INF/module/file/emergencyContact/view.jsp" />
                            </argus-html:window>
                        </perm:present>
                        <perm:present module="/file" path="/fileAudit">
                            <c:set var="title"><bean:message key="label.audit.om89" bundle="${bundle}" /></c:set>
                            <argus-html:window id="om89" title="${title}" collapse="true">
                                <jsp:include page="/WEB-INF/module/file/audit/om89/view.jsp" />
                            </argus-html:window>
                        </perm:present>
                        <perm:present module="/file" path="/viewAlarmPanel">
                            <c:set var="title"></c:set>
                            <argus-html:window id="alarmPanel" title="Fire Panel Data &amp; External Alarm Technician" collapse="true">
                                <perm:present module="/file" path="/editAlarmPanel">
                                    <argus-html:winoption id="alarmPanelEdit" url="javascript:;" title="Edit">edit</argus-html:winoption>
                                </perm:present>
                                <jsp:include page="/WEB-INF/module/file/alarmPanel/view.jsp" />
                            </argus-html:window>
                        </perm:present>
                    </div>
                    <div class="yui-u">
                        <perm:present module="/file" path="/viewBuilding">
                            <c:set var="title"></c:set>
                            <argus-html:window id="buildingDetails" title="Building Details" collapse="true">
                                <perm:present module="/file" path="/editBuilding">
                                    <argus-html:winoption id="buildingDetailsEdit" url="javascript:;" title="Edit">edit</argus-html:winoption>
                                </perm:present>
                                <jsp:include page="/WEB-INF/module/file/building/view.jsp" />
                            </argus-html:window>
                        </perm:present>
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
                        <perm:present module="/file" path="/viewFcaDoc">
                            <c:set var="title"><bean:message key="label.fcaDoc" bundle="${bundle}" /></c:set>
                            <argus-html:window id="fcaDoc" title="${title}" collapse="true">
                                <perm:present module="/file" path="/editFcaDoc">
                                    <input type="hidden" id="editFcaDoc" value="true" />
                                    <c:if test="${fn:length(fcaDocs) eq 0}">
                                        <argus-html:winoption id="fcaDocEdit" url="javascript:;" title="New Folder">New Folder</argus-html:winoption>
                                    </c:if>
                                </perm:present>
                                <jsp:include page="/WEB-INF/module/file/fcaDoc/view.jsp" />
                            </argus-html:window>
                        </perm:present>
                        <perm:present module="/file" path="/viewStakeholder">
                            <c:set var="title"></c:set>
                            <argus-html:window id="qfrsStakeholders" title="QFRS Stakeholders" collapse="true">
                                <jsp:include page="/WEB-INF/module/file/stakeholder/view.jsp" />
                            </argus-html:window>
                        </perm:present>
                        <perm:present module="/file" path="/viewSapBilling">
                            <c:set var="title"></c:set>
                            <argus-html:window id="sapBilling" title="SAP Billing Data" collapse="true">
                                <perm:present module="/file" path="/editSapBilling">
                                    <argus-html:winoption id="sapBillingEdit" url="javascript:;" title="Edit">edit</argus-html:winoption>
                                </perm:present>
                                <jsp:include page="/WEB-INF/module/file/sapBilling/view.jsp" />
                            </argus-html:window>
                        </perm:present>
                        <perm:present module="/file" path="/mailRegister">
                            <c:set var="title"><bean:message key="label.mail" bundle="${bundle}" /></c:set>
                            <argus-html:window id="mailRegister" title="${title}" collapse="true">
                                <perm:present module="/file" path="/mailRegister">
                                    <argus-html:winoption id="mailRegisterEdit" url="javascript:;" title="Edit">edit</argus-html:winoption>
                                </perm:present>
                                <jsp:include page="/WEB-INF/module/file/mail/viewAll.jsp" />
                            </argus-html:window>
                        </perm:present>
                    </div>
                </div>
                <div>
                    <perm:present module="/file" path="/viewCrmActions">
                        <c:set var="title"></c:set>
                        <argus-html:window id="crmActions" title="CRM Actions" collapse="true">
                            <jsp:include page="/WEB-INF/module/file/crmActions/view.jsp" />
                        </argus-html:window>
                    </perm:present>
                    <perm:present module="/file" path="/viewCrmActionsToDo">
                        <c:set var="title"></c:set>
                        <argus-html:window id="crmActionsToDo" title="CRM Actions ToDo" collapse="true">
                            <perm:present module="/file" path="/addFileAction">
                                <argus-html:winoption id="crmActionsToDoEdit" url="javascript:;" title="Add">add</argus-html:winoption>
                            </perm:present>
                            <jsp:include page="/WEB-INF/module/file/crmActionsToDo/view.jsp" />
                        </argus-html:window>
                    </perm:present>
                    <perm:present module="/file" path="/viewCrmCompletedActions">
                        <c:set var="title"></c:set>
                        <argus-html:window id="crmCompletedActions" title="CRM Completed Actions" collapse="true">
                            <jsp:include page="/WEB-INF/module/file/crmCompletedActions/view.jsp" />
                        </argus-html:window>
                    </perm:present>
                    <perm:present module="/file" path="/viewJobActions">
                        <c:set var="title"></c:set>
                        <argus-html:window id="jobActions" title="Job Actions" collapse="true">
                            <jsp:include page="/WEB-INF/module/file/jobActions/view.jsp" />
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
                        <argus-html:window id="jobCompletedActions" title="Job Completed Actions" collapse="true">
                            <jsp:include page="/WEB-INF/module/file/jobCompletedActions/view.jsp" />
                        </argus-html:window>
                    </perm:present>
                    <perm:present module="/file" path="/isolation">
                    <c:if test="${fn:length(entity.isolations) gt 0}">
                        <c:set var="title"><bean:message key="label.isolation" bundle="${bundle}" /></c:set>
                        <argus-html:window id="isolation" title="${title}" collapse="true">
                            <jsp:include page="/WEB-INF/module/file/isolation/viewAll.jsp" />
                        </argus-html:window>
                    </c:if>
                    </perm:present>
                </div>
            </div>
        </div>
    </html:form>

</jsp:root>