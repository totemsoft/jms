<?xml version="1.0" encoding="utf-8"?>
<jsp:root
    xmlns:jsp="http://java.sun.com/JSP/Page"
    xmlns:html="http://struts.apache.org/tags-html"
    xmlns:bean="http://struts.apache.org/tags-bean"
    xmlns:logic="http://struts.apache.org/tags-logic"
    xmlns:c="http://java.sun.com/jsp/jstl/core"
    xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
    version="2.0">

    <html:xhtml/>

    <c:set var="action" value="/changePassword" />

    <head>
        <!-- to change the content type or response encoding change the following line -->
        <jsp:directive.page contentType="text/html;charset=utf-8"/>

        <title><bean:message key="title.changePassword.step1" /></title>

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
            <html:form action="${action}" focus="password">
                <html:hidden property="dispatch" value="changePassword" />
                <html:hidden property="readOnly" value="false" />
                <html:hidden property="entity.userId" />
                <html:hidden property="entity.active" />
                <html:hidden property="contact.contactId" />
                
                <div class="hd, maximized_title">
                    <c:set var="icon"><bean:message key="icon.security" /></c:set>
                    <img src="${icon}" hspace="4" />
                    <bean:message key="title.changePassword.step1" />
                </div>
                
                <div class="bd">
                    <p><bean:message key="title.changePassword.step1" /></p>
    
                    <table align="center">
                        <html:errors />
                    </table>
    
                    <table class="nonDataTable">
                        <tr>
                            <td class="jms-label"><bean:message key="label.user.login" /></td>
                            <td><html:text property="entity.login" readonly="true" styleClass="text" /></td>
                        </tr>
                        <html:errors property="password" />
                        <tr>
                            <td class="jms-label"><bean:message key="label.user.password.current" /></td>
                            <td><html:password property="password" styleId="password" styleClass="text" /></td>
                        </tr>
                        <html:errors property="passwordNew" />
                        <tr>
                            <td class="jms-label"><bean:message key="label.user.password.new" /></td>
                            <td><html:password property="passwordNew" styleClass="text" /></td>
                        </tr>
                        <html:errors property="passwordConfirm" />
                        <tr>
                            <td class="jms-label-optional"><bean:message key="label.user.password.confirm" /></td>
                            <td><html:password property="passwordConfirm" styleClass="text" /></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td>
                                <html:submit property="changePassword" styleClass="button-submit">
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