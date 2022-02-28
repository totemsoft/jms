<?xml version="1.0" encoding="UTF-8"?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:html="http://struts.apache.org/tags-html"
    xmlns:bean="http://struts.apache.org/tags-bean"
    xmlns:logic="http://struts.apache.org/tags-logic"
    xmlns:c="http://java.sun.com/jsp/jstl/core"
    xmlns:fn="http://java.sun.com/jsp/jstl/functions"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    xmlns:argus-html="/WEB-INF/tld/argus-html.tld"
    version="2.0">

    <html:xhtml/>

    <c:set var="bundle" value="file" />

    <html:form action="/fileAudit.do?dispatch=save">
        <argus-html:window id="sapBillingAudit" title="SAP Billing Data" collapse="false">
            <jsp:include page="/WEB-INF/module/file/sapBilling/audit.jsp" />
        </argus-html:window>

        <argus-html:window id="buildingDetailsAudit" title="Building Details" collapse="false">
            <jsp:include page="/WEB-INF/module/file/building/audit.jsp" />
        </argus-html:window>

		<div id="auditTabView" class="yui-navset" style="padding-top: 5px;">
		    <ul class="yui-nav">
		        <li class="selected"><a href="#tab1"><em>Owner</em></a></li>
		        <li><a href="#tab2"><em>Body Corporate</em></a></li>
		        <li><a href="#tab3"><em>Property Manager</em></a></li>
		        <li><a href="#tab4"><em>Tenant</em></a></li>
		        <li><a href="#tab5"><em>Alternate</em></a></li>
		        <li><a href="#tab6"><em>Emergency Contact</em></a></li>
		    </ul>
		    <div class="yui-content">
		        <c:set var="owner" value="owner" scope="request" />
		        <div id="${owner}DetailsAudit">
		            <c:set var="auditOwner" value="${fileAuditForm.owner}" scope="request" />
		            <c:set var="originalOwner" value="${original.owner}" scope="request" />
		            <jsp:include page="/WEB-INF/module/file/owner/audit.jsp" />
		        </div>
		
		        <c:set var="owner" value="bodyCorporate" scope="request" />
		        <div id="${owner}DetailsAudit">
		            <c:set var="auditOwner" value="${fileAuditForm.bodyCorporate}" scope="request" />
		            <c:set var="originalOwner" value="${original.bodyCorporate}" scope="request" />
		            <jsp:include page="/WEB-INF/module/file/owner/audit.jsp" />
		        </div>
		
		        <c:set var="owner" value="propertyManager" scope="request" />
		        <div id="${owner}DetailsAudit">
		            <c:set var="auditOwner" value="${fileAuditForm.propertyManager}" scope="request" />
		            <c:set var="originalOwner" value="${original.propertyManager}" scope="request" />
		            <jsp:include page="/WEB-INF/module/file/owner/audit.jsp" />
		        </div>
		
		        <c:set var="owner" value="tenant" scope="request" />
		        <div id="${owner}DetailsAudit">
		            <c:set var="auditOwner" value="${fileAuditForm.tenant}" scope="request" />
		            <c:set var="originalOwner" value="${original.tenant}" scope="request" />
		            <jsp:include page="/WEB-INF/module/file/owner/audit.jsp" />
		        </div>
		
		        <c:set var="owner" value="alternate" scope="request" />
		        <div id="${owner}DetailsAudit">
		            <c:set var="auditOwner" value="${fileAuditForm.alternate}" scope="request" />
		            <c:set var="originalOwner" value="${original.alternate}" scope="request" />
		            <jsp:include page="/WEB-INF/module/file/owner/audit.jsp" />
		        </div>
		
		        <div id="emergencyContactAudit">
		            <jsp:include page="/WEB-INF/module/file/emergencyContact/audit.jsp" />
		        </div>
		    </div>
		</div>
    </html:form>

    <script type="text/javascript">
        var auditTabView = new YAHOO.widget.TabView('auditTabView');
    </script>

</jsp:root>