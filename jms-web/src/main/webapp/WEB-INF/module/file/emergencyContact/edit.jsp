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

    <html:form action="/editEmergencyContact" enctype="multipart/form-data">
        <html:hidden property="dispatch" value="save" />

        <div id="errors" class="error">
            <html:errors />
        </div>

        <c:set var="attachmentIndex" value="0" scope="request" />

        <table class="nonDataTable">
            <caption>Emergency Contact Details</caption>
            <tr>
                <td>
                    <c:set var="buildingContactType" value="owner" scope="request" />
                    <c:set var="buildingContacts" value="${fileForm.ownerBuildingContacts}" scope="request" />
                    <c:set var="buildingContactsProperty" value="ownerBuildingContacts" scope="request" />
                    <c:set var="copyBuildingContactDaytime" value="false" scope="request" />
                    <jsp:include page="/WEB-INF/module/file/emergencyContact/editByType.jsp" />
                </td>
            </tr>
        </table>

        <table class="nonDataTable">
            <caption>Fire Company Contact Details</caption>
            <tr>
                <td>
                    <c:set var="buildingContactType" value="fire" scope="request" />
                    <c:set var="buildingContacts" value="${fileForm.fireBuildingContacts}" scope="request" />
                    <c:set var="buildingContactsProperty" value="fireBuildingContacts" scope="request" />
                    <c:set var="copyBuildingContactDaytime" value="false" scope="request" />
                    <jsp:include page="/WEB-INF/module/file/emergencyContact/editByType.jsp" />
                </td>
            </tr>
        </table>

        <table class="nonDataTable">
            <caption>Security Company Contact Details</caption>
            <tr>
                <td>
                    <c:set var="buildingContactType" value="security" scope="request" />
                    <c:set var="buildingContacts" value="${fileForm.securityBuildingContacts}" scope="request" />
                    <c:set var="buildingContactsProperty" value="securityBuildingContacts" scope="request" />
                    <c:set var="copyBuildingContactDaytime" value="false" scope="request" />
                    <jsp:include page="/WEB-INF/module/file/emergencyContact/editByType.jsp" />
                </td>
            </tr>
        </table>

    </html:form>

</jsp:root>