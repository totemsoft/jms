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

    <c:set var="bundle" value="setup" />

    <html:form action="/scheduledTask.do?dispatch=saveImportData" enctype="multipart/form-data">
        <html:hidden property="entity.scheduledTaskId" />

        <div id="errors" class="error">
            <html:errors />
        </div>

        <jsp:include page="/WEB-INF/entity/upload.jsp" />
    </html:form>

</jsp:root>