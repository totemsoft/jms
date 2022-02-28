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

    <html:form action="/editArea">
        <html:hidden property="dispatch" value="save" />

        <div id="errors" class="error">
            <html:errors />
        </div>

        <table class="nonDataTable">
            <html:errors property="entity.areaId" />
            <tr>
                <td class="jms-label"><bean:message key="label.area.areaId" bundle="${bundle}" /></td>
                <td><html:text property="entity.areaId" readonly="${not empty areaForm.entity.areaId}" styleClass="text" /></td>
                <td class="jms-label"><bean:message key="label.region.active" bundle="${bundle}" /></td>
                <td>
                    <html:hidden property="entity.active" styleId="active" />
                    <html:checkbox property="entity.active" onchange="this.form.active.value=this.checked;" />
                </td>
            </tr>

            <html:errors property="entity.name" />
            <tr>
                <td class="jms-label"><bean:message key="label.area.name" bundle="${bundle}" /></td>
                <td colspan="3"><html:text property="entity.name" styleClass="text" /></td>
            </tr>

            <html:errors property="entity.region.regionId" />
            <tr>
                <td class="jms-label"><bean:message key="label.region" bundle="${bundle}" /></td>
                <td colspan="3">
                    <html:select property="entity.region.regionId">
                        <option value=""><bean:message key="option.select" /></option>
                        <html:options collection="regions" property="regionId" labelProperty="name" />
                    </html:select>
                </td>
            </tr>

            <tr>
                <td colspan="4">
                    <jsp:include page="/WEB-INF/entity/contact/edit.jsp" />
                </td>
            </tr>

            <tr>
                <td colspan="4">
                    <jsp:include page="/WEB-INF/entity/address/edit.jsp" />
                </td>
            </tr>
        </table>
    </html:form>

</jsp:root>