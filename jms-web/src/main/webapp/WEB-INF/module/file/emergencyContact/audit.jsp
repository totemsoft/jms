<?xml version="1.0" encoding="UTF-8"?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:html="http://struts.apache.org/tags-html"
    xmlns:bean="http://struts.apache.org/tags-bean"
    xmlns:logic="http://struts.apache.org/tags-logic"
    xmlns:c="http://java.sun.com/jsp/jstl/core"
    xmlns:fn="http://java.sun.com/jsp/jstl/functions"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    version="2.0">

    <html:xhtml/>

    <c:set var="bundle" value="file" />

    <c:set var="buildingContacts" value="${fileAuditForm.ownerBuildingContacts}" scope="request" />
    <c:set var="buildingContactsProperty" value="ownerBuildingContacts" scope="request" />
    <c:if test="${fn:length(buildingContacts) gt 0}">
        <table class="nonDataTable">
            <caption>Day Emergency Contact Details</caption>
            <tr>
                <td>
                    <jsp:include page="/WEB-INF/module/file/emergencyContact/auditByType.jsp" />
                </td>
            </tr>
        </table>
    </c:if>

    <c:set var="buildingContacts" value="${fileAuditForm.securityBuildingContacts}" scope="request" />
    <c:set var="buildingContactsProperty" value="securityBuildingContacts" scope="request" />
    <c:if test="${fn:length(buildingContacts) gt 0}">
        <table class="nonDataTable">
            <caption>Security Company Contact Details</caption>
            <tr>
                <td>
                    <jsp:include page="/WEB-INF/module/file/emergencyContact/auditByType.jsp" />
                </td>
            </tr>
        </table>
    </c:if>

</jsp:root>