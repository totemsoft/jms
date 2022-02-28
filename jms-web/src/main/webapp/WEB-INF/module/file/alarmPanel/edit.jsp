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

    <html:form action="/editAlarmPanel">
        <html:hidden property="dispatch" value="save" />

        <div id="errors" class="error">
            <html:errors />
        </div>

        <table class="nonDataTable">
            <html:errors property="entity.alarmPanel.supplier.supplierId" />
            <tr>
                <td class="jms-label"><bean:message key="label.alarmPanel.supplier" bundle="${bundle}" /></td>
                <td colspan="3">
                    <html:select property="entity.alarmPanel.supplier.supplierId"
                        onchange="YAHOO.jms.sendPostRequest(this.form, this.form.action + '?dispatch=changeSupplier');">
                        <option value=""><bean:message key="option.select" /></option>
                        <html:options collection="suppliers" property="supplierId" labelProperty="name" />
                    </html:select>
                </td>
            </tr>
            <c:if test="${not empty fileForm.entity.alarmPanel.supplier.contact}">
                <tr>
                    <td class="jms-label"><bean:message key="label.contact.phone" /></td>
                    <td><html:text property="entity.alarmPanel.supplier.contact.phone" readonly="true" styleClass="text" /></td>
                    <td class="jms-label"><bean:message key="label.contact.fax" /></td>
                    <td><html:text property="entity.alarmPanel.supplier.contact.fax" readonly="true" styleClass="text" /></td>
                </tr>
            </c:if>
            <tr>
                <td class="jms-label"><bean:message key="label.alarmPanel.alrmSystType" bundle="${bundle}" /></td>
                <td colspan="3">
                    <html:text property="entity.alarmPanel.alrmSystType" styleClass="text" />
                </td>
            </tr>
            <tr>
                <td class="jms-label"><bean:message key="label.alarmPanel.alrmManuf" bundle="${bundle}" /></td>
                <td>
                    <html:text property="entity.alarmPanel.alrmManuf" styleClass="text" />
                </td>
                <td class="jms-label"><bean:message key="label.alarmPanel.alrmModel" bundle="${bundle}" /></td>
                <td>
                    <html:text property="entity.alarmPanel.alrmModel" styleClass="text" />
                </td>
            </tr>
            <tr>
                <td class="jms-label"><bean:message key="label.alarmPanel.panelLoc" bundle="${bundle}" /></td>
                <td>
                    <html:text property="entity.alarmPanel.panelLoc" styleClass="text" />
                </td>
                <td class="jms-label"><bean:message key="label.alarmPanel.subPanelQty" bundle="${bundle}" /></td>
                <td>
                    <html:text property="entity.alarmPanel.subPanelQty" styleClass="text numberInput" />
                </td>
            </tr>
        </table>

        <table class="nonDataTable">
            <caption><bean:message key="label.alarmPanel.detector" bundle="${bundle}" /></caption>
            <tr>
                <td class="jms-label"><bean:message key="label.alarmPanel.dthermQty" bundle="${bundle}" /></td>
                <td>
                    <html:text property="entity.alarmPanel.dthermQty" styleClass="text numberInput" />
                </td>
                <td class="jms-label"><bean:message key="label.alarmPanel.dsmokeQty" bundle="${bundle}" /></td>
                <td>
                    <html:text property="entity.alarmPanel.dsmokeQty" styleClass="text numberInput" />
                </td>
            </tr>
            <tr>
                <td class="jms-label"><bean:message key="label.alarmPanel.dcombustQty" bundle="${bundle}" /></td>
                <td>
                    <html:text property="entity.alarmPanel.dcombustQty" styleClass="text numberInput" />
                </td>
                <td class="jms-label"><bean:message key="label.alarmPanel.duvQty" bundle="${bundle}" /></td>
                <td>
                    <html:text property="entity.alarmPanel.duvQty" styleClass="text numberInput" />
                </td>
            </tr>
            <tr>
                <td class="jms-label"><bean:message key="label.alarmPanel.dinfredQty" bundle="${bundle}" /></td>
                <td>
                    <html:text property="entity.alarmPanel.dinfredQty" styleClass="text numberInput" />
                </td>
                <td class="jms-label"><bean:message key="label.alarmPanel.dmulticQty" bundle="${bundle}" /></td>
                <td>
                    <html:text property="entity.alarmPanel.dmulticQty" styleClass="text numberInput" />
                </td>
            </tr>
            <tr>
                <td class="jms-label"><bean:message key="label.alarmPanel.dsprinkQty" bundle="${bundle}" /></td>
                <td>
                    <html:text property="entity.alarmPanel.dsprinkQty" styleClass="text numberInput" />
                </td>
                <td class="jms-label"><bean:message key="label.alarmPanel.dairsampQty" bundle="${bundle}" /></td>
                <td>
                    <html:text property="entity.alarmPanel.dairsampQty" styleClass="text numberInput" />
                </td>
            </tr>
            <tr>
                <td class="jms-label"><bean:message key="label.alarmPanel.dmancallQty" bundle="${bundle}" /></td>
                <td>
                    <html:text property="entity.alarmPanel.dmancallQty" styleClass="text numberInput" />
                </td>
                <td class="jms-label"><bean:message key="label.alarmPanel.dotherQty" bundle="${bundle}" /></td>
                <td>
                    <html:text property="entity.alarmPanel.dotherQty" styleClass="text numberInput" />
                </td>
            </tr>
            <tr>
                <td class="jms-label"><bean:message key="label.alarmPanel.dotherDefine" bundle="${bundle}" /></td>
                <td colspan="3">
                    <html:text property="entity.alarmPanel.dotherDefine" styleClass="text" />
                </td>
            </tr>
        </table>

        <table class="nonDataTable">
            <caption><bean:message key="label.alarmPanel.sfs" bundle="${bundle}" /></caption>
            <tr>
                <td class="jms-label"><bean:message key="label.alarmPanel.sfsDualDetect" bundle="${bundle}" /></td>
                <td>
                    <html:text property="entity.alarmPanel.sfsDualDetect" styleClass="text" />
                </td>
                <td class="jms-label"><bean:message key="label.alarmPanel.sfsAircon" bundle="${bundle}" /></td>
                <td>
                    <html:text property="entity.alarmPanel.sfsAircon" styleClass="text" />
                </td>
            </tr>
            <tr>
                <td class="jms-label"><bean:message key="label.alarmPanel.sfsElevator" bundle="${bundle}" /></td>
                <td>
                    <html:text property="entity.alarmPanel.sfsElevator" styleClass="text" />
                </td>
                <td class="jms-label"><bean:message key="label.alarmPanel.sfsEvacctrl" bundle="${bundle}" /></td>
                <td>
                    <html:text property="entity.alarmPanel.sfsEvacctrl" styleClass="text" />
                </td>
            </tr>
            <tr>
                <td class="jms-label"><bean:message key="label.alarmPanel.sfsFiredoors" bundle="${bundle}" /></td>
                <td>
                    <html:text property="entity.alarmPanel.sfsFiredoors" styleClass="text" />
                </td>
                <td class="jms-label"><bean:message key="label.alarmPanel.sfsBooster" bundle="${bundle}" /></td>
                <td>
                    <html:text property="entity.alarmPanel.sfsBooster" styleClass="text" />
                </td>
            </tr>
            <tr>
                <td class="jms-label"><bean:message key="label.alarmPanel.sfsDrypipe" bundle="${bundle}" /></td>
                <td>
                    <html:text property="entity.alarmPanel.sfsDrypipe" styleClass="text" />
                </td>
                <td class="jms-label"><bean:message key="label.alarmPanel.sfsOther" bundle="${bundle}" /></td>
                <td>
                    <html:text property="entity.alarmPanel.sfsOther" styleClass="text" />
                </td>
            </tr>
        </table>

        <table class="nonDataTable">
            <caption><bean:message key="label.alarmPanel.mimic" bundle="${bundle}" /></caption>
            <tr>
                <td class="jms-label"><bean:message key="label.alarmPanel.mimcLoc" bundle="${bundle}" /></td>
                <td>
                    <html:text property="entity.alarmPanel.mimcLoc" styleClass="text" />
                </td>
                <td class="jms-label"><bean:message key="label.alarmPanel.mimicLayout" bundle="${bundle}" /></td>
                <td>
                    <html:text property="entity.alarmPanel.mimicLayout" styleClass="text" />
                </td>
            </tr>
        </table>

        <table class="nonDataTable">
            <caption><bean:message key="label.alarmPanel.areaCircdes" bundle="${bundle}" /></caption>
            <tr>
                <td>
                    <html:text property="entity.alarmPanel.areaCircdes" styleClass="text" />
                </td>
            </tr>
        </table>

        <table class="nonDataTable">
            <caption><bean:message key="label.alarmPanel.mods" bundle="${bundle}" /></caption>
            <tr>
                <td>
                    <html:text property="entity.alarmPanel.mods" styleClass="text" />
                </td>
            </tr>
        </table>

    </html:form>

</jsp:root>