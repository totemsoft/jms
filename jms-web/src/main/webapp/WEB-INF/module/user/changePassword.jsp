<?xml version="1.0" encoding="UTF-8"?>
<jsp:root
    xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:html="http://struts.apache.org/tags-html"
    xmlns:bean="http://struts.apache.org/tags-bean"
    xmlns:logic="http://struts.apache.org/tags-logic"
    xmlns:c="http://java.sun.com/jsp/jstl/core"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    version="2.0">

    <c:set var="action" value="/changePassword" />

    <head>
        <!-- to change the content type or response encoding change the following line -->
        <jsp:directive.page contentType="text/html;charset=utf-8"/>

        <title><bean:message key="title.changePassword.step2" /></title>

        <script>
            YAHOO.namespace("jms");
            function init() {
                // Instantiate a Panel from markup
                YAHOO.jms.changePassword = new YAHOO.widget.Panel("changePassword",
                    { fixedcenter:true, width:"350px", visible:true, draggable:false, close:false } );
                YAHOO.jms.changePassword.render();
            }
            YAHOO.util.Event.addListener(window, "load", init);
        </script>
    </head>

    <body class="yui-skin-sam">
        <div id="changePassword">
            <html:form action="${action}">
                <html:hidden property="dispatch" value="save" />
                <html:hidden property="readOnly" value="false" />
                <html:hidden property="entity.userId" />
                <html:hidden property="entity.active" />
                <html:hidden property="contact.contactId" />

                <div class="hd, maximized_title">
                    <c:set var="icon"><bean:message key="icon.security" /></c:set>
                    <img src="${icon}" hspace="4" />
                    <bean:message key="title.changePassword.step2" />
                </div>

                <div class="bd">
                    <p><bean:message key="title.changePassword.step2" /></p>

                    <div id="errors" class="error">
                        <html:errors />
                    </div>

                    <table class="nonDataTable">
                        <tr>
                            <td class="jms-label"><bean:message key="label.user.login" /></td>
                            <td><html:text property="entity.login" readonly="true" styleClass="text" /></td>
                        </tr>

                        <!-- FIXME: does not decorate on WebLogic -->
                        <!--jsp:include page="/WEB-INF/entity/contact/edit.jsp" /-->
                        <tr>
                            <td class="jms-label"><bean:message key="label.contact.salutation" /></td>
                            <td>
                                <html:select property="contact.salutation.salutation">
                                    <!--option value=""><bean:message key="option.select" /></option-->
                                    <html:options collection="salutations" property="salutation" labelProperty="salutation" />
                                </html:select>
                            </td>
                        </tr>
                        <tr>
                            <td class="jms-label"><bean:message key="label.contact.firstName" /></td>
                            <td><html:text property="contact.firstName" styleClass="text" /></td>
                        </tr>
                        <tr>
                            <td class="jms-label"><bean:message key="label.contact.surname" /></td>
                            <td><html:text property="contact.surname" styleClass="text" /></td>
                        </tr>
                        <tr>
                            <td class="jms-label"><bean:message key="label.contact.email" /></td>
                            <td><html:text property="contact.email" styleClass="text" /></td>
                        </tr>
                        <tr>
                            <td class="jms-label-optional"><bean:message key="label.contact.emailConfirm" /></td>
                            <td><html:text property="contact.emailConfirm" styleClass="text" /></td>
                        </tr>
                        <tr>
                            <td class="jms-label"><bean:message key="label.contact.phone" /></td>
                            <td><html:text property="contact.phone" styleClass="text" /></td>
                        </tr>
                        <tr>
                            <td class="jms-label"><bean:message key="label.contact.fax" /></td>
                            <td><html:text property="contact.fax" styleClass="text" /></td>
                        </tr>
                        <tr>
                            <td class="jms-label"><bean:message key="label.contact.mobile" /></td>
                            <td><html:text property="contact.mobile" styleClass="text" /></td>
                        </tr>
                        <!--tr>
                            <td class="jms-label"><bean:message key="label.contact.skype" /></td>
                            <td><html:text property="contact.skype" styleClass="text" /></td>
                        </tr-->
                        <!-- FIXME: does not decorate on WebLogic -->

                        <tr>
                            <td></td>
                            <td>
                                <html:submit property="save" styleClass="button-submit">
                                    <bean:message key="button.change" />
                                </html:submit>
                            </td>
                        </tr>
                    </table>
                </div>
            </html:form>
        </div>
    </body>

</jsp:root>