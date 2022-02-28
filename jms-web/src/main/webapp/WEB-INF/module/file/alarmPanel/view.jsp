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

    <table class="nonDataTable">
        <tr>
            <td class="jms-label"><bean:message key="label.alarmPanel.supplier" bundle="${bundle}" /></td>
            <td colspan="3">
                <html:text property="entity.alarmPanel.supplier.name" readonly="true" styleClass="text" />
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
                <html:text property="entity.alarmPanel.alrmSystType" readonly="true" styleClass="text" />
            </td>
        </tr>
        <tr>
            <td class="jms-label"><bean:message key="label.alarmPanel.alrmManuf" bundle="${bundle}" /></td>
            <td>
                <html:text property="entity.alarmPanel.alrmManuf" readonly="true" styleClass="text" />
            </td>
            <td class="jms-label"><bean:message key="label.alarmPanel.alrmModel" bundle="${bundle}" /></td>
            <td>
                <html:text property="entity.alarmPanel.alrmModel" readonly="true" styleClass="text" />
            </td>
        </tr>
        <tr>
            <td class="jms-label"><bean:message key="label.alarmPanel.panelLoc" bundle="${bundle}" /></td>
            <td colspan="3">
                <html:text property="entity.alarmPanel.panelLoc" readonly="true" styleClass="text" />
            </td>
        </tr>
        <tr>
            <td class="jms-label"><bean:message key="label.alarmPanel.subPanelQty" bundle="${bundle}" /></td>
            <td>
                <html:text property="entity.alarmPanel.subPanelQty" styleClass="text numberInput" readonly="true" />
            </td>
            <td colspan="2"></td>
        </tr>
    </table>

    <table class="nonDataTable">
        <caption><bean:message key="label.alarmPanel.detector" bundle="${bundle}" /></caption>
        <tr>
            <td class="jms-label"><bean:message key="label.alarmPanel.dthermQty" bundle="${bundle}" /></td>
            <td>
                <html:text property="entity.alarmPanel.dthermQty" styleClass="text numberInput" readonly="true" />
            </td>
            <td class="jms-label"><bean:message key="label.alarmPanel.dsmokeQty" bundle="${bundle}" /></td>
            <td>
                <html:text property="entity.alarmPanel.dsmokeQty" styleClass="text numberInput" readonly="true" />
            </td>
        </tr>
        <tr>
            <td class="jms-label"><bean:message key="label.alarmPanel.dcombustQty" bundle="${bundle}" /></td>
            <td>
                <html:text property="entity.alarmPanel.dcombustQty" styleClass="text numberInput" readonly="true" />
            </td>
            <td class="jms-label"><bean:message key="label.alarmPanel.duvQty" bundle="${bundle}" /></td>
            <td>
                <html:text property="entity.alarmPanel.duvQty" styleClass="text numberInput" readonly="true" />
            </td>
        </tr>
        <tr>
            <td class="jms-label"><bean:message key="label.alarmPanel.dinfredQty" bundle="${bundle}" /></td>
            <td>
                <html:text property="entity.alarmPanel.dinfredQty" styleClass="text numberInput" readonly="true" />
            </td>
            <td class="jms-label"><bean:message key="label.alarmPanel.dmulticQty" bundle="${bundle}" /></td>
            <td>
                <html:text property="entity.alarmPanel.dmulticQty" styleClass="text numberInput" readonly="true" />
            </td>
        </tr>
        <tr>
            <td class="jms-label"><bean:message key="label.alarmPanel.dsprinkQty" bundle="${bundle}" /></td>
            <td>
                <html:text property="entity.alarmPanel.dsprinkQty" styleClass="text numberInput" readonly="true" />
            </td>
            <td class="jms-label"><bean:message key="label.alarmPanel.dairsampQty" bundle="${bundle}" /></td>
            <td>
                <html:text property="entity.alarmPanel.dairsampQty" styleClass="text numberInput" readonly="true" />
            </td>
        </tr>
        <tr>
            <td class="jms-label"><bean:message key="label.alarmPanel.dmancallQty" bundle="${bundle}" /></td>
            <td>
                <html:text property="entity.alarmPanel.dmancallQty" styleClass="text numberInput" readonly="true" />
            </td>
            <td class="jms-label"><bean:message key="label.alarmPanel.dotherQty" bundle="${bundle}" /></td>
            <td>
                <html:text property="entity.alarmPanel.dotherQty" styleClass="text numberInput" readonly="true" />
            </td>
        </tr>
        <tr>
            <td class="jms-label"><bean:message key="label.alarmPanel.dotherDefine" bundle="${bundle}" /></td>
            <td colspan="3">
                <html:text property="entity.alarmPanel.dotherDefine" readonly="true" styleClass="text" />
            </td>
        </tr>
    </table>

    <table class="nonDataTable">
        <caption><bean:message key="label.alarmPanel.sfs" bundle="${bundle}" /></caption>
        <tr>
            <td class="jms-label"><bean:message key="label.alarmPanel.sfsDualDetect" bundle="${bundle}" /></td>
            <td>
                <html:text property="entity.alarmPanel.sfsDualDetect" readonly="true" styleClass="text" />
            </td>
        </tr>
        <tr>
            <td class="jms-label"><bean:message key="label.alarmPanel.sfsAircon" bundle="${bundle}" /></td>
            <td>
                <html:text property="entity.alarmPanel.sfsAircon" readonly="true" styleClass="text" />
            </td>
        </tr>
        <tr>
            <td class="jms-label"><bean:message key="label.alarmPanel.sfsElevator" bundle="${bundle}" /></td>
            <td>
                <html:text property="entity.alarmPanel.sfsElevator" readonly="true" styleClass="text" />
            </td>
        </tr>
        <tr>
            <td class="jms-label"><bean:message key="label.alarmPanel.sfsEvacctrl" bundle="${bundle}" /></td>
            <td>
                <html:text property="entity.alarmPanel.sfsEvacctrl" readonly="true" styleClass="text" />
            </td>
        </tr>
        <tr>
            <td class="jms-label"><bean:message key="label.alarmPanel.sfsFiredoors" bundle="${bundle}" /></td>
            <td>
                <html:text property="entity.alarmPanel.sfsFiredoors" readonly="true" styleClass="text" />
            </td>
        </tr>
        <tr>
            <td class="jms-label"><bean:message key="label.alarmPanel.sfsBooster" bundle="${bundle}" /></td>
            <td>
                <html:text property="entity.alarmPanel.sfsBooster" readonly="true" styleClass="text" />
            </td>
        </tr>
        <tr>
            <td class="jms-label"><bean:message key="label.alarmPanel.sfsDrypipe" bundle="${bundle}" /></td>
            <td>
                <html:text property="entity.alarmPanel.sfsDrypipe" readonly="true" styleClass="text" />
            </td>
        </tr>
        <tr>
            <td class="jms-label"><bean:message key="label.alarmPanel.sfsOther" bundle="${bundle}" /></td>
            <td>
                <html:text property="entity.alarmPanel.sfsOther" readonly="true" styleClass="text" />
            </td>
        </tr>
    </table>

    <table class="nonDataTable">
        <caption><bean:message key="label.alarmPanel.mimic" bundle="${bundle}" /></caption>
        <tr>
            <td class="jms-label"><bean:message key="label.alarmPanel.mimcLoc" bundle="${bundle}" /></td>
            <td>
                <html:text property="entity.alarmPanel.mimcLoc" readonly="true" styleClass="text" />
            </td>
        </tr>
        <tr>
            <td class="jms-label"><bean:message key="label.alarmPanel.mimicLayout" bundle="${bundle}" /></td>
            <td>
                <html:text property="entity.alarmPanel.mimicLayout" readonly="true" styleClass="text" />
            </td>
        </tr>
    </table>

    <table class="nonDataTable">
        <caption><bean:message key="label.alarmPanel.areaCircdes" bundle="${bundle}" /></caption>
        <tr>
            <td>
                <html:text property="entity.alarmPanel.areaCircdes" readonly="true" styleClass="text" />
            </td>
        </tr>
    </table>

    <table class="nonDataTable">
        <caption><bean:message key="label.alarmPanel.mods" bundle="${bundle}" /></caption>
        <tr>
            <td>
                <html:text property="entity.alarmPanel.mods" readonly="true" styleClass="text" />
            </td>
        </tr>
    </table>

</jsp:root>